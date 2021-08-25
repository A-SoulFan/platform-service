package com.asoulfan.asfbbs.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asoulfan.asfbbs.admin.domain.SysPermission;
import com.asoulfan.asfbbs.admin.service.SysPermissionService;
import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.api.IErrorCode;
import com.asoulfan.asfbbs.api.ResultCode;
import com.asoulfan.asfbbs.service.RedisService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ZGQ
 * @create 2021-08-24-13:15
 */
@Controller
@RequestMapping("/sys/permission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 查询用户拥有的菜单权限
     * param userId
     *
     * @return
     */
    @RequestMapping(value = "/getUserPermissionById", method = RequestMethod.GET)
    public CommonResult<?> getUserPermissionByToken(@RequestParam("userId") String userId) {
            List<SysPermission> permissionList = sysPermissionService.getByUserId(userId);
            return CommonResult.success(permissionList);
    }

    /**
     * 将角色存权限存入redis
     * key :Role_User Role_Admin ...
     * value :permissionList
     * */
    @RequestMapping(value = "/putPermissionInRedis",method = RequestMethod.PUT)
     public CommonResult<?> putPermissionInRedis(){
         Boolean ok=sysPermissionService.pubPermissionInRedis();
         if(ok=true){
             return CommonResult.success("权限缓存redis成功");
         }else {
             return CommonResult.failed("权限缓存redis失败");
         }

    }
}
