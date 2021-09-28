package AnalizadorLexico;

import Utils.*;

import java.util.ArrayList;


public class AnalizadorLexico {


    private final SourceCode cFuente;
    private final States stateMatrix;
    private int tokenObtenido;
    private String lexemaObtenido;
    private SymbolTable tSymbol;
    private ArrayList<String> generado = new ArrayList<>();


    public AnalizadorLexico(SourceCode cfuente,SymbolTable tSymbol){
        this.cFuente = cfuente;
        this.tSymbol = tSymbol;
        this.stateMatrix = new States(this, this.tSymbol, this.cFuente);
    }

    public int getCurrentLine(){return cFuente.getCurrentLine();}

    public void setTokenGenerado(int token, String lexema){
        this.tokenObtenido = token;
        this.lexemaObtenido = lexema;
        //System.out.println("Token: " + token);
        //System.out.println("Lexema: " + lexema);
    }

    public int generateToken(){
        while( !stateMatrix.isInFinalState()){
            if(cFuente.eof()) {
                stateMatrix.goToLastState();
            }
            else{
                stateMatrix.goToNextState(cFuente.getNextChar());
            }
            cFuente.increaseIndex();
        }
        stateMatrix.reset();
        return tokenObtenido;
    }

    public SourceCode getSourceCode(){
        return this.cFuente;
    }
    public SymbolTable getSymbolTable(){
        return this.tSymbol;
    }
    public String getLexemaObtenido(){return this.lexemaObtenido;}
    public int getTokenObtenido(){return this.tokenObtenido;}

    public void generated(String gen){
        String g = "Se ha generado la siguiente regla: " + gen + ". En la linea: " + cFuente.getCurrentLine();
        this.generado.add(g);
        //System.err.println(g);
    }

    public ArrayList<String> getGenerado() {
        return generado;
    }
}
