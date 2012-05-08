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
package scut.bgooo.entities;

import java.io.Serializable;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 
 * ����Ŀ¼��
 * 
 * 2012��4��1��21:37:21 ���Գɹ�
 * */
public class SecCategory implements KvmSerializable,Serializable {

	private int SecCategoryID;
	private int CategoryID;
	private String SecCategoryName;
	private String EntityKey;

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		Object res = null;
		switch (arg0) {
		case 0:
			res = this.EntityKey;
			break;
		case 1:
			res = this.SecCategoryID;
			break;
		case 2:
			res = this.CategoryID;
			break;
		case 3:
			res = this.SecCategoryName;
			break;	
		default:
			break;
		}
		return res;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "EntityKey";
			break;
		case 1:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "secCategoryID";
			break;
		case 2:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "categoryID";
			break;
		case 3:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "secCategoryName";
			break;		
		default:
			break;
		}
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 == null)
			return;
		switch (arg0) {
		case 0:
			this.EntityKey=arg1.toString();
			break;
		case 1:
			this.SecCategoryID = Integer.valueOf(arg1.toString());
			break;
		case 2:
			this.CategoryID = Integer.valueOf(arg1.toString());
			break;
		case 3:
			this.SecCategoryName = arg1.toString();
			break;
		default:
			break;
		}
	}

}
