package com.example.demo.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.GroupFuncRepo;
import com.example.demo.dto.PageResult;
import com.example.demo.entity.GroupFunc;
import com.example.demo.entity.GroupFuncPK;

@Service
public class GroupFuncService {
	
	@Autowired
    GroupFuncRepo groupFuncR;
	
    public Optional<GroupFunc> findById(long groupid, long funcid) {
        return groupFuncR.findById(new GroupFuncPK(groupid, funcid));
    }
    
    //用page查詢所有資料(設定輸入輸出格式class)(list型態回傳)
    public PageResult findAllList(Pageable pageable) {
    	//page查詢
        Page<GroupFunc> page = groupFuncR.findAll(pageable);
        //PageResult輸出的型態
        PageResult pageResult = new PageResult();
        //設定PageResult class中的參數
        //總筆數
    	pageResult.setTotal(page.getTotalElements());
    	//總頁數
    	pageResult.setTotalpage(page.getTotalPages());
    	//結果
    	pageResult.setResult(page.getContent());
        return pageResult;
    }
    //用page查詢所有資料(page型態回傳)
    public Page<GroupFunc> findAllPage(Pageable pageable){
    	return groupFuncR.findAll(pageable);
    }
    
    //用page以groupid和funcid為條件查詢資料(設定輸入輸出格式class)(list型態回傳)
    public PageResult findGFList(Long groupid, Long funcid, Pageable pageable) {
    	Page<GroupFunc> page = groupFuncR.findByGroupidAndFuncid(groupid, funcid, pageable);
    	PageResult pageResult = new PageResult();
    	pageResult.setTotal(page.getTotalElements());
    	pageResult.setTotalpage(page.getTotalPages());
    	pageResult.setResult(page.getContent());
        return pageResult;
    }
    //用page以groupid和funcid為條件查詢資料(page型態回傳)
    public Page<GroupFunc> findGFPage(Long groupid, Long funcid, Pageable pageable){
    	return groupFuncR.findByGroupidAndFuncid(groupid, funcid, pageable);
    }
    
    //使用JPQL查詢資料(四種方式)用PostMapping(設定輸入class)
    public List<GroupFunc> search(Long groupid, Long funcid){
		return groupFuncR.search(groupid, funcid);
	}
    //使用JPQL查詢資料(四種方式)用GetMapping
    public List<GroupFunc> idSearch(Long groupid, Long funcid){
		return groupFuncR.idSearch(groupid, funcid);
	}
    
    //不輸入條件，直接顯示所有資料
	public List<GroupFunc> getAllGroupFunc() {
	    return groupFuncR.findAll();
	}
	// 根據groupid和funcid查詢
	public List<GroupFunc> findByGroupidAndFuncid(long groupid, long funcid) {
        return groupFuncR.findByGroupidAndFuncid(groupid, funcid);
    }
	// 根據groupid查詢
	public List<GroupFunc> findByGroupid(long groupid) {
	    List<GroupFunc> result = groupFuncR.findByGroupid(groupid);
	    if (result.isEmpty()) {
	        return null;
	    }
	    return result;
	}
	// 根據funcid查詢
	public List<GroupFunc> findByFuncid(long groupid) {
	    return groupFuncR.findByFuncid(groupid);
	}
	
	//刪除資料
	public List<GroupFunc> deleteGF(long groupid, long funcid) {
		groupFuncR.deleteById(new GroupFuncPK(groupid, funcid));
	    return groupFuncR.findAll();
	}
	
	//新增資料
	public GroupFunc insert(GroupFunc inGF) {
	    Optional<GroupFunc> igf = groupFuncR.findById(new GroupFuncPK(inGF.getGroupid(), inGF.getFuncid()));
	    //判斷資料庫中是否存在新增的資料
	    if (igf.isEmpty()) {
	    	//設定自動抓取新增當下的時間
	    	inGF.setCdate(Timestamp.valueOf(LocalDateTime.now()));
	        groupFuncR.save(inGF);
	        return inGF; 
	    }
	    else {
	    	return null;  
		}
	}
	
	//更新資料
	public GroupFunc update(GroupFunc upGF) {
	    Optional<GroupFunc> ugf = groupFuncR.findById(new GroupFuncPK(upGF.getGroupid(), upGF.getFuncid()));
	    //判斷資料庫中是否存在新增的資料
	    if (ugf.isPresent()) {
	    	//設定自動抓取更新當下的時間
	    	upGF.setCdate(Timestamp.valueOf(LocalDateTime.now()));
	        groupFuncR.save(upGF);
	        return upGF; 
	    }
	    else {
	    	return null;  
		}
	}
	
	
	
	
	//新增資料
	public Optional<GroupFunc> insertGF(String groupid, String funcid, String authtype, Timestamp cdate) {
	    try {
	        if (groupid == null || funcid == null) {
	            // 返回一個Optional.empty()表示輸入的pk無效
	            return Optional.empty();
	        }
	        long groupId = Long.parseLong(groupid);
	        long funcId = Long.parseLong(funcid);

	        // 檢查是否已經存在相同的pk
	        Optional<GroupFunc> existingData = groupFuncR.findById(new GroupFuncPK(groupId, funcId));
	        if (existingData.isPresent()) {
	            // 如果已存在相同的pk，返回 Optional.empty() 表示新增失敗
	            return Optional.empty();
	        }
	        // 創建新的GroupFuncPK
	        GroupFuncPK groupFuncPK = new GroupFuncPK();
	        groupFuncPK.setGroupid(groupId);
	        groupFuncPK.setFuncid(funcId);
	        // 創建新的GroupFunc，並設定GroupFuncPK
	        GroupFunc newGroupFunc = new GroupFunc();
	        newGroupFunc.setGroupid(groupId);
	        newGroupFunc.setFuncid(funcId);
	        newGroupFunc.setAuthtype(authtype);
	        newGroupFunc.setCdate(cdate);
	        groupFuncR.save(newGroupFunc);
	        // 返回 Optional.of(newGroupFunc) 表示新增成功
	        return Optional.of(newGroupFunc);
	    } catch (NumberFormatException e) {
	        // 返回一個 Optional.empty() 表示轉換失敗
	        return Optional.empty();
	    }
	}

	//更新資料
	public Optional<GroupFunc> updateGF(String groupid, String funcid, String authtype, Timestamp cdate){
		try {
			if (groupid == null || funcid == null) {
	            // 返回一個 Optional.empty() 表示輸入的主鍵值無效
	            return Optional.empty();
	        }
	        long groupId = Long.parseLong(groupid);
	        long funcId = Long.parseLong(funcid);
	        Optional<GroupFunc> existingData = groupFuncR.findById(new GroupFuncPK(groupId, funcId));
	        if (existingData.isPresent()) {
	            // 如果已存在相同的pk，進行更新
	            GroupFunc existingGroupFunc = existingData.get();
	            existingGroupFunc.setAuthtype(authtype);
	            existingGroupFunc.setCdate(cdate);
	            // 保存到資料庫
	            groupFuncR.save(existingGroupFunc);
	            // 返回Optional.of(existingGroupFunc)表示更新成功
	            return Optional.of(existingGroupFunc);
	        }
	        else {
	            // 如果不存在相同的 pk，這裡可以選擇返回一個默認值，或者根據具體需求返回空的 Optional
	            return Optional.empty();
	        }
		} catch (NumberFormatException e) {
	        // 返回一個Optional.empty()表示轉換失敗
	        return Optional.empty();
		}
	}
	
	//新增及更新資料(寫一起)
	public Optional<GroupFunc> insertOrUpdateGF(String groupid, String funcid, String authtype, Timestamp cdate) {
	    try {
	        if (groupid == null || funcid == null) {
	            // 返回一個 Optional.empty() 表示輸入的主鍵值無效
	            return Optional.empty();
	        }
	        long groupId = Long.parseLong(groupid);
	        long funcId = Long.parseLong(funcid);
	        // 檢查是否已經存在相同的pk
	        Optional<GroupFunc> existingData = groupFuncR.findById(new GroupFuncPK(groupId, funcId));
	        if (existingData.isPresent()) {
	            // 如果已存在相同的pk，進行更新
	            GroupFunc existingGroupFunc = existingData.get();
	            existingGroupFunc.setAuthtype(authtype);
	            existingGroupFunc.setCdate(cdate);
	            // 保存到資料庫
	            groupFuncR.save(existingGroupFunc);
	            // 返回Optional.of(existingGroupFunc)表示更新成功
	            return Optional.of(existingGroupFunc);
	        } else {
	            // 創建新的GroupFuncPK
	            GroupFuncPK groupFuncPK = new GroupFuncPK();
	            groupFuncPK.setGroupid(groupId);
	            groupFuncPK.setFuncid(funcId);

	            // 創建新的GroupFunc，並設定GroupFuncPK
	            GroupFunc newGroupFunc = new GroupFunc();
	            newGroupFunc.setGroupid(groupId);
	            newGroupFunc.setFuncid(funcId);
	            newGroupFunc.setAuthtype(authtype);
	            newGroupFunc.setCdate(cdate);
	            // 保存到資料庫
	            groupFuncR.save(newGroupFunc);

	            // 返回Optional.of(newGroupFunc)表示新增成功
	            return Optional.of(newGroupFunc);
	        }
	    } catch (NumberFormatException e) {
	        // 返回一個Optional.empty()表示轉換失敗
	        return Optional.empty();
	    }
	}
	
	//新增及更新資料(分開寫)
	public Optional<GroupFunc> insertOrUpdateGF1(String groupid, String funcid, String authtype, Timestamp cdate) {
	    try {
	        if (groupid == null || funcid == null) {
	            // 返回一個Optional.empty()表示輸入的主鍵值無效
	            return Optional.empty();
	        }

	        long groupId = Long.parseLong(groupid);
	        long funcId = Long.parseLong(funcid);

	        // 檢查是否已經存在相同的pk
	        Optional<GroupFunc> existingData = groupFuncR.findById(new GroupFuncPK(groupId, funcId));

	        if (existingData.isPresent()) {
	            // 如果已存在相同的pk，進行更新
	            return updateGroupFunc(existingData.get(), authtype, cdate);
	        } else {
	            // 如果不存在相同的pk，進行新增
	            return insertGroupFunc(groupId, funcId, authtype, cdate);
	        }
	    } catch (NumberFormatException e) {
	        // 返回一個Optional.empty()表示轉換失敗
	        return Optional.empty();
	    }
	}
	//更新的部分
	private Optional<GroupFunc> updateGroupFunc(GroupFunc existingGroupFunc, String authtype, Timestamp cdate) {
	    existingGroupFunc.setAuthtype(authtype);
	    existingGroupFunc.setCdate(cdate);
	    // 保存到資料庫
	    groupFuncR.save(existingGroupFunc);
	    // 返回Optional.of(existingGroupFunc)表示更新成功
	    return Optional.of(existingGroupFunc);
	}
	//新建的部分
	private Optional<GroupFunc> insertGroupFunc(long groupId, long funcId, String authtype, Timestamp cdate) {
	    // 創建新的GroupFuncPK
	    GroupFuncPK groupFuncPK = new GroupFuncPK();
	    groupFuncPK.setGroupid(groupId);
	    groupFuncPK.setFuncid(funcId);

	    // 創建新的GroupFunc，並設定GroupFuncPK
	    GroupFunc newGroupFunc = new GroupFunc();
	    newGroupFunc.setGroupid(groupId);
	    newGroupFunc.setFuncid(funcId);
	    newGroupFunc.setAuthtype(authtype);
	    newGroupFunc.setCdate(cdate);
	    // 保存到資料庫
	    groupFuncR.save(newGroupFunc);

	    // 返回Optional.of(newGroupFunc)表示新增成功
	    return Optional.of(newGroupFunc);
	}





}
