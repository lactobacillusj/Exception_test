import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;

import util_test.Util;

public class Exception_test {
	public static void main(String[] args) throws Exception {
		
		Util ut = new Util();
		//Util ut = new Util();
		Connection conn = null;
		conn = ut.connectDB();
		////////////////////////////////////////////////////////////���̺�� ��������
		List<String> TN_list = ut.getTableName(conn);
//		for(int i = 0; i < TN_list.size(); i++)
//		System.out.println(TN_list.get(i));  
		//���̺�� �� ��������
		LinkedHashMap<String,String> TD_hm = ut.getTableData(conn, TN_list); 
		////////////////////////////////////////////////////////////���̺� ���� ��������
		
		
	}
}
