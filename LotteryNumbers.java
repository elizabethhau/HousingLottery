/* FILE NAME: LotteryNumbers.java
 * 
 * Elizabeth Hau
 * Emily Cetlin
 * CS 230
 * Independent Study - Wellesley College Housing Lottery
 * Date Created: December 2014
 * Last Modification: 2/22/15
 * 
 * The LotteryNumbers class creates reads in a file containing students' information
 * and generates random but unique lottery numbers for each student. Depending on the
 * student's class year, the lottery number they receive will be in a different range.
 * For example, seniors could receive a lottery number in the range of 1-10, juniors 11-20,
 * sophomores 21-30, and first years 31-40. The ranges are made larger than actual class
 * sizes to accomodate for fluctuating class sizes. 
 */
import java.util.*;
import java.io.*;

public class LotteryNumbers {
  
  // instance variables
  private int [] seniors;
  private int [] juniors;
  private int [] sophomores;
  
  private int number;
  private int min; // starting point for each range (for each class year)
  private final int MAX = 1000; // max amount of numbers in each range
  private int seniorClass; // class year of the senior class helps determine other class years
  
  /* This simple constuctor creates lottery numbers for the seniros
   */
  public LotteryNumbers() {
    seniors = new int [MAX];
    seniorClass = 2016; // assuming senior for now...
    min = 0; 
  }
  
  
  /* parameter year is the year for the upcoming year. So the lottery numbers 
   * for the year 2016, input 2016 as the first parameter.
   */
  public LotteryNumbers(int year) {
    seniorClass = year; // initialize classYear to the senior class
    seniors = new int [MAX];
    juniors = new int[MAX];
    sophomores = new int [MAX];
    
    // generate lottery numbers for the seniors
    for(int i = 0; i <= MAX; i++) {
      min = 0; // range starts at zero
      generateLotNums(seniors, 0);
    }
    // generate lottery numbers for the juniors
    for(int i = 0; i <= MAX; i++) {
      min = MAX*1;
      generateLotNums(juniors, MAX);
    }
    // sophomores
    for(int i = 0; i <= MAX; i++) {
      min = MAX*2;
      generateLotNums(sophomores, 2*MAX);
    }
    
  }
  
  /* This constructor generates lottery numbers and writes it out to a file.
   * This constructor has three parameters: the year the lottery is for, an input file name, 
   * and an output file name
   */
  public LotteryNumbers(int year, String inFileName, String outFileName) {
    this(year);
    
    addFile(inFileName, outFileName);
  }
  
  /* This method takes in an input file name and an output file name. 
   * It writes to a file with the randomly generated lottery numbers attached to 
   * each person's information
   */
  public String addFile(String inFileName, String outFileName) {
    String s = "";
    try {
      Scanner scan = new Scanner(new File(inFileName));
      PrintWriter writer = new PrintWriter(new File(outFileName));
      int sCounter = 0; // counter for the senior lot num array
      int jCounter = 0;
      int soCounter = 0;
      String line = scan.nextLine();
      //System.out.println(line);
      s= s + line+", Lottery Number\n"; // add lottery number to the last column
      String [] info = line.split(",");
      
      while(scan.hasNextLine()) {
        
        line = scan.nextLine();
        s+=line;
        
        info = line.split(",");
        
        int newNum = Integer.parseInt(info[3]);
        //System.out.println(newNum);
        
        // if the student is a senior
        if (newNum == seniorClass) {
          s = s + "," + seniors[sCounter] + "\n";
          
          sCounter++;
        }
        
        // if the student is a junior
        else if(newNum == seniorClass+1) {
          s = s + "," + juniors[jCounter] + "\n";
          jCounter++;
          //line = scan.nextLine();
        }
        
        // if the student is a sophomore
        else if(newNum == seniorClass+2) {
          // writer.write(sophomores.get(soCounter));
          s = s + "," + sophomores[soCounter] + "\n";
          soCounter++;
          //line = scan.nextLine();
        }
        
      }
      
      writer.write(s);
      scan.close();
      writer.close();
      
    }catch (FileNotFoundException e){
      System.out.println(e);
    } 
    System.out.println(s);
    return s;
  }
  
  /*
   public String printArray(int [] list) {
   String s = "";
   for(int i = 0; i < list.length; i++) {
   if(i != list.length-1)
   s = s + list[i] + "\t";
   else
   s = s+list[i];
   }
   return s;
   }
   */
  
  /* This method randomly generates lottery numbers
   */
  public void generateLotNums(int[] list, int start) {
    
    int counter = MAX; // number of numbers to generate
    int [] array = new int[MAX]; // array that stores the randomly generated lottery numbers
    int index = 0;
    for(int i = 0; i < MAX; i++) {
      array[i] = start + i+1;
    }
    
    while(counter > 0) {
      number = start + (int)(Math.random()*(counter-1)); // choose a random number
      
      
      list[index] =  array[number-start]; // put the chosen number from array into index in list
      
      // decrease the array so that we don't choose the same number again
      array[number-start] = array[counter-1]; 
      
      counter--;
      index++;
    } 
  }
  
  /* This method checks if a number is already in the list of numbers
   * @returns boolean
   */
  public boolean contains(int[] list, int n) {
    for(int i = 0; i < list.length; i++) {
      if(list[i] == n)
        return true;
    }
    return false;
  }
  
  /* This method returns the current value of the minimum...
   * @return int
   */
  public int getMin() {
    return min;
  }
  
  /* This method returns MAX, the size of the range
   * @ returns int
   */
  public int getMAX() {
    return MAX;
  }
  
  /* This method prints out a list of randomly generated lottery numbers for each 
   * classyear
   * @return String
   */
  public String toString(){
    
    String s = "Seniors: ";
    for(int i = 0; i < seniors.length; i++) {
      if(i != seniors.length-1)
        s+= seniors[i] +", ";
      else 
        s+= seniors[i];
    }
    s+= "\nJuniors: "; 
    for(int i = 0; i < juniors.length; i++) {
      if(i != juniors.length-1)
        s+= juniors[i] + ", ";
      else
        s+= juniors[i];
    }
    s+= "\nSophomores: ";
    for(int i = 0; i < sophomores.length; i++) {
      if(i != sophomores.length-1)
        s+= sophomores[i] + ", ";
      else
        s+= sophomores[i];
    }
    
    return s;
  }
  
  public static void main(String [] args) {
    /*
     LotteryNumbers second = new LotteryNumbers(2016);
     LotteryNumbers third = new LotteryNumbers(2016,"info2.csv","lotteryNumbers2.txt");
     System.out.println(third);
     */
    
    /*
    LotteryNumbers test100 = new LotteryNumbers(2016, "testResultForNumbers100.csv", 
                                                "testResultsWithNumbers100.csv");
    
    LotteryNumbers test200 = new LotteryNumbers(2016, "testResultForNumbers200.csv",
                                                "testResultsWithNumbers200.csv");
    
    LotteryNumbers test250 = new LotteryNumbers(2016, "testResultForNumbers250.csv",
                                                "testResultsWithNumbers250.csv");
    
    LotteryNumbers test500 = new LotteryNumbers(2016, "testResultForNumbers500.csv",
                                                "testResultsWithNumbers500.csv");
    
    LotteryNumbers test1000 = new LotteryNumbers(2016, "testResultForNumbers1000.csv",
                                                 "testResultsWithNumbers1000.csv");
    */
    LotteryNumbers test2000 = new LotteryNumbers(2016, "testResultForNumbers2000.csv",
                                                 "testResultsWithNumbers2000.csv");
                                                 
  }
  
}