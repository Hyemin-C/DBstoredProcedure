package net.tis.day06;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;


public class DBGuestTis { 
  Connection CN=null;//DB���������� user/pwd���, CN�����ؼ� ��ɾ����
  Statement ST=null;//�����θ�ɾ� ST=CN.createStatement(X);
  PreparedStatement PST=null; //�����θ�ɾ�PST=CN.prepareStatememt(msg)
  CallableStatement CST=null; //storedprocedure����
  ResultSet RS=null;//RS=ST.executeQuery("select~") ; ��ȸ����� RS���
  String msg="" ; 
  int Gtotal=0; //��ü���ڵ尹��
  Scanner sc = new Scanner(System.in);
     
public DBGuestTis() {
   try{
    Class.forName("oracle.jdbc.driver.OracleDriver"); //����̺�ε�
    String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
    CN=DriverManager.getConnection(url,"system","1234");
    System.out.println("����Ŭ���Ἲ��success ������");
    ST=CN.createStatement();
   }catch (Exception ex) { System.out.println(ex+"���� ����");}
}//������



    
   public static void main(String[] args) {
      DBGuestTis gg = new DBGuestTis();
            
      Scanner scin = new Scanner(System.in);
      while(true) {
         System.out.print("\nsp 1��� 2��ü��� 3����  9����>>> ");
         int sel=scin.nextInt();
         if (sel==1){gg.guestInsert();}
         else if(sel==2){gg.guestSelectAll();}
         else if(sel==3){ gg.GuestUpdate();}
         else if(sel==9){ gg.myexit(); break; }
      }
      scin.close();
   }//main end
   
   public void guestInsert() {
  	 try {
  		 System.out.print("sp����Է�>>" );
  		 int s= Integer.parseInt(sc.nextLine());
  		 
  		 System.out.print("sp�̸��Է�>>"); String n=sc.nextLine();
  		 System.out.print("sp�����Է�>>"); String t=sc.nextLine();
  		 System.out.print("sp�޿��Է�>>");
  		 int p= Integer.parseInt(sc.nextLine());
  		 
  		 int h=3; //ī��Ʈ
  		 System.out.print("sp�����Է�>>"); String e= sc.nextLine();
       
  		 CST=CN.prepareCall("{call guest_sp_insert(?,?,?,?,?,?)}");
  		 CST.setInt(1,s);
  		 CST.setString(2,n);
  		 CST.setString(3,t);
  		 CST.setInt(4,p);
  		 CST.setInt(5,h);
  		 CST.setString(6,e);
  		 CST.executeUpdate();
  		 System.out.println("Stored procedure ���强��");
  	 }catch (Exception e) {System.out.println("SP�������");}
  	 
  	 
  	}//guestInsert END
   
   public void guestSelectAll( ) {//��ü���
     try {
     System.out.println("=============================================");
     Gtotal=dbCount();
     msg="select rownum as rn, g.* from(select * from guest order by sabun)g";
     RS = ST.executeQuery(msg);
     while(RS.next()) {
      int r = RS.getInt("rn");
      int a = RS.getInt("sabun"); 
      String b = RS.getString("name"); 
      String c = RS.getString("title"); 
      Date dt = RS.getDate("wdate");
      int pay = RS.getInt("pay");
      int hit = RS.getInt("hit");
      String d = RS.getString("email");
       System.out.println(a+"\t"+b+"\t"+c+"\t"+dt+"\t"+pay+"\t"+hit+"\t"+d); 
     }System.out.println("================================�ѷ��ڵ尹��:"+Gtotal +"��");
     }catch (Exception e) { System.out.println("��ü��ȸ����");}
  }//end--------------------
  

      
   public int dbCount(){//���ڵ尹�� Statement
      int mytotal=0;
      try {
         msg="select count(*) as cnt from guest";
         RS=ST.executeQuery(msg);
         if(RS.next()==true) {
            mytotal = RS.getInt("cnt");
         }
      }catch (Exception e) { System.out.println(e);}
      return mytotal;
   }//end--------------------
   
   
   public void guestSearchName( ) {//name�ʵ���ȸ
      System.out.print("\n��ȸ�̸��Է�>>>");
      String data = sc.nextLine();
      try {      
         msg="select rownum as rn, g.* from(select * from guest order by sabun)g where name like '%" + data + "%'" ;
         RS = ST.executeQuery(msg);
         while(RS.next()) {
            int r = RS.getInt("rn");
          int a = RS.getInt("sabun");
          String b = RS.getString("name"); 
          String c = RS.getString("title"); 
          Date dt = RS.getDate("wdate");
          int pay = RS.getInt("pay");
          int hit = RS.getInt("hit");
          String d = RS.getString("email");
           System.out.println(r+"\t"+a+"\t"+b+"\t"+c+"\t"+dt+"\t"+pay+"\t"+hit+"\t"+d);
         }//while end
         int mytotal=0;
         msg="select count(*) as cnt from guest where name like '%" + data + "%'";
         RS=ST.executeQuery(msg);
         if(RS.next()==true) {

            }//
         Gtotal=mytotal;
         System.out.println("================================�ѷ��ڵ尹��:"+Gtotal +"��");      
      }catch (Exception e) { System.out.println("�̸���ȸ����" + e.toString());}
   }//end--------------------
   
   
   public void GuestUpdate() {
  	 guestSelectAll( );
     try {
        System.out.println("������ ����� �Է��ϼ���.");
        while(true) {
            System.out.print("����Է� >> ");    int a = Integer.parseInt(sc.nextLine());
            
            System.out.print("�̸��Է� >> ");    String b = sc.nextLine();
            System.out.print("���� �Է� >> ");    String c = sc.nextLine();
            System.out.print("�����Է� >> ");    int d = Integer.parseInt(sc.nextLine());
            System.out.print("�̸����Է� >> ");    String f = sc.nextLine();
            int hit=0;
            CST=CN.prepareCall("{call GUEST_SP_UPDATE(?,?,?,?,?,?)}");
            CST.setInt(1,a);
            CST.setString(2,b);
            CST.setString(3,c);
            CST.setInt(4,d);
            CST.setInt(5,hit);
            CST.setString(6,f);
            
            CST.executeUpdate();
                 System.out.println("SP ���� �Ǿ����ϴ�.");
              System.out.println("==================================================");
              break;
           }
     } catch (Exception e) {System.out.println("SP ���� ����" + e.toString());
    }
  }
  
   
   public void myexit() {
     System.out.println("7-13-Mon ���α׷��� �����մϴ�");
     System.exit(1);
  }//end--------------------
  
   
}/////////////////////////////////////////////class END

