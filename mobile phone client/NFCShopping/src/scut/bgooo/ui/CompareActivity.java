package scut.bgooo.ui;

import java.util.ArrayList;

import scut.bgooo.concern.ConcernItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

public class CompareActivity extends Activity {

	private ListView mCompareList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compare);

		/*
		 * http://blog.csdn.net/hellogv/article/details/6075014
		 * �о�������ƵĹ���ʵ�֡��βο�������������������������
		 */
		mCompareList = (ListView) findViewById(R.id.lvCompare);

		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		ArrayList<TableRow> table = new ArrayList<TableRow>();
		TableCell[] titles = new TableCell[NFCShoppingTab.mItemArray.size() + 1];// ÿ�и��ݶԱ���Ŀ������Ԫ

		int width = this.getWindowManager().getDefaultDisplay().getWidth() / 2;
		// ��һ��
		titles[0] = new TableCell("����", width + 8 * 0,
				LayoutParams.FILL_PARENT, TableCell.STRING);
		// �������
		for (int i = 1; i < titles.length; i++) {
			titles[i] = new TableCell("��Ʒ" + String.valueOf(i), width,
					LayoutParams.FILL_PARENT, TableCell.STRING);
		}
		table.add(new TableRow(titles));

		if (NFCShoppingTab.mItemArray.size() > 0) {

			for (int i = 0; i < ConcernItem.getCount(); i++) {
				TableCell[] cells = new TableCell[NFCShoppingTab.mItemArray
						.size() + 1];// ÿ��5����Ԫ
				switch (i) {
				case 0:
					cells[0] = new TableCell("��ƷͼƬ", titles[0].width,
							LayoutParams.FILL_PARENT, TableCell.STRING);
					break;
				case 1:
					cells[0] = new TableCell("��Ʒ��", titles[0].width,
							LayoutParams.FILL_PARENT, TableCell.STRING);
					break;
				case 2:
					cells[0] = new TableCell("Ʒ��", titles[0].width,
							LayoutParams.FILL_PARENT, TableCell.STRING);
					break;
				case 3:
					cells[0] = new TableCell("�۸�(Ԫ)", titles[0].width,
							LayoutParams.FILL_PARENT, TableCell.STRING);
					break;
				case 4:
					cells[0] = new TableCell("����(5����)", titles[0].width,
							LayoutParams.FILL_PARENT, TableCell.STRING);
					break;
				case 5:
					cells[0] = new TableCell("���", titles[0].width,
							LayoutParams.FILL_PARENT, TableCell.STRING);
					break;
				case 6:
					cells[0] = new TableCell("����", titles[0].width,
							LayoutParams.FILL_PARENT, TableCell.STRING);
					break;
				case 7:
					cells[0] = new TableCell("��ϸ����", titles[0].width,
							LayoutParams.FILL_PARENT, TableCell.STRING);
					break;
				default:
					cells[0] = new TableCell("����", titles[0].width,
							LayoutParams.FILL_PARENT, TableCell.STRING);
					break;
				}
				for (int j = 1; j < cells.length; j++) {
					if (i == 0) {
						cells[j] = new TableCell(NFCShoppingTab.mItemArray
								.get(j - 1)// ע��Ҫ��ȥ1
								.getAttribute(i), titles[j].width,
								LayoutParams.FILL_PARENT, TableCell.IMAGE);
					} else {
						cells[j] = new TableCell(NFCShoppingTab.mItemArray
								.get(j - 1)// ע��Ҫ��ȥ1
								.getAttribute(i).toString(), titles[j].width,
								LayoutParams.FILL_PARENT, TableCell.STRING);
					}

				}
				table.add(new TableRow(cells));
			}

			TableAdapter tableAdapter = new TableAdapter(this, table);
			mCompareList.setAdapter(tableAdapter);
			mCompareList.setOnItemClickListener(new ItemClickEvent());
		}

		super.onResume();
	}

	class ItemClickEvent implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Toast.makeText(CompareActivity.this,
					"ѡ�е�" + String.valueOf(arg2) + "��", 500).show();
		}
	}

}
