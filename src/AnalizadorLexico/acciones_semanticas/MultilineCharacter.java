package AnalizadorLexico.acciones_semanticas;

import AnalizadorLexico.States;
import Utils.SourceCode;
import Utils.SymbolTable;
import AnalizadorSintactico.Parser;

public class MultilineCharacter extends AccionSemantica{
    private SourceCode cFuente;
    private SymbolTable sTable;
    private States matrix;
    public MultilineCharacter(SourceCode cFuente, SymbolTable sTable, States matrix){
        this.cFuente = cFuente ;
        this.sTable = sTable;
        this.matrix = matrix;
    }
    @Override
    public void execute(char c) {
        switch(c){
            case('\n'):{
                cFuente.increaseLine();
                break;
            }
            case('%'): {
                sTable.addToken(getCurrentBuffer(), Parser.CONST_STR);
                matrix.setTokenToLexic(sTable.getTokenID(getCurrentBuffer()),getCurrentBuffer());
                inicString();
                break;
            }
            default:cFuente.addError("Error al generar un string, se esperaba un '%' o salto de linea");
        }

    }
}
