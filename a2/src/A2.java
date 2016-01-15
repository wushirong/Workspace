/** NetIds: djg17, jnf27. Time spent: hh hours, mm minutes. */

/** An instance contains static methods for assignment A2 */

import java.util.Arrays;
public class A2 {

    /* NOTE: You will have to rely on methods that are declared in class String.
     * Visit docs.oracle.com/javase/8/docs/api/index.html?java/lang/String.html,
     * scroll down to the Method Summary, and peruse the available methods.
     * Oft-used ones are charAt, length, trim, substring, indexOf, isEmpty,
     * lastIndexOf, startsWith, endsWith, split, toLowerCase, toUpperCase.
     *
     * In all these methods, assume that String parameters are not null.
     * Also assume that the preconditions of the methods are true you do not have
     * to deal with the cases that the input is malformed.
     * 
     * Of course, you can, if you want, use assert statement to test preconditions,
     * but you do not have to..
     * 
     * It is OK to have return statements within the body of a loop.
     * However, try to write loops that do NOT us break or continue;
     * these statements often lead to loop bodies that are hard to
     * understand. */

    /** if b, return the sum of s1 and s2.
     * if not b, return the difference of s1 and s2 */
    public static int sumDif(boolean b, int s1, int s2) {
        if (b) {
            return s1 + s2;
        }
        // b is false;
        return s1 - s2;
    }

    /** if b, return the sum of s1 and s2.
     * if not b, return the difference of s1 and s2 */
     
    public static int sumDif1(boolean b, int s1, int s2) {
        /* sumDif1 and sumDIf have the same specifications. We show two ways of
         * writing the body. In the then-part below, you see a declaration of local
         * variable s, an assignment to it, and then the use of s in the return
         * statement. The declaration and assignment could have been written in 
         * one statement:  int s= s1 + s2;*/
        if (b) {
            int s;
            s= s1 + s2;
            return s;
        }
        else {
            return s1 - s2;
        }
    }

    /** Return true iff s reads the same backwards and forwards.
     * Examples: For s = "", return true
     *           For s = "b", return true
     *           For s = "ab", return false
     *           For s = "aba", return true.
     *           For s = "abba", return true.
     *           For s = "Madam, I'm Adam", return false.
     *           For s = "MadamImAdam", return false.
     *           For s = "madamimadam", return true.  */
    public static boolean sameBackAndForth(String s) {
        /* Use one loop. Do not use recursion.
         * Do not use an array; just process the characters of s.
         * Visit each character of the string at most once. */
    	int length=s.length();
    	if (length==0&&length==1){
    		return true;
    	}
    	for(int i=0;i<length/2;i++){
    		if(s.charAt(i)!=s.charAt(length-1-i)){
    			return false;
    		}
    	}
        return true;
        
    }

    /** Return the number of times query occurs as a substring of src
     * (different occurrences may overlap).
     * Precondition: query is not the empty string "".
     * Examples: For src = "ab", query = "b", return 1.
     *           For src = "Luke Skywalker", query = "ke", return 2.
     *           For src = "abababab", query = "aba", return 3.
     *           For src = "aaaa", query = "aa", return 3.*/
    public static int numOccurrences(String src, String query) {
        /* This should be done with one loop. If at all possible, don't have
         * each iteration of the loop process one character of src. Instead,
         * see whether some method of class String can be used to jump from one
         * occurrence of query to the next. */
    	String subStr=null;
    	int i=0;
    	subStr=src.substring(0, src.length());
    	while(subStr.length()>0){
    		if(subStr.indexOf(query)==-1){
    			break;
    		}
    		subStr=subStr.substring(subStr.indexOf(query)+1, subStr.length());
    		i++;
    	}
        return i;
    }

    
    /** String s is written in a form that looks something like this:
     * "b3c1x2a013b2". For this s, return the decompressed string "bbbcxx111bb".
     *
     * More formally, we have:
     * Precondition: s is in "compressed form": it consists of a sequence of
     * pairs of characters, with the second character being a digit in 0..9.
     * Return the decompressed form of s, which is found by replacing each
     * pair "ci" by i occurrences of character c.
     */
    public static String decompress(String s) {
        /* You can use function Integer.parseInt(s1) to get the int that is in s1.
         * Remember that a character c is not a String, and to change c into a
         * String you can catenate it with the empty String "".
         * This function will probably need a loop within a loop
         */
    	
    	int repeat;
    	char single;
    	StringBuilder sb = new StringBuilder();
    	for(int i=0;i<s.length()/2;i++){
    		single=s.charAt(2*i);
    		repeat=s.charAt(2*i+1)-'0';
        	for(int j=0;j<repeat;j++){
        		sb.append(single);
        	}
    	} 
        return sb.toString();
    }
    
    /*test function3*/
    

    /** Precondition: String s consists of a last-name, a comma ',', a first-name,
     *      and an optional middle-name. There are one or more blank characters
     *      (spaces) before the first-name and before the optional middle-name.
     *      There are 0 or more blanks at the beginning and end of s. Any of the
     *      characters in the names may be in lower or upper case.
     * Return the correctly formatted name as
     * first-name middle-name (if present) last-name
     * The first character of each name must be in uppercase and the rest of the
     * characters in lowercase.
     * Exactly one blank must separate adjacent names. There should be no blank
     * before the first-name and after the last-name.
     *
     * Examples: For s = "   gRies,  DAVID     "   return "David Gries"
     *           For s = "foster,    nate"         return "Nate Foster"
     *           For s = "GOSLING, JAMES   ARTHUR" return "James Arthur Gosling"*/
    public static String fixName(String s) {
        /* As you know, String is a class. An object of class String is
         * immutable -- you cannot change the sequence of chars that it
         * contains. However, you can create new strings by catenating together
         * parts of the original string.
         *
         * Do not use a loop or recursion. Use only if-statements,
         * assignments, and return statements.
         *
         * Finally, this method can be written using an oft-used pattern:
         *   1. Break the string into its parts
         *   2. Modify the parts
         *        (How can you make the first letter of each part of the name
         *         uppercase? How can you extract the first name?)
         *   3. Build the result from the modified parts. */
    	String[] splitStr=new String[3];
    	splitStr=s.split(",");
    	String firstN="";
    	String lastN="";
    	String midN="";
    	StringBuilder sb=new StringBuilder();
    	
    	lastN=splitStr[0];
    	lastN=lastN.trim();
    	firstN=splitStr[1];
    	firstN=firstN.trim();
    	lastN=lastN.toLowerCase();
    	firstN=firstN.toLowerCase();
    	firstN=firstN.substring(0,1).toUpperCase()+firstN.substring(1);
    	lastN=lastN.substring(0, 1).toUpperCase()+lastN.substring(1);
    	if(firstN.contains(" ")){
    		midN=firstN.substring(firstN.indexOf(" ")+1);
    		midN=midN.trim()+" ";
    		firstN=firstN.substring(0,firstN.indexOf(" "));
    		midN=midN.substring(0,1).toUpperCase()+midN.substring(1);
    	}
    	sb.append(firstN+" ");
    	sb.append(midN);
    	sb.append(lastN);

        return sb.toString();
    }

    /** Return a string that is s but with all upper-case consonants (letters of
     * the English alphabet other than the vowels a, e, i, o, u) replaced with
     * _, and all lower-case consonants replaced with their upper-case version.
     *
     * Examples: For s = "Minecraft" return "_iNeCRaFT".
     *           For s = "Alan Turing" return "_LaN _uRiNG".
     */
    public static String replaceConsonants(String s) {
        /* Writing a long list of 42 statements, one for each (upper-case or
         * lower-case) consonant is not a great idea. Instead, put the 21
         * lower-case consonants in a string and the upper-case consonants in
         * another string. Then write a loop that sequences the
         * chars in s in turn, replacing the upper and
         * lowercase versions of each letter in s. This should be the ONLY loop
         * you write! 
         *
         * A for-loop to loop through a range b..c-1 of integers can look like
         * this:
         *    for (int i = b; i < c ; i = i + 1) {
         *        ...
         *    }
         */

    	String uppercase;
    	String lowercase;
    	StringBuilder res=new StringBuilder();
    	int length=s.length();
    	int charNum=0;
    	char lowerChar;
    	char upperChar;
    	
    	uppercase="BCDFGHJKLMNPQRSTVWXYZ";
    	lowercase="bcdfghjklmnpqrstvwxyz";
    
    	for(int i=0;i<length;i++){
    		if(lowercase.indexOf(s.charAt(i))!=-1){
    			lowerChar=s.charAt(i);
    			charNum=lowercase.indexOf(lowerChar);
    			upperChar=uppercase.charAt(charNum);
    			res.append(upperChar);
    		}
    		else if(uppercase.indexOf(s.charAt(i))!=-1){
    			res.append("_");
    		}
    		else{
    			res.append(s.charAt(i));
    		}
    	}
        return res.toString();
    }
    

    /** Return true iff s and t are anagrams of each other. An anagram of a string
     * is another string that has the same characters, but possibly in a
     * different order. Note that 'a' and 'A' are considered different characters
     * and that the space is a character.
     *
     * Examples: For s = "noon", t = "noon", return true.
     *           For s = "mary", t = "army", return true.
     *           For s = "tom marvolo riddle", t = "i am lordvoldemort", return true.
     *           For s = "tommarvoloriddle", t = "i am lordvoldemort", return false.
     *           For s = "hello", t = "world", return false.  */
    public static boolean areAnagrams(String s, String t) {
        /* Do not use a loop or recursion! This is tricky but can in fact
         * be done in a few lines. Hint: how can a sequence of characters be
         * uniquely ordered? You might need to first convert the string to an
         * array of characters and then use functions in class Arrays
         * (http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html). */
    	char[] charArray1=new char[s.length()];
    	char[] charArray2=new char[t.length()];
    	charArray1=s.toCharArray();
    	charArray2=t.toCharArray();
    	Arrays.sort(charArray1);
    	Arrays.sort(charArray2);
    	s=Arrays.toString(charArray1);
    	t=Arrays.toString(charArray2);
    	if(s.equals(t)){
    		return true;
    	}

        return false;
    }
 
    
}
