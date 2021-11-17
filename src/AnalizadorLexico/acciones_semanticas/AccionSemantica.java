package AnalizadorLexico.acciones_semanticas;

import AnalizadorLexico.States;
import AnalizadorSintactico.Parser;
import Utils.SourceCode;
import Utils.SymbolTable;

public class AccionSemantica{
    private static final StringBuilder sTemporal = new StringBuilder();
    private SourceCode cFuente;
    private SymbolTable tSymbol;
    private States stateMatrix;

    public AccionSemantica(){}

    public AccionSemantica(States matrix,SourceCode codeFuente, SymbolTable sTable){
        this.stateMatrix = matrix;
        this.cFuente = codeFuente;
        this.tSymbol = sTable;
    }

    public void inicString(){
        sTemporal.delete(0,sTemporal.length());
    }

    public void concatenateChar(char nuevoChar){
        sTemporal.append(nuevoChar);
    }

    public boolean truncString(int limite){
        if(sTemporal.length() > limite) {
            sTemporal.delete(limite, sTemporal.length());
            return true;
        }
        return false;
    }
    public String getCurrentBuffer(){
        return sTemporal.toString();
    }

    public void execute(char c){return;};


    public class AddChar extends AccionSemantica{

        public AddChar() {
        }
        public void execute(char c){
            concatenateChar(c);
        }
    }

    public class AddCharBis extends AccionSemantica{

        public AddCharBis() {
        }

        public void execute(char c) {
            concatenateChar('e');
        }
    }

    public class GeneratorFloat extends GeneratorNumbers{

        public final static double MIN_POSITIV = 1.17549435e-38d;
        public final static double MAX_POSITIV = 3.40282347e+38d;
        public final static double MIN_NEGATIVE = -3.40282347e+38d;
        public final static double MAX_NEGATIVE = -1.17549435e-38d;
        public final static double CERO = 0.0d;

        @Override
        public void addNewTokenByType() {
            tSymbol.addToken(getCurrentBuffer(),Parser.CONST_SINGLE);
        }
    }
    public class GeneratorInteger extends GeneratorNumbers{

        public final static int MIN = -32768;
        public final static int MAX = 32767;

        @Override
        public void addNewTokenByType() {
            tSymbol.addToken(getCurrentBuffer(),Parser.CONST_INT);
        }
    }
    public abstract class GeneratorNumbers extends AccionSemantica{

        public GeneratorNumbers() {
        }

        public void execute(char c) {
            cFuente.decreaseIndex();
            if(!tSymbol.isInTable(getCurrentBuffer()))
                addNewTokenByType();
            stateMatrix.setTokenToLexic(tSymbol.getTokenID(getCurrentBuffer()),getCurrentBuffer());
        }
        public abstract void addNewTokenByType();
    }

    public class GeneratorIdentifier extends AccionSemantica{

        private static final int LIMITE_STRING = 22;

        public GeneratorIdentifier() {
        }

        public void execute(char c){
            if(truncString(LIMITE_STRING)){
                cFuente.addWarning("Se ha truncado el identificador porque supera los " + LIMITE_STRING + " caracteres" );
            }

            if(!tSymbol.isInTable(getCurrentBuffer())){
                tSymbol.addToken(getCurrentBuffer(), Parser.ID);
            }
            stateMatrix.setTokenToLexic(tSymbol.getTokenID(getCurrentBuffer()), getCurrentBuffer());
        }
    }


    public class GoToFinalState extends AccionSemantica{

        public GoToFinalState() {
        }

        public void execute(char c) {
            stateMatrix.goToLastState();
            stateMatrix.setTokenToLexic(0,"$");
        }
    }

    public class InicBuffer extends AccionSemantica{

        public InicBuffer() {
        }

        //Accion Semantica 1: Inicializar buffer y agg el caracter leido
        public void execute(char c) {
            inicString();
            if(c != '%')
                concatenateChar(c);
        }
    }

    public class MultilineCharacter extends AccionSemantica{

        public MultilineCharacter() {
        }

        public void execute(char c) {
            switch(c){
                case('\n'):case '\r':{
                    cFuente.increaseLine();
                    break;
                }
                case('%'): {
                    tSymbol.addToken(getCurrentBuffer(), Parser.CONST_STR);
                    stateMatrix.setTokenToLexic(tSymbol.getTokenID(getCurrentBuffer()),getCurrentBuffer());
                    inicString();
                    break;
                }
                default: cFuente.addError("Error al generar un string, se esperaba un '%' o salto de linea");
            }

        }
    }

    public class NewLine extends AccionSemantica{

        public NewLine() {
        }

        public void execute(char c) {
            switch (c){
                case '\n':case '\r':{//\r es el enter para el salto de linea
                    cFuente.increaseLine();
                }
                case '\t': case ' ': case '\b': break;
                default: {
                    cFuente.addWarning("Se ha encontrado y omitido el caracter " + c);
                    break;
                }
            }
        }
    }

    public class Reparadora extends AccionSemantica{

        public Reparadora() {
        }

        public void execute(char c) {
            cFuente.addWarning("Se ha intentado leer un + y no se ha encontrado por lo tanto se ha añadido");
        }
    }
    public class ResetBuffer extends AccionSemantica{

        public ResetBuffer() {
        }

        public void execute(char c) {
            cFuente.decreaseIndex();
            inicString();
        }
    }
    public class TranslateToASCII extends AccionSemantica{

        public TranslateToASCII() {
        }

        public void execute(char c) {
            cFuente.decreaseIndex();
            stateMatrix.setTokenToLexic((int) getCurrentBuffer().charAt(0), getCurrentBuffer());
        }
    }

    public class AddAndReturn extends AccionSemantica{

        public AddAndReturn() {
        }

        public void execute(char c){
            concatenateChar(c);
            if(tSymbol.isPR(getCurrentBuffer()))
                stateMatrix.setTokenToLexic(tSymbol.getTokenID(getCurrentBuffer()), getCurrentBuffer());
            else{
                //es el arco del punto, los parentesis, etc.
                stateMatrix.setTokenToLexic((int)(getCurrentBuffer().charAt(0)), getCurrentBuffer());
            }
        }

    }

}