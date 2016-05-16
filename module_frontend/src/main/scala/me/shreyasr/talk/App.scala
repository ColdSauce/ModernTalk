package me.shreyasr.talk

import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.jquery.jQuery
import upickle.default._
import scala.scalajs.js.JSApp

object App extends JSApp {

  var name = ""

  def main(): Unit = {
    jQuery(setupUi _)
  }

  def setupUi(): Unit = {
    jQuery("#connect-button").click(onButtonClick _)
  }

  def onButtonClick(): Unit = {
    name = jQuery("#name-text").value.toString
    initWS()
  }

  def initWS(): Unit = {
    val doc = dom.document
    val proto = if(doc.location.protocol == "https:") "wss" else "ws"
    val url = s"$proto://${doc.location.host}/ws?name=$name"
    val ws = new WebSocket(url)
    ws.onmessage = (x: MessageEvent) => {
      val talkModel = read[TalkModel](x.data.toString)
      jQuery("#other-person-label").text(s"${talkModel.name}")
      jQuery("#ta2").text(s"${talkModel.msg}")
    }
    ws.onopen = (x: Event) => {
      jQuery("#connect-div").hide()
    }
    ws.onerror = (x: ErrorEvent) => Console.println("WS Error: " + x.message)
    ws.onclose = (x: CloseEvent) => Console.println("WS Close: " + x.reason)

    jQuery("body").on("keyup", "#ta1", (event: Event) => {
      ws.send(write(new TalkModel(name, jQuery("#ta1").value.toString)))
    })
  }
}
