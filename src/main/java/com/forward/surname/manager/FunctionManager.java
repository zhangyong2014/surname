package com.forward.surname.manager;

import android.text.TextUtils;

import com.forward.surname.function.FunctionException;
import com.forward.surname.function.FunctionNoParamNoResult;
import com.forward.surname.function.FunctionWithParamAndResult;
import com.forward.surname.function.FunctionWithParamOnly;
import com.forward.surname.function.FunctionWithResultOnly;

import java.util.HashMap;

/**
 * Created by zy on 2017/11/7.
 */

public class FunctionManager {
    private static FunctionManager instance;
    private HashMap<String,FunctionNoParamNoResult> mFunctionNoParamNoResault;
    private HashMap<String,FunctionWithParamOnly> mFunctionWithParamOnly;
    private HashMap<String,FunctionWithResultOnly> mFunctionWithResultOnly;
    private HashMap<String,FunctionWithParamAndResult> mFunctionWithParamAndResult;

    private FunctionManager() {
        mFunctionNoParamNoResault = new HashMap<>();
        mFunctionWithParamOnly = new HashMap<>();
        mFunctionWithResultOnly = new HashMap<>();
        mFunctionWithParamAndResult = new HashMap<>();
    }
    public static FunctionManager getInstance(){
        if(instance == null){
            instance = new FunctionManager();
        }
        return instance;
    }

    /**
     * 添加无参无返回值的接口方法
     * @param function
     * @return
     */

    public FunctionManager addFunction(FunctionNoParamNoResult function){
        mFunctionNoParamNoResault.put(function.mFunctionName,function);
        return this;
    }

    /**
     * 调用无参无返回值的接口方法
     * @param funcName
     */
    public void invokeFunc(String funcName){
        if(TextUtils.isEmpty(funcName) == true){
            return;
        }
        if(mFunctionNoParamNoResault != null){
            FunctionNoParamNoResult f = mFunctionNoParamNoResault.get(funcName);
            if(f != null){
                f.function();
            }
            if (f == null){
                try {
                    throw  new FunctionException("Has no this function" + funcName);
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 添加无参有返回值的接口方法
     * @param function
     * @return
     */
    public FunctionManager addFunction(FunctionWithResultOnly function){
        mFunctionWithResultOnly.put(function.mFunctionName,function);
        return this;
    }
    /**
     * 调用无参有返回值的接口方法
     * @param funcName
     */
    public <Result> Result invokeFunc(String funcName,Class<Result> clz){
        if(TextUtils.isEmpty(funcName) == true){
            return null;
        }
        if(mFunctionWithResultOnly != null){
            FunctionWithResultOnly f = mFunctionWithResultOnly.get(funcName);
            if(f != null){
                if(clz != null){
                    return clz.cast(f.function());
                } else {
                    return (Result) f.function();
                }
            }else {
                try {
                    throw  new FunctionException("Has no this function" + funcName);
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * 添加有参有返回值的接口方法
     * @param function
     * @return
     */
    public FunctionManager addFunction(FunctionWithParamAndResult function){
        mFunctionWithParamAndResult.put(function.mFunctionName,function);
        return this;
    }
    /**
     * 调用有参有返回值的接口方法
     * @param funcName
     */
    public <Result,Param> Result invokeFunc(String funcName,Class<Result> clz ,Param data){
        if(TextUtils.isEmpty(funcName) == true){
            return null;
        }
        if(mFunctionWithParamAndResult != null){
            FunctionWithParamAndResult f = mFunctionWithParamAndResult.get(funcName);
            if(f != null){
                if(clz != null){
                    return clz.cast(f.function(data));
                } else {
                    return (Result) f.function(data);
                }
            }else {
                try {
                    throw  new FunctionException("Has no this function" + funcName);
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * 添加有参无返回值的接口方法
     * @param function
     * @return
     */
    public FunctionManager addFunction(FunctionWithParamOnly function){
        mFunctionWithParamOnly.put(function.mFunctionName,function);
        return this;
    }
    /**
     * 调用有参无返回值的接口方法
     * @param funcName
     */
    public <Param> void invokeFunc(String funcName,Param data ){
        if(TextUtils.isEmpty(funcName) == true){
            return ;
        }
        if(mFunctionWithParamOnly != null){
            FunctionWithParamOnly f = mFunctionWithParamOnly.get(funcName);
            if(f != null){
                f.function(data);
            }else {
                try {
                    throw  new FunctionException("Has no this function" + funcName);
                } catch (FunctionException e) {
                    e.printStackTrace();
                }
            }
        }
        return ;
    }
}
