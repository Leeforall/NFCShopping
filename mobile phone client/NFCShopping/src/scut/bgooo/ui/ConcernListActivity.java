/*
 * Copyright (C) 2012 The Team of BGOOO
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package scut.bgooo.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import scut.bgooo.concern.ConcernItem;
import scut.bgooo.concern.ConcernItemAdapter;
import scut.bgooo.concern.ConcernManager;
import scut.bgooo.entities.Product;
import scut.bgooo.entities.SecCategory;
import scut.bgooo.utility.TaskHandler;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author 肥哥
 * 
 * @since 2012年3月15日
 * 
 * 
 * @License: Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 * */
public class ConcernListActivity extends Activity {

	private static final String TAG = ConcernListActivity.class.getSimpleName();

	/**
	 * <p>
	 * 移除窗口的内部私有类，实现Runnable接口
	 * </p>
	 * <p>
	 * 参考API DEMO 的listview 的例子 list9
	 * </p>
	 * 
	 * @author Leeforall
	 * 
	 * @since 2012年3月19日
	 * */
	private final class RemoveWindow implements Runnable {
		public void run() {
			removeWindow();
		}
	}

	// RemoveWindow实例对象
	private RemoveWindow removeWindow = new RemoveWindow();
	// Handler实例对象
	private Handler handler = new Handler();
	// 具体看帮助文档
	private WindowManager windorManager;
	// 滚动时显示的提示TextView
	private TextView dialogText;
	// 显示控制量
	private boolean isShowing;
	private boolean isReady;
	// 滚动时显示的提示文字
	private String groupString = "";
	// 适配器对象
	private ConcernItemAdapter mConcernAdapter = null;
	// 数据访问对象
	private ConcernManager mConcernManager = null;
	// listview显示的数据
	private List<ConcernItem> mItems = null;

	private ListView mConcernList;
	private View mNodata;
	private TextView mNodataText;

	// private TextView mEmptyTextView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		setContentView(R.layout.concerns);
		mConcernList = (ListView) findViewById(R.id.concernList);
		mNodata = findViewById(R.id.nodatapopup);
		mNodataText = (TextView) mNodata.findViewById(R.id.prompt);
		mNodataText.setText("没有关注记录哦亲！！");
		this.mConcernManager = new ConcernManager(this); // 创建数据访问对象
		mItems = mConcernManager.buildConcernItems();
		mConcernAdapter = new ConcernItemAdapter(this, mItems);
		mConcernList.setAdapter(mConcernAdapter);

		mConcernList.setOnScrollListener(onScrollerListener);
		mConcernList.setOnItemClickListener(onItemClickListener);

		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dialogText = (TextView) inflate.inflate(R.layout.list_position, null);
		dialogText.setVisibility(View.INVISIBLE);
		windorManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		handler.post(runnable);
		// 注册上下文菜单
		this.registerForContextMenu(mConcernList);
	}

	/**
	 * 移除窗口的方法
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
	 * listview 滚动事件监听器
	 * */
	OnScrollListener onScrollerListener = new OnScrollListener() {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

			if (mItems.size() == 0) {
				return;// 如果没有数据，则直接跳出滚动事件的处理
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
	 * listview 每一项的点击事件监听器
	 * */
	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ConcernListActivity.this,
					ProductActivity.class);
			Product product=new Product();
			product.setProperty(1, mItems.get(position).getProductId());
			product.setProperty(3, mItems.get(position).getBarcode());
			product.setProperty(4, mItems.get(position).getName());
			product.setProperty(5, mItems.get(position).getPrice());
			product.setProperty(6, mItems.get(position).getBrand());
			product.setProperty(7, mItems.get(position).getLocation());
			SecCategory secCategory=new SecCategory();
			secCategory.setProperty(3, mItems.get(position).getSecCategory());
			product.setProperty(10, secCategory);
			product.setProperty(9, mItems.get(position).getDescription());
			intent.putExtra("product", product);
			startActivity(intent);
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "onResume");
		mItems = mConcernManager.buildConcernItems();
		// 因为没有必要重新加载adapter适配器，所以只对数据进行删除并notifyDataSetChanged()操作
		((ConcernItemAdapter) mConcernList.getAdapter()).dataSetChanged(mItems);
		if (mItems.size() != 0) {
			mNodata.setVisibility(View.GONE);
		} else {
			mNodata.setVisibility(View.VISIBLE);
		}
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
			onResume();
			break;
		case 2:
			NFCShoppingTab.mItemArray.add(mItems.get(position));
			Toast.makeText(getApplicationContext(), "已经加入对比",
					Toast.LENGTH_SHORT).show();
			break;
		}
		return true;

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		windorManager.removeView(dialogText);

		TaskHandler.getInstance().stop(); // 停止线程
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
