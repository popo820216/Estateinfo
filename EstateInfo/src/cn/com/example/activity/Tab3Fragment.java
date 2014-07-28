package cn.com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;
import cn.com.example.customview.CustomProgressdialog;
import cn.com.example.customview.XListView;
import cn.com.example.customview.XListView.IXListViewListener;
import cn.com.example.domain.Info;
import cn.com.example.domain.Result;
import cn.com.example.domain.SearchResult;
import cn.com.example.utils.HttpAccessUtil;

public class Tab3Fragment extends Activity implements IXListViewListener {
	private String[] ids = { "title_tab3", "crtime_tab3" };
	private int[] rids = { R.id.title_tab3, R.id.crtime_tab3 };

	private XListView mListView;
	private SimpleAdapter mSimpleAdapter;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private Handler mHandler;

	private StringBuffer url;
	private String jsonStr;
	private CustomProgressdialog progressDialog;
	private List<Info> info_list;

	private int page = 0;
	private int page_next = 1;
	private int page_previous = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab3_fragment_layout);
		// ImageView title = (ImageView)this.findViewById(R.id.title);
		// title.setBackgroundResource(R.drawable.zixun);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("房产资讯");

		mListView = (XListView) findViewById(R.id.tab3_xListView);
		mListView.setPullLoadEnable(true);

		setUrl();
		url.append("&page=1");
		
		info_list = new ArrayList<Info>();
		progressDialog = CustomProgressdialog.createDialog(this);
		// progressDialog.setMessage("正在加载中...");
		progressDialog.show();
		new Thread(getDataStr).start();

		mHandler = new Handler();

	}

	private void setUrl() {
		url = new StringBuffer();
		url.append(HttpAccessUtil.ip).append("news/news.php?op=list");
	}

	Runnable getDataStr = new Runnable() {
		public void run() {
			jsonStr = HttpAccessUtil.connServerForResult(url.toString());
			Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
		};
	};

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				getData();
			} else {
				stopProgressdialog();
			}
		}

	};

	private void getData() {
		// tv.setVisibility(View.INVISIBLE);
		SearchResult sr = SearchResult.convertJsonToBean(jsonStr);

		if (sr == null) {
			// tv.setVisibility(View.VISIBLE);
			sr = new SearchResult();
		}

		Result rs = sr.getResult();
		if (rs != null && rs.getTotal() != 0) {
			// tv.setVisibility(View.VISIBLE);
			page = rs.getPage();
			page_next = rs.getPage_next();
			page_previous = rs.getPage_previous();

			if (info_list == null)
				info_list = new ArrayList<Info>();
			info_list.addAll(rs.getInfos());

			int size = info_list.size();
			for (int i = 0; i < size; i++) {
				Info info = info_list.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", info.getId());
				map.put("title_tab3", info.getContent());
				map.put("crtime_tab3", info.getCrtime());
				data.add(map);
			}
		}

		mSimpleAdapter = new SimpleAdapter(this, data, R.layout.table3listitem,
				ids, rids);
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
				Info info = info_list.get(arg2 - 1);
				if (info == null)
					return;
				Intent intent = new Intent(Tab3Fragment.this,
						Tab3HousingInfo.class);
				intent.putExtra("info", Info.convertToJson(info));
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
				info_list.clear();
				setUrl();
				url.append("&page=1");
				new Thread(getDataStr).start();
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

	private void showTips() {
		AlertDialog alertDialog = new AlertDialog.Builder(Tab3Fragment.this)
				.setTitle("退出程序").setMessage("是否退出程序")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Tab3Fragment.this.finish();
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

	private void stopProgressdialog() {
		if (progressDialog != null) {
			progressDialog.cancel();
			progressDialog = null;
		}
	}
}