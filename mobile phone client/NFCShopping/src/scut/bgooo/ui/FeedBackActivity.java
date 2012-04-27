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

import scut.bgooo.db.UserProfileUtil;
import scut.bgooo.entities.Profile;
import scut.bgooo.entities.Suggestion;
import scut.bgooo.entities.User;
import scut.bgooo.webservice.WebServiceUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * �û��������������ҳ��
 * 
 * @author Lee
 * @since 2012��4��9��
 * */
public class FeedBackActivity extends Activity {

	protected static final int SUCCESS = 0;
	protected static final int FAILE = 1;
	protected static final int NOUSER = 2;
	private Button mCancle;
	private Button mConfirm;
	private EditText mFeedBack;

	public ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		mCancle = (Button) findViewById(R.id.fbcancle);
		mConfirm = (Button) findViewById(R.id.fbconfirm);
		mFeedBack = (EditText) findViewById(R.id.fbedit);

		mConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mProgressDialog = new ProgressDialog(FeedBackActivity.this);
				mProgressDialog.setMessage(FeedBackActivity.this.getResources()
						.getString(R.string.app_name));
				mProgressDialog.setTitle(FeedBackActivity.this.getResources()
						.getString(R.string.app_name));
				mProgressDialog.show();
				UpdateSuggestion();
			}
		});

	}

	private void UpdateSuggestion() {
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Suggestion suggestion = new Suggestion();
				Profile user = UserProfileUtil
						.readProfile(FeedBackActivity.this);
				if (user != null) {
					suggestion.setProperty(1, user.getId());
					suggestion.setProperty(2, mFeedBack.getText().toString());
					Message msg = new Message();
					if (WebServiceUtil.getInstance().AddSuggestion(suggestion)) {
						msg.arg1 = SUCCESS;
					} else {
						msg.arg1 = FAILE;
					}
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.arg1 = NOUSER;
					handler.sendMessage(msg);
				}
			}
		});

		thread.start();
		thread = null;
	}

	final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.arg1) {
			case SUCCESS:
				Toast.makeText(getApplicationContext(), "�ύ�ɹ�", 2000).show();
				mProgressDialog.dismiss();
				finish();
				break;
			case FAILE:
				Toast.makeText(getApplicationContext(), "�ύʧ��", 2000).show();
				mProgressDialog.dismiss();
				break;
			case NOUSER:
				Toast.makeText(getApplicationContext(), "���ȵ�¼", 2000).show();
				mProgressDialog.dismiss();
				break;
			}

		}
	};
}
