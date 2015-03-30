package math.vecmat;

class MCVecN extends VecN {

	private Mat<?, ?> mat;

	private int n;

	MCVecN(Mat<?, ?> mat, int n) {
		this.mat = mat;
		this.n = n;
	}

	@Override
	public int dim() {
		return mat.m();
	}

	@Override
	public double get(int i) {
		return mat.get(i, n);
	}

	@Override
	public void set(int i, double v) {
		mat.set(i, n, v);
	}

}
