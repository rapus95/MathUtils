package math.vecmat;

class RMat4 extends Mat4 {

	public final double[] mat;

	RMat4() {
		mat = new double[16];
	}

	RMat4(double[] mat) {
		if (mat.length != 16)
			throw new IllegalArgumentException();
		this.mat = mat;
	}

	@Override
	public double get(int m, int n) {
		if (m < 0 || m >= 4 || n < 0 || n >= 4)
			throw new IndexOutOfBoundsException();
		return mat[n * 4 + m];
	}

	@Override
	public void set(int m, int n, double v) {
		if (m < 0 || m >= 4 || n < 0 || n >= 4)
			throw new IndexOutOfBoundsException();
		mat[n * 4 + m] = v;
	}

}
