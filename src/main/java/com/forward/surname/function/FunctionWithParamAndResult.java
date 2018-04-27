package com.forward.surname.function;

/**
 * Created by zy on 2017/11/7.
 * 有参有返回值接口抽象类
 */

public abstract class FunctionWithParamAndResult<Result,Param> extends Function{
    public FunctionWithParamAndResult(String functionName){
        super(functionName);
    }
    public abstract Result function(Param pram);
}
