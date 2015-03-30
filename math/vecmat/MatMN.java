package math.vecmat;

public abstract class MatMN extends Mat<MatMN, Mat<?, ?>> {

	@Override
	protected Vec<?> getRow(int m) {
		switch (n()) {
		case 2:
			return new MRVec2(this, m);
		case 3:
			return new MRVec3(this, m);
		case 4:
			return new MRVec4(this, m);
		}
		return new MRVecN(this, m);
	}

	@Override
	protected Vec<?> getColumn(int n) {
		switch (m()) {
		case 2:
			return new MCVec2(this, n);
		case 3:
			return new MCVec3(this, n);
		case 4:
			return new MCVec4(this, n);
		}
		return new MCVecN(this, n);
	}

	@Override
	public Mat<?, ?> mul(MatMN v) {
		return mulU(v);
	}

	@Override
	protected Vec<?> mulV(Vec<?> v) {
		final int m = m();
		final int n = n();
		if (m != v.dim())
			throw new IllegalArgumentException();
		double[] vec = new double[n];
		for (int i = 0; i < n; i++) {
			double vv = 0;
			for (int j = 0; j < m; j++) {
				vv += get(j, i) * v.get(j);
			}
			vec[i] = vv;
		}
		return Vec.VecX(vec);
	}

	@Override
	public MatMN transpose() {
		return new TMatMN(this);
	}

	@Override
	public MatMN invert() {
		throw new UnsupportedOperationException();
	}

	@Override
	public double det() {
		throw new UnsupportedOperationException();
	}

	@Override
	public MatMN adjunkte() {
		throw new UnsupportedOperationException();
	}

	@Override
	public MatMN pow(int y) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public MatMN clone() {
		final int m = m();
		final int n = n();
		MatMN mat = new RMatMN(m, n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				mat.set(j, i, get(j, i));
			}
		}
		return mat;
	}

	@Override
	protected MatMN _new() {
		return new RMatMN(m(), n());
	}

}
