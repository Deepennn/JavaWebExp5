package util;

import java.util.Random;

public class IdGenerator {
	
	/*
	 * id 1
	 * */
    private static int currentId = 1; 

    public static synchronized int generatePositiveId() {
        return currentId++;
    }
    
	/*
	 * id 2
	 * */
    public static int generatePositiveRandomId() {
        Random random = new Random();
        int randomId = random.nextInt(Integer.MAX_VALUE);
        return Math.abs(randomId);
    }
    
    /*
	 * id 3
	 * */
    private static int sequence_int = 0;

    public static synchronized int generateSnowflakeId_simplified() {
        long timestamp = System.currentTimeMillis();
        return Math.abs((int)(timestamp << 8 | sequence_int++));
    }
    
    /*
   	 * id 4
   	 * */
    private static final int BITS_TIMESTAMP = 42;
    private static final int BITS_SEQUENCE = 12;

    private static final long MAX_TIMESTAMP = -1L ^ (-1L << BITS_TIMESTAMP);
    private static final long SEQUENCE_MASK = -1L ^ (-1L << BITS_SEQUENCE);

    private static long lastTimestamp = -1L;
    private static long sequence_long = 0;

    public static synchronized int generateSnowflakeId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence_long = (sequence_long + 1) & SEQUENCE_MASK;
            if (sequence_long == 0) {
                // Sequence overflow, wait for the next millisecond
                currentTimestamp = waitUntilNextMillis(currentTimestamp);
            }
        } else {
            sequence_long = 0;
        }

        lastTimestamp = currentTimestamp;

        return Math.abs((int)((currentTimestamp << (BITS_SEQUENCE)) | sequence_long));
    }

    private  static long waitUntilNextMillis(long lastTimestamp) {
        long currentTimestamp;
        do {
            currentTimestamp = System.currentTimeMillis();
        } while (currentTimestamp <= lastTimestamp);
        return currentTimestamp;
    }
}