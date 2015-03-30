package math.vecmat;

public abstract class VecN extends Vec<VecN> {

	@Override
	public double get(char c) {
		return get(getIndex(c));
	}

	@Override
	public void set(char c, double v) {
		set(getIndex(c), v);
	}

	@Override
	public int getIndex(char c) {
		switch (c) {
		case 'x':
		case 'r':
		case 's':
			return 0;
		case 'y':
		case 'g':
		case 't':
			return 1;
		case 'z':
		case 'b':
		case 'p':
			return 2;
		case 'w':
		case 'a':
		case 'q':
			return 3;
		}
		return -1;
	}

	@Override
	public VecN clone() {
		final int size = dim();
		double[] data = new double[size];
		for (int i = 0; i < size; i++) {
			data[i] = get(i);
		}
		return new RVecN(data);
	}

	@Override
	protected VecN _new() {
		return new RVecN(dim());
	}

}
