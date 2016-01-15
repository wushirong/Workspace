package list;

public class testlist {
	public static void testfunction(){
		DList testlist=new DList();
		System.out.println(testlist.length());
		testlist.insertFront(2);
		testlist.insertFront(3);
		testlist.insertFront(4);
		testlist.insertBack(5);
		testlist.insertBack(6);
		String wholelist=testlist.toString();
		System.out.println(wholelist);
		String frontnode=testlist.front().item.toString();
		System.out.println(frontnode);
		testlist.insertAfter(9,testlist.front());
		String wholelist2=testlist.toString();
		System.out.println(wholelist2);
		testlist.insertBefore(8,testlist.front());
		String wholelist3=testlist.toString();
		System.out.println(wholelist3);
		System.out.println(testlist.next(testlist.front()).item);
		
		
		
	}

	public static void main(String[] args){
		testfunction();
	}
}
