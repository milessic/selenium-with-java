package utils;

import java.util.Random;

public class DataGenerator {
    Random random;
	public DataGenerator(){
		random = new Random();
	}

	public String generateInvoiceName(){
		int leftLimit = 97; // letter 'a'
    	int rightLimit = 122; // letter 'z'
    	int targetStringLength = 10;

    	return random.ints(leftLimit, rightLimit + 1)
    	  .limit(targetStringLength)
    	  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
    	  .toString();
	}
}
