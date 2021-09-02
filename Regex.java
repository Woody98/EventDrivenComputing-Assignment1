package intros;
import java.util.ArrayList;
import java.util.Scanner;

/*After several attempts, I find it difficult to achieve all functions of regex in a single attempt.
 * Therefore, I will divide my coding into several stages.
 * For each stage of my coding, I will develop more functions.
 * Date: 27/08/2021
 * This is version1, which will have the functions of judging single basic operators as: "a?b", "abc*d".
 * Date: 03/09/2021
 * Version2 is under development, the functions are to include multiple operators as: "a?b*", "ab?cc+".
 * Most of the public static methods are for version2 but haven't being used yet, since this is only version1.
 * */
public class Regex {
	public static void main(String args[]) {
		Scanner scanner1 = new Scanner(System.in);
		System.out.println("Please input the regex function:");
		String a = scanner1.nextLine();
		System.out.println("Please input the string:");
		Scanner scanner2 = new Scanner(System.in);    /*These two are the inputs of regex and string.*/
		String b = scanner2.nextLine();
		//String a = "ab+c";
		//String b = "abbbbbbbbbc";
		int count = 0;
		//System.out.print(b.charAt(3));
		while(isSpecial(a.charAt(count + 1)) == 0) {   /*To begin with, judge whether the chars that has nothing to do with special symbols such as *, +, ? are all correct.*/
			if(a.charAt(count) != b.charAt(0)) {
				System.out.println("False");
				System.exit(0);
			}
			else {
				b = b.replaceFirst(String.valueOf(a.charAt(count)), "");
				count++;
			}
		}
		//System.out.print(count);
		if(count < a.length() && a.charAt(count + 1) == '+') {     /*This part is to judge whether the string is correct when the special symbol is +.*/
			if(count == a.length() - 2) {
				while(b.isEmpty() == false && a.charAt(count) == b.charAt(0)) {
					b = b.replaceFirst(String.valueOf(a.charAt(count)), "");
				}
				if(b.isEmpty() == true){
					System.out.println("TRUE");
					System.exit(0);
				}
				else{
					System.out.println("False");
					System.exit(0);
				}
			}
			else if(a.charAt(count) != b.charAt(0)){
				System.out.println("False");
				System.exit(0);
			}
			else{
				while(b.isEmpty() == false && a.charAt(count) == b.charAt(0)) {
					b = b.replaceFirst(String.valueOf(a.charAt(count)), "");
				}
				count += 2; /*To skip the special symbol, += 2 instead of ++*/
			}
		}
		else if(a.charAt(count + 1) == '?') {     /*This part is to judge whether the string is correct when the special symbol is ?.*/
			if(count == a.length() - 2) {    /*If there is nothing after the ?*/
				if(b.isEmpty() == true){
					System.out.println("TRUE");
					System.exit(0);
				}
				else {
					if(b.charAt(0) != a.charAt(count)){
						System.out.println("False");
						System.exit(0);
					}
					else {
						if(b.isEmpty() == true){
							System.out.println("TRUE");
							System.exit(0);
						}
						else{
							System.out.println("False");
							System.exit(0);
						}
					}
				}
			}
			else {
				if(b.charAt(0) != a.charAt(count)) {        /*If the char before and after the special symbol are different*/
					if(b.charAt(0) != a.charAt(count + 2)){
						System.out.println("False");
						System.exit(0);
						}
					else {
						count += 2;
					}
				}
				else {                                      /*If the char before and after the special symbol are the same*/
					if(a.charAt(count) != a.charAt(count + 2)) {
						b = b.replaceFirst(String.valueOf(a.charAt(count)), "");
						count += 2;
					}
					else {
						if(b.length() == a.length() - 2) {
							count += 2;
						}
						else if (b.length() == a.length() - 1) {
							b = b.replaceFirst(String.valueOf(a.charAt(count)), "");
							count += 2;
						}
						else {
							System.out.println("False");
							System.exit(0);
						}
					}
				}
			}
				
		}
		else if(count < a.length() && a.charAt(count + 1) == '*') {
			if(b.charAt(0) != a.charAt(count)) {
				count += 2;
			}
			else {
				while (b.isEmpty() == false && b.charAt(0) == a.charAt(count)) {
					b = b.replaceFirst(String.valueOf(a.charAt(count)), "");
				}
				count += 2;
			}
		}
		if(b.length() != a.length() - count){
			System.out.println("False");
			System.exit(0);
		}
		while(count < a.length() && b.isEmpty() == false) {     /*When it's empty, and all is correct, output TRUE.*/
				if(a.charAt(count) != b.charAt(0)){
					System.out.println("False");
					System.exit(0);
				}
				else {
					b = b.replaceFirst(String.valueOf(a.charAt(count)), "");
					if(b.isEmpty() == true) {
						System.out.println("TRUE");
						System.exit(0);
					}
					else {
						count++;
						continue;
					}
			}
		}
		//System.out.print(b);
		//System.out.println(star(a, 0) + " " + questionMark(a, 0) + " " + plus(a, 0));
		//System.out.print(a.indexOf("abc"));
		//a = a.replace("aa", "");
		//System.out.println(a.substring(1, 2));
		
		
	}
	
	/*This method checks whether a char is \, since \ disables the immediate char following it.*/
	public static int judgeDash(char a) {
		if (a == '\\')
			return 1;
		else 
			return 0;
	}
	
	/*This method judges whether the char is a special symbol.*/
	public static int isSpecial(char a) {
		if (a == '*' || a== '?' || a == '+' || a == '|')
			return 1;
		else
			return 0;
	}
	
	/* This method helps identify the open bracket which is of 2nd highest priority.
	 * We should also check whether the previous char is \ to see if the ( is valid.
	 * */
	public static int openBracket(String a, int index) {
		if (index >= a.length())
			return -1;
		else if (index == 0 && a.charAt(index) == '(')
			return 0;
		else if (index == 0 && a.charAt(index) != '(')
			return openBracket(a, index + 1);
		else if (judgeDash(a.charAt(index - 1)) == 1)
			return openBracket(a, index + 1);
		else if (a.charAt(index) == '(')
			return index;
		else
			return openBracket(a, index + 1);
	}
	
	/* Index is the place of the open bracket.
	 * This method helps find the close bracket that matches the open bracket.
	 * We should also check whether the previous char is \ to see if the ( is valid.
	 * */
	public static int closeBracket(String a, int index) {
		if (index == -1)
			return -1;
		else if (judgeDash(a.charAt(index - 1)) == 1)
			return closeBracket(a, index + 1);
		else if (a.charAt(index) == ')')
			return index;
		else
			return closeBracket(a, index + 1);
	}
	
	/* This is the same as openBracket.*/
	public static int openMiddleBracket(String a, int index) {
		if (index >= a.length())
			return -1;
		else if (index == 0 && a.charAt(index) == '[')
			return 0;
		else if (index == 0 && a.charAt(index) != '[')
			return openMiddleBracket(a, index + 1);
		else if (judgeDash(a.charAt(index - 1)) == 1)
			return openMiddleBracket(a, index + 1);
		else if (a.charAt(index) == '[')
			return index;
		else
			return openMiddleBracket(a, index + 1);
	}
	
	/* This is the same as closeBracket.*/
	public static int closeMiddleBracket(String a, int index) {
		if (index == -1)
			return -1;
		else if (index >= a.length())
			return -1;
		else if (judgeDash(a.charAt(index - 1)) == 1)
			return closeMiddleBracket(a, index + 1);
		else if (a.charAt(index) == ']')
			return index;
		else
			return closeMiddleBracket(a, index + 1);
	}
	
	/*This finds the + symbol.*/
	public static int plus(String a, int index) {
		if (index >= a.length())
			return -1;
		else if (index == 0)
			return plus(a, index + 1);
		else if (judgeDash(a.charAt(index - 1)) == 1)
			return plus(a, index + 1);
		else if (a.charAt(index) == '+')
			return index;
		else
			return plus(a, index + 1);
	}
	
	/*This finds the * symbol.*/
	public static int star(String a, int index) {
		if (index >= a.length())
			return -1;
		else if (index == 0)
			return star(a, index + 1);
		else if (judgeDash(a.charAt(index - 1)) == 1)
			return star(a, index + 1);
		else if (a.charAt(index) == '*')
			return index;
		else
			return star(a, index + 1);
	}
	
	/*This finds the ? symbol.*/
	public static int questionMark(String a, int index) {
		if (index >= a.length())
			return -1;
		else if (index == 0)
			return questionMark(a, index + 1);
		else if (judgeDash(a.charAt(index - 1)) == 1)
			return questionMark(a, index + 1);
		else if (a.charAt(index) == '?')
			return index;
		else
			return questionMark(a, index + 1);
	}
}
