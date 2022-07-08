
public class Zlecenie{
    private final int pid;
    private final int momentZgloszenia;
    private int deadline;
    private final int cylinder;

    public Zlecenie(int momentPojawienia, int cylinder)
    {
        pid=-1;
        deadline=-1;
        this.momentZgloszenia=momentPojawienia;
        this.cylinder=cylinder;
    }
    public Zlecenie(Zlecenie zlecenie)
    {
        this.pid = zlecenie.pid;
        this.momentZgloszenia = zlecenie.momentZgloszenia;
        this.deadline = zlecenie.deadline;
        this.cylinder = zlecenie.cylinder;
    }

    public Zlecenie(int pid, int momentPojawienia, int cylinder)
    {
        this.pid=pid;
        deadline=-1;
        this.momentZgloszenia=momentPojawienia;
        this.cylinder=cylinder;
    }
    public Zlecenie(int pid, int momentZgloszenia, int cylinder, int deadline)
    {
        this.pid=pid;
        this.momentZgloszenia = momentZgloszenia;
        this.cylinder=cylinder;
        this.deadline=deadline;
    }

    @Override
    public String toString()
    {
        return "Identyfikator: " + pid + " moment zgloszenia: " + momentZgloszenia + " deadline: " + deadline + " cylinder: " + cylinder;
    }
	public int getDeadline() {
		return deadline;
	}
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
	public int getPid() {
		return pid;
	}
	public int getMomentZgloszenia() {
		return momentZgloszenia;
	}
	public int getCylinder() {
		return cylinder;
	}

}
