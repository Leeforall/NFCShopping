package scut.bgooo.ui;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

/**
 * ���Ǳ�ϵͳ��TAB��������
 * 
 * @author �ʸ�
 * 
 */
public class MainActivity extends TabActivity {

	public View msgTitle;// ��Ϣͷ����ť
	private TabHost mTabHost;// ����tabhost

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintabs);
		_InitTabs();
		
		
	}

	/**
	 * ��ʼ��tabs
	 */
	private void _InitTabs() {

		mTabHost = getTabHost();
		//LayoutInflater.from(this).inflate(R.layout.maintabs, mTabHost.getTabContentView(), true);
		mTabHost.addTab(mTabHost.newTabSpec("TAB_CONCERNS").setIndicator("��ע")
				.setContent(new Intent(this, ConcernsActivity.class)));
		
		mTabHost.addTab(mTabHost.newTabSpec("TAB_COMPARE").setIndicator("�Ա�")
				.setContent(new Intent(this, CompareActivity.class)));
		
		mTabHost.addTab(mTabHost.newTabSpec("TAB_COLLECT").setIndicator("�ղ�")
				.setContent(new Intent(this, CollectActivity.class)));
		
		mTabHost.addTab(mTabHost.newTabSpec("TAB_DISCOUNT").setIndicator("�Ż�")
				.setContent(new Intent(this, DiscountListActivity.class)));
		
		mTabHost.addTab(mTabHost.newTabSpec("TAB_MORE").setIndicator("����")
				.setContent(new Intent(this, MoreActivity.class)));
	}
}