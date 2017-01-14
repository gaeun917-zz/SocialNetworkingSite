package com.team5.dto;

public class UploadFile {

    private int upfileNo;//PK&FK
    private int boardNo;//FK
    private int memberId;//FK
    private String savedFileName;
    private String userFileName;


    public int getUpfileNo() {
        return upfileNo;
    }

    public void setUpfileNo(int upfileNo) {
        this.upfileNo = upfileNo;
    }

    public String getSavedFileName() {
        return savedFileName;
    }

    public void setSavedFileName(String savedFileName) {
        this.savedFileName = savedFileName;
    }

    public String getUserFileName() {
        return userFileName;
    }

    public void setUserFileName(String userFileName) {
        this.userFileName = userFileName;
    }

    public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }


}
