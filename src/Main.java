import AnalizadorLexico.*;
import AnalizadorSintactico.*;
import Utils.*;

import java.util.ArrayList;

public class Main {

    private static AnalizadorLexico lexico;
    private static ReaderFromFile reader;
    private static SourceCode sourceCode;
    private static Parser parser = new Parser();
    private static SymbolTable symbolTable = new SymbolTable();
    private static ErrorLog logs;

    public static void main(String[] args) throws Exception {
        run(args[0]);
    }

    private static void run(String path) throws Exception {
        logs = new ErrorLog();
        ArrayList<Character> code = reader.reader(path);
        sourceCode = new SourceCode(code,logs);
        logs.setSourceCode(sourceCode);
        lexico = new AnalizadorLexico(sourceCode,symbolTable);
        parser = new Parser(lexico, logs);
        parser.run();
        ArrayList<String> gen = lexico.getGenerado();
        for (String s: gen)
            System.out.println(s);


        //guardar lo generado en un archivo
        //guardar los errores en un archivo
        logs.printErrors();
        logs.printWarning();
    }


}
