package cn.com.example.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Tab4Fragment extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab4_fragment_layout);

		// ImageView title = (ImageView)this.findViewById(R.id.title);
		// title.setBackgroundResource(R.drawable.women_title);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("关于我们");

		LinearLayout telphone = (LinearLayout) this.findViewById(R.id.telphone);
		telphone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showtel();
			}
		});

	}

	private void showTips() {
		AlertDialog alertDialog = new AlertDialog.Builder(Tab4Fragment.this)
				.setTitle("退出程序").setMessage("是否退出程序")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Tab4Fragment.this.finish();
						System.exit(0);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				}).create(); // 创建对话框
		alertDialog.show(); // 显示对话框
	}

	private void showtel() {
		AlertDialog alertDialog = new AlertDialog.Builder(Tab4Fragment.this)
				.setTitle("提示")
				.setMessage("是否拨打400-041-7515")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent phoneIntent = new Intent(
								"android.intent.action.CALL", Uri.parse("tel:"
										+ "400-041-7515"));
						// 鍚姩
						startActivity(phoneIntent);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create(); // 创建对话
		alertDialog.show(); // 显示对话框
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.showTips();
			return false;
		}
		return false;
	}

	final int COMMON_DIALOG = 1;

	protected Dialog onCreateDialog(int id) { // 重写onCreateDialog方法
		Dialog dialog = null; // 声明一个Dialog对象用于返回
		switch (id) { // 对id进行判断
		case COMMON_DIALOG:
			Builder b = new AlertDialog.Builder(this);
			// b.setIcon(); //设置对话框的图标
			b.setTitle("提示"); // 设置对话框的标题
			b.setMessage("是否庶拨打电话"); // 设置对话框的显示内容
			b.setPositiveButton( // 添加按钮
					"确定", new DialogInterface.OnClickListener() { // 为按钮添加监听器
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							System.exit(0);
						}
					});
			b.setNegativeButton( // 添加按钮
					"取消", new DialogInterface.OnClickListener() { // 为按钮添加监听器
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						}
					});
			dialog = b.create(); // 生成Dialog对象
			break;
		default:
			break;
		}
		return dialog; // 返回生成Dialog的对象
	}
}