/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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
    private Button BoutonRetour = new Button("Retour accueil");
    private Button BoutonRecommencer = new Button("Recommencer");
    private GridPane gPane = new GridPane();
    private Stage dialog = new Stage();
    private String configFilename;
    private Plateau plateau;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        dialog.initModality(Modality.APPLICATION_MODAL);

        BoutonRetour.setPrefSize(295,50);
        BoutonRecommencer.setPrefSize(295,50);

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
                        configFilename = level;
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

        BoutonRetour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
                PanelAnnuaire.getChildren().remove(panelJeux);
                PanelAnnuaire.setCenter(panelAccueil);
                dialog.close();
            }
        });

        BoutonRecommencer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
                PanelAnnuaire.getChildren().remove(panelJeux);
                try {
                    gPaneConfig(configFilename);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.close();
                panelJeux.setCenter(gPane);
                PanelAnnuaire.setCenter(panelJeux);
            }
        });


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

        if (plateau != null) {
            plateau.deleteObservers();
        }
        int m = 0;
        while (m < gPane.getChildren().size()) {
            if (gPane.getChildren().get(m) instanceof DragPane) {
                gPane.getChildren().remove(gPane.getChildren().get(m));
            } else {
                ++m;
            }
        }
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
                p.reDraw();
                gPane.add(p, j, i);
            }
        }


        plateau.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if(plateau.partieTerminee){
                    VBox vbButtons = new VBox();
                    vbButtons.setSpacing(10);
                    vbButtons.setPadding(new Insets(0, 20, 10, 20));
                    vbButtons.getChildren().addAll(new Text("BRAVO !"),BoutonRecommencer,BoutonRetour);
                    Scene dialogScene = new Scene(vbButtons, 300, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();

                }
            }
        });
    }

}
