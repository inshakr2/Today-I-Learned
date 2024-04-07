package org.example.cache.domain.repository;

import org.example.cache.domain.entity.RedisHashUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisHashUserRepository extends CrudRepository<RedisHashUser, Long> {
}
