package math.vecmat;


final class Operators {

	private Operators() {
	}

	static interface Func1 {

		public double calc(double f);

	}

	static interface Func2 {

		public double calc(double f1, double f2);

	}

	static interface Func3 {

		public double calc(double f1, double f2, double f3);

	}

	static final Func2 ADD = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return f1 + f2;
		}

	};

	static final Func2 SUB = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return f1 - f2;
		}

	};

	static final Func2 SUB2 = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return f2 - f1;
		}

	};

	static final Func1 NEG = new Func1() {

		@Override
		public double calc(double f1) {
			return -f1;
		}

	};

	static final Func2 MUL = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return f1 * f2;
		}

	};

	static final Func2 DIV = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return f1 / f2;
		}

	};

	static final Func2 DIV2 = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return f2 / f1;
		}

	};

	static final Func2 MOD = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return f1 % f2;
		}

	};

	static final Func2 MOD2 = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return f2 % f1;
		}

	};

	static final Func2 POW = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return (double) Math.pow(f1, f2);
		}

	};

	static final Func2 POW2 = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return (double) Math.pow(f2, f1);
		}

	};

	static final Func1 RADIANS = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.toRadians(f);
		}

	};

	static final Func1 DEGREES = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.toDegrees(f);
		}

	};

	static final Func1 SIN = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.sin(f);
		}

	};

	static final Func1 COS = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.cos(f);
		}

	};

	static final Func1 TAN = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.tan(f);
		}

	};

	static final Func1 ASIN = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.asin(f);
		}

	};

	static final Func1 ACOS = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.acos(f);
		}

	};

	static final Func1 ATAN = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.atan(f);
		}

	};

	static final Func2 ATAN2 = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return (double) Math.atan2(f1, f2);
		}

	};

	static final Func1 SINH = new Func1() {

		@Override
		public double calc(double f) {
			return (double) (Math.exp(f) - Math.exp(-f)) * 0.5f;
		}

	};

	static final Func1 COSH = new Func1() {

		@Override
		public double calc(double f) {
			return (double) (Math.exp(f) + Math.exp(-f)) * 0.5f;
		}

	};

	static final Func1 TANH = new Func1() {

		@Override
		public double calc(double f) {
			return (double) ((Math.exp(f) - Math.exp(-f)) / (Math.exp(f) + Math.exp(-f)));
		}

	};

	static final Func1 ASINH = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.log(f + Math.sqrt(f * f + 1.0f));
		}

	};

	static final Func1 ACOSH = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.log(f + Math.sqrt(f * f - 1.0f));
		}

	};

	static final Func1 ATANH = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.log((1.0f + f) / (1.0f - f)) * 0.5f;
		}

	};

	static final Func1 EXP = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.exp(f);
		}

	};

	static final Func1 LOG = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.log(f);
		}

	};

	static final Func1 EXP2 = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.pow(2, f);
		}

	};

	static final Func1 LOG2 = new Func1() {

		public final double L2 = Math.log(2);

		@Override
		public double calc(double f) {
			return (double) (Math.log(f) / L2);
		}

	};

	static final Func1 SQRT = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.sqrt(f);
		}

	};

	static final Func1 INVERSESQRT = new Func1() {

		@Override
		public double calc(double f) {
			return 1 / (double) Math.sqrt(f);
		}

	};

	static final Func1 ABS = new Func1() {

		@Override
		public double calc(double f) {
			return f < 0 ? -f : f;
		}

	};

	static final Func1 SIGN = new Func1() {

		@Override
		public double calc(double f) {
			return f > 0 ? 1 : f < 0 ? -1 : 0;
		}

	};

	static final Func1 ROUNDEVEN = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.rint(f);
		}

	};

	static final Func1 ROUND = new Func1() {

		@Override
		public double calc(double f) {
			return Math.round(f);
		}

	};

	static final Func1 TRUNC = new Func1() {

		@Override
		public double calc(double f) {
			return (int) f;
		}

	};

	static final Func1 FLOOR = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.floor(f);
		}

	};

	static final Func1 CEIL = new Func1() {

		@Override
		public double calc(double f) {
			return (double) Math.ceil(f);
		}

	};

	static final Func1 FRACT = new Func1() {

		@Override
		public double calc(double f) {
			return f - (double) Math.floor(f);
		}

	};

	static final Func2 MIN = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return f1 > f2 ? f2 : f1;
		}

	};

	static final Func2 MAX = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return f1 > f2 ? f1 : f2;
		}

	};

	static final Func3 CLAMP = new Func3() {

		@Override
		public double calc(double f1, double f2, double f3) {
			return f1 < f2 ? f2 : f1 > f3 ? f3 : f1;
		}

	};

	static final Func3 MIX = new Func3() {

		@Override
		public double calc(double f1, double f2, double f3) {
			return (1 - f3) * f1 + f3 * f2;
		}

	};

	static final Func2 STEP = new Func2() {

		@Override
		public double calc(double f1, double f2) {
			return f1 < f2 ? 0 : 1;
		}

	};

	static final Func3 SMOOTHSTEP = new Func3() {

		@Override
		public double calc(double f1, double f2, double f3) {
			final double t = (f1 - f2) / (f3 - f2);
			if (t < 0)
				return 0;
			if (t > 1)
				return 1;
			return t * t * (3.0f - 2.0f * t);
		}

	};

	public static final Func3 ADD_SCALED = new Func3() {

		@Override
		public double calc(double f1, double f2, double f3) {
			return f1 + f2 * f3;
		}

	};

}
