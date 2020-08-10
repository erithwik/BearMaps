package bearmaps.deploy;

import bearmaps.graph.streetmap.Node;
import bearmaps.graph.streetmap.StreetMapGraph;
import bearmaps.priorityqueue.Point;
import bearmaps.priorityqueue.WeirdPointSet;
import bearmaps.deploy.utils.TrieSET;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 * @author Alan Yao, Josh Hug, Rithwik Ediga Lakhamsani
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private HashMap<Point, Node> pnmap;
    private WeirdPointSet tool;
    private HashMap<String, ArrayList<Node>> trieHelper;
    private TrieSET trie;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        List<Point> points = new ArrayList<>();

        trie = new TrieSET();
        trieHelper = new HashMap<>();
        pnmap = new HashMap<>();

        for (Node n : nodes) {
            if (neighbors(n.id()).size() > 0) {
                Point temp = new Point(n.lon(), n.lat());
                pnmap.put(temp, n);
                points.add(temp);
            }
            if (n.name() != null) {
                String cleanedName = cleanString(n.name());
                trie.add(cleanedName);
                if (trieHelper.get(cleanedName) == null) {
                    ArrayList<Node> temp2 = new ArrayList<>();
                    trieHelper.put(cleanedName, temp2);
                }
                trieHelper.get(cleanedName).add(n);
            }
        }

        tool = new WeirdPointSet(points);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        return (pnmap.get(tool.nearest(lon, lat))).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     *
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> returner = new ArrayList<>();
        HashSet<String> tempSet = new HashSet<>();
        for (String s : trie.keysWithPrefix(cleanString(prefix))) {
            List<Node> t = trieHelper.get(s);
            for (Node temp : t) {
                if (!tempSet.contains(temp.name())) {
                    tempSet.add(temp.name());
                    returner.add(temp.name());
                }
            }
        }
        return returner;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     *
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        ArrayList<Node> temp = trieHelper.get(cleanString(locationName));
        ArrayList<Map<String, Object>> returner = new ArrayList<>();
        for (Node n : temp) {
            HashMap<String, Object> t = new HashMap<>();
            t.put("lat", n.lat());
            t.put("lon", n.lon());
            t.put("name", n.name());
            t.put("id", n.id());
            returner.add(t);
        }
        return returner;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
