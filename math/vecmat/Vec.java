package math.vecmat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import math.utils.Operators;
import math.utils.Operators.Func1;
import math.utils.Operators.Func2;
import math.utils.Operators.Func3;

public abstract class Vec<T extends Vec<T>> implements Iterable<Double>, Cloneable {

	public abstract int dim();

	public abstract double get(int i);

	public int getInt(int i) {
		return (int) Math.round(get(i));
	}

	public abstract void set(int i, double v);

	public abstract double get(char c);

	public int getInt(char c) {
		return (int) Math.round(get(c));
	}

	public abstract void set(char c, double v);

	public abstract int getIndex(char c);

	public <V extends Vec<V>> V get(String t) {
		final int size = t.length();
		int[] indices = new int[size];
		for (int i = 0; i < size; i++) {
			char c = t.charAt(i);
			int id = getIndex(c);
			if (id == -1)
				throw new IllegalArgumentException("Bad character '" + c + "'");
			indices[i] = getIndex(c);
		}
		return VecX_checked(this, indices);
	}

	public void set(String t, Vec<?> v) {
		final int size = t.length();
		if (size != v.dim()) {
			throw new IllegalArgumentException("String length have to equal vector length");
		}
		for (int i = 0; i < size; i++) {
			char c = t.charAt(i);
			set(c, v.get(i));
		}
	}

	public <V extends Vec<V>> V get(int... indices) {
		return VecX(this, indices);
	}

	public void set(Vec<?> v, int... indices) {
		set(indices, v);
	}

	public void set(int[] indices, Vec<?> v) {
		final int size = indices.length;
		if (size != v.dim()) {
			throw new IllegalArgumentException("indices length have to equal vector length");
		}
		for (int i = 0; i < size; i++) {
			set(indices[i], v.get(i));
		}
	}

	public void set(T v) {
		final int size = dim();
		if (size != v.dim())
			throw new IllegalArgumentException();
		for (int i = 0; i < size; i++) {
			set(i, v.get(i));
		}
	}

	public T addScaled(T v, double scale) {
		if (scale == 0)
			return clone();
		return forEach(v, scale, Operators.ADD_SCALED);
	}

	public T addScaledAndZeroIfNull(T v, double scale) {
		if (v == null || scale == 0)
			return clone();
		return forEach(v, scale, Operators.ADD_SCALED);
	}

	public T add(double n) {
		if (n == 0)
			return clone();
		return forEach(n, Operators.ADD);
	}

	public T add(T v) {
		return forEach(v, Operators.ADD);
	}

	public T addZeroIfNull(T v) {
		if (v == null)
			return clone();
		return forEach(v, Operators.ADD);
	}

	public T add2(double n) {
		if (n == 0)
			return clone();
		return forEach(n, Operators.ADD);
	}

	public T sub(double n) {
		if (n == 0)
			return clone();
		return forEach(n, Operators.SUB);
	}

	public T sub(T v) {
		return forEach(v, Operators.SUB);
	}

	public T sub2(double n) {
		if (n == 0)
			return clone();
		return forEach(n, Operators.SUB2);
	}

	public T neg() {
		return forEach(Operators.NEG);
	}

	public T mul(double n) {
		if (n == 0)
			return _new();
		if (n == 1)
			return clone();
		return forEach(n, Operators.MUL);
	}

	public T mul(T v) {
		return forEach(v, Operators.MUL);
	}

	public T mul2(double n) {
		if (n == 0)
			return _new();
		if (n == 1)
			return clone();
		return forEach(n, Operators.MUL);
	}

	public T div(double n) {
		if (n == 1)
			return clone();
		return forEach(n, Operators.DIV);
	}

	public T div(T v) {
		return forEach(v, Operators.DIV);
	}

	public T div2(double n) {
		return forEach(n, Operators.DIV2);
	}

	public T mod(double n) {
		return forEach(n, Operators.MOD);
	}

	public T mod(T v) {
		return forEach(v, Operators.MOD);
	}

	public T mod2(double n) {
		return forEach(n, Operators.MOD2);
	}

	public T pow(double n) {
		if (n == 0)
			return VecX(dim(), 1);
		if (n == 1)
			return clone();
		return forEach(n, Operators.POW);
	}

	public T pow(T v) {
		return forEach(v, Operators.POW);
	}

	public T pow2(double n) {
		if (n == 1)
			return VecX(dim(), 1);
		return forEach(n, Operators.POW2);
	}

	public T radians() {
		return forEach(Operators.RADIANS);
	}

	public T degrees() {
		return forEach(Operators.DEGREES);
	}

	public T sin() {
		return forEach(Operators.SIN);
	}

	public T cos() {
		return forEach(Operators.COS);
	}

	public T tan() {
		return forEach(Operators.TAN);
	}

	public T asin() {
		return forEach(Operators.ASIN);
	}

	public T acos() {
		return forEach(Operators.ACOS);
	}

	public T atan2(T x) {
		return forEach(x, Operators.ATAN2);
	}

	public T atan() {
		return forEach(Operators.ATAN);
	}

	public T sinh() {
		return forEach(Operators.SINH);
	}

	public T cosh() {
		return forEach(Operators.COSH);
	}

	public T tanh() {
		return forEach(Operators.TANH);
	}

	public T asinh() {
		return forEach(Operators.ASINH);
	}

	public T acosh() {
		return forEach(Operators.ACOSH);
	}

	public T atanh() {
		return forEach(Operators.ATANH);
	}

	public T exp() {
		return forEach(Operators.EXP);
	}

	public T log() {
		return forEach(Operators.LOG);
	}

	public T exp2() {
		return forEach(Operators.EXP2);
	}

	public T log2() {
		return forEach(Operators.LOG2);
	}

	public T sqrt() {
		return forEach(Operators.SQRT);
	}

	public T inversesqrt() {
		return forEach(Operators.INVERSESQRT);
	}

	public T abs() {
		return forEach(Operators.ABS);
	}

	public T sign() {
		return forEach(Operators.SIGN);
	}

	public T roundeven() {
		return forEach(Operators.ROUNDEVEN);
	}

	public T round() {
		return forEach(Operators.ROUND);
	}

	public T trunc() {
		return forEach(Operators.TRUNC);
	}

	public T floor() {
		return forEach(Operators.FLOOR);
	}

	public T ceil() {
		return forEach(Operators.CEIL);
	}

	public T fract() {
		return forEach(Operators.FRACT);
	}

	public T min(double n) {
		return forEach(n, Operators.MIN);
	}

	public T min(T v) {
		return forEach(v, Operators.MIN);
	}

	public T max(double n) {
		return forEach(n, Operators.MAX);
	}

	public T max(T v) {
		return forEach(v, Operators.MAX);
	}

	public T clamp(double min, double max) {
		return forEach(min, max, Operators.CLAMP);
	}

	public T clamp(T min, T max) {
		return forEach(min, max, Operators.CLAMP);
	}

	public T mix(T v, double p) {
		return forEach(v, p, Operators.MIX);
	}

	public T mix(T v, T p) {
		return forEach(v, p, Operators.MIX);
	}

	public T step(double edge) {
		return forEach(edge, Operators.STEP);
	}

	public T step(T edge) {
		return forEach(edge, Operators.STEP);
	}

	public T smoothstep(double edge0, double edge1) {
		return forEach(edge0, edge1, Operators.SMOOTHSTEP);
	}

	public T smoothstep(T edge0, T edge1) {
		return forEach(edge0, edge1, Operators.SMOOTHSTEP);
	}

	public double length() {
		final int size = dim();
		double l = 0;
		for (int i = 0; i < size; i++) {
			final double g = get(i);
			l += g * g;
		}
		return (double) Math.sqrt(l);
	}

	public double distance(T v) {
		return sub(v).length();
	}

	public boolean distanceSmaller(T v, double dist) {
		T tmp = sub(v);
		return tmp.dot(tmp) < dist * dist;
	}

	public double dot(T v) {
		final int size = dim();
		if (Debug.DEBUG) {
			if (size != v.dim())
				throw new IllegalArgumentException("In order to combine three Vectors they have to be of the same dimension!");
		}
		double ret = 0;
		for (int i = 0; i < size; i++) {
			ret += get(i) * v.get(i);
		}
		return ret;
	}

	public T normalize() {
		return div(length());
	}

	public T normalizeToLength(double targetLength) {
		if (targetLength == 0)
			return _new();
		double currentLength = length();
		if (currentLength == 0)
			return _new();
		if (currentLength == 1)
			return this.mul(targetLength);
		return this.div(currentLength / targetLength);
	}

	public T normalizeRandomIfPoint() {
		@SuppressWarnings("unchecked")
		double dot = dot((T) this);
		if (dot != 0)
			return normalize();
		T dest = _new();
		final int size = dim();
		for (int i = 0; i < size; i++) {
			dest.set(i, Math.random() * 2 - 1);
		}
		if (size > 0 && dest.dot(dest) == 0)
			dest.set(0, 1);
		return dest.normalize();
	}

	private static double sqrt(double v, int sqrt) {
		if (sqrt == 1)
			return v;
		if (sqrt % 2 == 0) {
			return Math.pow(v, 1.0 / sqrt);
		} else if (v < 0) {
			return -Math.pow(-v, 1.0 / sqrt);
		} else {
			return Math.pow(v, 1.0 / sqrt);
		}
	}

	public double pNorm(int p) {
		return p == 2 ? length() : sqrt(pMul(p), p);
	}

	@SuppressWarnings("unchecked")
	public double pMul(int p) {
		if (p == 2)
			return dot((T) this);
		double sum = 0;
		final int size = dim();
		if (p == 1) {
			for (int i = 0; i < size; i++) {
				sum += get(i);
			}
		} else {
			for (int i = 0; i < size; i++) {
				sum += Math.pow(get(i), p);
			}
		}
		return sum;
	}

	public boolean withinRectangle(T v2, T v3) {
		final int size = dim();
		if (Debug.DEBUG)
			if (size != v2.dim() || size != v3.dim())
				throw new IllegalArgumentException("In order to compare Vectors they have to be of the same dimension!");
		double left, right, curr;
		for (int i = 0; i < size; i++) {
			left = v2.get(i);
			right = v3.get(i);
			curr = this.get(i);
			if ((left > curr || curr > right) && (right > curr || curr > left))
				return false;
		}
		return true;
	}

	@Override
	public abstract T clone();

	protected abstract T _new();

	@Override
	public String toString() {
		String s = "[";
		final int size = dim();
		if (size > 0) {
			s += get(0);
			for (int i = 1; i < size; i++) {
				s += ", " + get(i);
			}
		}
		return s + "]";
	}

	@Override
	public ListIterator<Double> iterator() {
		return new VecIterator(this);
	}

	public double[] toArray() {
		final int size = dim();
		double[] a = new double[size];
		for (int i = 0; i < size; i++) {
			a[i] = get(i);
		}
		return a;
	}

	public void writeTo(ByteBuffer byteBuffer) {
		writeTo(byteBuffer.asDoubleBuffer());
	}

	public void writeTo(DoubleBuffer doubleBuffer) {
		final int size = dim();
		for (int i = 0; i < size; i++) {
			doubleBuffer.put(get(i));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		final int size = dim();
		for (int i = 0; i < size; i++)
			result = prime * result + Double.hashCode(get(i));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Vec))
			return false;
		Vec<?> other = (Vec<?>) obj;
		final int size = dim();
		if (size != other.dim())
			return false;
		for (int i = 0; i < size; i++)
			if (get(i) != other.get(i))
				return false;
		return true;
	}

	private static class VecIterator implements ListIterator<Double> {

		private Vec<?> vec;

		private int max;

		private int i;

		private int l;

		VecIterator(Vec<?> vec) {
			this.vec = vec;
			max = vec.dim();
			i = 0;
		}

		@Override
		public boolean hasNext() {
			return max > i;
		}

		@Override
		public Double next() {
			l = i;
			return this.vec.get(i++);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(Double f) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasPrevious() {
			return i > 0;
		}

		@Override
		public int nextIndex() {
			return i;
		}

		@Override
		public Double previous() {
			l = --i;
			return this.vec.get(i);
		}

		@Override
		public int previousIndex() {
			return i - 1;
		}

		@Override
		public void set(Double f) {
			this.vec.set(l, f);
		}

	}

	protected T forEach(Func1 f) {
		T ret = _new();
		final int size = dim();
		for (int i = 0; i < size; i++) {
			ret.set(i, f.calc(get(i)));
		}
		return ret;
	}

	protected T forEach(T v, Func2 f) {
		T ret = _new();
		final int size = dim();
		if (Debug.DEBUG) {
			if (size != v.dim())
				throw new IllegalArgumentException("In order to combine two Vectors they have to be of the same dimension!");
		}
		for (int i = 0; i < size; i++) {
			ret.set(i, f.calc(get(i), v.get(i)));
		}
		return ret;
	}

	protected T forEach(double n, Func2 f) {
		T ret = _new();
		final int size = dim();
		for (int i = 0; i < size; i++) {
			ret.set(i, f.calc(get(i), n));
		}
		return ret;
	}

	protected T forEach(T v2, T v3, Func3 f) {
		T ret = _new();
		final int size = dim();
		if (Debug.DEBUG) {
			if (size != v2.dim() || size != v3.dim())
				throw new IllegalArgumentException("In order to combine three Vectors they have to be of the same dimension!");
		}
		for (int i = 0; i < size; i++) {
			ret.set(i, f.calc(get(i), v2.get(i), v3.get(i)));
		}
		return ret;
	}

	protected T forEach(T v, double n, Func3 f) {
		T ret = _new();
		final int size = dim();
		if (Debug.DEBUG) {
			if (size != v.dim())
				throw new IllegalArgumentException("In order to combine two Vectors they have to be of the same dimension!");
		}
		for (int i = 0; i < size; i++) {
			ret.set(i, f.calc(get(i), v.get(i), n));
		}
		return ret;
	}

	protected T forEach(double n1, double n2, Func3 f) {
		T ret = _new();
		final int size = dim();
		for (int i = 0; i < size; i++) {
			ret.set(i, f.calc(get(i), n1, n2));
		}
		return ret;
	}

	public static <V extends Vec<V>> V clone(V x) {
		return x.clone();
	}

	public static <V extends Vec<V>> V add(double x, V y) {
		return y.add2(x);
	}

	public static <V extends Vec<V>> V add(V x, double y) {
		return x.add(y);
	}

	public static <V extends Vec<V>> V add(V x, V y) {
		return x.add(y);
	}

	public static <V extends Vec<V>> V sub(double x, V y) {
		return y.sub2(x);
	}

	public static <V extends Vec<V>> V sub(V x, double y) {
		return x.sub(y);
	}

	public static <V extends Vec<V>> V sub(V x, V y) {
		return x.sub(y);
	}

	public static <V extends Vec<V>> V neg(V x) {
		return x.neg();
	}

	public static <V extends Vec<V>> V mul(double x, V y) {
		return y.mul2(x);
	}

	public static <V extends Vec<V>> V mul(V x, double y) {
		return x.mul(y);
	}

	public static <V extends Vec<V>> V mul(V x, V y) {
		return x.mul(y);
	}

	public static <V extends Vec<V>> V div(double x, V y) {
		return y.div2(x);
	}

	public static <V extends Vec<V>> V div(V x, double y) {
		return x.div(y);
	}

	public static <V extends Vec<V>> V div(V x, V y) {
		return x.div(y);
	}

	public static <V extends Vec<V>> V mod(double x, V y) {
		return y.mod2(x);
	}

	public static <V extends Vec<V>> V mod(V x, double y) {
		return x.mod(y);
	}

	public static <V extends Vec<V>> V mod(V x, V y) {
		return x.mod(y);
	}

	public static <V extends Vec<V>> V pow(double x, V y) {
		return y.pow2(x);
	}

	public static <V extends Vec<V>> V pow(V x, double y) {
		return x.pow(y);
	}

	public static <V extends Vec<V>> V pow(V x, V y) {
		return x.pow(y);
	}

	public static <V extends Vec<V>> V radians(V degrees) {
		return degrees.radians();
	}

	public static <V extends Vec<V>> V degrees(V radians) {
		return radians.degrees();
	}

	public static <V extends Vec<V>> V sin(V x) {
		return x.sin();
	}

	public static <V extends Vec<V>> V cos(V x) {
		return x.cos();
	}

	public static <V extends Vec<V>> V tan(V x) {
		return x.tan();
	}

	public static <V extends Vec<V>> V asin(V x) {
		return x.asin();
	}

	public static <V extends Vec<V>> V acos(V x) {
		return x.acos();
	}

	public static <V extends Vec<V>> V atan(V y, V x) {
		return y.atan2(x);
	}

	public static <V extends Vec<V>> V atan(V y_over_x) {
		return y_over_x.atan();
	}

	public static <V extends Vec<V>> V sinh(V x) {
		return x.sinh();
	}

	public static <V extends Vec<V>> V cosh(V x) {
		return x.cosh();
	}

	public static <V extends Vec<V>> V tanh(V x) {
		return x.tanh();
	}

	public static <V extends Vec<V>> V asinh(V angle) {
		return angle.asinh();
	}

	public static <V extends Vec<V>> V acosh(V angle) {
		return angle.acosh();
	}

	public static <V extends Vec<V>> V atanh(V x) {
		return x.atanh();
	}

	public static <V extends Vec<V>> V exp(V x) {
		return x.exp();
	}

	public static <V extends Vec<V>> V log(V x) {
		return x.log();
	}

	public static <V extends Vec<V>> V exp2(V x) {
		return x.exp2();
	}

	public static <V extends Vec<V>> V log2(V x) {
		return x.log2();
	}

	public static <V extends Vec<V>> V sqrt(V x) {
		return x.sqrt();
	}

	public static <V extends Vec<V>> V inversesqrt(V x) {
		return x.inversesqrt();
	}

	public static <V extends Vec<V>> V abs(V x) {
		return x.abs();
	}

	public static <V extends Vec<V>> V sign(V x) {
		return x.sign();
	}

	public static <V extends Vec<V>> V roundeven(V x) {
		return x.roundeven();
	}

	public static <V extends Vec<V>> V round(V x) {
		return x.round();
	}

	public static <V extends Vec<V>> V trunc(V x) {
		return x.trunc();
	}

	public static <V extends Vec<V>> V floor(V x) {
		return x.floor();
	}

	public static <V extends Vec<V>> V ceil(V x) {
		return x.ceil();
	}

	public static <V extends Vec<V>> V fract(V x) {
		return x.fract();
	}

	public static <V extends Vec<V>> V min(V x, V y) {
		return x.min(y);
	}

	public static <V extends Vec<V>> V min(V x, double y) {
		return x.min(y);
	}

	public static <V extends Vec<V>> V max(V x, V y) {
		return x.max(y);
	}

	public static <V extends Vec<V>> V max(V x, double y) {
		return x.max(y);
	}

	public static <V extends Vec<V>> V clamp(V x, V minVal, V maxVal) {
		return x.clamp(minVal, maxVal);
	}

	public static <V extends Vec<V>> V clamp(V x, double minVal, double maxVal) {
		return x.clamp(minVal, maxVal);
	}

	public static <V extends Vec<V>> V mix(V x, V y, V a) {
		return x.mix(y, a);
	}

	public static <V extends Vec<V>> V mix(V x, V y, double a) {
		return x.mix(y, a);
	}

	public static <V extends Vec<V>> V step(V edge, V x) {
		return x.step(edge);
	}

	public static <V extends Vec<V>> V step(double edge, V x) {
		return x.step(edge);
	}

	public static <V extends Vec<V>> V smoothstep(V edge0, V edge1, V x) {
		return x.smoothstep(edge0, edge1);
	}

	public static <V extends Vec<V>> V smoothstep(double edge0, double edge1, V x) {
		return x.smoothstep(edge0, edge1);
	}

	public static <V extends Vec<V>> double length(V x) {
		return x.length();
	}

	public static <V extends Vec<V>> double distance(V p0, V p1) {
		return p0.distance(p1);
	}

	public static <V extends Vec<V>> boolean distanceSmaller(V p0, V p1, double dist) {
		return p0.distanceSmaller(p1, dist);
	}

	public static <V extends Vec<V>> double dot(V x, V y) {
		return x.dot(y);
	}

	public static Vec3 cross(Vec3 x, Vec3 y) {
		return x.cross(y);
	}

	public static <V extends Vec<V>> V normalize(V x) {
		return x.normalize();
	}

	public static <V extends Vec<V>> V normalizeToLength(V x, double targetLength) {
		return x.normalizeToLength(targetLength);
	}

	public static <V extends Vec<V>> V faceforward(V N, V I, V Nref) {
		if (dot(Nref, I) < 0) {
			return N.clone();
		}
		return N.neg();
	}

	public static <V extends Vec<V>> V reflect(V l, V N) {
		return l.sub(N.mul(2.0f * dot(N, l)));
	}

	public static <V extends Vec<V>> V refract(V l, V N, double eta) {
		double k = 1.0f - eta * eta * (1.0f - dot(N, l) * dot(N, l));
		if (k < 0.0) {
			return N._new();
		} else {
			return l.mul(eta).sub(N.mul(eta * dot(N, l) * (double) Math.sqrt(k)));
		}
	}

	public static <V extends Vec<V>> double pNorm(V v, int p) {
		return v.pNorm(p);
	}

	public static <V extends Vec<V>> double pMul(V v, int p) {
		return v.pMul(p);
	}

	private static DoubleBuffer tmp;
	public static DoubleBuffer asTmpBuffer(Vec<?> v) {
		final int size = v.dim();
		if (tmp == null || tmp.capacity() < size) {
			tmp = ByteBuffer.allocateDirect(size * 8).order(ByteOrder.nativeOrder()).asDoubleBuffer();
		}
		tmp.position(0);
		v.writeTo(tmp);
		tmp.position(0);
		return tmp;
	}

	@SuppressWarnings({"unchecked"})
	private static <V extends Vec<V>> V badCast(Vec<?> v) {
		return (V) v;
	}

	/**
	 * Creates a Vec with the dimension {@code dimension}
	 * 
	 * @param dimension
	 *            The dimension the created Vec shall have
	 * @return Vec of the given dimension
	 */
	public static <V extends Vec<V>> V VecX(int dimension) {
		return VecX(dimension, 0);
	}

	/**
	 * Creates a Vec with the dimension {@code dimension}
	 * 
	 * @param dimension
	 *            The dimension the created Vec shall have
	 * @param pad
	 *            The intial value of every component
	 * @return Vec of given dimension and intial values
	 */
	public static <V extends Vec<V>> V VecX(int dimension, double pad) {
		switch (dimension) {
			case 2 :
				return badCast(Vec.Vec2(pad));
			case 3 :
				return badCast(Vec.Vec3(pad));
			case 4 :
				return badCast(Vec.Vec4(pad));
			default :
				return badCast(new RVecN(dimension, pad));
		}
	}

	public static <V extends Vec<V>> V VecXfixed(int dimension, Vec<?> vec) {
		double[] a = new double[dimension];
		int min = vec.dim();
		if (min > dimension) {
			min = dimension;
		}
		for (int i = 0; i < min; i++) {
			a[i] = vec.get(i);
		}
		return VecX(a);
	}

	/**
	 * Creates a Vec consisting of the first {@code length} elements of
	 * {@code x}
	 * 
	 * @param dimension
	 *            the dimension of the Vec created
	 * @param initial
	 *            the list of coordinates (will be copied) if it's too long it's
	 *            cut else it's padded with 0s
	 */
	public static <V extends Vec<V>> V VecXfixed(int dimension, double... initial) {
		double[] a = new double[dimension];
		int b = initial.length;
		System.arraycopy(initial, 0, a, 0, (dimension <= b) ? dimension : b);
		return VecX(a);
	}

	/**
	 * Creates a Vec consisting of elements of {@code x}
	 * 
	 * @param data
	 *            the list of coordinates (will <b>NOT</b> be copied!!!)
	 */
	public static <V extends Vec<V>> V VecX(double... data) {
		switch (data.length) {
			case 2 :
				return badCast(Vec.Vec2(data[0], data[1]));
			case 3 :
				return badCast(Vec.Vec3(data[0], data[1], data[2]));
			case 4 :
				return badCast(Vec.Vec4(data[0], data[1], data[2], data[3]));
			default :
				return badCast(new RVecN(data));
		}
	}

	public static <V extends Vec<V>> V VecX(Vec<?> v, int... indices) {
		final int size = indices.length;
		final int size1 = v.dim();
		for (int i = 0; i < size; i++) {
			if (indices[i] < 0 || indices[i] >= size1) {
				throw new IllegalArgumentException();
			}
		}
		return VecX_checked(v, indices);
	}

	protected static <V extends Vec<V>> V VecX_checked(Vec<?> v, int[] indices) {
		switch (indices.length) {
			case 2 :
				return badCast(new VVec2(v, indices));
			case 3 :
				return badCast(new VVec3(v, indices));
			case 4 :
				return badCast(new VVec4(v, indices));
			default :
				return badCast(new VVecN(v, indices));
		}
	}

	public static <V extends Vec<V>> V VecX(double[] array, int... indices) {
		final int size = indices.length;
		final int size1 = array.length;
		for (int i = 0; i < size; i++) {
			if (indices[i] < 0 || indices[i] >= size1) {
				throw new IllegalArgumentException();
			}
		}
		return VecX_checked(array, indices);
	}

	protected static <V extends Vec<V>> V VecX_checked(double[] array, int[] indices) {
		switch (indices.length) {
			case 2 :
				return badCast(new AVec2(array, indices));
			case 3 :
				return badCast(new AVec3(array, indices));
			case 4 :
				return badCast(new AVec4(array, indices));
			default :
				return badCast(new AVecN(array, indices));
		}
	}

	public static Vec2 ZeroIfNull(Vec2 vec) {
		if (vec == null)
			return Vec2();
		return vec;
	}

	public static AVec2 Vec2(double[] array, int i1, int i2) {
		return new AVec2(array, i1, i2);
	}

	public static VVec2 Vec2(Vec<?> v, int i1, int i2) {
		return new VVec2(v, i1, i2);
	}

	public static RVec2 Vec2() {
		return Vec2(0);
	}

	public static RVec2 Vec2(double x) {
		return new RVec2(x, x);
	}

	public static RVec2 Vec2(double x, double y) {
		return new RVec2(x, y);
	}

	public static RVec2 Vec2(Vec2 v) {
		return new RVec2(v.x(), v.y());
	}

	public static RVec2 Vec2(Vec3 v) {
		return new RVec2(v.x(), v.y());
	}

	public static RVec2 Vec2(Vec4 v) {
		return new RVec2(v.x(), v.y());
	}

	public static RVec2 Vec2(VecN v) {
		if (Debug.DEBUG) {
			if (v.dim() < 2)
				throw new IllegalArgumentException("You can't clip " + v.dim() + "D-Vectors to dimension 2");
		}
		return new RVec2(v.get(0), v.get(1));
	}

	public static Vec3 ZeroIfNull(Vec3 vec) {
		if (vec == null)
			return Vec3();
		return vec;
	}

	public static AVec3 Vec3(double[] array, int i1, int i2, int i3) {
		return new AVec3(array, i1, i2, i3);
	}

	public static VVec3 Vec3(Vec<?> v, int i1, int i2, int i3) {
		return new VVec3(v, i1, i2, i3);
	}

	public static RVec3 Vec3() {
		return Vec3(0);
	}

	public static RVec3 Vec3(double x) {
		return new RVec3(x, x, x);
	}

	public static RVec3 Vec3(double x, double y, double z) {
		return new RVec3(x, y, z);
	}

	public static RVec3 Vec3(Vec2 v, double z) {
		return new RVec3(v.x(), v.y(), z);
	}

	public static RVec3 Vec3(double x, Vec2 v) {
		return new RVec3(x, v.x(), v.y());
	}

	public static RVec3 Vec3(Vec3 v) {
		return new RVec3(v.x(), v.y(), v.z());
	}

	public static RVec3 Vec3(Vec4 v) {
		return new RVec3(v.x(), v.y(), v.z());
	}

	public static RVec3 Vec3(VecN v) {
		if (Debug.DEBUG) {
			if (v.dim() < 3)
				throw new IllegalArgumentException("You can't clip " + v.dim() + "D-Vectors to dimension 3");
		}
		return new RVec3(v.get(0), v.get(1), v.get(2));
	}

	public static Vec4 ZeroIfNull(Vec4 vec) {
		if (vec == null)
			return Vec4();
		return vec;
	}

	public static AVec4 Vec4(double[] array, int i1, int i2, int i3, int i4) {
		return new AVec4(array, i1, i2, i3, i4);
	}

	public static VVec4 Vec4(Vec<?> v, int i1, int i2, int i3, int i4) {
		return new VVec4(v, i1, i2, i3, i4);
	}

	public static Vec4 Vec4() {
		return Vec4(0);
	}

	public static Vec4 Vec4(double x) {
		return new RVec4(x, x, x, x);
	}

	public static Vec4 Vec4(double x, double y, double z, double w) {
		return new RVec4(x, y, z, w);
	}

	public static Vec4 Vec4(Vec2 v, double z, double w) {
		return new RVec4(v.x(), v.y(), z, w);
	}

	public static Vec4 Vec4(double x, Vec2 v, double w) {
		return new RVec4(x, v.x(), v.y(), w);
	}

	public static Vec4 Vec4(double x, double y, Vec2 v) {
		return new RVec4(x, y, v.x(), v.y());
	}

	public static Vec4 Vec4(Vec2 v1, Vec2 v2) {
		return new RVec4(v1.x(), v1.y(), v2.x(), v2.y());
	}

	public static Vec4 Vec4(Vec3 v, double w) {
		return new RVec4(v.x(), v.y(), v.z(), w);
	}

	public static Vec4 Vec4(double x, Vec3 v) {
		return new RVec4(x, v.x(), v.y(), v.z());
	}

	public static Vec4 Vec4(Vec4 v) {
		return new RVec4(v.x(), v.y(), v.z(), v.w());
	}

	public static RVec4 Vec4(VecN v) {
		if (Debug.DEBUG) {
			if (v.dim() < 4)
				throw new IllegalArgumentException("You can't clip " + v.dim() + "D-Vectors to dimension 4");
		}
		return new RVec4(v.get(0), v.get(1), v.get(2), v.get(3));
	}

	public static <V extends Vec<V>> V ZeroIfNull(V vec, int dim) {
		if (vec == null)
			return VecX(dim);
		if (Debug.DEBUG) {
			if (vec.dim() != dim)
				throw new IllegalArgumentException();
		}
		return vec;
	}

	public static Vec2 mixFromHighestComponents(Vec2... vecs) {
		Vec2 ret = Vec2();
		for (int i = 0; i < vecs.length; i++) {
			Vec2 v2 = vecs[i];
			if (v2 == null)
				continue;
			double v = ret.x();
			double v3 = v2.x();
			if (Math.abs(v3) > Math.abs(v)) {
				ret.x(v3);
			}
			v = ret.y();
			v3 = v2.y();
			if (Math.abs(v3) > Math.abs(v)) {
				ret.y(v3);
			}
		}
		return ret;
	}

	public static Vec3 mixFromHighestComponents(Vec3... vecs) {
		Vec3 ret = Vec3();
		for (int i = 0; i < vecs.length; i++) {
			Vec3 v2 = vecs[i];
			if (v2 == null)
				continue;
			double v = ret.x();
			double v3 = v2.x();
			if (Math.abs(v3) > Math.abs(v)) {
				ret.x(v3);
			}
			v = ret.y();
			v3 = v2.y();
			if (Math.abs(v3) > Math.abs(v)) {
				ret.y(v3);
			}
			v = ret.z();
			v3 = v2.z();
			if (Math.abs(v3) > Math.abs(v)) {
				ret.z(v3);
			}
		}
		return ret;
	}

	public static Vec4 mixFromHighestComponents(Vec4... vecs) {
		Vec4 ret = Vec4();
		for (int i = 0; i < vecs.length; i++) {
			Vec4 v2 = vecs[i];
			if (v2 == null)
				continue;
			double v = ret.x();
			double v3 = v2.x();
			if (Math.abs(v3) > Math.abs(v)) {
				ret.x(v3);
			}
			v = ret.y();
			v3 = v2.y();
			if (Math.abs(v3) > Math.abs(v)) {
				ret.y(v3);
			}
			v = ret.z();
			v3 = v2.z();
			if (Math.abs(v3) > Math.abs(v)) {
				ret.z(v3);
			}
			v = ret.w();
			v3 = v2.w();
			if (Math.abs(v3) > Math.abs(v)) {
				ret.w(v3);
			}
		}
		return ret;
	}

	public static <V extends Vec<V>> V mixFromHighestComponents(int dim, V... vecs) {
		V ret = VecX(dim);
		for (int i = 0; i < vecs.length; i++) {
			V v2 = vecs[i];
			if (v2 == null)
				continue;
			if (Debug.DEBUG) {
				if (v2.dim() != dim)
					throw new IllegalArgumentException();
			}
			for (int j = 0; j < dim; j++) {
				double v = ret.get(j);
				double v3 = v2.get(j);
				if (Math.abs(v3) > Math.abs(v)) {
					ret.set(j, v3);
				}
			}
		}
		return ret;
	}

	public static <V extends Vec<V>> V VecX(Object... o) {
		List<Number> list = new ArrayList<Number>();
		fill(list, o);
		int size = list.size();
		double[] a = new double[size];
		for(int i=0; i<size; i++){
			a[i] = list.get(i).doubleValue();
		}
		return VecX(a);
	}

	private static void fill(List<Number> f, Object[] obj) {
		for (Object o : obj) {
			if (o instanceof Number) {
				f.add((Number) o);
			} else if (o instanceof Vec<?>) {
				Vec<?> v = (Vec<?>) o;
				final int size = v.dim();
				for (int j = 0; j < size; j++) {
					f.add(v.get(j));
				}
			}
		}
	}

}
