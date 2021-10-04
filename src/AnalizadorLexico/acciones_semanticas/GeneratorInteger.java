package AnalizadorLexico.acciones_semanticas;
import AnalizadorLexico.States;
import Utils.SourceCode;
import Utils.SymbolTable;
import AnalizadorSintactico.Parser;

public class GeneratorInteger extends GeneratorNumbers {
    //Accion Semantica 4: Reconociendo integers
    public final static int MIN = -32768;
    public final static int MAX = 32767;

    public GeneratorInteger(SymbolTable tSymbol, SourceCode cFuente, States matrix) {
        super(tSymbol, cFuente, matrix);
    }


    public boolean isInRange(String sNumber) {
        int iNumber = bufferToInteger(sNumber);
        return ( iNumber <= MAX+1); //re chequear en la generacion de codigo
    }

    @Override
    public void addNewTokenByType() {
        tSymbol.addToken(getCurrentBuffer(),Parser.CONST_INT);

    }

    private int bufferToInteger(String sNumber) throws NumberFormatException{
        return Integer.parseInt(sNumber);
    }



}
