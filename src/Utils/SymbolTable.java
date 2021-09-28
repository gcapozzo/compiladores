package Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import AnalizadorSintactico.*;


public class SymbolTable {
    private Map<String, Integer> table;
    private final ArrayList<String> pReservadas;

    public SymbolTable() {
        table = new HashMap<>();
        pReservadas = new ArrayList<String>();
        inicPR();
    }
    public void addToken(String lexeme, int tokenID){
        table.putIfAbsent(lexeme,tokenID);
    }

    private void inicPR(){
        pReservadas.add("BEGIN");
        pReservadas.add("BREAK");
        pReservadas.add("DO");
        pReservadas.add("ELSE");
        pReservadas.add("END");
        pReservadas.add("ENDIF");
        pReservadas.add("FUNC");
        pReservadas.add("IF");
        pReservadas.add("INT");
        pReservadas.add("PRINT");
        pReservadas.add("POST");
        pReservadas.add("PROGRAM");
        pReservadas.add("RETURN");
        pReservadas.add("SINGLE");
        pReservadas.add("STRING");
        pReservadas.add("THEN");
        pReservadas.add("TYPEDEF");
        pReservadas.add("WHILE");
        pReservadas.add(":=");
        pReservadas.add("<=");
        pReservadas.add(">=");
        pReservadas.add("==");
        pReservadas.add("&&");
        pReservadas.add("||");
    }
    public boolean isInTable(String lexeme){
        return (table.containsKey(lexeme) || isPR(lexeme));
    }

    public boolean isPR(String lexeme){
        return (pReservadas.contains(lexeme));
    }

    public int getTokenID(String lexeme){
        if (this.isPR(lexeme))
            return getTokenPR(lexeme);
        return table.get(lexeme);
    }

    private int getTokenPR(String lexeme){
        switch (lexeme){
            case("BEGIN"): return Parser.BEGIN;
            case("BREAK"): return Parser.BREAK;
            case("DO"): return Parser.DO;
            case("ELSE"): return Parser.ELSE;
            case("END"): return Parser.END;
            case("ENDIF"): return Parser.ENDIF;
            case("FUNC"): return Parser.FUNC;
            case("IF"): return Parser.IF;
            case("INT"): return Parser.INT;
            case("PRINT"): return Parser.PRINT;
            case("POST"): return Parser.POST;
            case("PROGRAM"): return Parser.PROGRAM;
            case("RETURN"): return Parser.RETURN;
            case("SINGLE"): return Parser.SINGLE;
            case("STRING"): return Parser.STRING;
            case("THEN"): return Parser.THEN;
            case("TYPEDEF") :return Parser.TYPEDEF;
            case("WHILE"): return Parser.WHILE;
            case(":="): return Parser.ASIGN;
            case("<="): return Parser.MENOR_IGUAL;
            case(">="): return Parser.MAYOR_IGUAL;
            case("=="): return Parser.IGUAL;
            case("&&"): return Parser.AND;
            case("||"): return Parser.OR;
            default: return Parser.YYERRCODE;
        }
    }

    @Override
    public String toString() {
        return table.toString();
    }

}
