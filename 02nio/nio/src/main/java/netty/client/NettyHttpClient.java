package netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.net.URI;
import java.net.URISyntaxException;

public class NettyHttpClient {

  public static void main(String[] args) {
    EventLoopGroup group = new NioEventLoopGroup();

    try {
      Bootstrap clientStrap = new Bootstrap();

      clientStrap.group(group);

      clientStrap.channel(NioSocketChannel.class);

      clientStrap.option(ChannelOption.SO_KEEPALIVE, true);

      clientStrap.handler(new HttpClientChannelInitializer());


      ChannelFuture cf = clientStrap.connect("127.0.0.1", 8808).sync();
      URI uri = new URI("/test");
      DefaultFullHttpRequest request = new DefaultFullHttpRequest(
          HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());

//      request.headers().set(HttpHeaderNames.CONNECTION,
//          HttpHeaderNames.CONNECTION);
//      request.headers().set(HttpHeaderNames.CONTENT_LENGTH,
//          request.content().readableBytes());

      // 发送http请求
      cf.channel().write(request);
      cf.channel().flush();
      cf.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } finally {
      group.shutdownGracefully();
    }
  }
}
