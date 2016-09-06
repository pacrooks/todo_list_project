import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import comms.*;
import categories.*;

public class CategoryTest{

  @Before
  public void before(){
  }

  @Test
  public void retrieveIndexTest(){
    Session.login("matt", "matt");
    CategoryIndex ci = new RemoteCategoryIndex();
    ci.fetch();
    assertEquals(3, ci.length());
    Session.logout();
  }

  @Test
  public void expandIndexTest1(){
    Session.login("matt", "matt");
    CategoryIndex ci = new RemoteCategoryIndex();
    ci.fetch();
    ci.expand();
    Category c = ci.getCategory(40);
    assertEquals(true, c != null);
    assertEquals("work", c.name);
    Session.logout();
  }

  @Test
  public void expandIndexTest2(){
    ArrayList<Category> categoryList = new ArrayList<Category>();
    Session.login("matt", "matt");
    CategoryIndex ci = new RemoteCategoryIndex();
    ci.fetch();
    ci.expand(categoryList);
    Category c = ci.getCategory(40);
    assertEquals(true, c != null);
    assertEquals("work", c.name);
    assertEquals(3, categoryList.size());
    Session.logout();
  }

}