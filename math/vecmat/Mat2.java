package math.vecmat;

public abstract class Mat2 extends Mat<Mat2, Mat2> {

	@Override
	public int n() {
		return 2;
	}

	@Override
	public int m() {
		return 2;
	}

	@Override
	protected Vec2 getRow(int m) {
		return new MRVec2(this, m);
	}

	@Override
	protected Vec2 getColumn(int n) {
		return new MCVec2(this, n);
	}

	@Override
	public Mat2 mul(Mat2 v) {
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double v00 = v.get(0, 0);
		final double v10 = v.get(1, 0);
		final double v01 = v.get(0, 1);
		final double v11 = v.get(1, 1);
		RMat2 m = new RMat2();
		m.mat[0] = t00 * v00 + t01 * v10;
		m.mat[1] = t10 * v00 + t11 * v10;
		m.mat[2] = t00 * v01 + t01 * v11;
		m.mat[3] = t10 * v01 + t11 * v11;
		return m;
	}

	@Override
	protected Vec2 mulV(Vec<?> v) {
		if (v.dim() != 2)
			throw new IllegalArgumentException();
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double x = v.get(0);
		final double y = v.get(1);
		final double xx = t00 * x + t10 * y;
		final double yy = t01 * x + t11 * y;
		return Vec.Vec2(xx, yy);
	}

	@Override
	public Mat2 transpose() {
		return new TMat2(this);
	}

	@Override
	public Mat2 invert() {
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double det = t00 * t11 - t01 * t10;
		if(det==0)
			throw new IllegalStateException("Not invertable matrix");
		final double invert = 1 / det;
		RMat2 m = new RMat2();
		m.mat[0] = invert * t11;
		m.mat[1] = -invert * t10;
		m.mat[2] = -invert * t01;
		m.mat[3] = invert * t00;
		return m;
	}

	@Override
	public double det() {
		return get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);
	}

	@Override
	public Mat2 adjunkte() {
		RMat2 m = new RMat2();
		m.mat[0] = get(1, 1);
		m.mat[1] = -get(0, 1);
		m.mat[2] = -get(1, 0);
		m.mat[3] = get(0, 0);
		return m;
	}

	@Override
	public Mat2 pow(int y) {
		if(y==0)
			return Mat.Mat2();
		Mat2 m = this;
		for(int i=1; i<y; i++){
			m = m.mul(this);
		}
		return m;
	}
	
	@Override
	public Mat2 clone() {
		RMat2 m = new RMat2();
		m.mat[0] = get(0, 0);
		m.mat[1] = get(1, 0);
		m.mat[2] = get(0, 1);
		m.mat[3] = get(1, 1);
		return m;
	}

	@Override
	protected Mat2 _new() {
		return new RMat2();
	}

}
