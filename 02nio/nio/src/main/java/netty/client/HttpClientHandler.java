package netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpClientHandler  extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    System.out.println("msg -> " + msg.toString());
    if (msg instanceof HttpResponse) {
      HttpResponse response = (HttpResponse) msg;
      System.out.println("CONTENT_TYPE:"
          + response.headers().get(HttpHeaderNames.CONTENT_TYPE));
    } else if (msg instanceof HttpContent) {
      HttpContent httpContent = (HttpContent) msg;
      ByteBuf content = httpContent.content();

      String result = content.toString(CharsetUtil.UTF_8);
      System.out.println("response -> " + result);

      content.release();
      ctx.close();
    }
  }
}
