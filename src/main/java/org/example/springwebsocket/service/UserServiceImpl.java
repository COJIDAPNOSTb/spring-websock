package org.example.springwebsocket.service;

import org.example.springwebsocket.app.model.User;
import org.example.springwebsocket.app.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername( username )
				.orElseThrow(() -> new UsernameNotFoundException( "Пользователь не найден" ));
	}

	@Override
	public boolean existsByUsername(String username) {
		User user = userRepository.findByUsername( username ).orElse( null );
		if(user != null) {
			return true;
		}
		return false;
	}

//	@Override
//	public boolean existsByEmail(String email) {
//		User user = userRepository.findByEmail( email ).orElse( null );
//		if(user != null) {
//			return true;
//		}
//		return false;
//	}
}
