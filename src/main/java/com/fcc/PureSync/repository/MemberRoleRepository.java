package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.MpMemRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRoleRepository extends JpaRepository<MpMemRole,Long> {

    Optional<MpMemRole> findByMemSeq(Long memberSeq);
}
