/* FILE NAME: ResHall.java
 * 
 * Elizabeth Hau
 * Emily Cetlin
 * CS 230
 * Final Project - Wellesley College Housing Lottery
 * Date Created: 05/01/14
 * Last Modification: 02/03/15
 * 
 * The ResHall class creates a dorm by taking in the dorm's name and number of each
 * type of room. This class assumes that we are only looking at singles and doubles
 * and removes suites from the equation. Each dorm also contains a linked list, 
 * representing the students in the dorm organized by their housing block. The 
 * class contains getter methods for each instance variable in the dorm, but there
 * are no setters because we do not want the user to be able to change the contents
 * of the dorm. The only 'setter' type method in the class is the addBlock method
 * which allows the user to add a block of students to the dorm. This adds the 
 * block to the residents list and decreases the number of empty rooms in the dorm.
 * The class also contains a private method that checks to see if it is possible
 * to add a block. This is used in the addBlock method before attempting to add the
 * block to the dorm and is used to tell the user if the block was added or not.
 */

import java.util.*;

public class ResHall{
  
  // instance variables
  private String name; // name of the residence hall
  
  // # of senior/ junior singles, sophomore/ first-year doubles
  private int senSing, junSing, sophDoub, fyDoub;
  private int[] empty; // # of empty rooms for each class year
  private LinkedList<Block> residents; // list of all the residents
  private int size; // the number of people living in the residence hall
  
  
  /* This constructor creates a dorm, taking 5 parameters: the dorm name, number of 
   * singles for seniors, singles for juniors, doubles for sophomores, and doubles 
   * for first years. It creates a dorm with the given number of each type of rooms
   * and sets the number of empty rooms of each type to be the same. The number
   * of empty rooms are kept track of in an integer array. The 
   * constructor also creates an empty linked list of residents, which will be used
   * to store the blocks of students as they are placed into the dorm.
   */
  public ResHall(String n, int s, int j, int so, int f) {
    name = n;
    senSing = s;
    junSing = j;
    sophDoub = so;
    fyDoub = f;
    residents = new LinkedList<Block>();
    size = senSing + junSing + (2*sophDoub) + (2*fyDoub);
    empty = new int[] {fyDoub, sophDoub, junSing, senSing};
    
  }
  
  /* Returns the name of the ResHall.
   * @ returns String
   */
  public String getResHallName() {
    return name;
  }
  
  /* Returns the total number of rooms in a dorm
   * @ returns int
   */
  public int getTotalRooms() {
    return senSing + junSing + sophDoub + fyDoub;
  }
  
  /* Returns the string representation of the number of empty first year doubles,
   * sophomore doubles, junior singles, and senior singles
   * @ returns String
   */
  public String getEmptyRooms() {
    String r = "[";
    for(int i = 0; i < empty.length; i++) {
      if(i != empty.length - 1)
        r += empty[i] + ",";
      else
        r += empty[i] + "]";
    }
    return r;
    }
  /* Returns the total number of singles in the dorm that are assigned to rising
   * seniors.
   * @ returns int
   */
  public int getSeniorSingles() {
    return senSing;
  }
  
  /* Returns the number of singles that have not yet been assigned to rising 
   * seniors.
   * @ returns int
   */
  public int getEmptySeniorSingles() {
    return empty[3];
  }
  
  /* Returns the total number of singles in the dorm that are assigned to rising 
   * juniors.
   * @ returns int
   */
  public int getJuniorSingles() {
    return junSing;
  }
  
  /* Returns the number of singles that have not yet been assigned to rising 
   * juniors.
   * @ returns int
   */
  public int getEmptyJuniorSingles() {
    return empty[2];
  }
  
  /* Returns the total number of doubles in the dorm that are assigned to rising
   * sophomores.
   * @ returns int
   */
  public int getSophomoreDoubles() {
    return sophDoub;
  }
  
  /* Returns the number of doubles that have not yet been assigned to rising 
   * sophomores.
   */
  public int getEmptySophomoreDoubles() {
    return empty[1];
  }
  
  /* Returns the total number of doubles in the dorm that are assigned to rising 
   * first years.
   * @ returns int
   */
  public int getFYDoubles() {
    return fyDoub;
  }
  
  /* Returns the number of doubles that have not yet been assigned to rising first
   * years.
   * @ returns int
   */
  public int getEmptyFYDoubles() {
    return empty[0];
  }
  
  /* Returns the blocks of students that are residents of the dorm.
   * @ returns LinkedList<Block>
   */
  public LinkedList<Block> getResidents() {
    return residents;
  }
  
  /* This method checks to see if a block will be able to fit into a dorm. It takes
   * the year of the students in the block (using the assumption that all students
   * in a block are from the same year) and the number of students in the block. 
   * If the students are rising seniors/juniors and there are enough empty 
   * senior/junior singles, or if they are sophomores/first years and there are 
   * enough sophomore/first year doubles, the method will return true. Otherwise,
   * it will return false. 
   * @ returns boolean
   */
  private boolean canAddBlock(Block b) {
    int[] y = b.getYearArray();
    //int s = b.getSize();
    for(int i = 0; i<y.length; i++) {
      if((i==0) || (i==1)) {
        if((y[i]/2) > empty[i])
          return false;
      } else {
        if(y[i] > empty[i])
          return false;
      }
    }
    return true;
  }
  
  /* This method checks if a block can be added to a dorm by using the 
   * canAddBlock method. If the result is true, the method will take the year of
   * the block and decrease the corresponding number of empty rooms by the size
   * of the block. The method then adds the block to the list of residents and
   * returns true if the block was successfully added. If the block was not 
   * added to the dorm, the method returns false. This method returns a boolean
   * result because it helps us determine whether the block was added to a dorm.
   * @ returns boolean
   */
  public boolean addBlock(Block b) {
    boolean result = canAddBlock(b);
    if(result) {
      int[] y = b.getYearArray();
      //int s = b.getSize();
      for(int i = 0; i<y.length; i++) {
        if((i==0) || (i==1))
          empty[i] -= (y[i]/2);
        else
          empty[i] -= y[i];
      }
      residents.add(b);
    }
    return result;
  }
  
  /* This toString method prints out the residents in the dorm in the order of their individual 
   * lottery numbers (the order in which they will be choosing their rooms).
   * @ returns String
   */
  public String toString() {
 String s = "\nResHall: " + name + "\nSenior singles: " + senSing + "\nJunior singles: "
      + junSing + "\nSophomore doubles: " + sophDoub + "\nFirst year doubles: " +
      fyDoub + "\nResidents:";
    PriorityQueue<Student> newQueue = divideBlocks();
    
    while(!newQueue.isEmpty()){
      s += "\n" + newQueue.poll();
    }
    return s;

  }

  
  /* This method breaks up each block and places each student into a new PriorityQueue
   * where they will be sorted in the order of their lottery number
   */
  public PriorityQueue<Student> divideBlocks() {
    PriorityQueue<Student> newQueue = new PriorityQueue<Student>(size);
    LinkedList<Block> copy = (LinkedList<Block>)(residents.clone());
    for(int i = 0; i<copy.size(); i++) {
      Block b = copy.get(i);
      for(int j = 0; j<b.getSize(); j++) {
        LinkedList<Student> l = b.getStudents();
        Student s = l.get(j);
        newQueue.add(s);
      }
    }
    //System.out.println("The new queue is: " + newQueue);
    return newQueue;
  }
  
  /* main method for testing*/
  public static void main(String[] args) {
    Block b1 = new Block(new Student("Jun Sing",2017,"B28493759",374), 2016);
    b1.addStudent(new Student("Bob Builder",2017,"B28405938",72));
    b1.addStudent(new Student("Wendy Builder",2017,"B20495873",155));
    ResHall tower = new ResHall("Tower",5,5,5,5);
    System.out.println("A dorm named \"Tower\" is created.\n\tName of dorm (tower): " + 
                       tower.getResHallName() + "\n\tTotal number of rooms (20): " + 
                       tower.getTotalRooms() + "\n\tNumber of Senior Singles (5): " + 
                       tower.getSeniorSingles() + "\n\tNumber of Junior Singles (5): " + 
                       tower.getJuniorSingles() + "\n\tNumber of Sophomore doubles (5): " + 
                       tower.getSophomoreDoubles() + "\n\tNumber of First Year Doubles (5): " +
                       tower.getFYDoubles() + "\n\tEmpty Senior Singles (5): " + 
                       tower.getEmptySeniorSingles() + "\n\tEmpty Junior Singles (5): " + 
                       tower.getEmptyJuniorSingles() + "\n\tEmpty Sophomore doubles (5) : " + 
                       tower.getEmptySophomoreDoubles() + "\n\tEmpty First Year Doubles (5): " +
                       tower.getEmptyFYDoubles());

    System.out.println("\nGet residents of Tower (EMPTY): " + tower.getResidents());
    
    System.out.println("\nCan block 1 be added to tower (TRUE): " + tower.canAddBlock(b1));
    System.out.println("Block 1 added to tower (TRUE)?: " + tower.addBlock(b1));
    
    System.out.println("A block containing three juniors (Bob Builder, Jun Sing, and Wendy, "
                         + "average: 200.33) has been added to the dorm Tower, which "
                         + "contains 5 of each type of room.");
    System.out.println("Get residents of \"Tower\" (Bob, Jun Sing, Wendy):\n" +
                       tower.getResidents());

    System.out.println("\nThe number of empty junior singles in Tower is now (2): "
                         + tower.getEmptyJuniorSingles());
    
    System.out.println("\nPrinting out all the information stored in \"Tower\"\n"
                         + tower);
    
    System.out.println("\nPrinting out the information stored in \"Tower\" in our "
                         + "updated version:\n" + tower + "\n");
    
    System.out.println("\nCreating a block a combination of students from 3 class years " +
                       "(Alex, Mary, Sarah, and Sammy, average: 349).");
    /*
    Block b2 = new Block(new Student("Alex", "Senior","B20673848",78));
    b2.addStudent(new Student("Mary", "Junior","B29384930",154));
    b2.addStudent(new Student("Sarah", "Sophomore","B29483940",643,"B29304938"));
    b2.addStudent(new Student("Sammy", "Sophomore","B29304938",521,"B29483940"));
    System.out.println("Printing out block 2:\n" + b2 + "\n");
    System.out.println("This block can be added to Tower: (TRUE) " + tower.canAddBlock(b2));
    System.out.println("Add block 2 to Tower (TRUE): " + tower.addBlock(b2));
    System.out.println("\nPrinting out the information stored in Tower using print():" +
                       tower.print());
    System.out.println("\nGet residents again, should contain both block 1 and block 2: " +
                       tower.getResidents());
    System.out.println("\nPrint out the number of empty rooms in tower (5, 4, 1, 4): " + 
                       tower.getEmptyRooms());
                       */
  }
  
}