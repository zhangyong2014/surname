package com.forward.surname.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forward.surname.manager.FunctionManager;

/**
 * Created by zy on 2017/11/3.
 */

public abstract class BaseFragment extends Fragment {
    static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected Activity mActivity;
    protected View mRootView;
    boolean mbFirstShow = true;
    protected boolean mUIInitialize = false;
    protected FunctionManager mFunctionManager;

    /**
     * 为要实现接口的Fragment添加FunctionManager
     */
    public void setFunctionManager(FunctionManager functionManager){
        this.mFunctionManager = functionManager;
    }

    /**
     * 当Activity与Fragment发生关联时调用
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity){
            BaseActivity activity = (BaseActivity) context;
            activity.setFuctionsForFragment(getTAG());
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    /**
     * 保持数据
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }



    /**
     * 创建该Fragment的视图
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initViews();
            if (mbFirstShow) {
                mbFirstShow = false;
                mUIInitialize = true;
                loadPage(savedInstanceState);
            }
        } else{
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null){
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }


    /**
     * 当activity onCreate返回时调用
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    /**
     * 与onCreateView相对应，当改Fragment被移除时调用
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 与onAttach()相对应，当Fragment与Activity的关联被取消时调用
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    // 子类必须实现初始化布局的方法
    public abstract View initViews();

    // 加载页面信息，默认是在第一次初始化的时候会调用
    public abstract void loadPage(Bundle savedInstanceState);

    //获取tag
    public abstract String  getTAG();

}
