package com.evact.trustalent.repository;

import com.evact.trustalent.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

	@Query(value = "FROM TokenEntity t INNER JOIN UserEntity u on t.user.id = u.id WHERE u.id = :id and (t.expired = false or t.revoked = false)")
	List<TokenEntity> findAllValidTokenByUser(Long id);

	Optional<TokenEntity> findByToken(String token);

	void deleteAllByUserId(Long id);

}
