import AnalizadorLexico.*;
import AnalizadorSintactico.*;
import Utils.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    private static AnalizadorLexico lexico;
    private static SourceCode sourceCode;
    private static Parser parser = new Parser();
    private static SymbolTable symbolTable = new SymbolTable();
    private static ErrorLog logs;

    public static void main(String[] args) throws Exception {
        if (!Files.isRegularFile(Paths.get(args[0]))){
            System.err.println("El argumento debe ser el archivo de prueba.");
            System.exit(1);
        }
        run(args[0]);
    }

    private static void run(String path) throws Exception {
        logs = new ErrorLog();
        ArrayList<Character> code = FileManager.reader(path);
        sourceCode = new SourceCode(code,logs);
        logs.setSourceCode(sourceCode);
        symbolTable = new SymbolTable();
        lexico = new AnalizadorLexico(sourceCode,symbolTable);
        parser = new Parser(lexico, logs);
        parser.run();
        ArrayList<String> gen = lexico.getGenerado();
        for (String s: gen)
            System.out.println(s);
        String errorsPath = FileManager.TEST_FILE_PATH + FileManager.TEST_FILE_NAME + "error.txt";
        String warningsPath = FileManager.TEST_FILE_PATH + FileManager.TEST_FILE_NAME + "warnings.txt";
        FileManager.saveStringListToFile(logs.getErrors(),errorsPath);
        FileManager.saveStringListToFile(logs.getWarnings(),warningsPath);
        logs.printErrors();
        logs.printWarning();
    }


}
