package co.edu.unbosque.model.persistence;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
public class FileManager {
    private static File file;
    private static Scanner fileReader;
    private static PrintWriter fileWriter;
    private final String FOLDER_PATH = "src/files";
    private static FileInputStream fis;
    private static ObjectInputStream ois;
    private static FileOutputStream fos;
    private static ObjectOutputStream oos;
    public static void checkDataFolder() {
        file = new File("FOLDER_PATH");
        if (file.mkdir()) {
            System.out.println("Data folder first time created in: " + file.toURI());
        } else {
            System.out.println("Data folder found in: " + file.toURI());
        }
    }
    public static String openAndReadTextfiles(String url) {
        String contenido = "";
        try {
            file = new File(url);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileReader = new Scanner(file);
            while (fileReader.hasNext()) {
                contenido += fileReader.nextLine() + "\n";
            }
            fileReader.close();
        } catch (IOException e) {
            System.out.println("error al abrir y leer en el file (texto) " + e.getMessage());
            e.printStackTrace();
        }
        return contenido;
    }
    public static void openAndWriteTextfiles(String url, String conetenido) {
        file = new File(url);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new PrintWriter(file);
            fileWriter.print(conetenido);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("error al abrir y escribir en el file (texto) " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static Object serializableOpenAndReadFile(String fileName) {
        Object content = null;
        try {
            file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);
            content = ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException e) {
            System.out.println("No se pudo leer en el file serializado (input)" + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("error al obtener el contenido " + e.getMessage());
            e.printStackTrace();
        }
        return content;
    }
    public static void serializableOpenAndWriteFile(String fileName, Object content) {
        try {
            file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(content);
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("problemas al abrir el file serializado (escritura) " + e.getMessage());
            e.printStackTrace();
        }
    }
}