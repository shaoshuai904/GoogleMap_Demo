package com.maple.googlemap.view;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog {
    private volatile ProgressDialog progressDialog;

    public ProgressDialog getDefault(Context context) {
        if (progressDialog == null) {
            synchronized (LoadingDialog.this) {
                if (progressDialog == null) {
                    createDialog(context);
                }
            }
        }
        return progressDialog;
    }

    private void createDialog(Context context) {
        progressDialog = new ProgressDialog(context);
//        if (progressDialog.getWindow() != null) {
//            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        }
//        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

}
