package math.vecmat;

class TMatN extends MatN {

	private MatN m;
	
	TMatN(MatN m){
		this.m = m;
	}
	
	@Override
	public int size() {
		return m.size();
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
	public MatN transpose() {
		return m;
	}
	
}
