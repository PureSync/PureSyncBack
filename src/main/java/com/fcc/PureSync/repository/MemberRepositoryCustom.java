package com.fcc.PureSync.repository;

import com.fcc.PureSync.dto.AdminMemberDto;
import com.fcc.PureSync.dto.MemberDetailDto;
import com.fcc.PureSync.entity.MemberSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {
    Page<AdminMemberDto> searchMemberList(MemberSearchCondition condition, Pageable pageable);
    MemberDetailDto getMemberDetail(Long seq);
}
