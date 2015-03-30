package math.vecmat;

import java.util.Arrays;

class RVecN extends VecN {

	public final double[] vec;

	RVecN(int size, double fill) {
		vec = new double[size];
		Arrays.fill(vec, fill);
	}

	RVecN(double... data) {
		vec = data;
	}

	@Override
	public int dim() {
		return vec.length;
	}

	@Override
	public double get(int i) {
		return vec[i];
	}

	@Override
	public void set(int i, double v) {
		vec[i] = v;
	}

	@Override
	public VecN clone() {
		double[] data = new double[vec.length];
		System.arraycopy(vec, 0, data, 0, vec.length);
		return new RVecN(data);
	}

}
