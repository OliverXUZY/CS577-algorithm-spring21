import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 * @author Zhuoyan Xu
 * 577 hw4 coding question
 */
public class FurthestInFuture {
  
  private String[] cache;
  private String[] request; // sequence of request
  private int idPage = 0; // int showed how many pages have already been requested
  private int cacheSize = 0; // size of oversized array cache
  private int pageFaults = 0; // the last result we want to output
  
  /**
   * Constructor
   * @param numInCache
   * @param numRequest
   */
  public FurthestInFuture(int numInCache, int numRequest) {
    this.cache = new String[numInCache];
    this.request = new String[numRequest];
    
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
   * check whether we have page fault
   * @param newRequest
   * @return false if new request already in cache
   */
  public boolean isFault(String newRequest) {
    for(int i = 0; i < cache.length; i++) {
      if(newRequest.equals(cache[i])) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * for a given page in cache, find the furthest position this page shows in request sequence
   * @param page in cache
   * @return the last position, -1 if didn't find one
   */
  public int findFurthestPagePosition(String page) {
    int furthestPosition = -1;
    int position = idPage;
    while(position < request.length) {
      if(request[position].equals(page)) {
        furthestPosition = position;
        break;
      }
      position++;
    }    
    return furthestPosition;
  }
  
  /**
   * find which page in cache should be evicted, make sure the cache is full before use it
   * @return  page id in cache
   */
  public int findEvictedPagePosition() {
    /*
    // for test!!!!!!!!!!!!!!!!!!!!!!!!!!!
    this.cache = new String[] {"3","1"};  
    cacheSize = 2; 
    */
    
    int cacheID = 0; // find which page in cache should be evicted
    for(int i = 1; i < cacheSize; i++) {
      if (this.findFurthestPagePosition(cache[cacheID]) == -1) return cacheID; // if didn't find cacheID, evict cacheID this time
      if (this.findFurthestPagePosition(cache[i]) == -1) return i; // if didn't find i, evict i this time
      if (this.findFurthestPagePosition(cache[i]) > this.findFurthestPagePosition(cache[cacheID])) {
        cacheID = i;
      }
    } 
    return cacheID;
  }
  
  /**
   * what to do when encounter a new page request
   */
  public void getNewPageRequest() {
    // check whether cache is full, if not, when we encounter a fault, add it directly
    if(cacheSize < cache.length) {
      if(this.isFault(request[idPage])) {
        
        /*
        System.out.println("Faults at idPage " + idPage);
        System.out.println("Now the cache is " + Arrays.toString(cache));
        System.out.println("Now the new page is " + request[idPage]);
        */
        
       
        pageFaults++;
        cache[cacheSize] = request[idPage];
        cacheSize++;
        /*
        System.out.println("After modifying the cache is " + Arrays.toString(cache));
        System.out.println(" ");
        */
      }
      return;
    }
    
    // in following case, cache is full, cacheSize = cache.length
    if(this.isFault(request[idPage])) {  // encounter a fault
      /*
      System.out.println("Faults at idPage " + idPage);
      System.out.println("Now the cache is " + Arrays.toString(cache));
      System.out.println("Now the new page is " + request[idPage]);
      */
      
      pageFaults++;
      cache[this.findEvictedPagePosition()] = request[idPage]; // replace evicted one with new requested one
      
      /*
      System.out.println("After modifying the cache is " + Arrays.toString(cache));
      System.out.println(" ");
      */
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
    /*
    FurthestInFuture test = new FurthestInFuture(2,7);
    test.setRequestSequence("1 2 3 2 3 1 2");
    System.out.println("Final nums is " + test.getNumFaults());
    

    FurthestInFuture test2 = new FurthestInFuture(4,12);
    test2.setRequestSequence("12 3 33 14 12 20 12 3 14 33 12 20");
    System.out.println("Final nums is " + test2.getNumFaults());
    
    
    FurthestInFuture test3 = new FurthestInFuture(3,15);
    test3.setRequestSequence("1 2 3 4 5 1 2 3 4 5 1 2 3 4 5");
    System.out.println("Final nums is " + test3.getNumFaults());
    */
    
    //System.out.println(test.findFurthestPagePosition("1"));
    //System.out.println("findEvictedPagePosition() is " + test.findEvictedPagePosition());
    
    ArrayList<FurthestInFuture> instanceList = new  ArrayList<FurthestInFuture>();
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
      instanceList.add(new FurthestInFuture(numInCache, numRequest));
      
      // System.out.println("Please enter the request sequence in " + i + "-th instance" );
      linesString = scan.nextLine().trim();
      instanceList.get(id).setRequestSequence(linesString);
    }
    
    for(int id = 0; id< numInstance; id++) {
      System.out.println(instanceList.get(id).getNumFaults());
    }
    
    
    
    
    
   
    
    
    
    
    
   

  }

}
