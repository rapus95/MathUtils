package math.matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public final class Vec implements IVec {

	protected final double vec[];

	/**
	 * Creates a Vec with the dimension {@code dimension}
	 * 
	 * @param dimension
	 *            The dimension the created Vec shall have
	 */
	public Vec(int dimension) {
		vec = new double[dimension];
	}

	/**
	 * Creates a Vec consisting of elements of {@code x}
	 * 
	 * @param x
	 *            the list of coordinates (will be copied)
	 */
	public Vec(double... x) {
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
	public Vec(int length, double... x) {
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
	public Vec(Vec vec) {
		this(vec.getDimensionCount(), vec.vec);
	}

	@Override
	public double getComponent(int dim) {
		return vec[dim];
	}

	@Override
	public void setComponent(int dim, double val) {
		this.vec[dim] = val;
	}

	@Override
	public int getDimensionCount() {
		return vec.length;
	}

	public double dot(Vec other) {
		if (this.getDimensionCount() != other.getDimensionCount())
			return Double.NaN;
		double tmp = 0;
		for (int i = 0; i < getDimensionCount(); i++) {
			tmp += vec[i] * other.vec[i];
		}
		return tmp;
	}

	public Vec cross(Vec other) {
		if (this.getDimensionCount() != 3 || other.getDimensionCount() != 3)
			return null;
		return new Vec(this.vec[1] * other.vec[2] - this.vec[2] * other.vec[1], this.vec[2] * other.vec[0] - this.vec[0] * other.vec[2], this.vec[0] * other.vec[1] - this.vec[1] * other.vec[0]);
	}

	/**
	 * @deprecated Use {@link #pNorm(int p)} with p = 2 instead
	 */
	public double length() {
		return pNorm(2);
	}

	public double pNorm(int p) {
		if (p == 2)
			return Math.sqrt(dot(this));
		return Math.pow(pMul(p), 1 / p);
	}

	public double pMul(int p) {
		if (p == 2)
			return dot(this);
		double sum = 0;
		for (int i = 0; i < vec.length; i++) {
			if (p == 1) {
				sum += vec[i];
			} else {
				sum += Math.pow(vec[i], p);
			}
		}
		return sum;
	}

	public Vec normalize() {
		return normalizeToLength(1);
	}

	public Vec normalizeToLength(double targetLength) {
		double currentLength = this.pNorm(2);
		if (currentLength == 1)
			return this.mul(targetLength);
		if (currentLength == 0 || targetLength == 0)
			return new Vec(this);
		return this.div(this.pNorm(2) / targetLength);
	}

	public Vec add(double val) {
		Vec dest = new Vec(this);
		for (int row = 0; row < dest.vec.length; row++) {
			dest.vec[row] += val;
		}
		return dest;
	}

	public Vec add(Vec other) {
		return addWithMultiplier(other, 1);
	}
	
	public Vec addZeroIfNull(Vec other) {
		return addWithMultiplierAndZeroIfNull(other, 1);
	}

	public Vec addWithMultiplier(Vec other, double multiplier) {
		if (this.getDimensionCount() != other.getDimensionCount())
			throw new IllegalArgumentException("In order to add two Vectors they have to be of the same dimension!");
		Vec dest = new Vec(this);
		for (int row = 0; row < dest.vec.length; row++) {
			dest.vec[row] += other.vec[row] * multiplier;
		}
		return dest;
	}
	
	public Vec addWithMultiplierAndZeroIfNull(Vec other, double multiplier) {
		if(other==null || multiplier==0)
			return this.clone();
		if (this.getDimensionCount() != other.getDimensionCount())
			throw new IllegalArgumentException("In order to add two Vectors they have to be of the same dimension!");
		Vec dest = new Vec(this);
		for (int row = 0; row < dest.vec.length; row++) {
			dest.vec[row] += other.vec[row] * multiplier;
		}
		return dest;
	}

	public Vec sub(Vec other) {
		if (this.getDimensionCount() != other.getDimensionCount())
			throw new IllegalArgumentException("In order to sub two Vectors they have to be of the same dimension!");
		Vec dest = new Vec(this);
		for (int row = 0; row < dest.vec.length; row++) {
			dest.vec[row] -= other.vec[row];
		}
		return dest;
	}

	public Vec mul(double val) {
		if (val == 1)
			return new Vec(this);
		Vec dest = new Vec(this);
		for (int row = 0; row < dest.vec.length; row++) {
			dest.vec[row] *= val;
		}
		return dest;
	}

	public Vec div(double val) {
		if (val == 1)
			return new Vec(this);
		if (val == 0)
			return new Vec(getDimensionCount());
		Vec dest = new Vec(this);
		for (int row = 0; row < dest.vec.length; row++) {
			dest.vec[row] /= val;
		}
		return dest;
	}

	public double[] toArray() {
		double[] array = new double[getDimensionCount()];
		for (int i = 0; i < array.length; i++) {
			array[i] = vec[i];
		}
		return array;
	}

	public Vec clone() {
		return new Vec(this);
	}

	@Override
	public String toString() {
		return "Vec [vec=" + Arrays.toString(this.vec) + "]";
	}

	public double distanceTo(Vec other) {
		return other.sub(this).pNorm(2);
	}

	public boolean distanceToSmaller(Vec other, double dist) {
		return other.sub(this).pMul(2) < dist * dist;
	}

	public boolean withinRectangle(Vec a, Vec b) {
		if (this.getDimensionCount() != a.getDimensionCount() || this.getDimensionCount() != b.getDimensionCount() || a.getDimensionCount() != b.getDimensionCount())
			throw new IllegalArgumentException("In order to compare Vectors they have to be of the same dimension!");
		double left, right, curr;
		for (int i = 0; i < this.getDimensionCount(); i++) {
			left = a.getComponent(i);
			right = b.getComponent(i);
			curr = this.getComponent(i);
			if ((left > curr || curr > right) && (right > curr || curr > left))
				return false;
		}
		return true;
	}

	public static Vec createVectorFromConsole() {
		Scanner s = new Scanner(System.in);
		System.out.println("What Dimension shall the Vector have?");
		Vec v = new Vec(s.nextInt());
		s.nextLine();
		v.readVectorFromConsole(s);
		s.close();
		return v;
	}

	public static Vec createVectorFromConsole(int dimension) {
		Scanner s = new Scanner(System.in);
		Vec v = new Vec(dimension);
		v.readVectorFromConsole(s);
		s.close();
		return v;
	}

	public static Vec fromList(double... x) {
		return new Vec(x);
	}

	public Vec readVectorFromConsole(Scanner s) {
		System.out.println("Enter " + this.getDimensionCount() + " coordinates (as doubles) seperated by spaces.");
		String[] input = s.nextLine().split(" ");
		if (input.length != this.getDimensionCount()) {
			throw new IllegalArgumentException("expected " + this.getDimensionCount() + " elements but got " + input.length + " elements.");
		}
		for (int j = 0; j < input.length; j++) {
			this.vec[j] = Double.valueOf(input[j]);
		}
		return this;
	}

	public static class EulerianComparator implements Comparator<Vec> {
		private final boolean checkDimension;

		public EulerianComparator(boolean checkDimension) {
			this.checkDimension = checkDimension;
		}

		@Override
		public int compare(Vec o1, Vec o2) {
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
				return (int) Math.signum(o1.pNorm(2) - o2.pNorm(2));
		}

	}

	public Vec round() {
		Vec vec = new Vec(this);
		for (int i = 0; i < vec.vec.length; i++) {
			vec.vec[i] = Math.round(vec.vec[i]);
		}
		return vec;
	}
	
	public static Vec mixFromHighestComponents(int dim, Vec... vecs) {
		Vec ret = new Vec(dim);
		for(int i=0; i<vecs.length; i++){
			if(vecs[i]==null || vecs[i].getDimensionCount()!=dim)
				continue;
			for(int j=0; j<dim; j++){
				ret.vec[j] = Math.abs(ret.vec[j])>Math.abs(vecs[i].vec[j])?ret.vec[j]:vecs[i].vec[j];
			}
		}
		return ret;
	}

	public Vec normalizeRandomIfPoint() {
		if (pMul(2) != 0)
			return this.normalize();
		Vec dest = new Vec(this.getDimensionCount());
		for (int i = 0; i < dest.getDimensionCount(); i++) {
			dest.setComponent(i, Math.random() * 2 - 1);
		}
		if (dest.getDimensionCount() > 0 && dest.pMul(2) == 0)
			dest.setComponent(0, 1);
		return dest.normalize();
	}

	public static Vec fromPolarVec(PolarVec x) {
		if (x.getDimensionCount() < 2 || x.length() == 0)
			return new Vec(x.vec);
		Vec dest = new Vec(x.getDimensionCount());
		double beginningPart = x.vec[0];
		int i = 1;
		for (; i < dest.getDimensionCount(); i++) {
			dest.vec[i-1] = beginningPart * Math.cos(x.vec[i]);
			beginningPart *= Math.sin(x.vec[i]);
		}
		dest.vec[i-1] = beginningPart;
		return dest;
	}
	
	public PolarVec toPolarVec(){
		return PolarVec.fromVec(this);
	}


	
	private static final Mat YZSWAP = new Mat(3,3);
	
	static{
		YZSWAP.setIdentity(0);
		YZSWAP.setValue(0, 0, 1);
		YZSWAP.setValue(2, 1, 1);
		YZSWAP.setValue(1, 2, 1);
	}
	
	private static DoubleBuffer tmpBuffer = null; 
	public DoubleBuffer asTmpDoubleBuffer(boolean yzSwap) {
		if(tmpBuffer==null){
			tmpBuffer = ByteBuffer.allocateDirect(4*8).order(ByteOrder.nativeOrder()).asDoubleBuffer();
		}
		if(yzSwap){
			Vec nw = YZSWAP.mul(this);
			tmpBuffer.put(nw.vec);
		}else{
			tmpBuffer.put(vec);
		}
		tmpBuffer.position(0);
		return tmpBuffer;
	}
	
}
