import java.util.*;
import java.io.*;

/**
 * Elizabeth Hau & Emily Cetlin
 * Test file created 02/22/15
 * Last modified (Cleaned up code): 11/12/15
 */

public class TestProgramForNumbers {
  private final int MAX_BLOCK = 4;
  private final int MAX_RESHALL = 14;
  private String s;
  
  public TestProgramForNumbers(int n) {
    s = "Username,Last name, First name,Class year,B number,Current Residence Hall,Lottery Number\n";

    String [] reshalls = new String [] {"Beebe","Cazenove","Munger","Pomeroy","Shafer", "Claflin",
      "Lake House","Severance","Tower","Bates","Freeman","McAfee","Dower","Stone Davis"};
    
    while(n>0) {
      String username = "wwellesley" + n;
      String lastname = "Wellesley" + n;
      String firstname = "Wendy";
      int year = (int)(Math.random()*MAX_BLOCK-1) + 2016;
      String bnum = "B20679" + n;
      int hall = (int)(Math.random()*MAX_RESHALL);
      String reshall = reshalls[hall];
      
      s = s + username + "," + lastname + "," + firstname + ","
        + year + "," + bnum + "," + reshall + "\n"; 
      
      n--;
    }
  }
  
  public void writer(String outFileName) {
    try{
      PrintWriter writer = new PrintWriter(new File(outFileName));
      writer.write(toString());
      writer.close();
    } catch(FileNotFoundException e){
      System.out.println(e);
    }
  }
  
  public String toString() {
    return s; 
  }
  
  public static void main(String [] args) {
    /*
    TestProgramForNumbers p1 = new TestProgramForNumbers(100);
    System.out.println(p1);
    p1.writer("testResultForNumbers100.csv");
    
    TestProgramForNumbers p2 = new TestProgramForNumbers(200);
    p2.writer("testResultForNumbers200.csv");
    
    TestProgramForNumbers p3 = new TestProgramForNumbers(250);
    p3.writer("testResultForNumbers250.csv");
    
    TestProgramForNumbers p4 = new TestProgramForNumbers(500);
    p4.writer("testResultForNumbers500.csv");
    
    TestProgramForNumbers p5 = new TestProgramForNumbers(1000);
    p5.writer("testResultForNumbers1000.csv");
    */
    TestProgramForNumbers p6 = new TestProgramForNumbers(2000);
    p6.writer("testResultForNumbers2000.csv");
    
  }
  
  
  
}