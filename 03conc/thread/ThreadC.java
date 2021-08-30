import java.util.concurrent.Callable;

public class ThreadC implements Callable<String> {

  @Override
  public String call() throws Exception {
    Thread.sleep(1000);
    System.out.println("This is thread C");
    return "ThreadC";
  }
}
