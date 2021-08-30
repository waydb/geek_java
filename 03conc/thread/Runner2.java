
public class Runner2 implements Runnable{


  @Override
  public void run() {
//    try {
//      Thread.sleep(1000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
    for (int i = 0; i < 100; i++) {
      System.out.println("Runner2 is in processing" + i);
    }

    boolean result1 = Thread.currentThread().isInterrupted();

    boolean result2 = Thread.interrupted();

    boolean result3 = Thread.currentThread().isInterrupted();

    System.out.println("Runner2.run result1 ===> " + result1);
    System.out.println("Runner2.run result2 ===> " + result2);
    System.out.println("Runner2.run result3 ===> " + result3);
  }
}
