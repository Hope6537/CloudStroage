package org.hope6537.cloudstroage.member.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.cloudstroage.basic.dao.BasicDao;
import org.hope6537.cloudstroage.item.model.ItemInfo;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.page.annotation.MybatisRepository;

import java.util.List;

/**
 * Created by Hope6537 on 2015/3/10.
 */
@MybatisRepository
public interface MemberDao extends BasicDao<Member> {

    Member getMemberByUsername(String username);

    int putItem(@Param("memberId") String memberId, @Param("items") List<ItemInfo> itemInfo);

    int deleteItem(@Param("memberId") String memberId, @Param("items") List<ItemInfo> itemInfo);

    List<ItemInfo> getItemsByMember(String memberId);

    List<ItemInfo> getItemsByPath(@Param("memberId") String memberId, @Param("items") ItemInfo parent);
}
