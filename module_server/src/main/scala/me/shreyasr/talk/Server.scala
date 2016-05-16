package me.shreyasr.talk

import java.net.InetSocketAddress

import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.duration.Duration

class Server(host: String, port: Int) {
  
  private val logger = org.log4s.getLogger
  logger.info(s"Starting Http4s-blaze Server on '$host:$port'")

  def run(): Unit = {
    BlazeBuilder
      .bindSocketAddress(new InetSocketAddress(host, port))
      .withWebSockets(true)
      .withIdleTimeout(Duration.Inf)
      .mountService(new StaticRoutes().service, "/")
      .mountService(new WebsocketRoutes().service, "/")
      .run
      .awaitShutdown()
  }
}
