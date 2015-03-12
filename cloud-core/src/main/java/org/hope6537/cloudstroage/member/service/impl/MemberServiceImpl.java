package org.hope6537.cloudstroage.member.service.impl;

import org.hope6537.cloudstroage.basic.service.impl.BasicServiceImpl;
import org.hope6537.cloudstroage.item.model.ItemInfo;
import org.hope6537.cloudstroage.member.dao.MemberDao;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.cloudstroage.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public boolean putItem(String memberId, List<ItemInfo> itemInfo) {
        logger.debug("用户业务——添加文件");
        return dao.putItem(memberId, itemInfo) == itemInfo.size();
    }

    @Override
    public boolean deleteItem(String memberId, List<ItemInfo> itemInfo) {
        logger.debug("用户业务——删除文件");
        return dao.deleteItem(memberId, itemInfo) >= itemInfo.size();
    }

    @Override
    public List<ItemInfo> getItemsByMember(String memberId) {
        logger.debug("用户业务——查看该用户下所有文件");
        return dao.getItemsByMember(memberId);
    }


}

