package com.madhouse.netty.log

import java.util.Properties

import akka.actor.Actor
import com.madhouse.netty.utils.Constants
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

case class Msg(topic:String, key:Long, value:String)
class KafkaClient extends Actor {
  val producer = {
    val props = new Properties()
    props.put("bootstrap.servers", Constants.brokers)
    props.put("client.id", "DemoProducer")
    props.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[Long, String](props)
  }

  class CallBack1 extends Callback{
    override def onCompletion(metadata: RecordMetadata, e: Exception): Unit = {
      if (e != null) e.printStackTrace()
    }
  }

  override def receive: Receive = {
    case Msg(topic, key, value) =>{
      producer.send(new ProducerRecord[Long, String](topic, key, value), new CallBack1())
    }

  }
}