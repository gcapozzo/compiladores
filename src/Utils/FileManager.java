package Utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final int ENTER_ASCII = 13;

    public static ArrayList<Character> reader(String path) throws Exception {
        File file = new File(path);

        FileReader fr = new FileReader(file);
        ArrayList<Character> caracteres = new ArrayList<>();
        ArrayList<Integer> intVal = new ArrayList<>();
        int i;
        while ((i = fr.read()) != -1) {
            if (i != ENTER_ASCII)
                caracteres.add((char) i);
        }

        caracteres.add((char) 36); //36 es el codigo ascii del $
        return caracteres;
    }

    public static void saveStringListToFile(List<String> listToSave, String path) throws Exception{
        FileWriter writer = new FileWriter(path);
        for(String str: listToSave) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
    }

}
