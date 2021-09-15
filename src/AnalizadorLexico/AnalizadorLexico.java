package AnalizadorLexico;

import AnalizadorLexico.Utils.SourceCode;
import AnalizadorLexico.Utils.SymbolTable;
import AnalizadorLexico.acciones_semanticas.*;


public class AnalizadorLexico {
    /*
      este el rial main que mientras la maquina de estados no haya llegado al final
      va avanzando en la matriz y leyendo el codigo
    */

    private final SourceCode cFuente;
    private final States stateMatrix;
    private int tokenObtenido;
    private String lexemaObtenido;
    private SymbolTable tSymbol;

    public AnalizadorLexico(SourceCode cfuente){
        this.cFuente = cfuente;
        this.tSymbol = new SymbolTable();
        this.stateMatrix = new States(this, this.tSymbol);
    }

    public void setTokenGenerado(int token, String lexema){
        this.tokenObtenido = token;
        this.lexemaObtenido = lexema;
    }

    public int generateToken(){
        //este seria el yylex() ?????
        while( !stateMatrix.isInFinalState()){
            if(cFuente.eof()) stateMatrix.goToLastState();
            else{
                stateMatrix.goToNextState(cFuente.getNextChar());
                cFuente.increaseIndex();
            }
        }
        stateMatrix.reset();
        return tokenObtenido; //OnlyToken or pair<token,lexema> -> esto va pal sintactico
    }

    public SourceCode getSourceCode(){
        return this.cFuente;
    }
    public SymbolTable getSymbolTable(){
        return this.tSymbol;
    }

    public static void main(String[] args) {
    /*
        States states = new States();

        String identificador = "num = 1525$";

        int size = identificador.length();
        int state = 0;
        int i = 0;
        boolean condition = true;
        StringBuilder buffer = new StringBuilder();

        while (condition) {
            System.out.println("Estado actual: " + state + " con el caracter: " + identificador.charAt(i));
            state = states.getStates(state, identificador.charAt(i), buffer);
            if (state != -1) {
                i++;
            } else if (state == -2) {
                condition = false;
            } else {
                System.out.println("Se leyo todo el token correctamente");
                state = 0;
                buffer = new StringBuilder(); //se q creo un nuevo objeto y dejo el otro en memoria, pero no se si hay alguna forma de limpiar el StringBuilder
            }
        }

     */

        SourceCode codigo = new SourceCode("abc123-estoNoImportaNada");
        AnalizadorLexico aLexico = new AnalizadorLexico(codigo);
//        aLexico.generateToken();
        AccionSemantica a1 = new InicBuffer();
        AccionSemantica a2 = new AddChar();
        AccionSemantica a2bis = new AddCharBis();
        AccionSemantica a3 = new GeneratorFloat(aLexico.getSymbolTable(),aLexico.getSourceCode(),
                            aLexico.stateMatrix);
        a1.execute('1');
        a2.execute('.');
        a2.execute('2');
        a2.execute('2');
        a2bis.execute('S');
        a2.execute('3');
        a3.execute(' ');

        System.out.println(a1.getCurrentBuffer() + " len: " + a1.getCurrentBuffer().length());
        System.out.println(aLexico.getSymbolTable().toString());



    }

}
