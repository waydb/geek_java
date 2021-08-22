package gateway;

import java.util.ArrayList;
import java.util.List;

public class GatewayConfig {

  public static List<String> backends = new ArrayList<>();

  static {
    backends.add("http://127.0.0.1:8801");
  }
}
