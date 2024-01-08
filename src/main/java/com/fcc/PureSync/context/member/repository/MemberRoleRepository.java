package com.fcc.PureSync.context.member.repository;

import com.fcc.PureSync.context.member.entity.MpMemRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRoleRepository extends JpaRepository<MpMemRole,Long> {

    Optional<MpMemRole> findByMemSeq(Long memberSeq);
}
