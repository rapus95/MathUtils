package math.utils;

public class MathUtils {

	public static double absMin(double... params) {
		return params.length == 0 ? Double.NaN : params[absMinIndex(params)];
	}

	public static int absMinIndex(double... params) {
		if (params.length == 0)
			return -1;
		if (params.length == 1)
			return 0;
		int index = 0;
		double abs = Math.abs(params[0]), tmp;
		for (int i = 1; i < params.length; i++) {
			if ((tmp = Math.abs(params[i])) < abs) {
				abs = tmp;
				index = i;
			}
		}
		return index;
	}

	public static double absMax(double... params) {
		return params.length == 0 ? Double.NaN : params[absMaxIndex(params)];
	}

	public static int absMaxIndex(double... params) {
		if (params.length == 0)
			return -1;
		if (params.length == 1)
			return 0;
		int index = 0;
		double abs = Math.abs(params[0]), tmp;
		for (int i = 1; i < params.length; i++) {
			if ((tmp = Math.abs(params[i])) > abs) {
				abs = tmp;
				index = i;
			}
		}
		return index;
	}

	public static double clamp(double value, double min, double max) {
		return Operators.CLAMP.calc(value, min, max);
	}

}
