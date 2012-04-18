package scut.bgooo.concern;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import scut.bgooo.ui.R;
import scut.bgooo.utility.TaskHandler;

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
	private Bitmap bitmap;
	private byte[] data;

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
		if (mItems.get(position).getId() == 0) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.datetagitemview, null);

			TextView tv = (TextView) convertView.findViewById(R.id.tvDatetag);

			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			Date scanDate = new Date(mItems.get(position).getTimestamp());

			String date = formater.format(scanDate);
			tv.setText(date);
		} else {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.productitem, null);

			vh.mImageView = (ImageView) convertView
					.findViewById(R.id.goods_image);
			vh.mGoodScore = (RatingBar) convertView.findViewById(R.id.score);
			vh.mGoodsNmae = (TextView) convertView.findViewById(R.id.name);
			vh.mGoodsPrice = (TextView) convertView.findViewById(R.id.price);

			ConcernItem item = mItems.get(position);
			if (TaskHandler.allIcon.get(item.getProductId()) == null) {
				data = item.getIcon();
				bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				TaskHandler.allIcon.put(item.getProductId(), bitmap);
			} else {
				bitmap = TaskHandler.allIcon.get(item.getProductId());
			}
			vh.mImageView.setImageBitmap(bitmap);

			vh.mGoodScore.setRating(item.getRating());
			vh.mGoodsNmae.setText(item.getName());
			DecimalFormat df = new java.text.DecimalFormat("#0.00");
			vh.mGoodsPrice.setText(df.format(item.getPrice()) + "Ԫ");

		}
		return convertView;
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
