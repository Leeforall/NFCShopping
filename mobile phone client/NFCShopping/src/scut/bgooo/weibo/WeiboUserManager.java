package scut.bgooo.weibo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import scut.bgooo.db.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

public class WeiboUserManager {

	private SQLiteDatabase db;
	private DBHelper dbHelper;

	public WeiboUserManager(Context context) {
		dbHelper = new DBHelper(context, DBHelper.DB_NAME, null,
				DBHelper.DB_VERSION);
	}

	public void Close() {
		db.close();
		// dbHelper.close();
	}

	// ��ȡusers���е�UserID��Access Token��Access Secret�ļ�¼
	public List<WeiboUserItem> GetUserList(Boolean isSimple) {
		List<WeiboUserItem> userList = new ArrayList<WeiboUserItem>();
		db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(DBHelper.WIBO_TB_NAME, null, null, null, null,
				null, WeiboUserItem.ID + " DESC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
			WeiboUserItem user = new WeiboUserItem();
			user.SetId(cursor.getInt(0));
			user.SetUserId(cursor.getString(1));
			user.SetAccessToken(cursor.getString(2));
			user.SetAccessSecret(cursor.getString(3));
			if (1 == Integer.parseInt(cursor.getString(6))) {
				user.SetDefault(true);
			} else {
				user.SetDefault(false);
			}
			if (!isSimple) {
				user.SetUserName(cursor.getString(4));
				user.SetLocation(cursor.getString(5));
				user.SetIcon(cursor.getBlob(7));
			}
			userList.add(user);
			cursor.moveToNext();
		}
		cursor.close();
		Close();
		return userList;
	}

	// �ж�users���е��Ƿ����ĳ��UserID�ļ�¼
	public Boolean HaveUserInfo(String UserId) {
		Boolean b = false;
		db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(DBHelper.WIBO_TB_NAME, null,
				WeiboUserItem.USERID + "=" + UserId, null, null, null, null);
		b = cursor.moveToFirst();
		Log.e("HaveUserInfo", b.toString());
		cursor.close();
		return b;
	}

	// ����users��ļ�¼������UserId�����û��ǳƺ��û�ͼ��
	public int UpdateUserInfo(String userName, Bitmap userIcon, String UserId,
			String Loaction) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(WeiboUserItem.USERNAME, userName);
		// BLOB����
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		// ��Bitmapѹ����PNG���룬����Ϊ100%�洢
		userIcon.compress(Bitmap.CompressFormat.PNG, 100, os);
		// ����SQLite��Content��������Ҳ����ʹ��raw
		values.put(WeiboUserItem.USERICON, os.toByteArray());
		values.put(WeiboUserItem.USERLOCATION, Loaction);
		int id = db.update(DBHelper.WIBO_TB_NAME, values, WeiboUserItem.USERID
				+ "=" + UserId, null);
		Log.e("UpdateUserInfo2", id + "");
		Close();
		return id;
	}

	// ����users��ļ�¼���Ƕ�һ���µ��û����и���
	public int UpdateUserInfo(WeiboUserItem user) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(WeiboUserItem.USERID, user.GetUserId());
		values.put(WeiboUserItem.TOKEN, user.GetAToken());
		values.put(WeiboUserItem.TOKENSECRET, user.GetASecret());
		values.put(WeiboUserItem.ISDEFAULT, user.IsDefault());
		int id = db.update(DBHelper.WIBO_TB_NAME, values, WeiboUserItem.ID
				+ "=" + user.GetId(), null);
		Log.e("UpdateUserInfo", id + "");
		Close();
		return id;
	}

	// ���users��ļ�¼
	public long SaveUserInfo(WeiboUserItem user) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		Long uid = null;
		if (!HaveUserInfo(user.GetUserId())) {
			values.put(WeiboUserItem.USERID, user.GetUserId());
			values.put(WeiboUserItem.TOKEN, user.GetAToken());
			values.put(WeiboUserItem.TOKENSECRET, user.GetASecret());
			values.put(WeiboUserItem.USERNAME, user.GetUserName());
			values.put(WeiboUserItem.USERICON, user.GetIcon());
			values.put(WeiboUserItem.USERLOCATION, user.GetLocationg());
			values.put(WeiboUserItem.ISDEFAULT, user.IsDefault());
			uid = db.insert(DBHelper.WIBO_TB_NAME, WeiboUserItem.ID, values);
			Log.e("SaveUserInfo", uid + "");
		}
		Close();
		return uid;
	}

	// ɾ��users��ļ�¼
	public int DelUserInfo(String UserId) {
		db = dbHelper.getWritableDatabase();
		int id = db.delete(DBHelper.WIBO_TB_NAME, WeiboUserItem.USERID + "="
				+ UserId, null);
		Log.e("DelUserInfo", id + "");
		Close();
		return id;
	}

	public void ClearUserInfo(List<WeiboUserItem> userlist) {
		for (int i = 0; i < userlist.size(); i++) {
			WeiboUserItem user = userlist.get(i);
			String userId = user.GetUserId();
			DelUserInfo(userId);
		}
	}

	public void UpdateDefault(WeiboUserItem user) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(WeiboUserItem.ISDEFAULT, false);
		db.update(DBHelper.WIBO_TB_NAME, values,
				WeiboUserItem.ID + "=" + user.GetId(), null);
		Close();
	}
}
