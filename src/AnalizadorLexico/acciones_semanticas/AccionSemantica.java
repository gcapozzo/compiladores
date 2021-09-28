package AnalizadorLexico.acciones_semanticas;

import jdk.swing.interop.SwingInterOpUtils;

public abstract class AccionSemantica {
    private static final StringBuilder sTemporal = new StringBuilder();


    public void inicString(){
        sTemporal.delete(0,sTemporal.length());
    }

    public void concatenateChar(char nuevoChar){
        sTemporal.append(nuevoChar);
    }

    public boolean truncString(int limite){
        if(sTemporal.length() > limite) {
            sTemporal.delete(limite, sTemporal.length());
            return true;
        }
        return false;
        //Elimina subString que esta entre dos indices. Desde limite hasta la long del String
    }

    public String getCurrentBuffer(){
        return sTemporal.toString();
    }
    public abstract void execute(char c);


}
