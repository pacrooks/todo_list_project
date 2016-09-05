package categories;

import java.util.*;

public abstract class Category {
  protected HashMap<Integer, String> index;

  public CategoryIndex() {
    index = new HashMap<Integer, String>();
  }

  public int length() {
    return index.size();
  }

  public abstract Category getCategory();
  public abstract void fetch();
  public abstract void expand(ArrayList<Task> tasks);
}