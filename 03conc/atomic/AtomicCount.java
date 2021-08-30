import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCount {

  private AtomicInteger num  = new AtomicInteger();

  public int add() {
    return num.getAndIncrement();
  }

  public int get() {
    return num.get();
  }
}
