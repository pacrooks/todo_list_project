package categories;

import java.util.*;

public abstract class CategoryIndex {
  protected HashMap<Integer, Category> index;

  public CategoryIndex() {
    index = new HashMap<Integer, Category>();
  }

  public int length() {
    return index.size();
  }

  public abstract Category getCategory(Integer i);
  public abstract void fetch();
  public abstract void expand();
  public abstract void expand(ArrayList<Category> tasks);
}