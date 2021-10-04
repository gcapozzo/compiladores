package AnalizadorLexico.acciones_semanticas;
import AnalizadorLexico.States;
import Utils.SourceCode;
import Utils.SymbolTable;

public abstract class GeneratorNumbers extends AccionSemantica{
    protected SymbolTable tSymbol;
    private SourceCode cFuente;
    protected States stateMatrix;

    public GeneratorNumbers(SymbolTable tSymbol, SourceCode cFuente, States matrix){
        this.tSymbol = tSymbol;
        this.cFuente = cFuente;
        this.stateMatrix = matrix;
    }

    @Override
    public void execute(char c) {
        cFuente.decreaseIndex();
        if(!tSymbol.isInTable(getCurrentBuffer()))
            addNewTokenByType();
        stateMatrix.setTokenToLexic(tSymbol.getTokenID(this.getCurrentBuffer()),this.getCurrentBuffer());

    }
    public abstract void addNewTokenByType();




}
