package org.apache.zeppelin.storch

import org.apache.zeppelin.interpreter.{Interpreter, InterpreterContext, InterpreterResult}

import java.util.Properties


class StorchInterpreter(properties: Properties) extends Interpreter(properties){

  override def open(): Unit = ???

  override def close(): Unit = ???

  override def interpret(s: String, interpreterContext: InterpreterContext): InterpreterResult = ???

  override def cancel(interpreterContext: InterpreterContext): Unit = ???

  override def getFormType: Interpreter.FormType = ???

  override def getProgress(interpreterContext: InterpreterContext): Int = ???
}
