package com.dfx.reverseproxy.settings.userprovison.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dfx.reverseproxy.settings.userprovison.model.TBL_DFX_User_Groups;

public interface TBL_DFX_UserGroupsRepository extends JpaRepository<TBL_DFX_User_Groups,Long>{

	TBL_DFX_User_Groups findByLogonIdAndGroupName(String logonId, String groupName);

	List<TBL_DFX_User_Groups> findByGroupName(String groupName);

	List<TBL_DFX_User_Groups> findByLogonId(String logonId);

	List<TBL_DFX_User_Groups> findByLogonIdAndDeletedRecord(String logonId, boolean b);

	TBL_DFX_User_Groups findByLogonIdAndGroupNameAndDeletedRecord(String logonId, String groupName, boolean b);

	List<TBL_DFX_User_Groups> findByGroupNameAndDeletedRecord(String groupName, boolean b);

}
