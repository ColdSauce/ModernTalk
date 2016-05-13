name := "ModernTalk"

scalaVersion := "2.11.8"

lazy val root = project.in(file("."))
  .aggregate(frontend, server, modelJvm, modelJs)
  .dependsOn(frontend, server, modelJvm, modelJs)
  .settings(publish := {}, publishLocal := {})

lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := "2.11.8"
)

val upickleVersion = "0.4.0"

lazy val frontend = project.in(file("module_frontend"))
  .enablePlugins(ScalaJSPlugin)
  .settings(commonSettings: _*)
  .settings(
    name := "ModernTalk-frontend",
    scalaJSUseRhino in Global := false,
    persistLauncher in Compile := true,
    skip in packageJSDependencies := false,
    libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.0",
    libraryDependencies += "com.lihaoyi" %%% "upickle" % upickleVersion,
    jsDependencies += "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js"
  )
  .dependsOn(modelJs)

lazy val http4s = Seq(
  resolvers += {
    // http4s dependsOn scalaz-stream wich is available at
    "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
  },
  libraryDependencies ++= {
    val http4sVersion = "0.13.2"
    Seq(
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-dsl"          % http4sVersion
    )
  }
)

lazy val server = project.in(file("module_server"))
  .settings(commonSettings: _*)
  .settings(Revolver.settings)
  .settings(http4s: _*)
  .settings(
    name := "ModernTalk-server",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "upickle" % upickleVersion,
      "ch.qos.logback" % "logback-classic" % "1.1.3"
    ),
    resources in Compile ++= {
      def andSourceMap(aFile: java.io.File) = {
        Seq(
          aFile,
          file(aFile.getAbsolutePath + ".map")
        )
      }
      andSourceMap((fastOptJS in(frontend, Compile)).value.data) ++
        andSourceMap((packageJSDependencies in(frontend, Compile)).value) ++
        andSourceMap((packageScalaJSLauncher in(frontend, Compile)).value.data)
    }
  )
  .dependsOn(modelJvm)

lazy val modelProject = crossProject.in(file("module_model"))
  .settings(commonSettings: _*)
  .settings(
    name := "ModernTalk-model"
  )
  .jsSettings(
  )

lazy val modelJvm = modelProject.jvm
lazy val modelJs = modelProject.js