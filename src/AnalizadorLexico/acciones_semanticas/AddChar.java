package AnalizadorLexico.acciones_semanticas;

import Utils.SourceCode;

public class AddChar extends AccionSemantica{
    //Accion Semantica 2: Agrega un char al StringBuffer
    private SourceCode cFuente;

    @Override
    public void execute(char c) {
        concatenateChar(c);

    }
}
