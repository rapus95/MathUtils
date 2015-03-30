package math.vecmat;

class MRVecN extends VecN {

	private Mat<?, ?> mat;

	private int m;

	MRVecN(Mat<?, ?> mat, int m) {
		this.mat = mat;
		this.m = m;
	}

	@Override
	public int dim() {
		return mat.n();
	}

	@Override
	public double get(int i) {
		return mat.get(m, i);
	}

	@Override
	public void set(int i, double v) {
		mat.set(m, i, v);
	}

}
