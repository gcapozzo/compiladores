package AnalizadorLexico.acciones_semanticas;

import AnalizadorLexico.Utils.SourceCode;

public class NewLine extends  AccionSemantica{
    SourceCode cFuente;
    public NewLine(SourceCode cFuente){
        this.cFuente = cFuente;
    }

    @Override
    public void execute(char c) {
        if (c == '\n')
            increaseLineCounter();
        cFuente.increaseIndex();
    }
}
