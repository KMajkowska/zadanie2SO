import java.util.Comparator;

public class CompareMomentZgloszenia implements Comparator<Zlecenie> {

    public int compare(Zlecenie z1, Zlecenie z2)
    {
        return z1.getMomentZgloszenia() - z2.getMomentZgloszenia();

    }
}