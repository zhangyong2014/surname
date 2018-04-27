package com.forward.surname.function;

/**
 * Created by zy on 2017/11/7.
 * 无参无返回值接口抽象类
 */

public abstract class FunctionNoParamNoResult extends Function{
    public FunctionNoParamNoResult(String functionName){
        super(functionName);
    }
    public abstract void function();
}
