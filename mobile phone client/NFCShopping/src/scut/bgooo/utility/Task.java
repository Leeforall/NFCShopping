package scut.bgooo.utility;

import java.util.Map;

public class Task {

	private int mTaskId ;//�����ID
	private Map taskParam;// �������
	public static final int GET_USER_INFORMATION = 1;//ע��������û�����Ϣ	
	public static final int SEND_COMMENT_WEIBO = 2;//������ۺ󣬷���������Ϣ
	public static final int GET_DISCOUNT = 3;//�����Ʒ�Ż��б�
	public static final int GET_DISCOUNTITEM = 4;//��þ���ĳ�ڵ���Ʒ�Ż���Ŀ
	
	public Task(int id,  Map param) {
		 mTaskId = id;
		 taskParam = param;
	}

	public int getTaskID() {
		return mTaskId;
	}

	public void setTaskID(int taskID) {
		 mTaskId = taskID;
	}

	public Map getTaskParam() {
		return taskParam;
	}

	public void setTaskParam(Map taskParam) {
		taskParam = taskParam;
	}
}
