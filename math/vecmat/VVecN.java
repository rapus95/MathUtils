package math.vecmat;

class VVecN extends VecN {

	private Vec<?> v;

	private int[] indices;

	VVecN(Vec<?> v, int[] indices) {
		this.v = v;
		this.indices = indices;
		int dim = v.dim();
		for (int i = 0; i < indices.length; i++) {
			if (indices[i] < 0 || indices[i] >= dim) {
				throw new IllegalArgumentException();
			}
		}
	}

	@Override
	public int dim() {
		return indices.length;
	}

	@Override
	public double get(int i) {
		return v.get(indices[i]);
	}

	@Override
	public void set(int i, double x) {
		v.set(indices[i], x);
	}

}
