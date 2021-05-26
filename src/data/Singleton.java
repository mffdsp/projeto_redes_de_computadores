package data;
public class Singleton {
	
	private static FinalReport finalreport;
	
	public static FinalReport getInstance(int id) {
		if(finalreport == null) {
			finalreport = new FinalReport(id);
		} 
		return finalreport;
	}

}
