package com.dfx.reverseproxy.settings.userprovison.model;

import java.io.Serializable;

public class UserGroupId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String logonId;
	
	private String groupName;
	
	public UserGroupId() {}
	
	public UserGroupId(String logonId,String groupName) {
		this.setLogonId(logonId);
		this.setGroupName(groupName);
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((logonId == null) ? 0 : logonId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGroupId other = (UserGroupId) obj;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (logonId == null) {
			if (other.logonId != null)
				return false;
		} else if (!logonId.equals(other.logonId))
			return false;
		return true;
	}
	
//	public int hashCode() {
//        return (int)this.logonId.hashCode();
//    }
//
//    public boolean equals(Object obj) {
//        if (obj == this) return true;
//        if (!(obj instanceof UserGroupId)) return false;
//        UserGroupId id = (UserGroupId) obj;
//        return id.groupName.equals(this.groupName) && id.logonId.equals(this.logonId);
//    }
}
