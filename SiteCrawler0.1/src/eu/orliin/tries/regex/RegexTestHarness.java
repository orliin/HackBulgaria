package eu.orliin.tries.regex;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexTestHarness {

    public static void main(String[] args){
        @SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);
        while (console != null) {

        	System.out.print("Enter your regex: ");
            Pattern pattern = 
            Pattern.compile(console.next());

            System.out.println("Enter input string to search: ");
            Matcher matcher = 
//            pattern.matcher(console.next());
            pattern.matcher("http://address.something.net/boza/ti.da.vi");
            
            boolean found = false;
            while (matcher.find()) {
                System.out.format("I found the text" +
                    " \"%s\" starting at " +
                    "index %d and ending at index %d.%n",
                    matcher.group(),
                    matcher.start(),
                    matcher.end());
                found = true;
            }
            if(!found){
                System.out.format("No match found.%n");
            }
        }
        
    }
}