package math.matrix;

import java.util.Arrays;
import java.util.Comparator;

public final class PolarVec implements IVec {

	/**
	 * First element is the length of the vector every other is the i-th angle
	 */
	protected final double vec[];

	/**
	 * Creates a Vec with the dimension {@code dimension}
	 * 
	 * @param dimension
	 *            The dimension the created Vec shall have
	 */
	public PolarVec(int dimension) {
		vec = new double[dimension];
	}

	/**
	 * Creates a Vec consisting of elements of {@code x}
	 * 
	 * @param x
	 *            the list of coordinates (will be copied)
	 */
	public PolarVec(double... x) {
		this(x.length);
		for (int i = 0; i < x.length; i++) {
			vec[i] = x[i];
		}
	}

	/**
	 * Creates a Vec consisting of the first {@code length} elements of
	 * {@code x}
	 * 
	 * @param length
	 *            the dimension of the Vec created
	 * @param x
	 *            the list of coordinates (will be copied) if it's too long it's
	 *            cut else it's padded with 0s
	 */
	public PolarVec(int length, double... x) {
		this(length);
		for (int i = 0; i < vec.length; i++) {
			if (i < x.length)
				vec[i] = x[i];
			else
				vec[i] = 0;
		}
	}

	/**
	 * Copies another Vec
	 * 
	 * @param vec
	 *            the Vec to copy
	 */
	public PolarVec(PolarVec vec) {
		this(vec.getDimensionCount(), vec.vec);
	}

	public double getComponent(int dim) {
		return vec[dim];
	}

	public void setComponent(int dim, double val) {
		if(dim>0){
			while (val <= -Math.PI) val += 2*Math.PI;
		    while (val > Math.PI) val -= 2*Math.PI;
		}
		this.vec[dim] = val;
	}

	public int getDimensionCount() {
		return vec.length;
	}

	public double length() {
		return vec[0];
	}

	public PolarVec normalize() {
		return normalizeToLength(1);
	}

	public PolarVec normalizeToLength(double targetLength) {
		if (vec[0] == 0 || vec[0] == 0)
			return new PolarVec(this);
		PolarVec dest = new PolarVec(this);
		dest.setComponent(0, targetLength);
		return dest;
	}

	public PolarVec mul(double val) {
		PolarVec dest = new PolarVec(this);
		dest.vec[0] *= val;
		return dest;
	}

	public PolarVec div(double val) {
		if (val == 0)
			return new PolarVec(getDimensionCount());
		PolarVec dest = new PolarVec(this);
		dest.vec[0] /= val;
		return dest;
	}

	public double[] toArray() {
		double[] array = new double[getDimensionCount()];
		for (int i = 0; i < array.length; i++) {
			array[i] = vec[i];
		}
		return array;
	}

	public PolarVec clone() {
		return new PolarVec(this);
	}

	@Override
	public String toString() {
		return "PolarVec [vec=" + Arrays.toString(this.vec) + "]";
	}

	public static PolarVec fromList(double... x) {
		return new PolarVec(x);
	}

	public static class EulerianComparator implements Comparator<PolarVec> {
		private final boolean checkDimension;

		public EulerianComparator(boolean checkDimension) {
			this.checkDimension = checkDimension;
		}

		@Override
		public int compare(PolarVec o1, PolarVec o2) {
			if (o1 == null) {
				if (o2 == null)
					return 0;
				else
					return -1;
			}
			if (o2 == null)
				return 1;
			int dimDif;
			if (checkDimension && (dimDif = o1.getDimensionCount() - o2.getDimensionCount()) != 0)
				return dimDif;
			else
				return (int) Math.signum(o1.length() - o2.length());
		}

	}

	public static PolarVec fromVec(Vec x) {
		double tmpFirstAngle;
		if (x.getDimensionCount() < 2 || (tmpFirstAngle = x.pMul(2)) == 0)
			return new PolarVec(x.vec);
		PolarVec dest = new PolarVec(x.getDimensionCount());
		int index = dest.getDimensionCount() - 1;
		boolean onlyZeros = x.vec[index] == 0;
		double sum = x.vec[index] * x.vec[index] + x.vec[index - 1] * x.vec[index - 1];
		if (onlyZeros && x.vec[index - 1] == 0) {
			dest.vec[index] = 0;
		} else {
			onlyZeros = false;
			if (x.vec[index] >= 0) {
				dest.vec[index] = Math.acos(x.vec[index - 1] / Math.sqrt(sum));
			} else {
				dest.vec[index] = 2 * Math.PI - Math.acos(x.vec[index - 1] / Math.sqrt(sum));
			}
		}
		for (index--; index >= 2; index--) {
			if ((onlyZeros && x.vec[index - 1] == 0)) {
				dest.vec[index] = 0;
			} else {
				sum += x.vec[index - 1] * x.vec[index - 1];
				if (onlyZeros) {
					onlyZeros = false;
					dest.vec[index] = x.vec[index - 1] > 0 ? 0 : Math.PI;
				} else {
					dest.vec[index] = Math.acos(x.vec[index - 1] / Math.sqrt(sum));
				}
			}
		}
		dest.vec[0] = Math.sqrt(tmpFirstAngle);
		dest.vec[1] = Math.acos(x.vec[0] / dest.vec[0]);
		return dest;
	}
	
	public Vec toVec(){
		return Vec.fromPolarVec(this);
	}

}
