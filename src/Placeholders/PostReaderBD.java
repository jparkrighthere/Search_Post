import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class PostReaderBD implements PostReaderInterface {

  public PostReaderBD() {

  }

  @Override
  public List<PostInterface> readPostsFromFile(String filename) throws FileNotFoundException {
    // TODO Auto-generated method stub
    List<PostInterface> newList = new LinkedList<>();

    PostBD post1 = new PostBD("The Old Man and The Sea","www.google.com","It is about the old man and the sea");
    PostBD post2 = new PostBD("Sleeping Beauty","www.naver.com","sleeping beauty loved the old man");
    PostBD post3 = new PostBD("Toy Story","www.daum.net","It is the story about a man that loved toy");
    PostBD post4 = new PostBD("The Beauty and the Beast","www.google.com","They loved each other");
    PostBD post5 = new PostBD("The Great grandparents","www.naver.com","Life is boring");
    PostBD post6 = new PostBD("Dragon Treasure","www.daum.net","That is what you are");

    newList.add(post1);
    newList.add(post2);
    newList.add(post3);
    newList.add(post4);
    newList.add(post5);
    newList.add(post6);

    return newList;
  }
}