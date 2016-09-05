public abstract class Category {
  protected int id;
  protected String name;
  protected String colour;

  public Category {
  }

  public abstract void fetch( int id );
  // public abstract int save();
  // public abstract void update();
  // public abstract void delete();
} 