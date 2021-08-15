package netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpHandler extends ChannelInboundHandlerAdapter {

  /**
   * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward
   * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
      String uri = fullHttpRequest.uri();

      if (uri.contains("/test")) {
        handlerTest(fullHttpRequest, ctx, "hello, man!");
      } else {
        handlerTest(fullHttpRequest, ctx, "hello, others!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      ReferenceCountUtil.release(msg);
    }
  }

  /**
   * Calls {@link ChannelHandlerContext#fireChannelReadComplete()} to forward
   * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  /**
   * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
   * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
   *
   * Sub-classes may override this method to change behavior.
   */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
      throws Exception {
    cause.printStackTrace();
    ctx.close();
  }

  private void handlerTest(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx, String body) {
    FullHttpResponse response = null;

    try {
      String value = body;

      response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
      response.headers().set("Content-Type", "application/json");
      response.headers().setInt("Content-Length", response.content().readableBytes());

    } catch (Exception e) {
      System.out.println("处理出错:" + e.getMessage());
      response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
    } finally {
      if (fullHttpRequest != null) {
        if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
          ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
          response.headers().set(CONNECTION, KEEP_ALIVE);
          ctx.write(response);
        }
        ctx.flush();
      }
    }
  }
}
