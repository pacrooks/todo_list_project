import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import comms.*;
import tasks.*;

public class TaskTest{
  TaskIndex ti;
  Session session;

  @Before
  public void before(){
    session = new Session("philc", "test");
    ti = new RemoteTaskIndex();
  }

  @Test
  public void checkSessionId() {
    assertEquals(false, session.getSessionId().isEmpty());
  }

  @Test
  public void retrieveIndexTest(){
    ti.fetch();
    assertEquals(2, ti.length());
  }

  @Test
  public void expandIndexTest(){
    ArrayList<Task> taskList = new ArrayList<Task>();
    ti.fetch();
    ti.expand(taskList);
    assertEquals(2, taskList.size()); 
  }

}