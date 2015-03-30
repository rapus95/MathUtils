package math.vecmat;

class RMatMN extends MatMN {

	public final int m;

	public final int n;

	public final double[] mat;

	RMatMN(int m, int n) {
		this.m = m;
		this.n = n;
		mat = new double[m * n];
	}

	RMatMN(int m, int n, double[] mat) {
		this.m = m;
		this.n = n;
		if (mat.length != m * n)
			throw new IllegalArgumentException();
		this.mat = mat;
	}

	@Override
	public int n() {
		return n;
	}

	@Override
	public int m() {
		return m;
	}

	@Override
	public double get(int m, int n) {
		if (m < 0 || m >= this.m || n < 0 || n >= this.n)
			throw new IndexOutOfBoundsException();
		return mat[n * this.m + m];
	}

	@Override
	public void set(int m, int n, double v) {
		if (m < 0 || m >= this.m || n < 0 || n >= this.n)
			throw new IndexOutOfBoundsException();
		mat[n * this.m + m] = v;
	}

}
