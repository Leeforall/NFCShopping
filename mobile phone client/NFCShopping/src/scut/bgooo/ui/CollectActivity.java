package scut.bgooo.ui;

import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CollectActivity extends ListActivity {


	private List<Map<String, Object>> mList = null;// ��õ����ݼ�
	private MyAdapter mMyAdapter = null;// ��Ӧ��������
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		mMyAdapter = new MyAdapter(this, mList);

		setListAdapter(mMyAdapter);		
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CollectActivity.this, CommentListActivity.class);
				startActivity(intent);
			}
		});		
	}

	private class MyAdapter extends BaseAdapter {

		private Context mContext; // ����������
		private List<Map<String, Object>> mListItems; // ��Ʒ��Ϣ����
		private LayoutInflater mListContainer; // ��ͼ����

		public MyAdapter(Context context, List<Map<String, Object>> listItems) {
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
			final int selectID = arg0; //��ʾ�Ѿ����õ��ڼ�����
			ViewHolder viewHolder = null;
			if (arg1 == null) {   
				viewHolder = new ViewHolder();    
	            //��ȡlist_item�����ļ�����ͼ   
	            arg1 = mListContainer.inflate(R.layout.itemview, null);   
	            //��ȡ�ؼ�����   
	            viewHolder.mImageView = (ImageView)arg1.findViewById(R.id.goods_image);   
	            viewHolder.mGoodScore = (RatingBar)arg1.findViewById(R.id.score); 
	            viewHolder.mGoodsNmae = (TextView)arg1.findViewById(R.id.name); 
	            viewHolder.mGoodsPrice = (TextView)arg1.findViewById(R.id.price); 

	            //���ÿؼ�����arg1  
	            arg1.setTag(viewHolder);   
	        }else {   
	        	viewHolder = (ViewHolder)arg1.getTag();  
	        } 
			viewHolder.mImageView.setBackgroundColor(R.drawable.logo);
			viewHolder.mGoodScore.setRating(0);
			viewHolder.mGoodsNmae.setText("�ʸ���������");
			viewHolder.mGoodsPrice.setText("50000000");
			return arg1;
		}
		
		/**
		 * ÿһ��listitem��Ķ���
		 * @author �ʸ�
		 *
		 */
		private class ViewHolder {			
			public ImageView mImageView;//ͼƬ��
			public TextView mGoodsNmae;//��ʵ��Ʒ��
			public TextView mGoodsPrice;//��ʵ��Ʒ�۸�
			public RatingBar mGoodScore;//������
		}

	}
}
