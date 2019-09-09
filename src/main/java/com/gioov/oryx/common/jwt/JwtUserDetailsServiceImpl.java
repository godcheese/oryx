package com.gioov.oryx.common.jwt;

import com.gioov.oryx.common.properties.AppProperties;
import com.gioov.oryx.system.service.DictionaryService;
import com.gioov.oryx.user.entity.RoleAuthorityEntity;
import com.gioov.oryx.user.entity.RoleEntity;
import com.gioov.oryx.user.entity.UserEntity;
import com.gioov.oryx.user.entity.UserRoleEntity;
import com.gioov.oryx.user.mapper.RoleAuthorityMapper;
import com.gioov.oryx.user.mapper.RoleMapper;
import com.gioov.oryx.user.mapper.UserMapper;
import com.gioov.oryx.user.mapper.UserRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.gioov.oryx.user.service.UserService.SYSTEM_ADMIN;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-08-28
 */
@Component
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUserDetailsServiceImpl.class);

    public static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    private UserEntity userEntity;

    /**
     * 角色是否存在
     * @param roleValue 角色值
     * @return boolean
     */
    private boolean isExistSystemAdminRole(String roleValue) {
        List<String> roleList = appProperties.getSystemAdminRole();
        if (roleList != null && !roleList.isEmpty()) {
            for (String role : roleList) {
                if (roleValue.equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public JwtUserDetails loadUserByUsername(String account) {
        // 从数据库中获取 user 实体
        userEntity = userMapper.getOneByUsername(account);
//        if (userEntity == null) {
//            throw new UsernameNotFoundException("Account " + account + " not found");
//        }
//        if (userEntity.getGmtDeleted() != null) {
//            throw new UsernameNotFoundException("Account " + account + " not found");
//        }
//        boolean enabled = true;
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = listAllSimpleGrantedAuthorityByUserId(userEntity.getId());

        for(SimpleGrantedAuthority s: simpleGrantedAuthorityList) {
            LOGGER.info("getAuthority={}", s.getAuthority());
        }
        return new JwtUserDetails(userEntity.getId(),userEntity.getUsername(),userEntity.getPassword(), simpleGrantedAuthorityList, userEntity.getGmtPasswordLastModified());
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    /**
     * 从数据库获取用户所拥有的角色和角色权限
     * @param userId 用户 id
     * @return List<SimpleGrantedAuthority>
     */
    private List<SimpleGrantedAuthority> listAllSimpleGrantedAuthorityByUserId(Long userId) {
        List<UserRoleEntity> userRoleEntityList = userRoleMapper.listAllByUserId(userId);
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
        // 装载角色
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList1 = new ArrayList<>();
        // 装载角色对应的权限
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList2;
        if (userRoleEntityList != null) {
            boolean isExistSystemAdminRole = false;
            for (UserRoleEntity userRoleEntity : userRoleEntityList) {
                RoleEntity roleEntity = roleMapper.getOne(userRoleEntity.getRoleId());
                if (roleEntity != null) {
                    // 读取和装载角色
                    Long roleId = roleEntity.getId();
                    String roleValue = roleEntity.getValue().toUpperCase();
                    if (isExistSystemAdminRole(roleValue)) {
                        isExistSystemAdminRole = true;
                    }
                    simpleGrantedAuthorityList1.add(new SimpleGrantedAuthority(ROLE_PREFIX + roleValue));
                    // 读取和装载角色权限
                    simpleGrantedAuthorityList2 = listAllSimpleGrantedAuthorityByRoleId(roleId);
                    if (!simpleGrantedAuthorityList2.isEmpty()) {
                        simpleGrantedAuthorityList1.addAll(simpleGrantedAuthorityList2);
                    }
                }
            }
            // 检查是否有重复的 ROLE_SYSTEM_ADMIN
            int index = 0;
            for (SimpleGrantedAuthority simpleGrantedAuthority : simpleGrantedAuthorityList1) {
                index++;
                if (!simpleGrantedAuthority.getAuthority().equals(ROLE_PREFIX + SYSTEM_ADMIN) && (index == simpleGrantedAuthorityList1.size()) && isExistSystemAdminRole) {
                    simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(ROLE_PREFIX + SYSTEM_ADMIN));
                }
            }
        }
        simpleGrantedAuthorityList.addAll(simpleGrantedAuthorityList1);
        return simpleGrantedAuthorityList;
    }

    /**
     * 从数据库获取角色所拥有的权限
     * @param roleId 角色 id
     * @return List<SimpleGrantedAuthority>
     */
    private List<SimpleGrantedAuthority> listAllSimpleGrantedAuthorityByRoleId(Long roleId) {
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
        List<RoleAuthorityEntity> roleAuthorityEntityList = roleAuthorityMapper.listAllByRoleId(roleId);
        if (roleAuthorityEntityList != null) {
            for (RoleAuthorityEntity roleAuthorityEntity : roleAuthorityEntityList) {
                simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(roleAuthorityEntity.getAuthority().toUpperCase()));
            }
        }
        return simpleGrantedAuthorityList;
    }
}
