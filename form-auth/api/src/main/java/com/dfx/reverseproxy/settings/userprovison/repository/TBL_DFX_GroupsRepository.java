package com.dfx.reverseproxy.settings.userprovison.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dfx.reverseproxy.settings.userprovison.model.TBL_DFX_Groups;

public interface TBL_DFX_GroupsRepository extends JpaRepository<TBL_DFX_Groups,Long>{

	TBL_DFX_Groups findByGroupName(String groupName);

}
