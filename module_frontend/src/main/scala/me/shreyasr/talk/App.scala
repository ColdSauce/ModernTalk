package me.shreyasr.talk

import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.jquery.jQuery

import scala.scalajs.js.JSApp

object App extends JSApp {

  def main(): Unit = {
    jQuery(setupUi _)
    initWS()
  }

  def setupUi(): Unit = {
    jQuery("#click-me-button").click(onButtonClick _)
    jQuery("body").append(s"<p>${TalkModel.str}</p>")
  }

  def onButtonClick(): Unit = {
    jQuery("body").append("<p>You clicked the button!</p>")
  }

  def initWS(): Unit = {
    val doc = dom.document
    val proto = if(doc.location.protocol == "https:") "wss" else "ws"
    val url = s"$proto://${doc.location.host}/ws"
    val ws = new WebSocket(url)
    ws.onmessage = (x: MessageEvent) => jQuery("body").append(s"<p>${x.data.toString}</p>")
    ws.onopen = (x: Event) => {}
    ws.onerror = (x: ErrorEvent) => Console.println("some error has occured " + x.message)
    ws.onclose = (x: CloseEvent) => {}
  }
}
