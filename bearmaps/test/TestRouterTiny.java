package bearmaps.test;

import org.junit.Before;
import org.junit.Test;
import bearmaps.deploy.Router;
import bearmaps.deploy.AugmentedStreetMapGraph;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by hug, 4/9/2018. Basic tests for routing on the tiny graph.
 * This graph is so small you can draw it out by hand and visually inspect the results!
 */
public class TestRouterTiny {
    private static final String OSM_DB_PATH_TINY = "data//proj2c_xml/tiny-clean.osm.xml";
    private static AugmentedStreetMapGraph graphTiny;
    private static boolean initialized = false;

    @Before
    public void setUp() throws Exception {
        if (initialized) {
            return;
        }
        graphTiny = new AugmentedStreetMapGraph(OSM_DB_PATH_TINY);
        initialized = true;
    }

    @Test
    public void test22to66() {
        List<Long> actual = Router.shortestPath(graphTiny, 0.2, 38.2, 0.6, 38.6);
        List<Long> expected = new ArrayList<>();
        expected.add(22L);
        expected.add(46L);
        expected.add(66L);
        assertEquals("Best path from 22 to 66 is incorrect.", expected, actual);
    }

    @Test
    public void test22to11() {
        List<Long> actual = Router.shortestPath(graphTiny, 0.2, 38.2, 0.1, 38.1);
        List<Long> expected = new ArrayList<>();
        expected.add(22L);
        expected.add(11L);
        assertEquals(expected, actual);
    }

    @Test
    public void test41to46() {
        List<Long> actual = Router.shortestPath(graphTiny, 0.4, 38.1, 0.4, 38.6);
        List<Long> expected = new ArrayList<>();
        expected.add(41L);
        expected.add(63L);
        expected.add(66L);
        expected.add(46L);
        assertEquals(expected, actual);
    }

    @Test
    public void test66to55() {
        List<Long> actual = Router.shortestPath(graphTiny, 0.6, 38.6, 0.5, 38.5);
        List<Long> expected = new ArrayList<>();
        expected.add(66L);
        expected.add(63L);
        expected.add(55L);
        assertEquals(expected, actual);
    }
}
