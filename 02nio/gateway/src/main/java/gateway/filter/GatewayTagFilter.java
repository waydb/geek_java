package gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class GatewayTagFilter implements HttpRequestFilter {

  @Override
  public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
    fullRequest.headers().set("tag", "API-GATEWAY");
  }
}
