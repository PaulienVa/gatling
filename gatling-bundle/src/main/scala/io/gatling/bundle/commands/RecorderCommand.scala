/*
 * Copyright 2011-2022 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.gatling.bundle.commands

import java.io.File

import scala.jdk.CollectionConverters._

import io.gatling.bundle.BundleIO
import io.gatling.bundle.commands.CommandHelper._
import io.gatling.plugin.util.{ Fork, JavaLocator }

class RecorderCommand(args: List[String]) {
  private[bundle] def run(): Unit = {
    val javaOpts = systemJavaOpts ++ List("-Xms128M", "-Xmx512M")
    val javaClasspath = optionListEnv("JAVA_CLASSPATH")

    val classPath = gatlingLibs ++ gatlingConfFiles ++ javaClasspath

    new Fork(
      "io.gatling.recorder.GatlingRecorder",
      classPath.asJava,
      javaOpts.asJava,
      args.asJava,
      JavaLocator.getJavaExecutable,
      true,
      BundleIO.getLogger,
      new File(gatlingHome)
    ).run()
  }
}
