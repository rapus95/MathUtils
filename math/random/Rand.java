package math.random;

import java.util.Random;

public class Rand {

	private long seed;
	private Random rand;
	private Random childs;
	
	public Rand(long seed){
		this.seed = seed;
		this.rand = new Random(seed);
		this.childs = new Random(rand.nextLong());
	}
	
	public float rand(){
		return rand.nextFloat();
	}
	
	public Rand child(){
		return new Rand(this.childs.nextLong());
	}
	
	public long getSeed(){
		return seed;
	}
	
}
