package cn.com.example.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import cn.com.example.domain.CItem;

public class Tab2Fragment extends Activity {

	private static final String[] city_strs = new String[] { "不限", "尔湾",
			"库格牧场", "安大略", "奇诺岗", "约巴林达", "阿卡迪亚", "东谷", "钻石吧", "罗兰岗", "核桃" };
	private static final String[] price_strs = new String[] { "不限", "50万以下",
			"50万~100万", "100万~150万", "150万~200万", "200万以上" };
	private static final List<CItem> city = new ArrayList<CItem>();// {"城市一","城市二","城市三","城市四","城市五"};
	private static final List<CItem> price = new ArrayList<CItem>();// {"价格一","价格二","价格三","价格四","价格五"};
	private static final List<CItem> type = new ArrayList<CItem>();// {"公寓","别墅"};
	private static final List<CItem> score = new ArrayList<CItem>();// score =
																	// {"分数一","分数二"};
	private static final List<CItem> around = new ArrayList<CItem>();// {"华人区","白人区","混合区"};
	private static final List<CItem> pool = new ArrayList<CItem>();// {"是","否"};

	private ArrayAdapter<CItem> adapter_city;
	private ArrayAdapter<CItem> adapter_price;
	private ArrayAdapter<CItem> adapter_type;
	private ArrayAdapter<CItem> adapter_score;
	private ArrayAdapter<CItem> adapter_around;
	private ArrayAdapter<CItem> adapter_pool;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2_fragment_layout);
		initCItem();
		initView();
	}

	private void initCItem() {
		int size = city_strs.length;
		for (int i = 0; i < size; i++) {
			CItem citem_city = new CItem("" + i, city_strs[i]);
			city.add(citem_city);
		}

		CItem citem_type = new CItem("-1", "不限");
		type.add(citem_type);
		CItem citem_type0 = new CItem("0", "公寓");
		type.add(citem_type0);
		CItem citem_type1 = new CItem("1", "独立屋");
		type.add(citem_type1);

		size = price_strs.length;
		for (int i = 0; i < size; i++) {
			CItem citem_price = new CItem("" + i, price_strs[i]);
			price.add(citem_price);
		}

		CItem citem_score = new CItem("0", "不限");
		CItem citem_score0 = new CItem("6", "6及6分以下");
		CItem citem_score1 = new CItem("7", "7");
		CItem citem_score2 = new CItem("8", "8");
		CItem citem_score3 = new CItem("9", "9");
		CItem citem_score4 = new CItem("10", "10");
		score.add(citem_score);
		score.add(citem_score0);
		score.add(citem_score1);
		score.add(citem_score2);
		score.add(citem_score3);
		score.add(citem_score4);

		CItem citem_around0 = new CItem("0", "不限");
		CItem citem_around1 = new CItem("1", "华人区");
		CItem citem_around2 = new CItem("2", "白人区");

		around.add(citem_around0);
		around.add(citem_around1);
		around.add(citem_around2);

		CItem citem_pool = new CItem("-1", "不限");
		CItem citem_pool0 = new CItem("0", "不带");
		CItem citem_pool1 = new CItem("1", "带");
		pool.add(citem_pool);
		pool.add(citem_pool0);
		pool.add(citem_pool1);
	}

	private void initView() {
		// ImageView title = (ImageView)this.findViewById(R.id.title);
		// title.setBackgroundResource(R.drawable.titlie_search);

		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("房源搜索");

		Spinner spinner_city = (Spinner) findViewById(R.id.spinner_city);
		adapter_city = new ArrayAdapter<CItem>(this,
				R.layout.custom_spinner_item, city);
		adapter_city
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_city.setAdapter(adapter_city);
		spinner_city.setOnItemSelectedListener(new ItemSelectedListener());

		Spinner spinner_price = (Spinner) findViewById(R.id.spinner_price);
		adapter_price = new ArrayAdapter<CItem>(this,
				R.layout.custom_spinner_item, price);
		adapter_price
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_price.setAdapter(adapter_price);
		spinner_price.setOnItemSelectedListener(new ItemSelectedListener());

		Spinner spinner_type = (Spinner) findViewById(R.id.spinner_type);
		adapter_type = new ArrayAdapter<CItem>(this,
				R.layout.custom_spinner_item, type);
		adapter_type
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_type.setAdapter(adapter_type);
		spinner_type.setOnItemSelectedListener(new ItemSelectedListener());

		Spinner spinner_score = (Spinner) findViewById(R.id.spinner_score);
		adapter_score = new ArrayAdapter<CItem>(this,
				R.layout.custom_spinner_item, score);
		adapter_score
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_score.setAdapter(adapter_score);
		spinner_score.setOnItemSelectedListener(new ItemSelectedListener());

		Spinner spinner_around = (Spinner) findViewById(R.id.spinner_around);
		adapter_around = new ArrayAdapter<CItem>(this,
				R.layout.custom_spinner_item, around);
		adapter_around
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_around.setAdapter(adapter_around);
		spinner_around.setOnItemSelectedListener(new ItemSelectedListener());

		Spinner spinner_pool = (Spinner) findViewById(R.id.spinner_pool);
		adapter_pool = new ArrayAdapter<CItem>(this,
				R.layout.custom_spinner_item, pool);
		adapter_pool
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_pool.setAdapter(adapter_pool);
		spinner_pool.setOnItemSelectedListener(new ItemSelectedListener());

		Button submit = (Button) findViewById(R.id.search_but);
		submit.setOnClickListener(new SearchListener(this));
	}

	class ItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}

	}

	class SearchListener implements OnClickListener {
		private Context ctx;

		public SearchListener(Context ctx) {
			this.ctx = ctx;
		}

		@Override
		public void onClick(View v) {
			Intent it = new Intent(ctx, Tab2SearchResultList.class);
			Spinner spinner_city = (Spinner) findViewById(R.id.spinner_city);
			Spinner spinner_price = (Spinner) findViewById(R.id.spinner_price);
			Spinner spinner_type = (Spinner) findViewById(R.id.spinner_type);
			Spinner spinner_score = (Spinner) findViewById(R.id.spinner_score);
			Spinner spinner_around = (Spinner) findViewById(R.id.spinner_around);
			Spinner spinner_pool = (Spinner) findViewById(R.id.spinner_pool);

			CItem city_sel = (CItem) spinner_city.getSelectedItem();
			String city = city_sel.getValue();
			if (city_strs[0].equals(city))
				it.putExtra("city", "");
			else {
				try {
					city = URLEncoder.encode(city, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				it.putExtra("city", city);
			}

			String lprice = "0";
			String rprice = "0";
			CItem price_sel = (CItem) spinner_price.getSelectedItem();
			switch (price_sel.getID()) {
			case "0":
				break;
			case "1":
				lprice = "0";
				rprice = "500000";
				break;
			case "2":
				lprice = "500000";
				rprice = "1000000";
				break;
			case "3":
				lprice = "1000000";
				rprice = "1500000";
				break;
			case "4":
				lprice = "1500000";
				rprice = "2000000";
				break;
			default:
				lprice = "0";
				rprice = "500000";
			}
			it.putExtra("lprice", lprice);
			it.putExtra("rprice", rprice);

			CItem type_sel = (CItem) spinner_type.getSelectedItem();
			
			String type = type_sel.getValue();
			if (!"-1".equals(type_sel.getID())){
				try{
					type = URLEncoder.encode(type, "utf-8");
				}catch(Exception e){
					e.printStackTrace();
				}
				it.putExtra("type", type);
			}else
				it.putExtra("type", "");
			
			CItem score_sel = (CItem) spinner_score.getSelectedItem();
			it.putExtra("districtscore", score_sel.getID());

			CItem around_sel = (CItem) spinner_around.getSelectedItem();
			it.putExtra("regionnature", around_sel.getID());

			CItem swimming_sel = (CItem) spinner_pool.getSelectedItem();
			it.putExtra("swimmingpool", swimming_sel.getID());
			startActivity(it);
		}

	}

	private void showTips() {
		AlertDialog alertDialog = new AlertDialog.Builder(Tab2Fragment.this)
				.setTitle("退出程序").setMessage("是否退出程序")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Tab2Fragment.this.finish();
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.showTips();
			return false;
		}
		return false;
	}
}