import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Homework {

  public static void test1() {
    Callable<Integer> task = new Callable<Integer>(){
      @Override
      public Integer call() throws Exception {
        Thread.sleep(1000);
        return 100;
      }
    };
    FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
    new Thread(futureTask).start();
    try {
      System.out.println(futureTask.get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

  public static void test2() {
    final int[] result = {0};
    Runnable task = new Runnable(){
      @Override
      public void run() {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        result[0] = 100;
      }
    };
    Thread t = new Thread(task);
    t.start();
    try {
      t.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(result[0]);
  }

  public static void test3() {
    final int[] result = {0};
    Thread t = new Thread(){
      @Override
      public void run() {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        result[0] = 100;
      }
    };
    t.start();
    try {
      t.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(result[0]);
  }

  public static void test4() {

    Work work = new Work();
    Thread thread = new WorkThread(work);
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(work.getData());
  }

  public static void main(String[] args) {
    test1();
    test2();
    test3();
    test4();
  }
}

class Work {
  public int getData() {
    return data;
  }

  private int data;
  public void process() {
     data = 100;
  }
}

class WorkThread extends Thread {
  private Work work;
  public WorkThread(Work work) {
    this.work = work;
  }
  public void run() {
    work.process();
  }
}