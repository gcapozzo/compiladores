package Utils;



import java.util.ArrayList;

public class SourceCode {
    private int index = 0;
    private int lines = 1;
    private ErrorLog log;
    private final ArrayList<Character> code;

    public SourceCode(ArrayList<Character> code, ErrorLog log){
        this.code = code;
        this.log = log;
    }

    public void addWarning(String text){
        log.addWarning(text);
    }
    public void addError(String text){
        log.addError(text);
    }
    public boolean eof(){
        return (index == code.size()-1);
    }

    public void increaseIndex(){
        if (eof()) return;
        index++;
    }
    public void increaseLine(){
        this.lines++;
    }
    public int getCurrentLine(){
        return this.lines;
    }

    public void decreaseIndex(){
        if (index == 0) return;
        index--;
    }

    public char getNextChar(){

        return code.get(this.index);
    }

    public String getCode(){
        StringBuilder codigo = null;
        for(Character c: this.code) {
            codigo.append(c);
        }
        return codigo.toString();

    }

}
