import java.util.HashSet;

public class Solution {
    public boolean isHappy(int n) {
        if(n == 0){
            return false;
        }
    int sum = 0;
    int transform = n;
    HashSet digit=new HashSet();
    while(sum != 1){
        while(transform>0) {
            sum += (transform % 10)*(transform % 10);
            transform /= 10;
        }
        if(digit.contains(sum)) {
            return false;
        }
        digit.add(sum);
    }
    return true;
    }
    public static void main(String args[]){
    	Solution s=new Solution();
    	boolean res = s.isHappy(7);
    	String a="dd";
    	String b="dd";
    	boolean k=a.equals(b);
    	System.out.println(res);
    
    }
}


