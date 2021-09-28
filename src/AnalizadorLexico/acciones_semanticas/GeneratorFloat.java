package AnalizadorLexico.acciones_semanticas;

import AnalizadorLexico.States;
import Utils.SourceCode;
import Utils.SymbolTable;
import AnalizadorSintactico.Parser;

public class GeneratorFloat extends GeneratorNumbers {
    //Accion Semantica 4: Reconociendo flotantes
    private final float MIN_POSITIV = 1.17549435e-38f;
    private final float MAX_POSITIV = 3.40282347e+38f;
    private final float MIN_NEGATIVE = -3.40282347e+38f;
    private final float MAX_NEGATIVE = -1.17549435e-38f;
    private final float CERO = 0.0f;

    public GeneratorFloat(SymbolTable tSymbol, SourceCode cFuente, States matrix) {
        super(tSymbol, cFuente, matrix);
    }

    @Override
    public boolean isInRange(String sNumber) {
        float fNumber = bufferToFloat(sNumber);
        //Por ahora como no entiende si es negativo o positivo, comparo solo con los positivos
        return ( (fNumber >= MIN_POSITIV && fNumber <= MAX_POSITIV ) || (fNumber == CERO));

    }

    @Override
    public void addNewTokenByType() {
        tSymbol.addToken(getCurrentBuffer(),Parser.CONST_SINGLE);

    }

    private float bufferToFloat(String buffer){
        String numberWithE = buffer.concat("f");
        return Float.parseFloat(numberWithE);
    }



}
