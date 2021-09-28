package AnalizadorLexico.acciones_semanticas;

import AnalizadorLexico.States;
import Utils.SourceCode;
import Utils.SymbolTable;

public class AddAndReturn extends AccionSemantica{
    private SymbolTable tSymbol;
    private States matrix;
    public AddAndReturn(States matrix, SymbolTable tSymbol){
        this.matrix = matrix;
        this.tSymbol = tSymbol;
    }
    @Override
    public void execute(char c) {
        concatenateChar(c);
        if(tSymbol.isPR(this.getCurrentBuffer()))
            matrix.setTokenToLexic(tSymbol.getTokenID(getCurrentBuffer()), this.getCurrentBuffer());
        else
            matrix.setTokenToLexic((int)(getCurrentBuffer().charAt(0)), this.getCurrentBuffer());
    }
}
