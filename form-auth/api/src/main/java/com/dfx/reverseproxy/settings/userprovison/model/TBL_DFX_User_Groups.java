package com.dfx.reverseproxy.settings.userprovison.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TBL_DFX_User_Groups {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="logonid", nullable = false)
	private String logonId;
	
	@Column(name="Group_Name", nullable = false)
	private String groupName;
	
	@Column(name="UserGroupId", nullable = true)
	private Long userGroupId;
	
	@Column(name="DeletedRecord", nullable = false)
	private boolean deletedRecord=false;
	
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

	public boolean getDeletedRecord() {
		return deletedRecord;
	}

	public void setDeletedRecord(boolean deletedRecord) {
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

	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	@Override
	public String toString() {
		return "TBL_DFX_UserGroups [logonId=" + logonId + ", groupName=" + groupName + ", userGroupId=" + userGroupId + ", deletedRecord=" + deletedRecord + ", dateCreated=" + dateCreated
				+ ", dateModified=" + dateModified + "]";
	}

}
