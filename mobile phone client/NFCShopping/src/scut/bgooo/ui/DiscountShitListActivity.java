package scut.bgooo.ui;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import scut.bgooo.discount.DiscountShitItem;
import scut.bgooo.entities.Discount;
import scut.bgooo.utility.IWeiboActivity;
import scut.bgooo.utility.Task;
import scut.bgooo.utility.TaskHandler;
import scut.bgooo.webservice.WebServiceUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * ���activity��Ϊ��չʾ��ͬ�ڵ��Ż��б��
 * 
 * @author �ʸ�
 * 
 */
public class DiscountShitListActivity extends Activity implements
		IWeiboActivity {

	private MyAdapter mAdapter;
	private Vector<Discount> mDiscount = null;
	private View mProgress = null;
	private ListView mListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discountshit);
		mProgress = findViewById(R.id.progress1);
		mListView = (ListView) findViewById(R.id.discountshit_listview);
		Task task = new Task(Task.GET_DISCOUNT, null);
		TaskHandler.allActivity.put(DiscountShitListActivity.class
				.getSimpleName(), this);
		TaskHandler.addTask(task);

	}

	private class MyAdapter extends BaseAdapter {

		private Context mContext; // ����������
		private Vector<Discount> mListItems; // ��Ʒ��Ϣ����
		private LayoutInflater mListContainer; // ��ͼ����

		public MyAdapter(Context context, Vector<Discount> listItems) {
			mContext = context;
			mListItems = listItems;
			mListContainer = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mListItems.size();
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
			final int selectID = arg0; // ��ʾ�Ѿ����õ��ڼ�����
			ViewHolder viewHolder = null;
			if (arg1 == null) {
				viewHolder = new ViewHolder();
				// ��ȡlist_item�����ļ�����ͼ
				arg1 = mListContainer.inflate(R.layout.discountshititem, null);
				// ��ȡ�ؼ�����
				viewHolder.mImageView = (ImageView) arg1
						.findViewById(R.id.discount_image);
				viewHolder.mDiscountDiscription = (TextView) arg1
						.findViewById(R.id.discount_discription);
				viewHolder.mDiscountTime = (TextView) arg1
						.findViewById(R.id.discount_time);
				// ���ÿؼ�����arg1
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}

			Discount discount = mListItems.get(selectID);
			viewHolder.mDiscountTime
					.setText(discount.getProperty(3).toString());
			viewHolder.mDiscountDiscription.setText(discount.getProperty(2)
					.toString());

			return arg1;
		}

		/**
		 * ÿһ��listitem��Ķ���
		 * 
		 * @author �ʸ�
		 * 
		 */
		private class ViewHolder {
			public ImageView mImageView;// �Ż���ͼƬʹ��
			public TextView mDiscountDiscription;// �Ż�������
			public TextView mDiscountTime;// �Ż���ʱ��
		}

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		String result = (String) param[0];
		if (result.equals("OK")) {
			mProgress.setVisibility(View.GONE);
			mDiscount = (Vector<Discount>) param[1];
			mAdapter = new MyAdapter(this, mDiscount);
			mListView.setAdapter(mAdapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(DiscountShitListActivity.this,
							DiscountListActivity.class);
					Discount discount = mDiscount.get(arg2);
					String id = discount.getProperty(1).toString();
					Bundle bundle = new Bundle();
					bundle.putString("ID", id);
					intent.putExtra("ID", bundle);
					startActivity(intent);
				}
			});

		}

	}
}
