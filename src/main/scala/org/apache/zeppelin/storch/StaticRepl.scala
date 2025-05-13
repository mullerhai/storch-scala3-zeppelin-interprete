///*
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *    http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.apache.zeppelin.storch
//
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import javax.tools.Diagnostic
//import javax.tools.DiagnosticCollector
//import javax.tools.JavaCompiler
//import javax.tools.JavaCompiler.CompilationTask
//import javax.tools.JavaFileObject
//import javax.tools.SimpleJavaFileObject
//import javax.tools.ToolProvider
//import java.io.ByteArrayOutputStream
//import java.io.File
//import java.io.PrintStream
//import java.io.StringReader
//import java.lang.reflect.InvocationTargetException
//import java.net.URI
//import java.net.URL
//import java.net.URLClassLoader
//import java.util
//
///**
// * StaticRepl for compling the java code in memory
// */
//object StaticRepl {
////  private val LOGGER = LoggerFactory.getLogger(classOf[StaticRepl])
//
//  @throws[Exception]
//  def execute(generatedClassName: String, code: String): String = {
//    val compiler = ToolProvider.getSystemJavaCompiler
//    val diagnostics = new DiagnosticCollector[JavaFileObject]
//    // Java parasing
//    val builder = new JavaProjectBuilder
//    val src = builder.addSource(new StringReader(code))
//    // get all classes in code (paragraph)
//    val classes = src.getClasses
//    var mainClassName: String = null
//    // Searching for class containing Main method
//    for (i <- 0 until classes.size) {
//      var hasMain = false
//      for (j <- 0 until classes.get(i).getMethods.size) {
//        if (classes.get(i).getMethods.get(j).getName == "main" && classes.get(i).getMethods.get(j).isStatic) {
//          mainClassName = classes.get(i).getName
//          hasMain = true
//          break //todo: break is not supported
//        }
//      }
//      if (hasMain == true) break //todo: break is not supported
//    }
//    // if there isn't Main method, will retuen error
//    if (mainClassName == null) {
//      LOGGER.error("Exception for Main method", "There isn't any class " + "containing static main method.")
//      throw new Exception("There isn't any class containing static main method.")
//    }
//    // replace name of class containing Main method with generated name
//    code = code.replace(mainClassName, generatedClassName)
//    val file = new JavaSourceFromString(generatedClassName, code.toString)
//    val compilationUnits = util.Arrays.asList(file)
//    val baosOut = new ByteArrayOutputStream
//    val baosErr = new ByteArrayOutputStream
//    // Creating new stream to get the output data
//    val newOut = new PrintStream(baosOut)
//    val newErr = new PrintStream(baosErr)
//    // Save the old System.out!
//    val oldOut = System.out
//    val oldErr = System.err
//    // Tell Java to use your special stream
//    System.setOut(newOut)
//    System.setErr(newErr)
//    val task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits)
//    // executing the compilation process
//    val success = task.call
//    // if success is false will get error
//    if (!success) {
//      import scala.jdk.CollectionConverters._
//      for (diagnostic <- diagnostics.getDiagnostics.asScala) {
//        if (diagnostic.getLineNumber == -1) continue //todo: continue is not supported
//        System.err.println("line " + diagnostic.getLineNumber + " : " + diagnostic.getMessage(null))
//      }
//      System.out.flush()
//      System.err.flush()
//      System.setOut(oldOut)
//      System.setErr(oldErr)
//      LOGGER.error("Exception in Interpreter while compilation", baosErr.toString)
//      throw new Exception(baosErr.toString)
//    }
//    else try {
//      // creating new class loader
//      val classLoader = URLClassLoader.newInstance(Array[URL](new File("").toURI.toURL))
//      // execute the Main method
//      Class.forName(generatedClassName, true, classLoader).getDeclaredMethod("main", Array[Class[_]](classOf[Array[String]])).invoke(null, Array[AnyRef](null))
//      System.out.flush()
//      System.err.flush()
//      // set the stream to old stream
//      System.setOut(oldOut)
//      System.setErr(oldErr)
//      baosOut.toString
//    } catch {
//      case e@(_: ClassNotFoundException | _: NoSuchMethodException | _: IllegalAccessException | _: InvocationTargetException) =>
//        LOGGER.error("Exception in Interpreter while execution", e)
//        System.err.println(e)
//        e.printStackTrace(newErr)
//        throw new Exception(baosErr.toString, e)
//    } finally {
//      System.out.flush()
//      System.err.flush()
//      System.setOut(oldOut)
//      System.setErr(oldErr)
//    }
//  }
//}
//
//class JavaSourceFromString private[java](name: String, private[java] val code: String) extends SimpleJavaFileObject(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE) {
//  override def getCharContent(ignoreEncodingErrors: Boolean): CharSequence = code
//}