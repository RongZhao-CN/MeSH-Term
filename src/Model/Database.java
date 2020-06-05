package Model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import DataFormat.*;
import java.sql.Statement;

public class Database {
//	private static List table_name;
//	private static Map<String,List> field;
	private final static String databaseName = "mesh_database";// 已经在MySQL数据库中创建好的数据库。
	private final static String userName = "root";// MySQL默认的root账户名  
	private final static String password = "zzz987654321";// 默认的root账户密码为空  
	private static Connection conn;
	private static PreparedStatement stmt;
	private static List SQLs;
	private static int COUNT=0;
	public static JSON2Data j2d=new JSON2Data();
	private final static int ID_field=1;
	public Database(){
//		this.table_name=new ArrayList();
//		this.field=new HashMap<String,List>();
		getConnection();
		SQLs=new ArrayList();
		SQLs.add("select * from ");
		SQLs.add("insert ignore into ");
		SQLs.add("update ");
		
	}
	
	public static void getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName+"?serverTimezone=UTC", userName, password); 
			System.out.println("database connected ...");
			conn.setAutoCommit(false);
		}catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	public static void closeConnection(){
		try {
			if(stmt!=null)
				stmt.close();
			if(conn!=null)
				conn.isClosed();
			System.out.println("database closed...");
		}catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	public static List getTableNames() {
		List table_name=new ArrayList();
		ResultSet rs=null;
		try {
			DatabaseMetaData db=conn.getMetaData();
			rs=db.getTables(null, null, null, new String[] {"TABLE"});
			while(rs.next()) {
				table_name.add(rs.getString(3));
			}}catch(Exception e) {
				e.printStackTrace();
			}
		return table_name;
	}
	public static List getColumnNames(String tableName) {
		String sql=SQLs.get(0).toString()+tableName;
		List names=new ArrayList();
		try {
			stmt=conn.prepareStatement(sql);
			ResultSetMetaData rsmd=stmt.getMetaData();
			int size=rsmd.getColumnCount();
			for(int i=0;i<size;i++) {
				names.add(rsmd.getColumnName(i+1));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return names;
	}
	public static List getColumnTypes(String tableName) {
		String sql=SQLs.get(0).toString()+tableName;
		List names=new ArrayList();
		try {
			stmt=conn.prepareStatement(sql);
			ResultSetMetaData rsmd=stmt.getMetaData();
			int size=rsmd.getColumnCount();
			for(int i=0;i<size;i++) {
				names.add(rsmd.getColumnType(i+1));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return names;
	}
	
	public static ResultSet getResultSet(String table_name) {
		
		String sql=SQLs.get(0).toString()+table_name;
    	ResultSet rs = null;
    	try {
    		stmt=conn.prepareStatement(sql);
    		rs=stmt.executeQuery();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return rs;
	}
	
	public static HashMap<String,Integer> getTableContent(String table_name,int one,int two){
		HashMap<String,Integer> hashmap=new HashMap<>();
    	String sql=SQLs.get(0).toString()+table_name;
    	ResultSet rs = null;
    	try {
    		stmt=conn.prepareStatement(sql);
    		rs=stmt.executeQuery();
        	while(rs.next())
        	{
        		hashmap.put(rs.getString(one).toString(), rs.getInt(two));
        	}
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return hashmap;
	}
	public static HashMap<String,String> getMapFromTableField(String table_name,int key,int value) {
		//构建由primary所在字段和ID所在字段之间的一一映射
    	HashMap<String,String> hashmap=new HashMap<>();
    	String sql=SQLs.get(0).toString()+table_name;
    	ResultSet rs = null;
    	try {
    		stmt=conn.prepareStatement(sql);
    		rs=stmt.executeQuery();
        	while(rs.next())
        	{
        		hashmap.put(rs.getString(key), rs.getString(value));
        	}
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return hashmap;
    }
	public static HashMap<String,List> getUItoIds(String table_name,int primary,int ID) {
		//构建表中以primary所在字段为键，ID所在字段构成的列表为值
    	HashMap<String,List> hashmap=new HashMap<>();
    	String sql=SQLs.get(0).toString()+table_name;
    	ResultSet rs = null;
    	try {
    		stmt=conn.prepareStatement(sql);
    		rs=stmt.executeQuery();
        	while(rs.next())
        	{
        		String ui=rs.getString(primary);
        		String id=rs.getString(ID);
        		if(hashmap.containsKey(ui))
        			hashmap.get(ui).add(id);
        		else
        		{
        			List li=new ArrayList();
        			li.add(id);
        			hashmap.put(ui, li);
        			
        		}
        	}
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return hashmap;
    }
	
	public static Map<String,String> getInformation(String name){
		Map<String,String> res=new HashMap<String,String>();
		ResultSet rs = null;
		String sql;
		sql=SQLs.get(0)+"concept where "+getColumnNames("concept").get(1)+"="+"'"+name+"'";
		
    	try {
    		stmt=conn.prepareStatement(sql);
    		rs=stmt.executeQuery();
        	while(rs.next())
        	{
        		res.put("name", rs.getString(2));
        		res.put("subject", rs.getString(3));
        		res.put("date", rs.getString(7));
        		String an= rs.getString(6);
        		if(an!="") {
        			System.out.println(an);
        			if(an.contains("pharmacological action:")) {
        				String[] tmp=an.split("pharmacological action:");
            			res.put("description",tmp[0]);
            			res.put("pharm_action",tmp[1]);
        			}
        			else{
        				res.put("description",an);
        			}
			
        		}
        		}
        		
        	}catch (Exception e) {
        		e.printStackTrace();
        	}
		return res;
	}
	public static Map<String,String> getInformationRandom(Set l){
		Map<String,String> res=new HashMap<String,String>();
		ResultSet rs = null;
		String sql;
		sql=SQLs.get(0)+"concept";
		int i=1;
    	try {
    		stmt=conn.prepareStatement(sql);
    		rs=stmt.executeQuery();
        	while(rs.next())
        	{
        		if(l.contains(i)) {
        			res.put(rs.getString(2), rs.getString(6));
        			l.remove(i);
        		}
        		if(l.isEmpty())
        			break;
        		i++;
        			
        		}
        		
        	}catch (Exception e) {
        		e.printStackTrace();
        	}
		return res;
	}
	public TreeMap<String,Integer> getInformationBinary(String name,int i,int j){
		TreeMap<String,Integer> res=new TreeMap<String,Integer>();
		HashMap hm=new HashMap();
		ResultSet rs = null;
		String sql;
		sql=SQLs.get(0)+name;
		System.out.println(sql);
    	try {
    		stmt=conn.prepareStatement(sql);
    		rs=stmt.executeQuery();
        	while(rs.next())
        	{
        		hm.put( rs.getString(i),rs.getInt(j));
        		res.putAll(hm);
        			
        	}
        		
        	}catch (Exception e) {
        		e.printStackTrace();
        	}
		return res;
	}
	public ArrayList<List> getInformationList(String table,String name,int i,List<Integer> var){
		ArrayList<List> res=new ArrayList<List>();
		
		ResultSet rs = null;
		String sql;
		sql=SQLs.get(0)+table+" where "+getColumnNames(table).get(i-1)+"="+"'"+name+"'";
//		System.out.println(sql);
    	try {
    		stmt=conn.prepareStatement(sql);
    		rs=stmt.executeQuery();
        	while(rs.next())
        	{
        		ArrayList hm=new ArrayList();
        		for(int j=0;j<var.size();j++)
        			hm.add(rs.getString(var.get(j)));
        		
        		
        		res.add(hm);
        			
        	}
        		
        	}catch (Exception e) {
        		e.printStackTrace();
        	}
		return res;
	}
	public List<MeSHTreeNode> getChild(String table,String name,String num ,int i){
		List<MeSHTreeNode> res=new ArrayList<MeSHTreeNode>();
		ResultSet rs = null;
		String sql;
		sql=SQLs.get(0)+table+" where "+getColumnNames(table).get(i-1)+"="+"'"+num+"' and "+getColumnNames(table).get(3)+"='"+name+"'";
//		System.out.println(sql);
    	try {
    		stmt=conn.prepareStatement(sql);
    		rs=stmt.executeQuery();
        	while(rs.next())
        	{
        		String path=rs.getString(1);
//        		System.out.println(path+":"+name);
        		MeSHTreeNode m=new MeSHTreeNode();
    			m.name=rs.getString(2);
    			m.child=null;
    			res.add(m);
//        		if(path.contains(ex)) {
//        			MeSHTreeNode m=new MeSHTreeNode();
//        			m.name=rs.getString(2);
//        			m.child=null;
//        			res.add(m);
//        		}
        	}
        		
        	}catch (Exception e) {
        		e.printStackTrace();
        	}
		return res;
	}
	public Map<String,Integer> getInformationFilter(String name,int i,int j){
		Map<String,Integer> res=new HashMap<String,Integer>();
		
		ResultSet rs = null;
		String sql;
		sql=SQLs.get(0)+name+" where "+getColumnNames(name).get(1)+">"+"'40000'";
		System.out.println(sql);
    	try {
    		stmt=conn.prepareStatement(sql);
    		rs=stmt.executeQuery();
        	while(rs.next())
        	{
        		res.put( rs.getString(i),rs.getInt(j));
        			
        	}
        		
        	}catch (Exception e) {
        		e.printStackTrace();
        	}
		return res;
	}
	public Map<String,List<Integer>> getInformationFilterNormal(String name,int col_index,int threshold,int limit,List<Integer> type){
		Map<String,List<Integer>> res=new HashMap<String,List<Integer>>();
		
		ResultSet rs = null;
		String sql;
		int one=type.get(0);
		int two=type.get(1);
		int three=type.get(2);
		sql=SQLs.get(0)+name+" where "+getColumnNames(name).get(col_index)+">"+"'"+String.valueOf(threshold)+"' AND "+getColumnNames(name).get(col_index)+"<"+"'"+String.valueOf(limit)+"' AND "+getColumnNames(name).get(one)+" > "+getColumnNames(name).get(two)+" AND "+getColumnNames(name).get(one)+" > "+getColumnNames(name).get(three);
		System.out.println(sql);
    	try {
    		stmt=conn.prepareStatement(sql);
    		rs=stmt.executeQuery();
        	while(rs.next())
        	{
        		List li=new ArrayList();
        		for(int i=2;i<getColumnNames(name).size()+1;i++)
        			li.add(rs.getInt(i));
        		res.put(rs.getString(1),li);
        			
        	}
        		
        	}catch (Exception e) {
        		e.printStackTrace();
        	}
		return res;
	}
	public Map<String,List<Integer>> getInformationFilterNormal(String name,int comp_index,Map<String,String> map){
		Map<String,List<Integer>> res=new HashMap<String,List<Integer>>();
		
		ResultSet rs = null;
		String sql;
		sql=SQLs.get(0)+name;
		
    	try {
    		stmt=conn.prepareStatement(sql);
    		rs=stmt.executeQuery();
        	while(rs.next())
        	{
        		if(map.containsKey(rs.getString(comp_index))) {
        			List li=new ArrayList();
            		for(int i=2;i<getColumnNames(name).size()+1;i++)
            			li.add(rs.getInt(i));
            		res.put(rs.getString(comp_index),li);
        		}
        		
        			
        	}
        		
        	}catch (Exception e) {
        		e.printStackTrace();
        	}
		return res;
	}
	
	
}
