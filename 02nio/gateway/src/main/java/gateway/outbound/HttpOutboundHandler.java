package gateway.outbound;

import gateway.router.HttpEndpointRouter;
import gateway.util.HttpClientUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpOutboundHandler {

  private HttpEndpointRouter router;

  public HttpOutboundHandler(HttpEndpointRouter router) {
    this.router = router;
  }

  public void handleRequest(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
    String backend = router.route();
    FullHttpResponse response = null;
    try {
      HttpHeaders headers = fullHttpRequest.headers();
      String body = HttpClientUtil.getAsString(backend + fullHttpRequest.uri(), headers);
      byte[] bytesArray = body.getBytes("UTF-8");
      response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(bytesArray));
      response.headers().set("Content-Type", "text/json");
      response.headers().setInt("Content-Length", bytesArray.length);
    } catch (Throwable e) {
      e.printStackTrace();
      response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
    } finally {
      if (fullHttpRequest != null) {
        if(null == response){
          response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        }
        if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
          ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
          ctx.write(response);
        }
      }
      ctx.flush();
    }
  }
}
