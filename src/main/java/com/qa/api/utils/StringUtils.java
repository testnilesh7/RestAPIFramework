package com.qa.api.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StringUtils {

	// Generate Random email id
	public static String getRandomEmailId() {

        return "NBAPIAutomation"+System.currentTimeMillis()+"@opencart.com";
    }
	
	public static String genrateRandomPhoneNumber() {
		
		Random random = new Random();

        // Valid starting digits for Indian mobile numbers: 9, 8, 7, or 6
        int firstDigit = 6 + random.nextInt(4); // Generates 6, 7, 8, or 9

        // Generate the remaining 9 digits
        long remainingDigits = 100000000L + (long)(random.nextDouble() * 900000000L);

        return firstDigit + String.valueOf(remainingDigits);
	}
	
	public static int generateFourDigitRandomNumber() {
        // Generates a number between 1000 and 9999 (inclusive)
        return ThreadLocalRandom.current().nextInt(1000, 10000);
    }
}
