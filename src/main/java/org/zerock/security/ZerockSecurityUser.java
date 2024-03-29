package org.zerock.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.zerock.domain.Member;
import org.zerock.domain.MemberRole;

public class ZerockSecurityUser extends User{
	
	private static final String ROLE_PREFIX = "ROLE_";
	private Member member;

	public ZerockSecurityUser(
			String username, 
			String password, 
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	public ZerockSecurityUser(Member member) {
		super(member.getUid(), member.getUpw(), makeGrantedAuthority(member.getRoles()));
	}
	
	private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles) {
		List<GrantedAuthority> list = new ArrayList<>();
		roles.forEach(role->list.add(new SimpleGrantedAuthority(ROLE_PREFIX+role.getRoleName())));
		return list;
	}
	

}
