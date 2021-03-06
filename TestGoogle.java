import java.util.*;
import java.io.*;


/* TestGoogle generates the csv file [that would be generated by google form] for testing --> used in Lottery22015
 * Last modified: 02/22/15
 */
public class TestGoogle {
  private final int MAX_BLOCK = 4;
  private final int MAX_RESHALL = 14;
  
  private String s;
  
  public TestGoogle (String inFileName) {
    
    s = "";
    try{
      Scanner scan = new Scanner(new File(inFileName));
      
      String line = scan.nextLine();
      s += line + "\n";
      
      while(scan.hasNextLine()) {
        int num = (int)(Math.random()*MAX_BLOCK+1); // random number for # of ppl in block
        
        String [] splitLine;
        
        s += ",," + num + ",";
        if (num == 2)
          s+=",,,,,,,";
        if (num == 3)
          s+=",,,,,,,,,,,,,,,,,,,,,";
        if (num == 4)
          s+=",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
        
        for(int i = 0; i < num; i++) {
          
          boolean stay = (Math.random() < 0.5); // generate random boolean for stay
          String staying = "No";
          if(stay) staying = "Yes";
          
          line = scan.nextLine();
          
          splitLine = line.split(",");
          String username = splitLine[0];
          String name = splitLine[2] + " " + splitLine[1];
          String bNum = splitLine[4];
          int classyear = Integer.parseInt(splitLine[3]);
          String res = splitLine[5]; // current reshall
          int lot = Integer.parseInt(splitLine[6]);
          
          s = s + username + ","  + name + "," + bNum + "," + classyear + ","+ lot + 
            "," + res + "," + staying + ","; 
          
          
        }
        s+="\n";
      }
      
      
    } catch (FileNotFoundException e) {
      System.out.println(e);
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
     TestGoogle p1 = new TestGoogle("testResultsWithNumbers100.csv");
     System.out.println(p1);
     p1.writer("testResultsForLottery100.csv");
     
     TestGoogle p2 = new TestGoogle("testResultsWithNumbers200.csv");
     System.out.println(p2);
     p2.writer("testResultsForLottery200.csv");
     
     TestGoogle p3 = new TestGoogle("testResultsWithNumbers250.csv");
     System.out.println(p3);
     p3.writer("testResultsForLottery250.csv");
     
     TestGoogle p4 = new TestGoogle("testResultsWithNumbers500.csv");
     System.out.println(p4);
     p4.writer("testResultsForLottery500.csv");
     
    TestGoogle p5 = new TestGoogle("testResultsWithNumbers1000.csv");
    System.out.println(p5);
    p5.writer("testResultsForLottery1000.csv");
    */
    TestGoogle p6 = new TestGoogle("testResultsWithNumbers2000.csv");
    System.out.println(p6);
    p6.writer("testResultsForLottery2000.csv");
    
  }
  
  
  
}