public class WaitAndNotify {

  public static void main(String[] args) {
    MethodClass methodClass = new MethodClass();

    Thread t = new Thread();

    Thread thread1 = new Thread(() -> {
      try {
        methodClass.produce();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "thread1");
    Thread thread2 = new Thread(() -> {
      try {
        methodClass.consume();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "thread2");

    thread1.start();
    thread2.start();

  }
}

class MethodClass {

  private final int MAX = 20;

  int productCount = 0;

  public synchronized void produce() throws InterruptedException {
    while (true) {
      System.out.println(Thread.currentThread().getName() + "== run ==" + productCount);
      Thread.sleep(10);
      if (productCount >= MAX) {
        System.out.println("full, stop producing");
        wait();
      } else {
        productCount++;
      }
      notifyAll();
    }
  }

  public synchronized void consume() throws InterruptedException {
    while (true) {
      System.out.println(Thread.currentThread().getName() + "== run ==" + productCount);
      Thread.sleep(10);
      if (productCount <= 0) {
        System.out.println("Empty, stop consuming");
        wait();
      } else {
        productCount--;
      }
      notifyAll();
    }
  }
}
