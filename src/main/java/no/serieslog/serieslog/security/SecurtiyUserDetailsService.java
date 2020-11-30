package no.serieslog.serieslog.security;

import no.serieslog.serieslog.data.User;
import no.serieslog.serieslog.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurtiyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public SecurtiyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);

        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        return new SecurityUserDetails(user);
    }
}
