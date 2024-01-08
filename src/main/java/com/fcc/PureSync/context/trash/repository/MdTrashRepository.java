package com.fcc.PureSync.context.trash.repository;

import com.fcc.PureSync.context.trash.entity.MdTrash;
import com.fcc.PureSync.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MdTrashRepository extends JpaRepository<MdTrash, Long> {
    List<MdTrash> findAllByMemberOrderByTsWdateDesc(Member member);
    List<MdTrash> findAllByMemberAndTsStatusOrderByTsWdateDesc(Member member, boolean status);

    List<MdTrash> findAllByTsStatusAndTsWdateBefore(boolean status, LocalDateTime endDate);
}
