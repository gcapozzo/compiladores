package AnalizadorLexico.acciones_semanticas;
import AnalizadorLexico.Utils.SymbolTable;
import AnalizadorLexico.Utils.SourceCode;
import AnalizadorLexico.States;

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
        truncString(LIMITE_STRING);
        //INFORMAR WARNING POR RECORTAR STRING SI ES QUE RECORTE O NO LA RE PUTA Q TE PARIO
        if(tSymbol.isInTable(this.getCurrentBuffer())){
            if(tSymbol.isPR(this.getCurrentBuffer())){
                stateMatrix.setTokenToLexic(tSymbol.getTokenID(this.getCurrentBuffer()),"");
            }
            else{
                stateMatrix.setTokenToLexic(tSymbol.getTokenID(this.getCurrentBuffer()),
                                            this.getCurrentBuffer());
            }
        }
        else {
            tSymbol.addToken(this.getCurrentBuffer(), tSymbol.CONST_ID);
            stateMatrix.setTokenToLexic(tSymbol.CONST_ID, this.getCurrentBuffer());
        }

    }
}
