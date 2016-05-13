package me.shreyasr.talk

object Main extends App {

  import scala.util.Properties.envOrNone
  val ip   = envOrNone("HOST") getOrElse "0.0.0.0"
  val port = envOrNone("PORT").map(_.toInt) getOrElse 8080
  new Server(ip, port).run()
}
