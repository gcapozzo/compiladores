package Utils;

import java.util.ArrayList;

public class ErrorLog {
    private SourceCode cFuente = null;
    private ArrayList<String> errors = new ArrayList<String>();
    private ArrayList<String> warnings = new ArrayList<String>();

    public ErrorLog(){}

    public void setSourceCode(SourceCode cFuente){
        this.cFuente = cFuente;
    }
    public void addWarning(String text){
        this.warnings.add("WARNING -> Line " + this.cFuente.getCurrentLine() + ": " + text);
    }
    public void addError(String text){
        this.errors.add("ERROR -> Line " + this.cFuente.getCurrentLine() + ": " + text);
    }
    public void printWarning(){
        for (String warning :warnings){
            System.out.println(warning);
        }
    }
    public void printErrors(){
        for (String error :errors){
            System.out.println(error);
        }
    }

}
