package com.forward.surname.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.forward.surname.manager.ActivityManager;
import com.forward.surname.manager.FunctionManager;
import com.forward.surname.utils.DisplayUtils;

/**
 * Created by zy on 2017/6/15.
 */
public abstract class BaseActivity extends AppCompatActivity{
    final  static String TAG="CommonActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //优化activity的背景过度绘制
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //入栈
        ActivityManager.getInstance().pushActivity(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    /**
     * activity从后台恢复时候的流程 onRestart-onStart-onResume
     */
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 有新的activity进栈时 onPause会执行 如果当期activity可见，会到该阶段中
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 如果当期activity不可见，会到该阶段中
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 销毁时候调用
     */
    @Override
    protected void onDestroy() {
        //出栈
        ActivityManager.getInstance().popActivity(this);
        super.onDestroy();
    }


    @Override
    public void finish() {
        DisplayUtils.hideKeyboard(this);
        super.finish();
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onKeyBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    /**
     * 返回键处理
     */
    public abstract void onKeyBack();


    /**
     * 添加接口并实现接口中的方法回调
     */
    public void setFuctionsForFragment(String tag){
        FragmentManager fm = getSupportFragmentManager();
        BaseFragment fragment = (BaseFragment) fm.findFragmentByTag(tag);
        FunctionManager functionManager = FunctionManager.getInstance();
        impFunction(functionManager);
        fragment.setFunctionManager(functionManager);
    }

    /**
     * 实现接口
     */
    public abstract void impFunction(FunctionManager functionManager);


//    public  void impFunction(FunctionManager functionManager) {
//        if (functionManager != null)
//            functionManager
//                    .addFunction(new FunctionNoParamNoResult("interface1") {
//                        @Override
//                        public void function() {
//
//                        }
//                    })
//                    .addFunction(new FunctionWithParamOnly<String>("interface2") {
//                        @Override
//                        public void function(String param) {
//
//                        }
//                    })
//                    .addFunction(new FunctionWithResultOnly<String>("interface3") {
//                        @Override
//                        public String function() {
//                            return "";
//                        }
//                    })
//                    .addFunction(new FunctionWithParamAndResult<String, String>("interface4") {
//                        @Override
//                        public String function(String param) {
//                            return "";
//                        }
//                    });
//    }
}
