package math.vecmat;

public abstract class Mat4 extends Mat<Mat4, Mat4> {

	@Override
	public int n() {
		return 4;
	}

	@Override
	public int m() {
		return 4;
	}

	@Override
	protected Vec4 getRow(int m) {
		return new MRVec4(this, m);
	}

	@Override
	protected Vec4 getColumn(int n) {
		return new MCVec4(this, n);
	}

	@Override
	public Mat4 mul(Mat4 v) {
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t20 = get(2, 0);
		final double t30 = get(3, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double t21 = get(2, 1);
		final double t31 = get(3, 1);
		final double t02 = get(0, 2);
		final double t12 = get(1, 2);
		final double t22 = get(2, 2);
		final double t32 = get(3, 2);
		final double t03 = get(0, 3);
		final double t13 = get(1, 3);
		final double t23 = get(2, 3);
		final double t33 = get(3, 3);
		final double v00 = v.get(0, 0);
		final double v10 = v.get(1, 0);
		final double v20 = v.get(2, 0);
		final double v30 = v.get(3, 0);
		final double v01 = v.get(0, 1);
		final double v11 = v.get(1, 1);
		final double v21 = v.get(2, 1);
		final double v31 = v.get(3, 1);
		final double v02 = v.get(0, 2);
		final double v12 = v.get(1, 2);
		final double v22 = v.get(2, 2);
		final double v32 = v.get(3, 2);
		final double v03 = v.get(0, 3);
		final double v13 = v.get(1, 3);
		final double v23 = v.get(2, 3);
		final double v33 = v.get(3, 3);
		RMat4 m = new RMat4();
		m.mat[0] = t00 * v00 + t01 * v10 + t02 * v20 + t03 * v30;
		m.mat[1] = t10 * v00 + t11 * v10 + t12 * v20 + t13 * v30;
		m.mat[2] = t20 * v00 + t21 * v10 + t22 * v20 + t23 * v30;
		m.mat[3] = t30 * v00 + t31 * v10 + t32 * v20 + t33 * v30;
		m.mat[4] = t00 * v01 + t01 * v11 + t02 * v21 + t03 * v31;
		m.mat[5] = t10 * v01 + t11 * v11 + t12 * v21 + t13 * v31;
		m.mat[6] = t20 * v01 + t21 * v11 + t22 * v21 + t23 * v31;
		m.mat[7] = t30 * v01 + t31 * v11 + t32 * v21 + t33 * v31;
		m.mat[8] = t00 * v02 + t01 * v12 + t02 * v22 + t03 * v32;
		m.mat[9] = t10 * v02 + t11 * v12 + t12 * v22 + t13 * v32;
		m.mat[10] = t20 * v02 + t21 * v12 + t22 * v22 + t23 * v32;
		m.mat[11] = t30 * v02 + t31 * v12 + t32 * v22 + t33 * v32;
		m.mat[12] = t00 * v03 + t01 * v13 + t02 * v23 + t03 * v33;
		m.mat[13] = t10 * v03 + t11 * v13 + t12 * v23 + t13 * v33;
		m.mat[14] = t20 * v03 + t21 * v13 + t22 * v23 + t23 * v33;
		m.mat[15] = t30 * v03 + t31 * v13 + t32 * v23 + t33 * v33;
		return m;
	}

	@Override
	public Mat4 transpose() {
		return new TMat4(this);
	}

	@Override
	public Mat4 invert() {
		RMat4 m = new RMat4();
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t20 = get(2, 0);
		final double t30 = get(3, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double t21 = get(2, 1);
		final double t31 = get(3, 1);
		final double t02 = get(0, 2);
		final double t12 = get(1, 2);
		final double t22 = get(2, 2);
		final double t32 = get(3, 2);
		final double t03 = get(0, 3);
		final double t13 = get(1, 3);
		final double t23 = get(2, 3);
		final double t33 = get(3, 3);

		final double s0 = t00 * t11 - t10 * t01;
		final double s1 = t00 * t12 - t10 * t02;
		final double s2 = t00 * t13 - t10 * t03;
		final double s3 = t01 * t12 - t11 * t02;
		final double s4 = t01 * t13 - t11 * t03;
		final double s5 = t02 * t13 - t12 * t03;

		final double c5 = t22 * t33 - t32 * t23;
		final double c4 = t21 * t33 - t31 * t23;
		final double c3 = t21 * t32 - t31 * t22;
		final double c2 = t20 * t33 - t30 * t23;
		final double c1 = t20 * t32 - t30 * t22;
		final double c0 = t20 * t31 - t30 * t21;

		final double det = s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0;
		
		if(det==0)
			throw new IllegalStateException("Not invertable matrix");
		
		final double invdet = 1 / det;

		m.mat[0] = (t11 * c5 - t12 * c4 + t13 * c3) * invdet;
		m.mat[4] = (-t01 * c5 + t02 * c4 - t03 * c3) * invdet;
		m.mat[8] = (t31 * s5 - t32 * s4 + t33 * s3) * invdet;
		m.mat[12] = (-t21 * s5 + t22 * s4 - t23 * s3) * invdet;

		m.mat[1] = (-t10 * c5 + t12 * c2 - t13 * c1) * invdet;
		m.mat[5] = (t00 * c5 - t02 * c2 + t03 * c1) * invdet;
		m.mat[9] = (-t30 * s5 + t32 * s2 - t33 * s1) * invdet;
		m.mat[13] = (t20 * s5 - t22 * s2 + t23 * s1) * invdet;

		m.mat[2] = (t10 * c4 - t11 * c2 + t13 * c0) * invdet;
		m.mat[6] = (-t00 * c4 + t01 * c2 - t03 * c0) * invdet;
		m.mat[10] = (t30 * s4 - t31 * s2 + t33 * s0) * invdet;
		m.mat[14] = (-t20 * s4 + t21 * s2 - t23 * s0) * invdet;

		m.mat[3] = (-t10 * c3 + t11 * c1 - t12 * c0) * invdet;
		m.mat[7] = (t00 * c3 - t01 * c1 + t02 * c0) * invdet;
		m.mat[11] = (-t30 * s3 + t31 * s1 - t32 * s0) * invdet;
		m.mat[15] = (t20 * s3 - t21 * s1 + t22 * s0) * invdet;

		return m;
	}

	@Override
	protected Vec4 mulV(Vec<?> v) {
		if (v.dim() != 4)
			throw new IllegalArgumentException();
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t20 = get(2, 0);
		final double t30 = get(3, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double t21 = get(2, 1);
		final double t31 = get(3, 1);
		final double t02 = get(0, 2);
		final double t12 = get(1, 2);
		final double t22 = get(2, 2);
		final double t32 = get(3, 2);
		final double t03 = get(0, 3);
		final double t13 = get(1, 3);
		final double t23 = get(2, 3);
		final double t33 = get(3, 3);
		final double x = v.get(0);
		final double y = v.get(1);
		final double z = v.get(2);
		final double w = v.get(3);
		final double xx = t00 * x + t10 * y + t20 * z + t30 * w;
		final double yy = t01 * x + t11 * y + t21 * z + t31 * w;
		final double zz = t02 * x + t12 * y + t22 * z + t32 * w;
		final double ww = t03 * x + t13 * y + t23 * z + t33 * w;
		return Vec.Vec4(xx, yy, zz, ww);
	}

	@Override
	public double det() {
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t20 = get(2, 0);
		final double t30 = get(3, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double t21 = get(2, 1);
		final double t31 = get(3, 1);
		final double t02 = get(0, 2);
		final double t12 = get(1, 2);
		final double t22 = get(2, 2);
		final double t32 = get(3, 2);
		final double t03 = get(0, 3);
		final double t13 = get(1, 3);
		final double t23 = get(2, 3);
		final double t33 = get(3, 3);

		final double s0 = t00 * t11 - t10 * t01;
		final double s1 = t00 * t12 - t10 * t02;
		final double s2 = t00 * t13 - t10 * t03;
		final double s3 = t01 * t12 - t11 * t02;
		final double s4 = t01 * t13 - t11 * t03;
		final double s5 = t02 * t13 - t12 * t03;

		final double c5 = t22 * t33 - t32 * t23;
		final double c4 = t21 * t33 - t31 * t23;
		final double c3 = t21 * t32 - t31 * t22;
		final double c2 = t20 * t33 - t30 * t23;
		final double c1 = t20 * t32 - t30 * t22;
		final double c0 = t20 * t31 - t30 * t21;

		return s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0;
	}

	@Override
	public Mat4 adjunkte() {
		RMat4 m = new RMat4();
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t20 = get(2, 0);
		final double t30 = get(3, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double t21 = get(2, 1);
		final double t31 = get(3, 1);
		final double t02 = get(0, 2);
		final double t12 = get(1, 2);
		final double t22 = get(2, 2);
		final double t32 = get(3, 2);
		final double t03 = get(0, 3);
		final double t13 = get(1, 3);
		final double t23 = get(2, 3);
		final double t33 = get(3, 3);

		final double s0 = t00 * t11 - t10 * t01;
		final double s1 = t00 * t12 - t10 * t02;
		final double s2 = t00 * t13 - t10 * t03;
		final double s3 = t01 * t12 - t11 * t02;
		final double s4 = t01 * t13 - t11 * t03;
		final double s5 = t02 * t13 - t12 * t03;

		final double c5 = t22 * t33 - t32 * t23;
		final double c4 = t21 * t33 - t31 * t23;
		final double c3 = t21 * t32 - t31 * t22;
		final double c2 = t20 * t33 - t30 * t23;
		final double c1 = t20 * t32 - t30 * t22;
		final double c0 = t20 * t31 - t30 * t21;

		m.mat[0] = t11 * c5 - t12 * c4 + t13 * c3;
		m.mat[4] = -t01 * c5 + t02 * c4 - t03 * c3;
		m.mat[8] = t31 * s5 - t32 * s4 + t33 * s3;
		m.mat[12] = -t21 * s5 + t22 * s4 - t23 * s3;

		m.mat[1] = -t10 * c5 + t12 * c2 - t13 * c1;
		m.mat[5] = t00 * c5 - t02 * c2 + t03 * c1;
		m.mat[9] = -t30 * s5 + t32 * s2 - t33 * s1;
		m.mat[13] = t20 * s5 - t22 * s2 + t23 * s1;

		m.mat[2] = t10 * c4 - t11 * c2 + t13 * c0;
		m.mat[6] = -t00 * c4 + t01 * c2 - t03 * c0;
		m.mat[10] = t30 * s4 - t31 * s2 + t33 * s0;
		m.mat[14] = -t20 * s4 + t21 * s2 - t23 * s0;

		m.mat[3] = -t10 * c3 + t11 * c1 - t12 * c0;
		m.mat[7] = t00 * c3 - t01 * c1 + t02 * c0;
		m.mat[11] = -t30 * s3 + t31 * s1 - t32 * s0;
		m.mat[15] = t20 * s3 - t21 * s1 + t22 * s0;

		return m;
	}
	
	@Override
	public Mat4 pow(int y) {
		if(y==0)
			return Mat.Mat4();
		Mat4 m = this;
		for(int i=1; i<y; i++){
			m = m.mul(this);
		}
		return m;
	}

	@Override
	public Mat4 clone() {
		RMat4 m = new RMat4();
		m.mat[0] = get(0, 0);
		m.mat[1] = get(1, 0);
		m.mat[2] = get(2, 0);
		m.mat[3] = get(3, 0);
		m.mat[4] = get(0, 1);
		m.mat[5] = get(1, 1);
		m.mat[6] = get(2, 1);
		m.mat[7] = get(3, 1);
		m.mat[8] = get(0, 2);
		m.mat[9] = get(1, 2);
		m.mat[10] = get(2, 2);
		m.mat[11] = get(3, 2);
		m.mat[12] = get(0, 3);
		m.mat[13] = get(1, 3);
		m.mat[14] = get(2, 3);
		m.mat[15] = get(3, 3);
		return m;
	}

	@Override
	protected Mat4 _new() {
		return new RMat4();
	}

	public Mat4 translate(Vec3 v) {
		return mul(createTranslationMarix(v));
	}

	public Mat4 translate(double x, double y, double z) {
		return mul(createTranslationMarix(x, y, z));
	}

	public Mat4 rotate(double a, Vec3 axis) {
		return mul(createRotationMarix(a, axis));
	}
	
	public Mat4 rotate(double a, double x, double y, double z) {
		return mul(createRotationMarix(a, x, y, z));
	}

	public Mat4 rotateRad(double a, Vec3 axis) {
		return mul(createRotationMarixRad(a, axis));
	}
	
	public Mat4 rotateRad(double a, double x, double y, double z) {
		return mul(createRotationMarixRad(a, x, y, z));
	}

	public Mat4 scale(Vec3 v) {
		return mul(createScaleMarix(v));
	}

	public Mat4 scale(double x, double y, double z) {
		return mul(createScaleMarix(x, y, z));
	}

	public Mat4 rotateEuler(Vec3 v) {
		return mul(createEulerRotationMarix(v));
	}

	public Mat4 rotateEuler(double x, double y, double z) {
		return mul(createEulerRotationMarix(x, y, z));
	}

	public Mat4 rotateEulerRad(Vec3 v) {
		return mul(createEulerRotationMarixRad(v));
	}

	public Mat4 rotateEulerRad(double x, double y, double z) {
		return mul(createEulerRotationMarixRad(x, y, z));
	}

	public Mat4 setTranslation(Vec3 v) {
		return setTranslation(v.x(), v.y(), v.z());
	}

	public Mat4 setTranslation(double x, double y, double z) {
		Mat4 m = clone();
		m.set(0, 3, x);
		m.set(1, 3, y);
		m.set(2, 3, z);
		return m;
	}

	public Mat4 setRotationRad(Vec3 v) {
		return setRotationRad(v.x(), v.y(), v.z());
	}

	public Mat4 setRotationRad(double x, double y, double z) {
		Mat4 m = clone();
		double cr = (double) Math.cos(x);
		double sr = (double) Math.sin(x);
		double cp = (double) Math.cos(y);
		double sp = (double) Math.sin(y);
		double cy = (double) Math.cos(z);
		double sy = (double) Math.sin(z);

		m.set(0, 0, cp * cy);
		m.set(1, 0, cp * sy);
		m.set(2, 0, -sp);

		double srsp = sr * sp;
		double crsp = cr * sp;

		m.set(0, 1, srsp * cy - cr * sy);
		m.set(1, 1, srsp * sy + cr * cy);
		m.set(2, 1, sr * cp);

		m.set(0, 2, crsp * cy + sr * sy);
		m.set(1, 2, crsp * sy - sr * cy);
		m.set(2, 2, cr * cp);
		return m;
	}

	public Vec3 inverseRotateVec(Vec3 v) {
		final double x = v.x();
		final double y = v.y();
		final double z = v.z();
		final double xx = x * get(0, 0) + y * get(1, 0) + z * get(2, 0);
		final double yy = x * get(0, 1) + y * get(1, 1) + z * get(2, 1);
		final double zz = x * get(0, 2) + y * get(1, 2) + z * get(2, 2);
		return Vec.Vec3(xx, yy, zz);
	}

	public Vec3 rotateVec(Vec3 v) {
		final double x = v.x();
		final double y = v.y();
		final double z = v.z();
		final double xx = x * get(0, 0) + y * get(0, 1) + z * get(0, 2);
		final double yy = x * get(1, 0) + y * get(1, 1) + z * get(1, 2);
		final double zz = x * get(2, 0) + y * get(2, 1) + z * get(2, 2);
		return Vec.Vec3(xx, yy, zz);
	}

	public Vec3 translateVec(Vec3 v) {
		return v.add(get(0, 3), get(1, 3), get(2, 3));
	}

	public Vec3 inverseTranslateVec(Vec3 v) {
		return v.sub(get(0, 3), get(1, 3), get(2, 3));
	}

	public Mat4 postMultiply(Mat4 v) {
		final double t00 = get(0, 0);
		final double t10 = get(1, 0);
		final double t20 = get(2, 0);
		final double t01 = get(0, 1);
		final double t11 = get(1, 1);
		final double t21 = get(2, 1);
		final double t02 = get(0, 2);
		final double t12 = get(1, 2);
		final double t22 = get(2, 2);
		final double t03 = get(0, 3);
		final double t13 = get(1, 3);
		final double t23 = get(2, 3);
		final double v00 = v.get(0, 0);
		final double v10 = v.get(1, 0);
		final double v20 = v.get(2, 0);
		final double v01 = v.get(0, 1);
		final double v11 = v.get(1, 1);
		final double v21 = v.get(2, 1);
		final double v02 = v.get(0, 2);
		final double v12 = v.get(1, 2);
		final double v22 = v.get(2, 2);
		final double v03 = v.get(0, 3);
		final double v13 = v.get(1, 3);
		final double v23 = v.get(2, 3);

		RMat4 m2 = new RMat4();
		m2.mat[0] = t00 * v00 + t01 * v10 + t02 * v20;
		m2.mat[1] = t10 * v00 + t11 * v10 + t12 * v20;
		m2.mat[2] = t20 * v00 + t21 * v10 + t22 * v20;

		m2.mat[4] = t00 * v01 + t01 * v11 + t02 * v21;
		m2.mat[5] = t10 * v01 + t11 * v11 + t12 * v21;
		m2.mat[6] = t20 * v01 + t21 * v11 + t22 * v21;

		m2.mat[8] = t00 * v02 + t01 * v12 + t02 * v22;
		m2.mat[9] = t10 * v02 + t11 * v12 + t12 * v22;
		m2.mat[10] = t20 * v02 + t21 * v12 + t22 * v22;

		m2.mat[12] = t00 * v03 + t01 * v13 + t02 * v23 + t03;
		m2.mat[13] = t10 * v03 + t11 * v13 + t12 * v23 + t13;
		m2.mat[14] = t20 * v03 + t21 * v13 + t22 * v23 + t23;

		m2.mat[15] = 1;
		return m2;
	}

}
