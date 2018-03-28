package modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Créé par victor le 28/03/18.
 */
public class Chemin implements Iterable<Case> {
    private final ArrayList<Case> trace;

    public Chemin(Case depart) {
        this.trace = new ArrayList<>();
        this.trace.add(depart);
    }

    public void ajouterCase(Case suivant) {
        this.trace.add(suivant);
    }

    @Override
    public Iterator<Case> iterator() {
        Iterator<Case> it = new Iterator<Case>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < trace.size();
            }

            @Override
            public Case next() {
                return trace.get(index++);
            }
        };

        return it;
    }

    @Override
    public void forEach(Consumer<? super Case> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Spliterator<Case> spliterator() {
        throw new UnsupportedOperationException();
    }

    public Case getPremiere() {
        return this.trace.get(0);
    }

    public Case getDerniere() {
        return this.trace.get(this.trace.size() - 1);
    }
}
