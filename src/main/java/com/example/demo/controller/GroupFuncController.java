package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PageResult;
import com.example.demo.dto.PageReturn;
import com.example.demo.entity.GroupFunc;
import com.example.demo.entity.GroupFuncPK;
import com.example.demo.service.GroupFuncService;

@RestController
@RequestMapping("/api/groupfunc")
public class GroupFuncController {

    @Autowired
    private GroupFuncService groupFuncS;
    
    @GetMapping("/findById")
    public ResponseEntity<GroupFunc> findById(
            @RequestParam long groupid,
            @RequestParam long funcid) {
	    Optional<GroupFunc> result = groupFuncS.findById(groupid, funcid);
	
	    if (result.isPresent()) {
	        return new ResponseEntity<>(result.get(), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
    }
    
    //用page查詢所有資料
    @PostMapping("/findalllist")
	public PageResult getAllList(@RequestBody PageReturn pageReturn) {
    	int page = pageReturn.getPage();
    	int size = pageReturn.getSize();
	    Pageable pageable = PageRequest.of(page, size);
	    return groupFuncS.findAllList(pageable);
	}
    @GetMapping("/findallpage")
	public Page<GroupFunc> getAllPage(@RequestParam int page,@RequestParam int size) {
	    Pageable pageable = PageRequest.of(page, size);
	    return groupFuncS.findAllPage(pageable);
	}
    
    //用page以groupid和funcid為條件查詢資料
    @PostMapping("/findgflist")
	public PageResult getGFList(@RequestBody PageReturn pageReturn) {
    	Long groupid = pageReturn.getGroupid();
    	Long funcid = pageReturn.getFuncid();
    	int page = pageReturn.getPage();
    	int size = pageReturn.getSize();
	    Pageable pageable = PageRequest.of(page, size);
	    return groupFuncS.findGFList(groupid, funcid, pageable);
	}
    @GetMapping("/findgfpage")
	public Page<GroupFunc> getGFPage(
			@RequestParam(required = false) Long groupid,
            @RequestParam(required = false) Long funcid,
            @RequestParam int page,
            @RequestParam int size) {
	    Pageable pageable = PageRequest.of(page, size);
	    return groupFuncS.findGFPage(groupid, funcid, pageable);
	}
    
  	@PostMapping(value = "/search")
  	public List<GroupFunc> earch(@RequestBody GroupFuncPK groupFuncPK){
  		Long groupid = groupFuncPK.getGroupid();
  		Long funcid = groupFuncPK.getFuncid();
  		return groupFuncS.search(groupid, funcid);
  	}

  	@GetMapping(value = "/idsearch")
  	public List<GroupFunc> idSearch(@RequestParam(required = false) Long groupid,
            @RequestParam(required = false) Long funcid){
  		
  		return groupFuncS.idSearch(groupid, funcid);
  	}
    
    @GetMapping("/findByGroupidAndFuncid")
    public List<GroupFunc> findByGroupidAndFuncid(
            @RequestParam(required = false) Long groupid,
            @RequestParam(required = false) Long funcid) {

        if (groupid == null && funcid == null) {
            // 如果兩個都沒有輸入，列出全部資料
            return groupFuncS.getAllGroupFunc();
        } else if (groupid != null && funcid == null) {
            // 只輸入了 groupid
            return groupFuncS.findByGroupid(groupid);
        } else if (groupid == null && funcid != null) {
            // 只輸入了 funcid
            return groupFuncS.findByFuncid(funcid);
        } else {
            // 同時輸入了 groupid 和 funcid
            return groupFuncS.findByGroupidAndFuncid(groupid, funcid);
        }
    }

    
    
//    @GetMapping("/findByGroupidAndFuncid")
//    public ResponseEntity<?> findByGroupidAndFuncid(
//            @RequestParam(required = false) Long groupid,
//            @RequestParam(required = false) Long funcid) {
//
//        if (groupid == null && funcid == null) {
//            // 如果兩個都沒有輸入，列出全部資料
//            List<GroupFunc> allData = groupFuncS.getAllGroupFunc();
//            return new ResponseEntity<>(allData, HttpStatus.OK);
//        } else if (groupid != null && funcid == null) {
//            // 只輸入了 groupid
//            List<GroupFunc> resultByGroupid = groupFuncS.findByGroupid(groupid);
//            return new ResponseEntity<>(resultByGroupid, HttpStatus.OK);
//        } else if (groupid == null && funcid != null) {
//            // 只輸入了 funcid
//            List<GroupFunc> resultByFuncid = groupFuncS.findByFuncid(funcid);
//            return new ResponseEntity<>(resultByFuncid, HttpStatus.OK);
//        } else {
//            // 同時輸入了 groupid 和 funcid
//            List<GroupFunc> resultByBoth = groupFuncS.findByGroupidAndFuncid(groupid, funcid);
//            return new ResponseEntity<>(resultByBoth, HttpStatus.OK);
//        }
//    }
    
    
    
    
    //不輸入條件，直接顯示所有資料
    @GetMapping("/all")
    public List<GroupFunc> getAllGroupFunc() {
        return groupFuncS.getAllGroupFunc();
    }
//    // 根據 groupid 和 funcid 查詢
//    @GetMapping("/findByGroupidAndFuncid")
//    public List<GroupFunc> findByGroupidAndFuncid(
//            @RequestParam long groupid,
//            @RequestParam long funcid) {
//    	List<GroupFunc> result = groupFuncS.findByGroupidAndFuncid(groupid, funcid);
//    	return result;
//    }
//    // 根據groupid查詢
//    @GetMapping("/groupid")
//    public List<GroupFunc> findByGroupid(@RequestParam long groupid) {
//    	List<GroupFunc> resultgroup = groupFuncS.findByGroupid(groupid);
//        return resultgroup;
//    }
//    // 根據funcid查詢
//    @GetMapping("/funcid")
//    public List<GroupFunc> findByFuncid(@RequestParam long funcid) {
//    	List<GroupFunc> resultfunc = groupFuncS.findByFuncid(funcid);
//        return resultfunc;
//    }
    
    //刪除資料
    @DeleteMapping(value = "/deleteGF")
	public List<GroupFunc> deleteGF(@RequestParam long groupid, long funcid) {
		return groupFuncS.deleteGF(groupid, funcid);
	}
    
    //新增資料
    @PostMapping(value = "insert")
    public GroupFunc insert(@RequestBody GroupFunc inGF) {
    	return groupFuncS.insert(inGF);
    }
    
    //更新資料
    @PostMapping(value = "update")
    public GroupFunc update(@RequestBody GroupFunc upGF) {
    	return groupFuncS.update(upGF);
    }
    
    //新增資料
    @PostMapping(value="/insertGF")
    public ResponseEntity<String> insertGF(@RequestBody Map<String, String> request) {
        try {
            String groupidStr = request.get("groupid");
            String funcidStr = request.get("funcid");
            String authStr = request.get("authtype");
            String cdateStr = request.get("cdate");
            if (groupidStr == null || funcidStr == null) {
                return new ResponseEntity<>("groupid 和 funcid 不能為空", HttpStatus.BAD_REQUEST);
            }
            // 將 cdate 字符串轉換為 Timestamp
            Timestamp cdate = Timestamp.valueOf(cdateStr);
            Optional<GroupFunc> result = groupFuncS.insertGF(groupidStr, funcidStr, authStr, cdate);
            if (result.isPresent()) {
                return new ResponseEntity<>("新增成功", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("新增失敗，可能是已存在相同的pk", HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("轉換為 long 時發生錯誤", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("cdate 格式錯誤", HttpStatus.BAD_REQUEST);
        }
    }
    
    //更新資料
    @PostMapping(value = "/updateGF")
    public ResponseEntity<String> updateGF(@RequestBody Map<String, String> request) {
        try {
            String groupidStr = request.get("groupid");
            String funcidStr = request.get("funcid");
            String authStr = request.get("authtype");
            String cdateStr = request.get("cdate");

            if (groupidStr == null || funcidStr == null) {
                return new ResponseEntity<>("groupid 和 funcid 不能為空", HttpStatus.BAD_REQUEST);
            }

            // 將 cdate 字符串轉換為 Timestamp
            Timestamp cdate = Timestamp.valueOf(cdateStr);

            Optional<GroupFunc> result = groupFuncS.updateGF(groupidStr, funcidStr, authStr, cdate);

            if (result.isPresent()) {
                return new ResponseEntity<>("更新成功", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("更新失敗，可能是不存在相同的 pk", HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("轉換為 long 時發生錯誤", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("cdate 格式錯誤", HttpStatus.BAD_REQUEST);
        }
    }

    //更新和新增資料
    @PostMapping(value = "/insertOrUpdateGF")
    public ResponseEntity<String> insertOrUpdateGF(@RequestBody Map<String, String> request) {
        try {
            String groupidStr = request.get("groupid");
            String funcidStr = request.get("funcid");
            String authStr = request.get("authtype");
            String cdateStr = request.get("cdate");

            if (groupidStr == null || funcidStr == null) {
                return new ResponseEntity<>("groupid 和 funcid 不能為空", HttpStatus.BAD_REQUEST);
            }

            // 將 cdate 字符串轉換為 Timestamp
            Timestamp cdate = Timestamp.valueOf(cdateStr);

            Optional<GroupFunc> result = groupFuncS.insertOrUpdateGF(groupidStr, funcidStr, authStr, cdate);

            if (result.isPresent()) {
                return new ResponseEntity<>("新增或更新成功", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("新增或更新失敗，可能是已存在相同的 pk 或轉換失敗", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("cdate 格式錯誤", HttpStatus.BAD_REQUEST);
        }
    }

    
}
