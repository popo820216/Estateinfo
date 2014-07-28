package cn.com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import cn.com.example.customview.CustomProgressdialog;
import cn.com.example.customview.XListView;
import cn.com.example.customview.XListView.IXListViewListener;
import cn.com.example.domain.House;
import cn.com.example.domain.Result;
import cn.com.example.domain.SearchResult;
import cn.com.example.utils.HttpAccessUtil;
import cn.com.example.utils.ImageUtils;

public class Tab2SearchResultList extends Activity implements
		IXListViewListener {
	private String[] ids = { "image_listitem", "title_listitem",
			"type_listitem", "housearea", "floorarea", "price_us", "price_cny", "image_quanxin"};
	private int[] rids = { R.id.image_listitem, R.id.title_listitem,
			R.id.type_listitem, R.id.housearea, R.id.floorarea, R.id.price_us,
			R.id.price_cny, R.id.image_quanxin};

	private XListView mListView;
	private SimpleAdapter mSimpleAdapter;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private Handler mHandler;
	private Intent it;
	private StringBuffer url = new StringBuffer();
	private TextView tv;
	private String jsonStr;
	private Bitmap tempBitmap;
	private static final int COMPLETED = 0;
	private Map<String, List<Bitmap>> bitmapMap;
	private List<Bitmap> list_bitmap;
	private int page = 1;
	private int page_next = 1;
	private int page_previous = 1;
	private List<House> house_list;
	private CustomProgressdialog progressDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2_search_result_list);

		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("房源搜索");

		tv = (TextView) findViewById(R.id.result_count);

		mListView = (XListView) findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);

		it = getIntent();

		tempBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.pic);
		bitmapMap = new HashMap<String, List<Bitmap>>();

		house_list  = new ArrayList<House>();
		list_bitmap = new ArrayList<Bitmap>();
		
		
		setUrl();
		url.append("&page=1");

		progressDialog = CustomProgressdialog.createDialog(this);
		// progressDialog.setMessage("正在加载中...");
		progressDialog.show();
		new Thread(getDataStr).start();

		mHandler = new Handler();
		// mAdapter = new ArrayAdapter<String>(this, R.layout.table1listitem,
		// items);
		// mListView.setAdapter(mAdapter);
		// mListView.setPullLoadEnable(false);
		// mListView.setPullRefreshEnable(false);

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == COMPLETED) {
				getData();
				new Thread(preparedBitmap).start();
				;
			} else {
				stopProgressdialog();
			}
		}

		Handler handler_bitmap = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == COMPLETED) {
					for (Map<String, Object> data_ : data) {
						String id = (data_.get("hid")).toString();
						List<Bitmap> list = bitmapMap.get(id);
						if (list == null || list.isEmpty()){
							System.out.println(id);
						}else {
							data_.put("image_listitem", list.get(0));
							data_.put("image_quanxin", list.get(1));
							mSimpleAdapter.notifyDataSetChanged();
						}
					}
				}
			};
		};

		Runnable preparedBitmap = new Runnable() {
			public void run() {
				SearchResult sr = SearchResult.convertJsonToBean(jsonStr);
				if (sr == null || sr.getResult() == null
						|| sr.getResult().getData().isEmpty())
					return;
				Map<String, String> params = new HashMap<String, String>();

				for (House house : sr.getResult().getData()) {
					if (house.getHid() == null)
						continue;
					Bitmap temp = null;
					try {
						temp = ImageUtils.getHttpBitmap(house.getImage());
					} catch (Exception e) {
						// e.printStackTrace();
					}
					if (temp == null)
						continue;
					
					Bitmap quanxin;
					if ("1".equals(house.getIsnew())){
						quanxin = BitmapFactory.decodeResource(getResources(), R.drawable.fangwu_biaoqian);
					}else {
						quanxin = BitmapFactory.decodeResource(getResources(), R.drawable.fangwu_biaoqian1);
					}
					
					List<Bitmap> list = new ArrayList<Bitmap>();
					list.add(temp);
					list.add(quanxin);
					
					bitmapMap.put(house.getHid().toString(), list);
					handler_bitmap.sendEmptyMessage(0);
				}
			};
		};
	};

	private void setUrl() {
		url = new StringBuffer();
		url.append(HttpAccessUtil.ip).append("house/house.php?op=search");

		String city = it.getStringExtra("city");
		if (!"".equals(city)) {
			url.append("&city=").append(city);
		}

		String type = it.getStringExtra("type");
		if (!"".equals(type)) {
			url.append("&type=").append(type);
		}

		String districtscore = it.getStringExtra("districtscore");
		if (!"0".equals(districtscore)) {
			url.append("&districtscore=").append(districtscore);
		}

		String regionnature = it.getStringExtra("regionnature");
		if (!"0".equals(regionnature)) {
			url.append("&regionnature=").append(regionnature);
		}

		String swimmingpool = it.getStringExtra("swimmingpool");
		if (!"-1".equals(swimmingpool)) {
			url.append("&swimmingpool=").append(swimmingpool);
		}

		String rprice = it.getStringExtra("rprice");
		String lprice = it.getStringExtra("lprice");
		if (!"0".equals(rprice)) {
			url.append("&lprice=").append(lprice).append("&rprice=")
					.append(rprice);

		}
	}

	Runnable getDataStr = new Runnable() {
		public void run() {
			jsonStr = HttpAccessUtil.connServerForResult(url.toString());
			Message msg = new Message();
			msg.what = COMPLETED;
			handler.sendMessage(msg);
		};
	};

	private void getData() {
		tv.setVisibility(View.INVISIBLE);
		SearchResult sr = SearchResult.convertJsonToBean(jsonStr);

		if (sr == null) {
			sr = new SearchResult();
		}

		Result rs = sr.getResult();
		if (rs != null && rs.getTotal() != 0) {
			page = rs.getPage();
			page_next = rs.getPage_next();
			page_previous = rs.getPage_previous();

			if (house_list == null)
				house_list = new ArrayList<House>();
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
				map.put("image_listitem", tempBitmap);
				map.put("title_listitem", house.getTitle());
				map.put("type_listitem", house.getType());
				map.put("housearea", house.getArea());
				map.put("floorarea", house.getCovers());
				map.put("price_us", house.getPrice());
				map.put("price_cny", house.getPrice_rmb());
				map.put("hid", house.getHid());

				data.add(map);
			}
		}

		mSimpleAdapter = new SimpleAdapter(this, data, R.layout.list_item, ids,
				rids);
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

		mListView.setAdapter(mSimpleAdapter);
		mListView.setXListViewListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				House house = house_list.get(arg2 - 1);
				if (house == null)
					return;
				Intent intent = new Intent(Tab2SearchResultList.this,
						Tab1HousingInfo.class);
				intent.putExtra("house", house.convertToString(house));
				startActivityForResult(intent, 0);
			}

		});
		stopProgressdialog();
		onLoad();
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				data.clear();
				house_list.clear();
				setUrl();
				url.append("&page=1");
				new Thread(getDataStr).start();

				// onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		if (page >= page_next) {
			onLoad();
			return;
		}
		setUrl();
		url.append("&page=").append(page_next);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				new Thread(getDataStr).start();
				mSimpleAdapter.notifyDataSetChanged();
				// onLoad();
			}
		}, 2000);
	}

	public void stopProgressdialog() {
		if (progressDialog != null) {
			progressDialog.cancel();
			progressDialog = null;
		}
	}
}
