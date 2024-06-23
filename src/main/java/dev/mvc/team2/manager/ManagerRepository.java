package dev.mvc.team2.manager;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

  @Query("SELECT COUNT(m.id) FROM Manager m WHERE m.id = :id AND m.passwd = :passwd")
  Integer login(@Param("id") String id, @Param("passwd") String passwd);

  // rdate로 검색

}
