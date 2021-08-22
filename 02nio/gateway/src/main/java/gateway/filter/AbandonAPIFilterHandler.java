package gateway.filter;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

import java.util.Arrays;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

// 拦截某些黑名单url的访问，对这些url的访问返回禁用信息
public class AbandonAPIFilterHandler extends ChannelInboundHandlerAdapter {

  private final static String[] BLACK_LIST_PATHS = {"/hello", "/admin"};

  private final static String FORBID_NOTICE = "Abandoned API";

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    FullHttpResponse response = null;
    FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
    try {
      String uri = fullHttpRequest.uri();
      System.out.println("AbandonAPIFilterHandler receive request: " + uri);
      if (Arrays.stream(BLACK_LIST_PATHS).anyMatch(e -> uri.startsWith(e))) {
        byte[] bytesArray = FORBID_NOTICE.getBytes("UTF-8");
        response = new DefaultFullHttpResponse(HTTP_1_1, METHOD_NOT_ALLOWED, Unpooled.wrappedBuffer(bytesArray));
        response.headers().set("Content-Type", "plain/text");
        response.headers().setInt("Content-Length", bytesArray.length);
      } else {
        ctx.fireChannelRead(msg);
      }
    } finally {
      if (fullHttpRequest != null && response != null) {
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
