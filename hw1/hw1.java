import java.util.Scanner;

public class hw1 {

  public static void main(String[] args) {
    // TODO Auto-generated method stub\
    Scanner scan = new Scanner(System.in);
    // System.out.println("How many names would like to output: ");
    String linesString = scan.nextLine().trim();
    int lines = Integer.parseInt(linesString);
    String[] names = new String[lines];
    for(int i = 0; i < lines; i++) {
	// System.out.println("Please enter name " + (i+1) + ":");
      names[i] = scan.nextLine();
    }
    for(int i = 0; i < lines; i++) {
      System.out.println("Hello, " + names[i] + "!");
    }
    

  }

}
