import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class AtomicMain {

  public static void main(String[] args) {
    final LockCount count = new LockCount();
    long start = System.currentTimeMillis();
    List<Thread> list = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      Thread t = new Thread(() -> {
        for (int j = 0; j < 10000; j++) {
          count.add();
        }
      });
      list.add(t);
      t.start();
    }

    for (Thread t: list) {
      try {
        t.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    long duration = System.currentTimeMillis() - start;
//    try {
//      Thread.sleep(10000L);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }

    System.out.println("num=" + count.get() + " duration: " + duration);
  }
}
