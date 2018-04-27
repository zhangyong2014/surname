package com.forward.surname.function;

/**
 * Created by zy on 2017/11/7.
 * 有参无返回值接口抽象类
 */

public abstract class FunctionWithParamOnly<Param> extends Function{
    public FunctionWithParamOnly(String functionName){
        super(functionName);
    }
    public abstract void function(Param pram);
}