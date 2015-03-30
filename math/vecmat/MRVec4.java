package math.vecmat;

class MRVec4 extends Vec4 {

	private Mat<?, ?> mat;

	private int m;

	MRVec4(Mat<?, ?> mat, int m) {
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
	public double z() {
		return mat.get(m, 2);
	}

	@Override
	public double w() {
		return mat.get(m, 3);
	}

	@Override
	public void x(double x) {
		mat.set(m, 0, x);
	}

	@Override
	public void y(double y) {
		mat.set(m, 1, y);
	}

	@Override
	public void z(double z) {
		mat.set(m, 2, z);
	}

	@Override
	public void w(double w) {
		mat.set(m, 3, w);
	}

}
