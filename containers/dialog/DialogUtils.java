package com.hyj.containers.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.kemov.elecmobiletools.R;

public class DialogUtils {

	public static Dialog createDialog(Context context,int layoutID){

		Dialog dialog = new Dialog(context, R.style.custom_dialog);
		Window window = dialog.getWindow();
		window.setContentView(layoutID);
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}


	public static Dialog createYesNoDialog(Context context,String msg){

		Dialog dialog =new AlertDialog.Builder(context)
				.setTitle("标题")
				.setMessage(msg)
				.setPositiveButton("确定",null)
				.setNegativeButton("取消",null).show();

		return dialog;
	}


}
