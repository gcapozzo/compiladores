package AnalizadorLexico.acciones_semanticas;
import AnalizadorLexico.States;
import Utils.SourceCode;
import Utils.SymbolTable;
import AnalizadorSintactico.Parser;

public class GeneratorInteger extends GeneratorNumbers {
    //Accion Semantica 4: Reconociendo integers
    private final int MIN = -32768;
    private final int MAX = 32767;

    public GeneratorInteger(SymbolTable tSymbol, SourceCode cFuente, States matrix) {
        super(tSymbol, cFuente, matrix);
    }

    @Override
    public boolean isInRange(String sNumber) {
        int iNumber = bufferToInteger(sNumber);
        //Por ahora como no entiende si es negativo o positivo, comparo solo con los positivos
        return ( (iNumber >= MIN && iNumber <= MAX) );
    }

    @Override
    public void addNewTokenByType() {
        tSymbol.addToken(getCurrentBuffer(),Parser.CONST_INT);

    }

    private int bufferToInteger(String sNumber){
        return Integer.parseInt(sNumber);
    }



}
