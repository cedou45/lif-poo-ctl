import modele.Plateau;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Créé par victor le 28/03/18.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String configFilename = "easy.level";
        File levelConfig = new File(configFilename);

        try {
            Plateau plateau = new Plateau(levelConfig);
            System.out.println(plateau);
        } catch (NoSuchElementException e) {
            System.out.println("Le fichier de configuration " + configFilename + " est incorrect");
        }
    }
}
