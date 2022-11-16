package com.dfx.reverseproxy.settings.userprovison.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(UserGroupId.class)
public class TBL_DMS_User_Groups{
	
	@Id
	@Column(name="DomainUser", unique = true, nullable = false)
	private String domainUser;
	
	@Id
	@Column(name="Group_Name", unique = true, nullable = false)
	private String groupName;
	
	@Column(name="logonid", nullable = false)
	private String logonId;
	
	@Column(name="UserName", nullable = true)
	private String userName;
	
	@Column(name="UserGroupId", nullable = true)
	private int userGroupId;
	
	@Column(name="flag", nullable = false)
	private char flag;
	
	@Column(name="DeletedRecord", nullable = false)
	private char deletedRecord;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Date_Created", nullable = true)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Date_Modified", nullable = true)
	private Date dateModified;

	public String getDomainUser() {
		return domainUser;
	}

	public void setDomainUser(String domainUser) {
		this.domainUser = domainUser;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getLogonId() {
		return logonId;
	}

	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	public char getDeletedRecord() {
		return deletedRecord;
	}

	public void setDeletedRecord(char deletedRecord) {
		this.deletedRecord = deletedRecord;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@Override
	public String toString() {
		return "TBL_DMS_User_Groups [domainUser=" + domainUser + ", groupName=" + groupName + ", logonId=" + logonId
				+ ", userName=" + userName + ", userGroupId=" + userGroupId + ", flag=" + flag + ", deletedRecord="
				+ deletedRecord + ", dateCreated=" + dateCreated + ", dateModified=" + dateModified + "]";
	}

	
}
