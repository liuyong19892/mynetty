package com.madhouse.netty.handler

import akka.actor.Actor

/**
  * Created by Jay on 16/5/24.
  */
case class Task(name:String)
class TaskHandler extends Actor{
    override def receive: Receive = {
      case t: Task => println(s"akka received task : ${t.name}")
      case _           => println("Nothing received!")
    }
}
