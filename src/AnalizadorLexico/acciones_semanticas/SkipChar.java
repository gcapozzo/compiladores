package AnalizadorLexico.acciones_semanticas;

import AnalizadorLexico.Utils.SourceCode;

public class SkipChar extends AccionSemantica{
    //Accion Semantica 16: Ignorar un char
    private SourceCode cFuente;

    public SkipChar(SourceCode cFuente){
        this.cFuente = cFuente;
    }

    @Override
    public void execute(char c) {
        cFuente.increaseIndex();
    }
}
