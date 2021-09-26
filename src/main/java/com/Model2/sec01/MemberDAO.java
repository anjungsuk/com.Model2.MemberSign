package com.Model2.sec01;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberDAO {
    private DataSource dataFactory;
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /*****************************
    * DataSource 연동 메서드
    * webapp/META-INF/context.xml
    ******************************/
    public MemberDAO(){

        try{
            Context ctx = new InitialContext();
            Context evnContext = (Context) ctx.lookup("java:/comp/env");
            dataFactory = (DataSource) evnContext.lookup("jdbc/mariadb");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*********************
     * 회원정보 Select 메서드
     ********************/
    public List<MemberVO> listmembers(){

        List<MemberVO> membersList = new ArrayList<MemberVO>();

        try{
            conn = dataFactory.getConnection();

            String query = "select * from t_member order by joinDate desc";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                String id = rs.getString("user_id");
                String pwd = rs.getString("pwd");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date joinDate = rs.getDate("joinDate");

                MemberVO memberVO = new MemberVO(id, pwd, name, email, joinDate);
                membersList.add(memberVO);
            }
            pstmt.close();
            rs.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return membersList;
    }
    /*********************
     * 회원정보 Insert 메서드
     ********************/
    public void addMember(MemberVO memberVO)
    {
        try {
            conn = dataFactory.getConnection();
            /*파라미터로 받아온 getter로 받아옴.*/
            String id = memberVO.getUser_id();
            String pwd = memberVO.getPwd();
            String name = memberVO.getName();
            String email = memberVO.getEmail();
            /*prepareStatement 클래스 사용*/
            String query = "insert into t_member";
            query += "(user_id, pwd, name, email)";
            query += "values(?,?,?,?)";

            System.out.println("query : " + query);

            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            pstmt.setString(3, name);
            pstmt.setString(4, email);

            pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(pstmt != null)
            {
                try{
                    pstmt.close();
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
    public MemberVO modMemberForm(String user_id)
    {
        MemberVO memberID = null;

        try {
            conn = dataFactory.getConnection();

            String query = "select * from t_member where user_id = ? ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user_id);
            rs = pstmt.executeQuery();
            rs.next();
            String user_id1 = rs.getString("user_id");
            String pwd = rs.getString("pwd");
            String name = rs.getString("name");
            String email = rs.getString("email");
            Date joinDate = rs.getDate("joinDate");

            memberID = new MemberVO(user_id1, pwd, name, email, joinDate);

            pstmt.close();
            conn.close();


        }catch (SQLException e){
            e.printStackTrace();
        }

        return memberID;
    }
    /*********************
     * 회원정보 Update 메서드
     ********************/
    public void modMember(MemberVO memberVO){

        String user_id = memberVO.getUser_id();
        String pwd = memberVO.getPwd();
        String name = memberVO.getName();
        String email = memberVO.getEmail();

        try{
            conn = dataFactory.getConnection();

            String query = "update t_member set user_id = ?, pwd = ?, name = ?, email = ? where user_id =?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user_id);
            pstmt.setString(2, pwd);
            pstmt.setString(3, name);
            pstmt.setString(4, email);
            pstmt.setString(5, user_id);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    /*********************
     * 회원정보 Delete 메서드
     ********************/
    public void delMember(String user_id){

        try {
            conn = dataFactory.getConnection();

            String query = "delete from t_member where user_id = ?";

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user_id);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
