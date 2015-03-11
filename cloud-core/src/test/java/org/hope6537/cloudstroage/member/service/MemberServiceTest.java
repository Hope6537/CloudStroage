package org.hope6537.cloudstroage.member.service;

import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.spring.SpringTestHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hope6537 on 2015/3/10.
 */
@ContextConfiguration("classpath:spring/spring-test.xml")
public class MemberServiceTest extends SpringTestHelper {

    @Autowired
    private MemberService memberService;

    public Member memberAppend() {
        Member member = Member.getInstanceOfTest();
        return member;
    }

    @Test
    public void testAdd() {
        assertTrue(memberService.addEntry(memberAppend()));
    }

    @Test
    public void testGetByList() {
        Member member = memberAppend();
        memberService.addEntry(member);
        String id = member.getMemberId();
        Member queryMember = new Member();
        queryMember.setName("_test");
        List<Member> memberList = memberService.getEntryListByEntry(queryMember);
        assertTrue(memberList.size() == 1);
        assertEquals(id, memberList.get(0).getMemberId());
    }

    @Test
    public void testGetById() {
        Member member = memberAppend();
        memberService.addEntry(member);
        String id = member.getMemberId();
        Member queryMember = memberService.getEntryById(id);
        assertEquals(member.getName(), queryMember.getName());
    }

    @Test
    public void testUpdate() {
        Member member = memberAppend();
        memberService.addEntry(member);
        String id = member.getMemberId();
        Member queryMember = memberService.getEntryById(id);
        queryMember.setName("_after");
        memberService.updateEntry(queryMember);
        assertEquals("_after", memberService.getEntryById(id).getName());
    }

    @Test
    public void testDisable() {
        Member member = memberAppend();
        memberService.addEntry(member);
        String id = member.getMemberId();
        Member queryMember = memberService.getEntryById(id);
        memberService.disableEntry(queryMember);
        assertEquals(ApplicationConstant.STATUS_DIE, memberService.getEntryById(id).getStatus());
    }

    @Test
    public void testDelete() {
        Member member = memberAppend();
        memberService.addEntry(member);
        String id = member.getMemberId();
        Member deleteMember = new Member();
        deleteMember.setMemberId(id);
        memberService.deleteEntry(deleteMember);
        assertTrue(ApplicationConstant.isNull(memberService.getEntryById(id)));
    }

    @Test
    public void testGetUsername() {
        Member member = memberAppend();
        memberService.addEntry(member);
        String username = member.getUsername();
        Member queryMember = memberService.getMemberByUsername(username);
        assertEquals(member.getMemberId(), queryMember.getMemberId());
    }

}
