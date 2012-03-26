package scut.bgooo.userdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class DataHelper {
	//���ݿ�����
    private static String DB_NAME = "mysinaweibo.db";
    //���ݿ�汾
    private static int DB_VERSION = 2;
    private SQLiteDatabase db;
    private SqliteHelper dbHelper;
    
    public DataHelper(Context context){
        dbHelper = new SqliteHelper(context,DB_NAME, null, DB_VERSION);
        db = dbHelper.getWritableDatabase();
    }
    
    public void Close()
    {
        db.close();
        dbHelper.close();
    }
    
    //��ȡusers���е�UserID��Access Token��Access Secret�ļ�¼
    public List<UserInfo> GetUserList(Boolean isSimple)
    {
        List<UserInfo> userList = new ArrayList<UserInfo>();
        Cursor cursor=db.query(SqliteHelper.TB_NAME, null, null, null, null, null, UserInfo.ID+" DESC");
        cursor.moveToFirst();
        while(!cursor.isAfterLast()&& (cursor.getString(1)!=null)){
            UserInfo user=new UserInfo();
            user.SetId(cursor.getString(0));
            user.SetUserId(cursor.getString(1));
            user.SetAccessToken(cursor.getString(2));
            user.SetAccessSecret(cursor.getString(3));
            if (1 == Integer.parseInt(cursor.getString(6))) {
            	user.SetDefault(true);
            } else {
            	user.SetDefault(false);
            }             
            if(!isSimple){
            user.SetUserName(cursor.getString(4));
            user.SetLocation(cursor.getString(5));           
            user.SetIcon(cursor.getBlob(7)); 
            }
            userList.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return userList;
    }
    
    //�ж�users���е��Ƿ����ĳ��UserID�ļ�¼
    public Boolean HaveUserInfo(String UserId)
    {
        Boolean b = false;
        Cursor cursor=db.query(SqliteHelper.TB_NAME, null, UserInfo.USERID + "=" + UserId, null, null, null,null);
        b = cursor.moveToFirst();
        Log.e("HaveUserInfo",b.toString());
        cursor.close();
        return b;
    }
    
    //����users��ļ�¼������UserId�����û��ǳƺ��û�ͼ��
    public int UpdateUserInfo(String userName, Bitmap userIcon, String UserId, String Loaction)
    {
        ContentValues values = new ContentValues();
        values.put(UserInfo.USERNAME, userName);
        // BLOB����  
        final ByteArrayOutputStream os = new ByteArrayOutputStream();  
        // ��Bitmapѹ����PNG���룬����Ϊ100%�洢          
        userIcon.compress(Bitmap.CompressFormat.PNG, 100, os);   
        // ����SQLite��Content��������Ҳ����ʹ��raw  
        values.put(UserInfo.USERICON, os.toByteArray());
        values.put(UserInfo.USERLOCATION, Loaction);
        int id= db.update(SqliteHelper.TB_NAME, values, UserInfo.USERID + "=" + UserId, null);
        Log.e("UpdateUserInfo2",id+"");
        return id;
    }
    
    //����users��ļ�¼���Ƕ�һ���µ��û����и���
    public int UpdateUserInfo(UserInfo user)
    {
        ContentValues values = new ContentValues();
        values.put(UserInfo.USERID, user.GetUserId());
        values.put(UserInfo.TOKEN, user.GetAToken());
        values.put(UserInfo.TOKENSECRET, user.GetASecret());
        values.put(UserInfo.ISDEFAULT, user.IsDefault());
        int id= db.update(SqliteHelper.TB_NAME, values, UserInfo.USERID + "=" + user.GetUserId(), null);
        Log.e("UpdateUserInfo",id+"");
        return id;
    }
    
    //���users��ļ�¼
    public long SaveUserInfo(UserInfo user)
    {
        ContentValues values = new ContentValues();
        Long uid = null;
        if (!HaveUserInfo(user.GetUserId())) {
        	values.put(UserInfo.USERID, user.GetUserId());
            values.put(UserInfo.TOKEN, user.GetAToken());
            values.put(UserInfo.TOKENSECRET, user.GetASecret());
            values.put(UserInfo.USERNAME, user.GetUserName());
            values.put(UserInfo.USERICON, user.GetIcon());
            values.put(UserInfo.USERLOCATION,user.GetLocationg());    
            values.put(UserInfo.ISDEFAULT, user.IsDefault());
            uid = db.insert(SqliteHelper.TB_NAME, UserInfo.ID, values);
            Log.e("SaveUserInfo",uid+"");
        } 
        return uid;
    }
    
    //ɾ��users��ļ�¼
    public int DelUserInfo(String UserId){
        int id=  db.delete(SqliteHelper.TB_NAME, UserInfo.USERID +"="+UserId, null);
        Log.e("DelUserInfo",id+"");
        return id;
    }
    
    public void ClearUserInfo(List<UserInfo> userlist) {
    	for (int i = 0; i < userlist.size(); i++) {
    		UserInfo user = userlist.get(i);
    		String userId = user.GetUserId();
    		DelUserInfo(userId);
    	}
    }
    
       
    public void UpdateDefault(UserInfo user) {
    	ContentValues values = new ContentValues();
    	values.put(UserInfo.ISDEFAULT, false);
    	db.update(SqliteHelper.TB_NAME, values, UserInfo.USERID + "="+user.GetUserId(), null);
    }
}
