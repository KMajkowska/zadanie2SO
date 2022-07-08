import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Test {
    private Dysk dysk;
    private Generator generatorZlecen;


    @org.junit.jupiter.api.BeforeEach
    public void beforeEach() {
        ArrayList<Zlecenie> lista = new ArrayList<>();
        lista.add(new Zlecenie(0,0,98));
        lista.add(new Zlecenie(0,0,183));
        lista.add(new Zlecenie(0,0,37));
        lista.add(new Zlecenie(0,0,122));
        lista.add(new Zlecenie(0,0,14));
        lista.add(new Zlecenie(0,0,124));
        //lista.add(new Zlecenie(0,0,124));
        lista.add(new Zlecenie(0,0,65));
        lista.add(new Zlecenie(0,0,67));
        //lista.add(new Zlecenie(0,0,67));

        generatorZlecen = new Generator(lista);
        dysk = new Dysk(200, generatorZlecen);
        dysk.setPozycjaGlowicy(53);
    }

    @org.junit.jupiter.api.Test
    void FCFS() {
        dysk.FCFS();
        assertEquals(640, dysk.getPrzesunieciaGlowicy());
    }

    @org.junit.jupiter.api.Test
    void SSTF(){
        dysk.SSTF();
        assertEquals(236, dysk.getPrzesunieciaGlowicy());
    }
    @org.junit.jupiter.api.Test
    void SCAN(){
        dysk.SCAN(1);
        assertEquals(332, dysk.getPrzesunieciaGlowicy());
    }

    @org.junit.jupiter.api.Test
    void SCAN2(){
        dysk.SCAN(-1);
        assertEquals(237, dysk.getPrzesunieciaGlowicy());
    }

    @org.junit.jupiter.api.Test
    void CSCAN(){
        dysk.CSCAN(1);
        assertEquals(185, dysk.getPrzesunieciaGlowicy());
    }

    @org.junit.jupiter.api.Test
    void CSCAN2(){
        dysk.CSCAN(-1);
        assertEquals(188, dysk.getPrzesunieciaGlowicy());
    }
    @org.junit.jupiter.api.Test
    void EDF_opoznianie(){
        ArrayList<Zlecenie> lista = new ArrayList<>();
        lista.add(new Zlecenie(0,0,125,-1));
        lista.add(new Zlecenie(0,110,50,500));
        lista.add(new Zlecenie(0,110,100,-1));
        lista.add(new Zlecenie(0,150,1, -1));
        lista.add(new Zlecenie(0,295,10,700));
        lista.add(new Zlecenie(0,295,90, 600));
        lista.add(new Zlecenie(0,295,65,-1));

        generatorZlecen = new Generator(lista);
        dysk = new Dysk(150, generatorZlecen);
        dysk.setPozycjaGlowicy(0);
        int ileOpozniono = dysk.EDF_opoznianie();
        System.out.println("Opozniono:");
        System.out.println(ileOpozniono);
        assertEquals(573, dysk.getPrzesunieciaGlowicy());

    }

    @org.junit.jupiter.api.Test
    void EDF_pomijanie(){
        ArrayList<Zlecenie> lista = new ArrayList<>();
        lista.add(new Zlecenie(0,0,125,-1));
        lista.add(new Zlecenie(0,110,50,500));
        lista.add(new Zlecenie(0,110,100,-1));
        lista.add(new Zlecenie(0,150,1, -1));
        lista.add(new Zlecenie(0,295,10,700));
        lista.add(new Zlecenie(0,295,90, 600));
        lista.add(new Zlecenie(0,295,65,-1));

        generatorZlecen = new Generator(lista);
        dysk = new Dysk(150, generatorZlecen);
        dysk.setPozycjaGlowicy(0);
        int ilePominieto = dysk.EDF_pomijanie();
        System.out.println("Pominieto:");
        System.out.println(ilePominieto);
        assertEquals(573, dysk.getPrzesunieciaGlowicy());

    }
    @org.junit.jupiter.api.Test
    void FDSCAN_pomijanie(){
        ArrayList<Zlecenie> lista = new ArrayList<>();
        lista.add(new Zlecenie(0,0,125,-1));
        lista.add(new Zlecenie(0,110,50,500));
        lista.add(new Zlecenie(0,110,100,-1));
        lista.add(new Zlecenie(0,150,1, -1));
        lista.add(new Zlecenie(0,295,10,700));
        lista.add(new Zlecenie(0,295,90, 600));
        lista.add(new Zlecenie(0,295,65,-1));

        generatorZlecen = new Generator(lista);
        dysk = new Dysk(150, generatorZlecen);
        dysk.setPozycjaGlowicy(0);
        int ilePominieto = dysk.FD_SCAN_pomijanie(1);
        System.out.println("Pominieto FDSCAN:");
        System.out.println(ilePominieto);
        assertEquals(526, dysk.getPrzesunieciaGlowicy());
    }
   @org.junit.jupiter.api.Test
    void FDSCAN_opoznianie(){
        ArrayList<Zlecenie> lista = new ArrayList<>();
        lista.add(new Zlecenie(0,0,125,-1));
        lista.add(new Zlecenie(0,110,50,500));
        lista.add(new Zlecenie(0,110,100,-1));
        lista.add(new Zlecenie(0,150,1, -1));
        lista.add(new Zlecenie(0,295,10,700));
        lista.add(new Zlecenie(0,295,90, 600));
        lista.add(new Zlecenie(0,295,65,-1));

        generatorZlecen = new Generator(lista);
        dysk = new Dysk(150, generatorZlecen);
        dysk.setPozycjaGlowicy(0);
        int ileOpozniono = dysk.FD_SCAN_opoznianie(1); 
        System.out.println("Opozniono FDSCAN");
        System.out.println(ileOpozniono);
        assertEquals(526, dysk.getPrzesunieciaGlowicy());
    }


}
