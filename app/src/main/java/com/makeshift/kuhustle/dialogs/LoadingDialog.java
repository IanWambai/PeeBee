package com.makeshift.kuhustle.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.makeshift.kuhustle.R;
import com.todddavies.components.progressbar.ProgressWheel;


public class LoadingDialog extends Dialog {

	public LoadingDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature((int) Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loading_dialog);

		ProgressWheel pw = (ProgressWheel) findViewById(R.id.pw_spinner);
		pw.spin();
	}
}
