package math.vecmat;

class VVec3 extends Vec3 {

	private Vec<?> v;

	private int i1;

	private int i2;

	private int i3;

	VVec3(Vec<?> v, int[] indices) {
		this.v = v;
		i1 = indices[0];
		i2 = indices[1];
		i3 = indices[2];
	}

	VVec3(Vec<?> v, int i1, int i2, int i3) {
		this.v = v;
		this.i1 = i1;
		this.i2 = i2;
		this.i3 = i3;
		int dim = v.dim();
		if (i1 < 0 || i2 < 0 || i3 < 0 || i1 >= dim || i2 >= dim || i3 >= dim) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public double x() {
		return v.get(i1);
	}

	@Override
	public double y() {
		return v.get(i2);
	}

	@Override
	public double z() {
		return v.get(i3);
	}

	@Override
	public void x(double x) {
		v.set(i1, x);
	}

	@Override
	public void y(double y) {
		v.set(i2, y);
	}

	@Override
	public void z(double z) {
		v.set(i3, z);
	}

}
