package cn.com.example.utils;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class ImageUtils {
	public static Bitmap getHttpBitmap(String url) {
		URL file = null;
		Bitmap bitmap = null;
		try {
			file = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) file.openConnection();
			conn.setConnectTimeout(0);
			conn.setDoInput(true);
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static Map<String, Bitmap> getHttpBitmaps(Map<String, String> list) {
		Map<String, Bitmap> result = new HashMap<String, Bitmap>();

		for (Entry<String, String> entry : list.entrySet()) {
			URL file = null;
			Bitmap bitmap = null;

			String id = entry.getKey();
			String url = entry.getValue();
			try {
				file = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) file
						.openConnection();
				conn.setConnectTimeout(0);
				conn.setDoInput(true);
				InputStream is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.put(id, bitmap);
		}
		return result;
	}

	private HashMap<String, SoftReference<Drawable>> imageCache;

	public ImageUtils() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	/**
	 * ����ͼƬ �ⲿ�������
	 * 
	 * @param imageUrl
	 * @param imageCallback
	 * @return
	 */
	public Drawable loadDrawable(final String imageUrl,
			final ImageCallback imageCallback) {
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
			}
		};
		new Thread() {
			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		return null;
	}

	public static Drawable loadImageFromUrl(String url) {
		URL m;
		InputStream i = null;
		Drawable d = null;
		try {
			m = new URL(url);
			System.out.println(url);
			i = (InputStream) m.getContent();
			d = Drawable.createFromStream(i, "src");
			i.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}
}
