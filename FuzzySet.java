class FuzzySet {
    double slope1, slope2;
    double c1, c2;
    double min, max;
    boolean slicing = false;
    double topValue = 1.0;

    FuzzySet(double x1, double x2) {
        min = x1;
        max = x2;
        if (x2 - x1 >= 0.2) {
            slope1 = 10;
            slope2 = -10;
            c1 = -(slope1 * x1);
            c2 = -(slope2 * x2);
        } else {
            slope1 = (x2 - x1) / 20;
            slope2 = -(x2 - x1) / 20;
            c1 = -(slope1 * x1);
            c2 = -(slope2 * x2);
        }
    }

    void slice(double x) {
        if (slicing == false) {
            topValue = x;
            slicing = true;
        } else {
            if (topValue > x) {
            } else {
                topValue = x;
            }
        }
    }

    double membership(double x) {
        double result;
        if (x < min || x > max) {
            return 0.0;
        } else {
            if (x >= (min + 0.1) && (x <= (max - 0.1))) {
                return topValue;
            } else if (x >= (min + 0.1)) {
                result = (slope2 * x) + c2;
                return Math.min(result, topValue);
            } else {
                result = (slope1 * x) + c1;
                return Math.min(result, topValue);
            }
        }
    }
}