package math.vecmat;

class RMat2 extends Mat2 {

	public final double[] mat;

	RMat2() {
		mat = new double[4];
	}

	RMat2(double[] mat) {
		if (mat.length != 4)
			throw new IllegalArgumentException();
		this.mat = mat;
	}

	@Override
	public double get(int m, int n) {
		if (m < 0 || m >= 2 || n < 0 || n >= 2)
			throw new IndexOutOfBoundsException();
		return mat[n * 2 + m];
	}

	@Override
	public void set(int m, int n, double v) {
		if (m < 0 || m >= 2 || n < 0 || n >= 2)
			throw new IndexOutOfBoundsException();
		mat[n * 2 + m] = v;
	}

}
