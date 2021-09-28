import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public final class ReaderFromFile {
    private static final int ENTER_ASCII = 13;

    public static ArrayList<Character> reader(String path) throws Exception {

        File file = new File(path);

        /*
        System.out.println("Enter the file path:");
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();
        File file = new File(path);
        in.close();
        */

        FileReader fr = new FileReader(file);
        ArrayList<Character> caracteres = new ArrayList<>();
        ArrayList<Integer> intVal = new ArrayList<>();
        int i;


        while((i=fr.read()) != -1){
            if( i != ENTER_ASCII)
                caracteres.add((char) i);
        }

        caracteres.add((char) 36); //36 es el codigo ascii del $
        return caracteres;
    }

}
