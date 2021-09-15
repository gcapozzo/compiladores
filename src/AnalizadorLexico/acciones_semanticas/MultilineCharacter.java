package AnalizadorLexico.acciones_semanticas;

import AnalizadorLexico.Utils.SourceCode;
import AnalizadorLexico.Utils.SymbolTable;

public class MultilineCharacter extends AccionSemantica{
    private SourceCode cFuente;
    private SymbolTable sTable;
    public MultilineCharacter(SourceCode cFuente, SymbolTable sTable){
        this.cFuente = cFuente ;
        this.sTable = sTable;
    }
    @Override
    public void execute(char c) {
        if(c == '\n'){
            increaseLineCounter();
            //upss.. hubo un errorsito papi
        }
        else{
            sTable.addToken(getCurrentBuffer(),sTable.CONST_STR);
        }
        cFuente.increaseIndex();
    }
}
