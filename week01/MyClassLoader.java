import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MyClassLoader extends ClassLoader{

  private String path;

  public MyClassLoader(String path) {
    super();
    this.path = path;
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {

    try {
      byte[] classByte = read(path);
      Class<?> c = this.defineClass(name, classByte, 0, classByte.length);
      return c;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return super.findClass(name);
  }

  private byte[] read(String path) throws IOException {
    File file = new File(path);
    file.length();
    FileInputStream fis = new FileInputStream(file);
    DataInputStream dis = new DataInputStream(fis);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    for (int i = 0; i < file.length(); i ++) {
      baos.write(255 - dis.read());
    }
    return  baos.toByteArray();
  }

  public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
    MyClassLoader myClassLoader = new MyClassLoader("01/Hello.xlass");
    Class cls = Class.forName("Hello", true, myClassLoader);
//    Class cls = myClassLoader.loadClass("Hello");
    System.out.println(cls.getClassLoader());
    Object obj = cls.newInstance();

    for (Method method: cls.getDeclaredMethods()) {
      System.out.println(method.getName());
      System.out.println(method.getParameterCount());
    }

    Method m = cls.getDeclaredMethod("hello");
    m.invoke(obj);
  }
}
