package math.vecmat;

class TMat3 extends Mat3 {

	private Mat3 m;
	
	TMat3(Mat3 m){
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
	public Mat3 transpose() {
		return m;
	}
	
}
