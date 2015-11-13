/* FILE NAME: Block.java
 * 
 * Elizabeth Hau
 * Emily Cetlin
 * CS 230
 * Final Project - Wellesley College Housing Lottery
 * Date Created: 05/01/14
 * Last Modification: 02/23/14
 * 
 * 
 * The Block class creates a block object that contains a group of students. A 
 * block can have 1 to 4 students and the number of students is stored in the 
 * block. Each block also contains a LinkedList of the students in the block, a
 * LinkedList of their dorm preferences, the average of the students' lottery 
 * numbers, and an array of the rising class years of all the students in the block. 
 * The LinkedList of reshall preferences is of type String because the name of the 
 * residence hall is the only information needed in the preferences. 
 * The Block class implements the Comparable interface because we use the compareTo() 
 * method to determine the order of Blocks in the priority queue later on in the 
 * Lottery class.
 */


import java.util.*;
import java.util.Arrays;

public class Block implements Comparable<Block>{
  
  // instance variables
  private LinkedList<Student> students;
  private LinkedList<String> dormPref; // list of other res halls the block is willing to live in
  private double avg; // average of everyone's lottery numbers
  private int size; // size of the block
  private int[] yearArray; // # of students from each class year in the block
  private int year; // class year for the rising seniors
  
  //private boolean stay; // tells us if everyone in the block wants to stay in resHall
  //private double numStay; //keeps track of number of people in block who can return
  
  private LinkedList<String> returnPref; // the res halls they want to return to
  
  //alt method
  private Hashtable<String,Integer> returnHall; // String: Res halls, integer: # students who can return
  
  private final int MAX = 4; // maximum size of the block
  
  /* Constructor creates a block object that takes in a student as the 
   * parameter. It initially creates a block that contains one student.
   * Information stored in a block include the average lottery number of the block,
   * the list of students in the block, their class year(s), the size of the block, 
   * and the dorm preferences of the students in the block.
   */
  public Block(Student s, int y)  {
    year = y;
    students = new LinkedList<Student>();
    students.add(s);
    avg = s.getLotteryNum();
    size = 1;
    dormPref = new LinkedList<String>();
    yearArray = new int[MAX]; // # of FYs, Sophomores, juniors, and seniors in the block
    yearUpdate(s);
    returnHall = new Hashtable<String,Integer>(MAX);
    returnPref = new LinkedList<String>();
    if(s.getStayResHall()) {
      returnHall.put(s.getCurrentResHall(), 1);
    } 
  }
  
  /* This helper method gets the class year of the student and adds the student's
   * class year to the appropriate spot in the array of class years
   * 
   * purpose: keep track of # of available rooms for each class year in various res halls
   */
  private void yearUpdate(Student s) {
    int y = s.getYear(); // class year of student s
    
    if (y == year)
      yearArray[3] += 1;
    else if (y == year + 1)
      yearArray[2] += 1;
    else if (y == year + 2)
      yearArray[1] += 1;
    else if (y == year + 3)
      yearArray[0] += 1;
  }
  
  /* This method adds a student to the block.
   * The method first checks whether a student can be added to the block and adds
   * the student to the block if the block has not reached its maximum size (4) yet.
   * If the block is already full before a student is added, a message
   * is displayed and the student doesn't get added to the block.
   */
  public void addStudent(Student s) {
    if(size < MAX) {
      students.add(s);
      size++;
      avg = ((avg*(size-1)) + s.getLotteryNum())/size;
      yearUpdate(s);
      if(s.getStayResHall()) {
        String hall = s.getCurrentResHall();
        if(returnHall.get(hall) == null) // if the hall is not in the HashTable yet, put it in
          returnHall.put(hall, 1);
        else returnHall.put(hall, returnHall.get(hall)+1); // else increase the "counter" (i.e.key)
        }
    } else {
      System.out.println("Sorry you have already reached the maximum number of" +
                         " students allowed in a Block.");
    }
  }
  
  /* This method links the students who are going to be roommates together.
   * This method uses a for loop to go through the linkedlist of students,
   * checking if they have a roommate and if they do, link them together by
   * updating their lottery number so that their new lottery number is the 
   * average of their lottery numbers. This allows the doubles to stay as a
   * unit.
   */
  /*
  public void linkDoubles() {
    for(int i = 0; i < students.size(); i++) {
      Student s = students.get(i);
      String sbnum = s.getBnumber();
      //String rbnum = s.getRoommate();
      // if student has a roommate
      if(rbnum != "") {
        for(int j = i+1; j < students.size(); j++) {
          Student r = students.get(j);
          String num = r.getBnumber();
          if (num.equals(rbnum))
            s.updateLotteryNum(r);
        }
      }
    }
  }
  */
  
  /* This method sets the res hall preferences by taking in a variable length parameter
   * list. Each item is added to the dormPref linked list.
   */
  public void setResHallPref(String ... dorm) {
    for (int i = 0; i< dorm.length; i++)
      dormPref.add(dorm[i]);
  }
  
  /* returns the average of the lottery numbers of every student in the block,
   * @returns double
   */
  public double getAverage() {
    return avg;
  }
  
  /* returns true if the entire block wants to stay in the same res hall.
   * else returns false.
   * 
   * @ returns boolean
   */
  public boolean getStayResHall(){
    boolean result = false;
    Set<String> set = returnHall.keySet(); // list of halls in the block
    String[] array = set.toArray(new String[set.size()]);

    int value = (size+1)/2;
    for(int i = 0; i< array.length; i++) {
      if (returnHall.get(array[i]) >= value) {
        if (!returnPref.contains(array[i]))
          returnPref.add(array[i]);
        result = true;
      }
    }
    return result;
  }
  
  /* Implementation of the Comparable interface.
   * This method compares two blocks (this and b) by comparing their averages 
   * and returns 0 if two blocks have the same average, 1 if this has a greater 
   * average than b, and -1 if this has a smaller average than b
   * @ returns int
   */
  public int compareTo(Block b) {
    if(avg == b.getAverage()) return 0;
    else if (avg > b.getAverage()) return 1;
    else return -1;
  }
  
  /* This method detemines if two blocks are the same by comparing each student in
   * the first LinkedList to each student in the second LinkedList. It checks
   * if there are any students that are the same. If there are any repeated
   * students, the blocks are considered the same.
   * @ returns boolean
   */
  public boolean equals(Block b) {
    LinkedList<Student> s1 = this.getStudents();
    LinkedList<Student> s2 = b.getStudents();
    for (int i = 0; i<s1.size(); i++) {
      Student s = s1.get(i);
      for (int j = 0; j<s2.size(); j++) {
        if(s.compareTo(s2.get(j)) == 0) return true;
      }
    }
    return false;
  }
  
 /* This method returns a list of the residence halls that the block can return 
  * to (need at least 50% of the block currently living there).
  * @ return LinkedList<String>
  */
  public LinkedList<String> getReturnPref() {
    getStayResHall();
    return returnPref;
  }
  
  /* This method returns the list of students that are in the block.
   * @ returns LinkedList <Student>
   */
  public LinkedList<Student> getStudents() {
    return students;
  }
  
  /* This method returns the list of dorm preferences of the block.
   * @ returns LinkedList <String>
   */
  public LinkedList<String> getResHallPref() {
    return dormPref;
  }
  
  /* This method returns the size of the block (i.e. the number of students
   * that are in the block).
   * @ returns int
   */
  public int getSize() {
    return size;
  }
  
  
  /* This method returns the array which includes information on the number of
   * students in each class year (Senior, Junior, Sophomore, or First Year, 
   * respectively) of the block. 
   * THIS WILL PRINT OUT THE ADDRESS OF THE ARRAY, not the contents.
   * @ returns int []
   */
  public int[] getYearArray() {
   return yearArray;
   }
  
  /* This method returns the number of students in each class year (Senior, Junior, 
   * Sophomore, or First Year, respectively) of the block. 
   * @ returns String
   */
  public String getYear() {
    String y = "[";
    for(int i = 0; i < yearArray.length; i++) {
      if(i != yearArray.length-1)
        y += yearArray[i] + ",";
      else
        y+= yearArray[i];
    }
    return y+"]";
  }
  
  /* Returns the String representation of the Block class
   * @ returns String
   */
  public String toString() {
    String s = "Block: " + avg + "\tCan return: " + getReturnPref() + "\tReturn? " + getStayResHall();
    for(int i = 0; i < size; i++) 
      s += "\n" + students.get(i);
    return s;
  }
  
  /* main method for testing
   */
  public static void main(String[] args) {
    Student s1 = new Student("Soph Sing",2018,"B20679888",1396);
    s1.setCurrentResHall("Freeman");
    s1.stayResHall(true);
    Block b1 = new Block(s1, 2016);
    
    System.out.println("b1 only contains " + b1.getSize() + " student:\n" + b1);
    System.out.println("\nThe average of b1: (expected 1396.0)\tget: " 
                         + b1.getAverage());
    
    b1.addStudent(new Student("Sophie",2018,"B27846758",1475));
    
    b1.addStudent(new Student("Susie",2018,"B27847688",1598));
    b1.addStudent(new Student("Sam",2018,"B27839286",1307));
    b1.addStudent(new Student("Sarah",2018,"B27899446",1894));
    System.out.println("\nb1 now contains " + b1.getSize() +" students:\n" + b1);
    System.out.println("\nThe average of b1: (expected 1444.0)\tget: " 
                         + b1.getAverage());
    System.out.println(b1);
    
    System.out.println("\nThe size of the block is now (4):" + b1.getSize());
    /*
    System.out.println("\nTries to add a 7th student to the block");
    b1.addStudent(new Student("Shan","Sophomore","B27991555",1792));
    System.out.println(b1);
    System.out.println("The size of the block is " + b1.getSize());
    
    System.out.println("\nThe students in this block's class year: \tExpected: "
                       + "[0,6,0,0]\tget: " + b1.getYear());
    
    b1.setResHallPref("Beebe","Shafer","Cazenove","Pomeroy");
    System.out.println("\nSetting the res hall preferences to be: Beebe, Shafer, "
                         + "Cazenove, and Pomeroy.");
    System.out.println("ResHall preferences for Block b1:\n" + b1.getResHallPref());
    
    System.out.println("\nThe students in this block are:\n" + b1.getStudents());
    */
    
    // Block 2 is newly added test code
    Block b2 = new Block(new Student("Ely",2018,"B27946388",1200), 2016);
    b2.addStudent(new Student("Maria",2017,"B20628446",704));
    b2.addStudent(new Student("Teddy",2016,"B20671456",348));
    
    System.out.println("\nThe students in block 2 are:\n" + b2.getStudents());
    System.out.println("The size of block 2 (3): " + b2.getSize());
    System.out.println("The average of block 2 is (750.667): " + b2.getAverage());
    System.out.println("The number of students of each class year in block 2: " +
                       "(expected: [0,1,1,1])\tget: " +b2.getYear());
    
    Block b5 = new Block(new Student("Jamie Ren", 2017,"B27835748",27), 2016);
    Block b6 = new Block(new Student("Jamie Ren", 2017,"B27835748",27), 2016);
    b6.addStudent(new Student("Alexa Smith", 2018,"B27846379",45));
    System.out.println("\nTwo blocks have been created. They contain one student"
                         + " in common but are otherwise different. Are they "
                         + "equal? (TRUE): " + b5.equals(b6));   
    /*
    Block b7 = new Block(new Student("Molly Baker","Senior","B20394857",147)); // constructor 1
    b7.addStudent(new Student("Serena Wozniak","Junior","B28394857",478, true)); // constructor 2
    b7.addStudent(new Student("Steph Park","Sophomore","B29384756",1023,"B28364958")); // constructor 3
    //b7.addStudent(new Student("Megan Marcus","Sophomore","B28364958",799,true,"B29384756")); // constructor 4
    System.out.println("\nA new block is created containing a senior (Molly), a junior (Serena), and " + 
                       "two sophomores (Steph, Megan) who are roommates. Two of the students in " +
                       "the block have indicated that they would like to return to" + 
                       " the same res hall.\n" + b7);
    System.out.println("The size of the block is (4): " + b7.getSize());
    
    System.out.println("\nThe students in this block's class year: \tExpected: "
                       + "[0,2,1,1]\tget: " + b7.getYear());
    
    b7.setResHallPref("Pomeroy","Beebe","Cazenove","Shafer");
    System.out.println("\nSetting the dorm preferences to be: Pom, Beebe "
                         + "Cazenove, and Shafer.");
    System.out.println("ResHall preferences for Block b7:\n" + b7.getResHallPref());
    
    System.out.println("The students in this block are:\n" + b7.getStudents());
    
    System.out.println("The average of this block (611.75): " + b7.getAverage());
    System.out.println("Is this block returning to the same res hall (FALSE): " + 
                       b7.getStayResHall());
    System.out.println("Link the doubles");
    //b7.linkDoubles();
    System.out.println("The block is now: \n" + b7); 
    */
    //Block tester = new Block(new Student("Jane", "Senior", "B20456883", 52, true, "Caz"));
  }
  
}