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
package scut.bgooo.concern;

public class ConcernItem implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int getCount() {
		return 8;
	}
	private String Barcode;
	private String Brand;
	private String Description;
	/**
	 * ��������ݿ��ID��
	 * */
	private int Id;
	/**
	 * ��Ǹļ�¼�Ƿ��ղ�
	 * */
	private short IsCollected;

	private String Location;

	/**
	 * �ӷ�������ȡ������Ʒ��Name
	 * */
	private String Name;

	/**
	 * �ӷ�������ȡ������Ʒ�۸�
	 * */
	private float Price;

	/**
	 * �ӷ�������ȡ������ƷID��
	 * */
	private int ProductId;

	/**
	 * �ӷ�������ȡ������Ʒ������
	 * */
	private float Rating;

	private String SecCategory; // ��ʱ��û�����ݿ�

	/**
	 * ��¼��ӵ�ʱ��
	 * */
	private long Timestamp;
	/**
	 * �ӷ�������ȡ������Ʒ����Type
	 * */
	private int Type;
	/**
	 * ����Ǵ����ݿ��ȡitem�Ĺ��캯����Ҫ����id
	 * */
	
	/**
	 *��ƷͼƬ
	 */	
	private byte [] icon;
	
	public ConcernItem(int id, int productId, String name, int type,
			String seccategory, float price, float rating, String brand,
			String location, String barcode, String description,
			long timestamp, short iscollected, byte[] data) {
		Id = id;
		ProductId = productId;
		Name = name;
		Type = type;
		SecCategory = seccategory;
		Rating = rating;
		Price = price;
		setBarcode(barcode);
		setBrand(brand);
		setLocation(location);
		setDescription(description);
		Timestamp = timestamp;
		IsCollected = iscollected;
		icon = data;
	}

	public ConcernItem(long timestamp) {
		Id = 0;
		Timestamp = timestamp;
	}

	public Object getAttribute(int index) {
		Object temp = null;
		switch (index) {
		case 0:
			temp = getIcon();
			break;
		case 1:
			temp = getName();
			break;
		case 2:
			temp = getBrand();
			break;
		case 3:
			temp = getPrice();
			break;
		case 4:
			temp = getRating();
			break;
		case 5:
			temp = getSecCategory();
			break;
		case 6:
			temp = getLocation();
			break;
		case 7:
			temp = getDescription();
			break;
		default:
			break;
		}
		return temp;
	}

	public String getBarcode() {
		return Barcode;
	}

	public String getBrand() {
		return Brand;
	}

	public String getDescription() {
		return Description;
	}

	public int getId() {
		return Id;
	}

	public short getIsCollected() {
		return IsCollected;
	}

	public String getLocation() {
		return Location;
	}

	public String getName() {
		return Name;
	}

	public float getPrice() {
		return Price;
	}

	public int getProductId() {
		return ProductId;
	}

	public float getRating() {
		return Rating;
	}

	public String getSecCategory() {
		return SecCategory;
	}

	public long getTimestamp() {
		return Timestamp;
	}

	public int getType() {
		return Type;
	}
	
	public byte[] getIcon() {
		return icon;
	}

	public void setBarcode(String barcode) {
		Barcode = barcode;
	}

	public void setBrand(String brand) {
		Brand = brand;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public void setIsCollected(short collected) {
		IsCollected = collected;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public void setProductId(int productId) {
		ProductId = productId;
	}

	public void setRating(float rating) {
		Rating = rating;
	}

	public void setSecCategory(String secCategory) {
		SecCategory = secCategory;
	}
	
	public void setIcon(byte[] data) {
		icon = data;
	}
}
