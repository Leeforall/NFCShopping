package scut.bgooo.ui;

import java.util.List;
import java.util.Map;

import scut.bgooo.discount.DiscountShitItem;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * ���activity��Ϊ��չʾ��ͬ�ڵ��Ż��б��
 *
 * @author �ʸ�
 *
 */
public class DiscountShitListActivity extends ListActivity {

	private MyAdapter mAdapter;
	private List<DiscountShitItem> mList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mAdapter = new MyAdapter(this, mList);
		setListAdapter(mAdapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DiscountShitListActivity.this, DiscountListActivity.class);
				startActivity(intent);
			}
		});
		
		
	}


	private class MyAdapter extends BaseAdapter {

		private Context mContext; // ����������
		private List<DiscountShitItem> mListItems; // ��Ʒ��Ϣ����
		private LayoutInflater mListContainer; // ��ͼ����

		public MyAdapter(Context context, List<DiscountShitItem> listItems) {
			mContext = context;
			mListItems = listItems;
			mListContainer = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 10;
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
				viewHolder.mDiscountQuantity = (TextView)arg1.findViewById(R.id.discount_quantity);
				viewHolder.mDiscountTime = (TextView)arg1.findViewById(R.id.discount_time);
				// ���ÿؼ�����arg1
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}

		
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
			public TextView mDiscountQuantity;// �Ż�������
			public TextView mDiscountTime;// �Ż���ʱ��
		}

	}
}
