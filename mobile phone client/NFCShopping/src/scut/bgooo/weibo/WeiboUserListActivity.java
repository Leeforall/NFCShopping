package scut.bgooo.weibo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import scut.bgooo.ui.R;
import scut.bgooo.userdb.DataHelper;
import scut.bgooo.userdb.UserInfo;
import weibo4android.User;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class WeiboUserListActivity extends Activity {

	private Button mClearList;
	private Button mDelUser;
	private Button mAddUser;
	private ListView mUserList;
	private DataHelper dataHelper;
	private List<UserInfo> mList;
	public static Weibo mWeibo;
	private RequestToken mRequestToken;
	private AccessToken mAccessToken;   
    private int defaultUser = -1;//Ĭ���û�
    public static UserInfo defaultUserInfo = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webuser);
		mClearList = (Button) findViewById(R.id.clear);
		mDelUser = (Button) findViewById(R.id.del);
		mAddUser = (Button) findViewById(R.id.add);
		mUserList = (ListView) findViewById(R.id.user);
		
		dataHelper = new DataHelper(this);//�����ݿ⡡һֱ�����activity����ʱ�Źر�
		mList = dataHelper.GetUserList(false);
		if (mList.isEmpty()) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"����δ���û�,������û���", Toast.LENGTH_SHORT);
			toast.show();
			Log.d("NFC", "AA");
		} else {
			MyAdapter myAdapter = new MyAdapter(this, mList);
			mUserList.setAdapter(myAdapter);
			mUserList.setClickable(true);
		}

		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret",
				Weibo.CONSUMER_SECRET);

		mAddUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					ConnectivityManager conn = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo net = conn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
					if (false == net.isConnectedOrConnecting()) {
						Toast toast = Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT);
						toast.show();
					} else {
						mWeibo = new Weibo();
						mRequestToken = mWeibo.getOAuthRequestToken();
						mAccessToken = null;
						String oAuthUrl = mRequestToken.getAuthenticationURL();
						Intent intent = new Intent(WeiboUserListActivity.this,
								VerifierWebViewActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("URL", oAuthUrl);
						intent.putExtras(bundle);
						Log.d("NFC", "ǰ����֤");
						startActivity(intent);
					}
					
				} catch (WeiboException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		mClearList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dataHelper.ClearUserInfo(mList);
				mList = dataHelper.GetUserList(true);

				MyAdapter myAdapter = new MyAdapter(WeiboUserListActivity.this,
						mList);
				mUserList.setAdapter(myAdapter);
				Toast toast = Toast.makeText(getApplicationContext(),
						"����δ���û�,������û���", Toast.LENGTH_SHORT);
				toast.show();
				defaultUser = -1;
			}
		});
		
		mDelUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (defaultUser >= 0) {
					UserInfo user = mList.get(defaultUser);
					dataHelper.DelUserInfo(user.GetUserId());
					mList = dataHelper.GetUserList(true);
					if (0 == mList.size()) {
						Toast toast = Toast.makeText(getApplicationContext(),
								"����δ���û�,������û���", Toast.LENGTH_SHORT);
						toast.show();
					} else if (mList.size() >=1) {
						Toast toast = Toast.makeText(getApplicationContext(),
								"��ѡ��Ĭ���û�", Toast.LENGTH_SHORT);
						toast.show();
					}
					MyAdapter myadapter = new MyAdapter(WeiboUserListActivity.this, mList);
					mUserList.setAdapter(myadapter);
					defaultUser = -1;
				} else  {
					Toast toast = Toast.makeText(getApplicationContext(),
							"��ѡ��Ĭ���û�", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		Bundle bundle = intent.getExtras();
		String Pin = bundle.getString("PIN");		
		try {
			mAccessToken = mRequestToken.getAccessToken(Pin);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//mWeibo.setToken(mAccessToken.getToken(), mAccessToken.getTokenSecret());
		
		// DataHelper dataHelper = new DataHelper(this);
		//�ж��Ƿ������ݿ����Ѿ���������û���
		if (dataHelper.HaveUserInfo(Long.toString(mAccessToken.getUserId()))) {
			Toast toast = Toast.makeText(getApplicationContext(), "���û��Ѿ���",
					Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		//��������ڣ�������һ��USERINFO�����ٴ������ݿ���
		UserInfo userInfo = new UserInfo();
		userInfo.SetAccessSecret(mAccessToken.getTokenSecret());
		userInfo.SetAccessToken(mAccessToken.getToken());
		userInfo.SetUserId(Long.toString(mAccessToken.getUserId()));

		try {
			User weiboUser = mWeibo.verifyCredentials();
			userInfo.SetLocation(weiboUser.getLocation());
			userInfo.SetUserName(weiboUser.getScreenName());
			URL url = weiboUser.getProfileImageURL();// �ü�ͷ���URL��ַ
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			InputStream inPutStream = conn.getInputStream();
			// ��ȡ��ͼƬ�Ķ���������
			byte[] data = readInputStream(inPutStream);
			userInfo.SetIcon(data);
			userInfo.SetDefault(true);//��������֤���û���ΪĬ��
			if (mList.size() != 0) {
				dataHelper.UpdateDefault(mList.get(defaultUser));
			}
			//dataHelper.SaveUserInfo(userInfo);//�Ѹ��µ�userinfo����������ݿ�
			if(dataHelper.SaveUserInfo(userInfo) == -1) {
				Toast toast = Toast.makeText(getApplicationContext(), "д������ʧ��",
						Toast.LENGTH_SHORT);
				toast.show();
			}
			//�ٸ���������
			mList = dataHelper.GetUserList(false);
			MyAdapter myAdapter = new MyAdapter(this, mList);
			mUserList.setAdapter(myAdapter);
			
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("NFC", "�ɹ���");
		
	}

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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		dataHelper.Close();
		if (mList.size() != 0) {
			defaultUserInfo = mList.get(defaultUser);		
		} else {
			defaultUserInfo = null;
		}
		Log.d("NFC", "�ر����ݿ�");
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		if (0 == mList.size()) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"����δ���û�,������û���", Toast.LENGTH_SHORT);
			toast.show();
		}
		super.onRestart();

	}

	private class MyAdapter extends BaseAdapter {

		private Context mContext; // ����������
		private List<UserInfo> mListItems; // ��Ʒ��Ϣ����
		private LayoutInflater mListContainer; // ��ͼ����

		public MyAdapter(Context context, List<UserInfo> listItems) {
			mContext = context;
			mListItems = listItems;
			mListContainer = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub		
			final int selectID = arg0;
			ViewHolder viewHolder = null;
			if (arg1 == null) {
				viewHolder = new ViewHolder();
				// ��ȡlist_item�����ļ�����ͼ
				arg1 = mListContainer.inflate(R.layout.weibouseritem, null);
				// ��ȡ�ؼ�����
				viewHolder.mUserIcon = (ImageView) arg1
						.findViewById(R.id.usericon);
				viewHolder.mUserName = (TextView) arg1
						.findViewById(R.id.username);
				viewHolder.mUserLocaton = (TextView) arg1
						.findViewById(R.id.userlocation);
				viewHolder.mCheckBox = (CheckBox) arg1
						.findViewById(R.id.checkBox1);
				// ���ÿؼ�����arg1
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			if (mList.size() != 0) {
				final UserInfo user = mList.get(selectID);
				byte[] data = user.GetIcon();
				Bitmap userIcon = BitmapFactory.decodeByteArray(data, 0,
						data.length);
				viewHolder.mUserIcon.setImageBitmap(userIcon);
				viewHolder.mUserName.setText(user.GetUserName());
				viewHolder.mUserLocaton.setText(user.GetLocationg());
				if(user.IsDefault()) {
					defaultUser = selectID;
					viewHolder.mCheckBox.setChecked(true);
				}
				viewHolder.mCheckBox
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								// TODO Auto-generated method stub
								if (isChecked) {
									Toast toast = Toast.makeText(
											getApplicationContext(), "��ѡ��"+user.GetUserName()+"ΪĬ���û�",
											Toast.LENGTH_SHORT);
									toast.show();
									if (defaultUser != -1)dataHelper.UpdateDefault(mList.get(defaultUser));									
									defaultUser = selectID;
									user.SetDefault(true);
									dataHelper.UpdateUserInfo(user);
								}
							}
						});

			}
			return arg1;

		}

		private class ViewHolder {
			public ImageView mUserIcon;// �û�ͼƬ��
			public TextView mUserName;// �û��ǳ�
			public TextView mUserLocaton;// �û�ע���ַ
			public CheckBox mCheckBox;// ��ѡ����
		}
	}
	

}


