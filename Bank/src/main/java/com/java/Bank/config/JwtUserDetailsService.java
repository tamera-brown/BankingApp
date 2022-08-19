package com.java.Bank.config;

import com.java.Bank.Authority;
import com.java.Bank.model.User;
import com.java.Bank.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
   public UserRepo userRepo;

    public static final String ROLE_USER = "ROLE_USER";


    @Override
    public  UserDetails loadUserByUsername(final String username) {
        User user=userRepo.findUserByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("user not in database");
        }
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.getAuthorities().forEach(authority -> {
            authorities.add(new SimpleGrantedAuthority(ROLE_USER));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);


    }

}
