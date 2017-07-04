package com.madhouse.netty.log

import org.slf4j.LoggerFactory

/**
  * Created by sky-wind on 2016/3/4.
  */

trait Logger {
  val logger  = LoggerFactory.getLogger(this.getClass);
}

object LoggerUtil extends Logger
object ErrorLogger extends Logger
