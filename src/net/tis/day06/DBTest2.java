package net.tis.day06;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class DBTest2  { 
	
	Connection CN=null;  //DB���������� user/pwd���, CN�����ؼ� ��ɾ����
	Statement ST=null;   //�����θ�ɾ� ST=CN.createStatement(X);
	PreparedStatement PST=null; //�����θ�¾� PST=CN.prepareStatement(msg)
	CallableStatement CST=null; //storedprocedure ����
	ResultSet RS=null;  //RS=ST.executeQuery("select~") ; ��ȸ����� RS���
	
	String msg="" ; 
	String code,name,title;
	Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
	
		DBTest2 db= new DBTest2();
		Scanner sc = new Scanner(System.in);
		
		 while (true) {
		 	try {
		 	System.out.println();
		  System.out.print("1.�űԵ��  2.��ü���  3.�̸�  4.����  5.���� 9.����>>");
		  int sel= Integer.parseInt(sc.nextLine());
		  if (sel==1) {db.dbInsert();}
		 	else if (sel==2) {db.dbSelectAll();}
		 	else if (sel==3) {db.dbSearchName();}
		 	else if (sel==4) {db. dbDelete();}
		 	else if (sel==5) {db.dbUpate();}
		 	else if (sel==9) {System.out.println("���α׷� �����մϴ�");
	 		System.exit(1);}
		 	}catch (Exception e) {System.out.println("1~3, 9 ���ڸ� �Է��Ͻÿ�");}
		 }//while END
	}//main end
	
	public DBTest2() {
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //����̺�ε�
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     ST=CN.createStatement();
		 }catch (Exception ex){System.out.println("����: "+ ex.toString() );}
	}
	
	public void dbInsert( ) {
		 System.out.println("\n===================�űԵ��==========================");
		 int a=0;
    try{
  
	     Scanner sc = new Scanner(System.in);
	     System.out.print("PST code >> ");
	     int data = Integer.parseInt(sc.nextLine());
	     //RS = ST.executeQuery("select * from test where code=" + data); //select�϶� executeQuery
 	     //while(RS.next()) {a=RS.getInt("code");}
	 	    //if(data== a) {	  	 
	 	    //System.out.println("�ߺ��� �ڵ� �Դϴ� �ٸ� �ڵ带 �Է��ϼ���");
 	      //System.out.print("PST code >> ");
 	      //data = Integer.parseInt(sc.nextLine()); }
	     msg="select count(*) as hit from test where code=" +data;
	     RS=ST.executeQuery(msg);
	      while(RS.next()) {
	    	a=RS.getInt("hit");
	    	if (a>0) {
	    		System.out.println(data+"�� �ߺ��� �ڵ��Դϴ� �ٸ� �ڵ带 �Է��ϼ���");
	    	  System.out.print("PST code >> ");
	 	      data = Integer.parseInt(sc.nextLine());
	    	}
	     }
 	 
	 	  
	     System.out.print("PST name >> ");
	     String name = sc.nextLine();
	     
	     System.out.print("PST title >> ");
	     String title = sc.nextLine();
	     
	     //insert������ ó���������� code�ߺ�üũ   
		   msg= "insert into test values(?,?,?,sysdate, 0)";
		   PST=CN.prepareStatement(msg);
		   PST.setInt(1,data);
		   PST.setString(2, name);
		   PST.setString(3, title);
		   
		   int OK=PST.executeUpdate(); //��¥���� ~.executeUpdate(x)	
		   System.out.println("test���̺�  PST��ɾ� ���强��success");
		   dbSelectAll();
		   }catch (Exception ex){System.out.println("����: "+ ex.toString() );}
   
			}//end--------------------
	
	 public void dbSelectAll( ) {
		   System.out.println("\n====================��ü���=========================");
		   try{
        RS = ST.executeQuery("select rownum,t.* from test t"); //select�϶� executeQuery
	 	    while(RS.next()==true) {
	 	    int rn=RS.getInt("rownum");
	 	  	int a=RS.getInt("code");
	 	  	String b = RS.getString("name");
	 	  	String c = RS.getString("title");
	 	  	Date dt = RS.getDate("wdate");
	 	  	int hit = RS.getInt("cnt");
	 	  	System.out.println(rn+ "\t"+a+"\t"+b+"\t"+c+"\t"+dt+"\t"+hit);
	 	   }System.out.println("\n�Ѱ���=" +dbCount() );
	     }catch (Exception ex){System.out.println("����: "+ ex.toString() );}
		   System.out.println("=================================================");
	   }//end--------------------
	
	public void dbSearchName( ) {
	    System.out.println("\n====================�̸��˻�====================");
		  try { 
      Scanner sc = new Scanner(System.in);
	    System.out.print("name >> ");
	    String name = sc.nextLine();
	    RS = ST.executeQuery("select * from test where name like '%" + name +"%'"  );
	    while(RS.next()==true) {
	    int a=RS.getInt("code");
		 	String b = RS.getString("name");
		 	String c = RS.getString("title");
		 	Date dt = RS.getDate("wdate");
		 	int hit = RS.getInt("cnt");
		 	System.out.println(a+"\t"+b+"\t"+c+"\t"+dt+"\t"+hit);
	     }
		  }catch (Exception ex){System.out.println("����: "+ ex.toString() );}
		}//end--------------------
	
	 public void dbUpate() { //�Ѱ� �ڵ�� ���� 
  	 	try{ 
  	   Scanner sc = new Scanner(System.in);
	     System.out.print("���� code >> ");
	     String code = sc.nextLine();
	   
	     System.out.print("���� code >> ");
	     String code2 = sc.nextLine();
	     
	     System.out.print("���� name >> ");
	     String name = sc.nextLine();
	     
	     System.out.print("���� title >> ");
	     String title= sc.nextLine();
	     
		   msg= "Update test set code= ?,name=?,title=? where code=? ";
		   PST=CN.prepareStatement(msg);
		   PST.setString(4,code); PST.setString(1,code2);
		   PST.setString(2,name); PST.setString(3,title);
		   
		   	   
		   int OK=PST.executeUpdate(); //��¥���� ~.executeUpdate(x)	
		   System.out.println("PST���� ����success");
		   dbSelectAll();
  	
  		}catch (Exception ex){
		 System.out.println("����: "+ ex.toString() );}
  	 }
  
  public int dbCount() { //���ڵ� ���� Statement
  	int mycount=0;
    try{
  	msg="select count(*) as cnt from test";
  	//msg="select count(*) as cnt from test where name like '%" +data +'%";
  	RS = ST.executeQuery(msg);
  	while(RS.next()) {
  	mycount=RS.getInt("cnt");
  	}
  	}catch (Exception ex){
		System.out.println("����: "+ ex.toString() );}
	  return mycount;
    }
  
  
	public void dbDelete( ) {
		System.out.println("\n=====================����========================");
		try{
	   Scanner sc = new Scanner(System.in);
	   System.out.print("������ code>> ");
	   int code = Integer.parseInt(sc.nextLine());
	   msg = "delete  from test where code= "+ code ;  
	   int OK = ST.executeUpdate(msg);
	   System.out.println(code+"���̺� ���� ����");
	   dbSelectAll( );
	    }catch (Exception ex){
			System.out.println("����: "+ ex.toString() );}
		 }//end--------------------
	
	public void dbInsert1234( ) {//�Ѱǵ��
	   System.out.println();
		 System.out.println("===================�űԵ��==========================");
  try{

	     Scanner sc = new Scanner(System.in);
	     System.out.print("code >> ");
	     String code = sc.nextLine();
	   
	     System.out.print("name >> ");
	     String name = sc.nextLine();
	     
	     System.out.print("title >> ");
	     String title = sc.nextLine();
	   
	   
	     //SQL> delete from test where code > 6600 ; 
	     //SQL> commit ;
		  
		   //msg= "insert into test values(8800, 'LG', 'LB' ,sysdate, 0)";
		   //msg= "insert into test values(code, 'name', 'title' ,sysdate, 0)";
		    msg= "insert into test values("+code+",'"+name+"','"+title+"',sysdate, 0)";
		   System.out.println(msg);
		   ST.executeUpdate(msg);//~.executeUpdate(i/d/u)
		   System.out.println("test���̺� ���强��success");
		   dbSelectAll();
  }catch (Exception ex){System.out.println("����: "+ ex.toString() );}
  
			  
	}//end--------------------
	
}// class END


//1�űԵ��  2.��ü���  3.�̸�  4.����  9.����
//������  Class.forName(); CN=DriverManager.getConnection(1,2,3)
//dbInsert(  )  dbSelectAll( )  dbSearchName()  , dbDelete()











