/*
package Model2.sec02;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
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

    */
/*****************************
    * DataSource 연동 메서드
    * webapp/META-INF/context.xml
    ******************************//*

    public MemberDAO(){

        try{
            Context ctx = new InitialContext();
            Context evnContext = (Context) ctx.lookup("java:/comp/env");
            dataFactory = (DataSource) evnContext.lookup("jdbc/mariadb");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    */
/*********************
     * 회원정보 Select 메서드
     ********************//*

    public List<MemberVO> listmembers(){

        List<MemberVO> membersList = new ArrayList<MemberVO>();

        try{
            conn = dataFactory.getConnection();

            String query = "select * from t_member order by id desc";
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

}
*/
