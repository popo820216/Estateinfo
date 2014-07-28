package cn.com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import cn.com.example.customview.XListView;
import cn.com.example.customview.XListView.IXListViewListener;
import cn.com.example.domain.House;
import cn.com.example.domain.Result;
import cn.com.example.domain.SearchResult;
import cn.com.example.utils.HttpAccessUtil;
import cn.com.example.utils.ImageUtils;

public class Tab1Fragment extends Activity implements IXListViewListener {
	private String[] ids = { "image_listitem", "title_listitem",
			"type_listitem", "housearea", "floorarea", "price_us", "price_cny",
			"image_quanxin" };
	private int[] rids = { R.id.image_listitem, R.id.title_listitem,
			R.id.type_listitem, R.id.housearea, R.id.floorarea, R.id.price_us,
			R.id.price_cny, R.id.image_quanxin };

	private XListView mListView;
	private SimpleAdapter mSimpleAdapter;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private List<House> house_list = new ArrayList<House>();
	private Handler mHandler;
	int page = 1;
	int page_next;
	int page_previous;
	private Bitmap tempBitmap, tempBitmap1_quanxin;
	private Map<String, List<Bitmap>> bitmapMap;
	int type;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1_fragment_layout);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("美房源");

		mListView = (XListView) findViewById(R.id.tab1_xListView);
		mListView.setPullLoadEnable(true);

		// getData();

		mListView.setXListViewListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(Tab1Fragment.this,
						Tab1HousingInfo.class);
				House house = house_list.get(arg2 - 1);
				String str = house.convertToString(house);
				intent.putExtra("house", str);
				startActivityForResult(intent, 0);
			}

		});
		mHandler = new Handler();

		// tempBitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.pic);
		bitmapMap = new HashMap<String, List<Bitmap>>();
		// mAdapter = new ArrayAdapter<String>(this, R.layout.table1listitem,
		// items);
		// mListView.setAdapter(mAdapter);
		// mListView.setPullLoadEnable(false);
		// mListView.setPullRefreshEnable(false);
		type = 0;
		new MyThread().doStart();

	}

	private void getData(String jsonStr) {

		System.out.println("jsonStr::ss" + jsonStr);
		// Resources res = getResources();
		// for (int i = 0; i < 5; i++) {
		// Map<String, Object> map = new HashMap<String, Object>();
		// Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.pic_1);
		// map.put("image_listitem", bmp);
		// map.put("title_listitem", "纽约市 六室一厅 独立居");
		// map.put("type_listitem", "独立居住");
		// map.put("housearea", "1990米");
		// map.put("floorarea", "1990米");
		// map.put("price_us", "$ 70 dollar");
		// map.put("price_cny", "200万");
		//
		// data.add(map);
		// }

		// tv.setVisibility(View.INVISIBLE);
		SearchResult sr = SearchResult.convertJsonToBean(jsonStr);

		if (sr == null) {
			// tv.setVisibility(View.VISIBLE);
			return;
		}

		Result rs = sr.getResult();
		if (rs == null || rs.getTotal() == 0) {
			// tv.setVisibility(View.VISIBLE);
			return;
		}

		if (rs.getCount() == 0) {
			return;
		}

		// page = rs.getPage();
		page_next = rs.getPage_next();
		page_previous = rs.getPage_previous();
		house_list.addAll(rs.getData());

		int size = house_list.size();
		for (int i = 0; i < size; i++) {
			House house = house_list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			// Bitmap bmp = ImageUtils.getHttpBitmap(house.getImage());
			// ImageUtils.getHttpBitmap(house.getImage_s());
			// Bitmap bmp = bitmapMap.get(house.getId().toString());
			// if (bmp == null)
			// map.put("image_listitem", tempBitmap);
			// else
			// map.put("image_listitem", bmp);

			map.put("image_quanxin", tempBitmap1_quanxin);
			map.put("image_listitem", tempBitmap);
			map.put("title_listitem", house.getTitle());
			map.put("type_listitem", house.getType());
			map.put("housearea", house.getArea());
			map.put("floorarea", house.getCovers());
			map.put("price_us", house.getPrice());
			map.put("price_cny", house.getPrice_rmb());
			map.put("hid", house.getHid());
			// map.put("title_listitem", "纽约市 六室一厅 独立居");
			// map.put("type_listitem", "独立居住");
			// map.put("housearea", "1990米");
			// map.put("floorarea", "1990米");
			// map.put("price_us", "$ 70 dollar");
			// map.put("price_cny", "200万");
			data.add(map);
		}
		
		handler_bitmap.sendEmptyMessage(1);
		new Thread(preparedBitmap).start();
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		// mHandler.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		data.clear();
		house_list.clear();
		// //getData();
		// System.out.println("" + HttpAccessUtil.ip
		// + "api/house/house.php?op=list&page=" + page);
		// String str = HttpAccessUtil
		// .connServerForResult(HttpAccessUtil.ip
		// + "api/house/house.php?op=list&page=" + page);
		//
		// System.out.println("第一页返回数据:" + str);
		// getData(str);
		// mSimpleAdapter.notifyDataSetChanged();
		type = 0;
		new MyThread().doStart();
		// mSimpleAdapter = new SimpleAdapter(Tab1Fragment.this, data,
		// R.layout.list_item, ids, rids);
		// mSimpleAdapter.setViewBinder(new ViewBinder() {
		//
		// @Override
		// public boolean setViewValue(View view, Object data,
		// String textRepresentation) {
		// if (view instanceof ImageView && data instanceof Bitmap) {
		// ImageView iv = (ImageView) view;
		// iv.setImageBitmap((Bitmap) data);
		// return true;
		// } else
		// return false;
		// }
		// });
		// mListView.setAdapter(mSimpleAdapter);

		// }
		// }, 2000);
	}

	@Override
	public void onLoadMore() {
		// mHandler.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// getData();
		// String str = HttpAccessUtil
		// .connServerForResult(HttpAccessUtil.ip
		// + "api/house/house.php?op=list&page=" + page);
		//
		// System.out.println("第一页返回数据:" + str);
		// getData(str);
		// mSimpleAdapter.notifyDataSetChanged();
		if (page >= page_next) {
			onLoad();
			return;
		}
		// onLoad();
		// }
		// }, 2000);
		page = page_next;
		type = 1;
		new MyThread().doStart();
	}

	private void showTips() {
		AlertDialog alertDialog = new AlertDialog.Builder(Tab1Fragment.this)
				.setTitle("退出程序").setMessage("是否退出程序")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Tab1Fragment.this.finish();
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

	ProgressDialog progressDialog = null;

	class MyThread extends Thread {

		public void doStart() {
//			progressDialog = ProgressDialog.show(Tab1Fragment.this, "提示",
//					"正在请求数据请稍等......", false);
//			progressDialog.setCancelable(true);
			this.start();
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// try {
			System.out.println("" + HttpAccessUtil.ip
					+ "house/house.php?op=list&page=" + page);
			str = HttpAccessUtil.connServerForResult(HttpAccessUtil.ip
					+ "house/house.php?op=list&page=" + page);

			System.out.println("第一页返回数据:" + str);

			getData(str);

			// }
			// finally {
//			progressDialog.dismiss();
			// progressDialog = null;
			// }
		}
	}

	String str;
	Runnable preparedBitmap = new Runnable() {
		public void run() {
			SearchResult sr = SearchResult.convertJsonToBean(str);
			if (sr == null || sr.getResult() == null
					|| sr.getResult().getData().isEmpty())
				return;

			for (House house : sr.getResult().getData()) {
				if (house.getHid() == null)
					continue;
				List<Bitmap> listbitmap = new ArrayList<Bitmap>();
				Bitmap temp = ImageUtils.getHttpBitmap(house.getImage());
				Bitmap qunxin;
				if ("1".equals(house.getIsnew())) {
					System.out.println("人椒全新");
					qunxin = BitmapFactory.decodeResource(getResources(),
							R.drawable.fangwu_biaoqian);
				} else {
					System.out.println("伙不是全新");
					qunxin = BitmapFactory.decodeResource(getResources(),
							R.drawable.fangwu_biaoqian1);
				}

				listbitmap.add(temp);
				listbitmap.add(qunxin);
				bitmapMap.put(house.getHid().toString(), listbitmap);
				handler_bitmap.sendEmptyMessage(0);

			}
		};
	};
	private static final int COMPLETED = 0;
	Handler handler_bitmap = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == COMPLETED) {
				for (Map<String, Object> data_ : data) {
					String hid = (data_.get("hid")).toString();
					List<Bitmap> list = bitmapMap.get(hid);
					if (list == null || list.isEmpty()){
						System.out.println(hid);
					}else{
						data_.put("image_listitem", list.get(0));
						data_.put("image_quanxin", list.get(1));
						mSimpleAdapter.notifyDataSetChanged();
					}
				}
			} else if (msg.what == 1) {
				mSimpleAdapter = new SimpleAdapter(Tab1Fragment.this, data,
						R.layout.list_item, ids, rids);
				mSimpleAdapter.setViewBinder(new ViewBinder() {

					@Override
					public boolean setViewValue(View view, Object data,
							String textRepresentation) {
						if (view instanceof ImageView && data instanceof Bitmap) {
							ImageView iv = (ImageView) view;
							iv.setImageBitmap((Bitmap) data);
							return true;
						} else
							return false;
					}
				});
				if (type == 1) {
					mSimpleAdapter.notifyDataSetChanged();
				} else {
					mListView.setAdapter(mSimpleAdapter);
				}
				
				onLoad();
			}
		};
	};
}