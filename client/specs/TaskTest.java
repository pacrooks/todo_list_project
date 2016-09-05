import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import comms.*;
import tasks.*;

public class TaskTest{
  TaskIndex ti;

  @Before
  public void before(){
  }

  @Test
  public void checkSessionId() {
    Session.login("philc", "test");
    assertEquals(true, Session.getSessionId() == null);
    assertEquals(false, Session.isActive());
  }

  @Test
  public void loginAndLogout() {
    Session.login("matt", "matt");
    assertEquals(false, Session.getSessionId() == null);
    assertEquals(true, Session.isActive());
    Session.logout();
    assertEquals(true, Session.getSessionId() == null);
    assertEquals(false, Session.isActive());
  }

  @Test
  public void retrieveIndexTest(){
    Session.login("matt", "matt");
    TaskIndex ti = new RemoteTaskIndex();
    ti.fetch();
    assertEquals(1, ti.length());
    Session.logout();
  }

  @Test
  public void expandIndexTest(){
    // ArrayList<Task> taskList = new ArrayList<Task>();
    // ti.fetch();
    // ti.expand(taskList);
    // assertEquals(2, taskList.size()); 
  }

}