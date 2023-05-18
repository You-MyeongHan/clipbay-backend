package com.homepage.security.token.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homepage.security.token.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{
	@Query(value="""
			select t\s
			from Token t inner join User u\s
			on t.user.id = u.id\s
			where u.id = :id and (t.expired = false or t.revoked = false)\s
			""")
	List<Token> findAllValidTokenByUser(@Param("id") Long id);
	
	Optional<Token> findByToken(String token);
}
