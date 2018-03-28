/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import modele.Plateau;
import javafx.scene.image.Image ;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author petit
 */
public class VueControleur extends Application {
        
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        int tailleCase = 100;
        String configFilename = "easy.level";
        File levelConfig = new File(configFilename);

        Plateau plateau;
        try {
            plateau = new Plateau(levelConfig);
        } catch (NoSuchElementException e) {
            System.out.println("Le fichier de configuration " + configFilename + " est incorrect");
            return;
        }
        GridPane gPane = new GridPane();
         // création des bouton et placement dans la grille
        for (int i=0; i<plateau.hauteur;i++) {
            for(int j=0; j<plateau.largeur;j++){               
                if(plateau.cases[i][j].symbole != 0){
                    ImageView image = new ImageView(new Image("image/"+plateau.cases[i][j].symbole+".jpg"));
                    image.setFitWidth(tailleCase);
                    image.setFitHeight(tailleCase);
                    gPane.add(image,j,i);
                }else{
                    Pane p = new Pane();
                    p.setPrefSize(tailleCase,tailleCase);
                    gPane.add(p,j,i);
                }
                
            
            }
            // un controleur (EventHandler) par bouton écoute et met à jour le champ affichage
            /*t.setOnMouseClicked(new EventHandler<MouseEvent>() {
                
                @Override
                public void handle(MouseEvent event) {
                    affichage.setText(affichage.getText() + t.getText());
                }
                
            });*/
            
            
            
        }
        gPane.setGridLinesVisible(true);
        StackPane root = new StackPane();
        root.getChildren().add(gPane);
        
        Scene scene = new Scene(root, 500, 400);
        
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
    
}
