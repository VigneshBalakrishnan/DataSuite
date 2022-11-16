package com.dfx.reverseproxy.settings.userprovison.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TBL_DFX_Groups")
public class TBL_DFX_Groups {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Group_id", unique = true, nullable = false)
	private long groupId;
	
	@Column(name="Group_Name", nullable = false)
	private String groupName;
	
	@Column(name="Comment")
	private String comment;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Date_Created", nullable = false)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Date_Modified")
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
		return "TBL_DFX_Groups [groupId=" + groupId + ", groupName=" + groupName + ", comment=" + comment + ", dateCreated=" + dateCreated
				+ ", dateModified=" + dateModified + "]";
	}
}
