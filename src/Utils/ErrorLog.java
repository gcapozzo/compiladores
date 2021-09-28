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
        if(warnings.isEmpty())
            System.out.println("No se han encontrado warnings");
        else
        for (String warning :warnings){
            System.out.println(warning);
        }
    }
    public void printErrors(){
        if(errors.isEmpty())
            System.out.println("No se han encontrado errores");
        else
            for(String error :errors){
                System.out.println(error);
            }
    }

}
