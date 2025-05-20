package game;

public class DynamicTimer {
    private static double totalTime = 0;
    private static int gamesPlayed = 0;
    private static final int DEFAULT_TIME = 300; // Default seconds if no data yet

    public static int getTimeLimit() {
        if (gamesPlayed == 0)
            return DEFAULT_TIME;
        return (int) Math.round(totalTime / gamesPlayed);
    }

    public static void updateAverage(long solveTimeMillis) {
        totalTime += solveTimeMillis / 1000.0;
        gamesPlayed++;
    }
}