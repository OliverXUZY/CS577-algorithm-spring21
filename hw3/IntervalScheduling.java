import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class IntervalScheduling {
  /**
   * This method for count the number of jobs scheduled for each instance
   * @param instance each instance stored in ArrayList
   * @return int the number of jobs
   */
  private static int arrangeJob(String[] instance) {
    /* transfer instance to a 2 way array
     * String[] ["1 2", "6 10", "2 6", "1 3", "3 4"]  -----> int[][] [[1, 2], [6, 10], [2, 6], [1, 3], [3, 4]]
     */
    int[][] Time = new int[instance.length][2];
    for(int i = 0; i < instance.length; i++) {
      Time[i][0] = Integer.parseInt(instance[i].trim().split(" ")[0]);
      Time[i][1] = Integer.parseInt(instance[i].trim().split(" ")[1]);
    }
    
    /* sort Time
     * [[1, 2], [6, 10], [2, 6], [1, 3], [3, 4]] -----> [[1, 2], [1, 3], [3, 4], [2, 6], [6, 10]] 
     */
    Arrays.sort(Time, new Comparator<int[]>() {
      @Override
      // compare values according to second value
      public int compare(int[] entry1, int[] entry2) {
        // sort in ascending order
        if (entry1[1] > entry2[1]) {
          return 1;
        } else {
          return -1;
        }
      }
      
    });
    
    /*
     * count the number of job schedule (FinishFirst algorithm involved)
     */
    int count = 1; // count the first job (earliest finish time) in
    int idx = 1;  // normal idx iterate over array
    int countInIdx = 0; // the actual index count in schedule
    while(idx < Time.length) {
      if(Time[countInIdx][1] <= Time[idx][0]) {
        count++;
        countInIdx = idx;
      }
      idx++;
    }
    return count;
  }
  
/**
 * the main function
 * @param args
 */
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    // System.out.println("How many instances are there? ");
    String linesString = scan.nextLine().trim();
    int numInstance = Integer.parseInt(linesString);
    
    ArrayList<String[]> instancesList = new ArrayList<String[]>(numInstance); //   Is numInstance necessary?
    
    for(int i = 1; i <= numInstance; i++) {   
      // System.out.println("How many jobs are in " + i + "-th instance?");
      linesString = scan.nextLine().trim();
      int numJobs = Integer.parseInt(linesString);
      
      //int numJobs = scan.nextInt(); do not use this one because it will observe next scan.nextLine()
      
      String[] instance = new String[numJobs];  // initialize a new instance, need to add to ArrayList afterwards
      
      int jobId = 0;
      while(jobId < numJobs) {
        // System.out.println("Please enter " + (jobId+1) + "-th job in " + i + "-th instance" );
        String jobString = scan.nextLine().trim();
        instance[jobId] = jobString;
        jobId++;
      }
      
      instancesList.add(instance);
    }
    
    
    
    for(int i = 0; i < instancesList.size(); i++) {   
      int countNum = arrangeJob(instancesList.get(i));
      System.out.println(countNum);
      
      //System.out.println(Arrays.toString(instancesList.get(i)));
  } 

    
    //Integer[] test  = new Integer[] {1,2,3};
    //int[] test2 = new int[] {1,2,3};
    //System.out.println(Arrays.toString(test));
   

  }

}
