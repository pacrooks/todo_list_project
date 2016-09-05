import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import comms.*;
import tasks.*;

public class TaskTest{
  TaskIndex ti;

  @Before
  public void before(){
    //ti = new RemoteTaskIndex();
  }

  @Test
  public void checkSessionId() {
    Session session = new Session();
    session.login("philc", "test");
    assertEquals(true, session.getSessionId() == null);
    assertEquals(false, session.isActive());
  }

  @Test
  public void loginAndLogout() {
    Session session = new Session();
    session.login("matt", "matt");
    assertEquals(false, session.getSessionId() == null);
    assertEquals(true, session.isActive());
    session.logout();
    assertEquals(true, session.getSessionId() == null);
    assertEquals(false, session.isActive());
  }

  @Test
  public void retrieveIndexTest(){
    // ti.fetch();
    // assertEquals(2, ti.length());
  }

  @Test
  public void expandIndexTest(){
    // ArrayList<Task> taskList = new ArrayList<Task>();
    // ti.fetch();
    // ti.expand(taskList);
    // assertEquals(2, taskList.size()); 
  }

}