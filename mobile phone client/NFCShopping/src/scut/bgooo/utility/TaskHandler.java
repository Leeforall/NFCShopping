package scut.bgooo.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import scut.bgooo.concern.ConcernItem;
import scut.bgooo.entities.Discount;
import scut.bgooo.entities.DiscountItem;
import scut.bgooo.entities.Product;
import scut.bgooo.ui.CommentActivity;
import scut.bgooo.ui.CommentListActivity;
import scut.bgooo.ui.DiscountItemListActivity;
import scut.bgooo.ui.DiscountListActivity;
import scut.bgooo.ui.ProductActivity;
import scut.bgooo.ui.WeiboUserListActivity;
import scut.bgooo.webservice.IWebServiceUtil;
import scut.bgooo.webservice.WebServiceUtil;
import scut.bgooo.weibo.WeiboUserItem;
import weibo4android.User;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.ImageItem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class TaskHandler implements Runnable {

	public boolean isrun = false;
	private static List<Task> mTaskList = new ArrayList<Task>();// �����б�
	public static HashMap<String, INFCActivity> allActivity = new HashMap<String, INFCActivity>();
	public static HashMap<Integer, Bitmap> allIcon = new HashMap<Integer, Bitmap>();

	private static TaskHandler mTaskHandler = new TaskHandler();
	public Weibo mWeibo = null;

	public static TaskHandler getInstance() {
		return mTaskHandler;
	}

	/**
	 * �ж��߳��Ƿ����ܣ��Դ��������Ƿ񴴽����߳�
	 * 
	 * */
	public boolean isRunning() {
		return isrun;
	}

	/**
	 * ֹͣ�̲߳���
	 * 
	 * */
	public void stop() {
		mTaskList.clear();
		mTaskHandler.isrun = false;
	}

	@Override
	public void run() {
		isrun = true;
		// TODO Auto-generated method stub
		while (isrun) {
			Log.d("NFC", "run");
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
			System.setProperty("weibo4j.oauth.consumerSecret",
					Weibo.CONSUMER_SECRET);
			if (!mTaskList.isEmpty()) {
				Task task = mTaskList.get(0);
				doTask(task);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void addTask(Task task) {
		mTaskList.add(task);
	}

	private void doTask(Task task) {
		switch (task.getTaskID()) {
		case Task.GET_USER_INFORMATION: {
			Log.d("NFC", Integer.toString(Task.GET_USER_INFORMATION));
			Map<String, WeiboUserItem> map = task.getTaskParam();
			WeiboUserItem weiUser = map.get("weiuser");
			mWeibo = new Weibo();
			mWeibo.setToken(weiUser.GetAToken(), weiUser.GetASecret());
			try {

				User user = mWeibo.verifyCredentials();
				URL url = user.getProfileImageURL();
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setReadTimeout(1000);
				InputStream inputstream = conn.getInputStream();
				byte[] data = readInputStream(inputstream);
				String screename = user.getScreenName();
				String location = user.getLocation();

				weiUser.SetIcon(data);
				weiUser.SetUserName(screename);
				weiUser.SetLocation(location);
				weiUser.SetDefault(true);

				Message msg = new Message();
				msg.what = Task.GET_USER_INFORMATION;
				msg.obj = weiUser;
				mHandle.sendMessage(msg);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WeiboException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			break;
		case Task.SEND_COMMENT_WEIBO: {
			Log.d("NFC", Integer.toString(Task.SEND_COMMENT_WEIBO));
			try {
				Weibo weibo = new Weibo();
				Map<String, String> m = task.getTaskParam();
				weibo.setToken(
						WeiboUserListActivity.defaultUserInfo.GetAToken(),
						WeiboUserListActivity.defaultUserInfo.GetASecret());
				String commit = m.get("COMMIT");
				weibo.updateStatus(commit);

				Message msg = new Message();
				msg.what = Task.SEND_COMMENT_WEIBO;
				msg.obj = "OK";
				mHandle.sendMessage(msg);

			} catch (WeiboException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			break;
		case Task.SEND_SHARE_WEIBO_WITH_IMAGE: {
			try {
				Log.d("WITH", "Image");
				Weibo weibo = new Weibo();
				Map<String, Object> m = task.getTaskParam();
				weibo.setToken(
						WeiboUserListActivity.defaultUserInfo.GetAToken(),
						WeiboUserListActivity.defaultUserInfo.GetASecret());
				ConcernItem item = (ConcernItem) m.get("Product");
				Message msg = new Message();
				msg.what = Task.SEND_SHARE_WEIBO_WITH_IMAGE;
				String shareStr = "����#YY����#�������������" + item.getName()
						+ "�ܲ���Ŷ���Ͻ���������!!!!!!";
				ImageItem pImage = new ImageItem(item.getIcon());
				weibo.uploadStatus(shareStr, pImage);
				msg.obj = "WITHIMAGE";
				mHandle.sendMessage(msg);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
			break;
		case Task.SEND_SHARE_WEIBO_WITHOUT_IMAGE: {
			try {
				Log.d("WITHOUT", "Image");
				Weibo weibo = new Weibo();
				Map<String, Object> m = task.getTaskParam();
				weibo.setToken(
						WeiboUserListActivity.defaultUserInfo.GetAToken(),
						WeiboUserListActivity.defaultUserInfo.GetASecret());
				ConcernItem item = (ConcernItem) m.get("Product");
				Message msg = new Message();
				msg.what = Task.SEND_SHARE_WEIBO_WITH_IMAGE;
				String shareStr = "����#YY����#�������������" + item.getName()
						+ "�ܲ���Ŷ���Ͻ���������";
				weibo.updateStatus(shareStr);
				msg.obj = "WITHOUTIMAGE";
				mHandle.sendMessage(msg);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
			break;
		case Task.GET_DISCOUNT: {
			Vector<Discount> discount = WebServiceUtil.getInstance()
					.getDiscounts();
			Message msg = new Message();
			msg.what = Task.GET_DISCOUNT;
			msg.obj = discount;
			mHandle.sendMessage(msg);
		}
			break;
		case Task.GET_DISCOUNTITEM: {
			int id = Integer.valueOf(task.getTaskParam().get("ID").toString());
			Vector<DiscountItem> discountitem = WebServiceUtil.getInstance()
					.getDiscountItems(id);
			for (int i = 0; i < discountitem.size(); i++) {
				Product product = ((Product) discountitem.get(i).getProperty(6));
				HashMap<Integer, Object> map = new HashMap<Integer, Object>();
				map.put(0, product);
				Task new_task = new Task(Task.GET_PRODUCTIMAGE, map);
				TaskHandler.addTask(new_task);
			}
			Message msg = new Message();
			msg.what = Task.GET_DISCOUNTITEM;
			msg.obj = discountitem;
			mHandle.sendMessage(msg);
		}
			break;
		case Task.GET_PRODUCTIMAGE: {
			try {
				Product product = (Product) task.getTaskParam().get(0);
				int id = Integer.valueOf(product.getProperty(1).toString());
				String URL = WebServiceUtil.ImageURL
						+ product.getProperty(8).toString();
				URL url = new URL(URL);
				Bitmap bitmap = getProductImage(url);
				allIcon.put(id, bitmap);
				Message msg = new Message();
				msg.what = Task.GET_PRODUCTIMAGE;
				mHandle.sendMessage(msg);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
			break;
		default: {

		}

		}
		mTaskList.remove(task);

	}

	// ����ˢ��UI�Ĺ���
	public Handler mHandle = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case Task.GET_USER_INFORMATION: {
				INFCActivity iwa = allActivity.get(WeiboUserListActivity.class
						.getSimpleName());
				iwa.refresh(msg.obj);
			}
				break;
			case Task.SEND_COMMENT_WEIBO: {
				INFCActivity iwa = allActivity.get(CommentActivity.class
						.getSimpleName());
				iwa.refresh(msg.obj);
			}
				break;
			case Task.SEND_SHARE_WEIBO_WITH_IMAGE: {
				INFCActivity iwa = allActivity.get(CommentListActivity.class
						.getSimpleName());
				iwa.refresh("OK", msg.obj);
			}
				break;
			case Task.SEND_SHARE_WEIBO_WITHOUT_IMAGE: {
				INFCActivity iwa = allActivity.get(ProductActivity.class
						.getSimpleName());
				iwa.refresh("OK", msg.obj);
			}
				break;
			case Task.GET_DISCOUNT: {
				INFCActivity iwa = allActivity.get(DiscountListActivity.class
						.getSimpleName());
				iwa.refresh("OK", msg.obj);
			}
				break;
			case Task.GET_DISCOUNTITEM: {
				INFCActivity iwa = allActivity
						.get(DiscountItemListActivity.class.getSimpleName());
				iwa.refresh("OK", msg.obj);
			}
				break;
			case Task.GET_PRODUCTIMAGE: {
				INFCActivity iwa = allActivity
						.get(DiscountItemListActivity.class.getSimpleName());
				iwa.refresh("IMAGE", msg.obj);
			}
				break;
			default: {

			}
			}
		}

	};

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		// ����һ��ByteArrayOutputStream
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// ����һ��������
		byte[] buffer = new byte[1024];
		int len = 0;
		// �ж������������Ƿ����-1�����ǿ�
		while ((len = inStream.read(buffer)) != -1) {
			// �ѻ�����������д�뵽������У���0��ʼ��ȡ������Ϊlen
			outStream.write(buffer, 0, len);
		}
		// �ر�������
		inStream.close();
		return outStream.toByteArray();
	}

	public Bitmap getProductImage(URL Url) {
		Bitmap bitmap = null;
		try {
			URL url = Url;
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(1000);
			InputStream inputstream = conn.getInputStream();
			byte[] data = readInputStream(inputstream);
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bitmap;
	}

}
