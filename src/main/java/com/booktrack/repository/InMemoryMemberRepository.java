package com.booktrack.repository;

import com.booktrack.model.Member;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryMemberRepository implements MemberRepository{

    private final Map<String, Member> members = new LinkedHashMap<>();

    @Override
    public boolean existsByCode(String code) {
        return members.containsKey(code);
    }

    @Override
    public boolean existsByEmail(String email) {
        return members.values().stream().anyMatch(member -> member.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public void save(Member member) {
        members.put(member.getCode(), member);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(
                members.values()
        );
    }

    @Override
    public Member findByCode(String code) {
        return members.get(code);
    }

    @Override
    public Member findByEmail(String email) {
        return members.values().stream().filter(member -> member.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);
    }
}
