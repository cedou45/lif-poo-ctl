package modele;

/**
 * Créé par victor le 28/03/18.
 */
public class Case {
    private Lien entree = Lien.NONE;
    private Lien sortie = Lien.NONE;

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

    private Lien getCote(Case other) {
        if (this.ligne == other.ligne) {
            if (other.colonne == this.colonne - 1) {
                return Lien.LEFT;
            } else if (other.colonne == this.colonne + 1) {
                return Lien.RIGHT;
            }
        } else if (this.colonne == other.colonne) {
            if (other.ligne == this.ligne - 1) {
                return Lien.TOP;
            } else if (other.ligne == this.ligne + 1) {
                return Lien.BOTTOM;
            }
        }

        return Lien.NONE;
    }

    public boolean estVoisine(Case other) {
        return this.getCote(other) != Lien.NONE;
    }

    public void setEntree(Case other) {
        this.entree = this.getCote(other);
    }

    public void setSortie(Case other) {
        this.sortie = this.getCote(other);
    }

    public Lien getEntree() {
        return entree;
    }

    public Lien getSortie() {
        return sortie;
    }

    public void reset() {
        this.chemin = null;
        this.entree = Lien.NONE;
        this.sortie = Lien.NONE;
    }
}
