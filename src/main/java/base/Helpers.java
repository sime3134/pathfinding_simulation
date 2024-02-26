package base;

public class Helpers {

    public static void delay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static double calculateStandardDeviation(double variance) {
        return Math.sqrt(variance);
    }
}
