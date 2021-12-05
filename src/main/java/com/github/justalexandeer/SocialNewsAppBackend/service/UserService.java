package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.AppUser;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser appUser);
    List<AppUser> getAllAppUsers();
    AppUser getAppUser(String userName);
}
