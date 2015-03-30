package math.vecmat;

class RVec2 extends Vec2 {

	public double x;
	public double y;

	RVec2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double x() {
		return x;
	}

	@Override
	public double y() {
		return y;
	}

	@Override
	public void x(double x) {
		this.x = x;
	}

	@Override
	public void y(double y) {
		this.y = y;
	}

}
