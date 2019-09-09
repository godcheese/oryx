package com.gioov.oryx.oauth.api;

import com.gioov.oryx.common.easyui.Pagination;
import com.gioov.oryx.common.operationlog.OperationLog;
import com.gioov.oryx.common.operationlog.OperationLogType;
import com.gioov.oryx.oauth.Oauth;
import com.gioov.oryx.oauth.entity.ClientEntity;
import com.gioov.oryx.oauth.service.ClientService;
import com.gioov.tile.util.RandomUtil;
import com.gioov.tile.web.exception.BaseResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gioov.oryx.user.service.UserService.SYSTEM_ADMIN;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-02-01
 */
@RestController
@RequestMapping(Oauth.Api.CLIENT)
public class ClientRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRestController.class);

    private static final String CLIENT = "/API/OAUTH/CLIENT";

    private static final int CLIENT_SECRET_RANDOM_STRING_LENGTH = 56;
    private static final String CLIENT_SECRET_RANDOM_STRING = "!@#$%^&*()abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890)(*&^%$#@!";

    @Autowired
    private ClientService clientService;

    /**
     * 分页获取所有 OAuth 客户端
     * @param page 页
     * @param rows 每页显示数量
     * @return ResponseEntity<Pagination<ClientEntity>>
     */
    @OperationLog(value = "分页获取所有 OAuth 客户端", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + CLIENT + "/PAGE_ALL')")
    @GetMapping(value = "/page_all")
    public ResponseEntity<Pagination<ClientEntity>> pageAll(@RequestParam Integer page, @RequestParam Integer rows) {
        return new ResponseEntity<>(clientService.pageAll(page, rows), HttpStatus.OK);
    }

    /**
     * 新增 OAuth 客户端
     * @param clientId
     * @param resourceIds
     * @param scope
     * @param authorizedGrantTypes
     * @param webServerRedirectUri
     * @param authorities
     * @param accessTokenValidity
     * @param refreshTokenValidity
     * @param additionalInformation
     * @param autoapprove
     * @return ResponseEntity<ClientEntity>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "新增 OAuth 客户端", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + CLIENT + "/ADD_ONE')")
    @PostMapping(value = "/add_one")
    public ResponseEntity<ClientEntity> addOne(@RequestParam String clientId, @RequestParam String resourceIds, @RequestParam String scope, @RequestParam String authorizedGrantTypes, @RequestParam String webServerRedirectUri, @RequestParam String authorities, @RequestParam String accessTokenValidity, @RequestParam String refreshTokenValidity, @RequestParam String additionalInformation, @RequestParam String autoapprove) throws BaseResponseException {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId(clientId);
        // 生成明文 Client Secret
       String clientSecret = RandomUtil.randomString(CLIENT_SECRET_RANDOM_STRING_LENGTH, CLIENT_SECRET_RANDOM_STRING);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密明文 Client Secret
        clientEntity.setClientSecret(passwordEncoder.encode(clientSecret));
        clientEntity.setResourceIds(resourceIds);
        clientEntity.setScope(scope);
        clientEntity.setAuthorizedGrantTypes(authorizedGrantTypes);
        clientEntity.setWebServerRedirectUri(webServerRedirectUri);
        clientEntity.setAuthorities(authorities);
        clientEntity.setAccessTokenValidity(accessTokenValidity);
        clientEntity.setRefreshTokenValidity(refreshTokenValidity);
        clientEntity.setAdditionalInformation(additionalInformation);
        clientEntity.setAutoapprove(autoapprove);
        ClientEntity clientEntity1 = clientService.addOne(clientEntity);
        // 返回明文 Client Secret 到前端
        clientEntity1.setClientSecret(clientSecret);
        return new ResponseEntity<>(clientEntity1, HttpStatus.OK);
    }

    /**
     * 保存 OAuth 客户端
     * @param clientId
     * @param resourceIds
     * @param scope
     * @param authorizedGrantTypes
     * @param webServerRedirectUri
     * @param authorities
     * @param accessTokenValidity
     * @param refreshTokenValidity
     * @param additionalInformation
     * @param autoapprove
     * @return ResponseEntity<ClientEntity>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "保存 OAuth 客户端", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + CLIENT + "/SAVE_ONE')")
    @PostMapping(value = "/save_one")
    public ResponseEntity<ClientEntity> saveOne(@RequestParam String clientId, @RequestParam String resourceIds, @RequestParam String scope, @RequestParam String authorizedGrantTypes, @RequestParam String webServerRedirectUri, @RequestParam String authorities, @RequestParam String accessTokenValidity, @RequestParam String refreshTokenValidity, @RequestParam String additionalInformation, @RequestParam String autoapprove) throws BaseResponseException {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId(clientId);
        clientEntity.setResourceIds(resourceIds);
        clientEntity.setScope(scope);
        clientEntity.setAuthorizedGrantTypes(authorizedGrantTypes);
        clientEntity.setWebServerRedirectUri(webServerRedirectUri);
        clientEntity.setAuthorities(authorities);
        clientEntity.setAccessTokenValidity(accessTokenValidity);
        clientEntity.setRefreshTokenValidity(refreshTokenValidity);
        clientEntity.setAdditionalInformation(additionalInformation);
        clientEntity.setAutoapprove(autoapprove);
        ClientEntity clientEntity1 = clientService.saveOne(clientEntity);
        return new ResponseEntity<>(clientEntity1, HttpStatus.OK);
    }

    /**
     * 指定 OAuth 客户端 client id list，批量删除 OAuth 客户端
     * @param clientIdList OAuth 客户端 client id list
     * @return ResponseEntity<Integer>
     * @throws BaseResponseException BaseResponseException
     */
    @OperationLog(value = "指定 OAuth 客户端 client id list，批量删除 OAuth 客户端", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + CLIENT + "/DELETE_ALL')")
    @PostMapping(value = "/delete_all")
    public ResponseEntity<Integer> deleteAll(@RequestParam List<String> clientIdList) throws BaseResponseException {
        return new ResponseEntity<>(clientService.deleteAll(clientIdList), HttpStatus.OK);
    }

    /**
     * 指定 OAuth 客户端 client id，获取 OAuth 客户端
     * @param clientId OAuth 客户端 client id
     * @return ResponseEntity<ClientEntity>
     */
    @OperationLog(value = "指定 OAuth 客户端 client id，获取 OAuth 客户端", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + CLIENT + "/ONE')")
    @GetMapping(value = "/one/{clientId}")
    public ResponseEntity<ClientEntity> getOne(@PathVariable String clientId) {
        return new ResponseEntity<>(clientService.getOne(clientId), HttpStatus.OK);
    }

    /**
     * 指定 OAuth 客户端 client id，获取 OAuth 客户端
     * @param clientId OAuth 客户端 client id
     * @return ResponseEntity<ClientEntity>
     */
    @OperationLog(value = "指定 OAuth 客户端 client id，获取 OAuth 客户端", type = OperationLogType.API)
    @PreAuthorize("hasRole('" + SYSTEM_ADMIN + "') OR hasAuthority('" + CLIENT + "/RESET_CLIENT_SECRET')")
    @PostMapping(value = "/reset_client_secret/{clientId}")
    public ResponseEntity<ClientEntity> resetClientSecret(@PathVariable String clientId) throws BaseResponseException {
        ClientEntity clientEntity = clientService.getOne(clientId);
        // 生成明文 Client Secret
        String clientSecret = RandomUtil.randomString(CLIENT_SECRET_RANDOM_STRING_LENGTH, CLIENT_SECRET_RANDOM_STRING);
        clientEntity.setClientId(clientId);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密明文 Client Secret
        clientEntity.setClientSecret(passwordEncoder.encode(clientSecret));
        ClientEntity clientEntity1 = clientService.saveOne(clientEntity);
        // 返回明文 Client Secret 到前端
        clientEntity1.setClientSecret(clientSecret);
        return new ResponseEntity<>(clientEntity1, HttpStatus.OK);
    }

}
