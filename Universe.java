import java.util.ArrayList;
import java.util.List;

public class Universe {
    private List<Galaxy> galaxies;

    public Universe() {
        galaxies = new ArrayList<>();
    }

    public void addGalaxy(Galaxy galaxy) {
        galaxies.add(galaxy);
    }

    public List<Galaxy> getGalaxies() {
        return galaxies;
    }
}

class Galaxy {
    private String name;
    private List<System> systems;

    public Galaxy(String name) {
        this.name = name;
        systems = new ArrayList<>();
    }

    public void addSystem(System system) {
        systems.add(system);
    }

    public String getName() {
        return name;
    }

    public List<System> getSystems() {
        return systems;
    }
}

class System {
    private String name;
    private List<Planet> planets;

    public System(String name) {
        this.name = name;
        planets = new ArrayList<>();
    }

    public void addPlanet(Planet planet) {
        planets.add(planet);
    }

    public String getName() {
        return name;
    }

    public List<Planet> getPlanets() {
        return planets;
    }
}

class Planet {
    private String name;
    private int size;

    public Planet(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}
