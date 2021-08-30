public class UnsafeCount {
  private int num = 0;

  public int add() {
    return num++;
  }

  public int get() {
    return num;
  }
}
