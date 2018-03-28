package modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

/**
 * Créé par victor le 28/03/18.
 */
public class Plateau extends Observable {
    private final int hauteur;
    private final int largeur;
    private final Case[][] cases;
    public final ArrayList<Chemin> chemins;
    private Chemin cheminActuel;

    public Plateau(File configFile) throws FileNotFoundException {
        Scanner configScanner = new Scanner(configFile);
        this.hauteur = configScanner.nextInt();
        System.out.println("Hauteur: " + this.hauteur);
        this.largeur = configScanner.nextInt();
        System.out.println("Largeur: " + this.largeur);

        this.cases = new Case[this.hauteur][this.largeur];
        for (int i = 0; i < this.hauteur; ++i) {
            for (int j = 0; j < this.largeur; ++j) {
                this.cases[i][j] = new Case(i, j, configScanner.nextInt());
            }
        }

        this.chemins = new ArrayList<>();
    }

    public String toString() {
        StringBuilder display = new StringBuilder("┌");

        for (int j = 0; j < this.largeur; j++) {
            display.append("─");
            if (j == this.largeur - 1) {
                display.append("┐");
            } else {
                display.append("┬");
            }
        }
        display.append("\n");

        for (int i = 0; i < this.hauteur; i++) {
            display.append("│");
            for (int j = 0; j < this.largeur; j++) {
                display.append(this.cases[i][j]);
                display.append("│");
            }
            display.append("\n");
            if (i != this.hauteur - 1) {
                display.append("├");
                for (int j = 0; j < this.largeur; j++) {
                    display.append("─");
                    if (j == this.largeur - 1) {
                        display.append("┤");
                    } else {
                        display.append("┼");
                    }
                }
                display.append("\n");
            }
        }
        display.append("└");
        for (int j = 0; j < this.largeur; j++) {
            display.append("─");
            if (j == this.largeur - 1) {
                display.append("┘");
            } else {
                display.append("┴");
            }
        }
        display.append("\n");

        return display.toString();
    }

    public boolean commencerChemin(int x, int y) {
        Case premiere = this.cases[x][y];
        if (premiere.hasSymbol() && !premiere.hasChemin()) {
            this.cheminActuel = new Chemin(premiere);
            // TODO set Chemin

            return true;
        }

        return false;
    }

    public boolean ajouterCaseChemin(int x, int y) {
        if (this.cheminActuel != null) {
            Case suivante = this.cases[x][y];
            if (suivante.estVoisine(this.cheminActuel.getDerniere())) {
                suivante.setChemin(this.cheminActuel);

                return true;
            }
        }

        return false;
    }

    public boolean terminerChemin(int x, int y) {
        Case derniere = this.cases[x][y];
        if (derniere.isPair(this.cheminActuel.getPremiere())) {
            derniere.setChemin(this.cheminActuel);
            this.chemins.add(this.cheminActuel);
            this.cheminActuel = null;

            return true;
        } else {
            this.effacerChemin(this.cheminActuel);
        }

        return false;
    }

    public boolean supprimerChemin(int x, int y) {
        Case aCase = this.cases[x][y];
        Chemin chemin = aCase.getChemin();
        if (chemin != null) {
            this.effacerChemin(chemin);

            return true;
        }

        return false;
    }

    private void effacerChemin(Chemin chemin) {
        for (Case c : chemin) {
            c.setChemin(null);
        }
        this.chemins.remove(chemin);
    }
}

