package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.TestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITestTypeRepository extends JpaRepository<TestType, Long> {
    boolean existsByName(String name);

    Optional<TestType> findByName(String name);
}
