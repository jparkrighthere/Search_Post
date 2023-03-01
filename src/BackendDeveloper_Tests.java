// --== CS400 Project One File Header ==--
// Name: Jeonghyeon Park
// CSL Username: jeonghyeon
// Email: jpark634@wisc.edu
// Lecture #: COMPSCI400: Programming III (001) SP23, TH @ 1:00PM
// Notes to Grader: none

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;


public class BackendDeveloper_Tests {

  //Test if findPostsByTitleWords() method works properly
  public static boolean test1() {

    //create objects
    HashtableWithDuplicateKeysBD <String, PostInterface> hashtable = new HashtableWithDuplicateKeysBD();
    PostReaderBD postReader = new PostReaderBD();
    CHSearchBackendBD backend = new CHSearchBackendBD(hashtable,postReader);

    //load data
    try {
      backend.loadData("file.txt");
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return false;
    }

    //post by title words
    List <String> postTitle = backend.findPostsByTitleWords("the");

    if (!postTitle.get(0).equals("TITLE: The Old Man and The Sea\n"
        + "BODY: It is about the old man and the sea\n"
        + "URL: www.google.com\n")) {
      return false;
    }
    if (!postTitle.get(1).equals("TITLE: The Beauty and the Beast\n"
        + "BODY: They loved each other\n"
        + "URL: www.google.com\n")) {
      return false;
    }
    if (!postTitle.get(2).equals("TITLE: The Great grandparents\n"
        + "BODY: Life is boring\n"
        + "URL: www.naver.com\n")) {
      return false;
    }

    return true;
  }

  //Test if findPostsByBodyWords() method works properly
  public static boolean test2() {

    HashtableWithDuplicateKeysBD <String, PostInterface> hashtable = new HashtableWithDuplicateKeysBD();
    PostReaderBD postReader = new PostReaderBD();
    CHSearchBackendBD backend = new CHSearchBackendBD(hashtable,postReader);

    try {
      backend.loadData("file.txt");
    } 
    catch (FileNotFoundException e) {
      e.printStackTrace();

      return false;
    }

    //post by body words
    List <String> postBody = backend.findPostsByBodyWords("love");

    if (!postBody.get(0).equals("TITLE: Sleeping Beauty\n"
        + "BODY: sleeping beauty loved the old man\n"
        + "URL: www.naver.com\n")) {
      return false;
    }

    if (!postBody.get(1).equals("TITLE: Toy Story\n"
        + "BODY: It is the story about a man that loved toy\n"
        + "URL: www.daum.net\n")) {
      return false;
    }

    if (!postBody.get(2).equals("TITLE: The Beauty and the Beast\n"
        + "BODY: They loved each other\n"
        + "URL: www.google.com\n")) {
      return false;
    }

    return true;
  }

  //Test if findPostsByTitleOrBodyWords() method works properly
  public static boolean test3() {

    HashtableWithDuplicateKeysBD <String, PostInterface> hashtable = new HashtableWithDuplicateKeysBD();
    PostReaderBD postReader = new PostReaderBD();
    CHSearchBackendBD backend = new CHSearchBackendBD(hashtable,postReader);

    try {
      backend.loadData("file.txt");
    } 
    catch (FileNotFoundException e) {
      e.printStackTrace();

      return false;
    }

    //post by both title and body words
    List <String> postBoth = backend.findPostsByTitleOrBodyWords("man");

    if (!postBoth.get(0).equals("TITLE: The Old Man and The Sea\n"
        + "BODY: It is about the old man and the sea\n"
        + "URL: www.google.com\n")){
      return false;
    }

    return true;

  }

  //Test if getStatisticsString() method works properly
  public static boolean test4() {

    HashtableWithDuplicateKeysBD <String, PostInterface> hashtable = new HashtableWithDuplicateKeysBD();
    PostReaderBD postReader = new PostReaderBD();
    CHSearchBackendBD backend = new CHSearchBackendBD(hashtable,postReader);

    try {
      backend.loadData("file.txt");
    } 
    catch (FileNotFoundException e) {
      e.printStackTrace();

      return false;
    }

    //for the purpose of loading posts
    List <String> postTitle = backend.findPostsByTitleWords("the");

    if (!backend.getStatisticsString().equals( "POST FOUND: \n" 
        + "[TITLE: The Old Man and The Sea\n"
        + "BODY: It is about the old man and the sea\n"
        + "URL: www.google.com\n"
        + ", TITLE: The Beauty and the Beast\n"
        + "BODY: They loved each other\n"
        + "URL: www.google.com\n"
        + ", TITLE: The Great grandparents\n"
        + "BODY: Life is boring\n"
        + "URL: www.naver.com\n"
        + "]")){
      return false;
    }

    return true; 
  }

  //Test if it throws FileNotFoundException when the filename is null
  public static boolean test5() {

    HashtableWithDuplicateKeysBD <String, PostInterface> hashtable = new HashtableWithDuplicateKeysBD();
    PostReaderBD postReader = new PostReaderBD();
    CHSearchBackendBD backend = new CHSearchBackendBD(hashtable,postReader);
    try {
      backend.loadData(null);
    } catch (FileNotFoundException e) {//<------------HERE

      return true;//if it throws the exception, return true
    }

    return false;
  }

  //tests load data of front end role, false if malfunction
  public static boolean test6() {
    PostReaderInterface reader = new PostReaderDW();
    HashtableWithDuplicateKeysInterface<String, PostInterface> hashT = new HashtableWithDuplicateKeysAE<String, PostInterface>();

    CHSearchBackendBD backend =new CHSearchBackendBD(hashT,reader);
    TextUITester tester = new TextUITester("L\ndata/large.txt\nQ\n");
    try (Scanner scan = new Scanner(System.in)) {
      CHSearchFrontendFD frontTest1 = new CHSearchFrontendFD(scan, backend);
      frontTest1.runCommandLoop();
      String output = tester.checkOutput();
      if (!output.contains("Choose a command from the list below:") && 
          !output.contains("    [L]oad data from file") &&
          !output.contains("    Search Post [T]itles") &&
          !output.contains("    Search Post [B]odies") &&
          !output.contains("    Search [P]ost titles and bodies") &&
          !output.contains("    Display [S]tatistics for dataset") &&
          !output.contains("    [Q]uit") &&
          !output.contains("Enter the name of the file to load: ")&&
          !output.contains("Thank you for using the Cheap and Healthy Search App.")) {
        return false;
      } 
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  //tests load data of front end role, false if malfunction
  public static boolean test7() {
    PostReaderInterface reader = new PostReaderDW();
    HashtableWithDuplicateKeysInterface<String, PostInterface> hashT = new HashtableWithDuplicateKeysAE<String, PostInterface>();
    CHSearchBackendBD backend =new CHSearchBackendBD(hashT,reader);
    TextUITester tester = new TextUITester("L\n1.txt\nQ\n");
    try (Scanner scan = new Scanner(System.in)) {
      CHSearchFrontendFD frontTest3 = new CHSearchFrontendFD(scan, backend);
      frontTest3.runCommandLoop();
      String output = tester.checkOutput();
      if (!output.contains("Choose a command from the list below:") && 
          !output.contains("    [L]oad data from file") &&
          !output.contains("    Search Post [T]itles") &&
          !output.contains("    Search Post [B]odies") &&
          !output.contains("    Search [P]ost titles and bodies") &&
          !output.contains("    Display [S]tatistics for dataset") &&
          !output.contains("    [Q]uit")&&
          !output.contains("Enter the name of the file to load: ") &&
          !output.contains("Error: Could not find or load file: 1.txt")&&
          !output.contains("Thank you for using the Cheap and Healthy Search App.")) {
        return false;
      }
    }
    catch (Exception e) {
      return false;
    }
    return true;
  }
  
//tests integration is well functioning. testing find title
  public static boolean test8() {
    PostReaderInterface reader = new PostReaderDW();
    HashtableWithDuplicateKeysInterface<String, PostInterface> hashT = new HashtableWithDuplicateKeysAE<String, PostInterface>();
    CHSearchBackendBD back = new CHSearchBackendBD(hashT,reader);
    try {
      back.loadData("data/fake.txt");
      back.loadData("data/large.txt");
      back.loadData("data/small.txt");

    }
    catch(FileNotFoundException e){
      e.printStackTrace();
      return false;
    }
    TextUITester uiTester = new TextUITester("T\nQuick\nN\nQ\n");
    Scanner scan = new Scanner(System.in);

    CHSearchFrontendFD front = new CHSearchFrontendFD(scan, back);
//    front.runCommandLoop();
    String systemOutput = uiTester.checkOutput();

    if(!systemOutput.contains("Quick, easy, high calorie meals?!")
        &&!systemOutput.contains("https://www.reddit.com/r/EatCheapAndHealthy/comments/zofez7/quick_easy_high_calorie_meals/")
        &&!systemOutput.contains("I'm going through a hard time of struggling to eat & prep good meals.")
        &&!systemOutput.contains("Title with \"[Quick]\" being searched.")
        &&!systemOutput.contains("Choose the option\nL - Load data from file\n")
        &&!systemOutput.contains("T - Search Post Titles\nB-Search Post Bodies\n")
        &&!systemOutput.contains("P - Search Post titles and bodies\n")
        &&!systemOutput.contains("S - Display Statistics for dataset\nQ - Quit")) {
    }
    return true;
  }

  //tests integration is well functioning. testing find body
  public static boolean test9(){
    PostReaderInterface reader = new PostReaderDW();

    HashtableWithDuplicateKeysInterface<String, PostInterface> hashT = new HashtableWithDuplicateKeysAE<String, PostInterface>();
    CHSearchBackendBD back = new CHSearchBackendBD(hashT,reader);
    try {
      back.loadData("data/fake.txt");
      back.loadData("data/large.txt");
      back.loadData("data/small.txt");

    }
    catch(FileNotFoundException e){
      e.printStackTrace();
      return false;
    }
    TextUITester uiTester = new TextUITester("B\npouched\nN\nQ\n");
    Scanner scan = new Scanner(System.in);

    CHSearchFrontendFD front = new CHSearchFrontendFD(scan, back);
//    front.runCommandLoop();
    String systemOutput = uiTester.checkOutput();

    if(!systemOutput.contains("Is there any good way to compare canned and pouched tuna for price?")
        &&!systemOutput.contains("https://www.reddit.com/r/EatCheapAndHealthy/comments/zod0s8/is_tuna_in_the_can_or_pouch_generally_cheaper/")
        &&!systemOutput.contains("Usually I buy the cheapest tuna I can")
        &&!systemOutput.contains("Body with \"[pouched]\" being searched.")
        &&!systemOutput.contains("Choose the option\nL - Load data from file\n")
        &&!systemOutput.contains("T - Search Post Titles\nB-Search Post Bodies\n")
        &&!systemOutput.contains("P - Search Post titles and bodies\n")
        &&!systemOutput.contains("S - Display Statistics for dataset\nQ - Quit")) {
    }
    return true;    
  }   

  public static void main(String[] args) {

    System.out.println("Individual Test 1 passed: " + test1());
    System.out.println("Individual Test 2 passed: " + test2());
    System.out.println("Individual Test 3 passed: " + test3());
    System.out.println("Individual Test 4 passed: " + test4());
    System.out.println("Individual Test 5 passed: " + test5());
    System.out.println("FrontEnd Test 1 passed: " + test6());
    System.out.println("FrontEnd Test 2 passed: " + test7());
    System.out.println("Integration Test 1 passed: " + test8());
    System.out.println("Integration Test 2 passed: " + test9());

  }

}
