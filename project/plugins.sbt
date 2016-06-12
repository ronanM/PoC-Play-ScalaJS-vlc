// Resolvers
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.4")

// Scala-js  plugins
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.9")

// plugin for gzip compressing web assets.
addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.0")

// Play-scala.js Integration plugin: https://github.com/vmunier/sbt-play-scalajs
addSbtPlugin("com.vmunier" % "sbt-play-scalajs" % "0.3.0")

// web plugins
//addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.0")
//addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.1.0")
//addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.3")
//addSbtPlugin("com.typesafe.sbt" % "sbt-rjs" % "1.0.7")
//addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.0")
//addSbtPlugin("com.typesafe.sbt" % "sbt-mocha" % "1.1.0")
//addSbtPlugin("org.irundaia.sbt" % "sbt-sassify" % "1.4.2")

// ------------------------------------------------------

// Comment to get more information during initialization
//logLevel := Level.Warn




// Plugins for improving code quality

// Plugins to check scala style warnings
//addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0")

// Plugin to format scala code while compilation
//addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

// Plugin for source code statistics and analytics
//addSbtPlugin("com.orrsella" % "sbt-stats" % "1.0.5")

// Plugin for copy paste detector
//addSbtPlugin("de.johoop" % "cpd4sbt" % "1.1.5")

resolvers += Resolver.sonatypeRepo("releases")

//addSbtPlugin("org.brianmckenna" % "sbt-wartremover" % "0.14")

//addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.3.0")