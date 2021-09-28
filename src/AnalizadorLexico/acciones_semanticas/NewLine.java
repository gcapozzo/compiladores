package AnalizadorLexico.acciones_semanticas;

import Utils.SourceCode;

public class NewLine extends  AccionSemantica{
    private SourceCode cFuente;
    public NewLine(SourceCode cFuente){
        this.cFuente = cFuente;
    }

    @Override
    public void execute(char c) {
        switch (c){
            case '\n': {
                cFuente.increaseLine();
            }
            case '\t': case ' ': break;
            default: {
                cFuente.addWarning("Se ha encontrado y omitido el caracter " + c);
                break;
            }
        }
    }
}
