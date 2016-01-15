import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class TreeMapTester {

	@Test
    public void testGetSplit() {
        TreeMap tree= new TreeMap();
 
        // Put into b two nodes with weights 6 and 2 and test
        ArrayList<TreeMap.Node> b= new ArrayList<TreeMap.Node>();
        b.add(tree.getNewNode(null, null, 6, null));
        b.add(tree.getNewNode(null, null, 2, null));
        TreeMap.Wrapper2 w= TreeMap.getSplit(b, 0, 1);
        assertEquals(0, w.k);
        assertEquals(.75, w.d, .0000000000001);
 
        // Put into b 10 elements with weights 1
        b= new ArrayList<TreeMap.Node>();
        for (int j= 0; j < 10; j= j+1) {
            b.add(tree.getNewNode(null, null, 1, null));
        }
 
        // Test using only part of array
        w= TreeMap.getSplit(b, 4, 8);
        assertEquals(5, w.k);
        assertEquals(.4, w.d, .0000000000001);
    }

}
