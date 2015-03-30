package math.utils;

public class MathUtils {

	public static double absMin(double... params){
		return params[absMinIndex(params)];
	}
	
	public static int absMinIndex(double... params){
		if(params.length==0) return 0;
		if(params.length==1) return 1;
		int index = 0;
		double abs = Math.abs(params[0]), tmp;
		for(int i=1;i<params.length; i++){
			if((tmp=Math.abs(params[i]))<abs){
				abs = tmp;
				index = i;
			}
		}
		return index;
	}
	
	public static double absMax(double... params){
		return params[absMaxIndex(params)];
	}
	
	public static int absMaxIndex(double... params){
		if(params.length==0) return 0;
		if(params.length==1) return 1;
		int index = 0;
		double abs = Math.abs(params[0]), tmp;
		for(int i=1;i<params.length; i++){
			if((tmp=Math.abs(params[i]))>abs){
				abs = tmp;
				index = i;
			}
		}
		return index;
	}
}
