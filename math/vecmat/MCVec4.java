package math.vecmat;

class MCVec4 extends Vec4 {

	private Mat<?, ?> mat;

	private int n;

	MCVec4(Mat<?, ?> mat, int n) {
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
	public double z() {
		return mat.get(2, n);
	}

	@Override
	public double w() {
		return mat.get(3, n);
	}

	@Override
	public void x(double x) {
		mat.set(0, n, x);
	}

	@Override
	public void y(double y) {
		mat.set(1, n, y);
	}

	@Override
	public void z(double z) {
		mat.set(2, n, z);
	}

	@Override
	public void w(double w) {
		mat.set(3, n, w);
	}

}
