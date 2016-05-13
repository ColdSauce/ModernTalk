package me.shreyasr.talk

import org.http4s.HttpService
import org.http4s.dsl._
import org.http4s.server.websocket.WS
import org.http4s.websocket.WebsocketBits.{Text, WebSocketFrame}

import scala.concurrent.duration._
import scalaz.concurrent.Strategy.{DefaultStrategy, DefaultTimeoutScheduler}
import scalaz.concurrent.Task
import scalaz.stream.{Exchange, Process, Sink, time}

class WebsocketRoutes() {
  private val log = org.log4s.getLogger

  val service = HttpService {
    case req @ GET -> Root / "ws" =>
      log.info("GET WEBSOCKET")
      val src = time.awakeEvery(1.seconds)(DefaultStrategy, DefaultTimeoutScheduler).map{ d => Text(s"Ping! $d") }
      val sink: Sink[Task, WebSocketFrame] = Process.constant {
        case Text(t, _) => Task.delay(log.info(t))
        case f          => Task.delay(log.info(s"Unknown type: $f"))
      }
      WS(Exchange(src, sink))
  }
}
