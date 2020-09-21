package Room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RoomDBBean {
	
	private static RoomDBBean instance = new RoomDBBean();

	public static RoomDBBean getinstance() {
		return instance;
	}
	
	public Connection getConnection() throws Exception{
		Connection con = null;
		String url = "jdbc:mysql://203.245.44.74:3306/allintonight?serverTimezone=UTC"; 
		String user = "allintonight";
		String pwd = "team1team1";
		
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url, user, pwd);
			
			} catch (Exception e) { 
				e.printStackTrace();
			}
		return con;
	}
	
	public int addRoom(RoomBean roombean)  throws Exception{
		Connection con=null;
		PreparedStatement pstmt = null;
		String sql=null;
		int re=-1;
		
		try {
			sql="insert into room values(?,?,?,?,?,?)";
			con=getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, roombean.getRno());
			pstmt.setString(2, roombean.getRname());
			pstmt.setInt(3, roombean.getMen());
			pstmt.setInt(4, roombean.getWeekday());
			pstmt.setInt(5, roombean.getWeekend());
			pstmt.setInt(6, roombean.getSweekday());
			pstmt.executeUpdate();
			re = 1;
		}catch(Exception e) {
			e.printStackTrace();
			re = -1;
		}finally {
			con.close();
			pstmt.close();
		}
		return re;
	}
	
	
	public ArrayList<RoomBean> getAll() throws SQLException {
		Connection con=null;
		PreparedStatement pstmt = null;
		String sql=null;
		ResultSet rs=null;
		
		ArrayList<RoomBean> roomBean = new ArrayList<RoomBean>();
		
		try {
			sql="select * from room;";
			con=getConnection();
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				roomBean.add(new RoomBean(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)
										, rs.getInt(5), rs.getInt(6)));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return roomBean;
		
	}
	@SuppressWarnings("null")
	public ResultSet selectRoom(int rno) throws SQLException {
		Connection con=null;
		Statement stmt = null;
		String sql=null;
		ResultSet rs = null;
		RoomBean roombean=null;
		
		try {
			sql="select * from room where rno="+rno;
			con=getConnection();
			stmt=con.createStatement();
			rs = stmt.executeQuery(sql);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(rno);
		}
		return rs;
	}
	
	public int deleteRoom(int rno) {
		Connection con=null;
		PreparedStatement pstmt = null;
		String sql=null;
		int re=-1;
		
		try {
			sql="delete from room where rno=?";
			con=getConnection();
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, rno);
			pstmt.executeUpdate();
			re = 1;
		}catch(Exception e) {
			e.printStackTrace();
			re = -1;
		}
		return re;
	}
}
