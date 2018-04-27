package com.forward.surname.utils;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Frank on 2016/12/22.
 * 崩溃相关工具类
 */
public final class CrashUtils implements Thread.UncaughtExceptionHandler {
    private volatile static CrashUtils mInstance;
    private Thread.UncaughtExceptionHandler mHandler;
    private boolean mInitialized;
    private OnCrashListener mCrashListener;
    private CrashUtils(OnCrashListener crashListener) {
        this.mCrashListener = crashListener;
    }
    /**单例**/
    public static CrashUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (CrashUtils.class) {
                if (mInstance == null) {
                    mInstance = new CrashUtils((OnCrashListener) context);
                }
            }
        }
        return mInstance;
    }
    /*** 初始化{true: 成功;false: 失败}**/
    public boolean init() {
        if (mInitialized) return true;
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return mInitialized = true;
    }


    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mHandler.uncaughtException(thread, ex);
        } else { // 如果自己处理了异常，则不会弹出错误对话框，则需要手动退出app
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }

    }
    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @return true代表处理该异常，不再向上抛异常，
     * false代表不处理该异常(可以将该log信息存储起来)然后交给上层(这里就到了系统的异常处理)去处理，
     * 简单来说就是true不会弹出那个错误提示框，false就会弹出
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }

        mCrashListener.handleCrash();

        final StackTraceElement[] stack = ex.getStackTrace();
        final String message = ex.getMessage();

        //保存异常信息
        new Thread() {
            @Override
            public void run() {

                Looper.prepare();

                LogUtils.i(message);
                dosave(stack, message);
                Looper.loop();
            }

            private void dosave(final StackTraceElement[] stack,
                                final String message) {
                // 保存出错信息到文件
                String fileName = "crash-" + new Date().getTime() + ".txt";
                File file = new File(Environment.getExternalStorageDirectory()
                        + "/log");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file1 = new File(file, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file1, true);
                    fos.write(message.getBytes());
                    for (int i = 0; i < stack.length; i++) {
                        fos.write(stack[i].toString().getBytes());
                    }

                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                }
            }

        }.start();
        return true;
    }

    /**
     * 获取手机信息
     *
     * @return
     */
    public String getPhoneInfo() {
        String phoneInfo = "Product: " + android.os.Build.PRODUCT;
        phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI;
        phoneInfo += ", TAGS: " + android.os.Build.TAGS;
        phoneInfo += ", VERSION_CODES.BASE: "
                + android.os.Build.VERSION_CODES.BASE;
        phoneInfo += ", MODEL: " + android.os.Build.MODEL;
        phoneInfo += ", SDK: " + android.os.Build.VERSION.SDK_INT;
        phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
        phoneInfo += ", DEVICE: " + android.os.Build.DEVICE;
        phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY;
        phoneInfo += ", BRAND: " + android.os.Build.BRAND;
        phoneInfo += ", BOARD: " + android.os.Build.BOARD;
        phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT;
        phoneInfo += ", ID: " + android.os.Build.ID;
        phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER;
        phoneInfo += ", USER: " + android.os.Build.USER;
        return phoneInfo;
    }
    public interface OnCrashListener {
        void handleCrash();
    }
}
