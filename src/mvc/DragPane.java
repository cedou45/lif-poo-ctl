package mvc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.text.Text;
import modele.Case;
import modele.Lien;
import modele.Plateau;

/**
 * Créé par victor le 28/03/18.
 */
public class DragPane extends Pane {
    static final int tailleCase = 100;
    private Case c;
    private Text text;

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
        this.text = new Text(c.getEntree() + " | " + c.getSortie());
        this.getChildren().add(this.text);
        if (this.c.hasSymbol()) {
            ImageView image = new ImageView(new Image("image/" + this.c.symbole + ".jpg"));
            image.setFitWidth(tailleCase);
            image.setFitHeight(tailleCase);
            this.getChildren().add(image);
        }
    }

    void update() {
        this.setPrefSize(tailleCase, tailleCase);
        if (!this.c.hasSymbol()) {
            this.text.setText(this.c.getEntree() + " | " + this.c.getSortie());
            this.drawLine(c.getEntree());
            this.drawLine(c.getSortie());
        }
    }
    
    public void drawLine(Lien entree){
        Line redLine = LineBuilder.create()
                        .startX(0)
                        .startY(0)
                        .build();
        if("TOP".equals(entree.name())){
            redLine = LineBuilder.create()
                        .startX(this.getWidth()/2)
                        .startY(0)
                        .endX(this.getWidth()/2)
                        .endY(this.getHeight()/2)
                        .fill(Color.RED)
                        .build();
        }
        if("RIGHT".equals(entree.name())){
            redLine = LineBuilder.create()
                        .startX(this.getWidth())
                        .startY(this.getHeight()/2)
                        .endX(this.getWidth()/2)
                        .endY(this.getHeight()/2)
                        .fill(Color.RED)
                        .build();
        }
        if("BOTTOM".equals(entree.name())){
            redLine = LineBuilder.create()
                        .startX(this.getWidth()/2)
                        .startY(this.getHeight()/2)
                        .endX(this.getWidth()/2)
                        .endY(this.getHeight())
                        .fill(Color.RED)
                        .build();
        }
        if("LEFT".equals(entree.name())){
            redLine = LineBuilder.create()
                        .startX(0)
                        .startY(this.getHeight()/2)
                        .endX(this.getWidth()/2)
                        .endY(this.getHeight()/2)
                        .fill(Color.RED)
                        .build();
        }
        
        this.getChildren().add(redLine);
    }
}
