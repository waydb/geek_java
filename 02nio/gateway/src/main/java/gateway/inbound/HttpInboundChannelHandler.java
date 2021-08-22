package gateway.inbound;

import gateway.GatewayConfig;
import gateway.outbound.HttpOutboundHandler;
import gateway.router.RandomHttpEndpointRouter;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

public class HttpInboundChannelHandler extends ChannelInboundHandlerAdapter {

  private HttpOutboundHandler handler;

  public HttpInboundChannelHandler() {
    RandomHttpEndpointRouter router = new RandomHttpEndpointRouter(GatewayConfig.backends);
    this.handler = new HttpOutboundHandler(router);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
      handler.handleRequest(fullHttpRequest, ctx);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      ReferenceCountUtil.release(msg);
    }
  }

}
