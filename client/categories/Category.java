package categories;

public abstract class Category {
  protected int id;
  protected String name;
  protected String colour;

  public Category() {
    id = 0;
    name = null;
    colour = null;
  }

  public boolean isEmpty() {
    return (name == null);
  }

  public abstract void fetch( int id );
  // public abstract int save();
  // public abstract void update();
  // public abstract void delete();
} 