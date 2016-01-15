
import static org.junit.Assert.*;

import org.junit.Test;

public class A2Test {
		@Test
		public void testSumDif() { 
			// assertEquals(expected value, computed value);
		assertEquals(8, A2.sumDif(true, 5, 3)); 
		assertEquals(2, A2.sumDif(false, 5, 3)); 
		assertEquals(8, A2.sumDif(true, 5, 3));
		//test sameback and forth
		assertEquals(true,A2.sameBackAndForth(""));
		assertEquals(true,A2.sameBackAndForth("b"));
		assertEquals(false,A2.sameBackAndForth("ab"));
		assertEquals(true,A2.sameBackAndForth("aa"));
		assertEquals(true,A2.sameBackAndForth("aba"));
		assertEquals(true,A2.sameBackAndForth("abba"));
		assertEquals(false,A2.sameBackAndForth("Madam, I'm Adam"));
		assertEquals(false,A2.sameBackAndForth("MadamImAdam"));
		assertEquals(true,A2.sameBackAndForth("madamimadam"));
		
		assertEquals("bbbcxx111bb",A2.decompress("b3c1x2a013b2"));
		assertEquals("bbbbbxxvvvbb",A2.decompress("b5c0x2a0v3b2"));
		//test numoccurences
		assertEquals(1,A2.numOccurrences("ab","b"));
		assertEquals(2,A2.numOccurrences("Luke Skywalker","ke"));
		assertEquals(3,A2.numOccurrences("abababab", "aba"));
		assertEquals(3,A2.numOccurrences("aaaa", "aa"));
		assertEquals(5,A2.numOccurrences("aaaaa", "a"));
		assertEquals(2,A2.numOccurrences("abaaab", "ab"));
		
		assertEquals("David Gries",A2.fixName("   gRies,  DAVID     "));
		assertEquals("Nate Foster",A2.fixName("foster,    nate"));
		assertEquals("James Arthur Gosling",A2.fixName("GOSLING, JAMES   ARTHUR"));
		assertEquals("Bbyou Aart Can",A2.fixName("can, bbyou aart "));
		
		assertEquals( "_iNeCRaFT",A2.replaceConsonants( "Minecraft"));
		assertEquals("BCDFGHJKLMN",A2.replaceConsonants("bcdfghjklmn"));
		assertEquals("___________",A2.replaceConsonants("BCDFGHJKLMN"));
		assertEquals("Aa_BC_D_",A2.replaceConsonants("AaBbcCdD"));
		
		
		assertEquals(true,A2.areAnagrams("noon", "noon"));
		assertEquals(true,A2.areAnagrams("mary", "army"));
		assertEquals(true,A2.areAnagrams("tom marvolo riddle",  "i am lordvoldemort"));
		assertEquals(false,A2.areAnagrams("tommarvoloriddle", "i am lordvoldemort"));
		assertEquals(false,A2.areAnagrams("hello", "world"));
		
		}
}
