package com.marcsystem.shorturl.shorturlservices;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ShortUrlServicesApplicationTests {


	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int    BASE     = ALPHABET.length();

	public static String encode(int num) {
		StringBuilder sb = new StringBuilder();
		while ( num > 0 ) {
			sb.append( ALPHABET.charAt( num % BASE ) );
			num /= BASE;
		}
		return sb.reverse().toString();
	}

	public static int decode(String str) {
		int num = str.length();
		for ( int i = 0 ; i < str.length(); i++ ) {
			if(ALPHABET.indexOf(str.charAt(i)) != -1)
				num += ALPHABET.indexOf(str.charAt(i)) * BASE;
		}
		return num;
	}


	@Test
	public void contextLoads() {

		String url = "https://www.google.com.br/search";

        int numC = decode(url);
        String encode = encode(numC);
        int num = decode(encode);

        System.out.println(encode);
		System.out.println(numC);
		System.out.println(num);


	}

}
