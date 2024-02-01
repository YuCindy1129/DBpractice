package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.GroupFunc;
import com.example.demo.entity.GroupFuncPK;

public interface GroupFuncRepo extends JpaRepository<GroupFunc,GroupFuncPK>{

	//輸入兩種id查詢資料
	List<GroupFunc> findByGroupidAndFuncid(long groupid, long funcid);
	
	//輸入其中一種id查詢資料
	List<GroupFunc> findByGroupid(long groupid);
	List<GroupFunc> findByFuncid(long funcid);
	
	//使用JPQL完成四種條件的查詢(PostMapping)
	@Query(value = "SELECT g FROM GroupFunc g WHERE (:groupid is null or g.groupid = :groupid)"
	+ "AND (:funcid is null or g.funcid = :funcid)")
	List<GroupFunc> idSearch(Long groupid, Long funcid);
	
	//使用JPQL完成四種條件的查詢(GetMapping)
	@Query(value = "SELECT g FROM GroupFunc g WHERE (:groupid is null or g.groupid = :groupid)"
	+ "AND (:funcid is null or g.funcid = :funcid)")
	List<GroupFunc> search(Long groupid, Long funcid);
	
	//使用page查詢(四種條件的查詢)
	Page <GroupFunc> findAll(Pageable pageable);
	@Query(value = "SELECT g FROM GroupFunc g WHERE (:groupid is null or g.groupid = :groupid)"
			+ "AND (:funcid is null or g.funcid = :funcid)",
			 countQuery ="SELECT COUNT(*)  FROM GroupFunc g WHERE (:groupid is null or g.groupid = :groupid)"
						+ "AND (:funcid is null or g.funcid = :funcid)")
	Page <GroupFunc> findByGroupidAndFuncid(Long groupid, Long funcid,Pageable pageable);
}

