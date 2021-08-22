
public class ByteCodeTest {

  private int data;

  public static void main(String[] args) {
    ByteCodeTest h = new ByteCodeTest();
    int sum = 0;
    for (int i = 0; i < 10; i++) {
      if (i < 3) {
        sum = sum + i;
      }
    }
    System.out.println(sum);
    System.out.println(h.data);
  }
}