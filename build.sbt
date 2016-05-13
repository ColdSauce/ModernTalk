name := "ModernTalk"

scalaVersion := "2.11.8"

lazy val root = project.aggregate(frontend)
lazy val frontend = project.in(file("module_frontend"))