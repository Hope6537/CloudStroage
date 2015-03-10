package org.hope6537.cloudstroage.member.service;

import org.hope6537.cloudstroage.basic.service.BasicService;
import org.hope6537.cloudstroage.member.dao.MemberDao;
import org.hope6537.cloudstroage.member.model.Member;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public interface MemberService extends BasicService<Member, MemberDao> {

    Member getMemberByUsername(String username);

}
