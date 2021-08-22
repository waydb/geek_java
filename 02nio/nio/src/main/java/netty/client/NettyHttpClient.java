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

  private EventLoopGroup group;
  private Bootstrap clientStrap;

  public NettyHttpClient() {
    this.group = new NioEventLoopGroup();

    this.clientStrap = new Bootstrap();

    clientStrap.group(group);

    clientStrap.channel(NioSocketChannel.class);

    clientStrap.option(ChannelOption.SO_KEEPALIVE, true);

    clientStrap.handler(new HttpClientChannelInitializer());
  }

  public void sendGet(String host, int port, URI uri) {
    try {
      ChannelFuture cf = clientStrap.connect(host, port).sync();
      DefaultFullHttpRequest request = new DefaultFullHttpRequest(
          HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
      cf.channel().write(request);
      cf.channel().flush();
      cf.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void close() {
    group.shutdownGracefully();
  }

  public static void main(String[] args) {
    NettyHttpClient client = new NettyHttpClient();
    try {
      client.sendGet("127.0.0.1", 8808, new URI("/test"));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } finally {
      client.close();
    }
  }

}
