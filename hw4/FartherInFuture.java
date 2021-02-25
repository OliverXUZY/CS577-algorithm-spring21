import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 
 * @author Zhuoyan Xu
 * 577 hw4 coding question, this is a more efficient version, 
 * which used binary heap for cache storing,
 * 1) We use a long array(same size as request sequence) to store the steps of next time same page being requested,
 * so we do not need to search for farthest position of each page in cache every time we get a new request.
 */

public class FartherInFuture {
  private String[] request; // sequence of request
  private int idPage = 0; // int showed how many pages have already been requested
  private int cacheSize; // size of oversized array cache
  private int pageFaults = 0; // the last result we want to output
  
  private int[] stepArray; // an array storing the steps of next time same page being requested
  private Hashtable<String, Integer> hash = new Hashtable<String, Integer>(); // hashTable helps set up stepArray
  
  private Hashtable<String, Integer> hashCache = new Hashtable<String, Integer>(); // hashTable represent the page (key)
                                                    //and steps(values) (how many step next run into same page in sequence)
  private PriorityQueue<Integer> pQueueCache = new PriorityQueue<Integer>(Collections.reverseOrder());   // priority queue to represent the max binary heap storint
                                                                 // the steps of pages in cache
  
  /**
   * Constructor
   * @param numInCache
   * @param numRequest
   */
  public FartherInFuture(int numInCache, int numRequest) {
    this.cacheSize = numInCache;
    this.request = new String[numRequest];
    this.stepArray = new int[numRequest];
  }
  
  /**
   * mutator for setting Request Sequence
   * @param requestSequence
   */
  public void setRequestSequence(String requestSequence) {
    this.request = requestSequence.trim().split(" ");
    //System.out.println(Arrays.toString(this.request));   
  }
  
  /**
   * check whether we have page fault (modify in a new version, since now use hash table and binary heap to represent cache)
   * @param newRequest
   * @return false if new request already in cache
   */
  public boolean isFault(String newRequest) {
    if(!hashCache.containsKey(newRequest)) {
      return true;
    }
    return false;
  }
  
  /**
   * setup an array storing the steps of next time same page being requested
   * this array has already been initialized as stepArray
   * the key of hash table should be unique value of pages in request sequence, 
   * the value should be the index of those pages in sequence, we start from end backwards, 
   * if keys do not have this page, add this to hash table, set value to infinity; 
   * if keys has, update value to be how many step we see the same (previous value(index) - current index)
   */
  public void findStepArray() {
    for(int i = this.request.length - 1; i>=0; i--) {
      if(!hash.containsKey(this.request[i])) {
        hash.put(this.request[i], i);
        stepArray[i] = Integer.MAX_VALUE /2 ;

        
      } else {
        stepArray[i] = hash.get(this.request[i]) - i;
        hash.put(this.request[i], i);
      }
    }
    //System.out.println("The step array is " + Arrays.toString(stepArray));
  }
  
  /**
   * This is for find key in hash table based on values
   * @return the key string, won't return null in practice, since the value is pop from priority queue
   */
  public String findKeyBasedValue(int val) {
    for(Object o:hashCache.keySet()) {
      if(hashCache.get(o).equals(val)) {
        return o.toString();
      }
    }
    return "null";
  }
  /**
   * what to do when encounter a new page request
   */
  public void getNewPageRequest() {
    // request[idPage] is the new page we got every time
    //!!!
    // Everytime we update steps, we need to add idPage, so they are comparable in example 1 2 3 4 5 1 2 3 4 5...
    //!!!
    // check whether cache is full, if not, when we encounter a fault, add it directly
    if(hashCache.size() < cacheSize ) {
      if(this.isFault(request[idPage])) {
        
        /*
        System.out.println("Faults at idPage " + idPage);
        System.out.println("Now the cache is " + hashCache);
        System.out.println("Now the new page is " + request[idPage]);
        */
        
        
       
        pageFaults++;
        hashCache.put(request[idPage], stepArray[idPage] + idPage);  // add page and step to cache
        pQueueCache.add(stepArray[idPage] + idPage); // add step to binary heap
        
        /*
        System.out.println("After modifying the cache is " + hashCache);
        System.out.println(" ");
        */
        
      } else { // if do not have fault, update steps to newest one!!!!!
        Iterator<Integer> iterator = pQueueCache.iterator();
        
        while (iterator.hasNext()) {
          if((int)iterator.next() == hashCache.get(request[idPage])) {
            iterator.remove();
            break;
          }
        }
        pQueueCache.add(stepArray[idPage] + idPage);
        
        
        hashCache.put(request[idPage], stepArray[idPage] + idPage);
        
        
      }
      return;
    }
    
    // in following case, cache is full, hashCache.size() < cacheSize
    if(this.isFault(request[idPage])) {  // encounter a fault
     
      /*
      System.out.println("Faults at idPage " + idPage);
      System.out.println("Now the cache is " + hashCache);
      System.out.println("Now the new page is " + request[idPage]);
      */
      
      pageFaults++;
      // remove the page with largest steps
      //System.out.println(pQueueCache.size() == hashCache.size());
      
      String keyRemoved = this.findKeyBasedValue(pQueueCache.poll());
      hashCache.remove(keyRemoved);
      
      hashCache.put(request[idPage], stepArray[idPage] + idPage); // replace evicted one with new requested one  
      pQueueCache.add(stepArray[idPage] + idPage);
      
      /*
      System.out.println("After modifying the cache is " + hashCache);
      System.out.println(" ");
      */
      
    } else {
      /*
      // new problem here: integer overflow !!!!
      System.out.println("DO NOT Faults at idPage " + idPage);
      System.out.println("Now the stepArray[idPage] is " + stepArray[idPage] + idPage);
      System.out.println(" ");
      */
      
      // if do not have fault, update steps to newest one!!!!!
      Iterator<Integer> iterator = pQueueCache.iterator();
      
      while (iterator.hasNext()) {
        if((int)iterator.next() == hashCache.get(request[idPage])) {
          iterator.remove();
          break;
        }
      }
      pQueueCache.add(stepArray[idPage] + idPage);
      hashCache.put(request[idPage], stepArray[idPage] + idPage);
    }
  }
  
  
  
  public int getNumFaults() {
    //System.out.println(pageFaults);
    while(idPage < request.length) {
      this.getNewPageRequest();
      idPage++;
    }
    return pageFaults;
  }
  
  
  
  
  
  
  
  
  public static void main(String[] args) {
    
    
    //FartherInFuture test = new FartherInFuture(4,12);
    //test.setRequestSequence("12 3 33 14 12 20 12 3 14 33 12 20");
    //FartherInFuture test = new FartherInFuture(2,7);
    //test.setRequestSequence("1 2 3 2 3 1 2");
    //test.findStepArray();
    /*
    FartherInFuture test = new FartherInFuture(3,15);
    test.setRequestSequence("1 2 3 4 5 1 2 3 4 5 1 2 3 4 5");
    test.findStepArray();
    System.out.println("Final nums is " + test.getNumFaults());
    */
    
    
    
    
    
    ArrayList<FartherInFuture> instanceList = new  ArrayList<FartherInFuture>();
    Scanner scan = new Scanner(System.in);
      // System.out.println("How many instances are there? ");
    String linesString = scan.nextLine().trim();
    int numInstance = Integer.parseInt(linesString);
    for(int id = 0; id< numInstance; id++) {
      // System.out.println("How many page in the cache are in " + i + "-th instance?");
      linesString = scan.nextLine().trim();
      int numInCache = Integer.parseInt(linesString);
      
      // System.out.println("How many page in the request sequence are in " + i + "-th instance?");
      linesString = scan.nextLine().trim();
      int numRequest = Integer.parseInt(linesString);
      instanceList.add(new FartherInFuture(numInCache, numRequest));
      
      // System.out.println("Please enter the request sequence in " + i + "-th instance" );
      linesString = scan.nextLine().trim();
      instanceList.get(id).setRequestSequence(linesString);
    }
    
    for(int id = 0; id< numInstance; id++) {
      instanceList.get(id).findStepArray();
      System.out.println(instanceList.get(id).getNumFaults());
    }
    
    
    
  }

  
  
  
  
  
  
  
  
  
  
}












