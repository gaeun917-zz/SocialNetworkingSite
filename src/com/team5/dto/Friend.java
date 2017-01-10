package com.team5.dto;

import java.sql.Date;

public class Friend {
	
	private int friend_id;
	private int friendOne;	
	private int friendTwo;
	private int status;
	private Date createDate;
	
	public int getFriendOne() {
		return friendOne;
	}
	public void setFriendOne(int friendOne) {
		this.friendOne = friendOne;
	}
	public int getFriendTwo() {
		return friendTwo;
	}
	public void setFriendTwo(int friendTwo) {
		this.friendTwo = friendTwo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}
	
	
}
