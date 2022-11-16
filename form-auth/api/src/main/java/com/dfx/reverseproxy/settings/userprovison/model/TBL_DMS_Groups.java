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
public class TBL_DMS_Groups {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Group_id", unique = true, nullable = false)
	private long groupId;
	
	@Column(name="Group_Name", nullable = false)
	private String groupName;
	
	@Column(name="Group_Access_rights", nullable = false)
	private String groupAccessRights;
	
	@Column(name="Comment", nullable=true)
	private String comment;
	
	@Column(name="flag", nullable = false)
	private char flag;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Date_Created", nullable = false)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Date_Modified", nullable=true)
	private Date dateModified;

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupAccessRights() {
		return groupAccessRights;
	}

	public void setGroupAccessRights(String groupAccessRights) {
		this.groupAccessRights = groupAccessRights;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
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
		return "TblDmsGroups [groupId=" + groupId + ", groupName=" + groupName + ", groupAccessRights=" + groupAccessRights
				+ ", comment=" + comment + ", flag=" + flag + ", dateCreated=" + dateCreated + ", dateModified="
				+ dateModified + "]";
	}
	
	
}
