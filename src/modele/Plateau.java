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
    public final int hauteur;
    public final int largeur;
    public final Case[][] cases;
    public final ArrayList<Chemin> chemins;
    private Chemin cheminActuel;

    public Plateau(File configFile) throws FileNotFoundException {
        Scanner configScanner = new Scanner(configFile);
        this.hauteur = configScanner.nextInt();
        this.largeur = configScanner.nextInt();

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

    public void commencerChemin(int x, int y) {
        System.out.println("commencerChemin");
        Case premiere = this.cases[x][y];
        if (premiere.hasSymbol() && !premiere.hasChemin()) {
            this.cheminActuel = new Chemin(premiere);

            setChanged();
            notifyObservers();
        }
    }

    public void ajouterCaseChemin(int x, int y) {
        System.out.println("ajouterCaseChemin");
        if (this.cheminActuel != null) {
            Case suivante = this.cases[x][y];
            if (suivante != this.cheminActuel.getDerniere() && !suivante.hasChemin() && suivante.estVoisine(this.cheminActuel.getDerniere())) {
                this.cheminActuel.ajouterCase(suivante);

                setChanged();
                notifyObservers();
            }
        }
    }

    public void terminerChemin() {
        System.out.println("terminerChemin");
        if (this.cheminActuel != null) {
            if (this.cheminActuel.getDerniere().isPair(this.cheminActuel.getPremiere())) {
                this.chemins.add(this.cheminActuel);
                this.cheminActuel = null;
                System.out.println("Good chemin");
            } else {
                System.out.println("Wrong chemin");
                this.effacerChemin(this.cheminActuel);
            }

            setChanged();
            notifyObservers();
        }
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
            c.reset();
        }
        this.chemins.remove(chemin);
    }
}

