package org.hope6537.cloudstroage.member.dao;

import org.hope6537.cloudstroage.basic.dao.BasicDao;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.page.annotation.MybatisRepository;

/**
 * Created by Hope6537 on 2015/3/10.
 */
@MybatisRepository
public interface MemberDao extends BasicDao<Member> {

    Member getMemberByUsername(String username);

}
