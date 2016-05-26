package com.madhouse.netty

import java.net.URI

import com.madhouse.netty.handler.ClientHandler
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.{ChannelInitializer, ChannelOption}
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.http.HttpHeaderNames._
import io.netty.handler.codec.http._

/**
  * Created by Jay on 16/5/23.
  */
class NettyClient(host:String, port:Int) {
  def start = {
    val eventLoopGroup = new NioEventLoopGroup
    try{
      val bootstrap =  new Bootstrap
      bootstrap.channel(classOf[NioSocketChannel])
      bootstrap.option(ChannelOption.SO_KEEPALIVE, boolean2Boolean(true))
      bootstrap.group(eventLoopGroup)
      bootstrap.remoteAddress(host,port)
      bootstrap.handler(new ChannelInitializer[SocketChannel]() {
        override def initChannel(ch: SocketChannel): Unit = {
          ch.pipeline().addLast(new HttpResponseDecoder())
          ch.pipeline().addLast(new HttpRequestEncoder())
          ch.pipeline().addLast(new ClientHandler())
        }
      })
      val f = bootstrap.connect(host, port).sync()
      val uri = new URI("http://localhost:9000")
      val msg = "Are you ok?"
      if(f.isSuccess) println("~~~~~~~~~~~~~~~~~~connect server success~~~~~~~~~~~~~~")
      val request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
        uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")))

      // 构建http请求
      request.headers().set("host", host)
      request.headers().set(CONNECTION, CONNECTION)
      request.headers().set(CONTENT_LENGTH,request.content().readableBytes().toString)
      // 发送http请求
      f.channel().write(request)
      f.channel().flush()
      f.channel().closeFuture().sync()

    } catch {
      case e:Exception => e.printStackTrace()
    }finally {
      eventLoopGroup.shutdownGracefully()
    }

  }

}
object  NettyClient{
  def main(args: Array[String]) {
    new NettyClient("localhost", 9000).start
  }
}