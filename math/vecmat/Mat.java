package math.vecmat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import math.vecmat.Operators.Func1;
import math.vecmat.Operators.Func2;
import math.vecmat.Operators.Func3;

public abstract class Mat<T extends Mat<T, T2>, T2 extends Mat<?, ?>> implements Cloneable {

	public abstract int n();

	public abstract int m();

	public abstract double get(int m, int n);

	public abstract void set(int m, int n, double v);

	protected abstract Vec<?> getRow(int m);

	protected abstract Vec<?> getColumn(int n);

	public <V extends Vec<V>> V row(int m) {
		if (m < 0 || m >= m())
			throw new IndexOutOfBoundsException();
		return badCast(getRow(m));
	}

	public <V extends Vec<V>> V column(int n) {
		if (n < 0 || n >= n())
			throw new IndexOutOfBoundsException();
		return badCast(getColumn(n));
	}

	public void row(int m, Vec<?> v) {
		final int n = n();
		if (m < 0 || m >= m())
			throw new IndexOutOfBoundsException();
		if (n != v.dim()) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < n; i++) {
			set(m, i, v.get(i));
		}
	}

	public void column(int n, Vec<?> v) {
		final int m = m();
		if (n < 0 || n >= n())
			throw new IndexOutOfBoundsException();
		if (m != v.dim()) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < m; i++) {
			set(i, n, v.get(i));
		}
	}

	public void set(T v) {
		final int m = m();
		final int n = n();
		if (m != v.m() || n != v.n())
			throw new IllegalArgumentException();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				set(i, j, v.get(i, j));
			}
		}
	}

	public T add(double n) {
		return forEach(n, Operators.ADD);
	}

	public T add(T v) {
		return forEach(v, Operators.ADD);
	}

	public T add2(double n) {
		return forEach(n, Operators.ADD);
	}

	public T sub(double n) {
		return forEach(n, Operators.SUB);
	}

	public T sub(T v) {
		return forEach(v, Operators.SUB);
	}

	public T sub2(double n) {
		return forEach(n, Operators.SUB2);
	}

	public T neg() {
		return forEach(Operators.NEG);
	}

	public T mul(double n) {
		return forEach(n, Operators.MUL);
	}

	public abstract T2 mul(T v);

	public <M extends Mat<M, ?>> M mulU(Mat<?, ?> v) {
		final int m = m();
		final int n = v.n();
		final int size = v.m();
		if (size != n())
			throw new IllegalArgumentException();
		Mat<?, ?> mat = MatX(m, n);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < size; k++) {
					mat.set(i, j, mat.get(i, j) + get(i, k) * v.get(k, j));
				}
			}
		}
		return badCast(mat);
	}

	protected abstract Vec<?> mulV(Vec<?> v);

	public <V extends Vec<V>> V mul(Vec<?> v) {
		if (v.dim() != n())
			throw new IllegalArgumentException();
		return badCast(mulV(v));
	}

	public T mul2(double n) {
		return forEach(n, Operators.MUL);
	}

	public T matrixCompMul(T y) {
		return forEach(y, Operators.MUL);
	}

	public T div(double y) {
		return forEach(y, Operators.DIV);
	}

	public T matrixCompDiv(T y) {
		return forEach(y, Operators.DIV);
	}

	public T mod(double y) {
		return forEach(y, Operators.MOD);
	}

	public T matrixCompMod(T y) {
		return forEach(y, Operators.MOD);
	}

	public abstract T pow(int y);

	public T matrixCompPow(double y) {
		return forEach(y, Operators.POW);
	}
	
	public T matrixCompPow(T y) {
		return forEach(y, Operators.POW);
	}

	public abstract T transpose();

	public abstract T invert();

	public abstract double det();

	public abstract T adjunkte();

	@Override
	public abstract T clone();

	protected abstract T _new();

	protected T forEach(Func1 f) {
		T ret = _new();
		final int m = m();
		final int n = n();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ret.set(j, i, f.calc(get(j, i)));
			}
		}
		return ret;
	}

	protected T forEach(T v, Func2 f) {
		T ret = _new();
		final int m = m();
		final int n = n();
		if(Debug.DEBUG){
			if(m!=v.m() ||n!=v.n())
				throw new IllegalArgumentException("Incompatible matrix sizes!");
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ret.set(j, i, f.calc(get(j, i), v.get(j, i)));
			}
		}
		return ret;
	}

	protected T forEach(double n1, Func2 f) {
		T ret = _new();
		final int m = m();
		final int n = n();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ret.set(j, i, f.calc(get(j, i), n1));
			}
		}
		return ret;
	}

	protected T forEach(T v2, T v3, Func3 f) {
		T ret = _new();
		final int m = m();
		final int n = n();
		if(Debug.DEBUG){
			if(m!=v2.m() ||n!=v2.n()||m!=v3.m() ||n!=v3.n())
				throw new IllegalArgumentException("Incompatible matrix sizes!");
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ret.set(j, i, f.calc(get(j, i), v2.get(j, i), v3.get(j, i)));
			}
		}
		return ret;
	}

	protected T forEach(T v, double n1, Func3 f) {
		T ret = _new();
		final int m = m();
		final int n = n();
		if(Debug.DEBUG){
			if(m!=v.m() ||n!=v.n())
				throw new IllegalArgumentException("Incompatible matrix sizes!");
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ret.set(j, i, f.calc(get(j, i), v.get(j, i), n1));
			}
		}
		return ret;
	}

	protected T forEach(double n1, double n2, Func3 f) {
		T ret = _new();
		final int m = m();
		final int n = n();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ret.set(j, i, f.calc(get(j, i), n1, n2));
			}
		}
		return ret;
	}

	@Override
	public String toString() {
		final int m = m();
		final int n = n();
		String[] lines = new String[m];
		String[] num = new String[m];
		int max;
		int ll = -1;
		for (int j = 0; j < n; j++) {
			max = 0;
			for (int i = 0; i < m; i++) {
				String nu = Double.toString(get(i, j));
				int l = nu.length();
				if (l > max)
					max = l;
				num[i] = nu;
			}
			if (j == 0) {
				for (int i = 0; i < m; i++) {
					String f = " ";
					for (int k = num[i].length(); k < max; k++) {
						f += " ";
					}
					lines[i] = f + num[i];
				}
			} else {
				for (int i = 0; i < m; i++) {
					String f = ", ";
					for (int k = num[i].length(); k < max; k++) {
						f += " ";
					}
					lines[i] += f + num[i];
				}
			}
			ll += 2 + max;
		}
		if (ll == -1) {
			return "+- -+\n|   |\n+- -+";
		}
		String top = "+-";
		for (int i = 1; i < ll; i++) {
			top += " ";
		}
		top += "-+";
		String ret = top + "\n";
		for (int i = 0; i < m; i++) {
			ret += "|" + lines[i] + " |\n";
		}
		return ret + top;
	}

	public double[] toArray(){
		final int m = m();
		final int n = n();
		double[] array = new double[m*n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				array[i*n+j] = get(i, j);
			}
		}
		return array;
	}
	
	public double[] toArrayGL(){
		final int m = m();
		final int n = n();
		double[] array = new double[m*n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				array[j*m+i] = get(i, j);
			}
		}
		return array;
	}
	
	public void writeTo(ByteBuffer byteBuffer) {
		writeTo(byteBuffer.asDoubleBuffer());
	}

	public void writeTo(DoubleBuffer doubleBuffer) {
		final int m = m();
		final int n = n();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				doubleBuffer.put(get(i, j));
			}
		}
	}
	
	public void writeToGL(ByteBuffer byteBuffer) {
		writeTo(byteBuffer.asDoubleBuffer());
	}

	public void writeToGL(DoubleBuffer doubleBuffer) {
		final int m = m();
		final int n = n();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				doubleBuffer.put(get(j, i));
			}
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		final int m = m();
		final int n = n();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				result = prime * result + Double.hashCode(get(i, j));
			}
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Mat))
			return false;
		Mat<?, ?> other = (Mat<?, ?>) obj;
		final int m = m();
		if (m != other.m())
			return false;
		final int n = n();
		if (m != other.n())
			return false;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (get(i, j) != other.get(i, j))
					return false;
			}
		}
		return true;
	}

	@SuppressWarnings({"unchecked"})
	private static <V extends Vec<V>> V badCast(Vec<?> v) {
		return (V) v;
	}

	@SuppressWarnings({"unchecked"})
	private static <M> M badCast(Mat<?, ?> m) {
		return (M) m;
	}

	public static <M extends Mat<M, ?>> M clone(M x) {
		return x.clone();
	}

	public static <M extends Mat<M, ?>> M add(double x, M y) {
		return y.add2(x);
	}

	public static <M extends Mat<M, ?>> M add(M x, double y) {
		return x.add(y);
	}

	public static <M extends Mat<M, ?>> M add(M x, M y) {
		return x.add(y);
	}

	public static <M extends Mat<M, ?>> M sub(double x, M y) {
		return y.sub2(x);
	}

	public static <M extends Mat<M, ?>> M sub(M x, double y) {
		return x.sub(y);
	}

	public static <M extends Mat<M, ?>> M sub(M x, M y) {
		return x.sub(y);
	}

	public static <M extends Mat<M, ?>> M neg(M x) {
		return x.neg();
	}

	public static <M extends Mat<M, ?>> M mul(double x, M y) {
		return y.mul2(x);
	}

	public static <M extends Mat<M, ?>> M mul(M x, double y) {
		return x.mul(y);
	}

	public static <M extends Mat<M, M2>, M2 extends Mat<?, ?>> M2 mul(M x, M y) {
		return x.mul(y);
	}

	public static Vec2 mul(Mat2 x, Vec2 y) {
		return x.mul(y);
	}

	public static Vec3 mul(Mat3 x, Vec3 y) {
		return x.mul(y);
	}

	public static Vec4 mul(Mat4 x, Vec4 y) {
		return x.mul(y);
	}

	public static VecN mul(MatN x, VecN y) {
		return x.mul(y);
	}

	public static <V extends Vec<V>> V mul(MatMN x, Vec<?> y) {
		return x.mul(y);
	}

	public static <M extends Mat<M, ?>> M matrixCompMul(M x, M y) {
		return x.matrixCompMul(y);
	}

	public static <M extends Mat<M, ?>> M div(M x, double y) {
		return x.div(y);
	}

	public static <M extends Mat<M, ?>> M matrixCompDiv(M x, M y) {
		return x.matrixCompDiv(y);
	}

	public static <M extends Mat<M, ?>> M mod(M x, double y) {
		return x.mod(y);
	}

	public static <M extends Mat<M, ?>> M matrixCompMod(M x, M y) {
		return x.matrixCompMod(y);
	}

	public static <M extends Mat<M, ?>> M pow(M x, int y) {
		return x.pow(y);
	}
	
	public static <M extends Mat<M, ?>> M matrixCompPow(M x, double y) {
		return x.matrixCompPow(y);
	}

	public static <M extends Mat<M, ?>> M matrixCompPow(M x, M y) {
		return x.matrixCompPow(y);
	}

	public static <M extends Mat<M, ?>> M transpose(M m) {
		return m.transpose();
	}

	public static <M extends Mat<M, ?>> M invert(M m) {
		return m.invert();
	}

	public static <M extends Mat<M, ?>> double det(M m) {
		return m.det();
	}

	public static <M extends Mat<M, ?>> M adjunkte(M m) {
		return m.adjunkte();
	}

	private static DoubleBuffer tmp;
	public static DoubleBuffer asTmpBuffer(Mat<?, ?> m) {
		final int size = m.n() * m.m();
		if (tmp == null || tmp.capacity() < size) {
			tmp = ByteBuffer.allocateDirect(size * 8).order(ByteOrder.nativeOrder()).asDoubleBuffer();
		}
		tmp.position(0);
		m.writeTo(tmp);
		tmp.position(0);
		return tmp;
	}
	
	public static DoubleBuffer asTmpBufferGL(Mat<?, ?> m) {
		final int size = m.n() * m.m();
		if (tmp == null || tmp.capacity() < size) {
			tmp = ByteBuffer.allocateDirect(size * 8).order(ByteOrder.nativeOrder()).asDoubleBuffer();
		}
		tmp.position(0);
		m.writeToGL(tmp);
		tmp.position(0);
		return tmp;
	}
	
	public static Mat2 outerProduct(Vec2 c, Vec2 r) {
		final double cx = c.x();
		final double cy = c.y();
		final double rx = r.x();
		final double ry = r.y();
		double[] mat = new double[4];
		mat[0] = cx * rx;
		mat[1] = cy * rx;
		mat[2] = cx * ry;
		mat[3] = cy * ry;
		return new RMat2(mat);
	}

	public static Mat3 outerProduct(Vec3 c, Vec3 r) {
		final double cx = c.x();
		final double cy = c.y();
		final double cz = c.z();
		final double rx = r.x();
		final double ry = r.y();
		final double rz = r.z();
		double[] mat = new double[9];
		mat[0] = cx * rx;
		mat[1] = cy * rx;
		mat[2] = cz * rx;
		mat[3] = cx * ry;
		mat[4] = cy * ry;
		mat[5] = cz * ry;
		mat[6] = cx * rz;
		mat[7] = cy * rz;
		mat[8] = cz * rz;
		return new RMat3(mat);
	}

	public static Mat4 outerProduct(Vec4 c, Vec4 r) {
		final double cx = c.x();
		final double cy = c.y();
		final double cz = c.z();
		final double cw = c.w();
		final double rx = r.x();
		final double ry = r.y();
		final double rz = r.z();
		final double rw = r.w();
		double[] mat = new double[16];
		mat[0] = cx * rx;
		mat[1] = cy * rx;
		mat[2] = cz * rx;
		mat[3] = cw * rx;
		mat[4] = cx * ry;
		mat[5] = cy * ry;
		mat[6] = cz * ry;
		mat[7] = cw * ry;
		mat[8] = cx * rz;
		mat[9] = cy * rz;
		mat[10] = cz * rz;
		mat[11] = cw * rz;
		mat[12] = cx * rw;
		mat[13] = cy * rw;
		mat[14] = cz * rw;
		mat[15] = cw * rw;
		return new RMat4(mat);
	}

	public static MatN outerProduct(VecN c, VecN r) {
		final int size = c.dim();
		if (size != r.dim())
			throw new IllegalArgumentException();
		double[] mat = new double[size * size];
		int k = 0;
		for (int i = 0; i < size; i++) {
			double rg = r.get(i);
			for (int j = 0; j < size; j++) {
				mat[k++] = c.get(j) * rg;
			}
		}
		return new RMatN(size, mat);
	}

	public static Mat2 Mat2() {
		return new RMat2(new double[]{1, 0, 0, 1});
	}

	public static Mat2 Mat2(double scale) {
		return new RMat2(new double[]{scale, 0, 0, scale});
	}

	public static Mat2 Mat2F(double fill) {
		return new RMat2(new double[]{fill, fill, fill, fill});
	}

	public static Mat2 Mat2(double m00, double m10, double m01, double m11) {
		return new RMat2(new double[]{m00, m10, m01, m11});
	}

	public static Mat2 Mat2T(double m00, double m01, double m10, double m11) {
		return new RMat2(new double[]{m00, m10, m01, m11});
	}

	public static Mat2 Mat2(Object... o) {
		double[] f = new double[4];
		fill(f, o);
		return new RMat2(f);
	}

	public static Mat2 Mat2T(Object... o) {
		double[] f = new double[4];
		fill(f, o);
		return new RMat2(new double[]{f[0], f[2], f[1], f[3]});
	}

	public static Mat2 Mat2(Mat2 m) {
		return m.clone();
	}

	public static Mat3 Mat3() {
		return new RMat3(new double[]{1, 0, 0, 0, 1, 0, 0, 0, 1});
	}

	public static Mat3 Mat3(double scale) {
		return new RMat3(new double[]{scale, 0, 0, 0, scale, 0, 0, 0, scale});
	}

	public static Mat3 Mat3F(double fill) {
		return new RMat3(new double[]{fill, fill, fill, fill, fill, fill, fill, fill, fill});
	}

	public static Mat3 Mat3(double m00, double m10, double m20, double m01, double m11, double m21, double m02, double m12, double m22) {
		return new RMat3(new double[]{m00, m10, m20, m01, m11, m21, m02, m12, m22});
	}

	public static Mat3 Mat3T(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
		return new RMat3(new double[]{m00, m10, m20, m01, m11, m21, m02, m12, m22});
	}

	public static Mat3 Mat3(Object... o) {
		double[] f = new double[9];
		fill(f, o);
		return new RMat3(f);
	}

	public static Mat3 Mat3T(Object... o) {
		double[] f = new double[9];
		fill(f, o);
		return new RMat3(new double[]{f[0], f[3], f[6], f[1], f[4], f[7], f[2], f[5], f[8]});
	}

	public static Mat3 Mat3(Mat3 m) {
		return m.clone();
	}

	public static Mat4 Mat4() {
		return new RMat4(new double[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1});
	}

	public static Mat4 Mat4(double scale) {
		return new RMat4(new double[]{scale, 0, 0, 0, 0, scale, 0, 0, 0, 0, scale, 0, 0, 0, 0, scale});
	}

	public static Mat4 Mat4F(double fill) {
		return new RMat4(new double[]{fill, fill, fill, fill, fill, fill, fill, fill, fill, fill, fill, fill, fill, fill, fill, fill});
	}

	public static Mat4 Mat4(double m00, double m10, double m20, double m30, double m01, double m11, double m21, double m31, double m02, double m12, double m22, double m32, double m03, double m13, double m23, double m33) {
		return new RMat4(new double[]{m00, m10, m20, m30, m01, m11, m21, m31, m02, m12, m22, m32, m03, m13, m23, m33});
	}

	public static Mat4 Mat4T(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, double m30, double m31, double m32, double m33) {
		return new RMat4(new double[]{m00, m10, m20, m30, m01, m11, m21, m31, m02, m12, m22, m32, m03, m13, m23, m33});
	}

	public static Mat4 Mat4(Object... o) {
		double[] f = new double[16];
		fill(f, o);
		return new RMat4(f);
	}

	public static Mat4 Mat4T(Object... o) {
		double[] f = new double[16];
		fill(f, o);
		return new RMat4(new double[]{f[0], f[4], f[8], f[12], f[1], f[5], f[9], f[13], f[2], f[6], f[10], f[14], f[3], f[7], f[11], f[15]});
	}

	public static Mat4 Mat4(Mat4 m) {
		return m.clone();
	}

	public static <M extends Mat<M, M>> M MatX(int size) {
		switch (size) {
			case 2 :
				return badCast(Mat2());
			case 3 :
				return badCast(Mat3());
			case 4 :
				return badCast(Mat4());
		}
		double[] mat = new double[size * size];
		int s1 = size + 1;
		for (int i = 0; i < size; i++) {
			mat[i * s1] = 1;
		}
		return badCast(new RMatN(size, mat));
	}

	public static <M extends Mat<M, ?>> M MatX(int size, double scale) {
		switch (size) {
			case 2 :
				return badCast(Mat2(scale));
			case 3 :
				return badCast(Mat3(scale));
			case 4 :
				return badCast(Mat4(scale));
		}
		double[] mat = new double[size * size];
		int s1 = size + 1;
		for (int i = 0; i < size; i++) {
			mat[i * s1] = scale;
		}
		return badCast(new RMatN(size, mat));
	}

	public static <M extends Mat<M, ?>> M MatXF(int size, double fill) {
		switch (size) {
			case 2 :
				return badCast(Mat2F(fill));
			case 3 :
				return badCast(Mat3F(fill));
			case 4 :
				return badCast(Mat4F(fill));
		}
		double[] mat = new double[size * size];
		Arrays.fill(mat, fill);
		return badCast(new RMatN(size, mat));
	}

	public static <M extends Mat<M, ?>> M MatX(double... mat) {
		final int l = mat.length;
		final int size = (int) (Math.sqrt(l) + 0.5);
		if (size * size != l)
			throw new IllegalArgumentException();
		switch (size) {
			case 2 :
				return badCast(new RMat2(mat));
			case 3 :
				return badCast(new RMat3(mat));
			case 4 :
				return badCast(new RMat4(mat));
		}
		return badCast(new RMatN(size, mat));
	}

	public static <M extends Mat<M, ?>> M MatXT(double... mat) {
		final int l = mat.length;
		final int size = (int) (Math.sqrt(l) + 0.5);
		if (size * size != l)
			throw new IllegalArgumentException();
		double[] t = new double[l];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				t[i + j * size] = mat[i * size + j];
			}
		}
		switch (size) {
			case 2 :
				return badCast(new RMat2(t));
			case 3 :
				return badCast(new RMat3(t));
			case 4 :
				return badCast(new RMat4(t));
		}
		return badCast(new RMatN(size, t));
	}

	public static <M extends Mat<M, ?>> M MatX(Object... o) {
		List<Number> ll = new ArrayList<Number>();
		fill(ll, o);
		final int l = ll.size();
		final int size = (int) (Math.sqrt(l) + 0.5);
		if (size * size != l)
			throw new IllegalArgumentException();
		double[] mat = new double[l];
		for (int i = 0; i < l; i++) {
			mat[i] = ll.get(i).doubleValue();
		}
		switch (size) {
			case 2 :
				return badCast(new RMat2(mat));
			case 3 :
				return badCast(new RMat3(mat));
			case 4 :
				return badCast(new RMat4(mat));
		}
		return badCast(new RMatN(size, mat));
	}

	public static <M extends Mat<M, ?>> M MatXT(Object... o) {
		List<Number> ll = new ArrayList<Number>();
		fill(ll, o);
		final int l = ll.size();
		final int size = (int) (Math.sqrt(l) + 0.5);
		if (size * size != l)
			throw new IllegalArgumentException();
		double[] mat = new double[l];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				mat[i + j * size] = ll.get(i * size + j).doubleValue();
			}
		}
		switch (size) {
			case 2 :
				return badCast(new RMat2(mat));
			case 3 :
				return badCast(new RMat3(mat));
			case 4 :
				return badCast(new RMat4(mat));
		}
		return badCast(new RMatN(size, mat));
	}

	public static <M extends Mat<M, ?>> M MatX(M m) {
		return m.clone();
	}

	public static <M extends Mat<M, ?>> M MatX(int m, int n) {
		if (m == n) {
			return badCast(MatX(m));
		}
		return badCast(new RMatMN(m, n));
	}

	public static <M extends Mat<M, ?>> M MatX(int m, int n, double... mat) {
		if (m == n) {
			return MatX(m, mat);
		}
		return badCast(new RMatMN(m, n, mat));
	}

	private static void fill(double[] f, Object[] obj) {
		int i = 0;
		for (Object o : obj) {
			if (o instanceof Number) {
				f[i++] = ((Number) o).doubleValue();
			} else if (o instanceof Vec<?>) {
				Vec<?> v = (Vec<?>) o;
				final int size = v.dim();
				for (int j = 0; j < size; j++) {
					f[i++] = v.get(j);
				}
			} else {
				throw new IllegalArgumentException();
			}
		}
		if (i != f.length)
			throw new IllegalArgumentException();
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

	public static Mat4 createTranslationMarix(Vec3 v) {
		return createTranslationMarix(v.x(), v.y(), v.z());
	}

	public static Mat4 createTranslationMarix(double x, double y, double z) {
		RMat4 m = new RMat4();
		m.mat[0] = 1;
		m.mat[5] = 1;
		m.mat[10] = 1;
		m.mat[12] = x;
		m.mat[13] = y;
		m.mat[14] = z;
		m.mat[15] = 1;
		return m;
	}

	public static Mat4 createRotationMarix(double a, double x, double y, double z) {
		return createRotationMarixRad((double) Math.toRadians(a), x, y, z);
	}

	public static Mat4 createRotationMarixRad(double a, double x, double y, double z) {
		RMat4 m = new RMat4();
		double c = (double) Math.cos(a);
		double s = (double) Math.sin(a);
		double c1 = 1 - c;
		double l = x * x + y * y + z * z;
		if (l != 1) {
			l = 1 / Math.sqrt(l);
			x *= l;
			y *= l;
			z *= l;
		}
		m.mat[0] = x * x * c1 + c;
		m.mat[4] = x * y * c1 - z * s;
		m.mat[8] = x * z * c1 + y * s;

		m.mat[1] = y * x * c1 + z * s;
		m.mat[5] = y * y * c1 + c;
		m.mat[9] = y * z * c1 - x * s;

		m.mat[2] = x * z * c1 - y * s;
		m.mat[6] = y * z * c1 + x * s;
		m.mat[10] = z * z * c1 + c;
		m.mat[15] = 1;
		return m;
	}
	
	public static Mat3 createRotationMarix3(double a, double x, double y, double z) {
		return createRotationMarixRad3((double) Math.toRadians(a), x, y, z);
	}

	public static Mat3 createRotationMarixRad3(double a, double x, double y, double z) {
		RMat3 m = new RMat3();
		double c = (double) Math.cos(a);
		double s = (double) Math.sin(a);
		double c1 = 1 - c;
		double l = x * x + y * y + z * z;
		if (l != 1) {
			l = 1 / Math.sqrt(l);
			x *= l;
			y *= l;
			z *= l;
		}
		m.mat[0] = x * x * c1 + c;
		m.mat[3] = x * y * c1 - z * s;
		m.mat[6] = x * z * c1 + y * s;

		m.mat[1] = y * x * c1 + z * s;
		m.mat[4] = y * y * c1 + c;
		m.mat[7] = y * z * c1 - x * s;

		m.mat[2] = x * z * c1 - y * s;
		m.mat[5] = y * z * c1 + x * s;
		m.mat[8] = z * z * c1 + c;
		return m;
	}

	public static Mat4 createScaleMarix(Vec3 v) {
		return createScaleMarix(v.x(), v.y(), v.z());
	}

	public static Mat4 createScaleMarix(double x, double y, double z) {
		RMat4 m = new RMat4();
		m.mat[0] = x;
		m.mat[5] = y;
		m.mat[10] = z;
		m.mat[15] = 1;
		return m;
	}

	public static Mat4 createEulerRotationMarix(Vec3 v) {
		return createEulerRotationMarix(v.x(), v.y(), v.z());
	}

	public static Mat4 createEulerRotationMarix(double x, double y, double z) {
		return createEulerRotationMarixRad((double) Math.toRadians(x), (double) Math.toRadians(y), (double) Math.toRadians(z));
	}

	public static Mat4 createEulerRotationMarixRad(Vec3 v) {
		return createEulerRotationMarixRad(v.x(), v.y(), v.z());
	}

	public static Mat4 createEulerRotationMarixRad(double x, double y, double z) {
		RMat4 m = new RMat4();
		double cx = (double) Math.cos(x);
		double sx = (double) Math.sin(x);
		double cy = (double) Math.cos(y);
		double sy = (double) Math.sin(y);
		double cz = (double) Math.cos(z);
		double sz = (double) Math.sin(z);
		double cxsy = cx * sy;
		double sxsy = sx * sy;
		m.mat[0] = cy * cz;
		m.mat[1] = -cy * sz;
		m.mat[2] = sy;
		m.mat[4] = cxsy * cz + cx * sz;
		m.mat[5] = -cxsy * sz + cx * cz;
		m.mat[6] = -sx * cy;
		m.mat[8] = -sxsy * cz + sx * sz;
		m.mat[9] = sxsy * sz + sx * cz;
		m.mat[10] = cx * cy;
		m.mat[15] = 1;
		return m;
	}

	public static Mat4 createLookAtMatrix(double eyeX, double eyeY, double eyeZ, double centerX, double centerY, double centerZ, double upX, double upY, double upZ) {
		RMat4 m = new RMat4();

		double fx = centerX - eyeX;
		double fy = centerY - eyeY;
		double fz = centerZ - eyeZ;

		double l = fx * fx + fy * fy + fz * fz;
		if (l != 1) {
			l = 1 / Math.sqrt(l);
			fx *= l;
			fy *= l;
			fz *= l;
		}

		double sx = fy * upZ - fz * upY;
		double sy = fz * upX - fx * upZ;
		double sz = fx * upY - fy * upX;

		l = sx * sx + sy * sy + sz * sz;
		if (l != 1) {
			l = 1 / Math.sqrt(l);
			sx *= l;
			sy *= l;
			sz *= l;
		}

		double ux = sy * fz - sz * fy;
		double uy = sz * fx - sx * fz;
		double uz = sx * fy - sy * fx;
		m.mat[0] = sx;
		m.mat[1] = ux;
		m.mat[2] = -fx;
		m.mat[4] = sy;
		m.mat[5] = uy;
		m.mat[6] = -fy;
		m.mat[8] = sz;
		m.mat[9] = uz;
		m.mat[10] = -fz;
		m.mat[15] = 1;
		return m.translate(-eyeX, -eyeY, -eyeZ);
	}

	public static Mat4 createPerspectiveMarix(double fovy, double aspect, double zNear, double zFar) {
		RMat4 m = new RMat4();
		double f = (double) (1 / Math.tan(Math.toRadians(fovy)));
		m.mat[0] = f / aspect;
		m.mat[5] = f;
		m.mat[10] = (zNear + zFar) / (zNear - zFar);
		m.mat[11] = -1;
		m.mat[14] = 2 * zNear * zFar / (zNear - zFar);
		return m;
	}

	public static Mat4 createOrthoMarix(double left, double right, double bottom, double top, double near, double far) {
		RMat4 m = new RMat4();
		m.mat[0] = 2 / (right - left);
		m.mat[5] = 2 / (top - bottom);
		m.mat[10] = 2 / (far - near);
		m.mat[12] = -(right + left) / (right - left);
		m.mat[13] = -(top + bottom) / (top - bottom);
		m.mat[14] = -(far + near) / (far - near);
		m.mat[15] = 1;
		return m;
	}

	public static Mat4 createFrustumMarix(double left, double right, double bottom, double top, double near, double far) {
		RMat4 m = new RMat4();
		m.mat[0] = 2 * near / (right - left);
		m.mat[5] = 2 * near / (top - bottom);
		m.mat[8] = (right + left) / (right - left);
		m.mat[9] = (top + bottom) / (top - bottom);
		m.mat[10] = -(near - far) / (near - far);
		m.mat[11] = -1;
		m.mat[14] = -2 * near * far / (near - far);
		return m;
	}

}
