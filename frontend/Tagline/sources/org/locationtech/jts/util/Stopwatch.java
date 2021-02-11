package org.locationtech.jts.util;

public class Stopwatch {
    private boolean isRunning = false;
    private long startTimestamp;
    private long totalTime = 0;

    public Stopwatch() {
        start();
    }

    public void start() {
        if (!this.isRunning) {
            this.startTimestamp = System.currentTimeMillis();
            this.isRunning = true;
        }
    }

    public long stop() {
        if (this.isRunning) {
            updateTotalTime();
            this.isRunning = false;
        }
        return this.totalTime;
    }

    public void reset() {
        this.totalTime = 0;
        this.startTimestamp = System.currentTimeMillis();
    }

    public long split() {
        if (this.isRunning) {
            updateTotalTime();
        }
        return this.totalTime;
    }

    private void updateTotalTime() {
        long endTimestamp = System.currentTimeMillis();
        long elapsedTime = endTimestamp - this.startTimestamp;
        this.startTimestamp = endTimestamp;
        this.totalTime += elapsedTime;
    }

    public long getTime() {
        updateTotalTime();
        return this.totalTime;
    }

    public String getTimeString() {
        return getTimeString(getTime());
    }

    public static String getTimeString(long j) {
        StringBuilder sb;
        String totalTimeStr;
        StringBuilder sb2;
        long timeMillis = j;
        if (timeMillis < 10000) {
            new StringBuilder();
            totalTimeStr = sb2.append(timeMillis).append(" ms").toString();
        } else {
            new StringBuilder();
            totalTimeStr = sb.append(((double) timeMillis) / 1000.0d).append(" s").toString();
        }
        return totalTimeStr;
    }
}
