import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import tasks.*;

public class TaskIndexTest{
  TaskIndex ti;

  @Before
  public void before(){
    ti = new TaskIndex();

  }

  @Test
  public void retrieveIndexTest(){
    ti.fetch();
    System.out.println(ti.response);
  }

}