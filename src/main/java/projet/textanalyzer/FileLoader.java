package projet.textanalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileLoader {
    /**
     * Charge le contenu d'un fichier texte
     * @param filePath
     * @return Le contenu du fichier
     * @throws IOException 
     */
    public static String loadFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String content = Files.readString(path);
        return content;
    }

}
