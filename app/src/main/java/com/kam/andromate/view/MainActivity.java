package com.kam.andromate.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kam.andromate.singletons.AndroMateApp;
import com.kam.andromate.singletons.AndroMateDevice;
import com.kam.andromate.IConstants;
import com.kam.andromate.R;
import com.kam.andromate.utils.AppUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AndroMateApp";

    /*
    / receiver to restart AndroMate app
    */
    BroadcastReceiver appRestartReceiver = null;

    private MainReportSection mainReportSection = null;
    ImageButton execButton = null;
    ToggleButton ctsButton = null;
    ToggleButton controlLineRts = null;

    private void initView() {
        mainReportSection = new MainReportSection(findViewById(R.id.androidMateReportSectionId));
        execButton = findViewById(R.id.send_btn);
        ctsButton = findViewById(R.id.controlLineCts);
        controlLineRts = findViewById(R.id.controlLineRts);
        if (!IConstants.SHOW_EXECUTE_BAR) {
            findViewById(R.id.CommandLinearLayoutId).setVisibility(View.GONE);
        }
        AndroMateDevice.setInstance(getApplicationContext());
        AndroMateApp.setInstance(getApplicationContext());
        mainReportSection.initAndroMateReportInfo();
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter, int flags) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return super.registerReceiver(receiver, filter, flags);
        } else {
            return super.registerReceiver(receiver, filter);
        }
    }

    @SuppressLint("InlinedApi")
    private void initBroadcastReceiver() {
        if (appRestartReceiver == null) {
            appRestartReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    try {
                        Intent restartIntent = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage(getBaseContext().getPackageName());
                        if (restartIntent != null) {
                            restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(restartIntent);
                            finish();
                            Runtime.getRuntime().exit(0);
                        }
                    } catch (Throwable ignored) {}
                }
            };
            registerReceiver(appRestartReceiver, new IntentFilter(IConstants.APP_RESTART_RECEIVER), Context.RECEIVER_EXPORTED);
        }
    }

    private void unregisterReceiver() {
        if (appRestartReceiver != null) {
            try {
                unregisterReceiver(appRestartReceiver);
                appRestartReceiver = null;
            } catch (Throwable t) {
                Log.e(TAG, " cannot unregister app restart receiver due to "+t);
            }
        }
    }

    private void initClickEvent() {
        if (execButton != null) {
            execButton.setOnClickListener(view -> {

            });
        }
        if (ctsButton != null) {
            ctsButton.setOnClickListener(view -> {
                mainReportSection.clear();
            });
        }
        if (controlLineRts != null) {
            controlLineRts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.rebootApp(getApplicationContext());
                }
            });
        }
    }

    private void initAndroMateApp() {
        initView();
        initClickEvent();
        initBroadcastReceiver();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initAndroMateApp();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver();
        super.onDestroy();
    }

}