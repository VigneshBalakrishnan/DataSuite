package com.dfx.reverseproxy.settings.userprovison.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dfx.reverseproxy.settings.userprovison.model.TBL_DMS_Groups;

public interface GroupsRepository extends JpaRepository<TBL_DMS_Groups,Long>{

	TBL_DMS_Groups findByGroupId(long groupId);

	TBL_DMS_Groups findByGroupNameAndGroupAccessRights(String groupName, String groupAccessRights);

	TBL_DMS_Groups findByGroupName(String groupName);

}
