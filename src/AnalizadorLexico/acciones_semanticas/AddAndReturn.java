package AnalizadorLexico.acciones_semanticas;

import AnalizadorLexico.States;
import AnalizadorLexico.Utils.SourceCode;
import AnalizadorLexico.Utils.SymbolTable;

public class AddAndReturn extends AccionSemantica{
    private SymbolTable tSymbol;
    private States matrix;
    private SourceCode sCode;
    public AddAndReturn(States matrix, SymbolTable tSymbol,SourceCode sCode){
        this.matrix = matrix;
        this.tSymbol = tSymbol;
        this.sCode = sCode;
    }
    @Override
    public void execute(char c) {
        concatenateChar(c);
        sCode.increaseIndex();
        matrix.setTokenToLexic(tSymbol.getTokenID(getCurrentBuffer()),"" );
    }
}
