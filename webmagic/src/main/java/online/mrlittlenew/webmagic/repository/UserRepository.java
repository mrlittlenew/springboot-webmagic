package online.mrlittlenew.webmagic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.mrlittlenew.webmagic.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findById(Long id);
}