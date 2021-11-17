import AnalizadorLexico.*;
import AnalizadorSintactico.*;
import Utils.*;

import java.io.File;
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
            System.err.println("El primer argunmento no es un archivo");
            System.exit(1);
        }
        if (!Files.isDirectory(Paths.get(args[1]))){
            System.err.println("El segundo argumento no es un directorio");
            System.exit(1);
        }
        run(args[0], args[1]);
    }

    private static void run(String path, String folderToSave) throws Exception {
        logs = new ErrorLog();
        ArrayList<Character> code = FileManager.reader(path);
        sourceCode = new SourceCode(code,logs);
        logs.setSourceCode(sourceCode);
        lexico = new AnalizadorLexico(sourceCode,symbolTable);
        parser = new Parser(lexico, logs);
        parser.run();
        ArrayList<String> gen = lexico.getGenerado();
        for (String s: gen)
            System.out.println(s);
        String errorsPath = folderToSave + File.pathSeparator + "error.txt";
        String warningsPath = folderToSave + File.pathSeparator + "warnings.txt";
        FileManager.saveStringListToFile(logs.getErrors(),errorsPath);
        FileManager.saveStringListToFile(logs.getWarnings(),warningsPath);
        logs.printErrors();
        logs.printWarning();
    }


}
