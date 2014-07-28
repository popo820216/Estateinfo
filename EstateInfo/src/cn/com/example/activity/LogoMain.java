package cn.com.example.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class LogoMain extends Activity {

	private ViewPager mPager;
	// 包裹滑动图片的LinearLayout
	private ViewGroup viewPics;
	// 包裹小圆点的LinearLayout
	private ViewGroup viewPoints;
	// 将小圆点的图片用数组表示 
	private ImageView[] imageViews;
	private ImageView imageView;
	GuidePageAdapter GuidePageAdapter;
	FrameLayout flview;
	private int count = 0;
	private SharedPreferences preferences;
	
	int positionid = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logomain);
		viewPoints = (ViewGroup) this.findViewById(R.id.viewGroup);
		// 添加小圆点的图片
		imageViews = new ImageView[3];
		for (int i = 0; i < 3; i++) {
			imageView = new ImageView(this);
			// 设置小圆点imageview的参数
			imageView.setLayoutParams(new LayoutParams(15, 15));// 创建一个宽高均为20
																// 的布局
			imageView.setPadding(20, 0, 20, 0);
			// 将小圆点layout添加到数组中
			imageViews[i] = imageView;

			// 默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.drawable.lan);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.hui);
			}
			// 将imageviews添加到小圆点视图组
			viewPoints.addView(imageViews[i]);
		}
		
		preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
        count = preferences.getInt("count", 0);
		Log.v("info", "count: "+count);
        System.out.println(count);
        if (count == 0){
        	flview = (FrameLayout)this.findViewById(R.id.flview);
    		flview.setVisibility(ViewGroup.GONE);
    		mPager = (ViewPager) this.findViewById(R.id.guidePages);
    		mPager.setAdapter(GuidePageAdapter = new GuidePageAdapter(this));
    		mPager.setOnPageChangeListener(new GuidePageChangeListener());
    		
    		Button gonext = (Button)this.findViewById(R.id.gonext);
    		gonext.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				if(positionid == 2){
    					Intent intent = new Intent(LogoMain.this,MainActivity.class);
    			    	startActivity(intent);
    			    	finish();
    				}
    			}
    		});
        }
        
		
		
		 new Handler().postDelayed(new Runnable(){  
		     public void run() {  
		     //execute the task  
//		    	 Intent intent = new Intent(LogoMain.this,MainActivity.class);
//		    	 startActivity(intent);
	    	 	if (count == 0){
	    	 		Editor editor = preferences.edit();
	    	        editor.putInt("count", ++count);
	    	        editor.commit();
	    	 		flview.setVisibility(ViewGroup.VISIBLE);
		    	 }else{
		    		Intent intent = new Intent(LogoMain.this,MainActivity.class);
 			    	startActivity(intent);
 			    	finish();
		    	 }
		     }  
		  }, 3000); 
	}
	
	class GuidePageAdapter extends PagerAdapter {
		Context context;
		public GuidePageAdapter(Context context) {
			this.context = context;
		}

		// 销毁position位置的界面
		@Override
		public void destroyItem(View v, int position, Object arg2) {
			// TODO Auto-generated method stub
			//((ViewPager) v).removeView((View) arg2);

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		// 获取当前窗体界面数
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
			// return 0;
		}

		// 初始化position位置的界面
		@Override
		public Object instantiateItem(View v, int position) {
			// TODO Auto-generated method stub
			System.out.println("position::" + position);
//			((ViewPager) v).addView(pageViews.get(position));
//			return pageViews.get(position);
			ImageView iv = new ImageView(context);
			switch (position) {
			case 0:
				iv.setBackgroundResource(R.drawable.hanyingye_01);
				break;
			case 1:
				iv.setBackgroundResource(R.drawable.hanyingye_02);
				break;
			case 2:
				iv.setBackgroundResource(R.drawable.hanyingye_03);
				break;
			default:
				break;
			}
			
			((ViewPager) v).addView(iv);
			return iv;
		}

		// 判断是否由对象生成界面
		@Override
		public boolean isViewFromObject(View v, Object arg1) {
			// TODO Auto-generated method stub
			return v == arg1;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			positionid = position;
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[position].setBackgroundResource(R.drawable.lan);
				// 不是当前选中的page，其小圆点设置为未选中的状态
				if (position != i) {
					imageViews[i].setBackgroundResource(R.drawable.bai);
				}
			}

		}
	}
	
	private void showTips(){
		AlertDialog alertDialog = new AlertDialog.Builder(LogoMain.this)
		.setTitle("退出程序")
		.setMessage("是否退出程序")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which){
				LogoMain.this.finish();
				System.exit(0);
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which){
			return;
		}})
		.create();  //创建对话框
		alertDialog.show(); // 显示对话框
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
			this.showTips();
			return false;
		}
		return false;
	}
}
