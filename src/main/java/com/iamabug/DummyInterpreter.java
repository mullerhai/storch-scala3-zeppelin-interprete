package com.iamabug;

import org.apache.zeppelin.interpreter.Interpreter;
import org.apache.zeppelin.interpreter.InterpreterContext;
import org.apache.zeppelin.interpreter.InterpreterResult;

import java.util.Properties;

public class DummyInterpreter extends Interpreter {

    // 增加一个 int 成员变量，记录解释器被调用的次数
    private int count;

    public DummyInterpreter(Properties properties) {
        super(properties);
    }

    // 其实在构造方法里初始化 count 更合适
    public void open() {
        count = 0;
    }

    // close 方法不需要做什么
    public void close() {

    }

    // 对输入进行处理，其实就是拼接字符串
    // 将结果封装成 InterpreterResult 类型
    // 其中 InterpreterResult.Code 是枚举类型，表明解释器执行结果的状态
    public InterpreterResult interpret(String s, InterpreterContext interpreterContext) {
        count ++;
        String res = count + ". " + "hello " + s;
        return new InterpreterResult(InterpreterResult.Code.SUCCESS, res);
    }

    // 不考虑 cancel 的情况
    public void cancel(InterpreterContext interpreterContext) {

    }

    // 动态表格类型，返回 None 枚举类型即可
    public FormType getFormType() {
        return FormType.NONE;
    }

    // 执行进度永远是 100
    public int getProgress(InterpreterContext interpreterContext) {
        return 100;
    }
}
