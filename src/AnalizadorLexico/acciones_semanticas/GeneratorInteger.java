package AnalizadorLexico.acciones_semanticas;
import AnalizadorLexico.States;
import AnalizadorLexico.Utils.SourceCode;
import AnalizadorLexico.Utils.SymbolTable;

public class GeneratorInteger extends GeneratorNumbers {
    //Accion Semantica 4: Reconociendo integers
    private final int MIN = -32768;
    private final int MAX = 32767;

    public GeneratorInteger(SymbolTable tSymbol, SourceCode cFuente, States matrix){
        super(tSymbol,cFuente,matrix);
    }

    @Override
    public boolean isInRange(String sNumber) {
        int iNumber = bufferToInteger(sNumber);
        //Por ahora como no entiende si es negativo o positivo, comparo solo con los positivos
        return ( (iNumber >= MIN && iNumber <= MAX) );
    }

    @Override
    public void addNewTokenByType() {
        SymbolTable sTable = getTSymbol();
        sTable.addToken(getCurrentBuffer(),sTable.CONST_INT);
        States matrix = getStateMatrix();
        matrix.setTokenToLexic(sTable.CONST_INT, getCurrentBuffer());
    }

    public int bufferToInteger(String sNumber){
        return Integer.parseInt(sNumber);
    }



}
