package math.vecmat;

public abstract class Mat3 extends Mat<Mat3, Mat3> {

	@Override
	public int n() {
		return 3;
	}

	@Override
	public int m() {
		return 3;
	}

	@Override
	protected Vec3 getRow(int m) {
		return new MRVec3(this, m);
	}

	@Override
	protected Vec3 getColumn(int n) {
		return new MCVec3(this, n);
	}

	@Override
	public Mat3 mul(Mat3 v) {
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t20 = get(2, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double t21 = get(2, 1);
		final double t02 = get(0, 2);
		final double t12 = get(1, 2);
		final double t22 = get(2, 2);
		final double v00 = v.get(0, 0);
		final double v10 = v.get(1, 0);
		final double v20 = v.get(2, 0);
		final double v01 = v.get(0, 1);
		final double v11 = v.get(1, 1);
		final double v21 = v.get(2, 1);
		final double v02 = v.get(0, 2);
		final double v12 = v.get(1, 2);
		final double v22 = v.get(2, 2);
		RMat3 m = new RMat3();
		m.mat[0] = t00 * v00 + t01 * v10 + t02 * v20;
		m.mat[1] = t10 * v00 + t11 * v10 + t12 * v20;
		m.mat[2] = t20 * v00 + t21 * v10 + t22 * v20;
		m.mat[3] = t00 * v01 + t01 * v11 + t02 * v21;
		m.mat[4] = t10 * v01 + t11 * v11 + t12 * v21;
		m.mat[5] = t20 * v01 + t21 * v11 + t22 * v21;
		m.mat[6] = t00 * v02 + t01 * v12 + t02 * v22;
		m.mat[7] = t10 * v02 + t11 * v12 + t12 * v22;
		m.mat[8] = t20 * v02 + t21 * v12 + t22 * v22;
		return m;
	}

	@Override
	public Mat3 transpose() {
		return new TMat3(this);
	}

	@Override
	public Mat3 invert() {
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t20 = get(2, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double t21 = get(2, 1);
		final double t02 = get(0, 2);
		final double t12 = get(1, 2);
		final double t22 = get(2, 2);
		final double m1 = t11 * t22 - t21 * t12;
		final double m2 = -t10 * t22 + t12 * t20;
		final double m3 = t10 * t21 - t11 * t20;
		final double det = t00 * m1 + t01 * m2 + t02 * m3;
		if(det==0)
			throw new IllegalStateException("Not invertable matrix");
		final double invdet = 1 / det;
		RMat3 m = new RMat3();
		m.mat[0] = m1 * invdet;
		m.mat[1] = -(t01 * t22 - t02 * t21) * invdet;
		m.mat[2] = (t01 * t12 - t02 * t11) * invdet;
		m.mat[3] = m2 * invdet;
		m.mat[4] = (t00 * t22 - t02 * t20) * invdet;
		m.mat[5] = -(t00 * t12 - t10 * t02) * invdet;
		m.mat[6] = m3 * invdet;
		m.mat[7] = -(t00 * t21 - t20 * t01) * invdet;
		m.mat[8] = (t00 * t11 - t10 * t01) * invdet;
		return m;
	}

	@Override
	protected Vec3 mulV(Vec<?> v) {
		if (v.dim() != 3)
			throw new IllegalArgumentException();
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t20 = get(2, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double t21 = get(2, 1);
		final double t02 = get(0, 2);
		final double t12 = get(1, 2);
		final double t22 = get(2, 2);
		final double x = v.get(0);
		final double y = v.get(1);
		final double z = v.get(2);
		final double xx = t00 * x + t10 * y + t20 * z;
		final double yy = t01 * x + t11 * y + t21 * z;
		final double zz = t02 * x + t12 * y + t22 * z;
		return Vec.Vec3(xx, yy, zz);
	}

	@Override
	public double det() {
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t20 = get(2, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double t21 = get(2, 1);
		final double t02 = get(0, 2);
		final double t12 = get(1, 2);
		final double t22 = get(2, 2);
		return t00 * (t11 * t22 - t21 * t12) - t01 * (t10 * t22 - t12 * t20) + t02 * (t10 * t21 - t11 * t20);
	}

	@Override
	public Mat3 adjunkte() {
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t20 = get(2, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double t21 = get(2, 1);
		final double t02 = get(0, 2);
		final double t12 = get(1, 2);
		final double t22 = get(2, 2);
		RMat3 m = new RMat3();
		m.mat[0] = t11 * t22 - t12 * t21;
		m.mat[1] = t12 * t20 - t10 * t22;
		m.mat[2] = t10 * t21 - t11 * t20;
		m.mat[3] = t02 * t21 - t01 * t22;
		m.mat[4] = t00 * t22 - t02 * t20;
		m.mat[5] = t01 * t20 - t00 * t21;
		m.mat[6] = t01 * t12 - t02 * t11;
		m.mat[7] = t02 * t10 - t00 * t12;
		m.mat[8] = t00 * t11 - t01 * t10;
		return m;
	}

	@Override
	public Mat3 clone() {
		RMat3 m = new RMat3();
		m.mat[0] = get(0, 0);
		m.mat[1] = get(1, 0);
		m.mat[2] = get(2, 0);
		m.mat[3] = get(0, 1);
		m.mat[4] = get(1, 1);
		m.mat[5] = get(2, 1);
		m.mat[6] = get(0, 2);
		m.mat[7] = get(1, 2);
		m.mat[8] = get(2, 2);
		return m;
	}

	@Override
	public Mat3 pow(int y) {
		if(y==0)
			return Mat.Mat3();
		Mat3 m = this;
		for(int i=1; i<y; i++){
			m = m.mul(this);
		}
		return m;
	}
	
	@Override
	protected Mat3 _new() {
		return new RMat3();
	}

}
