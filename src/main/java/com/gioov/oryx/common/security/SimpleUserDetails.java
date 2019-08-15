package com.gioov.oryx.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-04-14
 */
public interface SimpleUserDetails extends UserDetails, Serializable {

    /**
     * 角色、权限
     * @return Collection<? extends GrantedAuthority>
     */
    @Override
    Collection<? extends GrantedAuthority> getAuthorities();

    /**
     * 密码
     * @return String
     */
    @Override
    String getPassword();

    /**
     * id
     * @return Long
     */
    Long getId();

    /**
     * 用户名
     * @return String
     */
    @Override
    String getUsername();

    /**
     * 帐号是否未过期
     * @return boolean
     */
    @Override
    boolean isAccountNonExpired();

    /**
     * 帐号是否未锁定
     * @return boolean
     */
    @Override
    boolean isAccountNonLocked();

    /**
     * 凭证是否未过期
     * @return boolean
     */
    @Override
    boolean isCredentialsNonExpired();

    /**
     * 是否启用
     * @return boolean
     */
    @Override
    boolean isEnabled();

}
