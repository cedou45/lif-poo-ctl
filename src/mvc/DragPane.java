package mvc;

import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import modele.Case;
import modele.Lien;
import modele.Plateau;

import java.util.ArrayList;

/**
 * Créé par victor le 28/03/18.
 */
public class DragPane extends Pane {
    static final int tailleCase = 100;
    private Case c;
    private Line ligneEntree;
    private Line ligneSortie;
    private Plateau plateau;
    private ArrayList<Color> colors;

    DragPane(int ligne, int colonne, Plateau plateau, Case c, ArrayList<Color> colors, Image imageFile) {
        this.setPrefSize(tailleCase, tailleCase);
        this.c = c;
        this.plateau = plateau;
        this.colors = colors;
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
        
        this.setOnMouseClicked((MouseEvent event) -> {
            plateau.supprimerChemin(ligne,colonne);
            event.consume();
        });
        
        this.ligneEntree = new Line(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2, this.getHeight() / 2);
        this.ligneSortie = new Line(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2, this.getHeight() / 2);
        this.ligneEntree.setStrokeWidth(10);
        this.ligneSortie.setStrokeWidth(10);
        this.ligneEntree.setVisible(false);
        this.ligneSortie.setVisible(false);
        if (this.c.hasSymbol()) {
            ImageView image = new ImageView(imageFile);
            image.setFitWidth(tailleCase);
            image.setFitHeight(tailleCase);
            this.getChildren().add(image);
        } else {
            this.drawLine(c, c.getEntree(), this.ligneEntree);
            this.drawLine(c, c.getSortie(), this.ligneSortie);
        }
        this.getChildren().add(this.ligneEntree);
        this.getChildren().add(this.ligneSortie);
        plateau.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                reDraw();
            }
        });

    }

    void reDraw() {
        if (!this.c.hasSymbol()) {
            this.drawLine(c, c.getEntree(), this.ligneEntree);
            this.drawLine(c, c.getSortie(), this.ligneSortie);
        }
    }

    public void drawLine(Case c, Lien cote, Line ligne) {
        ligne.setEndX(this.getWidth() / 2);
        ligne.setEndY(this.getHeight() / 2);
        ligne.setVisible(true);
        switch (cote) {
            case TOP:
                ligne.setStartX(this.getWidth() / 2);
                ligne.setStartY(0);
                break;
            case RIGHT:
                ligne.setStartX(this.getWidth());
                ligne.setStartY(this.getHeight() / 2);
                break;
            case BOTTOM:
                ligne.setStartX(this.getWidth() / 2);
                ligne.setStartY(this.getHeight());
                break;
            case LEFT:
                ligne.setStartX(0);
                ligne.setStartY(this.getHeight() / 2);
                break;
            case NONE:
            default:
                ligne.setVisible(false);
                break;
        }

        if (c.getChemin() == this.plateau.getCheminActuel()) {
            ligne.setStroke(Color.RED);
        } else if (c.hasChemin()) {
            ligne.setStroke(colors.get(this.plateau.getChemins().indexOf(c.getChemin())));
        }
    }
}
