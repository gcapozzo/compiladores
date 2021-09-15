package AnalizadorLexico.Utils;


public class SourceCode {
    private int index = 0;
    private final String code;

    public SourceCode(String code){
        this.code = code;
    }

    public boolean eof(){
        return index == code.length();
    }

    public void increaseIndex(){
        if (eof()) return;
        index++;
    }

    public void decreaseIndex(){
        if (index == 0) return;
        index--;
    }

    public char getNextChar(){
        return code.charAt(index);
    }

    public String getCode(){ return this.code;}

}
