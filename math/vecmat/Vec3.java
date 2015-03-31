package math.vecmat;

import math.utils.Operators.Func1;
import math.utils.Operators.Func2;
import math.utils.Operators.Func3;

public abstract class Vec3 extends Vec<Vec3> {

	public abstract double x();

	public abstract double y();

	public abstract double z();

	public abstract void x(double x);

	public abstract void y(double y);

	public abstract void z(double y);

	@Override
	public int dim() {
		return 3;
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
		}
		return -1;
	}

	@Override
	public void set(Vec3 v) {
		x(v.x());
		y(v.y());
		z(v.z());
	}

	public void set(double x, double y, double z) {
		x(x);
		y(y);
		z(z);
	}

	@Override
	public Vec3 add(double n) {
		return new RVec3(x() + n, y() + n, z() + n);
	}

	@Override
	public Vec3 add(Vec3 v) {
		return new RVec3(x() + v.x(), y() + v.y(), z() + v.z());
	}

	public Vec3 add(double x, double y, double z) {
		return new RVec3(x() + x, y() + y, z() + z);
	}

	@Override
	public Vec3 add2(double n) {
		return new RVec3(n + x(), n + y(), n + z());
	}

	@Override
	public Vec3 sub(double n) {
		return new RVec3(x() - n, y() - n, z() - n);
	}

	@Override
	public Vec3 sub(Vec3 v) {
		return new RVec3(x() - v.x(), y() - v.y(), z() - v.z());
	}

	public Vec3 sub(double x, double y, double z) {
		return new RVec3(x() - x, y() - y, z() - z);
	}

	@Override
	public Vec3 sub2(double n) {
		return new RVec3(n - x(), n - y(), n - z());
	}

	@Override
	public Vec3 neg() {
		return new RVec3(-x(), -y(), -z());
	}

	@Override
	public Vec3 mul(double n) {
		return new RVec3(x() * n, y() * n, z() * n);
	}

	@Override
	public Vec3 mul(Vec3 v) {
		return new RVec3(x() * v.x(), y() * v.y(), z() * v.z());
	}

	public Vec3 mul(double x, double y, double z) {
		return new RVec3(x() * x, y() * y, z() * z);
	}

	@Override
	public Vec3 mul2(double n) {
		return new RVec3(n * x(), n * y(), n * z());
	}

	@Override
	public Vec3 div(double n) {
		return new RVec3(x() / n, y() / n, z() / n);
	}

	@Override
	public Vec3 div(Vec3 v) {
		return new RVec3(x() / v.x(), y() / v.y(), z() / v.z());
	}

	public Vec3 div(double x, double y, double z) {
		return new RVec3(x() / x, y() / y, z() / z);
	}

	@Override
	public Vec3 div2(double n) {
		return new RVec3(n / x(), n / y(), n / z());
	}

	@Override
	public Vec3 mod(double n) {
		return new RVec3(x() % n, y() % n, z() % n);
	}

	@Override
	public Vec3 mod(Vec3 v) {
		return new RVec3(x() % v.x(), y() % v.y(), z() % v.z());
	}

	public Vec3 mod(double x, double y, double z) {
		return new RVec3(x() % x, y() % y, z() % z);
	}

	@Override
	public Vec3 mod2(double n) {
		return new RVec3(n % x(), n % y(), n % z());
	}

	@Override
	public Vec3 pow(double n) {
		return new RVec3((double) Math.pow(x(), n), (double) Math.pow(y(), n), (double) Math.pow(z(), n));
	}

	@Override
	public Vec3 pow(Vec3 v) {
		return new RVec3((double) Math.pow(x(), v.x()), (double) Math.pow(y(), v.y()), (double) Math.pow(y(), v.z()));
	}

	public Vec3 pow(double x, double y, double z) {
		return new RVec3((double) Math.pow(x(), x), (double) Math.pow(y(), y), (double) Math.pow(y(), z));
	}

	@Override
	public Vec3 pow2(double n) {
		return new RVec3((double) Math.pow(n, x()), (double) Math.pow(n, y()), (double) Math.pow(n, z()));
	}

	@Override
	public Vec3 clone() {
		return new RVec3(x(), y(), z());
	}

	@Override
	protected Vec3 _new() {
		return new RVec3(0, 0, 0);
	}

	@Override
	public String toString() {
		return "[" + x() + ", " + y() + ", " + z() + "]";
	}

	public Vec3 cross(Vec3 v) {
		final double x = x();
		final double y = y();
		final double z = z();
		final double vx = v.x();
		final double vy = v.y();
		final double vz = v.z();
		return new RVec3(y * vz - z * vy, z * vx - x * vz, x * vy - y * vx);
	}

	@Override
	protected Vec3 forEach(Func1 f) {
		double x = f.calc(x());
		double y = f.calc(y());
		double z = f.calc(z());
		return new RVec3(x, y, z);
	}

	@Override
	protected Vec3 forEach(Vec3 v, Func2 f) {
		double x = f.calc(x(), v.x());
		double y = f.calc(y(), v.y());
		double z = f.calc(z(), v.z());
		return new RVec3(x, y, z);
	}

	@Override
	protected Vec3 forEach(double n, Func2 f) {
		double x = f.calc(x(), n);
		double y = f.calc(y(), n);
		double z = f.calc(z(), n);
		return new RVec3(x, y, z);
	}

	@Override
	protected Vec3 forEach(Vec3 v2, Vec3 v3, Func3 f) {
		double x = f.calc(x(), v2.x(), v3.x());
		double y = f.calc(y(), v2.y(), v3.y());
		double z = f.calc(z(), v2.z(), v3.z());
		return new RVec3(x, y, z);
	}

	@Override
	protected Vec3 forEach(Vec3 v, double n, Func3 f) {
		double x = f.calc(x(), v.x(), n);
		double y = f.calc(y(), v.y(), n);
		double z = f.calc(z(), v.z(), n);
		return new RVec3(x, y, z);
	}

	@Override
	protected Vec3 forEach(double n1, double n2, Func3 f) {
		double x = f.calc(x(), n1, n2);
		double y = f.calc(y(), n1, n2);
		double z = f.calc(z(), n1, n2);
		return new RVec3(x, y, z);
	}

}
