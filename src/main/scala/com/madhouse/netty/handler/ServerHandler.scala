package com.madhouse.netty.handler

import java.util.concurrent.Executors

import akka.actor.{ActorSystem, Props}
import io.netty.buffer.Unpooled
import io.netty.channel.{ChannelFutureListener, ChannelHandlerAdapter, ChannelHandlerContext}
import io.netty.handler.codec.http.HttpVersion._
import io.netty.handler.codec.http._
import io.netty.util.CharsetUtil
import org.slf4j.LoggerFactory

/**
  * Created by Jay on 16/5/23.
  */
class ServerHandler extends ChannelHandlerAdapter {
  val logger = LoggerFactory.getLogger(classOf[ServerHandler])
  val OK = new HttpResponseStatus(200, "OK")
 // val system = ActorSystem("taskSystem")
  val threadPool = Executors.newCachedThreadPool()
  def handlerHttpRequest(ctx: ChannelHandlerContext, msg: Any) = {
    msg match {
      case c: HttpContent => {
        val buf = c.content()
        logger.info(s"Received msg: ${buf.toString(CharsetUtil.UTF_8)}")
        threadPool.submit(new Runnable {
          override def run(): Unit = println(buf.toString(CharsetUtil.UTF_8))
        })
        //if the biz is complex, send to akka to process
      //  val actor = system.actorOf(Props[TaskHandler], name="task")
       // actor ! Task(buf.toString(CharsetUtil.UTF_8))

        buf.release()

        val res = "I am OK"
        val response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes().toString)
        response.content().readableBytes()

        ctx.write(response) addListener (ChannelFutureListener.CLOSE);
      }
    }
  }

  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
    handlerHttpRequest(ctx, msg)
  }

  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = ctx.flush()

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    cause.printStackTrace
    threadPool.shutdown
    ctx.close
  }
}
