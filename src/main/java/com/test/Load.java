package com.test;

/**
 * SPDX-License-Identifier: MIT
 *
 * Generates Load on the CPU by keeping it busy for the given load percentage
 * @author Sriram
 */
public class Load {
	
	
	private static void spin(int milliseconds) {
	    long sleepTime = milliseconds*1000000L; // convert to nanoseconds
	    long startTime = System.nanoTime();
	    while ((System.nanoTime() - startTime) < sleepTime) {}
	}
	
    /**
     * Starts the Load Generation
     * @param args Command line arguments, ignored
     */
    public static void loadTest() {
    	 final int NUM_TESTS = 1000;
    	    long start = System.nanoTime();
    	    for (int i = 0; i < NUM_TESTS; i++) {
    	        spin(500);
    	    }
    	    System.out.println("Took " + (System.nanoTime()-start)/1000000 +
    	        "ms (expected " + (NUM_TESTS*500) + ")");
    }

    /**
     * Thread that actually generates the given load
     * @author Sriram
     */
    private static class BusyThread extends Thread {
        private double load;
        private long duration;

        /**
         * Constructor which creates the thread
         * @param name Name of this thread
         * @param load Load % that this thread should generate
         * @param duration Duration that this thread should generate the load for
         */
        public BusyThread(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }

        /**
         * Generates the load when run
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}