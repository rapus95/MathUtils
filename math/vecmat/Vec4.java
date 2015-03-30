package math.vecmat;

import math.vecmat.Operators.Func1;
import math.vecmat.Operators.Func2;
import math.vecmat.Operators.Func3;

public abstract class Vec4 extends Vec<Vec4> {

	public abstract double x();

	public abstract double y();

	public abstract double z();

	public abstract double w();

	public abstract void x(double x);

	public abstract void y(double y);

	public abstract void z(double z);

	public abstract void w(double w);

	@Override
	public int dim() {
		return 4;
	}

	@Override
	public double get(int i) {
		switch (i) {
		case 0:
			return x();
		case 1:
			return y();
		case 2:
			return z();
		case 3:
			return w();
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void set(int i, double v) {
		switch (i) {
		case 0:
			x(v);
			break;
		case 1:
			y(v);
			break;
		case 2:
			z(v);
			break;
		case 3:
			w(v);
			break;
		default:
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public double get(char c) {
		switch (c) {
		case 'x':
		case 'r':
		case 's':
			return x();
		case 'y':
		case 'g':
		case 't':
			return y();
		case 'z':
		case 'b':
		case 'p':
			return z();
		case 'w':
		case 'a':
		case 'q':
			return w();
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void set(char c, double v) {
		switch (c) {
		case 'x':
		case 'r':
		case 's':
			x(v);
			break;
		case 'y':
		case 'g':
		case 't':
			y(v);
			break;
		case 'z':
		case 'b':
		case 'p':
			z(v);
			break;
		case 'w':
		case 'a':
		case 'q':
			w(v);
			break;
		default:
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public int getIndex(char c) {
		switch (c) {
		case 'x':
		case 'r':
		case 's':
			return 0;
		case 'y':
		case 'g':
		case 't':
			return 1;
		case 'z':
		case 'b':
		case 'p':
			return 2;
		case 'w':
		case 'a':
		case 'q':
			return 3;
		}
		return -1;
	}

	@Override
	public void set(Vec4 v) {
		x(v.x());
		y(v.y());
		z(v.z());
		w(v.w());
	}

	public void set(double x, double y, double z, double w) {
		x(x);
		y(y);
		z(z);
		w(w);
	}

	@Override
	public Vec4 add(double n) {
		return new RVec4(x() + n, y() + n, z() + n, w() + n);
	}

	@Override
	public Vec4 add(Vec4 v) {
		return new RVec4(x() + v.x(), y() + v.y(), z() + v.z(), w() + v.w());
	}

	public Vec4 add(double x, double y, double z, double w) {
		return new RVec4(x() + x, y() + y, z() + z, w() + w);
	}

	@Override
	public Vec4 add2(double n) {
		return new RVec4(n + x(), n + y(), n + z(), n + w());
	}

	@Override
	public Vec4 sub(double n) {
		return new RVec4(x() - n, y() - n, z() - n, w() - n);
	}

	@Override
	public Vec4 sub(Vec4 v) {
		return new RVec4(x() - v.x(), y() - v.y(), z() - v.z(), w() - v.w());
	}

	public Vec4 sub(double x, double y, double z, double w) {
		return new RVec4(x() - x, y() - y, z() - z, w() - w);
	}

	@Override
	public Vec4 sub2(double n) {
		return new RVec4(n - x(), n - y(), n - z(), n - w());
	}

	@Override
	public Vec4 neg() {
		return new RVec4(-x(), -y(), -z(), -w());
	}

	@Override
	public Vec4 mul(double n) {
		return new RVec4(x() * n, y() * n, z() * n, w() * n);
	}

	@Override
	public Vec4 mul(Vec4 v) {
		return new RVec4(x() * v.x(), y() * v.y(), z() * v.z(), w() * v.w());
	}

	public Vec4 mul(double x, double y, double z, double w) {
		return new RVec4(x() * x, y() * y, z() * z, w() * w);
	}

	@Override
	public Vec4 mul2(double n) {
		return new RVec4(n * x(), n * y(), n * z(), n * w());
	}

	@Override
	public Vec4 div(double n) {
		return new RVec4(x() / n, y() / n, z() / n, w() / n);
	}

	@Override
	public Vec4 div(Vec4 v) {
		return new RVec4(x() / v.x(), y() / v.y(), z() / v.z(), w() / v.w());
	}

	public Vec4 div(double x, double y, double z, double w) {
		return new RVec4(x() / x, y() / y, z() / z, w() / w);
	}

	@Override
	public Vec4 div2(double n) {
		return new RVec4(n / x(), n / y(), n / z(), n / w());
	}

	@Override
	public Vec4 mod(double n) {
		return new RVec4(x() % n, y() % n, z() % n, w() % n);
	}

	@Override
	public Vec4 mod(Vec4 v) {
		return new RVec4(x() % v.x(), y() % v.y(), z() % v.z(), w() % v.w());
	}

	public Vec4 mod(double x, double y, double z, double w) {
		return new RVec4(x() % x, y() % y, z() % z, w() % w);
	}

	@Override
	public Vec4 mod2(double n) {
		return new RVec4(n % x(), n % y(), n % z(), n % w());
	}

	@Override
	public Vec4 pow(double n) {
		return new RVec4((double) Math.pow(x(), n), (double) Math.pow(y(), n), (double) Math.pow(z(), n), (double) Math.pow(w(), n));
	}

	@Override
	public Vec4 pow(Vec4 v) {
		return new RVec4((double) Math.pow(x(), v.x()), (double) Math.pow(y(), v.y()), (double) Math.pow(y(), v.z()), (double) Math.pow(w(), v.w()));
	}

	public Vec4 pow(double x, double y, double z, double w) {
		return new RVec4((double) Math.pow(x(), x), (double) Math.pow(y(), y), (double) Math.pow(y(), z), (double) Math.pow(w(), w));
	}

	@Override
	public Vec4 pow2(double n) {
		return new RVec4((double) Math.pow(n, x()), (double) Math.pow(n, y()), (double) Math.pow(n, z()), (double) Math.pow(n, w()));
	}

	@Override
	public Vec4 clone() {
		return new RVec4(x(), y(), z(), w());
	}

	@Override
	protected Vec4 _new() {
		return new RVec4(0, 0, 0, 0);
	}

	@Override
	public String toString() {
		return "[" + x() + ", " + y() + ", " + z() + ", " + w() + "]";
	}

	@Override
	protected Vec4 forEach(Func1 f) {
		double x = f.calc(x());
		double y = f.calc(y());
		double z = f.calc(z());
		double w = f.calc(w());
		return new RVec4(x, y, z, w);
	}

	@Override
	protected Vec4 forEach(Vec4 v, Func2 f) {
		double x = f.calc(x(), v.x());
		double y = f.calc(y(), v.y());
		double z = f.calc(z(), v.z());
		double w = f.calc(w(), v.w());
		return new RVec4(x, y, z, w);
	}

	@Override
	protected Vec4 forEach(double n, Func2 f) {
		double x = f.calc(x(), n);
		double y = f.calc(y(), n);
		double z = f.calc(z(), n);
		double w = f.calc(w(), n);
		return new RVec4(x, y, z, w);
	}

	@Override
	protected Vec4 forEach(Vec4 v2, Vec4 v3, Func3 f) {
		double x = f.calc(x(), v2.x(), v3.x());
		double y = f.calc(y(), v2.y(), v3.y());
		double z = f.calc(z(), v2.z(), v3.z());
		double w = f.calc(w(), v2.w(), v3.w());
		return new RVec4(x, y, z, w);
	}

	@Override
	protected Vec4 forEach(Vec4 v, double n, Func3 f) {
		double x = f.calc(x(), v.x(), n);
		double y = f.calc(y(), v.y(), n);
		double z = f.calc(z(), v.z(), n);
		double w = f.calc(w(), v.w(), n);
		return new RVec4(x, y, z, w);
	}

	@Override
	protected Vec4 forEach(double n1, double n2, Func3 f) {
		double x = f.calc(x(), n1, n2);
		double y = f.calc(y(), n1, n2);
		double z = f.calc(z(), n1, n2);
		double w = f.calc(w(), n1, n2);
		return new RVec4(x, y, z, w);
	}

}
