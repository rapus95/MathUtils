package math.vecmat;

import java.util.Arrays;
import java.util.Comparator;

public final class PolarVec {

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
		this(vec.dim(), vec.vec);
	}

	public double get(int dim) {
		return vec[dim];
	}

	public void set(int dim, double val) {
		if(dim>0){
			while (val <= -Math.PI) val += 2*Math.PI;
		    while (val > Math.PI) val -= 2*Math.PI;
		}
		this.vec[dim] = val;
	}

	public int dim() {
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
		dest.set(0, targetLength);
		return dest;
	}

	public PolarVec mul(double val) {
		PolarVec dest = new PolarVec(this);
		dest.vec[0] *= val;
		return dest;
	}

	public PolarVec div(double val) {
		if (val == 0)
			return new PolarVec(dim());
		PolarVec dest = new PolarVec(this);
		dest.vec[0] /= val;
		return dest;
	}

	public double[] toArray() {
		double[] array = new double[dim()];
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
			if (checkDimension && (dimDif = o1.dim() - o2.dim()) != 0)
				return dimDif;
			else
				return (int) Math.signum(o1.length() - o2.length());
		}

	}

	public static PolarVec fromVec(Vec<?> x) {
		double tmpFirstAngle;
		if (x.dim() < 2 || (tmpFirstAngle = x.pMul(2)) == 0)
			return new PolarVec(x.toArray());
		PolarVec dest = new PolarVec(x.dim());
		int index = dest.dim() - 1;
		boolean onlyZeros = x.get(index) == 0;
		double xi = x.get(index);
		double xim1 = x.get(index-1);
		double sum = xi * xi + xim1 * xim1;
		if (onlyZeros && xim1 == 0) {
			dest.vec[index] = 0;
		} else {
			onlyZeros = false;
			if (xi >= 0) {
				dest.vec[index] = Math.acos(xim1 / Math.sqrt(sum));
			} else {
				dest.vec[index] = 2 * Math.PI - Math.acos(xim1 / Math.sqrt(sum));
			}
		}
		for (index--; index >= 2; index--) {
			xim1 = x.get(index-1);
			if ((onlyZeros && xim1 == 0)) {
				dest.vec[index] = 0;
			} else {
				sum += xim1 * xim1;
				if (onlyZeros) {
					onlyZeros = false;
					dest.vec[index] = xim1 > 0 ? 0 : Math.PI;
				} else {
					dest.vec[index] = Math.acos(xim1 / Math.sqrt(sum));
				}
			}
		}
		dest.vec[0] = Math.sqrt(tmpFirstAngle);
		dest.vec[1] = Math.acos(x.get(0) / dest.vec[0]);
		return dest;
	}
	
	public static <V extends Vec<V>> V VecXFromPolarVec(PolarVec x) {
		if (x.dim() < 2 || x.length() == 0)
			return Vec.VecX(x.vec);
		final int size = x.dim();
		V dest = Vec.VecX(size);
		double beginningPart = x.vec[0];
		int i = 1;
		for (; i < size; i++) {
			dest.set(i - 1, beginningPart * Math.cos(x.vec[i]));
			beginningPart *= Math.sin(x.vec[i]);
		}
		dest.set(i - 1, beginningPart);
		return dest;
	}
	
	public <V extends Vec<V>> V toVec(){
		return VecXFromPolarVec(this);
	}

}
