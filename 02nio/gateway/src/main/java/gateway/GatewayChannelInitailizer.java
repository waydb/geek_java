package gateway;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class GatewayChannelInitailizer  extends ChannelInitializer {

  @Override
  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline p = ch.pipeline();

    p.addLast(new HttpServerCodec());
    p.addLast(new HttpObjectAggregator(1024 * 1024));
    p.addLast(new HttpInboundChannelHandler());
  }

}
