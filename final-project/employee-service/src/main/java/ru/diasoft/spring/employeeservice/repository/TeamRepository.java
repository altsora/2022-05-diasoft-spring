package ru.diasoft.spring.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.employeeservice.domain.Team;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    boolean existsByName(String name);

    boolean existsByUniqNumber(Integer uniqNumber);

    Optional<Team> findByUniqNumber(Integer uniqNumber);

    @Modifying
    @Transactional
    @Query("UPDATE Team t SET t.active = :value WHERE t.uniqNumber = :uniqNumber")
    void setActivity(@Param("uniqNumber") Integer uniqNumber, @Param("value") boolean value);
}
