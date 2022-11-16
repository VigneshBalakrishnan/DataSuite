package com.dfx.reverseproxy.settings.userprovison.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dfx.reverseproxy.settings.userprovison.model.TBL_DMS_USERS;

@Transactional
public interface UsersRepository extends JpaRepository<TBL_DMS_USERS, Long> {

	TBL_DMS_USERS findByLogonid(String logonId);

	TBL_DMS_USERS findByUserId(int userId);

	void deleteByLogonid(String string);

	List<TBL_DMS_USERS> findByLogonidContainingIgnoreCase(String userName);

}
