package cn.com.example.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.example.activity.R;

public class CustomProgressdialog extends Dialog{
	private Context context = null;
	private static CustomProgressdialog customProgressdialog;
	
	public CustomProgressdialog(Context context){
		super(context);
		this.context = context;
	}
	
	public CustomProgressdialog(Context context, int theme){
		super(context, theme);
	}
	
	public static CustomProgressdialog createDialog(Context context){
		customProgressdialog = new CustomProgressdialog(context, R.style.dialog);
		customProgressdialog.setContentView(R.layout.custom_progress_dialog);
		customProgressdialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return customProgressdialog;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (customProgressdialog == null)
			return;
		ImageView imageView = (ImageView)findViewById(R.id.loadingImg);
		AnimationDrawable animationDrawable = (AnimationDrawable)imageView.getBackground();
		animationDrawable.start();
	}
	
	public CustomProgressdialog setTitle(String title){
		return customProgressdialog;
	}
	
	public CustomProgressdialog setMessage(String message){
		TextView loading_text = (TextView)findViewById(R.id.loadingText);
		if (loading_text != null){
			loading_text.setText(message);
		}
		return customProgressdialog;
	}
	
	
}
