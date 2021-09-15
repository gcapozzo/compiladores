package AnalizadorLexico;

import AnalizadorLexico.acciones_semanticas.AccionSemantica;

public class Celda {

    private int siguienteEstado;
    private AccionSemantica as;


    public Celda(int numero) {
        this.siguienteEstado = numero;
        //AGREGARLE LA AS ACA!!!!
        //this.as = as;
    }

    public int SiguienteEstado() {
        return siguienteEstado;
    }

    public void executeAS(char c) {
        as.execute(c);
    }
}
