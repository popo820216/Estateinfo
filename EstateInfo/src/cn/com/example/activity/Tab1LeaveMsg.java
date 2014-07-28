package cn.com.example.activity;

import java.net.URLEncoder;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.example.utils.HttpAccessUtil;

public class Tab1LeaveMsg extends Activity {
	private EditText et_name;
	private EditText et_phone;
	private EditText et_email;
	private EditText et_msg;

	private Map<String, Object> params;
	private StringBuffer uri;
	private Tab1LeaveMsg context;

	private String houseno;
	private String result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1_leavemsg_layout);

		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("房产资讯");

		ImageView back = (ImageView) findViewById(R.id.tab1_leavemsg_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		context = this;

		et_name = (EditText) findViewById(R.id.msg_name);
		et_phone = (EditText) findViewById(R.id.msg_phone);
		et_email = (EditText) findViewById(R.id.msg_email);
		et_msg = (EditText) findViewById(R.id.msg_message);

		
		Intent it = getIntent();

		houseno = it.getStringExtra("houseno");
		
		ImageView submit_btn = (ImageView) findViewById(R.id.msg_submit);
		submit_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				uri = new StringBuffer();
				uri.append(HttpAccessUtil.ip)
				   .append("house/favorite.php?op=add");
				
				String name = et_name.getText().toString();
				String phone = et_phone.getText().toString();
				String email = et_email.getText().toString();
				String msg = et_msg.getText().toString();
				
				if (phone == null || "".equals(phone)){
					return;
				}
//				params = new HashMap<String, Object>();
//
//				params.put("name", name);
//				params.put("phone", phone);
//				params.put("email", email);
//				params.put("msg", msg);
				
				try{
					name = URLEncoder.encode(name, "utf-8");
					msg = URLEncoder.encode(msg, "utf-8");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				uri.append("&phone=")
				   .append(phone)
				   .append("&name=")
				   .append(name)
				   .append("&msg=")
				   .append(msg)
				   .append("&houseno=")
				   .append(houseno)
				   .append("&email=")
				   .append(email);
				new Thread(submit).start();
			}
		});
	}

	Runnable submit = new Runnable() {
		@Override
		public void run() {
			result = HttpAccessUtil.connServerForResult(uri.toString());
			Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				if (result != null && result.indexOf("error") >= 0){
					Toast.makeText(Tab1LeaveMsg.this, "提交失败", 1).show();
				}else{
					Toast.makeText(Tab1LeaveMsg.this, "提交成功", 1).show();
					finish();
				}
			}
			
		};
	};

	abstract class ContextRunnable implements Runnable {
		@Override
		abstract public void run();

		private Context context;

		public ContextRunnable(Context context) {
			this.context = context;
		}
	}
}
