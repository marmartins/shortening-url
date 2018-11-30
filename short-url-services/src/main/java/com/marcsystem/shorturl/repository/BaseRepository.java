package com.marcsystem.shorturl.repository;

import com.marcsystem.shorturl.annotation.RedisEntity;
import com.marcsystem.shorturl.annotation.RedisIndex;
import com.marcsystem.shorturl.annotation.RedisKey;
import com.marcsystem.shorturl.domain.RedisDTO;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.marcsystem.shorturl.repository.RedisObjectMap.REDIS_KEY_SEPARATOR;
import static io.lettuce.core.dynamic.support.ReflectionUtils.getField;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.ReflectionUtils.setField;

abstract class BaseRepository<T extends RedisDTO> {

    @Autowired
    protected ConnectionManager connectionManager;

    public void insert(T entity) {
        try (StatefulRedisConnection<String, String> connection = connectionManager.getConnection()) {
            RedisObjectMap objectMap = extractRedisValues(entity, extractRedisKey(entity, false));
            RedisCommands<String, String> commands = connection.sync();
            commands.hmset(objectMap.getRedisKey(), objectMap.getValues());
        } catch (Exception e) {
            throw new IllegalStateException("Was not possible to persist the object: " + entity.toString(), e);
        }
        updateIndexes(entity);
    }


    protected List<T> findAll(T template) {
        try (StatefulRedisConnection<String, String> connection = connectionManager.getConnection()) {
            List<T> result = new ArrayList<>();
            RedisCommands<String, String> commands = connection.sync();
            commands.keys(extractRedisKey(template, true).getRedisKey()).forEach(key -> {
                result.add(findOne(key));
            });
            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Was not possible to access redis: ", e);
        }
    }

    protected T findOne(T template) {
        return findOne(extractRedisKey(template, true).getRedisKey());
    }

    protected T findOne(String key) {
        try (StatefulRedisConnection<String, String> connection = connectionManager.getConnection()) {
            RedisCommands<String, String> commands = connection.sync();
            return convertToObject(commands.hgetall(key));
        } catch (Exception e) {
            throw new IllegalStateException("Was not possible to access redis: ", e);
        }
    }

    protected void remove(String key) {
        try (StatefulRedisConnection<String, String> connection = connectionManager.getConnection()) {
            RedisCommands<String, String> commands = connection.sync();
            commands.del(key);
        } catch (Exception e) {
            throw new IllegalStateException("Was not possible to access redis: ", e);
        }
    }

    protected RedisObjectMap extractRedisValues(T entity, RedisObjectMap objectMap) {
        Stream.of(entity.getClass().getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    Object fieldValue = getField(field, entity);
                    if (isNull(fieldValue)) {
                        return;
                    }
                    objectMap.put(field.getName(), fieldValue.toString());
                });

        return objectMap;
    }

    protected RedisObjectMap extractRedisKey(T entity, boolean isFind) {

        RedisEntity redisEntityAnn = entity.getClass().getAnnotation(RedisEntity.class);
        RedisObjectMap objectMap = new RedisObjectMap(redisEntityAnn.keyPrefix());

        Stream.of(entity.getClass().getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    Object fieldValue = getField(field, entity);
                    if (isNull(fieldValue)) {
                        return;
                    }
                    RedisKey redisIdAnn = field.getAnnotation(RedisKey.class);
                    if (nonNull(redisIdAnn)) {
                        objectMap.concatRedisKey(fieldValue.toString());

                    }
                });

        if (isFind && !objectMap.getRedisKey().contains(REDIS_KEY_SEPARATOR)) {
            objectMap.concatRedisKey("*");
        }

        return objectMap;
    }

    protected T convertToObject(Map<String, String> values) {
        T entity = createNewInstance();

        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (nonNull(values.get(field.getName()))) {
                if (field.getType().getName().contains("int") ||
                        field.getType().getName().contains("Integer"))
                    setField(field, entity, Integer.parseInt(values.get(field.getName())));
                else if (field.getType().getName().contains("LocalDate"))
                    setField(field, entity, LocalDate.parse(values.get(field.getName()), DateTimeFormatter.ISO_LOCAL_DATE));
                else
                    setField(field, entity, values.get(field.getName()));
            }
        }
        return entity;
    }

    protected void updateIndexes(T entity) {
        for (Field field : entity.getClass().getDeclaredFields()){
            field.setAccessible(true);

            Object value = getField(field, entity);
            if (isNull(value)) {
                continue;
            }

            RedisIndex redisIndexAnn = field.getAnnotation(RedisIndex.class);
            if (isNull(redisIndexAnn)) {
                continue;
            }

            RedisEntity redisEntityAnn = entity.getClass().getAnnotation(RedisEntity.class);
            String keyIndex = redisEntityAnn.keyPrefix() + "." + (redisIndexAnn.name().isEmpty() ? field.getName() : redisIndexAnn.name()) + ".idx";

            try (StatefulRedisConnection<String, String> connection = connectionManager.getConnection()) {
                RedisCommands<String, String> commands = connection.sync();
                commands.zincrby(keyIndex, 1, convertValueAsString(field, value));
            } catch (Exception e) {
                throw new IllegalStateException("Was not possible to access redis: ", e);
            }
        }


    }

    private String convertValueAsString(Field field, Object value) {
        if(field.getType().getName().contains("Date")) {
            return ((LocalDate) value).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        }
        return value.toString();
    }

    protected abstract T createNewInstance();
}
