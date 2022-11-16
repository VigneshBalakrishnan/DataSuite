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
	@Column(name="logonid", unique = true, nullable = false)
	private String logonId;
	
	@Id
	@Column(name="Group_Name", unique = true, nullable = false)
	private String groupName;
	
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

	public String getLogonId() {
		return logonId;
	}

	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public int getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	@Override
	public String toString() {
		return "TblDmsUserGroups [logonId=" + logonId + ", groupName=" + groupName + ", flag=" + flag + ", deletedRecord="
				+ deletedRecord + ", dateCreated=" + dateCreated + ", dateModified=" + dateModified + ", userGroupId="
				+ userGroupId + "]";
	}
}
