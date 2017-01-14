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
	// 현철코드
	public int insertUpload(Upload upload) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int uploadNumber = -1;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql =
					"INSERT INTO upload (uploadno, title, uploader, content) " +
					"VALUES (upload_sequence.nextVal, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, upload.getTitle());
			pstmt.setString(2, upload.getUploader());
			pstmt.setString(3, upload.getContent());

			pstmt.executeUpdate();
			pstmt.close();

			// 자료번호(auto-incrimination) 조회
			sql =   "SELECT upload_sequence.currVal " +
					"FROM DUAL";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next())
				uploadNumber = rs.getInt(1);
		} catch (Exception ex) {
			uploadNumber = -1;
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
		return uploadNumber;
	}

	// DML(insert): parameter를 DTO(Object)로 해서 모든 column에 접근 할 수 있게함
	public void insertUploadFile(UploadFile file) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql =
					"INSERT INTO upload_file (upfile_no, savedfile_name, userfile_name, boardno, member_id) " +
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
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
	}
/*
	public void insertUploadFile2(UploadFile file) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql =
					"INSERT INTO UPLOAD_FILE (upfile_no, savedfile_name, userfile_name, member_id ) " +
							"VALUES (UPLOAD_FILE_seq.nextVal, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, file.getSavedFileName());
			pstmt.setString(2, file.getUserFileName());
			pstmt.setInt(3, file.getMemberId());

			pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try { pstmt.close(); } catch (Exception ex) { }
			try { conn.close(); } catch (Exception ex) { }
		}
	}
*/

	public List<Upload> selectUploadList() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Upload> uploads = new ArrayList<>();

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql = "SELECT uploadno, title, regdate " +
						 "FROM upload " +
						 "WHERE deleted = '0'";
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
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
		return uploads;// return 받을때는 ArrayList로 받음
	}


	public Upload selectUploadByUploadNo(int uploadNo) {
		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null, rs2 = null;
		Upload upload = null;
		ArrayList<UploadFile> files = new ArrayList<>();

		try {
			conn = ConnectionHelper.getConnection("oracle");

			String sql =
					"SELECT uploadno, title, uploader, content, regdate, readcount " +
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

				// upload에 file upload 자료 받고-> uploadfile(sub-table)의 데이터 받아오려고 함:
				// (둘다 pk(parameter:uploadNO)가 같아서 동시 작업 가능)
				sql =
						"SELECT uploadfileno, uploadno, savedfilename, userfilename, downloadcount " +
						"FROM uploadfile " +
						"WHERE uploadno = ?";

				// pstmt close()하지 않고, 새로운 pstmt 불러와서 데이터 저장
				pstmt2 = conn.prepareStatement(sql);

				pstmt2.setInt(1, uploadNo);

				rs2 = pstmt2.executeQuery();

				while (rs2.next()) {
					 UploadFile file = new UploadFile();
								file.setUpfileNo(rs2.getInt(1));
								file.setSavedFileName(rs2.getString(3));
								file.setUserFileName(rs2.getString(4));
								files.add(file);//ArrayList<uploadfile>에 저장해서 upload에 저장
				}
				upload.setFiles(files);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs2.close();
			} catch (Exception ex) {
			}
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt2.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
		return upload;
	}


/*
	public ArrayList<UploadFile> getUploadFilesByUploadNo(int uploadNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<UploadFile> files = new ArrayList<>();
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			String sql = 
						"SELECT uploadfileno, uploadno, savedfilename, userfilename " +
						"FROM uploadfile " +
						"WHERE uploadfileno = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uploadNo);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				UploadFile file = new UploadFile();
				file.setUpfileNo(rs.getInt(1));
				// 여기서 숫자를 바꿔야 함
				//file.setUploadNo(rs.getInt(2));
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
*/


	public ArrayList<UploadFile> selectAllUploadFiles() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<UploadFile> files = new ArrayList<>();

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
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
		return files;
	}

	// 위에서 UploadNo로 데이터 불러올때 uploadfile 데이터도 같이 불러와서, 따로 이 method를 부를 일이 없음.
/*
	public UploadFile getUploadFileByUploadFileNo(int uploadFileNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UploadFile file = null;
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			
			String sql = 
						"SELECT uploadfileno, uploadno, savedfilename, userfilename " +
						"FROM uploadfile " +
						"WHERE uploadfileno = ?";

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
*/

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
			try {
				pstmt.close();
			} catch (Exception ex) {
			}
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
	}
}

/*
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
*/


