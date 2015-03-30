package math.vecmat;

class TMat4 extends Mat4 {

	private Mat4 m;
	
	TMat4(Mat4 m){
		this.m = m;
	}
	
	@Override
	public double get(int m, int n) {
		return this.m.get(n, m);
	}

	@Override
	public void set(int m, int n, double v) {
		this.m.set(n, m, v);
	}

	@Override
	public Mat4 transpose() {
		return m;
	}
	
}
