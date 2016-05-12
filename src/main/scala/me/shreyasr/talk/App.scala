package me.shreyasr.talk

import org.scalajs
import org.scalajs.dom
import dom.document

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object App extends JSApp {

  def main(): Unit = {
    appendPar(document.body, "Hello world!")
  }

  @JSExport
  def onButtonClick(): Unit = {
    appendPar(document.body, "You clicked the button!")
  }

  def appendPar(targetNode: scalajs.dom.Node, text: String): Unit = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }
}
