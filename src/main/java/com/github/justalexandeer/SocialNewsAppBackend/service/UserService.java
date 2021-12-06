package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser appUser);
    Role saveRole(Role role);
    List<AppUser> getAllAppUsers();
    AppUser getAppUser(String userName);
}
