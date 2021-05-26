package data;
public class Delivery {
    
    private String sabor;
    private double preco;
    private String tipo;
    private double total = 0;

    private int qnt[] = {0,0,0,0,0};
    private double precos[] = {29.99,25.99,31.99,35.99,42.99};
    private double precosParciais[] = {0,0,0,0,0};

    public String getTipo() {
    	return this.tipo;
    }
    public String getSabor(){
        return this.sabor;
    }

    public double getPreco(){
        return this.preco;
    }
    public void setSabor(String sabor){
        this.sabor = sabor;
    }

    public void setPreco(int sabor, int qnt){
        this.precosParciais[sabor] += (this.precos[sabor] * qnt);
    }
    public void setQuantidade(int qnt, String str){
        int index = 0;
        if(str.equals("calabresa")){
            index = 0;
        }
        if(str.equals("frango catupiri")){
            index = 1;
        }
        if(str.equals("portuguesa")){
            index = 2;
        }
        if(str.equals("carne do sol")){
            index = 3;
        }
        if(str.equals("camarao")){
            index = 4;
        }
        this.qnt[index] += qnt;
        setPreco(index, this.qnt[index]);
    }

    public void resetPrecoParcial(String str){
        int index = 0;
        if(str.equals("calabresa")){
            index = 0;
        }
        if(str.equals("frango catupiri")){
            index = 1;
        }
        if(str.equals("portuguesa")){
            index = 2;
        }
        if(str.equals("carne do sol")){
            index = 3;
        }
        if(str.equals("camarao")){
            index = 4;
        }
        this.precosParciais[index] = 0;
    }

    public void setTipo(String str) {

        this.tipo = str;

        if(str.equals("pequeno")){
            this.preco *= 0.6;
        }
        if(str.equals("medio")){
            this.preco *= 0.8;
        }
    }
    public double getTotal(){
        double total = 0;
        for (int i = 0; i < 5; i++){
            if(this.qnt[i] != 0) {
                total += this.precosParciais[i];
            }
        }
        return total;
    }

    public String toString(String str) {
        int index = 0;
        if(str.equals("calabresa")){
            index = 0;
        }
        if(str.equals("frango catupiri")){
            index = 1;
        }
        if(str.equals("portuguesa")){
            index = 2;
        }
        if(str.equals("carne do sol")){
            index = 3;
        }
        if(str.equals("camarao")){
            index = 4;
        }
		return this.sabor + " | "+ this.tipo + " | " + this.qnt[index] + " | " + this.precos[index] + " | " + this.precos[index]*this.qnt[index];
  
    }




}
