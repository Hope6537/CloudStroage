package org.hope6537.cloudstroage.member.service;

import org.hope6537.cloudstroage.basic.service.BasicService;
import org.hope6537.cloudstroage.item.model.ItemInfo;
import org.hope6537.cloudstroage.member.dao.MemberDao;
import org.hope6537.cloudstroage.member.model.Member;

import java.util.List;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public interface MemberService extends BasicService<Member, MemberDao> {

    Member getMemberByUsername(String username);

    boolean putItem(String memberId, List<ItemInfo> itemInfo);

    boolean deleteItem(String memberId, List<ItemInfo> itemInfo);

    boolean mkDir(String memberId, ItemInfo folder);

    List<ItemInfo> getItemsByMember(String memberId);

    List<ItemInfo> getItemsByPath(String memberId, ItemInfo parent);

}
