import java.util.*;
public class Main {
    public static void Test(int liczbaZlecen, int minimalnyCzasZgloszenia, int maksymalnyCzasZgloszenia, int minimalnyDeadline, int maksymalnyDeadline,double szansaNaProcesPriorytetowy,  int rozmiarDysku)
    {
        Generator zlecenia = new Generator(liczbaZlecen, minimalnyCzasZgloszenia, maksymalnyCzasZgloszenia, minimalnyDeadline, maksymalnyDeadline, szansaNaProcesPriorytetowy, rozmiarDysku);
        Dysk dysk = new Dysk(rozmiarDysku,  zlecenia);
        FCFS_DYSK(dysk,  zlecenia);
        System.out.println();
        
        STTF_DYSK(dysk,  zlecenia);
        System.out.println();

        SCAN_DYSK(dysk,  zlecenia, 1);
        System.out.println();

        CSCAN_DYSK(dysk,  zlecenia, 1);
        System.out.println();

        EDF_DYSK(dysk,  zlecenia, "Usuwanie");
        System.out.println();

        EDF_DYSK(dysk,  zlecenia, "Opoznianie");
        System.out.println();

        FD_DYSK(dysk,  zlecenia, 1,"Usuwanie");
        System.out.println();
        
        //FD_DYSK(dysk,  zlecenia, 1, "Opoznianie");
        //System.out.println();

    }

    public static void FCFS_DYSK(Dysk dysk, Generator generatorZlecen)
    {
        resetDysku(dysk,generatorZlecen);
        dysk.FCFS();
        System.out.println("FCFS: Liczba przesuniec glowicy: "+dysk.getPrzesunieciaGlowicy());

    }
    public static void STTF_DYSK(Dysk dysk, Generator generatorZlecen)
    {
        resetDysku(dysk,generatorZlecen);
        dysk.SSTF();
        System.out.println("SSTF: Liczba przesuniec glowicy: "+dysk.getPrzesunieciaGlowicy());

    }
    public static void SCAN_DYSK(Dysk dysk, Generator generatorZlecen, int kierunekPoczatkowy)
    {
        resetDysku(dysk, generatorZlecen);
        dysk.SCAN(kierunekPoczatkowy);
        System.out.println("SCAN z kierunkiem "+kierunekPoczatkowy+": Liczba przesuniec glowicy: "+dysk.getPrzesunieciaGlowicy());
    }
    public static void CSCAN_DYSK(Dysk dysk, Generator generatorZlecen, int kierunekPoczatkowy)
    {
        resetDysku(dysk, generatorZlecen);
        dysk.CSCAN(kierunekPoczatkowy);
        System.out.println("CSCAN z kierunkiem "+ kierunekPoczatkowy +": Liczba przesuniec glowicy: "+dysk.getPrzesunieciaGlowicy());
    }
    public static void EDF_DYSK(Dysk dysk, Generator generatorZlecen, String tryb)
    {
        resetDysku(dysk, generatorZlecen);
        if(tryb.equals("Usuwanie")) {
            int porzuconeZlecenia = dysk.EDF_pomijanie();
            System.out.println("EDF z pomijaniem zlecen: Liczba przesuniec glowicy: " + dysk.getPrzesunieciaGlowicy());
            System.out.println("Liczba pomijaniem zlecen: " + porzuconeZlecenia);
        }
        else if(tryb.equals("Opoznianie"))
        {
            int opoznionePrzemieszczeniaGlowicy = dysk.EDF_opoznianie();
            System.out.println("EDF z wykonywaniem zlecen z opoznieniem: Liczba przesuniec glowicy: " + dysk.getPrzesunieciaGlowicy());
            System.out.println("Liczba przemieszczen dla opoznionych zlecen: " + opoznionePrzemieszczeniaGlowicy);
        }
        else{
            System.out.println("Niepoprawny tryb");
        }
    }
    
    public static void FD_DYSK(Dysk dysk, Generator generatorZlecen, int kierunekPoczatkowy, String tryb)
    {
        if(tryb.equals("Usuwanie")) {
        	resetDysku(dysk, generatorZlecen);
            int usunieteZlecenia = dysk.FD_SCAN_pomijanie(kierunekPoczatkowy);
            System.out.println("FDSCAN z kierunkiem " + kierunekPoczatkowy+ ": Liczba przesuniec glowicy: "+dysk.getPrzesunieciaGlowicy());
            System.out.println("Liczba usunietych zlecen priorytetowych: "+ usunieteZlecenia);
        }
        else if(tryb.equals("Opoznianie"))
        {
        	//resetDysku(dysk, generatorZlecen);
        	//int spoznioneZlecenia = dysk.FD_SCAN_opoznianie(kierunekPoczatkowy);
            //System.out.println("FDSCAN z kierunkiem " + kierunekPoczatkowy+ ": Liczba przesuniec glowicy: "+dysk.getPrzesunieciaGlowicy());
            //System.out.println("Liczba spoznionych zlecen priorytetowych: "+spoznioneZlecenia);
        }
        else{
            System.out.println("Niepoprawny tryb");
        }
    }

    public static void resetDysku(Dysk dysk, Generator  zlecenia)
    {
    	zlecenia.reset();
        dysk.reset();
    }


    public static void main(String[] args) {
    	Scanner scan = new Scanner(System.in);
    	 int zlecenia, maxCzas, minCzas, minDeadline, maxDeadline, rozmiar;
         double szansa;
       
        System.out.println("Liczba zleceñ");
        zlecenia = scan.nextInt();
        System.out.println("Minimalny czas zg³oszenia");
        minCzas = scan.nextInt();
        System.out.println("Maksymalny czas zg³oszenia");
        maxCzas = scan.nextInt();
        System.out.println("Minimalny deadline");
        minDeadline = scan.nextInt();
        System.out.println("Maksymalny deadline");
        maxDeadline = scan.nextInt();
        System.out.println("Szansa na to, ¿e wyst¹pi zg³oszenie z deadlinem (w procentach)");
        szansa = scan.nextDouble();
        System.out.println("Rozmiary dysku");
        rozmiar = scan.nextInt();
       
        Test(zlecenia, minCzas, maxCzas, minDeadline, maxDeadline, szansa, rozmiar);

    }
}
