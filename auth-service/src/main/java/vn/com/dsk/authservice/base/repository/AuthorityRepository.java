package vn.com.dsk.authservice.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.dsk.authservice.base.model.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByName(String name);


}
