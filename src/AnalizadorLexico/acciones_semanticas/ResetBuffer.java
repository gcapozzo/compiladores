package AnalizadorLexico.acciones_semanticas;

import Utils.SourceCode;

public class ResetBuffer extends AccionSemantica{
    //Accion Semantica 13: Eliminar el contenido del buffer
    private SourceCode cFuente;

    @Override
    public void execute(char c) {
        inicString();
    }
}
