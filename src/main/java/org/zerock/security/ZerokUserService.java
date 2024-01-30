package org.zerock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.domain.Member;
import org.zerock.persistence.MemberRepository;

import lombok.extern.java.Log;

@Log
@Service
public class ZerokUserService implements UserDetailsService{
	@Autowired
	MemberRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User sampleUser = new User(username, "1111", Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")));
//		return sampleUser;
		Member m = repo.findById(username).get();
		return new ZerockSecurityUser(m);
	}


}
