package Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ErrorLog {
    private SourceCode cFuente = null;
    private ArrayList<String> errors = new ArrayList<>();
    private ArrayList<String> warnings = new ArrayList<>();

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

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public List<String> getWarnings() {
        return Collections.unmodifiableList(warnings);
    }
}
