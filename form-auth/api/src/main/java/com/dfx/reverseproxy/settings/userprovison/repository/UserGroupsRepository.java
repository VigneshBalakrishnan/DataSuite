package com.dfx.reverseproxy.settings.userprovison.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dfx.reverseproxy.settings.userprovison.model.TBL_DMS_User_Groups;

public interface UserGroupsRepository extends JpaRepository<TBL_DMS_User_Groups,Long> {

	List<TBL_DMS_User_Groups> findByLogonIdAndGroupName(String logonId, String groupName);

	List<TBL_DMS_User_Groups> findByGroupName(String groupName);

	List<TBL_DMS_User_Groups> findByLogonId(String logonId);

	boolean existsByLogonIdAndGroupName(String logonId, String groupName);

}
