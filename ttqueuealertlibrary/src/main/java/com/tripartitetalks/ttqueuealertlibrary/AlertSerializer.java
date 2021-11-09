package com.tripartitetalks.ttqueuealertlibrary;

import android.app.Dialog;
import android.content.DialogInterface;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 多个AlertDialog依次显示，前一个隐藏后显示下一个
 * <p>
 * 1、可以添加多个dialog
 * 2、监听上一个dialog隐藏后显示下一个，每显示一个就出队列
 */
public class AlertSerializer {

    private static AlertSerializer INSTANCE;

    private Queue<Dialog> queue;

    private DialogCallBack callBack;

    public static AlertSerializer create() {
        if (INSTANCE == null) {
            synchronized (AlertSerializer.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AlertSerializer();
                }
            }
        }

        return INSTANCE;
    }

    public AlertSerializer addDialog(Dialog dialog) {
        if (queue == null) {
            queue = new LinkedList<>();
        }
        if (dialog != null) {
            queue.add(dialog);
        }
        return INSTANCE;
    }

    public void show() {
        if (queue != null && !queue.isEmpty()) {
            Dialog dialog = queue.poll();
            if (dialog != null) {
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface d) {
                        if (callBack != null) {
                            callBack.onDialogDismiss(dialog);
                        }
                        show();
                    }
                });
                dialog.show();
                if (callBack != null) {
                    callBack.onDialogShow(dialog);
                }
            }
        }
    }

    public AlertSerializer setDialogCallBack(DialogCallBack callBack) {
        this.callBack = callBack;
        return INSTANCE;
    }

    public interface DialogCallBack {
        void onDialogShow(Dialog dialog);

        void onDialogDismiss(Dialog dialog);
    }
}
