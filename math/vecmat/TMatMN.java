package math.vecmat;

class TMatMN extends MatMN {

	private MatMN m;
	
	TMatMN(MatMN m){
		this.m = m;
	}
	
	@Override
	public int n() {
		return m.m();
	}

	@Override
	public int m() {
		return m.n();
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
	public MatMN transpose() {
		return m;
	}

	
}
