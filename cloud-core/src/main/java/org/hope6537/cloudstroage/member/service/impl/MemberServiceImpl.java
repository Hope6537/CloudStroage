package org.hope6537.cloudstroage.member.service.impl;

import org.hope6537.cloudstroage.basic.service.impl.BasicServiceImpl;
import org.hope6537.cloudstroage.member.dao.MemberDao;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.cloudstroage.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Hope6537 on 2015/3/10.
 */
@Service("memberService")
public class MemberServiceImpl extends BasicServiceImpl<Member, MemberDao> implements MemberService {

    @Autowired
    @Qualifier(value = "memberDao")
    @Override
    public void setDao(MemberDao dao) {
        super.setDao(dao);
    }

    @Override
    public Member getMemberByUsername(String username) {
        logger.debug("用户业务——根据账号获取用户对象");
        return dao.getMemberByUsername(username);
    }
}

