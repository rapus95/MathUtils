package math.vecmat;

import math.vecmat.Operators.Func1;
import math.vecmat.Operators.Func2;
import math.vecmat.Operators.Func3;

public abstract class Vec2 extends Vec<Vec2> {

	public abstract double x();

	public abstract double y();

	public abstract void x(double x);

	public abstract void y(double y);

	@Override
	public int dim() {
		return 2;
	}

	@Override
	public double get(int i) {
		switch (i) {
		case 0:
			return x();
		case 1:
			return y();
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
		}
		return -1;
	}

	@Override
	public void set(Vec2 v) {
		x(v.x());
		y(v.y());
	}

	public void set(double x, double y) {
		x(x);
		y(y);
	}

	@Override
	public Vec2 add(double n) {
		return new RVec2(x() + n, y() + n);
	}

	@Override
	public Vec2 add(Vec2 v) {
		return new RVec2(x() + v.x(), y() + v.y());
	}

	public Vec2 add(double x, double y) {
		return new RVec2(x() + x, y() + y);
	}

	@Override
	public Vec2 add2(double n) {
		return new RVec2(n + x(), n + y());
	}

	@Override
	public Vec2 sub(double n) {
		return new RVec2(x() - n, y() - n);
	}

	@Override
	public Vec2 sub(Vec2 v) {
		return new RVec2(x() - v.x(), y() - v.y());
	}

	public Vec2 sub(double x, double y) {
		return new RVec2(x() - x, y() - y);
	}

	@Override
	public Vec2 sub2(double n) {
		return new RVec2(n - x(), n - y());
	}

	@Override
	public Vec2 neg() {
		return new RVec2(-x(), -y());
	}

	@Override
	public Vec2 mul(double n) {
		return new RVec2(x() * n, y() * n);
	}

	@Override
	public Vec2 mul(Vec2 v) {
		return new RVec2(x() * v.x(), y() * v.y());
	}

	public Vec2 mul(double x, double y) {
		return new RVec2(x() * x, y() * y);
	}

	@Override
	public Vec2 mul2(double n) {
		return new RVec2(n * x(), n * y());
	}

	@Override
	public Vec2 div(double n) {
		return new RVec2(x() / n, y() / n);
	}

	@Override
	public Vec2 div(Vec2 v) {
		return new RVec2(x() / v.x(), y() / v.y());
	}

	public Vec2 div(double x, double y) {
		return new RVec2(x() / x, y() / y);
	}

	@Override
	public Vec2 div2(double n) {
		return new RVec2(n / x(), n / y());
	}

	@Override
	public Vec2 mod(double n) {
		return new RVec2(x() % n, y() % n);
	}

	@Override
	public Vec2 mod(Vec2 v) {
		return new RVec2(x() % v.x(), y() % v.y());
	}

	public Vec2 mod(double x, double y) {
		return new RVec2(x() % x, y() % y);
	}

	@Override
	public Vec2 mod2(double n) {
		return new RVec2(n % x(), n % y());
	}

	@Override
	public Vec2 pow(double n) {
		return new RVec2((double) Math.pow(x(), n), (double) Math.pow(y(), n));
	}

	@Override
	public Vec2 pow(Vec2 v) {
		return new RVec2((double) Math.pow(x(), v.x()), (double) Math.pow(y(), v.y()));
	}

	public Vec2 pow(double x, double y) {
		return new RVec2((double) Math.pow(x(), x), (double) Math.pow(y(), y));
	}

	@Override
	public Vec2 pow2(double n) {
		return new RVec2((double) Math.pow(n, x()), (double) Math.pow(n, y()));
	}

	@Override
	public Vec2 clone() {
		return new RVec2(x(), y());
	}

	@Override
	protected Vec2 _new() {
		return new RVec2(0, 0);
	}

	@Override
	public String toString() {
		return "[" + x() + ", " + y() + "]";
	}

	@Override
	protected Vec2 forEach(Func1 f) {
		double x = f.calc(x());
		double y = f.calc(y());
		return new RVec2(x, y);
	}

	@Override
	protected Vec2 forEach(Vec2 v, Func2 f) {
		double x = f.calc(x(), v.x());
		double y = f.calc(y(), v.y());
		return new RVec2(x, y);
	}

	@Override
	protected Vec2 forEach(double n, Func2 f) {
		double x = f.calc(x(), n);
		double y = f.calc(y(), n);
		return new RVec2(x, y);
	}

	@Override
	protected Vec2 forEach(Vec2 v2, Vec2 v3, Func3 f) {
		double x = f.calc(x(), v2.x(), v3.x());
		double y = f.calc(y(), v2.y(), v3.y());
		return new RVec2(x, y);
	}

	@Override
	protected Vec2 forEach(Vec2 v, double n, Func3 f) {
		double x = f.calc(x(), v.x(), n);
		double y = f.calc(y(), v.y(), n);
		return new RVec2(x, y);
	}

	@Override
	protected Vec2 forEach(double n1, double n2, Func3 f) {
		double x = f.calc(x(), n1, n2);
		double y = f.calc(y(), n1, n2);
		return new RVec2(x, y);
	}

}
