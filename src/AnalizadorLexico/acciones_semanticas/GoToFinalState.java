package AnalizadorLexico.acciones_semanticas;

import AnalizadorLexico.States;
import AnalizadorSintactico.Parser;
import Utils.SourceCode;
import Utils.SymbolTable;

public class GoToFinalState extends AccionSemantica{
    private States matrix;

    public GoToFinalState(States matrix){
        this.matrix = matrix;
    }
    @Override
    public void execute(char c) {
        matrix.goToLastState();
    }
}
