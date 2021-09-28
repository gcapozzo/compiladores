package AnalizadorLexico;

import AnalizadorLexico.acciones_semanticas.AccionSemantica;

public class Celda {

    private int siguienteEstado;
    private AccionSemantica as;


    public Celda(int numero, AccionSemantica as) {
        this.siguienteEstado = numero;
        this.as = as;
    }

    public int SiguienteEstado() {
        return siguienteEstado;
    }

    public void executeAS(char c) {
        as.execute(c);
    }
}
