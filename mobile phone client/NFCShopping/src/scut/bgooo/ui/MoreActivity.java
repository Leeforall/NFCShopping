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

import scut.bgooo.concern.ConcernManager;
import scut.bgooo.db.UserProfileUtil;
import scut.bgooo.entities.Profile;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MoreActivity extends ListActivity {

	private static final String TAG = MoreActivity.class.getSimpleName();

	private ConcernManager mConcernManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mConcernManager = new ConcernManager(this);

		// ��ʾ�Ի���
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0: {
					Intent intent = new Intent(MoreActivity.this,
							LoginActivity.class);
					startActivity(intent);

				}
					break;
				case 1: {
					Intent intent = new Intent(MoreActivity.this,
							WeiboUserListActivity.class);
					startActivity(intent);
				}
					break;
				case 2:
					builder.setMessage(R.string.msg_sure);
					builder.setCancelable(true);
					builder.setPositiveButton(R.string.btOK,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int i2) {
									mConcernManager.clearConcern();
									Log.d(TAG, "��չ�ע�б�");
									dialog.dismiss();
								}
							});
					builder.setNegativeButton(R.string.btCancel, null);
					builder.show();
					break;
				case 3: {

				}

					break;
				case 4:
					break;
				case 5: {
					Intent intent = new Intent(MoreActivity.this,
							FeedBackActivity.class);
					startActivity(intent);
				}

				}
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		ArrayAdapter<String> adapter;
		String[] login = { "��¼", "��΢���˺�", "��չ�ע�б�", "Ӧ������", "����", "����" };

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, login);
		setListAdapter(adapter);
		super.onResume();
	}
}
