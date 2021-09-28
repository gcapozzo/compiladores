package AnalizadorLexico.acciones_semanticas;
import Utils.SymbolTable;
import Utils.SourceCode;
import AnalizadorLexico.States;
import AnalizadorSintactico.Parser;

public class GeneratorIdentifier extends AccionSemantica{
    //Accion Semantica 3: Obteniendo un identificador
    private SymbolTable tSymbol;
    private SourceCode cFuente;
    private States stateMatrix;
    private final int  LIMITE_STRING = 22;

    public GeneratorIdentifier(SymbolTable tSymbol, SourceCode cFuente, States matrix){
        this.tSymbol = tSymbol;
        this.cFuente = cFuente;
        this.stateMatrix = matrix;
    }

    @Override
    public void execute(char c) {
        cFuente.decreaseIndex();
        if(truncString(LIMITE_STRING)){
            cFuente.addWarning("Se ha truncado el identificador porque supera los " + LIMITE_STRING + " caracteres");
        }

        if(!tSymbol.isInTable(this.getCurrentBuffer())){
            tSymbol.addToken(this.getCurrentBuffer(), Parser.ID);
        }
        stateMatrix.setTokenToLexic(tSymbol.getTokenID(this.getCurrentBuffer()),this.getCurrentBuffer());
    }
}
