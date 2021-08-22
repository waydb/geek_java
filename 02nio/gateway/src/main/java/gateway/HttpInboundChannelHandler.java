package gateway;

import gateway.util.HttpClientUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpInboundChannelHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
      handleRequest(fullHttpRequest, ctx);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      ReferenceCountUtil.release(msg);
    }
  }

  private void handleRequest(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
    String backend = "http://127.0.0.1:8801";
    FullHttpResponse response = null;
    try {
      HttpHeaders headers = fullHttpRequest.headers();
      String body = HttpClientUtil.getAsString(backend + fullHttpRequest.uri(), headers);
      byte[] bytesArray = body.getBytes("UTF-8");
      response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(bytesArray));
      response.headers().set("Content-Type", "application/json");
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
