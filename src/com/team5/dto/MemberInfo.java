package com.team5.dto;

import java.sql.Date;

public class MemberInfo {

    private int memberId;//PK & FK 이렇게되면, FK를 주게된 Member table이 삭제되면 memberInfo도 PK없어져서 데이터 같이 잃음
    private String location;
    private String highschool;
    private String university;
    private String phone;
    private Date createDate;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHighschool() {
        return highschool;
    }

    public void setHighschool(String highschool) {
        this.highschool = highschool;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


}
