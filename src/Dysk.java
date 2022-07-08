import java.util.*;
import java.util.function.Predicate;

public class Dysk {
    private int pozycjaGlowicy;
    private int przesunieciaGlowicy;
    private int rozmiarDysku;
    private int czas;
    private ArrayList<Zlecenie> zlecenia;
    private Generator generator;

    public Dysk(int rozmiarDysku, Generator generator)
    {
        pozycjaGlowicy=0;
        przesunieciaGlowicy=0;
        czas=0;
        this.rozmiarDysku=rozmiarDysku;
        zlecenia= new ArrayList<>();
        this.generator=generator;

    }

    public void odswiez()
    {
        czas++;
        generator.dodajZgloszenia(czas, zlecenia);
    }

    public void FCFS()
    {
    	generator.dodajZgloszenia(czas, zlecenia);


        while(generator.nastepneZlecenie() || !zlecenia.isEmpty())
        {

            wyswietl();
            if(!zlecenia.isEmpty()) {
                przemiesc(zlecenia.get(0).getCylinder());
                zlecenia.remove(0);
            }
            else{
            	odswiez();
            }


        }
    }

    public void SSTF()
    {

    	generator.dodajZgloszenia(czas, zlecenia);

        while(generator.nastepneZlecenie() || !zlecenia.isEmpty())
        {

            if(!zlecenia.isEmpty()) 
            {
                sortujSSTF(zlecenia);

                //wyswietlA();

                przemiesc(zlecenia.get(0).getCylinder());
                zlecenia.remove(0);
            }
            else
            {
            	odswiez();
            }

        }
    }

    public void SCAN(int kierunekPoczatkowy)
    {
    	generator.dodajZgloszenia(czas, zlecenia);

        int kierunek=kierunekPoczatkowy;

        while(generator.nastepneZlecenie() || !zlecenia.isEmpty())
        {
            //wyswietl();
            wykonajZlecenia();
            przemiescSCAN(kierunek);

            if(pozycjaGlowicy==rozmiarDysku-1 || pozycjaGlowicy==0)
            {
                kierunek*=-1;
            }

        }

    }
    public void CSCAN(int kierunekPoczatkowy)
    {
    	generator.dodajZgloszenia(czas, zlecenia);

        int kierunek=kierunekPoczatkowy;

        while(generator.nastepneZlecenie() || !zlecenia.isEmpty())
        {
            //wyswietl();
            wykonajZlecenia();
            przemiescSCAN(kierunek);

            if(pozycjaGlowicy==0)
            {
                setPozycjaGlowicy(rozmiarDysku-1);
            }
            else if(pozycjaGlowicy==rozmiarDysku)
            {
                setPozycjaGlowicy(0);
            }

        }

    }
    public int EDF_pomijanie()
    {
    	generator.dodajZgloszenia(czas, zlecenia);
        int ilePominieto=0;
        Predicate<Zlecenie> predykat = new Predicate<Zlecenie>(){
            public boolean test(Zlecenie zlecenie)
            {
                return zlecenie.getDeadline()<czas;
            }
        };

        while(generator.nastepneZlecenie() || !zlecenia.isEmpty())
        {
            ilePominieto+= sortowanie("Usuwanie",predykat);

            if(!zlecenia.isEmpty()) {

                przemiesc(zlecenia.get(0).getCylinder());
                zlecenia.remove(0);

            }
            else{
            	odswiez();
            }

        }
        return ilePominieto;
    }
    public int EDF_opoznianie()
    {
        int ileOpoznione=0;
        generator.dodajZgloszenia(czas, zlecenia);
        Predicate<Zlecenie> predykat = new Predicate<Zlecenie>(){
            public boolean test(Zlecenie zlecenie)
            {
                return zlecenie.getDeadline()<czas;
            }
        };

        while(generator.nastepneZlecenie() || !zlecenia.isEmpty())
        {
            ileOpoznione+= sortowanie("Opoznianie",predykat);

            if(!zlecenia.isEmpty()) {


                if(zlecenia.get(0).getDeadline()==-2)
                {
                    ileOpoznione+=Math.abs(pozycjaGlowicy-zlecenia.get(0).getCylinder());
                }

                przemiesc(zlecenia.get(0).getCylinder());
                zlecenia.remove(0);

            }
            else{
            	odswiez();
            }

        }
        return ileOpoznione;
    }

    public int FD_SCAN_opoznianie(int kierunekPoczatkowy){
    	generator.dodajZgloszenia(czas, zlecenia);
        int spoznioneZlecenia=0;


        int kierunek=kierunekPoczatkowy;
        Zlecenie tmp=null;

        Predicate<Zlecenie> predykat = new Predicate<Zlecenie>(){
            @Override
            public boolean test(Zlecenie zlecenie) {
               return  zlecenie.getDeadline()<czas || (zlecenie.getDeadline()-czas)<Math.abs(zlecenie.getCylinder() - pozycjaGlowicy);
            }


        };

        while(generator.nastepneZlecenie() || !zlecenia.isEmpty())
        {
            //wyswietl();

            spoznioneZlecenia+=sortowanie("Opoznianie", predykat);
            wykonajZlecenia();
            if(!zlecenia.isEmpty() && (tmp=zlecenia.get(0)).getDeadline()>0)
            {

                if(tmp.getCylinder()<pozycjaGlowicy)
                {
                    kierunek=-1;
                }
                else if(tmp.getCylinder()>pozycjaGlowicy){
                    kierunek=1;
                }
                
                if(zlecenia.get(0).getDeadline()==-2)
                {
                    spoznioneZlecenia+=Math.abs(pozycjaGlowicy-zlecenia.get(0).getCylinder());
                }
            }


            przemiescSCAN(kierunek);

            if(pozycjaGlowicy==rozmiarDysku-1 || pozycjaGlowicy==0)
            {
                kierunek*=-1;
            }

        }

        return spoznioneZlecenia;
    }


    public int FD_SCAN_pomijanie(int kierunekPoczatkowy){
    	generator.dodajZgloszenia(czas, zlecenia);
        int opoznioneZlecenia=0;


        int kierunek=kierunekPoczatkowy;
        Zlecenie tmp=null;

        Predicate<Zlecenie> predykat = new Predicate<Zlecenie>(){
            @Override
            public boolean test(Zlecenie zlecenie) {
               return  zlecenie.getDeadline()<czas || (zlecenie.getDeadline()-czas)<Math.abs(zlecenie.getCylinder() - pozycjaGlowicy);
            }


        };

        while(generator.nastepneZlecenie() || !zlecenia.isEmpty())
        {
            //wyswietl();

        	opoznioneZlecenia+=sortowanie("Usuwanie", predykat);
            wykonajZlecenia();
            if(!zlecenia.isEmpty() && (tmp=zlecenia.get(0)).getDeadline()>0)
            {

                if(tmp.getCylinder()<pozycjaGlowicy)
                {
                    kierunek=-1;
                }
                else if(tmp.getCylinder()>pozycjaGlowicy){
                    kierunek=1;
                }
            }
            przemiescSCAN(kierunek);

            if(pozycjaGlowicy==rozmiarDysku-1 || pozycjaGlowicy==0)
            {
                kierunek*=-1;
            }
        }
        return opoznioneZlecenia;
    }


    private int sortowanie(String tryb, Predicate<Zlecenie> predykat)
    {
        Zlecenie tmp;
        int ilePominieto=0;
        ArrayList<Zlecenie> DeadlineZlecenia = new ArrayList<>();
        int i=0;

        while(i<zlecenia.size())
        {
            if((tmp = zlecenia.get(i)).getDeadline()>-1)
            {
                //Predykat definiuje jakie zlecenia opozniac lub usuwac
                if(predykat.test(tmp)) {

                    if(tryb.equals("Usuwanie"))
                    {
                        zlecenia.remove(tmp);
                        ilePominieto++;
                    }
                    else if(tryb.equals("Opoznianie"))
                    {
                        //Ustawianie deadline na -2 sprawia, że zlecenie jest rozrózniane jako zlecenie, które było kiedyś prioretytowe
                        tmp.setDeadline(-2);
                        //ilePominieto jest wykorzystwane w tym kontekscie aby sprawdzic ilu prioretytowym zleceniom "zdjeto" priorytet
                        ilePominieto++;
                        i++;
                    }
                    else{
                        System.out.println("Nieznany argument");
                        break;
                    }

                }
                else{
                	DeadlineZlecenia.add(tmp);
                    zlecenia.remove(i);
                }

            }
            else{
                i++;
            }


        }

        DeadlineZlecenia.sort(new Comparator<Zlecenie>(){
            public int compare(Zlecenie zl1, Zlecenie zl2)
            {
                return zl1.getDeadline() - zl2.getDeadline();
            }
        });

        zlecenia.addAll(0,DeadlineZlecenia);
       // wyswietl();

        return ilePominieto;

    }

    public void wyswietl()
    {
        System.out.println("Aktualny czas: "+czas);
        System.out.println("Liczba przesunięć głowicy: "+przesunieciaGlowicy);
        System.out.println("Aktualna pozycja głowicy: "+pozycjaGlowicy);
        System.out.printf("id " + " " + "Moment Zgloszenia " + "prioryet " + " cylinder " );
        for(Zlecenie zlecenie: zlecenia)
        {
            System.out.println(zlecenie);
        }
    }

    public int getRozmiarDysku()
    {
        return rozmiarDysku;
    }

    public void przemiesc(int cylinder)
    {
        int i=1;

        if(pozycjaGlowicy>cylinder)
        {
            i*=-1;
        }
        while(pozycjaGlowicy!=cylinder)
        {
            pozycjaGlowicy+=i;
            przesunieciaGlowicy++;
            odswiez();
        }

    }
    public void przemiescSCAN(int kierunek)
    {
        pozycjaGlowicy+=kierunek;
        przesunieciaGlowicy++;
        odswiez();
    }
    public void wykonajZlecenia()
    {
        int i=0;
        while(i<zlecenia.size())
        {
            if(zlecenia.get(i).getCylinder()==pozycjaGlowicy)
            {
                zlecenia.remove(i);
            }
            else
            {
                i++;
            }
        }
    }
    public void reset()
    {
        pozycjaGlowicy=0;
        przesunieciaGlowicy=0;
        czas=0;
        zlecenia= new ArrayList<>();
    }
    public int getPrzesunieciaGlowicy()
    {
        return przesunieciaGlowicy;
    }
    public void setPozycjaGlowicy(int pozycja)
    {
        pozycjaGlowicy=pozycja;
    }

    public void sortujSSTF(ArrayList<Zlecenie> zlecenia)
    {
    	Collections.sort(zlecenia, new Comparator<Zlecenie>(){
            public int compare(Zlecenie zl1, Zlecenie zl2)
           {
               return Math.abs(zl1.getCylinder() - pozycjaGlowicy) - Math.abs(zl2.getCylinder() - pozycjaGlowicy);
           }
       });
    }


}


