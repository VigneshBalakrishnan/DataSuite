package com.dfx.reverseproxy.settings.userprovison.bean;

import java.util.Date;
import java.util.List;


public class DFXUserGroups {

	private String logonId;

	private List<String> groupNames;

	private Long userGroupId;

	private boolean deletedRecord=false;

	private Date dateCreated;

	private Date dateModified;

	public String getLogonId() {
		return logonId;
	}

	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}

	public List<String> getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(List<String> groupNames) {
		this.groupNames = groupNames;
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

	

}
