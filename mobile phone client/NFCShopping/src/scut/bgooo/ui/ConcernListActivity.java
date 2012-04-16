package scut.bgooo.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import scut.bgooo.concern.ConcernItem;
import scut.bgooo.concern.ConcernItemAdapter;
import scut.bgooo.concern.ConcernManager;
import scut.bgooo.utility.TaskHandler;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author �ʸ�
 * 
 * @since 2012��3��15��
 * 
 * 
 * @License: Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 * */
public class ConcernListActivity extends ListActivity {

	private static final String TAG = ConcernListActivity.class.getSimpleName();

	/**
	 * <p>
	 * �Ƴ����ڵ��ڲ�˽���࣬ʵ��Runnable�ӿ�
	 * </p>
	 * <p>
	 * �ο�API DEMO ��listview ������ list9
	 * </p>
	 * 
	 * @author Leeforall
	 * 
	 * @since 2012��3��19��
	 * */
	private final class RemoveWindow implements Runnable {
		public void run() {
			removeWindow();
		}
	}

	// RemoveWindowʵ������
	private RemoveWindow removeWindow = new RemoveWindow();
	// Handlerʵ������
	private Handler handler = new Handler();
	// ���忴�����ĵ�
	private WindowManager windorManager;
	// ����ʱ��ʾ����ʾTextView
	private TextView dialogText;
	// ��ʾ������
	private boolean isShowing;
	private boolean isReady;
	// ����ʱ��ʾ����ʾ����
	private String groupString = "";
	// ����������
	private ConcernItemAdapter mConcernAdapter = null;
	// ���ݷ��ʶ���
	private ConcernManager mConcernManager = null;
	// listview��ʾ������
	private List<ConcernItem> mItems = null;

	// private TextView mEmptyTextView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		this.mConcernManager = new ConcernManager(this); // �������ݷ��ʶ���
		mItems = mConcernManager.buildConcernItems();
		mConcernAdapter = new ConcernItemAdapter(this, mItems);
		setListAdapter(mConcernAdapter);

		getListView().setOnScrollListener(onScrollerListener);
		getListView().setOnItemClickListener(onItemClickListener);

		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dialogText = (TextView) inflate.inflate(R.layout.list_position, null);
		dialogText.setVisibility(View.INVISIBLE);

		windorManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		handler.post(runnable);
		// ע�������Ĳ˵�
		this.registerForContextMenu(getListView());
	}
	
	

	/**
	 * �Ƴ����ڵķ���
	 * 
	 * */
	private void removeWindow() {
		if (isShowing) {
			isShowing = false;
			dialogText.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * <p>
	 * The runnable will be run on the thread to which this handler is attached.
	 * </p>
	 * */
	Runnable runnable = new Runnable() {

		public void run() {
			isReady = true;
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.TYPE_APPLICATION,
					WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
							| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					PixelFormat.TRANSLUCENT);
			windorManager.addView(dialogText, lp);

		}
	};

	/**
	 * listview �����¼�������
	 * */
	OnScrollListener onScrollerListener = new OnScrollListener() {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

			if (mItems.size() == 0) {
				return;// ���û�����ݣ���ֱ�����������¼��Ĵ���
			}

			int lastItem = firstVisibleItem + visibleItemCount - 1;
			int middleItem = (lastItem + firstVisibleItem) / 2;
			if (isReady) {

				SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
				Date scanDate = new Date(mItems.get(middleItem).getTimestamp());
				String grouping = formater.format(scanDate);

				if (!isShowing && !groupString.equals(grouping)) {
					isShowing = true;
					dialogText.setVisibility(View.VISIBLE);
				}

				dialogText.setText(grouping);
				handler.removeCallbacks(removeWindow);
				handler.postDelayed(removeWindow, 2000);
				groupString = grouping;
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		}
	};

	/**
	 * listview ÿһ��ĵ���¼�������
	 * */
	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ConcernListActivity.this,
					CommentListActivity.class);
			intent.putExtra("mItem", mItems.get(position));
			startActivity(intent);
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "onResume");
		mItems = mConcernManager.buildConcernItems();
		// ��Ϊû�б�Ҫ���¼���adapter������������ֻ�����ݽ���ɾ����notifyDataSetChanged()����
		((ConcernItemAdapter) this.getListAdapter()).dataSetChanged(mItems);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
		menu.add(Menu.NONE, position, 1, R.string.clear_one_concern);
		menu.add(Menu.NONE, position, 2, R.string.add_to_compare);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int position = item.getItemId();
		switch (item.getOrder()) {
		case 1:
			mConcernManager.deleteConcernItemById(mItems.get(position).getId());
			// ��Ϊû�б�Ҫ���¼���adapter������������ֻ�����ݽ���ɾ����notifyDataSetChanged()����
			((ConcernItemAdapter) this.getListAdapter()).removeItem(position);
			break;
		case 2:
			NFCShoppingTab.mItemArray.add(mItems.get(position));
			Toast.makeText(getApplicationContext(), "�Ѿ�����Ա�",
					Toast.LENGTH_SHORT).show();
			break;
		}
		return true;

	}

	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		windorManager.removeView(dialogText);

		TaskHandler.getInstance().stop(); // ֹͣ�߳�
		super.onDestroy();
	}
	


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
