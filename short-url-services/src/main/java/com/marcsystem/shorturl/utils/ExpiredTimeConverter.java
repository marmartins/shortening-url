package com.marcsystem.shorturl.utils;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;

/**
 * This class convert the properties referred to expiration time.
 */
public class ExpiredTimeConverter {

	@Value("${expired.time.value}")
	private long time;

	@Value("${expired.time.unit}")
	private TimeUnit timeUnit;

	private final static ExpiredTimeConverter INSTANCE = new ExpiredTimeConverter();

	private ExpiredTimeConverter() {
	}

	public static ExpiredTimeConverter getInstance() {
		return INSTANCE;
	}

	/**
	 * This always return the time in minutes.
	 * <p>
	 *     If unit is HOURS, the return is specified time * 60
	 * </p>
	 * <p>
	 *     If unit is DAYS, the return is specified time * 60 * 24
	 * </p>
	 *
	 * @return - time number in minutes.
	 */
	public long getTime() {
		return timeUnit == HOURS ? time * 60
				: timeUnit == DAYS ? time * 60 * 24
				: time;
	}
}
