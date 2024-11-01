package com.ssafy.fittapet.backend.domain.repository.group;

import com.ssafy.fittapet.backend.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long>, GroupCustomRepository {

}
