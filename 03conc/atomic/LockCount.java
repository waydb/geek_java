import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCount {

  private int num = 0;

  private Lock lock = new ReentrantLock(true);

  public int add() {
    try {
      lock.lock();
      return num++;
    } finally {
      lock.unlock();
    }
  }

  public int get() {
    return num;
  }
}
