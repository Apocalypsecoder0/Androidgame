import java.util.ArrayList;
import java.util.List;

public class Technology {
    private String name;
    private String description;
    private List<Technology> prerequisites;

    public Technology(String name, String description) {
        this.name = name;
        this.description = description;
        this.prerequisites = new ArrayList<>();
    }

    public void addPrerequisite(Technology technology) {
        prerequisites.add(technology);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Technology> getPrerequisites() {
        return prerequisites;
    }
}
