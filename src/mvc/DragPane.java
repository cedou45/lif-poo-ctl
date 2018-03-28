package mvc;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import modele.Case;
import modele.Plateau;

/**
 * Créé par victor le 28/03/18.
 */
public class DragPane extends Pane {
    private Case c;

    DragPane(int ligne, int colonne, Plateau plateau, Case c) {
        this.c = c;
        this.setOnDragDetected(event -> {
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("");
            db.setContent(content);
            plateau.commencerChemin(ligne, colonne);
            event.consume();
        });
        this.setOnDragEntered(event -> {
            plateau.ajouterCaseChemin(ligne, colonne);
            event.consume();
        });
        this.setOnDragDone(event -> {
            plateau.terminerChemin();
            event.consume();
        });
    }

    void update() {
        if (this.c.symbole == 0) {
            ((Text) this.getChildren().get(0)).setText(this.c.getEntree() + " | " + this.c.getSortie());
        }
    }
}
