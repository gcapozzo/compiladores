package AnalizadorLexico.acciones_semanticas;

import AnalizadorLexico.States;
import Utils.SourceCode;

public class Reparadora extends AccionSemantica{
    private SourceCode cFuente;

    public Reparadora(SourceCode cFuente){
        this.cFuente = cFuente;

    }
    @Override
    public void execute(char c) {
        cFuente.addWarning("Se ha intentado leer un + y no se ha encontrado por lo tanto se ha añadido");
    }

}
