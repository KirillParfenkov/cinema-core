package by.cinema.security;

import by.cinema.bean.User;
import by.cinema.dao.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 4/19/2017.
 */
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.getByName(username);

        if (user != null) {
            return new CinemaUserDetails(user);
        }

        throw new UsernameNotFoundException("User not Found");
    }

    public class CinemaUserDetails implements UserDetails {

        private User user;
        private List<GrantedAuthority> authorities;

        public CinemaUserDetails() {
        }

        public CinemaUserDetails(User user) {

            this.authorities = new ArrayList<>();
            this.user = user;

            String roles = user.getRoles();
            for (String role: roles.split(",")) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getEmail();
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

        public User getUser() {
            return user;
        }
    }
}
