package AnalizadorLexico.acciones_semanticas;

import AnalizadorLexico.States;
import AnalizadorLexico.Utils.SourceCode;
import AnalizadorLexico.Utils.SymbolTable;

public abstract class GeneratorNumbers extends AccionSemantica{
    private SymbolTable tSymbol;
    private SourceCode cFuente;
    private States stateMatrix;

    public GeneratorNumbers(SymbolTable tSymbol, SourceCode cFuente, States matrix){
        this.tSymbol = tSymbol;
        this.cFuente = cFuente;
        this.stateMatrix = matrix;
    }

    @Override
    public void execute(char c) {
        cFuente.decreaseIndex();
        if(isInRange(getCurrentBuffer())){
            if(tSymbol.isInTable(getCurrentBuffer()))
                stateMatrix.setTokenToLexic(tSymbol.getTokenID(getCurrentBuffer()),getCurrentBuffer());
            else {
                addNewTokenByType();
            }
        }
        else{
            //informar que esta fuera de rango y solo dios sabe que hacer aca
        }
    }
    public abstract boolean isInRange(String sNumber);
    public abstract void addNewTokenByType();

    public SymbolTable getTSymbol() {
        return tSymbol;
    }
    public SourceCode getCFuente() {
        return cFuente;
    }
    public States getStateMatrix() {
        return stateMatrix;
    }


}
