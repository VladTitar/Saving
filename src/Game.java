import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Game {
    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(100, 3, 10, 123.4);
        GameProgress game2 = new GameProgress(75, 2, 8, 94.7);
        GameProgress game3 = new GameProgress(50, 1, 5, 63.2);

        saveGame("C:/Games/savegames/save1.dat", game1);
        saveGame("C:/Games/savegames/save2.dat", game2);
        saveGame("C:/Games/savegames/save3.dat", game3);

        List<String> filesToZip = new ArrayList<>();
        filesToZip.add("C:/Games/savegames/save1.dat");
        filesToZip.add("C:/Games/savegames/save2.dat");
        filesToZip.add("C:/Games/savegames/save3.dat");

        zipFiles("C:/Games/savegames/zip.zip", filesToZip);

        for (String file : filesToZip) {
            File f = new File(file);
            f.delete();
        }
    }

    public static void saveGame(String fileName, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(fileName); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String zipFileName, List<String> filesToZip) {
        try (FileOutputStream fos = new FileOutputStream(zipFileName); ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (String fileToZip : filesToZip) {
                File file = new File(fileToZip);
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zos.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}