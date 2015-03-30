package math.vecmat;

class AVecN extends VecN {

	private double[] array;

	private int[] indices;

	AVecN(double[] array, int[] indices) {
		this.array = array;
		this.indices = indices;
		for (int i = 0; i < indices.length; i++) {
			if (indices[i] < 0 || indices[i] >= array.length) {
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
		return array[indices[i]];
	}

	@Override
	public void set(int i, double x) {
		array[indices[i]] = x;
	}

}
