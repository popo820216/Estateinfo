package cn.com.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.example.domain.Info;
import cn.com.example.utils.ImageUtils;

public class Tab3HousingInfo extends Activity {
	private Info info;
	private Bitmap tempBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab3_housinginfo_layout);

		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("资讯详情");

		ImageView back = (ImageView) this.findViewById(R.id.tab3_housing_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		Intent it = getIntent();
		String infoStr = it.getStringExtra("info");
		info = Info.convertJsonToBean(infoStr);

		String summary_txt = info.getTitle();
		TextView summary = (TextView) findViewById(R.id.text_summary);
		summary.setText(summary_txt);

		String detail_txt = info.getContent();
		TextView detail = (TextView) findViewById(R.id.detail);
		detail.setText(detail_txt);

		tempBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.pic);
		new Thread(image_run).start();
	}

	Handler image_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				ImageView image_summary = (ImageView) findViewById(R.id.image_summary);
				image_summary.setImageBitmap((Bitmap) msg.obj);
			}
		}
	};

	Runnable image_run = new Runnable() {
		@Override
		public void run() {
			Message msg = new Message();
			msg.what = 0;
			Bitmap bitmap = ImageUtils.getHttpBitmap(info.getImage_s());
			if (bitmap == null) {
				bitmap = tempBitmap;
			}
			msg.obj = bitmap;
			image_handler.sendMessage(msg);
		}
	};

}
