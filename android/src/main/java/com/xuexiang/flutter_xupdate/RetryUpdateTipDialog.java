package com.xuexiang.flutter_xupdate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xuexiang.xupdate.XUpdate;

public class RetryUpdateTipDialog extends AppCompatActivity implements DialogInterface.OnDismissListener {

    public static final String KEY_CONTENT = "com.xuexiang.flutter_xupdate.KEY_CONTENT";
    public static final String KEY_URL = "com.xuexiang.flutter_xupdate.KEY_URL";

    public static void show(String content, String url) {
        Intent intent = new Intent(XUpdate.getContext(), RetryUpdateTipDialog.class);
        intent.putExtra(KEY_CONTENT, content);
        intent.putExtra(KEY_URL, url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        XUpdate.getContext().startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String content = getIntent().getStringExtra(KEY_CONTENT);
        final String url = getIntent().getStringExtra(KEY_URL);

        if (TextUtils.isEmpty(content)) {
            content = getString(R.string.xupdate_retry_tip_dialog_content);
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(content)
                .setPositiveButton(android.R.string.yes, (d, which) -> goWeb(url))
                .setNegativeButton(android.R.string.no, null)
                .setCancelable(false)
                .show();
        dialog.setOnDismissListener(this);
    }

    private void goWeb(final String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}
