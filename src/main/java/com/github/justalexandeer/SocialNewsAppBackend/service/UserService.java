package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser appUser);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    List<AppUser> getAllAppUsers();
    AppUser getAppUser(String userName);
}
