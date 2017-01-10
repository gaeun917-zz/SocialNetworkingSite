package com.team5.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.team5.dao.ConnectionHelper;
import com.team5.dto.Upload;
import com.team5.dto.UploadFile;

public class UploadDao {
	
	public int insertUpload(Upload upload) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int uploadNumber = -1;
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			String sql = 
				"INSERT INTO upload " + 
				"(uploadno, title, uploader, content) " +
				"VALUES (upload_sequence.nextVal, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, upload.getTitle());
			pstmt.setString(2, upload.getUploader());
			pstmt.setString(3, upload.getContent());
			pstmt.executeUpdate();
			pstmt.close();			
			//아래는 등록된 자료의 자료번호(자동 증가된 값)를 조회하는 코드
			sql = "SELECT upload_sequence.currVal FROM DUAL";
			pstmt = conn.prepareStatement(sql);
		    rs = pstmt.executeQuery();
		    if (rs.next())
		    	uploadNumber = rs.getInt(1);
		} catch (Exception ex) {
			uploadNumber = -1;
			ex.printStackTrace();
		} finally {
			try { rs.close(); } catch (Exception ex) { }
			try { pstmt.close(); } catch (Exception ex) { }
			try { conn.close(); } catch (Exception ex) { }
		}
		
		return uploadNumber;
	}
	
	public void insertUploadFile(UploadFile file) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			String sql = 

				"INSERT INTO upload_file " + 
				"(upfile_no, savedfile_name, userfile_name, boardno, member_id) " +
				"VALUES (UPLOAD_FILE_SEQ.nextVal, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);


			pstmt.setString(1, file.getSavedFileName());
			pstmt.setString(2, file.getUserFileName());
			pstmt.setInt(3, file.getBoardNo());
			pstmt.setInt(4, file.getMemberId());

			
			pstmt.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { pstmt.close(); } catch (Exception ex) { }
			try { conn.close(); } catch (Exception ex) { }
		}		
		
	}
	
	public List<Upload> selectUploadList() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Upload> uploads = new ArrayList<Upload>();
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			String sql = "SELECT uploadno, title, regdate from upload where deleted = '0'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Upload upload = new Upload();
				upload.setUploadNo(rs.getInt(1));
				upload.setTitle(rs.getString(2));
				upload.setRegDate(rs.getDate(3));
				uploads.add(upload);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally { 
			try { rs.close(); } catch (Exception ex) { }
			try { pstmt.close(); } catch (Exception ex) { }
			try { conn.close(); } catch (Exception ex) { }
		}
		
		return uploads;
				
	}
	
	public Upload selectUploadByUploadNo(int uploadNo) {
		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;;
		ResultSet rs = null, rs2 = null;
		Upload upload = null;
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			String sql = 
				"SELECT uploadno,title,uploader, " + 
				"content, regdate, readcount " +
				"FROM upload " + 
				"WHERE uploadno = ? AND deleted = '0'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uploadNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				upload = new Upload();
				upload.setUploadNo(uploadNo);
				upload.setTitle(rs.getString(2));
				upload.setUploader(rs.getString(3));
				upload.setContent(rs.getString(4));
				upload.setRegDate(rs.getDate(5));
				upload.setReadCount(rs.getInt(6));
				
				sql = 
					"SELECT uploadfileno, uploadno, savedfilename, userfilename, downloadcount " + 
					"FROM uploadfile WHERE uploadno = ?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, uploadNo);
				rs2 = pstmt2.executeQuery();
				ArrayList<UploadFile> files = new ArrayList<UploadFile>();
				while (rs2.next()) {
					UploadFile file = new UploadFile();
					file.setUpfileNo(rs2.getInt(1));
					/*file.setUploadNo(rs2.getInt(2));*/
					file.setSavedFileName(rs2.getString(3));
					file.setUserFileName(rs2.getString(4));
					/*file.setDownloadCount(rs2.getInt(5));*/
					files.add(file);					
				}
				upload.setFiles(files);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { rs2.close(); } catch (Exception ex) {}
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt2.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		return upload;
		
	}
	
	public ArrayList<UploadFile> getUploadFilesByUploadNo(int uploadNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<UploadFile> files = new ArrayList<UploadFile>();
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			String sql = 
				"SELECT uploadfileno, uploadno, savedfilename, userfilename " + 
				"FROM uploadfile WHERE uploadfileno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uploadNo);
			rs = pstmt.executeQuery();
				
			while (rs.next()) {
				UploadFile file = new UploadFile();
				file.setUpfileNo(rs.getInt(1));
//-------------------여기서 숫자를 바꿔야 합니다 ----------------------------------------------------------//
				/*file.setUploadNo(rs.getInt(2));*/
				file.setSavedFileName(rs.getString(3));
				file.setUserFileName(rs.getString(4));
				files.add(file);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		return files;
		
	}
	
	
	
	public ArrayList<UploadFile> selectAllUploadFiles() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<UploadFile> files = new ArrayList<UploadFile>();
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			String sql = 
				"SELECT upfile_no, savedfile_name, member_id, boardno " + 
				"FROM upload_file";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
				
			while (rs.next()) {
				UploadFile file = new UploadFile();
				file.setUpfileNo(rs.getInt(1));
				file.setSavedFileName(rs.getString(2));
				file.setMemberId(rs.getInt(3));
				file.setBoardNo(rs.getInt(4));
				files.add(file);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		return files;
		
	}
	
	public UploadFile getUploadFileByUploadFileNo(int uploadFileNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UploadFile file = null;
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			String sql = 
				"SELECT uploadfileno, uploadno, savedfilename, userfilename " + 
				"FROM uploadfile WHERE uploadfileno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uploadFileNo);
			rs = pstmt.executeQuery();
				
			if (rs.next()) {
				file = new UploadFile();
				file.setUpfileNo(rs.getInt(1));
				//file.setUploadNo(rs.getInt(2));
				file.setSavedFileName(rs.getString(3));
				file.setUserFileName(rs.getString(4));
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		return file;
		
	}
	
	public void increaseUploadReadCount(int uploadNo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			String sql = 
				"UPDATE upload " + 
				"SET readcount = readcount + 1 " +
				"WHERE uploadno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uploadNo);
			pstmt.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { pstmt.close(); } catch (Exception ex) { }
			try { conn.close(); } catch (Exception ex) { }
		}		
		
	}
	
	public void increaseUploadFileDownloadCount(int uploadFileNo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			String sql = 
				"UPDATE uploadfile " + 
				"SET downloadcount = downloadcount + 1 " +
				"WHERE uploadfileno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uploadFileNo);
			pstmt.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { pstmt.close(); } catch (Exception ex) { }
			try { conn.close(); } catch (Exception ex) { }
		}		
		
	}
	
}
























