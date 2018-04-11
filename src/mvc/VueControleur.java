/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Case;
import modele.Plateau;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author petit
 */
public class VueControleur extends Application {

    private DragPane[][] tuiles;
    private BorderPane PanelAnnuaire = new BorderPane();
    private BorderPane panelAccueil = new BorderPane();
    private BorderPane panelJeux = new BorderPane();
    private GridPane gPane = new GridPane();

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        int levelCount = 30;
        int colsCout = (int) Math.floor(Math.sqrt(levelCount));

        GridPane grilleBoutons = new GridPane();
        for (int i = 0; i < levelCount; ++i) {
            String level = String.valueOf(i + 1) + ".level";
            Button bouton = new Button(String.valueOf(i + 1));
            bouton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    try {
                        gPaneConfig(level);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PanelAnnuaire.setCenter(panelJeux);
                }
            });


            bouton.setPrefSize(100, 100);
            grilleBoutons.add(bouton, i % colsCout, (int) Math.floor(i / colsCout));
        }


        panelAccueil.setCenter(grilleBoutons);

        gPane.setGridLinesVisible(true);
        panelJeux.setCenter(gPane);

        PanelAnnuaire.setCenter(panelAccueil);

        Scene scene = new Scene(PanelAnnuaire, 500, 500);

        primaryStage.setTitle("Casse tête - Lignes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void gPaneConfig(String configFilename) throws FileNotFoundException {
        File levelConfig = new File(configFilename);

        Plateau plateau;
        try {
            plateau = new Plateau(levelConfig);
        } catch (NoSuchElementException e) {
            System.out.println("Le fichier de configuration " + configFilename + " est incorrect");
            return;
        }

        this.tuiles = new DragPane[plateau.hauteur][plateau.largeur];

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.AQUA);
        colors.add(Color.CHARTREUSE);
        colors.add(Color.CRIMSON);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.MAROON);
        colors.add(Color.VIOLET);
        colors.add(Color.YELLOW);
        Collections.shuffle(colors);

        // création des bouton et placement dans la grille
        ArrayList<File> images = new ArrayList<>(Arrays.asList(new File("src/image").listFiles()));
        Collections.shuffle(images);

        for (int i = 0; i < plateau.hauteur; i++) {
            for (int j = 0; j < plateau.largeur; j++) {
                Case c = plateau.cases[i][j];
                DragPane p;
                if (c.hasSymbol()) {
                    p = new DragPane(i, j, plateau, c, colors, new Image("image/" + images.get(c.symbole).getName()));
                } else {
                    p = new DragPane(i, j, plateau, c, colors, null);
                }
                this.tuiles[i][j] = p;
                p.update();
                gPane.add(p, j, i);
            }
            // un controleur (EventHandler) par bouton écoute et met à jour le champ affichage
            /*t.setOnMouseClicked(new EventHandler<MouseEvent>() {
                
                @Override
                public void handle(MouseEvent event) {
                    affichage.setText(affichage.getText() + t.getText());
                }
                
            });*/


        }


        plateau.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                for (int i = 0; i < plateau.hauteur; ++i) {
                    for (int j = 0; j < plateau.largeur; ++j) {
                        tuiles[i][j].update();
                    }
                }
            }
        });
    }

}
