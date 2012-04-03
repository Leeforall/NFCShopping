package scut.bgooo.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scut.bgooo.ui.CommentActivity;
import scut.bgooo.weibo.WeiboUserListActivity;
import scut.bgooo.weibouser.WeiboUserItem;
import weibo4android.User;
import weibo4android.Weibo;
import weibo4android.WeiboException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class TaskHandler implements Runnable {

	private boolean isrun = true;
	private static List<Task> mTaskList = new ArrayList<Task>();// �����б�
	public static HashMap<String, IWeiboActivity> allActivity = new HashMap<String, IWeiboActivity>();
	public Weibo mWeibo = null;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.d("NFC", "run");
		while (isrun) {
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
				weibo.setToken(WeiboUserListActivity.defaultUserInfo
						.GetAToken(), WeiboUserListActivity.defaultUserInfo
						.GetASecret());
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
				IWeiboActivity iwa = allActivity
						.get(WeiboUserListActivity.class.getSimpleName());
				iwa.refresh(msg.obj);
			}
				break;
			case Task.SEND_COMMENT_WEIBO: {
				IWeiboActivity iwa = allActivity.get(CommentActivity.class.getSimpleName());
				iwa.refresh(msg.obj);
			}
				break;
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
}
