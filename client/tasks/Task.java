package tasks;

import java.util.*;

public abstract class Task {

  protected int id;
  public String headline;
  public String description;
  public Date createDate;
  public Date targetDate;
  public int priority;
  public int status;
  protected int createdByUserId;
  public int categoryId;
  protected int allocatedExecutiveId;
  protected int allocatedUserId;
  protected boolean isDeleted;

  public Task () {
    id = 0;
    priority = 0;
    status = 0;
    createdByUserId = 0;
    categoryId = 0;
    allocatedUserId = 0;
    allocatedExecutiveId = 0;
    headline = "";
    description = "";
    isDeleted = false;
  }

  public abstract void fetch( int id );
  public abstract int save();
  public abstract void update();
  public abstract void delete();
}