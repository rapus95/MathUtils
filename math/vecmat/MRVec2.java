package math.vecmat;

class MRVec2 extends Vec2 {

	private Mat<?, ?> mat;

	private int m;

	MRVec2(Mat<?, ?> mat, int m) {
		this.mat = mat;
		this.m = m;
	}

	@Override
	public double x() {
		return mat.get(m, 0);
	}

	@Override
	public double y() {
		return mat.get(m, 1);
	}

	@Override
	public void x(double x) {
		mat.set(m, 0, x);
	}

	@Override
	public void y(double y) {
		mat.set(m, 1, y);
	}

}
