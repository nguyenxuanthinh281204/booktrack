package com.booktrack.service;

import com.booktrack.exception.BusinessException;
import com.booktrack.model.Member;
import com.booktrack.repository.MemberRepository;

import java.util.List;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void registerMember(String code, String fullName, String email){
        code = normalize(code);
        fullName = normalize(fullName);
        email = normalize(email);

        validateNotBlank(code, "Member code");
        validateNotBlank(fullName, "Full name");
        validateNotBlank(email, "Email");

        validateEmail(email);

        if(memberRepository.existsByCode(code)){
            throw new BusinessException("Member code already exists.");
        }

        if(memberRepository.existsByEmail(email)){
            throw new BusinessException("Email already exists");
        }

        Member member = new Member(code,fullName,email);

        memberRepository.save(member);
    }

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public Member findByCode(String code){
        code = normalize(code);
        return memberRepository.findByCode(code);
    }

    public Member findByEmail(String email){
        email = normalize(email);
        return memberRepository.findByEmail(email);
    }

    private String normalize(String value){
        if(value == null){
            return null;
        }
        return value.trim();
    }

    private void validateNotBlank(String value, String fieldName){
        if(value == null || value.isBlank()){
            throw new BusinessException( fieldName + " must not be blank.");
        }
    }

    public void validateEmail(String email){
        if(!email.contains("@")){
            throw new BusinessException("Email must contain @.");
        }
    }
}
