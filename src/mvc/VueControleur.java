/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modele.Case;
import modele.Plateau;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * @author petit
 */
public class VueControleur extends Application {

    private DragPane[][] tuiles;
    private BorderPane PanelAnnuaire = new BorderPane();
    private BorderPane panelAccueil = new BorderPane();
    private BorderPane panelJeux = new BorderPane();
    private Button BoutonFacile = new Button("Facile"); 
    private Button BoutonNormal = new Button("Normal"); 
    private Button BoutonDifficile = new Button("Difficile"); 
    private Button BoutonRetour = new Button("Retour");
    private GridPane gPane = new GridPane();
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        BoutonFacile.setPrefSize(295, 50);
        panelAccueil.setTop(BoutonFacile);
        
        BoutonNormal.setPrefSize(295, 50);
        panelAccueil.setCenter(BoutonNormal);
        
        BoutonDifficile.setPrefSize(295, 50);
        panelAccueil.setBottom(BoutonDifficile);
        
        gPane.setGridLinesVisible(true);
        panelJeux.setCenter(gPane);
        
        BoutonFacile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String configFilename = "beginner.level";
                try {
                    gPaneConfig(configFilename);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
                }
                PanelAnnuaire.setCenter(panelJeux);
            }
        });
        
        
        
        PanelAnnuaire.setCenter(panelAccueil);

        Scene scene = new Scene(PanelAnnuaire, 500, 400);

        primaryStage.setTitle("Casse tête - Lignes");
        primaryStage.setScene(scene);
        primaryStage.show();    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void gPaneConfig(String configFilename) throws FileNotFoundException{
        int tailleCase = 100;
        File levelConfig = new File(configFilename);

        Plateau plateau;
        try {
            plateau = new Plateau(levelConfig);
        } catch (NoSuchElementException e) {
            System.out.println("Le fichier de configuration " + configFilename + " est incorrect");
            return;
        }

        this.tuiles = new DragPane[plateau.hauteur][plateau.largeur];

        // création des bouton et placement dans la grille
        for (int i = 0; i < plateau.hauteur; i++) {
            for (int j = 0; j < plateau.largeur; j++) {
                Case c = plateau.cases[i][j];
                DragPane p = new DragPane(i, j, plateau, c);
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
