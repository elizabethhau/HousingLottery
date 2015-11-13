/* FILE NAME: Lottery2.java
 * 
 * Elizabeth Hau
 * Emily Cetlin
 * CS 230
 * Wellesley College Housing Lottery
 * Date Created: 11/08/14
 * Last Modification: 2/23/15
 * 
 */

/* UPDATED TO REFLECT TWO NEW PIECES OF INFORMATION: 
 * 1) ONLY RETURNING STUDENTS WILL BE PLACED INTO DORMS - REST WILL BE PRINTED
 * 2) A BLOCK CAN RETURN IF 50% OR MORE WANTS TO RETURN
 */

/* STILL NEED TO account for times when the students enter incorrect information
 */
import java.io.*;
import java.util.*;

/* The Lottery class creates the Lottery that will be doing all the calculations
 * and sorting blocks of students into residence halls according to their preferences. 
 * This class can either simply create an empty Lottery object or create a Lottery
 * object that reads from a file. It can also add another file to an already
 * existing Lottery. 
 * Note: This class considers a block to be the same if there is one student that 
 * is in both blocks.
 * This class also assumes that any inputted file is formatted
 * correctly and does not contain any typos. The primary function for this program
 * is to do the calculation and sort people into dorms, so this class doesn't 
 * contain any setter or getter methods.
 * Once the lottery has been calculated and students are placed into dorms, 
 * additions to the lottery do not reconsider those who have been placed.
 * For now, this program is able to sort up to 40 blocks of students (20 in each
 * queue). If this was extended into a larger scale version, we would allow for up
 * to 2500 blocks.
 */


public class Lottery22015 {
  
  // instance variables
  //private PriorityQueue<Block> queue;
  private TreeMap <Double, Block> generalLot; // contains people in the general lottery
  private PriorityQueue<Block> preference; // priority queue for the students who wish to return
  // for people who want to stay in same resHall
  private final int SIZE = 100; // number of blocks in each priority queue. 
  // looking at smaller scale of school for now
  // If extened, would be 2500 (worst case scenario: each student in own block).
  
  private Hashtable<String, Student> check; // Hash table of students to cross check info
  private ResHall[] reshalls;
  private boolean isCalculated;
  private int year; // upcoming senior class graduation year
  
  //private LinkedList<Block> unhoused;
  
  /* The first constructor of the Lottery class initializes all instance variables 
   * including a priority queue of size "SIZE" that contains blocks of students
   * who do not wish to return to the same ResHall, a priority queue of size "SIZE"
   * that contains blocks of students who do wish to return to the same ResHall,
   * a list of all the dorms, whether the Lottery has been calcuated, 
   * and a list of students that are unhoused. All the dorms that are 
   * going to house students are initialized here and added to an array that is a
   * collection of the dorms.
   */
  public Lottery22015(int y) {
    //queue = new PriorityQueue<Block>(SIZE);
    generalLot = new TreeMap();
    preference = new PriorityQueue<Block>(SIZE);
    isCalculated = false;
    check = new Hashtable<String, Student>(SIZE);
    year = y; 
    
    ResHall beebe = new ResHall("Beebe", 10, 10, 10, 10);
    ResHall cazenove = new ResHall("Cazenove", 10, 10, 10, 10);
    ResHall pomeroy = new ResHall("Pomeroy", 10, 10, 10, 10);
    ResHall shafer = new ResHall("Shafer",10, 10, 10, 10);
    ResHall munger = new ResHall("Munger",10, 10, 10, 10);
    ResHall tower = new ResHall("Tower",10, 10, 10, 10);
    ResHall claflin = new ResHall("Claflin",10, 10, 10, 10);
    ResHall severance = new ResHall("Severance",10, 10, 10, 10);
    ResHall lakeHouse = new ResHall("Lake House",10, 10, 10, 10);
    ResHall bates = new ResHall("Bates",10, 10, 10, 10);
    ResHall freeman = new ResHall("Freeman",10, 10, 10, 10);
    ResHall mcafee = new ResHall("McAfee",10, 10, 10, 10);
    ResHall stoneD = new ResHall("Stone Davis",10, 10, 10, 10);
    ResHall dower = new ResHall("Dower",10, 10, 10, 10);
    
    // the residence halls that will be in the lottery for the upcoming year
    reshalls = new ResHall[]{ beebe, cazenove, pomeroy, shafer, munger, tower, claflin, severance,
      lakeHouse, bates, freeman, mcafee, stoneD, dower};
    
    
  }
  
  /* The second constructor takes in a String file name in the parameter that
   * allows the Lottery class to read from a file, create blocks of students, and 
   * sort them into dorms according to the information provided in the file. It 
   * calls the addFile helper method to read in the file and add it to the lottery.
   * 
   */
  public Lottery22015(int y, String fileName) {
    this(y);
    addFile(fileName);
  }
  
  /* This method allows a user to add a file to an existing lottery 
   * object. This method is beneficial because it doesn't restrict users from
   * adding a file only at the very beginning of initalizing a lottery. It also
   * enables the user to add multiple files to a lottery. 
   * 
   * This method uses the readStudent method to easily add a line from the file
   * to a block. Once each block is created, the method adds the block to the 
   * lottery and continues to do this until all blocks from the file have been
   * added.
   * 
   * Note: When creating every new block, we throw away the first line of the file
   * that reads "Block: ###" because it is not necessary to create the block. 
   * Although it is not necessary to keep, we found this line to be useful when
   * the document is being looked at by a user. This is also why we made the 
   * decision to separate blocks with a "*". 
   */
  public void addFile(String fileName) {
    try {
      Scanner scan = new Scanner(new File(fileName));
      scan.nextLine(); //throw away
      while(scan.hasNextLine()) {
        String line = scan.nextLine();
        String[] input = line.split(",");
        int size = Integer.parseInt(input[2]); // input[2] tells us the size of the block
        int start = 3; // start for 1 person block is the 3rd column
        for(int i = size-1; i > 0; i--){
          start += 7*i; //finds starting index of block
        }
        Student student = readStudent(input, start);
        size--;
        Block b = new Block(student, year);
        //b.setResHallPref(input[start+5]);
        while(size != 0) {
          start += 7;
          student = readStudent(input, start);
          b.addStudent(student);
          size--;
        }
        this.addBlock(b);
      }
      scan.close();
    } catch (FileNotFoundException e){
      System.out.println(e);
    } 
  }
  
  // Constructor to add a cross check file
  public Lottery22015(int y, String fileName, String crossCheckFile) {
    this(y);
    checkFile(crossCheckFile);
    addFile(fileName);
  }
  
  // Updates the Hashtable to be used to cross check info using given file
  public void checkFile(String file) {
    try {
      Scanner scan = new Scanner(new File(file));
      scan.nextLine(); //throw away
      while(scan.hasNextLine()) {
        String line = scan.nextLine();
        String[] input = line.split(",");
        int classyear = Integer.parseInt(input[3]);
        double num = Double.parseDouble(input[6]);
        Student s = new Student(input[2] + " " + input[1], classyear, input[4], num, 
                                false, input[5]);
        //username, last name, first name, class year, bnum, 
        //current ResHall,lot num
        check.put(input[0], s);
      }
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
  }
  
  /* private helper method that takes in a String that contains information about
   * a Student, splits the information about the delimeter ", " and stores it in
   * an array, then returns a Student. This method helps the program because there
   * are two situations in which we need to create a student from a line from the
   * file: one in order to create the block, and one in the while loop that adds to
   * the already created block. Also, this method is used in two different methods
   * that read in a file, simplifying the number of times we need to repeat this 
   * code from 4 to 1.
   * @ returns Student
   */
  private Student readStudent(String[] s, int i) {
    double lotNum = Double.parseDouble(s[i+4]);
    boolean r = false; // r for return
    if (s[i+6].equals("Yes"))
      r = true;
    /*
     String classyear = "";
     if(s[i+3].equals("2016"))
     classyear = "Senior";
     else if (s[i+3].equals("2017"))
     classyear = "Junior";
     else classyear = "Sophomore";
     */
    int classyear = Integer.parseInt(s[i+3]);
    Student a = new Student(s[i+1], classyear, s[i+2], lotNum, r, s[i+5]);
    if (!check.isEmpty()) {
      //check info in check
      String username = s[i];
      Student b = check.get(username);
      if(a.getLotteryNum() != b.getLotteryNum()) {
        a.setLotteryNum(b.getLotteryNum());
      }
      if(a.getCurrentResHall() != b.getCurrentResHall()) {
        a.setCurrentResHall(b.getCurrentResHall());
      }
    }
    return a;
  }
  
  /* This method checks to see if a lottery is empty. This method will return true
   * if there have never been any blocks placed into the lottery or if the lottery
   * has already been sorted and all of the blocks have been placed.
   */
  public boolean isEmpty() {
    return (generalLot.size()==0);
  }
  
  /* This method adds a block to the queue. This method uses the canAddBlock method
   * to check to see if the block can be added to either priority queue. 
   * If the block can be added and wants to return to the same ResHall, the block
   * is placed in the preference queue. Otherwise, it is placed in the general lottery.
   */
  public void addBlock(Block b) {
    if (!generalLot.containsValue(b) && canAddBlock(preference, b)){
      if (b.getStayResHall()) preference.add(b);
      else generalLot.put(b.getAverage(),b);
    } else {
      /*
      System.out.println("block NOT added\n");
      System.out.println(canAddBlock(preference, b));
      */
    }
  }
  
  /* The method checks to see if a block can be added to a specified priority 
   * queue. The block returns true if the block can be added to the lottery
   * and returns false if the block cannot be added to the lottery. This 
   * method uses the equals method from the Block class.
   * from the Block class.
   */
  private boolean canAddBlock(PriorityQueue<Block> queue, Block b) {
    Iterator iter = queue.iterator();
    while(iter.hasNext()) {
      Block b1 = (Block)iter.next();
      if(b1.equals(b)) {
        return false;
      }
    }
    return true;
  }
  
  
  /* This method returns the result of whether the calculate() method has been
   * called or not.
   * @ returns boolean
   */
  public boolean isCalculated() {
    return isCalculated;
  }
  
  /* This method determines what residence hall each block gets assigned to according to 
   * their preferences, giving preference to blocks of students that wish
   * to return to the same ResHall (by going through the preference queue first). 
   * The method has a boolean variable that keeps track of whether a block has
   * been placed into a residence hall or not.
   */
  public void calculate(String fileName) {
    isCalculated = true;
    // goes through the preference queue and tries to add each block to the res hall
    // they want to return to. Otherwise, the block is placed in the general lottery
    while(!preference.isEmpty()) {
      Block b = preference.poll();
      //b.linkDoubles(); //don't need to link doubles anymore with new system
      LinkedList<String> list = b.getReturnPref(); // list of res halls the block can return to
      int j = 0;
      int i = 0;
      //System.out.println(list.size());
      //System.out.println(quad.length);
      
      boolean r = false; // whether a block is in a res hall already
      //System.out.println("\n\nBlock we are looking at: " + b);
      // go through the list of halls the block can return to
      while(j < list.size()){
        while(j < list.size() && i < reshalls.length){
          // find the res hall in the list of residence halls
          if(list.get(j).equals(reshalls[i].getResHallName())) {
            r = reshalls[i].addBlock(b); // attempt to add the block to the specific res hall
            //System.out.println("The block is added to the reshall " + reshalls[i].getResHallName() + "? " + r);
            //System.out.println("i = " + i + " and j = " + j);
            //System.out.println("Block added: " + b + "\nTo ResHall: " + list.get(j) + "\n");
            //System.out.println("i = " + i + " and j = " + j + "\n");
            if(r) j = list.size();
            else {
              j++;
              i = 0;
            }
          } else {
            i++;
          }
        }
        j++;
        i=0;
      }
      // if the block has not been assigned to a res hall after going through the list
      // of residence halls that they can return to, add the block to the general lottery
      if(!r)
        generalLot.put(b.getAverage(),b);
    }
    
    writeToFile(fileName);
  }
  
  /* Private helper method that creates a text file of the results after it has
   * been calculated
   */
  private void writeToFile(String fileName) {
    try {
      PrintWriter writer = new PrintWriter(new File(fileName));
      writer.write(toString());
      writer.close();
    } catch (FileNotFoundException e){
      System.out.println(e);
    } 
  }
  
  public int getLotYear() {
    return year;
  }
  
  /* Returns a String representation of the Lottery class.
   * If the calculate method has been called, the lottery result is printed and
   * each dorm along with the students assigned to the dorm will be printed.
   * The list of students who are unhoused will also be printed.
   * If the calculate method has not been called, we will simply print out the 
   * priority queue as specified in the Java API. 
   * @ returns String
   */
  public String toString() {
    String s = "";
    s = print();
//    if(isCalculated) {
//      s += "Lottery Results:";
//      for(int i = 0; i < quad.length; i++)
//        s += "\n" + quad[i] + "\n";
//      s += "\nUnhoused Students:";
//      for(int j = 0; j < unhoused.size(); j++)
//        s += "\n" + unhoused.get(j);
//    } else {
//      Iterator iter = queue.iterator();
//      while(iter.hasNext())
//        s += iter.next() + "\n*\n";
//    }
    return s;
  }
  
  /* Alternative print method that splits students back up from their block after
   * they have been housed
   */
  private String print(){
    String s = "";
    if(isCalculated) {
      s += "Lottery Results:";
      for(int i = 0; i < reshalls.length; i++)
        s += "\n" + reshalls[i];
      //System.out.println("fine");
      s += "\n\nRemaning Students:\n";
      /*for(int j = 0; j < unhoused.size(); j++)
       s += "\n" + unhoused.get(j);*/
    } else {
      s += "Uncalculated Lottery:\n";
      Iterator iter = preference.iterator();
      while(iter.hasNext())
        s += iter.next() + "\n\n";
    }
    s += printTree(generalLot) + "\n";
    //while(iter.hasNext())
    //s += iter.next() + "\n*\n";
    // }
    return s;
  }
  
  
  /* Alternative print method for printing the TreeMap so that only the values (Blocks)
   * are printed and not the keys. 
   * @ returns String
   */
  private String printTree(TreeMap <Double, Block> t) {
    // get the set of keys in the TreeMap
    Set<Double> keys = t.keySet();
    //System.out.println("\n" + keys);
    
    // iterator that iterates through the keys
    Iterator iter = keys.iterator();
    String s = "";
    
    // while there is still a key, get the value & append it to the string to be returned
    while(iter.hasNext()) {
      Double nextKey = (Double)iter.next();
      s += t.get(nextKey) + "\n\n";
    }
    return s;
  }
  
  /* main method for testing
   */
  public static void main(String [] args) {
    // testing for 100 students
    /*
     Lottery22015 test100 = new Lottery22015(2016,"testResultsForLottery100.csv","testResultsWithNumbers100.csv");
     test100.calculate("LotteryTest100");
     System.out.println("\nCalculated Result:\n" + test100);
     
    
     Lottery22015 test200 = new Lottery22015(2016,"testResultsForLottery200.csv","testResultsWithNumbers200.csv");
     test200.calculate("LotteryTest200");
     System.out.println("\nCalculated Result:\n" + test200);
    
    
     Lottery22015 test250 = new Lottery22015(2016,"testResultsForLottery250.csv","testResultsWithNumbers250.csv");
     test250.calculate("LotteryTest250");
     System.out.println("\nCalculated Result:\n" + test250);
     
    
     Lottery22015 test500 = new Lottery22015(2016,"testResultsForLottery500.csv","testResultsWithNumbers500.csv");
     test500.calculate("LotteryTest500");
     System.out.println("\nCalculated Result:\n" + test500);
     
    
     Lottery22015 test1000 = new Lottery22015(2016,"testResultsForLottery1000.csv","testResultsWithNumbers1000.csv");
     test1000.calculate("LotteryTest1000");
     System.out.println("\nCalculated Result:\n" + test1000);
     */
    
     Lottery22015 test2000 = new Lottery22015(2016,"testResultsForLottery2000.csv","testResultsWithNumbers2000.csv");
     test2000.calculate("LotteryTest2000");
     //System.out.println("\nCalculated Result:\n" + test2000);
    /*
    Lottery22015 lot = new Lottery22015(2016,"responses2.csv");
    System.out.println(lot);
    lot.calculate("Housing Lottery 2015");
    System.out.println("\n\nCalculated Results:\n" + lot);
    */
    
    
    /*System.out.println("A new lottery has been created with two different blocks.");
     Lottery2 lottery1 = new Lottery2();
     Block b1 = new Block(new Student("Elizabeth Hau","Junior", "B27846395",908));
     b1.addStudent(new Student("Emily Ahn", "Junior", "B29047385", 972));
     b1.setResHallPref("Pomeroy","Cazenove","Beebe","Shafer");
     
     Block b2 = new Block(new Student ("Emily Cetlin","Senior", "B20679124",357));
     b2.addStudent(new Student("Emma Kaufman","Senior","B20694375",125));
     b2.addStudent(new Student("Michaela Fendrock","Senior","B20684294",52));
     b2.setResHallPref("Beebe","Cazenove","Pomeroy","Shafer");
     
     lottery1.addBlock(b1);
     lottery1.addBlock(b2);
     
     System.out.println("Created a new block with the same students from one of "
     + "the blocks, but in a different order. When we try "
     + "to add it:");
     Block b5 = new Block(new Student("Emma Kaufman","Senior","B20679124",125));
     b5.addStudent(new Student("Emily Cetlin","Senior","B20694375",357));
     b5.addStudent(new Student("Michaela Fendrock","Senior","B20684294",52));
     b5.setResHallPref("Beebe","Cazenove","Pomeroy","Shafer");
     
     lottery1.addBlock(b5);
     
     System.out.println("Two identical blocks have been created. When we attempt "
     + "to add both we get the result for the second attempt: ");
     Block b3 = new Block(new Student("Sen Sing","Senior","B20648374",0.01));
     b3.setResHallPref("Cazenove","Shafer","Pomeroy","Beebe");
     lottery1.addBlock(b3);
     
     Block b4 = new Block(new Student("Sen Sing","Senior","B20648374",0.01));
     b4.setResHallPref("Cazenove","Shafer","Pomeroy","Beebe");
     lottery1.addBlock(b4);
     
     System.out.println("A student appears in two different blocks. When "
     + "the second block is added:");
     Block b6 = new Block(new Student("Emily Cetlin","Senior","B20679124",357));
     b6.addStudent(new Student("Elizabeth Hau","Junior","B27846395",908));
     b6.addStudent(new Student("Geralle","Junior","B27849837",570));
     
     lottery1.addBlock(b6);
     
     System.out.println("A lottery has been created with three blocks:");
     System.out.println("Before calculate: \n" + lottery1);
     System.out.println("The lottery is calculated (FALSE):\tget: " 
     + lottery1.isCalculated());
     lottery1.calculate();
     System.out.println("\nExpected after calculate: Block 908 in Pomeroy, "
     + "Block 178 in Beebe, Block 0.01 in Cazenove.");
     System.out.println("\nAfter calculate:\n" + lottery1);
     
     System.out.println("\nLottery is calculated (TRUE):\tget: " 
     + lottery1.isCalculated());
     
     Lottery2 lottery2 = new Lottery2("TestFile1_UPDATED.txt");
     System.out.println("\n*************************************************\n"
     + "A new lottery is created from TestFile1_UPDATED.txt:"
     + "\nReading in from text:\n" + lottery2);
     lottery2.calculate();
     System.out.println("\nCalculated test file:\n" + lottery2 + "\n\n");
     
     System.out.println("***************************************"
     +"\nA new lottery is created from TestFile2_UPDATED.txt:");
     Lottery2 lottery3 = new Lottery2("TestFile2_UPDATED.txt");
     lottery3.calculate();
     System.out.println("\nTesting \"TestFile2_UPDATED.txt\":");
     System.out.println("Expect Amanda to be placed into Beebe and Jamie unhoused.");
     System.out.println("Reulsts:");
     System.out.println(lottery3);
     
     System.out.println("\n**************************************");
     System.out.println("\nAn example of adding multiple files:");
     Lottery2 lottery4 = new Lottery2("TestFile1_UPDATED.txt");
     lottery4.addFile("TestFile2_UPDATED.txt");
     System.out.println("\nReading in from text:\n" + lottery4);
     lottery4.calculate();
     System.out.println("Expected: Block 149 in Shafer, Block 230.833 in Pomeroy, "
     + "Block 228.0006 in Beebe, Block 233 in Unhoused, Block "
     + "940 in Cazenove, Block 1680 in Shafer, Block 1777 in "
     + "Pomeroy, Block 1488 in Pomeroy, Block 389 in Beebe, "
     + "Block 0.001 in Unhoused.");
     System.out.println("\nCalculated test file:\n" + lottery4);
     
     System.out.println("\nIs this lottery now empty? (TRUE) " + lottery4.isEmpty());
     */
  }
}