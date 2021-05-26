package data;
import java.util.ArrayList;
import java.util.Date;

public class FinalReport {
	String[] values = new String[99999];
	
	public FinalReport(int id) {
		for(int i= 0; i < values.length; i += 1){
			values[i] = "*****Nota fiscal*****\nPedido realizado em: " +
					new Date().toString().substring(11, 20) + "\n";
		}

	}
	
	public void add(String str, int id) {
		values[id] = values[id] + "\n" +
		"------------------------------------------------------\n" + 
		"ITEM | DESCRICAO | QUANTIDADE | VL. UNIT | VALOR (R$) \n" + 
		str + "\n";
	}

	public void addTotal(String str, int id) {
		values[id] = values[id] + str;
	}
	public String returnReport(int id) {
		return this.values[id];
	}

}
