package math.vecmat;

class MCVec2 extends Vec2 {

	private Mat<?, ?> mat;

	private int n;

	MCVec2(Mat<?, ?> mat, int n) {
		this.mat = mat;
		this.n = n;
	}

	@Override
	public double x() {
		return mat.get(0, n);
	}

	@Override
	public double y() {
		return mat.get(1, n);
	}

	@Override
	public void x(double x) {
		mat.set(0, n, x);
	}

	@Override
	public void y(double y) {
		mat.set(1, n, y);
	}

}
