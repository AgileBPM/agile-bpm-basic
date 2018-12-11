package com.dstz.security.login.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dstz.org.api.model.IUser;
import com.dstz.org.api.model.dto.UserDto;

public class LoginUser extends UserDto implements UserDetails {


    private Collection<? extends GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

    /**
     *
     */
    private static final long serialVersionUID = 4962121675615445357L;

    public LoginUser(IUser user) {
        this.account = user.getAccount();
        this.email = user.getEmail();
        this.fullname = user.getFullname();
        this.id = user.getUserId();
        this.mobile = user.getMobile();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.weixin = user.getWeixin();
    }

    /**
     * 设置角色。
     *
     * @param roles
     */
    public void setAuthorities(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles;
    }

    @Override
    public String getUsername() {
        return this.getAccount();
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
        int status = this.getStatus();
        if (status == 1) {
            return true;
        }
        return false;
    }

}
