package com.gioov.oryx.oauth.api;

import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.oauth.Oauth;
import com.gioov.oryx.oauth.entity.AccessTokenEntity;
import com.gioov.oryx.oauth.service.AccessTokenService;
import com.gioov.tile.web.exception.BaseResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gioov.oryx.user.service.UserService.SYSTEM_ADMIN;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-02-01
 */
@RestController
@RequestMapping(Oauth.Api.ACCESS_TOKEN)
public class AccessTokenRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenRestController.class);

    private static final String ACCESS_TOKEN = "/API/OAUTH/ACCESS_TOKEN";

    @Autowired
    private AccessTokenService accessTokenService;

    /**
     * 分页获取所有 OAuth Access Token
     * @param page 页
     * @param rows 每页显示数量
     * @return ResponseEntity<Pagination<AccessTokenEntity>>
     */
    @OperationLog(value = "分页获取所有 OAuth Access Token", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ACCESS_TOKEN + "/PAGE_ALL')")
    @GetMapping(value = "/page_all")
    public ResponseEntity<Pagination<AccessTokenEntity>> pageAll(@RequestParam Integer page, @RequestParam Integer rows) {
        return new ResponseEntity<>(accessTokenService.pageAll(page, rows), HttpStatus.OK);
    }

    /**
     * 指定 OAuth Access Token token id list，批量删除 OAuth Access Token
     * @param tokenIdList OAuth Access Token token id list
     * @return ResponseEntity<Integer>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "指定 OAuth Access Token token id list，批量删除 OAuth Access Token", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ACCESS_TOKEN + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam List<String> tokenIdList) throws BaseResponseException {
        return new ResponseEntity<>(accessTokenService.deleteAll(tokenIdList), HttpStatus.OK);
    }

    /**
     * 指定 OAuth Access Token token id，获取 OAuth Access Token
     * @param tokenId OAuth Access Token token id
     * @return ResponseEntity<AccessTokenEntity>
     */
    @OperationLog(value = "指定 OAuth Access Token token id，获取 OAuth Access Token", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ACCESS_TOKEN + "/ONE')")
    @GetMapping(value = "/one/{tokenId}")
    public ResponseEntity<AccessTokenEntity> getOne(@PathVariable String tokenId) {
        return new ResponseEntity<>(accessTokenService.getOne(tokenId), HttpStatus.OK);
    }

    /**
     * 清空所有 OAuth Access Token
     * @return ResponseEntity<Integer>
     */
    @OperationLog(value = "清空所有 OAuth Access Token", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + ACCESS_TOKEN + "/DELETE_ALL')")
    @PostMapping(value = "/clear_all")
    public ResponseEntity<HttpStatus> clearAll() {
        accessTokenService.clearAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
