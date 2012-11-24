package Hokuto;

import java.awt.geom.Point2D;

public class Utils {
    
    private Utils() {
	throw new AssertionError();
    }
    
    public static int random(int min, int max) {
	return (int)(Math.random() * (max-min)) + min;
    }
    public static double random(double min, double max) {
	return Math.random() * (max-min) + min;
    }
    
    public static double round(double value, int precision) {
	double pow = Math.pow(10, precision);
	return (double)Math.round(value * pow) / pow;
    }
    
    public static double absoluteBearing(double x1, double y1, double x2, double y2) {
		double xo = x2 - x1;
		double yo = y2 - y1;
		double h = Point2D.distance(x1, y1, x2, y2);
		if (xo > 0 && yo > 0) {
			return Math.asin(xo / h);
		}
		if (xo > 0 && yo < 0) {
			return Math.PI - Math.asin(xo / h);
		}
		if (xo < 0 && yo < 0) {
			return Math.PI + Math.asin(-xo / h);
		}
		if (xo < 0 && yo > 0) {
			return 2.0 * Math.PI - Math.asin(-xo / h);
		}
		return 0;
	}
    
}