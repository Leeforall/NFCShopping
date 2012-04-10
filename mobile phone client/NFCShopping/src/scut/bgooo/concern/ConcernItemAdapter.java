package scut.bgooo.concern;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import scut.bgooo.ui.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ConcernItemAdapter extends BaseAdapter {

	private Context mContext; // ����������
	private List<ConcernItem> mItems; // ��Ʒ��Ϣ����

	public ConcernItemAdapter(Context context, List<ConcernItem> items) {
		this.mContext = context;
		this.mItems = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position).getId();
	}

	/**
	 * ÿһ��listitem��Ķ���
	 * 
	 * @author �ʸ�
	 * 
	 */
	private class ViewHolder {
		public ImageView mImageView;// ͼƬ��
		public TextView mGoodsNmae;// ��ʵ��Ʒ��
		public TextView mGoodsPrice;// ��ʵ��Ʒ�۸�
		public RatingBar mGoodScore;// ������
	}

	private ViewHolder vh = new ViewHolder();

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View concernitem = null;
		if (mItems.get(position).getId() == 0) {
			concernitem = LayoutInflater.from(mContext).inflate(
					R.layout.datetagitemview, null);

			TextView tv = (TextView) concernitem.findViewById(R.id.tvDatetag);

			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			Date scanDate = new Date(mItems.get(position).getTimestamp());

			String date = formater.format(scanDate);
			tv.setText(date);
		} else {
			concernitem = LayoutInflater.from(mContext).inflate(
					R.layout.productitem, null);

			vh.mImageView = (ImageView) concernitem
					.findViewById(R.id.goods_image);
			vh.mGoodScore = (RatingBar) concernitem.findViewById(R.id.score);
			vh.mGoodsNmae = (TextView) concernitem.findViewById(R.id.name);
			vh.mGoodsPrice = (TextView) concernitem.findViewById(R.id.price);

			ConcernItem item = mItems.get(position);
			byte [] data = item.getIcon();
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			vh.mImageView.setImageBitmap(bitmap);
			vh.mGoodScore.setRating(item.getRating());
			vh.mGoodsNmae.setText(item.getName());
			vh.mGoodsPrice.setText(String.valueOf(item.getPrice()));

		}
		return concernitem;
	}

	/**
	 * ��ĳЩ���ݱ仯��ʱ��֪ͨ��ı�
	 * 
	 * */
	public void dataSetChanged(List<ConcernItem> items) {
		this.mItems = items;
		this.notifyDataSetChanged();
	}

	/**
	 * �Ƴ���λ�õ���
	 */
	public void removeItem(int position) {
		mItems.remove(position);
		this.notifyDataSetChanged();
	}

	// Ĭ��������������������Ƿָ��������true
	// �ָ�������ѡ�к��޵���¼���
	// ˵���ˣ����벻��Ѹ�position����ָ�������Ļ��ͷ���false�����򷵻�true
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		if (mItems.get(position).getId() == 0) {
			return false;
		}
		return true;
	}
}
