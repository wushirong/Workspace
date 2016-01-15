
import java.net.*;
import java.io.*;


public class Nuke2{
	
	public static void main(String[] arg) throws Exception{
   BufferedReader keyboard;
   String input;
   int j=0;
   char[] temp;
   
   keyboard=new BufferedReader(new InputStreamReader(System.in));
   System.out.print("Please enter your words: ");
   System.out.flush(); 
   input=keyboard.readLine();
   temp=input.toCharArray();
   char[] output=new char[temp.length-1];
   for(int i=0;i<temp.length;i++){
	   if(i==1){
		   i++;
	   }
	   output[j++]=temp[i];  
   } 
   
   System.out.print(String.valueOf(output));
}
}


