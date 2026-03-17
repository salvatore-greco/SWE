package DomainModel;

public class Library {

    private int budget;
    private final String name;

    public Library(int budget, String name) {
        this.budget = budget;
        this.name = name;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getName() {
        return name;
    }
}
