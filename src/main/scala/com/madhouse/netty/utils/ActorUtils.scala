package com.madhouse.netty.utils

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import com.madhouse.netty.log.LoggerActor
import com.madhouse.netty.log.KafkaClient
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * Created by Jay on 2016/3/25.
  */
object ActorUtils {

  def start() = {}

  implicit val system = ActorSystem("mklog-server")
  implicit def executionContext: ExecutionContext = system.dispatchers.defaultGlobalDispatcher
  implicit val timeout = Timeout(100.millisecond)
  val kafkaClient = system.actorOf(Props(classOf[KafkaClient]), "kafka-client")
  val logactor = system.actorOf(Props(classOf[LoggerActor], kafkaClient),"logger-actor")
}
