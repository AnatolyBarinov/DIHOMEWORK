package org.example.model;


public class Post {
  private long id;
  private String content;
  private String title;

  public Post() {
  }

  public Post(long id, String content) {
    this.id = id;
    this.content = content;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
