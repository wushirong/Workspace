/**sw782:shirong,wu  time spent:5 hours, 00 minutes.
 * An instance maintains info about the PhD of a person.
 */
import static org.junit.Assert.*;
import org.junit.Test;


public class PhDTester {
	@Test
	//test case for groupA functions
	public void testA() {
		PhD groupA=new PhD("Jason",'M',1994,4);   //construct a PhD 
		assertEquals("Jason",groupA.getName());   //test the getname() function
		assertEquals(1994,groupA.getYear());      //test whether the year is right
		assertEquals(4,groupA.getMonth());        //test whether the month is right
		assertEquals(true,groupA.isMale());       //test whether the gender is right, 'F' will be test later
		assertEquals(null,groupA.getFirstAdvisor());   //no first advisor added in the first case
		assertEquals(null,groupA.getSecondAdvisor());  //no second advisor added in the first case
		assertEquals(0,groupA.numAdvicees());          ////no advisee added in the first case
	}
	
		
	public void testB() {
		//test case1 for groupB functions
		PhD groupA=new PhD("Jason",'M',1994,4);
		PhD groupB1=new PhD("David",'M',1973,12);  //construct a PhD 
		groupA.addFirstAdvisor(groupB1);            //adding first advisor to groupA's PhD
		PhD Ad1=groupA.getFirstAdvisor();           // get info about the first advisor
		assertEquals("David",Ad1.getName());       //exam whether the first adviosr's info is right
		assertEquals(1973,Ad1.getYear() );
		assertEquals(12,Ad1.getMonth());
		assertEquals(true,Ad1.isMale());
		assertEquals(null,groupA.getSecondAdvisor());
		assertEquals(1,Ad1.numAdvicees());         //exam whether the advisee info has been changed
		//test case2 for groupB functions
		PhD groupB2=new PhD("Mia",'F',1977,4);     //construct a PhD
		groupA.addSecondAdvisor(groupB2);           //add second to groupA's PhD as second advisor
		PhD Ad2=groupA.getSecondAdvisor();          //get info about the second advisor
		assertEquals(1977,Ad2.getYear() );         //exam whether the first adviosr's info is right
		assertEquals(4,Ad2.getMonth());
		assertEquals(false,Ad2.isMale());          //test 'F' insert in isMale() function
		assertEquals("Mia",groupA.getSecondAdvisor().getName());
		assertEquals(Ad1,groupA.getFirstAdvisor());	
		assertEquals(1,groupB2.numAdvicees());
	}
		/*test for groupC function, first new PhD is female, and her first advisors is groupB PhD,
		 * she has no second advisor
		 *second new PhD is make, he has two advisors. And both advisors are constructed in groupB.*/
	public void testC() {
		PhD groupB1=new PhD("David",'M',1973,12); 
		PhD groupB2=new PhD("Mia",'F',1977,4);
		PhD groupC=new PhD("Bulala",'F',1987,3,groupB2);
		assertEquals("Bulala",groupC.getName());
		assertEquals(false,groupC.isMale());
		assertEquals(1987,groupC.getYear());
		assertEquals(3,groupC.getMonth());
		assertEquals(groupB2,groupC.getFirstAdvisor());
		assertEquals(null,groupC.getSecondAdvisor());
		assertEquals(2,groupB2.numAdvicees());
		
		PhD groupC2=new PhD("Barbecue",'M',1988,3,groupB2,groupB1);
		assertEquals("Barbecue",groupC2.getName());
		assertEquals(true,groupC2.isMale());
		assertEquals(1988,groupC2.getYear());
		assertEquals(3,groupC.getMonth());
		assertEquals(groupB2,groupC2.getFirstAdvisor());
		assertEquals(groupB1,groupC2.getSecondAdvisor());
		assertEquals(2,groupB1.numAdvicees());
		assertEquals(3,groupB2.numAdvicees());
	}
		/*test for group D function,two PhDs in groupC shoud be sibling
		 */
	public void test() {
		PhD groupB1=new PhD("David",'M',1973,12); 
		PhD groupB2=new PhD("Mia",'F',1977,4);
		PhD groupC=new PhD("Bulala",'F',1987,3,groupB2);
		PhD groupC2=new PhD("Barbecue",'M',1988,3,groupB2,groupB1);
		assertEquals(true,groupC2.isPhDSibling(groupC));
	}

}
