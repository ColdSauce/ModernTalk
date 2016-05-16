package me.shreyasr.talk

import org.http4s.HttpService
import org.http4s.dsl._
import org.http4s.server.websocket.WS
import org.http4s.websocket.WebsocketBits.{Text, WebSocketFrame}
import upickle.default._
import scalaz.concurrent.Strategy.DefaultStrategy
import scalaz.stream.Exchange
import scalaz.stream.async.unboundedQueue

class WebsocketRoutes() {
  private val log = org.log4s.getLogger

  val queue = unboundedQueue[WebSocketFrame]

  val service = HttpService {
    case req @ GET -> Root / "ws" =>
      val name = req.params("name")
      log.info("Connected: " + name)
      val src = queue.dequeue filter {
        case Text(msg, _) => name != read[TalkModel](msg).name
      } collect {
        case Text(msg, _) => Text(msg)
      }
      WS(Exchange(src, queue.enqueue))
  }
}
