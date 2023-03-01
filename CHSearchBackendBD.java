// --== CS400 Project One File Header ==--
// Name: Jeonghyeon Park
// CSL Username: jeonghyeon
// Email: jpark634@wisc.edu
// Lecture #: COMPSCI400: Programming III (001) SP23, TH @ 1:00PM
// Notes to Grader: none

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * This is CHSearchBackend class
 * @author park
 *
 */
public class CHSearchBackendBD implements CHSearchBackendInterface {

  //instance variables
  private PostReaderInterface postReader;
  private HashtableWithDuplicateKeysInterface<String, PostInterface> hashtable;

  //constructor
  public CHSearchBackendBD(HashtableWithDuplicateKeysInterface<String, PostInterface> hashtable ,PostReaderInterface postReader) {
    this.hashtable=hashtable;
    this.postReader=postReader;
  }


  /**
   * This is a method that loads data and put informations into hashtable
   */
  public void loadData(String filename) throws FileNotFoundException {
    List<PostInterface> datas = postReader.readPostsFromFile(filename);
    for (PostInterface data : datas)
      addPost(data);
  }

  /**
   * Private helper method that adds to the hashtable
   *
   */
  private void addPost(PostInterface post) {
    List<String> titleList = stripWord(post.getTitle());
    List<String> bodyList = stripWord(post.getBody());
    for (String word : titleList)
      hashtable.putOne("TITLE:" + word, post);
    for (String word : bodyList)
      hashtable.putOne("BODY:" + word, post);
  }

  /**
   * This private helper method strips out punctuation and converts to lower case
   * 
   * @param text contains words separated by spaces
   * @return list of lowercase words without punctuation
   */
  private List<String> stripWord(String text) {
    // strip punctuations
    text = text.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    String[] words = text.split(" ");
    return Arrays.asList(words);
  }

  /**
   * This method finds posts by words that are contained in title
   * @return a string list of posts founded
   */
  public List<String> findPostsByTitleWords(String words) {
    List<String> wordList = stripWord(words);
    List<String> postStrings = new ArrayList<>();
    findPostsHelper(wordList, postStrings, "TITLE:");
    return postStrings;
  }

  /**
   * This method finds posts by words that are contained in body
   * @return a string list of posts founded
   */
  public List<String> findPostsByBodyWords(String words) {
    List<String> wordList = stripWord(words);
    List<String> postStrings = new ArrayList<>();
    findPostsHelper(wordList, postStrings, "BODY:");
    return postStrings;
  }

  /**
   * This method finds posts by words that are contained in both title and body
   * @return a string list of posts founded
   */
  public List<String> findPostsByTitleOrBodyWords(String words) {
    List<String> wordList = stripWord(words);
    List<String> postStrings = new ArrayList<>();
    findPostsHelper(wordList, postStrings, "TITLE:");
    findPostsHelper(wordList, postStrings, "BODY:");
    return postStrings;
  }

  /**
   * Helper method that searches for posts, 
   * adds all such posts to a list, 
   * and then removes any duplicates from the list
   */
  private void findPostsHelper(List<String> words, List<String> postlist, String type) {
    for (String word : words) {
      try {
        List<PostInterface> datas = hashtable.get(type + word);
        // add posts into list
        for (PostInterface data : datas) {
          String str = data.getTitle() + " - " + data.getUrl() + " - "+ data.getBody();
          postlist.add(str);
        }
      } catch (NoSuchElementException e) {
      }
    }
    // sort string
    postlist.sort(null);
    // remove duplicates strings
    for (int i = 1; i < postlist.size(); i++)
      if (postlist.get(i).equals(postlist.get(i - 1)))
        postlist.remove(i--);
  }

  /**
   * This method returns the posts founded in a string using toString() method
   * @return the posts founded in a string using toString() method
   */
  public String getStatisticsString() {
    return hashtable.getSize() + " unique words\n" + hashtable.getNumberOfValues() +" total word-post pairs\n";
  }




}