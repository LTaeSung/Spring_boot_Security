package org.zerock;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.domain.Member;
import org.zerock.domain.MemberRole;
import org.zerock.persistence.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.extern.java.Log;

@Log
@Commit
@SpringBootTest
class MemberTests {
	@Autowired
	private MemberRepository repo;
	@Test
	void testInsert() {
		for (int i=0; i<=100; i++) {
			Member member = new Member();
			member.setUid("user"+i);
			member.setUpw("pw"+i);
			member.setUname("사용자"+i);
			
			MemberRole role = new MemberRole();
			if (i<=80) {
				role.setRoleName("BASIC");
			} else if (i<=90) {
				role.setRoleName("MANAGER");
			} else {
				role.setRoleName("ADMIN");
			}
			member.setRoles(Arrays.asList(role));
			repo.save(member);
		}
	}
	@Test
	@Transactional
	void testRead() {
		Optional<Member> result = repo.findById("user85");
		result.ifPresent(m->log.info("member:"+m));
	}

}
