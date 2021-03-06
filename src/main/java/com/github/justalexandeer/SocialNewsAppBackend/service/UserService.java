package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Role;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.Response;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseAppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseFullPost;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser appUser);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    List<AppUser> getAllAppUsers();
    Response<List<ResponseAppUser>> getTopAuthors(int limit);
    AppUser getAppUser(String userName);
    Response<Void> createAppUser(String userName, String name, String password);
}
