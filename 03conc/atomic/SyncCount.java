
public class SyncCount {

  private int num = 0;

  public int add() {
    synchronized (this) {
      return num++;
    }
  }

  public int get() {
    return num;
  }
}
