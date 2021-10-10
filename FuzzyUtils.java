public class FuzzyUtils {

    static double price(int x) {
        double degree = (360 * x) / 19;
        double r = (Math.random() * 2) - 1;
        double random = 8 * r * (x % 2);
        double value = 10 + (2.8 * (Math.sin(degree))) + (0.9 * (Math.cos(degree))) + random;
        return value;
    }

    static double mad(int x) {
        double degree1 = 0.4 * x;
        double degree2 = 0.5 * x;
        double value = 0.6 * (Math.cos(degree1)) - Math.sin(degree2);
        return value;
    }

    static double normalizePrice(double x) {
        double max = 23;
        double min = 2;
        if (x > max) {
            return 1;
        } else if (x < min) {
            return 0;
        } else {
            return ((x - min) / (max - min));
        }
    }

    static double normalizeMad(double x) {
        double max = 1.12;
        double min = -1.12;
        if (x > max) {
            return 1.0;
        } else if (x < min) {
            return 0.0;
        } else {
            return ((x - min) / (max - min));
        }
    }

    public static double roundToDouble(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value *= factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    static int defuzzifyShares(double x) {
        return (int) ((roundToDouble(x, 2) - 0.5) * 700);
    }

}

