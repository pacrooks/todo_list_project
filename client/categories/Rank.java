package categories;

public enum Priority {
  HIGH ("High", "Hi"),
  MEDIUM ("Medium", "Me"),
  LOW ("Low", "Lo");

  private final String text;
  private final String abbreviation;
  private static final int size = Priority.values().length;

  private Priority(String txt, String abbrev) {
    text = txt;
    abbreviation = abbrev;
  }

  public int getSize() {
    return size;
  }

  public String toString() {
    return text;
  }

  public String abbr() {
    return abbreviation;
  }
}
