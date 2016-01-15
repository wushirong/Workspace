import static org.junit.Assert.*;

import org.junit.Test;



public class DLinkedListTester {

	@Test
	//test toString, toStringRev
	public void testConstructor(){
		DLinkedList<Integer> d= new DLinkedList();
		assertEquals("[]",d.toString());
		assertEquals("[]",d.toStringRev());
		assertEquals(0,d.size());
		assertEquals(null,d.getHead());
		assertEquals(null,d.getTail());
	}

	//test Prepend
	public void testPrepend(){
		DLinkedList<Integer> d= new DLinkedList();

		d.prepend(1);
		assertEquals("[1]",d.toString());
		assertEquals("[1]",d.toStringRev());
		assertEquals(1,d.size());
		assertEquals((Integer)1,d.getHead().getData());
		assertEquals((Integer)1,d.getTail().getData());

	}

	//test Append
	public void testAppend(){
		DLinkedList<Integer> d= new DLinkedList();

		d.append(1);
		assertEquals("[1]",d.toString());
		assertEquals("[1]",d.toStringRev());
		assertEquals(1,d.size());
		assertEquals((Integer)1,d.getHead().getData());
		assertEquals((Integer)1,d.getTail().getData());

		d.append(2);
		assertEquals("[1, 2]",d.toString());
		assertEquals("[2, 1]",d.toStringRev());
		assertEquals(2,d.size());
		assertEquals((Integer)1,d.getHead().getData());
		assertEquals((Integer)2,d.getTail().getData());


	}

	//test insertAfter
	public void testinsertAfter(){
		DLinkedList<Integer> d= new DLinkedList();

		d.append(1);
		d.insertAfter(2,d.getHead());
		assertEquals("[1, 2]",d.toString());
		assertEquals("[2, 1]",d.toStringRev());
		assertEquals(2,d.size());
		assertEquals((Integer)1,d.getHead().getData());
		assertEquals((Integer)2,d.getTail().getData());

		d.insertAfter(3,d.getTail());
		assertEquals("[1, 2, 3]",d.toString());
		assertEquals("[3, 2, 1]",d.toStringRev());
		assertEquals(3,d.size());
		assertEquals((Integer)1,d.getHead().getData());
		assertEquals((Integer)3,d.getTail().getData());

	}
	

	@Test
	//test insertBefore
	public void testinsertBefore(){
		DLinkedList<Integer> d= new DLinkedList();

		d.append(2);
		d.insertBefore(1,d.getHead());
		assertEquals("[1, 2]",d.toString());
		assertEquals("[2, 1]",d.toStringRev());
		assertEquals(2,d.size());
		assertEquals((Integer)1,d.getHead().getData());
		assertEquals((Integer)2,d.getTail().getData());

		d.insertBefore(3,d.getTail());
		assertEquals("[1, 3, 2]",d.toString());
		assertEquals("[2, 3, 1]",d.toStringRev());
		assertEquals(3,d.size());
		assertEquals((Integer)1,d.getHead().getData());
		assertEquals((Integer)2,d.getTail().getData());

	}

	//test remove
	public void testremove(){
		DLinkedList<Integer> d= new DLinkedList();
		
		d.prepend(1);
		d.insertAfter(2,d.getHead());
		d.append(6);
		d.insertBefore(5,d.getTail());
		d.insertAfter(3,d.getHead().succ());
		d.insertBefore(4,d.getTail().pred());
		assertEquals("[1, 2, 3, 4, 5, 6]",d.toString());
		d.remove(d.getHead());
		assertEquals("[2, 3, 4, 5, 6]",d.toString());

	}


}
