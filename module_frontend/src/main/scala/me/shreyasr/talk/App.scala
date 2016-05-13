package me.shreyasr.talk

import org.scalajs.jquery.jQuery

import scala.scalajs.js.JSApp

object App extends JSApp {

  def main(): Unit = {
    jQuery(setupUi _)
  }

  def setupUi(): Unit = {
    jQuery("#click-me-button").click(onButtonClick _)
    jQuery("body").append(s"<p>${TalkModel.str}</p>")
  }

  def onButtonClick(): Unit = {
    jQuery("body").append("<p>You clicked the button!</p>")
  }
}
