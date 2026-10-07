package com.booktrack.model;

public class MemberLoanRanking {

    private final String memberCode;
    private final String fullName;
    private final long totalLoans;


    public MemberLoanRanking(String memberCode, String fullName, long totalLoans) {
        this.memberCode = memberCode;
        this.fullName = fullName;
        this.totalLoans = totalLoans;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public long getTotalLoans() {
        return totalLoans;
    }
}
