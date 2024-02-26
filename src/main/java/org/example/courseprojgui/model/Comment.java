package org.example.courseprojgui.model;

import java.time.LocalDate;
import java.util.List;

public class Comment {
private int id;
private String commentTitle;
private String commentBody;
private LocalDate dateCreated;

private User commentOwner;
private List<Comment> replies;

private float rating;
}
