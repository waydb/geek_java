import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class LongDemo {

  public static void main(String[] args) {

    final AtomicLong atomicLong = new AtomicLong();
    final LongAdder longAdder = new LongAdder();
    List<Thread> list = new ArrayList<>();
    long start = System.currentTimeMillis();
    for (int i = 0; i < 1000; i++) {
      Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
          for (int j = 0; j < 10000; j++) {
            atomicLong.getAndIncrement();
//            longAdder.increment();
          }
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


    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("atomicLong=" + atomicLong.get() + " duration=" + duration);
    System.out.println("longAdder =" + longAdder.sum() + " duration=" + duration);

  }
}
