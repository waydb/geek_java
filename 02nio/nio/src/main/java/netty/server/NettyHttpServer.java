package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.server.HttpServerChannelInitializer;

public class NettyHttpServer {
  public static void main(String[] args) throws InterruptedException {

    int port = 8808;

    // 主从reactor模型 的主selector
    EventLoopGroup bossGroup = new NioEventLoopGroup(2);
    // 主从reactor模型 的从selector
    EventLoopGroup workerGroup = new NioEventLoopGroup(16);

    try {
      // 引导
      ServerBootstrap b = new ServerBootstrap();

      // 设置 server 将应用于channel的设置
      // option 参数适用于新创建的ServerChannel的ChannelConfig，即监听并接受客户端连接的服务器套接字。当调用bind()或connect()方法时，将在服务器 channel 上设置这些选项
      // childOption 应用于 channel 的channelConfig，一旦serverChannel接受客户端连接，该 channel 便会创建。该 channel 是每个客户端(或每个客户端套接字)的。
      b.option(ChannelOption.SO_BACKLOG, 128)
          .childOption(ChannelOption.TCP_NODELAY, true)
          .childOption(ChannelOption.SO_KEEPALIVE, true)
          .childOption(ChannelOption.SO_REUSEADDR, true)
          .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
          .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
          .childOption(EpollChannelOption.SO_REUSEPORT, true);

      b.group(bossGroup, workerGroup)      // 设置eventloopGroup
          .channel(NioServerSocketChannel.class) // 设置channel类型
          .handler(new LoggingHandler(LogLevel.INFO)) // 设置handler handler/childHandler的区别和option/childOption的区别类似
          .childHandler(new HttpServerChannelInitializer());

      Channel ch = b.bind(port).sync().channel();
      System.out.println("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
      ch.closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }
}