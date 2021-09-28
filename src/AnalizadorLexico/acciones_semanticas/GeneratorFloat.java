package AnalizadorLexico.acciones_semanticas;

import AnalizadorLexico.States;
import Utils.SourceCode;
import Utils.SymbolTable;
import AnalizadorSintactico.Parser;

public class GeneratorFloat extends GeneratorNumbers {
    //Accion Semantica 4: Reconociendo flotantes
    public final static double MIN_POSITIV = 1.17549435e-38d;
    public final static double MAX_POSITIV = 3.40282347e+38d;
    public final static double MIN_NEGATIVE = -3.40282347e+38d;
    public final static double MAX_NEGATIVE = -1.17549435e-38d;
    public final static double CERO = 0.0d;

    public GeneratorFloat(SymbolTable tSymbol, SourceCode cFuente, States matrix) {
        super(tSymbol, cFuente, matrix);
    }

    @Override
    public boolean isInRange(String sNumber) {
        double fNumber = bufferToDouble(sNumber);
        //Por ahora como no entiende si es negativo o positivo, comparo solo con los positivos
        return ( (fNumber >= MIN_POSITIV && fNumber <= MAX_POSITIV ) || (fNumber == CERO));

    }

    @Override
    public void addNewTokenByType() {
        tSymbol.addToken(getCurrentBuffer(),Parser.CONST_SINGLE);

    }

    private double bufferToDouble(String buffer) throws NumberFormatException {
        if(buffer.charAt(buffer.length()-1) == 'e')
            buffer = buffer.concat("1");
        buffer = buffer.concat("d");
        return Double.parseDouble(buffer);
    }



}
