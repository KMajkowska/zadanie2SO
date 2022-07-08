import java.util.*;
public class Generator {
	
	 private ArrayList<Zlecenie> oryginalneZlecenia;
	 private ArrayList<Zlecenie> wszystkieZlecenia;
	 private int i;

	 public Generator(int liczbaZlecen, int minimalnyCzasZgloszenia, int maksymalnyCzasZgloszenia, int minimalnyDeadline, int maksymalnyDeadline, double szansaNaZleceniePriorytetowe,int rozmiarDysku)
	    {
	        wszystkieZlecenia=new ArrayList<>();
	        oryginalneZlecenia=new ArrayList<>();
	        generuj(liczbaZlecen, minimalnyCzasZgloszenia, maksymalnyCzasZgloszenia, minimalnyDeadline, maksymalnyDeadline,szansaNaZleceniePriorytetowe,rozmiarDysku);
	        i = 0;
	        kopiuj(wszystkieZlecenia,oryginalneZlecenia);
	    }
	    public void kopiuj(ArrayList<Zlecenie>zrodlo, ArrayList<Zlecenie>docelowa)
	    {
	        for(Zlecenie zlecenie: zrodlo)
	        {
	            docelowa.add(new Zlecenie(zlecenie));
	        }
	    }

	    public void generuj(int liczbaZlecen, int minimalnyCzasZgloszenia, int maksymalnyCzasZgloszenia, int minimalnyDeadline, int maksymalnyDeadline, double szansaNaZleceniePriorytetowe, int rozmiarDysku)
	    {
	        Random g = new Random();
	        int czasZgloszenia = g.nextInt(maksymalnyCzasZgloszenia - minimalnyCzasZgloszenia +1)+ minimalnyCzasZgloszenia;
	        int deadline;


	        for(int i=0;i<liczbaZlecen;i++)
	        {
	            if(maksymalnyDeadline==-1 || g.nextDouble()>=szansaNaZleceniePriorytetowe)
	            {
	                deadline=-1;

	            }
	            else{
	                czasZgloszenia= g.nextInt(maksymalnyCzasZgloszenia - minimalnyCzasZgloszenia +1)+ minimalnyCzasZgloszenia;
	                deadline = czasZgloszenia + g.nextInt(maksymalnyDeadline - minimalnyDeadline +1)+minimalnyDeadline;
	            }

	            wszystkieZlecenia.add(new Zlecenie(i, czasZgloszenia, g.nextInt(rozmiarDysku),deadline));
	        }

	        Collections.sort(wszystkieZlecenia, new CompareMomentZgloszenia());

	    }
	    //potrzebny do testów
	    public Generator(ArrayList<Zlecenie> wszystkieZlecenia)
	    {
	        i=0;
	        this.wszystkieZlecenia=wszystkieZlecenia;
	    }

	   public boolean nastepneZlecenie()
	    {
	        return i<wszystkieZlecenia.size();
	    }
	    public void reset()
	    {
	        i=0;
	        wszystkieZlecenia=new ArrayList<>();
	        kopiuj(oryginalneZlecenia,wszystkieZlecenia);
	    }
	    public void dodajZgloszenia(int momentZgloszenia, ArrayList<Zlecenie>zlecenia)
	    {
	        int rozmiarWszystkichZlecen = wszystkieZlecenia.size();
	        Zlecenie dodawaneZlecenie;
	        while(i< rozmiarWszystkichZlecen &&  (dodawaneZlecenie = wszystkieZlecenia.get(i)).getMomentZgloszenia()<=momentZgloszenia)
	        {
	            zlecenia.add(dodawaneZlecenie);
	            i++;
	        }
	    }
}

