package miniProj1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;
import org.eclipse.jdt.internal.compiler.batch.Main;

public class MemberDAO {
	private static Connection conn = null;
    private static PreparedStatement memberListPstmt = null;
    private static PreparedStatement memberViewPstmt = null;

	static {

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/miniProj_DB",
                    "bituser",
                    "1004"
            );
            System.out.println("연결 성공 대박");
            conn.setAutoCommit(false);

            memberListPstmt = conn.prepareStatement("select * from TB_MEMBER");
            memberViewPstmt = conn.prepareStatement("select * from TB_MEMBER where memberID=?");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

	public List<MemberVO> list(){
        List<MemberVO> list = new ArrayList<>();
        try {
            ResultSet rs = memberListPstmt.executeQuery();
            
            while (rs.next()) {
                MemberVO memberVO = new MemberVO(
                		  rs.getString("memberID")
                        , rs.getString("memberPW")
                        , rs.getString("memberName")
                        , rs.getString("memberADDR")
                        , rs.getString("memberPhone")
                        , rs.getString("memberGen")
                        );
                
                list.add(memberVO);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	
	public MemberVO view(String memberID) {

        MemberVO memberVO = null;
        try {
            memberViewPstmt.setString(1, memberID);

            ResultSet rs = memberViewPstmt.executeQuery();
            if (rs.next()) {
            	memberVO = new MemberVO(
                		  rs.getString("memberName")
                        , rs.getString("memberID")
                        , rs.getString("memberADDR")
                        , rs.getString("memberPhone")
                        , rs.getString("memberGen")
                		);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return memberVO;
    }

}