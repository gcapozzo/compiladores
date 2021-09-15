package AnalizadorLexico.acciones_semanticas;

public abstract class AccionSemantica {
    private static final StringBuilder sTemporal = new StringBuilder();
    private int lines = 0;

    public void inicString(){
        sTemporal.delete(0,sTemporal.length());
    }
    public void increaseLineCounter(){
        lines++;
    }
    public void concatenateChar(char nuevoChar){
        sTemporal.append(nuevoChar);
    }

    public void truncString(int limite){
        if(sTemporal.length() > limite)
            sTemporal.delete(limite,sTemporal.length());

        //Elimina subString que esta entre dos indices. Desde limite hasta la long del String
    }

    public String getCurrentBuffer(){
        return sTemporal.toString();
    }
    public abstract void execute(char c);


}
