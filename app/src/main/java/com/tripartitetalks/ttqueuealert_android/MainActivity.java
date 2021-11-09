package com.tripartitetalks.ttqueuealert_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tripartitetalks.ttqueuealertlibrary.AlertSerializer;

public class MainActivity extends AppCompatActivity {
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        AlertSerializer serializer = AlertSerializer.create();
        TextView tvAdd = findViewById(R.id.tv_add_dialog);
        TextView tvShow = findViewById(R.id.tv_show_dialog);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zgf", "=======i==========" + i);
                AlertDialog dialog = createAlertDialog(i + "");
                serializer.addDialog(dialog);

                i ++;
            }
        });
        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zgf", "=======show==========");
                serializer.show();
            }
        });

        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }

    private AlertDialog createAlertDialog(String message) {
        return new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("这是第" + message + "dialog")
                .create();
    }

    private void test() {
        AlertSerializer.create()
                .addDialog(createAlertDialog("你好"))
                .addDialog(createAlertDialog("是吗"))
                .addDialog(createAlertDialog("海鸥"))
                .setDialogCallBack(new AlertSerializer.DialogCallBack() {
                    @Override
                    public void onDialogShow(Dialog dialog) {
                        Log.e("zgf", "==========onDialogShow=======" + dialog);
                    }

                    @Override
                    public void onDialogDismiss(Dialog dialog) {
                        Log.e("zgf", "=========onDialogDismiss========" + dialog);
                    }
                })
                .show();
    }
}