package bearmaps.graph.streetmap;

/**
 * Vertex representation for the graph.
 *
 * @author Kevin Lowe
 */
public class Node {
    private long id;
    private double lat;
    private double lon;

    private String name;

    private Node(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public static Node of(long id, double lat, double lon) {
        return new Node(id, lat, lon);
    }

    public long id() {
        return id;
    }

    public double lat() {
        return lat;
    }

    public double lon() {
        return lon;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Node otherNode = (Node) other;
        return this.id == otherNode.id;
    }

    @Override
    public int hashCode() {
        return (int) this.id;
    }

    @Override
    public String toString() {
        return String.format("Node id: %d, lat: %.10f, lon: %.10f", id, lat, lon);
    }
}
