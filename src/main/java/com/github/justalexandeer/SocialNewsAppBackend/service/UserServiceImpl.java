package com.github.justalexandeer.SocialNewsAppBackend.service;

import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.AppUser;
import com.github.justalexandeer.SocialNewsAppBackend.domain.entity.Role;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.Response;
import com.github.justalexandeer.SocialNewsAppBackend.domain.response.ResponseAppUser;
import com.github.justalexandeer.SocialNewsAppBackend.repository.RoleRepository;
import com.github.justalexandeer.SocialNewsAppBackend.repository.UserRepository;
import com.github.justalexandeer.SocialNewsAppBackend.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final DataMapper dataMapper;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            DataMapper dataMapper
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.dataMapper = dataMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        appUser.getRoles().add(role);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public AppUser getAppUser(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public Response<Void> createAppUser(String userName, String name, String password) {
        if (userRepository.existsByUsername(userName)) {
            return new Response<>("error", "User with this username exist", null);
        } else {
            String encodedPassword = passwordEncoder.encode(password);
            Role role = roleRepository.findByName("ROLE_USER");
            AppUser appUser = new AppUser(name, userName, encodedPassword, new ArrayList<>());
            appUser.getRoles().add(role);
            userRepository.save(appUser);
            return new Response<>("success", null, null);
        }
    }

    @Override
    public List<AppUser> getAllAppUsers() {
        return userRepository.findAll();
    }

    @Override
    public Response<List<ResponseAppUser>> getTopAuthors(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<AppUser> listOfAppUser = userRepository.findTopAuthors(pageable);
        return new Response("success", null, dataMapper.mapListAppUserToListResponseAppUser(listOfAppUser));
    }

}
