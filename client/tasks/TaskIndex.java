package tasks;

import java.util.*;

public abstract class TaskIndex {
  protected int[] indices;
  protected String ordering;
  protected boolean reverseOrder;

  public TaskIndex() {
    indices = null;
    ordering = "";
    reverseOrder = false;
  }

  public void setOrdering(String ordering) {
    this.ordering = ordering;
  }

  public void setReverseOrder(boolean reverse) {
    reverseOrder = reverse;
  }

  public int length() {
    if (indices == null) return 0;
    return indices.length;
  }

  public int getIndex(int i) {
    if ((indices == null) || (i < 0) || (i >= indices.length)) return 0;
    return indices[i];
  }

  public abstract void fetch();
  public abstract void expand(ArrayList<Task> tasks);

}