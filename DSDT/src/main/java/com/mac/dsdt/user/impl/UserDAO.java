//package com.mac.dsdt.user.impl;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.web.multipart.MultipartFile;
//
//import com.mac.dsdt.common.JDBCUtil;
//import com.mac.dsdt.user.UserVO;
//
////DAO(Data Access Object)
//public class UserDAO {
//	// JDBC 관련 변수
//	private Connection conn = null;
//	private PreparedStatement stmt = null;
//	private ResultSet rs = null;
//
//	// SQL 명령어들
//	private final String USER_GET = "select * from users where u_id=? and u_pw=?";
//	private final String USER_INSERT = "insert into users(u_seq, u_name, u_gender, u_id, u_pw, u_pwc, u_addr, u_email, u_phone, u_hobby, u_introduce, u_filename) values((select nvl(max(u_seq), 0)+1 from users),?,?,?,?,?,?,?,?,?,?,?)";
//	private final String USER_UPDATE = "update users set u_pw=?, u_pwc=?, u_addr=?, u_email=?, u_phone=?, u_hobby=?, u_introduce=?, u_filename where u_id=?";
//	private final String USER_DELETE = "delete users where u_seq=?"; // seq를 기준으로 찾아서, 지운다
//	private final String USER_LIST = "select * from users order by u_seq desc";
//	private final String USER_GET_ONE = "select * from users where u_seq=?";
//
//	// CRUD 기능의 메소드 구현
//
//	// 회원 상세 조회1 - 로그인시에만 활용
//	public UserVO getUser(UserVO vo) {
//		UserVO user = null;
//		try {
//			System.out.println("===> JDBC로 getUser() 기능 처리");
//			conn = JDBCUtil.getConnection();
//			stmt = conn.prepareStatement(USER_GET);
//			stmt.setString(1, vo.getU_id());
//			stmt.setString(2, vo.getU_pw());
//			rs = stmt.executeQuery();
//			if (rs.next()) {
//				user = new UserVO();
//				user.setU_seq(rs.getInt("U_SEQ"));
//				user.setU_name(rs.getString("U_NAME"));
//				user.setU_gender(rs.getString("U_GENDER"));
//				user.setU_id(rs.getString("U_ID"));
//				user.setU_pw(rs.getString("U_PW"));
//				user.setU_addr(rs.getString("U_ADDR"));
//				user.setU_email(rs.getString("U_EMAIL"));
//				user.setU_phone(rs.getString("U_PHONE"));
//				user.setU_hobby(rs.getString("U_HOBBY"));
//				user.setU_introduce(rs.getString("U_INTRODUCE"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			JDBCUtil.close(rs, stmt, conn);
//		}
//		return user;
//	}
//	
//	// 회원 상세 조회2 - 유저전체목록에서, 상세조회 들어가서 - 수정할 때 사용
//	public UserVO getUserOne(UserVO vo) {
//		UserVO user = null;
//		try {
//			System.out.println("===> JDBC로 getUser() 기능 처리");
//			conn = JDBCUtil.getConnection();
//			stmt = conn.prepareStatement(USER_GET_ONE);
//			stmt.setInt(1, vo.getU_seq());
//			// stmt.setString(2, vo.getU_pw()); 빼기
//			rs = stmt.executeQuery();
//			if (rs.next()) {
//				user = new UserVO();
//				user.setU_seq(rs.getInt("U_SEQ"));
//				user.setU_name(rs.getString("U_NAME"));
//				user.setU_gender(rs.getString("U_GENDER"));
//				user.setU_id(rs.getString("U_ID"));
//				user.setU_pw(rs.getString("U_PW"));
//				user.setU_pwc(rs.getString("U_PWC"));
//				user.setU_addr(rs.getString("U_ADDR"));
//				user.setU_email(rs.getString("U_EMAIL"));
//				user.setU_phone(rs.getString("U_PHONE"));
//				user.setU_hobby(rs.getString("U_HOBBY"));
//				user.setU_introduce(rs.getString("U_INTRODUCE"));
//				user.setU_filename(rs.getString("U_FILENAME"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			JDBCUtil.close(rs, stmt, conn);
//		}
//		return user;
//	}
//	
//	public void insertUser(UserVO vo) { 
//		System.out.println("===> JDBC로 insertUser() 기능 처리");
//		try {
//			conn = JDBCUtil.getConnection(); // test db 가르킴
//			stmt = conn.prepareStatement(USER_INSERT);
//			stmt.setString(1, vo.getU_name()); //1번 ?
//			stmt.setString(2, vo.getU_gender()); //2번 ?
//			stmt.setString(3, vo.getU_id()); //3번 ?
//			stmt.setString(4, vo.getU_pw());
//			stmt.setString(5, vo.getU_pwc());
//			stmt.setString(6, vo.getU_addr());
//			stmt.setString(7, vo.getU_email());
//			stmt.setString(8, vo.getU_phone());
//			stmt.setString(9, vo.getU_hobby());
//			stmt.setString(10, vo.getU_introduce());
//			// getU_pic().getOriginalFilename()으로!
//			stmt.setString(11, vo.getU_pic().getOriginalFilename());
//			
//			stmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			//conn.close();
//			//stmt.close();
//			JDBCUtil.close(stmt, conn);
//		}
//	}
//	
//	public void updateUser(UserVO vo) {
//		System.out.println("===> JDBC로 updateUser() 기능 처리");
//		try {
//			conn = JDBCUtil.getConnection();
//			stmt = conn.prepareStatement(USER_UPDATE);
//			stmt.setString(1, vo.getU_pw());
//			stmt.setString(2, vo.getU_pwc());
//			stmt.setString(3, vo.getU_addr());
//			stmt.setString(4, vo.getU_email());
//			stmt.setString(5, vo.getU_phone());
//			stmt.setString(6, vo.getU_hobby());
//			stmt.setString(7, vo.getU_introduce());
//			stmt.setString(8, vo.getU_filename());
//			stmt.setString(9, vo.getU_id()); // 마지막 where문의 '?'
//			
//			stmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			JDBCUtil.close(stmt, conn);
//		}
//	}
//
//	public void deleteUser(UserVO vo) {
//		System.out.println("===> JDBC로 deleteUser() 기능 처리");
//		try {
//			conn = JDBCUtil.getConnection();
//			stmt = conn.prepareStatement(USER_DELETE);
//			stmt.setInt(1, vo.getU_seq());
//			stmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			JDBCUtil.close(stmt, conn);
//		}
//	}
//	
//	// 전체 유저 조회
//	public List<UserVO> getUserList(UserVO vo) {
//		System.out.println("===> JDBC로 getUserList() 기능 처리");
//		List<UserVO> userList = new ArrayList<UserVO>();
//		try {
//			conn = JDBCUtil.getConnection();
//			stmt = conn.prepareStatement(USER_LIST);
//			
//			rs = stmt.executeQuery();
//			while (rs.next()) {
//				UserVO user = new UserVO();
//				user.setU_seq(rs.getInt("U_SEQ")); // getXXX의 매개변수인 대문자"U_SEQ"는 DB의 컬럼명
//				user.setU_id(rs.getString("U_ID"));
//				user.setU_name(rs.getString("U_NAME"));
//				user.setU_gender(rs.getString("U_GENDER"));
//				user.setU_addr(rs.getString("U_ADDR"));
//				user.setU_email(rs.getString("U_EMAIL"));
//				user.setU_phone(rs.getString("U_PHONE"));
//				user.setU_hobby(rs.getString("U_HOBBY"));
//				user.setU_introduce(rs.getString("U_INTRODUCE"));
//				user.setU_regdate(rs.getDate("U_REGDATE"));
//				
//				userList.add(user);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			JDBCUtil.close(rs, stmt, conn);
//		}
//		return userList;
//	}
//}