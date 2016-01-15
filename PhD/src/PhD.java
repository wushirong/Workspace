/**sw782:shirong,wu  time spent:5 hours, 00 minutes.
 * An instance maintains info about the PhD of a person.
 */
import static org.junit.Assert.*;
public class PhD {

	private String name;  // the name of the PhD, a String of length>0
	private int awardY;    // year the PhD was awarded
	private int awardM;    // month the PhD was awarded
	private char gender;     //the gender of the PhD, 'M'for male and 'F' for female
	private PhD firstAd=null;    //the fist adviser name of this PhD
	private PhD secondAd=null;   // the second adviser name of this PhD, null if no
	private int adviseeNum;     // the number of students guided by this PhD
	
	 /*an instance for a person with name n, gender g, PhD year y, and PhD month m. Its advisors are unknown, and it has no advisees.
	 Precondition: n has at least 1 character, m is in 1..12, and g is 'M' for male or 'F' for female.*/
	PhD(String n, char g, int y, int m) {
		name=n;  //the field contains the person¡¯s name and must be a string of at least 1 character.
		gender=g;
		awardY=y;
		awardM=m;
	}
	
	//Return this person's name.
	public String getName(){
		return name;
	}
	
	//Return the year this person got their PhD.
	public int getYear(){
		return awardY;
	}
	
	//Return the year this person got their PhD.
	public int getMonth(){
		return awardM;
	}
	
	//Return the value of the sentence "this person is a male.¡±
	public boolean isMale(){
		char thisG=gender;
		return thisG=='M';
	}
		
	//Return this Phd's first advisor (null if unknown).
	public PhD getFirstAdvisor(){
		return firstAd;
	}
	//Return this Phd's second advisor (null if unknown non-existent).
	public PhD getSecondAdvisor(){
		return secondAd;
	}
		
	//Return the number of PhD advisees of this person.
	public int numAdvicees(){
		return adviseeNum;
	}
	//Add p as this person's first PhD advisor.
	//Precondition: this person's first advisor is unknown and p is not null.
	public void addFirstAdvisor(PhD p){
		assert(p!=null);
		assert(this.firstAd==null);
		firstAd=p;
		p.adviseeNum+=1;
	}

	/*Add p as this persons second advisor.
	Precondition: This person's first advisor is known, their second advisor is 
	unknown, p is not null, and p is different from this person's first advisor.*/
	public void addSecondAdvisor(PhD p){
		assert(p!=null);
		assert(this.secondAd==null);
		secondAd=p;
		p.adviseeNum+=1;
	}
	/*Constructor: a PhD with name n, gender g, PhD year y, PhD month m, first advisor adv, 
	 * and no second advisor.Precondition: n has at least 1 char, g is 'F' for female or 'M' 
	 * for male, m is in 1..12, and adv is not null.*/
	PhD(String n, char g, int y, int m, PhD adv){
		assert(n.length()>1);
		assert(adv!=null);
		assert(1<m&&m<12);
		assert(g=='F'||g=='M');
		name=n;
		gender=g;
		awardY=y;
		awardM=m;
		addFirstAdvisor(adv);
		
	}
   /*Constructor: a PhD with name n, gender g, PhD year y, PhD month m, first advisor adv1, 
    * and second advisor adv2.Precondition: n has at least 1 char, g is 'F' for female or 'M'
    *  for male, m is in 1..12, adv1 and adv2 are not null, and adv1 and adv2 are different.*/
	PhD(String n, char g, int y, int m, PhD adv1, PhD adv2){
		assert(n.length()>1);
		assert(adv1!=null&&adv2!=null);
		assert(1<m&&m<12);
		assert(g=='F'||g=='M');
		name=n;
		gender=g;
		awardY=y;
		awardM=m;
		addFirstAdvisor(adv1);
		addSecondAdvisor(adv2);
	}
	/*Return value of "this person got their PhD after p did." Precondition: p is not null.*/
	public boolean isYoungerThan(PhD p){
		assert(p!=null);
		return awardY>p.awardY||(awardY==p.awardY&&awardM>p.awardM);
	}
	
	/*Return value of "this person and p are intellectual siblings.¡± Note: 
	 * if p is null, they are not siblings.
	 */
    public boolean isPhDSibling(PhD p){
    	assert(p!=null);
    	assert(p!=this);
    	boolean nosameObj=!(p==this);
    	PhD a=this.firstAd;
    	PhD b=this.secondAd;
    	PhD c=p.firstAd;
    	PhD d=p.secondAd;
    	
    	boolean case1=a!=null&&c!=null&&a==c;
    	boolean case2=a!=null&&d!=null&&a==d;
    	boolean case3=b!=null&&c!=null&&b==c;
    	boolean case4=b!=null&&d!=null&&b==d;
    	return nosameObj&&(case1||case2||case3||case4);
    }
}
