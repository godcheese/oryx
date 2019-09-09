package com.gioov.oryx.common.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-08-28
 */
public class JwtUserDetails implements UserDetails  {

    private static final long serialVersionUID = 4741273820732627885L;

    private long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Date gmtPasswordLastModified;

    public long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Date getGmtPasswordLastModified() {
        return gmtPasswordLastModified;
    }

    public JwtUserDetails(long id, String username, String password, Collection<? extends GrantedAuthority> authorities, Date gmtPasswordLastModified) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.gmtPasswordLastModified = gmtPasswordLastModified;
    }
}
