package modele;

/**
 * Créé par victor le 28/03/18.
 */
public class Case {
    public final int ligne;
    public final int colonne;
    public final int symbole;
    private Chemin chemin;

    Case(int ligne, int colonne, int symbole) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.symbole = symbole;
    }

    public Chemin getChemin() {
        return chemin;
    }

    public void setChemin(Chemin chemin) {
        this.chemin = chemin;
        if (chemin != null) {
            chemin.ajouterCase(this);
        }
    }

    public boolean hasChemin() {
        return this.chemin != null;
    }

    public boolean hasSymbol() {
        return this.symbole != 0;
    }

    public boolean isPair(Case other) {
        return this.symbole == other.symbole && this.hashCode() != other.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(this.symbole);
    }

    public boolean estVoisine(Case other) {
        // TODO write method stub
        return false;
    }
}
