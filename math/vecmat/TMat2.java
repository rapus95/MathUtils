package math.vecmat;

class TMat2 extends Mat2 {

	private Mat2 m;
	
	TMat2(Mat2 m){
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
	public Mat2 transpose() {
		return m;
	}
	
}
