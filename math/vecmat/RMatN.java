package math.vecmat;

class RMatN extends MatN {

	public final int size;

	public final double[] mat;

	RMatN(int size) {
		this.size = size;
		mat = new double[size * size];
	}

	RMatN(int size, double[] mat) {
		this.size = size;
		if (mat.length != size * size)
			throw new IllegalArgumentException();
		this.mat = mat;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public double get(int m, int n) {
		if (m < 0 || m >= size || n < 0 || n >= size)
			throw new IndexOutOfBoundsException();
		return mat[n * size + m];
	}

	@Override
	public void set(int m, int n, double v) {
		if (m < 0 || m >= size || n < 0 || n >= size)
			throw new IndexOutOfBoundsException();
		mat[n * size + m] = v;
	}

}
