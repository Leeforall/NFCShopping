package scut.bgooo.webservice;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import scut.bgooo.entities.DiscountItem;
import scut.bgooo.entities.Review;
import scut.bgooo.entities.Discount;
import scut.bgooo.entities.Paging;
import scut.bgooo.entities.Product;
import scut.bgooo.entities.SecCategory;
import scut.bgooo.entities.Suggestion;
import scut.bgooo.entities.User;

public class WebServiceUtil implements IWebServiceUtil {

	private static String TAG = WebServiceUtil.class.getName();

	private static final String NAMESPACE = "http://tempuri.org/";
	private static String URL = "http://192.168.1.100:8080/NFCShopping/ShopWebservice.asmx";


	private static final String REGIST = "Regist";
	private static final String ADDSUGGESTION = "AddSuggestion";
	private static final String ADDREVIEW = "AddReview";
	private static final String GETUSER = "GetUser";
	private static final String LOGIN = "Login";
	private static final String GETREVIEWSBYUSERID = "FindReviewsByUserID";
	private static final String GETREVIEWSBYPRODUCTID = "FindReviewsByProductID";
	private static final String GETPRODUCTBYBARCODE = "FindProductByBarcode";
	private static final String GETDISCOUNTS = "GetDiscounts";
	private static final String GETDISCOUNTITEMSBYDISCOUNTID = "FindDiscountItemsByDiscountID";
	private static final String ADDVISITTIEMS = "AddVisitTimes";
	private static final String GETAVERAGERATING = "GetAverageRatingByProductID";

	// private static final String GETUSERBYID = "FindUserByID"; //��ʱ���Բ��õ�
	public static User nowUser = null;
	private static WebServiceUtil Webservice = new WebServiceUtil();

	/**
	 * װ�Ƶ�����һ�µ���ģʽ
	 * 
	 * */
	public static WebServiceUtil getInstance() {
		return Webservice;
	}

	@Override
	public User login(String userName, String password) {
		// TODO Auto-generated method stub
		SoapObject rpc = getSoapObject(Method.LOGIN);
		rpc.addProperty("userName", userName);
		rpc.addProperty("password", password);
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = false;// ע�⣺��������Ƕ�dotnetwebserviceЭ���֧��,���dotnet��webservice
								// ��ָ��rpc��ʽ����true����Ҫ��false
		envelope.setOutputSoapObject(rpc);
		envelope.addMapping(NAMESPACE, "User", User.class);
		/**
		 * ����ķ���ֵ
		 * 
		 * */
		nowUser = null;
		try {
			ht.call(NAMESPACE + LOGIN, envelope);
			if (envelope.getResponse() != null)
				nowUser = (User) envelope.getResponse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nowUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ���ص���vector����
	 * */
	public Vector<Review> getReviewsByMe() {
		// TODO Auto-generated method stub
		if (nowUser.equals(null)) {
			return null; // �û�Ϊ�յ�ʱ�򣬷���null
		} else {
			SoapObject rpc = getSoapObject(Method.GETREVIEWSBYUSERID);
			rpc.addProperty("uid", nowUser.getProperty(0)); // �����û���id
			HttpTransportSE ht = new HttpTransportSE(URL);
			ht.debug = true;
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.bodyOut = rpc;
			envelope.dotNet = false;
			envelope.setOutputSoapObject(rpc);
			envelope.addMapping(NAMESPACE, "Review", Review.class);
			envelope.addMapping(NAMESPACE, "User", User.class);
			envelope.addMapping(NAMESPACE, "Product", Product.class);
			envelope.addMapping(NAMESPACE, "SecCategory", SecCategory.class);
			Vector<Review> result = null;
			try {
				ht.call(NAMESPACE + GETREVIEWSBYUSERID, envelope);
				result = (Vector<Review>) envelope.getResponse();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result; // ��ȡ�������۵�ʱ�� vector��size �� 0
		}
	}

	@Override
	public Vector<Review> getReviewsByMe(Paging page) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vector<Review> getReviewsByProductId(int id) {
		// TODO Auto-generated method stub
		SoapObject rpc = getSoapObject(Method.GETREVIEWSBYPRODUCTID);
		rpc.addProperty("pid", id); // ������Ʒ��id
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(rpc);
		envelope.addMapping(NAMESPACE, "Review", Review.class);
		envelope.addMapping(NAMESPACE, "User", User.class);
		envelope.addMapping(NAMESPACE, "Product", Product.class);
		envelope.addMapping(NAMESPACE, "SecCategory", SecCategory.class);
		Vector<Review> result = null;
		try {
			ht.call(NAMESPACE + GETREVIEWSBYPRODUCTID, envelope);
			result = (Vector<Review>) envelope.getResponse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Vector<Review> getReviewsByProductId(int id, Paging page) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public User getUser(int id) {// ����ûʲô��Ҫ�ˣ���Ϊ��ȡ��Ʒ��ʱ���˳�����û�����Ϣ��������
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Discount> getDiscounts() {
		// TODO Auto-generated method stub
		SoapObject rpc = getSoapObject(Method.GETDISCOUNTS);
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(rpc);
		envelope.addMapping(NAMESPACE, "Discount", Discount.class);
		Vector<Discount> result = null;
		try {
			ht.call(NAMESPACE + GETDISCOUNTS, envelope);
			result = (Vector<Discount>) envelope.getResponse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Vector<Discount> getDiscounts(Paging page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<DiscountItem> getDiscountItems(int id) {
		// TODO Auto-generated method stub
		SoapObject rpc = getSoapObject(Method.GETDISCOUNTITEMSBYDISCOUNTID);
		rpc.addProperty("discountID", id);
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(rpc);
		envelope.addMapping(NAMESPACE, "DiscountItem", DiscountItem.class);
		envelope.addMapping(NAMESPACE, "Product", Product.class);
		envelope.addMapping(NAMESPACE, "SecCategory", SecCategory.class);
		Vector<DiscountItem> result = null;
		try {
			ht.call(NAMESPACE + GETDISCOUNTITEMSBYDISCOUNTID, envelope);
			Log.d(TAG, envelope.getResponse().toString());
			result = (Vector<DiscountItem>) envelope.getResponse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Product getProductByBarcode(String barcode) {
		// TODO Auto-generated method stub
		SoapObject rpc = getSoapObject(Method.GETPRODUCTBYBARCODE);
		rpc.addProperty("barcode", barcode); // �����û���id
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(rpc);
		envelope.addMapping(NAMESPACE, "Product", Product.class);
		envelope.addMapping(NAMESPACE, "SecCategory", SecCategory.class);
		Product result = null;
		try {
			ht.call(NAMESPACE + GETPRODUCTBYBARCODE, envelope);
			if (envelope.getResponse() != null) // �жϷ��صĶ����Ƿ�Ϊ��
				result = (Product) envelope.getResponse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result; // ����null ��ʾ�Ҳ�������
	}

	@Override
	public List<String> getAttributes(String barcode) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public User regist(String userName, String password, int gender) {
		// TODO Auto-generated method stub
		SoapObject rpc = getSoapObject(Method.REGIST);
		rpc.addProperty("userName", userName);
		rpc.addProperty("password", password);
		rpc.addProperty("gender", gender);
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = false;// ע�⣺��������Ƕ�dotnetwebserviceЭ���֧��,���dotnet��webservice
								// ��ָ��rpc��ʽ����true����Ҫ��false
		envelope.setOutputSoapObject(rpc);
		envelope.addMapping(NAMESPACE, "User", User.class);//
		// ������ʱ���룬����namespace��webservice��ָ���ģ�
		// name�Ƿ��������͵����ƣ�
		// claszz���Զ����������
		/**
		 * ����ķ���ֵ
		 * */
		nowUser = null; // ���ע�᷵�صĶ���
		try {
			ht.call(NAMESPACE + REGIST, envelope);
			if (envelope.getResponse() != null) {
				Log.d(TAG, envelope.getResponse().toString());
				nowUser = (User) envelope.getResponse();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nowUser;
	}

	@Override
	public boolean AddReview(Review review) {
		// TODO Auto-generated method stub
		SoapObject rpc = getSoapObject(Method.ADDREVIEW);
		PropertyInfo pi = getPropertyInfo(review);
		rpc.addProperty(pi);
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(rpc);
		envelope.addMapping(NAMESPACE, "Review", review.getClass());
		try {
			ht.call(NAMESPACE + ADDREVIEW, envelope);
			if (envelope.getResponse() != null) {
				return (Boolean) envelope.getResponse();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean AddSuggestion(Suggestion suggestion) {
		// TODO Auto-generated method stub
		SoapObject rpc = getSoapObject(Method.ADDSUGGESTION);
		PropertyInfo pi = getPropertyInfo(suggestion);
		rpc.addProperty(pi);
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(rpc);
		envelope.addMapping(NAMESPACE, "Suggestion", suggestion.getClass());// ע����仰����Ҫ
		try {
			ht.call(NAMESPACE + ADDSUGGESTION, envelope);
			if (envelope.getResponse() != null) {
				return (Boolean) envelope.getResponse();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public double getAverageRating(int productID) {
		SoapObject rpc = getSoapObject(Method.GETAVERAGERATING);
		rpc.addProperty("id", productID);
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(rpc);
		try {
			ht.call(NAMESPACE + GETAVERAGERATING, envelope);
			if (envelope.getResponse() != null) {
				return (Double) envelope.getResponse();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.0;
	}

	@Override
	public boolean AddVisitedTimes(int userID) {
		// TODO Auto-generated method stub
		SoapObject rpc = getSoapObject(Method.ADDVISITTIEMS);
		rpc.addProperty("userID", userID);
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = false;
		envelope.setOutputSoapObject(rpc);
		try {
			ht.call(NAMESPACE + ADDVISITTIEMS, envelope);
			if (envelope.getResponse() != null) {
				return (Boolean) envelope.getResponse();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private PropertyInfo getPropertyInfo(Object object) {
		PropertyInfo pi = new PropertyInfo();
		if (object instanceof Review) {
			pi.setName("review");
			pi.setValue((Review) object);
			pi.setType(((Review) object).getClass());
		} else if (object instanceof Suggestion) {
			pi.setName("suggestion");
			pi.setValue((Suggestion) object);
			pi.setType(((Suggestion) object).getClass());
		}
		return pi;
	}

	private SoapObject getSoapObject(int methodID) {
		SoapObject rpc = null;
		switch (methodID) {
		case Method.REGIST:
			rpc = new SoapObject(NAMESPACE, REGIST);
			break;
		case Method.LOGIN:
			rpc = new SoapObject(NAMESPACE, LOGIN);
			break;
		case Method.ADDSUGGESTION:
			rpc = new SoapObject(NAMESPACE, ADDSUGGESTION);
			break;
		case Method.ADDREVIEW:
			rpc = new SoapObject(NAMESPACE, ADDREVIEW);
			break;
		case Method.GETUSER:
			rpc = new SoapObject(NAMESPACE, GETUSER);
			break;
		case Method.GETDISCOUNTITEMSBYDISCOUNTID:
			rpc = new SoapObject(NAMESPACE, GETDISCOUNTITEMSBYDISCOUNTID);
			break;
		case Method.GETDISCOUNTS:
			rpc = new SoapObject(NAMESPACE, GETDISCOUNTS);
			break;
		case Method.GETREVIEWSBYPRODUCTID:
			rpc = new SoapObject(NAMESPACE, GETREVIEWSBYPRODUCTID);
			break;
		case Method.GETPRODUCTBYBARCODE:
			rpc = new SoapObject(NAMESPACE, GETPRODUCTBYBARCODE);
			break;
		case Method.ADDVISITTIEMS:
			rpc = new SoapObject(NAMESPACE, ADDVISITTIEMS);
			break;
		case Method.GETAVERAGERATING:
			rpc = new SoapObject(NAMESPACE, GETAVERAGERATING);
			break;
		default:
			break;
		}
		return rpc;
	}

}
