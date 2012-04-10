package scut.bgooo.concern;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import scut.bgooo.db.DBHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * ��ע�б����ݹ����࣬�������ݿ�Ĳ�ɾ�Ĳ�
 * 
 * @since 2012��3��16��
 * 
 * */
public final class ConcernManager {

	private static final String TAG = ConcernManager.class.getSimpleName();

	private static final int MAX_ITEMS = 100;

	private static final short COLLECTED = 1;

	private static final String[] COLUMNS = { DBHelper.ID_COL,
			DBHelper.PRODUCT_ID_COL, DBHelper.NAME_COL, DBHelper.TYPE_COL,
			DBHelper.PRICE_COL, DBHelper.RATING_COL, DBHelper.TIMESTAMP_COL,
			DBHelper.ISCOLLECTED_COL, DBHelper.BRAND_COL,
			DBHelper.LOCATION_COL, DBHelper.BARCODE_COL,
			DBHelper.SECCATEGORY_COL, DBHelper.DESCRIPTION_COL , DBHelper.CONCERN_PRODUCTIMAGE};

	private static final String[] COUNT_COLUMN = { "COUNT(1)" };

	private static final String[] ID_COL_PROJECTION = { DBHelper.ID_COL };

	private static final String[] PRODUCT_ID_COL_PROJECTION = { DBHelper.PRODUCT_ID_COL };

	private final Activity activity;

	public ConcernManager(Activity activity) {
		this.activity = activity;
	}

	/**
	 * �ж��ֻ��й�ע��Ʒ���ݿ������Ƿ�Ϊ��
	 * 
	 * @return ��������Ϊtrue�� û������Ϊfalse
	 * 
	 * @since 2012��3��17��
	 * @author Leeforall
	 * */
	public boolean hasConcernItems() {
		Log.d(TAG, "hasConcernItems()");
		SQLiteOpenHelper helper = new DBHelper(activity, DBHelper.DB_NAME,
				null, DBHelper.DB_VERSION);
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = helper.getReadableDatabase();
			cursor = db.query(DBHelper.CONCERN_TABLE_NAME, COUNT_COLUMN, null,
					null, null, null, null);
			cursor.moveToFirst();
			return cursor.getInt(0) > 0;
		} catch (Exception e) {
			return false;
		} finally {
			close(cursor, db);
		}
	}

	/**
	 * ��ѯ���������¼�ĺ���
	 * 
	 * @since 2012��3��18��
	 * 
	 * @author Leeforall
	 * */
	public List<ConcernItem> buildConcernItems() {
		Log.d(TAG, "buildConcernItems()");
		SQLiteOpenHelper helper = new DBHelper(activity, DBHelper.DB_NAME,
				null, DBHelper.DB_VERSION);
		SQLiteDatabase db = null;
		Cursor cursor = null;
		List<ConcernItem> items = new ArrayList<ConcernItem>();
		List<String> dateItems = new ArrayList<String>();
		try {
			db = helper.getReadableDatabase();
			cursor = db.query(DBHelper.CONCERN_TABLE_NAME, COLUMNS, null, null,
					null, null, DBHelper.TIMESTAMP_COL + " DESC");
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			Date scanDate;
			while (cursor.moveToNext()) {
				int id = cursor.getInt(0);
				int pid = cursor.getInt(1);
				String name = cursor.getString(2);
				int type = cursor.getInt(3);
				float price = cursor.getFloat(4);
				float rating = cursor.getFloat(5);
				long timestamp = cursor.getLong(6);
				short iscollected = cursor.getShort(7);
				String brand = cursor.getString(8);
				String location = cursor.getString(9);
				String barcode = cursor.getString(10);
				String category = cursor.getString(11);
				String description = cursor.getString(12);
				byte[] data = cursor.getBlob(13);
				// ���ʱ���ǩ�Ĵ����
				// ��������ˢ�������ڽ������ڹ���
				scanDate = new Date(timestamp);
				String date = formater.format(scanDate);
				if (!dateItems.contains(date)) {
					dateItems.add(date);
					Date tag = formater.parse(date);
					items.add(new ConcernItem(tag.getTime()));
				}
				// ���ﻹ��Ҫ����ConcernItem����ľ������Բ���ȷ��
				items.add(new ConcernItem(id, pid, name, type, category, price,
						rating, brand, location, barcode, description,
						timestamp, iscollected, data));
			}
			return items;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return items;
		} finally {
			close(cursor, db);
		}
	}

	public List<ConcernItem> buildCollectedConcernItems() {
		Log.d(TAG, "buildCollectedConcernItems()");
		SQLiteOpenHelper helper = new DBHelper(activity, DBHelper.DB_NAME,
				null, DBHelper.DB_VERSION);
		SQLiteDatabase db = null;
		Cursor cursor = null;
		List<ConcernItem> items = new ArrayList<ConcernItem>();
		List<String> dateItems = new ArrayList<String>();
		try {
			db = helper.getReadableDatabase();
			cursor = db.query(DBHelper.CONCERN_TABLE_NAME, COLUMNS,
					DBHelper.ISCOLLECTED_COL + "=?",
					new String[] { String.valueOf(COLLECTED) }, null, null,
					DBHelper.TIMESTAMP_COL + " DESC");
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			Date scanDate;
			while (cursor.moveToNext()) {
				int id = cursor.getInt(0);
				int pid = cursor.getInt(1);
				String name = cursor.getString(2);
				int type = cursor.getInt(3);
				float price = cursor.getFloat(4);
				float rating = cursor.getFloat(5);
				long timestamp = cursor.getLong(6);
				short iscollected = cursor.getShort(7);
				String brand = cursor.getString(8);
				String location = cursor.getString(9);
				String barcode = cursor.getString(10);
				String category = cursor.getString(11);
				String description = cursor.getString(12);
				byte[] data = cursor.getBlob(13);
				// ���ʱ���ǩ�Ĵ����
				// ��������ˢ�������ڽ������ڹ���
				scanDate = new Date(timestamp);
				String date = formater.format(scanDate);
				if (!dateItems.contains(date)) {
					dateItems.add(date);
					Date tag = formater.parse(date);
					items.add(new ConcernItem(tag.getTime()));
				}
								items.add(new ConcernItem(id, pid, name, type, category, price,
						rating, brand, location, barcode, description,
						timestamp, iscollected, data));
			}
			return items;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return items;
		} finally {
			close(cursor, db);
		}
	}

	public ConcernItem buildConcernItem(int number) {
		Log.d(TAG, "buildConcernItem()");
		SQLiteOpenHelper helper = new DBHelper(activity, DBHelper.DB_NAME,
				null, DBHelper.DB_VERSION);
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = helper.getReadableDatabase();
			cursor = db.query(DBHelper.CONCERN_TABLE_NAME, COLUMNS, null, null,
					null, null, DBHelper.TIMESTAMP_COL + " DESC");
			cursor.move(number + 1);
			int id = cursor.getInt(0);
			int pid = cursor.getInt(1);
			String name = cursor.getString(2);
			int type = cursor.getInt(3);
			float price = cursor.getFloat(4);
			float rating = cursor.getFloat(5);
			long timestamp = cursor.getLong(6);
			short iscollected = cursor.getShort(7);
			String brand = cursor.getString(8);
			String location = cursor.getString(9);
			String barcode = cursor.getString(10);
			String category = cursor.getString(11);
			String description = cursor.getString(12);
			byte[] data = cursor.getBlob(13);
			// ���ﻹ��Ҫ����ConcernItem����ľ������Բ���ȷ��
			return new ConcernItem(id, pid, name, type, category, price,
					rating, brand, location, barcode, description, timestamp,
					iscollected, data);
		} finally {
			close(cursor, db);
		}
	}

	/**
	 * ͨ��id����ConcernItem����
	 * 
	 * @param id
	 *            item ���id
	 * 
	 * @since 2012��3��17��
	 * 
	 * @author Leeforall
	 * */
	public ConcernItem buildConcernItemById(int id) {
		Log.d(TAG, "buildConcernItemById()");
		SQLiteOpenHelper helper = new DBHelper(activity, DBHelper.DB_NAME,
				null, DBHelper.DB_VERSION);
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = helper.getReadableDatabase();
			cursor = db.query(DBHelper.CONCERN_TABLE_NAME, COLUMNS,
					DBHelper.ID_COL + "=?",
					new String[] { String.valueOf(id) }, null, null, null);
			int pid = cursor.getInt(1);
			String name = cursor.getString(2);
			int type = cursor.getInt(3);
			float price = cursor.getFloat(4);
			float rating = cursor.getFloat(5);
			long timestamp = cursor.getLong(6);
			short iscollected = cursor.getShort(7);
			String brand = cursor.getString(8);
			String location = cursor.getString(9);
			String barcode = cursor.getString(10);
			String category = cursor.getString(11);
			String description = cursor.getString(12);
			byte[] data = cursor.getBlob(13);
			// ���ﻹ��Ҫ����ConcernItem����ľ������Բ���ȷ��
			return new ConcernItem(id, pid, name, type, category, price,
					rating, brand, location, barcode, description, timestamp,
					iscollected, data);
		} finally {
			close(cursor, db);
		}
	}

	/**
	 * <p>
	 * ɾ�����б������number��λ�õ���
	 * </p>
	 * */
	public void deleteConcernItem(int number) {
		SQLiteOpenHelper helper = new DBHelper(activity, DBHelper.DB_NAME,
				null, DBHelper.DB_VERSION);
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = helper.getReadableDatabase();
			cursor = db.query(DBHelper.CONCERN_TABLE_NAME, ID_COL_PROJECTION,
					null, null, null, null, DBHelper.TIMESTAMP_COL + " DESC");
			cursor.move(number + 1);
			db.delete(DBHelper.CONCERN_TABLE_NAME, DBHelper.ID_COL + '='
					+ cursor.getString(0), null);
		} finally {
			close(cursor, db);
		}
	}

	/**
	 * <p>
	 * ͨ����ע�б����idɾ����¼
	 * </p>
	 * */
	public void deleteConcernItemById(int id) {
		Log.d(TAG, "deleteConcernItemById()");
		SQLiteOpenHelper helper = new DBHelper(activity, DBHelper.DB_NAME,
				null, DBHelper.DB_VERSION);
		SQLiteDatabase db = null;
		try {
			db = helper.getWritableDatabase();
			db.delete(DBHelper.CONCERN_TABLE_NAME, DBHelper.ID_COL + "=?",
					new String[] { String.valueOf(id) });
		} finally {
			close(null, db);
		}
	}

	/**
	 * <p>
	 * ɾ����Ϊ��¼������ͬ����Ʒ��
	 * </p>
	 * <p>
	 * ��ע�������ȶ�λ��Ʒ����������ܻ��滻Ϊ��Ʒ���������Ż���ID
	 * </p>
	 * 
	 * */
	@SuppressWarnings("unused")
	private void deletePrevious(String name) {
		Log.d(TAG, "deletePrevious()");
		SQLiteOpenHelper helper = new DBHelper(activity, DBHelper.DB_NAME,
				null, DBHelper.DB_VERSION);
		SQLiteDatabase db = null;
		try {
			db = helper.getWritableDatabase();
			db.delete(DBHelper.CONCERN_TABLE_NAME, DBHelper.NAME_COL + "=?",
					new String[] { String.valueOf(name) });
		} finally {
			close(null, db);
		}
	}

	/**
	 * <p>
	 * ����µĹ�ע��¼
	 * </p>
	 * <p>
	 * 1. �����¼�Ѿ����ڣ���ֻ�Լ�¼���и���
	 * </p>
	 * <p>
	 * 2. �����¼�����ڣ�������µ�����
	 * </p>
	 * 
	 * @param item
	 *            �ӱ�ǩ��ȡ���µĹ�ע��¼
	 * @since 2012��3��17��
	 * @author Leeforall
	 */
	public void addConcernItem(ConcernItem item) {
		Log.d(TAG, "addConcernItem()");
		SQLiteOpenHelper helper = new DBHelper(activity, DBHelper.DB_NAME,
				null, DBHelper.DB_VERSION);
		SQLiteDatabase db = null;
		Cursor cursor = null;

		ContentValues values = new ContentValues();
		// ��ʱ��Ĭ��ֵ����
		values.put(DBHelper.NAME_COL, item.getName());
		values.put(DBHelper.PRODUCT_ID_COL, item.getProductId());
		values.put(DBHelper.TYPE_COL, item.getType());
		values.put(DBHelper.PRICE_COL, item.getPrice());
		values.put(DBHelper.RATING_COL, item.getRating());
		values.put(DBHelper.BRAND_COL, item.getBrand());
		values.put(DBHelper.LOCATION_COL, item.getLocation());
		values.put(DBHelper.BARCODE_COL, item.getBarcode());
		values.put(DBHelper.TIMESTAMP_COL, item.getTimestamp());
		values.put(DBHelper.ISCOLLECTED_COL, item.getIsCollected());
		values.put(DBHelper.SECCATEGORY_COL, item.getSecCategory());
		values.put(DBHelper.DESCRIPTION_COL, item.getDescription());
		values.put(DBHelper.CONCERN_PRODUCTIMAGE, item.getIcon());
		try {
			db = helper.getWritableDatabase();
			cursor = db.query(DBHelper.CONCERN_TABLE_NAME,
					PRODUCT_ID_COL_PROJECTION, DBHelper.PRODUCT_ID_COL + "=?",
					new String[] { String.valueOf(item.getProductId()) }, null,
					null, null);
			if (cursor.getCount() == 0) {
				// �����µĹ�ע��¼
				db.insert(DBHelper.CONCERN_TABLE_NAME, DBHelper.TIMESTAMP_COL,
						values);
			} else {
				// ��¼�������������
				db.update(DBHelper.CONCERN_TABLE_NAME, values,
						DBHelper.PRODUCT_ID_COL + "=?",
						new String[] { String.valueOf(item.getProductId()) });
			}
		} finally {
			close(null, db);
		}
	}

	/**
	 * ������ʷ��¼��������ʷ��¼�洢�����ֵ��ɾ��ʱ���������ļ�¼
	 * 
	 * @since 2012��3��17��
	 * 
	 * @author Leeforall
	 * */
	public void trimHistory() {
		Log.d(TAG, "trimHistory()");
		SQLiteOpenHelper helper = new DBHelper(activity, DBHelper.DB_NAME,
				null, DBHelper.DB_VERSION);
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = helper.getWritableDatabase();
			cursor = db.query(DBHelper.CONCERN_TABLE_NAME, ID_COL_PROJECTION,
					null, null, null, null, DBHelper.TIMESTAMP_COL + " DESC");
			cursor.move(MAX_ITEMS);
			while (cursor.moveToNext()) {
				db.delete(DBHelper.CONCERN_TABLE_NAME, DBHelper.ID_COL + '='
						+ cursor.getString(0), null);
			}
		} finally {
			close(cursor, db);
		}
	}

	/**
	 * <P>
	 * ��չ�ע�б������
	 * </P>
	 * 
	 * @since 2012��3��17��
	 * 
	 * @author Leeforall
	 * */
	public void clearConcern() {
		Log.d(TAG, "clearConcern()");
		SQLiteOpenHelper helper = new DBHelper(activity, DBHelper.DB_NAME,
				null, DBHelper.DB_VERSION);
		SQLiteDatabase db = null;
		try {
			db = helper.getWritableDatabase();
			db.delete(DBHelper.CONCERN_TABLE_NAME, null, null);
		} finally {
			close(null, db);
		}
	}

	/**
	 * �ر�Cursor��������ݿ��������
	 * 
	 * @param cursor
	 *            �α����
	 * @param db
	 *            ���ݿ��������
	 * 
	 * @since 2012��3��17��
	 * 
	 * @author Leeforall
	 * */
	private void close(Cursor cursor, SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d(TAG, "close()");
		if (cursor != null) {
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
	}
}
