package com.madhouse.netty.log

import akka.actor.{Actor, ActorRef}
import com.madhouse.netty.utils.Constants


class LoggerActor(kafkaClient: ActorRef) extends Actor with Logger{

  override def receive  = {
    case msg:String => {
      logger.info(msg)
      kafkaClient ! Msg(Constants.topic, System.currentTimeMillis(), msg)
    }
  }
}