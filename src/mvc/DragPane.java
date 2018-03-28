package mvc;

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
            plateau.commencerChemin(ligne, colonne);
            event.consume();
        });
        this.setOnDragEntered(event -> {
            plateau.ajouterCaseChemin(ligne, colonne);
            event.consume();
        });
        this.setOnDragExited(event -> {
            plateau.terminerChemin();
            event.consume();
        });
        this.setOnDragDone(event -> {
            System.out.println("setOnDragDone");
            event.consume();
        });
        this.setOnDragDropped(event -> {
            System.out.println("setOnDragDropped");
            event.consume();
        });
        this.setOnDragOver(event -> {
            System.out.println("setOnDragOver");
            event.consume();
        });
    }

    void update() {
        System.out.println(this);
        if (this.c.symbole == 0) {
            ((Text) this.getChildren().get(0)).setText(this.c.getEntree() + " | " + this.c.getSortie());
        }
    }
}
