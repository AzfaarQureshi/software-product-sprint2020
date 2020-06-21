package com.google.sps.data;


import java.util.ArrayList;
import java.util.List;

public class Comments {

  class Comment {
    private String userName;
    private String comment;
    public Comment(String userName, String comment) {
      this.userName = userName;
      this.comment = comment;
    }
  }
  private final List<Comment> commentlist = new ArrayList<Comment>();

  public void addComment(String userName, String comment) {
    this.commentlist.add(new Comment(userName, comment));
  }

  public List<Comment> getComments() {
    return this.commentlist;
  }
}
