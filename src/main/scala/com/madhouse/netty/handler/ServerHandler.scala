package com.madhouse.netty.handler

import java.net.URI

import com.madhouse.netty.log.{ErrorLogger, LoggerUtils}
import com.madhouse.netty.utils.ActorUtils
import io.netty.buffer.Unpooled
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.handler.codec.http.HttpHeaders._
import io.netty.handler.codec.http.HttpVersion._
import io.netty.handler.codec.http.{DefaultFullHttpResponse, FullHttpRequest, HttpResponseStatus, HttpUtil}

/**
  * Created by Jay on 16/5/23.
  */
class ServerHandler extends SimpleChannelInboundHandler[FullHttpRequest]  {
  val OK = new HttpResponseStatus(200, "OK")
  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    super.channelActive(ctx)
  }

  override def channelRead0(ctx: ChannelHandlerContext, request: FullHttpRequest): Unit = {

    try {
      val content = request.content()
      val bytes= new Array[Byte](content.readableBytes())
      val headers = request.headers()
      val urlx = request.getUri
      if(urlx.contains("favicon.ico")){
        return
      }
      val keepAlive = HttpUtil.isKeepAlive(request);
      val uri = URI.create(urlx)
      val path = uri.getPath
      if(bytes.length > 0) ActorUtils.logactor ! content.toString()
      else ErrorLogger.logger.info("No body content!")

      //response
      val response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer("hahahahahahh".getBytes("UTF-8")))
      response.headers().set(Names.CONTENT_TYPE, "text/plain; charset=UTF-8")
      response.headers().set(Names.CONTENT_LENGTH, response.content().readableBytes())
      response.headers().set(Names.CONNECTION, Values.KEEP_ALIVE)

      ctx.writeAndFlush(response)
    } catch {
      case e: Exception => ErrorLogger.logger.error("ERROR:"+ LoggerUtils.getTrace(e))
    } finally {
      ctx.close()
    }

  }

  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    ctx.flush()
  }
}
