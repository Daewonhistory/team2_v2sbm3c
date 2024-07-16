package dev.mvc.team2.allowips;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AllowIpsRepository extends JpaRepository<AllowIps, Long> {
    List<AllowIps> findAll();
}