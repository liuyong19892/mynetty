package com.madhouse.netty


import com.madhouse.netty.handler.ChildChannelHandler
import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.PooledByteBufAllocator
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import org.slf4j.LoggerFactory

/**
  * Created by Jay on 16/5/23.
  */
class NettyServer(port: Int) {
  val logger = LoggerFactory.getLogger(classOf[NettyServer])

  def run = {

    val default = Runtime.getRuntime.availableProcessors * 2

    val bossGroup = new NioEventLoopGroup(default)
    val workerGroup = new NioEventLoopGroup(default)
    try {
      val b = new ServerBootstrap()
      b.group(bossGroup, workerGroup)
        .channel(classOf[NioServerSocketChannel])
        .childHandler(new ChildChannelHandler)
        .option(ChannelOption.SO_BACKLOG, int2Integer(1024))
       // .childOption(ChannelOption.SO_SNDBUF, int2Integer(65535))
       // .childOption(ChannelOption.SO_RCVBUF, int2Integer(65535))
        .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
       // .childOption(ChannelOption.SO_KEEPALIVE, boolean2Boolean(true))

      val f = b.bind(port).sync();
      if(f.isSuccess) logger.info(s"Netty server started up on port: ${port}")

      f.channel().closeFuture().sync();
    } catch {
      case e:Exception => e.printStackTrace()
    }finally {
      workerGroup.shutdownGracefully()
      bossGroup.shutdownGracefully()
    }

  }

}

object NettyServer{
  val port = 9000
  def main(args: Array[String]) {
    new NettyServer(port).run
  }
}