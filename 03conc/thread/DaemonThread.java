public class DaemonThread {

  public static void main(String[] args) {
    Runnable task = () -> {
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName());
    };
    Thread thread = new Thread(task);
    thread.setName("test-thread-1");
    thread.setDaemon(true);
    thread.start();
    try {
      thread.wait();
//      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
