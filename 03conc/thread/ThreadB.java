
public class ThreadB implements Runnable {


  @Override
  public void run() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("This is thread B");

    Thread currentThread = Thread.currentThread();
    String currentThreadName = currentThread.getName();

    System.out.println("Thread name: " + currentThreadName);
    System.out.println("Thread: " + currentThreadName + "'s thread group's active thread num: " + Thread.currentThread().getThreadGroup().activeCount());
    System.out.println("Thread: " + currentThreadName + " id: " + currentThread.getId());
    System.out.println("Thread: " + currentThreadName + " priority: " + currentThread.getPriority());
    System.out.println("Thread: " + currentThreadName + " state: " + currentThread.getState());
    System.out.println("Thread: " + currentThreadName + " thread group: " + currentThread.getThreadGroup());
    System.out.println("Thread: " + currentThreadName + " is active: " + currentThread.isAlive());
    System.out.println("Thread: " + currentThreadName + " is deamon: " + currentThread.isDaemon());
  }
}
