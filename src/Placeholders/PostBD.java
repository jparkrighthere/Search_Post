//A placeholder class
public class PostBD implements PostInterface {
  
  private String title  ;
  private String url;
  private String body;
  
  public PostBD(String title, String url, String body) {
      this.title = title;
      this.url = url;
      this.body = body;
  }

  @Override
  public String getTitle() {
    
    return this.title;
  }

  @Override
  public String getUrl() {
    
    return this.url;
  }

  @Override
  public String getBody() {
    
    return this.body;
  }
  
}
