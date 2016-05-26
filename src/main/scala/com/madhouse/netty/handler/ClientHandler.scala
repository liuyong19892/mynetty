package com.madhouse.netty.handler

import io.netty.buffer.{ByteBuf, Unpooled}
import io.netty.channel.{ChannelHandlerAdapter, ChannelHandlerContext}
import io.netty.handler.codec.http.{HttpContent, HttpHeaderNames, HttpHeaders, HttpResponse}
import io.netty.util.CharsetUtil

/**
  * Created by Jay on 16/5/23.
  */
class ClientHandler extends ChannelHandlerAdapter{
  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any): Unit = {
    try {
      msg match {
        case m: HttpResponse => {
          println("CONTENT_TYPE:" + m.headers().get(HttpHeaderNames.CONTENT_TYPE))
        }
        case c: HttpContent => {
          val content = c.content
          println(content.toString(CharsetUtil.UTF_8))
          content.release()
        }
        case _ => println("error!")

      }

    }catch {
      case e:Exception => e.printStackTrace()
    }

  }

}
