package com.madhouse.netty.handler

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.{HttpObjectAggregator, HttpRequestDecoder, HttpResponseEncoder, HttpServerCodec}

/**
  * Created by Jay on 16/5/23.
  */
class ChildChannelHandler extends ChannelInitializer[SocketChannel] {
  override def initChannel(ch: SocketChannel): Unit = {

    ch.pipeline().addLast("decode",new HttpRequestDecoder())
                  .addLast("servercodec",new HttpServerCodec())
                  .addLast("aggegator",new HttpObjectAggregator(65536))
                  .addLast(new HttpResponseEncoder())
                  .addLast(new ServerHandler)

  }
}
