package netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

public class HttpClientChannelInitializer extends ChannelInitializer {

  @Override
  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline p = ch.pipeline();
    // add handlers

    // 这里因为书写错误，卡了很久
    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
    // 也可以简写成HttpClientCodec
    p.addLast(new HttpResponseDecoder());
    p.addLast(new HttpRequestEncoder());
    //    p.addLast(new HttpClientCodec());

    p.addLast(new HttpClientHandler());
  }
}
