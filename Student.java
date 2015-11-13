/* FILE NAME: Student.java
 * 
 * Elizabeth Hau
 * Emily Cetlin
 * CS 230
 * Final Project - Wellesley College Housing Lottery
 * Date Created: 05/01/14
 * Last Modification: 02/03/15
 * 
 * The Student class creates a student by taking in the student's name, class year
 * and lottery number. We assume that the class year is a string that says either
 * "Senior", "Junior", "Sophomore", or "First Year". An additional boolean variable
 * has been added to keep track (or change) whether the student wants to stay in the 
 * same residence hall the following year. There are methods to get each
 * of these components of the student. The program assumes that the inputted 
 * information is correct and therefore the user should not be able to change any
 * information about the student besides whether the student is staying in the 
 * same residence hall.
 */


public class Student implements Comparable<Student>{
  
  private String name;  // Name of Student
  private int year;  // class year 
  private double lotteryNum;  // assigned lottery number
  private String bnumber; // bnumber is the assigned B-number of the student
  private boolean stay; // return to the same res hall?
  //private String roommate; // keeps track of B-number of roommate
  private String currentResHall; // student's current residence hall
  
  /* This constructor creates the student object, taking in four parameters: name,
   * class year, b-number, and lottery number. This method assumes that the entry 
   * for class year is 'Senior', 'Junior', 'Sophomore', or 'First Year'. 
   * It also has another property that keeps track of whether the student wants to 
   * return to the same residence hall for the following year or not.
   */
  public Student(String n, int y, String b, double l) {
    name = n;
    year = y;
    bnumber = b;
    lotteryNum = l;
    stay = false; // assumes the student doesn't want to return to same res hall
    //roommate = ""; // assumes the student doesn't have a roommate, ie an empty string
  }
  
  /* This second constructor creates a student object, taking in five parameters: name,
   * class year, b-number, lottery number, and whether the student wants to return to the 
   * same residence hall the following year. This constructor is useful when reading
   * in a file.
   */
  public Student(String n, int y, String b, double l, boolean s) {
    this(n,y,b,l);
    stay = s;
  }
  
  /* This third constructor creates a student object, taking in four parameters: name,
   * class year, lottery number, and whether the student has a roommate or not.
   * This constructor is useful when reading in a file.
   */
  /*
  public Student(String n, String y, String b, double l, String r) {
    this(n,y,b,l);
    //setRoommate(r);
  }
  */
  
  /* This fourth constructor creates a student object, taking in six parameters: name,
   * class year, b-number, lottery number, whether the student wants to return to the 
   * same residence hall the following year, and whether the student has a
   * roommate or not. This constructor is useful when reading in a file.
   */
  
  public Student(String n, int y, String b, double l, boolean s, String r) {
    this(n,y,b,l,s);
    setCurrentResHall(r);
  }
  
  
  /* The getName method returns the student's name.
   * @ returns String
   */
  public String getName() {
    return name;
  }
  
  /* The getLotteryNum method returns the student's lottery number.
   * @ returns double
   */
  public double getLotteryNum() {
    return lotteryNum;
  }
  
  /* The getYear method returns the student's class year
   * @ returns int
   */
  public int getYear() {
    return year;
  }
  
  /* The getBnumber method returns the student's B-number
   * @ return String
   */
  public String getBnumber() {
    return bnumber;
  }
  
  /* This method changes the lotteryNumber of a student
   * This is used when we are updating the lottery number of students 
   * who are in doubles so that they stay as a unit or when students enter an 
   * incorrect lottery number and needs to be overrided 
   */
  public void setLotteryNum(double n) {
    lotteryNum = n;
  }
  
  /* This updateLotteryNum method calculates the joint average of the pair
   * of students who are rooming together so that they stay together as a unit.
   * We do this by calculating their average so that they have the same lottery
   * number going into housing night.
   */
 /* public void updateLotteryNum(Student r) {
    double slot = this.getLotteryNum();
    double rlot = r.getLotteryNum(); // roommate's lottery number
    double avg = (slot + rlot)/2; // new average
    this.setLotteryNum(avg);
    r.setLotteryNum(avg);
  }
  */
  
  /* This method changes stay depending on whether the student wants to stay in the
   * same res hall the following or not.
   */
  public void stayResHall(boolean b) {
    stay = b;
  }
  
  /* This method returns true if the student wants to stay in the same res hall.
   * Otherwise it returns false
   * @ returns boolean
   */
  public boolean getStayResHall() {
    return stay;
  }
  
  /* This method changes roommate depending on whether the student is rooming with
   * someone (ie the two students will be in a double). The string is a B-number.
   * Method assumes that the B-number is entered correctly
   *
  public void setRoommate(String s) {
    roommate = s;
  }
  
  */
  
  /* This method returns the B-number of the student's roommate.
   * It reutnrs an empty string if the student does not have a roommate
   * @ returns String
   *
  public String getRoommate() {
    return roommate;
  }
  */
  
  /* This method sets the student's current residence hall by taking in the name of 
   * the student's current residence hall.
   */
  public void setCurrentResHall(String s) {
    currentResHall = s;
  }
  
  /* This method returns the student's current residence hall
   * @ return String
   */
  public String getCurrentResHall() {
    return currentResHall;
  }
  
  /* This method prints the studen's name, year, and lottery number. It separates the
   * three items with a space
   * @ returns String
   */
  public String toString() {
    return name + ", " + year + ", " + lotteryNum;
  }
  
  /* Implementation of the Comparable interface.
   * This method compares two students (this and s) by comparing their lottery numbers 
   * and returns 0 if two students have the same number, 1 if this has a greater 
   * average than s, and -1 if this has a smaller number than s
   * @ returns int
   */
  public int compareTo(Student s) {
    if(lotteryNum == s.getLotteryNum()) return 0;
    else if (lotteryNum > s.getLotteryNum()) return 1;
    else return -1;
  }
  
  /* main method for testing
   */
  public static void main(String[] args) {
    Student s = new Student("Jane Smith", 2018,"B28944777",1510);
    System.out.println("A new student, Jane Smith, has been created.\nJane is a "
                         + "First Year and her lottery number is 1510.");
    System.out.println("Jane's name is: (JANE SMITH) " + s.getName());
    System.out.println("Jane's class year is: (FIRST YEAR) " + s.getYear());
    System.out.println("Jane's lottery number is: (1510) " + s.getLotteryNum());
    System.out.println("Setting Jane's lottery number to 1509.");
    s.setLotteryNum(1509);
    System.out.println("Jane's lottery number is: (1509) " + s.getLotteryNum());
    System.out.println("Jane's B-number is : (B28944777) " + s.getBnumber());
    System.out.println("Jane's information:\n" + s + "\n");
    
    // testing constructor 3, takes in (n,y,b,lotNum,roommateB)
    Student s2 = new Student("John Smith",2017,"B29957385",1200, false);
    //System.out.println("Roommate for John (Expected B25753578): " + s2.getRoommate());
    System.out.println("John Smith Get stay res hall (FALSE): " 
                         + s2.getStayResHall());
    s2.stayResHall(true);
    System.out.println("Change whether John wants to stay (TRUE): " 
                         + s2.getStayResHall());
    System.out.println("Comparing Jane and John (1): " + s.compareTo(s2));
    System.out.println("Comparing John and Jane (-1): " + s2.compareTo(s) + "\n");
    
    Student s3 = new Student("Jenny Brown",2016,"B29486937",800, true, "Bates");
    System.out.println("(JENNY BROWN) " + s3.getName() + " is a (JUNIOR) " + s3.getYear() 
                         + " and her lottery number is (800) " + s3.getLotteryNum());
    System.out.println("Jenny Brown stay? (TRUE): " + s3.getStayResHall());
    System.out.println("Jenny Brown lives in (BATES): " + s3.getCurrentResHall());
    System.out.println("Changing Jenny's ResHall to Munger.");
    s3.setCurrentResHall("Munger");
    System.out.println("Jenny lives in (MUNGER): " + s3.getCurrentResHall());
  }
  
}