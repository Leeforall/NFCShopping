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
package scut.bgooo.webservice;

import java.util.List;
import java.util.Vector;

import scut.bgooo.entities.DiscountItem;
import scut.bgooo.entities.Review;
import scut.bgooo.entities.Discount;
import scut.bgooo.entities.Paging;
import scut.bgooo.entities.Product;
import scut.bgooo.entities.Suggestion;
import scut.bgooo.entities.User;

public interface IWebServiceUtil {

	/**
	 * �����Ʒ����
	 * 
	 * @return �ɹ� true ʧ�� false
	 * @author Administrator
	 */
	public boolean AddReview(Review review);

	/**
	 * ��ӷ�����Ϣ
	 * 
	 * 
	 * @return �ɹ�true ʧ��false
	 * */
	public boolean AddSuggestion(Suggestion suggestion);

	/**
	 * <p>
	 * ��¼�жϽӿ�
	 * 
	 * @return ����User����
	 * */
	public User login(String userName, String password);

	/**
	 * ע��ӿ�
	 * 
	 * @param userName
	 *            �û���
	 * @param password
	 *            ����
	 * @param gender
	 *            �Ա�
	 * */
	public User regist(String userName, String password, int gender);

	/**
	 * <p>
	 * ͨ���û���id��ȡ���û�����Ϣ
	 * <p>
	 * ����ͨ�����۵�ʱ��鿴�û�����Ϣ
	 * 
	 * @param id
	 *            �û���id
	 * @return ����User����
	 * */
	public User getUser(int id);

	/**
	 * <p>
	 * ��ȡ���µ�20���Ż���Ϣ�ӿ�
	 * 
	 * @return �Ż���Ϣ�б�
	 * 
	 * */
	public Vector<Discount> getDiscounts();

	/**
	 * <p>
	 * ���ݴ���ķ�ҳ�����ȡ�Ż���Ϣ�ӿ�
	 * 
	 * @return �Ż���Ϣ�б�
	 * */
	public Vector<Discount> getDiscounts(Paging page);

	/**
	 * <p>
	 * �����Żݵ�id��ȡ�Żݵ���ϸ��Ϣ
	 * 
	 * @param id
	 *            �Ż�id
	 * @return �Ż���Ϣ
	 * */
	public Vector<DiscountItem> getDiscountItems(int id);

	/**
	 * <p>
	 * ͨ����Ʒid��ȡ��Ʒ����
	 * 
	 * @param id
	 *            ��Ʒ��barcode ��������
	 * 
	 * @return ������Ʒ�Ķ���
	 * 
	 * */
	public Product getProductByBarcode(String barcode);

	/**
	 * <p>
	 * ��ȡ�����µ�20�������б�
	 * 
	 * @return ���������б�
	 * 
	 * */
	public Vector<Review> getReviewsByMe();

	/**
	 * <p>
	 * ��ȡ����Paging��ȡ�����б�
	 * 
	 * @param Paging
	 *            Paging ����
	 * @return ���������б�
	 * 
	 * */
	public Vector<Review> getReviewsByMe(Paging page);

	/**
	 * 
	 * <p>
	 * ͨ����Ʒid��ȡ��Ʒ������20������
	 * 
	 * @param id
	 *            ��Ʒ��barcode ��������
	 * @return �����б�
	 */
	public Vector<Review> getReviewsByProductId(int id);

	/**
	 * 
	 * <p>
	 * ͨ����Ʒid��Paging�����ȡ��Ʒ������
	 * 
	 * @param barcode
	 *            ��Ʒ��barcode ��������
	 * @param page
	 *            Paging ��ҳ����
	 * @return �����б�
	 */
	public Vector<Review> getReviewsByProductId(int id, Paging page);

	/**
	 * 
	 * <p>
	 * ͨ����Ʒ��id��ȡ��Ʒ������
	 * 
	 * @param id
	 *            ��Ʒ��barcode ��������
	 * @param page
	 *            Paging ��ҳ����
	 * @return �����б�
	 */
	public List<String> getAttributes(String barcode);

	/**
	 * �û�ǩ������ӿ�
	 * 
	 * @param userID
	 *            ��¼���û���id��
	 * 
	 * @return �ɹ�true ʧ��false
	 * 
	 * */
	public boolean AddVisitedTimes(int userID);

	/**
	 * ��ȡ��Ʒ��ƽ���÷�
	 * 
	 * @param productID
	 *            ��Ʒ��id���
	 * 
	 * @return ��Ʒ��ƽ���÷�
	 * 
	 * */
	public float getAverageRating(int productID);
	
	

}
