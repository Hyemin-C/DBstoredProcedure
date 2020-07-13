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
  Connection CN=null;//DB서버정보및 user/pwd기억, CN참조해서 명령어생성
  Statement ST=null;//정적인명령어 ST=CN.createStatement(X);
  PreparedStatement PST=null; //동적인명령어PST=CN.prepareStatememt(msg)
  CallableStatement CST=null; //storedprocedure연결
  ResultSet RS=null;//RS=ST.executeQuery("select~") ; 조회결과를 RS기억
  String msg="" ; 
  int Gtotal=0; //전체레코드갯수
  Scanner sc = new Scanner(System.in);
     
public DBGuestTis() {
   try{
    Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브로드
    String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
    CN=DriverManager.getConnection(url,"system","1234");
    System.out.println("오라클연결성공success 월요일");
    ST=CN.createStatement();
   }catch (Exception ex) { System.out.println(ex+"연결 에러");}
}//생성자



    
   public static void main(String[] args) {
      DBGuestTis gg = new DBGuestTis();
            
      Scanner scin = new Scanner(System.in);
      while(true) {
         System.out.print("\nsp 1등록 2전체출력 3갱신  9종료>>> ");
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
  		 System.out.print("sp사번입력>>" );
  		 int s= Integer.parseInt(sc.nextLine());
  		 
  		 System.out.print("sp이름입력>>"); String n=sc.nextLine();
  		 System.out.print("sp제목입력>>"); String t=sc.nextLine();
  		 System.out.print("sp급여입력>>");
  		 int p= Integer.parseInt(sc.nextLine());
  		 
  		 int h=3; //카운트
  		 System.out.print("sp메일입력>>"); String e= sc.nextLine();
       
  		 CST=CN.prepareCall("{call guest_sp_insert(?,?,?,?,?,?)}");
  		 CST.setInt(1,s);
  		 CST.setString(2,n);
  		 CST.setString(3,t);
  		 CST.setInt(4,p);
  		 CST.setInt(5,h);
  		 CST.setString(6,e);
  		 CST.executeUpdate();
  		 System.out.println("Stored procedure 저장성공");
  	 }catch (Exception e) {System.out.println("SP저장실패");}
  	 
  	 
  	}//guestInsert END
   
   public void guestSelectAll( ) {//전체출력
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
     }System.out.println("================================총레코드갯수:"+Gtotal +"건");
     }catch (Exception e) { System.out.println("전체조회에러");}
  }//end--------------------
  

      
   public int dbCount(){//레코드갯수 Statement
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
   
   
   public void guestSearchName( ) {//name필드조회
      System.out.print("\n조회이름입력>>>");
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
         System.out.println("================================총레코드갯수:"+Gtotal +"건");      
      }catch (Exception e) { System.out.println("이름조회에러" + e.toString());}
   }//end--------------------
   
   
   public void GuestUpdate() {
  	 guestSelectAll( );
     try {
        System.out.println("변경할 사번을 입력하세요.");
        while(true) {
            System.out.print("사번입력 >> ");    int a = Integer.parseInt(sc.nextLine());
            
            System.out.print("이름입력 >> ");    String b = sc.nextLine();
            System.out.print("제목 입력 >> ");    String c = sc.nextLine();
            System.out.print("월급입력 >> ");    int d = Integer.parseInt(sc.nextLine());
            System.out.print("이메일입력 >> ");    String f = sc.nextLine();
            int hit=0;
            CST=CN.prepareCall("{call GUEST_SP_UPDATE(?,?,?,?,?,?)}");
            CST.setInt(1,a);
            CST.setString(2,b);
            CST.setString(3,c);
            CST.setInt(4,d);
            CST.setInt(5,hit);
            CST.setString(6,f);
            
            CST.executeUpdate();
                 System.out.println("SP 수정 되었습니다.");
              System.out.println("==================================================");
              break;
           }
     } catch (Exception e) {System.out.println("SP 수정 실패" + e.toString());
    }
  }
  
   
   public void myexit() {
     System.out.println("7-13-Mon 프로그램을 종료합니다");
     System.exit(1);
  }//end--------------------
  
   
}/////////////////////////////////////////////class END

