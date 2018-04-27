package com.forward.surname.function;

/**
 * Created by zy on 2017/11/7.
 * 无参有返回值接口抽象类
 */

public abstract class FunctionWithResultOnly<Result> extends Function{
    public FunctionWithResultOnly(String functionName){
        super(functionName);
    }
    public abstract Result function();
}
