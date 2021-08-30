
public class Runner1 implements Runnable{

  @Override
  public void run() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    for (int i = 0; i < 100; i++) {
//      System.out.println("Runner1 is in processing");
    }
  }
}
