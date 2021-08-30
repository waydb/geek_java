import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadMain {

  public static void main(String[] args) {

    ThreadA threadA = new ThreadA();
    threadA.start();

    System.out.println("Main thread");

    ThreadB threadB = new ThreadB();

//    threadB.run();// 会阻塞主线程
    new Thread(threadB).start();
    System.out.println("Main thread");

    ThreadC threadC = new ThreadC();
    FutureTask<String> futureTask = new FutureTask<>(threadC);
    new Thread(futureTask).start();

    System.out.println("Main thread begin:");
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      System.out.println("Get future: " + futureTask.get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

}
