package me.shreyasr.talk

import org.scalajs
import org.scalajs.dom
import dom.document

import scala.scalajs.js.JSApp

object App extends JSApp {

  def main(): Unit = {
    appendPar(document.body, "Hello world!")
  }

  def appendPar(targetNode: scalajs.dom.Node, text: String): Unit = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }
}
