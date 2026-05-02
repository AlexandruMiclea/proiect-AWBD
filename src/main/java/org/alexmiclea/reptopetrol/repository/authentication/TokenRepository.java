package org.alexmiclea.reptopetrol.repository.authentication;


import org.alexmiclea.reptopetrol.model.authentication.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    @Query(value = """
        select t from Token t inner join User u\s
        on t.user.id = u.id\s
        where u.id = :id and (t.expired = false or t.revoked = false)\s
    """)
    List<Token> findAllByUser(UUID id);

    Optional<Token> findByToken(String token);
}