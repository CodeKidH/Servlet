package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import VO.BoardVO;


public class BoardDao {
	
	
	public int insertDate(BoardVO vo) throws ClassNotFoundException, SQLException{
		
		int result = 0;
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/toby","root","1111");
		
		PreparedStatement pstmt = null;
		String sql;
		
		try{
			sql = "INSERT INTO board(num,name,subject,content,pwd,dated,ipaddr,hitcount)"
					+ "VALUES(?,?,?,?,?,now(),?,0)";
			
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1,vo.getNum());
			pstmt.setString(2,vo.getName());
			pstmt.setString(3,vo.getSubject());
			pstmt.setString(4, vo.getContent());
			pstmt.setString(5,vo.getPwd());
			pstmt.setString(6, vo.getIpAddr());
			result = pstmt.executeUpdate();
			pstmt.close();
			c.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return result;
	}
	
	//전체자료읽기 데이터
	public int getDataCount() throws ClassNotFoundException, SQLException{
		
		int result = 0;
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/toby","root","1111");
		String sql;
		ResultSet rs = null;
		Statement stmt = null;
		
		try{
			
			sql="SELECT ifnull(COUNT(*),0) as cnt FROM board";
			stmt = c.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				result = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			c.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	//특정페이지 자료읽기 메소드
	public List<BoardVO> getListData(int start, int end) throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/toby","root","1111");
		
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try{
			sql = "SELECT * FROM board WHERE num >= ? and num <= ?";
			
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1,start);
			pstmt.setInt(2,end);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				BoardVO vo = new BoardVO();
				vo.setNum(rs.getInt("num"));
				vo.setName(rs.getString("name"));
				vo.setSubject(rs.getString("subject"));
				vo.setDated(rs.getString("dated"));
				vo.setHitcount(rs.getInt("hitcount"));
				list.add(vo);
			}
			
			rs.close();
			pstmt.close();
			c.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return list;
		
	}
	
	//글번호 지정을 위한 기존 번호 최대값 얻는 메소드
	public int getMaxNum() throws ClassNotFoundException, SQLException{
		
		int result = 0;
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/toby","root","1111");
		Statement stmt = null;
		ResultSet rs = null;
		String sql;
		
		try{
			sql ="SELECT ifnull(MAX(num),0)FROM board";
			stmt = c.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				result = rs.getInt(1);
			}
			
			rs.close();
			stmt.close();
			c.close();
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public int deleteData(int num, String pwd) throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/toby","root","1111");		
		PreparedStatement pstmt = null;
		String sql;
		int result = 0;
		
		try{
			
			sql = "delete from board where num = ? and pwd = ?";
			pstmt = c.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setString(2, pwd);
			result = pstmt.executeUpdate();
			pstmt.close();
			c.close();
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return result;
	}
	
public int deleteData(int num) throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/toby","root","1111");		
		PreparedStatement pstmt = null;
		String sql;
		int result = 0;
		
		try{
			
			sql = "delete from board where num = ?";
			pstmt = c.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			pstmt.close();
			c.close();
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	//특정 자료 읽기
	public BoardVO getReadData(int num) throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/toby","root","1111");
		BoardVO vo = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try{
			sql = "select num, name,subject,content, DATE_FORMAT(SYSDATE(),'%Y-%m-%d')as dated, ipaddr, hitcount "
					+ "from board where num = ?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				vo = new BoardVO();
				vo.setNum(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setDated(rs.getString(5));
				vo.setIpAddr(rs.getString(6));
				vo.setHitcount(rs.getInt(7));
			}
			
			rs.close();
			pstmt.close();
			c.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return vo;
	}
	
	//수정메소드
	public int updateData(BoardVO vo) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/toby","root","1111");
		PreparedStatement pstmt = null;
		String sql;
		int result = 0;
		
		try{
			sql = "update board set name=?, subject=?,content=?"
					+ "where num =? and pwd = ?	";
			
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getSubject());
			pstmt.setString(3, vo.getContent());
			pstmt.setInt(4, vo.getNum());
			pstmt.setString(5, vo.getPwd());
			result = pstmt.executeUpdate();
			pstmt.close();
			c.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return result;
	}
	
}
