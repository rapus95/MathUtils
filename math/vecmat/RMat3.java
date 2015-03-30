package math.vecmat;

class RMat3 extends Mat3 {

	public final double[] mat;

	RMat3() {
		mat = new double[9];
	}

	RMat3(double[] mat) {
		if (mat.length != 9)
			throw new IllegalArgumentException();
		this.mat = mat;
	}

	@Override
	public double get(int m, int n) {
		if (m < 0 || m >= 3 || n < 0 || n >= 3)
			throw new IndexOutOfBoundsException();
		return mat[n * 3 + m];
	}

	@Override
	public void set(int m, int n, double v) {
		if (m < 0 || m >= 3 || n < 0 || n >= 3)
			throw new IndexOutOfBoundsException();
		mat[n * 3 + m] = v;
	}

}
