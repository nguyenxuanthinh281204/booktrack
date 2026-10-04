package com.booktrack.repository;

import com.booktrack.model.Member;

import java.util.List;

public interface MemberRepository {

    boolean existsByCode(String code);
    boolean existsByEmail(String email);
    void save(Member member);
    List<Member> findAll();
    Member findByCode(String code);
    Member findByEmail(String email);
}
