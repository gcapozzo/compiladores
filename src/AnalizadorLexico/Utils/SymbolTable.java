package AnalizadorLexico.Utils;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, Integer> table;
    public final int CONST_ID = 99;
    public final int CONST_FLOAT = 98;
    public final int CONST_INT = 97;
    public final int CONST_STR = 96;
    public final int LIMIT_PR = 100;

    //<lexema,ID(si es PR-Identif-Cte-etc..)>
    public SymbolTable() {
        table = new HashMap<>();
        inicPR();

    }
    private void inicPR(){
        //poner en el hash las PR
    }
    public boolean isInTable(String lexeme){
        return table.containsKey(lexeme);
    }

    public boolean isPR(String lexeme){
        return (this.getTokenID(lexeme) < LIMIT_PR);
    }
    public int getTokenID(String lexeme){
        return table.get(lexeme);
    }

    public int getTokenType(String lexeme){
        //Un case porque aca pueden ser muchas cositas xdlol nv perro
        return 1;
    }

    public void addToken(String lexeme, int tokenID){
        table.putIfAbsent(lexeme,tokenID);
    }

    @Override
    public String toString() {
        return table.toString();
    }
}
