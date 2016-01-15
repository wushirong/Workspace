//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//
//public class DiseasaeTreeTester {
//	private static Network n;
//	private static Person[] people;
//	
//	/** Return a representation of this tree. This representation is: 
//     * (1) the name of the person at the root, followed by
//     * (2) followed by the representations of the children.
//	 * There are two cases concerning the children. *
//	 * No children? Their representation is the empty string.
//	 * Children? Their representation is the representation of each child, with
//	 * a blank between adjacent ones, and delimited by "[" and "]".
//	 * Examples:
//	 * One-node tree: "A"
//	 * A root A with children B, C, D: "A[B C D]"
//	 * A root A with children B, C, D and B has a child F: "A[B[F] C D]" */
//	 public String toStringBrief(DiseaseTree t) {
//		 String res= t.getRoot().getName();
//		 Object[] childs= t.getChildren().toArray(); if (childs.length == 0) return res;
//		 res= res + "[";
//		 selectionSort1(childs);
//		 for (int k= 0; k < childs.length; k= k+1) {
//			 if (k > 0) res= res + " ";
//			 res= res + toStringBrief(((DiseaseTree)childs[k]));
//		 }	
//		 return res + "]";
//	 }
//	 
//	 /** Sort b --put its elements in ascending order.
//	 * Sort on the name of the person at the root of the DiseaseTree
//	 * Throw cast-class exception of b's elements are not DiseaseTree */
//	 public static void selectionSort1(Object[] b) { int j= 0;
//	 // {inv P: b[0..j-1] is sorted and b[0..j-1] <= b[j..]}
//	 // 0---------------j--------------- b.length //inv:b| sorted,<= | >= |
//	 // --------------------------------
//	 	while (j != b.length) {
//	 		// Put into p the index of smallest element in b[j..] 
//	 		int p= j;
//	 		for (int i= j+1; i != b.length; i++) {
//	 			String bi= ((DiseaseTree)b[i]).getRoot().getName(); 
//	 			String bp= ((DiseaseTree)b[p]).getRoot().getName(); 
//	 			if (bi.compareTo(bp) < 0) {
//	 				p= i;
//	 			} 
//	 		}
//	 		// Swap b[j] and b[p]
//	 		Object t= b[j]; b[j]= b[p]; b[p]= t; j= j+1;
//	 	} 
//	 }
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		n = new Network();
//		people = new Person[]{
//			new Person("A", n, 0), new Person("B", n, 0), new Person("C", n, 0), 
//			new Person("D", n, 0), new Person("E", n, 0), new Person("F", n, 0), 
//			new Person("G", n, 0), new Person("H", n, 0), new Person("I", n, 0), 
//			new Person("J", n, 0), new Person("K", n, 0), new Person("L", n, 0)
//		};
//	}
//	
	
//	@Test
//	 public void testOneNodeTree() {
//	 DiseaseTree t1= new DiseaseTree(people[0]);
//	 assertEquals("A", toStringBrief(t1));
//	 }
//
//	 @Test
//	 public void testAddAtLevel0() {
//	 DiseaseTree t1= new DiseaseTree(people[0]);
//	 t1.add(people[0], people[1]);
//	 assertEquals("A[B]", toStringBrief(t1));
//	 t1.add(people[0], people[2]);
//	 assertEquals("A[B C]", toStringBrief(t1));
//
//	 t1.add(people[0], people[3]);
//	 assertEquals("A[B C D]", toStringBrief(t1));
//	 }
//
//	 @Test
//	 public void testAddAtLevel1() {
//	 DiseaseTree t1= new DiseaseTree(people[0]);
//	 t1.add(people[0], people[1]);
//	 t1.add(people[0], people[2]);
//	 t1.add(people[0], people[3]);
//
//	 t1.add(people[1], people[5]);
//	 assertEquals("A[B[F] C D]", toStringBrief(t1));
//	 t1.add(people[1], people[4]);
//	 assertEquals("A[B[E F] C D]", toStringBrief(t1));
//	 t1.add(people[4], people[6]);
//	 assertEquals("A[B[E[G] F] C D]", toStringBrief(t1));
//
//	 t1.add(people[0], people[7]);
//	 assertEquals("A[B[E[G] F] C D H]", toStringBrief(t1));
//	 }
//
//	 @Test
//	 public void testSize(){
//		 DiseaseTree t1= new DiseaseTree(people[0]);
//		 assertEquals(1,t1.size());
//		 t1.add(people[0], people[1]);
//		 assertEquals(2,t1.size());
//		 t1.add(people[0], people[2]);
//		 assertEquals(3,t1.size());
//		 t1.add(people[0], people[3]);
//		 assertEquals(4,t1.size());
//		 
//	 }
//	 
//	 @Test
//	 public void testdepthOf(){
//		 DiseaseTree t1= new DiseaseTree(people[0]);
//		 t1.add(people[0], people[1]);
//		 t1.add(people[0], people[2]);
//		 t1.add(people[0], people[3]);		 
//		 t1.add(people[1], people[4]);
//		 t1.add(people[1], people[5]);
//		 t1.add(people[2], people[6]);
//		 t1.add(people[2], people[7]);
//		 t1.add(people[3], people[8]);
//		 t1.add(people[6], people[9]);
//		 t1.add(people[9], people[10]);
//		 
//		 assertEquals(-1, t1.depthOf(people[11]));
//		 assertEquals(0, t1.depthOf(people[0]));
//		 assertEquals(1, t1.depthOf(people[1]));
//		 assertEquals(2, t1.depthOf(people[4]));
//		 assertEquals(3, t1.depthOf(people[9]));
//		 assertEquals(4, t1.depthOf(people[10]));
//	 }
//	 
//	 @Test
//	 public void testwidthAtDepth(){
//		 DiseaseTree t1= new DiseaseTree(people[0]);
//		 t1.add(people[0], people[1]);
//		 t1.add(people[0], people[2]);
//		 t1.add(people[0], people[3]);		 
//		 t1.add(people[1], people[4]);
//		 t1.add(people[1], people[5]);
//		 t1.add(people[2], people[6]);
//		 t1.add(people[2], people[7]);
//		 t1.add(people[3], people[8]);
//		 t1.add(people[6], people[9]);
//		 t1.add(people[9], people[10]);
//		 
//		 assertEquals(1, t1.widthAtDepth(0));
//		 assertEquals(3, t1.widthAtDepth(1));
//		 assertEquals(5, t1.widthAtDepth(2));
//		 assertEquals(1, t1.widthAtDepth(3));
//		 assertEquals(1, t1.widthAtDepth(4));
//	 }
	 
//	 public String helpRoute(List<Person> path){
//		 if(path == null) return null;
//		 StringBuilder res = new StringBuilder();
//		 res.append("[ ");
//		 for(Person p : path) {
//			 res.append(p.getName() + " ");
//		 }
//		 res.append("]");
//		 return res.toString();
//	 }
//	 @Test
//	 public void testgetDiseaseRouteTo(){
//		 DiseaseTree t1= new DiseaseTree(people[0]);
//		 DiseaseTree dt1 = t1.add(people[0], people[1]);
//		 DiseaseTree dt2 = t1.add(people[0], people[2]);
//		 DiseaseTree dt3 = t1.add(people[0], people[3]);		 
//		 DiseaseTree dt4 = t1.add(people[1], people[4]);
//		 DiseaseTree dt5 = t1.add(people[1], people[5]);
//		 DiseaseTree dt6 = t1.add(people[2], people[6]);
//		 DiseaseTree dt7 = t1.add(people[2], people[7]);
//		 DiseaseTree dt8 = t1.add(people[3], people[8]);
//		 DiseaseTree dt9 = t1.add(people[6], people[9]);
//		 DiseaseTree dt10 =t1.add(people[9], people[10]);
//		 
//		 assertEquals("[ A B E ]", helpRoute(t1.getDiseaseRouteTo(people[4])));
//		 assertEquals("[ A C G J K ]", helpRoute(t1.getDiseaseRouteTo(people[10])));
//		 assertEquals("[ A ]", helpRoute(t1.getDiseaseRouteTo(people[0])));
//		 assertEquals(null, helpRoute(t1.getDiseaseRouteTo(people[11])));
//		 assertEquals(null, helpRoute(dt1.getDiseaseRouteTo(people[2])));
//	 }
	 
//	 @Test
//	 public void testgetSharedAncestorOf(){
//		 DiseaseTree t1= new DiseaseTree(people[0]);
//		 DiseaseTree dt1 = t1.add(people[0], people[1]);
//		 DiseaseTree dt2 = t1.add(people[0], people[2]);
//		 DiseaseTree dt3 = t1.add(people[0], people[3]);		 
//		 DiseaseTree dt4 = t1.add(people[1], people[4]);
//		 DiseaseTree dt5 = t1.add(people[1], people[5]);
//		 DiseaseTree dt6 = t1.add(people[2], people[6]);
//		 DiseaseTree dt7 = t1.add(people[2], people[7]);
//		 DiseaseTree dt8 = t1.add(people[3], people[8]);
//		 DiseaseTree dt9 = t1.add(people[6], people[9]);
//		 DiseaseTree dt10 =t1.add(people[9], people[10]);
//		 
//		 assertEquals(people[0], t1.getSharedAncestorOf(people[1], people[2]));
//		 assertEquals(people[2], t1.getSharedAncestorOf(people[7], people[9]));
//		 assertEquals(null, dt3.getSharedAncestorOf(people[4], people[8]));
//		 assertEquals(null, dt3.getSharedAncestorOf(people[3], people[3]));
//		 assertEquals(null, t1.getSharedAncestorOf(people[0], people[2]));
//		 assertEquals(null, dt6.getSharedAncestorOf(people[10], people[7]));
//	 }
//	 
//	 @Test
//	 public void testequals(){
//		 DiseaseTree t1= new DiseaseTree(people[0]);
//		 DiseaseTree dt1 = t1.add(people[0], people[1]);
//		 DiseaseTree dt2 = t1.add(people[0], people[2]);
//		 DiseaseTree dt3 = t1.add(people[0], people[3]);		 
//		 DiseaseTree dt4 = t1.add(people[1], people[4]);
//		 DiseaseTree dt5 = t1.add(people[1], people[5]);
//		 DiseaseTree dt6 = t1.add(people[2], people[6]);
//		 DiseaseTree dt7 = t1.add(people[2], people[7]);
//		 DiseaseTree dt8 = t1.add(people[3], people[8]);
//		 DiseaseTree dt9 = t1.add(people[6], people[9]);
//		 DiseaseTree dt10 =t1.add(people[9], people[10]);
//		 
//		 DiseaseTree t2= new DiseaseTree(people[0]);
//		 DiseaseTree ct1 = t2.add(people[0], people[1]);
//		 DiseaseTree ct2 = t2.add(people[0], people[2]);
//		 DiseaseTree ct3 = t2.add(people[0], people[3]);		 
//		 DiseaseTree ct4 = t2.add(people[1], people[4]);
//		 DiseaseTree ct5 = t2.add(people[1], people[5]);
//		 DiseaseTree ct6 = t2.add(people[2], people[6]);
//		 DiseaseTree ct7 = t2.add(people[2], people[7]);
//		 DiseaseTree ct8 = t2.add(people[3], people[8]);
//		 DiseaseTree ct9 = t2.add(people[6], people[9]);
//		 DiseaseTree ct10 =t2.add(people[9], people[10]);
//		 
//		 assertTrue(t1.equals(t1));
//		 assertTrue(dt2.equals(ct2));
//		 assertTrue(dt3.equals(ct3));
//		 assertTrue(dt4.equals(ct4));
//		 assertTrue(dt5.equals(ct5));
//		 assertTrue(dt6.equals(ct6));
//		 assertTrue(dt7.equals(ct7));
//		 assertTrue(dt8.equals(ct8));
//		 assertTrue(dt9.equals(ct9));
//		 assertTrue(dt10.equals(ct10));
//		 assertTrue(dt3.equals(dt3));
//		 assertTrue(! dt2.equals(ct3));
//		 
//	 }
//}
import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class DiseaseTreeTest {
	private static Network n;
	private static Person[] people;
	
	/** Return a representation of this tree. This representation is: 
     * (1) the name of the person at the root, followed by
     * (2) followed by the representations of the children.
	 * There are two cases concerning the children. *
	 * No children? Their representation is the empty string.
	 * Children? Their representation is the representation of each child, with
	 * a blank between adjacent ones, and delimited by "[" and "]".
	 * Examples:
	 * One-node tree: "A"
	 * A root A with children B, C, D: "A[B C D]"
	 * A root A with children B, C, D and B has a child F: "A[B[F] C D]" */
	 public String toStringBrief(DiseaseTree t) {
		 String res= t.getRoot().getName();
		 Object[] childs= t.getChildren().toArray(); if (childs.length == 0) return res;
		 res= res + "[";
		 selectionSort1(childs);
		 for (int k= 0; k < childs.length; k= k+1) {
			 if (k > 0) res= res + " ";
			 res= res + toStringBrief(((DiseaseTree)childs[k]));
		 }	
		 return res + "]";
	 }
	 
	 /** Sort b --put its elements in ascending order.
	 * Sort on the name of the person at the root of the DiseaseTree
	 * Throw cast-class exception of b's elements are not DiseaseTree */
	 public static void selectionSort1(Object[] b) { int j= 0;
	 // {inv P: b[0..j-1] is sorted and b[0..j-1] <= b[j..]}
	 // 0---------------j--------------- b.length //inv:b| sorted,<= | >= |
	 // --------------------------------
	 	while (j != b.length) {
	 		// Put into p the index of smallest element in b[j..] 
	 		int p= j;
	 		for (int i= j+1; i != b.length; i++) {
	 			String bi= ((DiseaseTree)b[i]).getRoot().getName(); 
	 			String bp= ((DiseaseTree)b[p]).getRoot().getName(); 
	 			if (bi.compareTo(bp) < 0) {
	 				p= i;
	 			} 
	 		}
	 		// Swap b[j] and b[p]
	 		Object t= b[j]; b[j]= b[p]; b[p]= t; j= j+1;
	 	} 
	 }
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		n = new Network();
		people = new Person[]{
			new Person("A", n, 0), new Person("B", n, 0), new Person("C", n, 0), 
			new Person("D", n, 0), new Person("E", n, 0), new Person("F", n, 0), 
			new Person("G", n, 0), new Person("H", n, 0), new Person("I", n, 0), 
			new Person("J", n, 0), new Person("K", n, 0), new Person("L", n, 0)
		};
	}

	@Test
	public void testOneNodeTree() {
		DiseaseTree t1= new DiseaseTree(people[0]);
		assertEquals("A", toStringBrief(t1)); 
	}
	
	@Test
	public void testAddAtLevel0() {
		DiseaseTree t1= new DiseaseTree(people[0]); 
		t1.add(people[0], people[1]); 
		assertEquals("A[B]", toStringBrief(t1));
		t1.add(people[0], people[2]); 
		assertEquals("A[B C]", toStringBrief(t1));
		t1.add(people[0], people[3]);
		assertEquals("A[B C D]", toStringBrief(t1)); 
	}
	
	@Test
	public void testAddAtLevel1() {
		DiseaseTree t1= new DiseaseTree(people[0]); 
		t1.add(people[0], people[1]); 
		t1.add(people[0], people[2]); 
		t1.add(people[0], people[3]);
		t1.add(people[1], people[5]); 
		assertEquals("A[B[F] C D]", toStringBrief(t1));
		t1.add(people[1], people[4]); 
		assertEquals("A[B[E F] C D]", toStringBrief(t1));
		t1.add(people[4], people[6]); 
		assertEquals("A[B[E[G] F] C D]", toStringBrief(t1));
		t1.add(people[0], people[7]);
		assertEquals("A[B[E[G] F] C D H]", toStringBrief(t1)); 
	}
	
	@Test
	public void testSize() {
		DiseaseTree dt = new DiseaseTree(people[0]);
		DiseaseTree dt1 = dt.add(people[0], people[1]); 
		DiseaseTree dt2 = dt.add(people[0], people[2]);
		DiseaseTree dt3 = dt.add(people[1], people[3]); 
		DiseaseTree dt4 = dt.add(people[1], people[4]);
		DiseaseTree dt5 = dt.add(people[2], people[5]); 
		DiseaseTree dt6 = dt.add(people[2], people[6]);
		DiseaseTree dt7 = dt.add(people[3], people[7]); 
		DiseaseTree dt8 = dt.add(people[3], people[8]);
		DiseaseTree dt9 = dt.add(people[3], people[9]);
		DiseaseTree dt10 = dt.add(people[5], people[10]); 
		DiseaseTree dt11 = dt.add(people[6], people[11]);
	
		//Test depth 0
		assertEquals(12, dt.size());
		
		//Test depth 1
		assertEquals(6, dt1.size());
		assertEquals(5, dt2.size());
		
		//Test depth 2
		assertEquals(4, dt3.size());
		assertEquals(1, dt4.size());
		assertEquals(2, dt5.size());
		assertEquals(2, dt6.size());
		
		//Test depth 2
		assertEquals(1, dt7.size());
		assertEquals(1, dt8.size());
		assertEquals(1, dt9.size());
		assertEquals(1, dt10.size());
		assertEquals(1, dt11.size());
	}
	
	@Test
	public void testDepthOf() {
		DiseaseTree dt = new DiseaseTree(people[0]);
		DiseaseTree dt1 = dt.add(people[0], people[1]); 
		DiseaseTree dt2 = dt.add(people[0], people[2]);
		DiseaseTree dt3 = dt.add(people[1], people[3]); 
		DiseaseTree dt4 = dt.add(people[1], people[4]);
		DiseaseTree dt5 = dt.add(people[2], people[5]); 
		DiseaseTree dt6 = dt.add(people[2], people[6]);
		DiseaseTree dt7 = dt.add(people[3], people[7]); 
		DiseaseTree dt8 = dt.add(people[3], people[8]);
		DiseaseTree dt9 = dt.add(people[3], people[9]);
		DiseaseTree dt10 = dt.add(people[5], people[10]); 
		DiseaseTree dt11 = dt.add(people[6], people[11]);
		
		//Test dt depthOf
		assertEquals(0, dt.depthOf(people[0]));
		assertEquals(1, dt.depthOf(people[1]));
		assertEquals(1, dt.depthOf(people[2]));
		assertEquals(2, dt.depthOf(people[3]));
		assertEquals(2, dt.depthOf(people[4]));
		assertEquals(2, dt.depthOf(people[5]));
		assertEquals(2, dt.depthOf(people[6]));
		assertEquals(3, dt.depthOf(people[7]));
		assertEquals(3, dt.depthOf(people[8]));
		assertEquals(3, dt.depthOf(people[9]));
		assertEquals(3, dt.depthOf(people[10]));
		assertEquals(3, dt.depthOf(people[11]));
		
		//Test dt1 depthOf
		assertEquals(0, dt1.depthOf(people[1]));
		assertEquals(1, dt1.depthOf(people[3]));
		assertEquals(1, dt1.depthOf(people[4]));
		assertEquals(2, dt1.depthOf(people[7]));
		assertEquals(2, dt1.depthOf(people[8]));
		assertEquals(2, dt1.depthOf(people[9]));
		
		//Test dt2 depthOf
		assertEquals(0, dt2.depthOf(people[2]));
		assertEquals(1, dt2.depthOf(people[5]));
		assertEquals(1, dt2.depthOf(people[6]));
		assertEquals(2, dt2.depthOf(people[10]));
		assertEquals(2, dt2.depthOf(people[11]));
		
		//Test dt3 depthOf
		assertEquals(0, dt3.depthOf(people[3]));
		assertEquals(1, dt3.depthOf(people[7]));
		assertEquals(1, dt3.depthOf(people[8]));
		assertEquals(1, dt3.depthOf(people[9]));
		
		//Test dt10
		assertEquals(0, dt10.depthOf(people[10]));
	}
	
	@Test
	public void testWidthAtDepth() {
		DiseaseTree dt = new DiseaseTree(people[0]);
		DiseaseTree dt1 = dt.add(people[0], people[1]); 
		DiseaseTree dt2 = dt.add(people[0], people[2]);
		DiseaseTree dt3 = dt.add(people[1], people[3]); 
		DiseaseTree dt4 = dt.add(people[1], people[4]);
		DiseaseTree dt5 = dt.add(people[2], people[5]); 
		DiseaseTree dt6 = dt.add(people[2], people[6]);
		DiseaseTree dt7 = dt.add(people[3], people[7]); 
		DiseaseTree dt8 = dt.add(people[3], people[8]);
		DiseaseTree dt9 = dt.add(people[3], people[9]);
		DiseaseTree dt10 = dt.add(people[5], people[10]); 
		DiseaseTree dt11 = dt.add(people[6], people[11]);
		
		assertEquals(1, dt.widthAtDepth(0));
		assertEquals(2, dt.widthAtDepth(1));
		assertEquals(4, dt.widthAtDepth(2));
		assertEquals(5, dt.widthAtDepth(3));
		
		assertEquals(1, dt1.widthAtDepth(0));
		assertEquals(2, dt1.widthAtDepth(1));
		assertEquals(3, dt1.widthAtDepth(2));
		
		assertEquals(1, dt2.widthAtDepth(0));
		assertEquals(2, dt2.widthAtDepth(1));
		assertEquals(2, dt2.widthAtDepth(2));
		
		assertEquals(1, dt3.widthAtDepth(0));
		assertEquals(3, dt3.widthAtDepth(1));
		
		assertEquals(1, dt10.widthAtDepth(0));
	}
	
	public String listToString(List<Person> list) {
		if(list == null) return null;
		StringBuilder res = new StringBuilder();
		res.append("[ ");
		for(Person p : list) {
			res.append(p.getName() + " ");
		}
		res.append("]");
		return res.toString();
	}
	
	@Test
	public void testGetDiseaseRoute() {
		DiseaseTree dt = new DiseaseTree(people[0]);
		DiseaseTree dt1 = dt.add(people[0], people[1]); 
		DiseaseTree dt2 = dt.add(people[0], people[2]);
		DiseaseTree dt3 = dt.add(people[1], people[3]); 
		DiseaseTree dt4 = dt.add(people[1], people[4]);
		DiseaseTree dt5 = dt.add(people[2], people[5]); 
		DiseaseTree dt6 = dt.add(people[2], people[6]);
		DiseaseTree dt7 = dt.add(people[3], people[7]); 
		DiseaseTree dt8 = dt.add(people[3], people[8]);
		DiseaseTree dt9 = dt.add(people[3], people[9]);
		DiseaseTree dt10 = dt.add(people[5], people[10]); 
		DiseaseTree dt11 = dt.add(people[6], people[11]);
		
		//Test from dt.root to every other node
		assertEquals("[ A ]",listToString(dt.getDiseaseRouteTo(people[0])));
		assertEquals("[ A B ]",listToString(dt.getDiseaseRouteTo(people[1])));
		assertEquals("[ A C ]",listToString(dt.getDiseaseRouteTo(people[2])));
		assertEquals("[ A B D ]",listToString(dt.getDiseaseRouteTo(people[3])));
		assertEquals("[ A B E ]",listToString(dt.getDiseaseRouteTo(people[4])));
		assertEquals("[ A C F ]",listToString(dt.getDiseaseRouteTo(people[5])));
		assertEquals("[ A C G ]",listToString(dt.getDiseaseRouteTo(people[6])));
		assertEquals("[ A B D H ]",listToString(dt.getDiseaseRouteTo(people[7])));
		assertEquals("[ A B D I ]",listToString(dt.getDiseaseRouteTo(people[8])));
		assertEquals("[ A B D J ]",listToString(dt.getDiseaseRouteTo(people[9])));
		assertEquals("[ A C F K ]",listToString(dt.getDiseaseRouteTo(people[10])));
		assertEquals("[ A C G L ]",listToString(dt.getDiseaseRouteTo(people[11])));
		
		//Test from dt1.root to every other node IN its children
		assertEquals("[ B ]",listToString(dt1.getDiseaseRouteTo(people[1])));
		assertEquals("[ B D ]",listToString(dt1.getDiseaseRouteTo(people[3])));
		assertEquals("[ B E ]",listToString(dt1.getDiseaseRouteTo(people[4])));
		assertEquals("[ B D H ]",listToString(dt1.getDiseaseRouteTo(people[7])));
		assertEquals("[ B D I ]",listToString(dt1.getDiseaseRouteTo(people[8])));
		assertEquals("[ B D J ]",listToString(dt1.getDiseaseRouteTo(people[9])));
		
		//Test null
		assertEquals(null,listToString(dt1.getDiseaseRouteTo(people[0])));
		assertEquals(null,listToString(dt1.getDiseaseRouteTo(people[2])));
		assertEquals(null,listToString(dt1.getDiseaseRouteTo(people[5])));
		assertEquals(null,listToString(dt1.getDiseaseRouteTo(people[6])));
		assertEquals(null,listToString(dt1.getDiseaseRouteTo(people[10])));
		assertEquals(null,listToString(dt1.getDiseaseRouteTo(people[11])));
		assertEquals(null,listToString(dt2.getDiseaseRouteTo(people[1])));
		assertEquals(null,listToString(dt11.getDiseaseRouteTo(people[0])));
	}
	
	@Test
	public void testGetSharedAnc() {
		DiseaseTree dt = new DiseaseTree(people[0]);
		DiseaseTree dt1 = dt.add(people[0], people[1]); 
		DiseaseTree dt2 = dt.add(people[0], people[2]);
		DiseaseTree dt3 = dt.add(people[1], people[3]); 
		DiseaseTree dt4 = dt.add(people[1], people[4]);
		DiseaseTree dt5 = dt.add(people[2], people[5]); 
		DiseaseTree dt6 = dt.add(people[2], people[6]);
		DiseaseTree dt7 = dt.add(people[3], people[7]); 
		DiseaseTree dt8 = dt.add(people[3], people[8]);
		DiseaseTree dt9 = dt.add(people[3], people[9]);
		DiseaseTree dt10 = dt.add(people[5], people[10]); 
		DiseaseTree dt11 = dt.add(people[6], people[11]);
		
		assertEquals("A", dt.getSharedAncestorOf(people[1], people[2]).getName());
		assertEquals("A", dt.getSharedAncestorOf(people[7], people[6]).getName());
		assertEquals("A", dt.getSharedAncestorOf(people[8], people[6]).getName());
		assertEquals("A", dt.getSharedAncestorOf(people[9], people[6]).getName());
		assertEquals("A", dt.getSharedAncestorOf(people[0], people[1]).getName());
		assertEquals("C", dt.getSharedAncestorOf(people[5], people[6]).getName());
		assertEquals(null, dt.getSharedAncestorOf(null, people[2]));
		assertEquals(null, dt.getSharedAncestorOf(null, null));
		assertEquals(null, dt.getSharedAncestorOf(people[2], null));
		assertEquals(null, dt1.getSharedAncestorOf(people[3], people[6]));
		assertEquals("B", dt1.getSharedAncestorOf(people[1], people[1]).getName());
	}
	
	@Test
	public void testEquals() {
		DiseaseTree dt = new DiseaseTree(people[0]);
		DiseaseTree dt1 = dt.add(people[0], people[1]); 
		DiseaseTree dt2 = dt.add(people[0], people[2]);
		DiseaseTree dt3 = dt.add(people[1], people[3]); 
		DiseaseTree dt4 = dt.add(people[1], people[4]);
		DiseaseTree dt5 = dt.add(people[2], people[5]); 
		DiseaseTree dt6 = dt.add(people[2], people[6]);
		DiseaseTree dt7 = dt.add(people[3], people[7]); 
		DiseaseTree dt8 = dt.add(people[3], people[8]);
		DiseaseTree dt9 = dt.add(people[3], people[9]);
		DiseaseTree dt10 = dt.add(people[5], people[10]); 
		DiseaseTree dt11 = dt.add(people[6], people[11]);
		
		//Copy of dt
		DiseaseTree ct = new DiseaseTree(people[0]);
		DiseaseTree ct1 = ct.add(people[0], people[1]); 
		DiseaseTree ct2 = ct.add(people[0], people[2]);
		DiseaseTree ct3 = ct.add(people[1], people[3]); 
		DiseaseTree ct4 = ct.add(people[1], people[4]);
		DiseaseTree ct5 = ct.add(people[2], people[5]); 
		DiseaseTree ct6 = ct.add(people[2], people[6]);
		DiseaseTree ct7 = ct.add(people[3], people[7]); 
		DiseaseTree ct8 = ct.add(people[3], people[8]);
		DiseaseTree ct9 = ct.add(people[3], people[9]);
		DiseaseTree ct10 = ct.add(people[5], people[10]); 
		DiseaseTree ct11 = ct.add(people[6], people[11]);
		
		//Test true
		assertTrue(dt.equals(ct));
		assertTrue(dt1.equals(ct1));
		assertTrue(dt2.equals(ct2));
		assertTrue(dt3.equals(ct3));
		assertTrue(dt4.equals(ct4));
		assertTrue(dt5.equals(ct5));
		assertTrue(dt6.equals(ct6));
		assertTrue(dt7.equals(ct7));
		assertTrue(dt8.equals(ct8));
		assertTrue(dt9.equals(ct9));
		assertTrue(dt10.equals(ct10));
		assertTrue(dt11.equals(ct11));
		
		//Test false;
		assertFalse(dt.equals(ct1));
		assertFalse(dt1.equals(ct2));
		assertFalse(dt2.equals(ct3));
		assertFalse(dt3.equals(ct4));
		assertFalse(dt4.equals(ct5));
		assertFalse(dt5.equals(ct6));
		assertFalse(dt6.equals(ct7));
		assertFalse(dt7.equals(ct8));
		assertFalse(dt8.equals(ct9));
		assertFalse(dt9.equals(ct10));
		assertFalse(dt10.equals(ct11));
	}
}

