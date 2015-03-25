package org.hope6537.cloudstroage.item.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.cloudstroage.basic.dao.BasicDao;
import org.hope6537.cloudstroage.item.model.ItemInfo;
import org.hope6537.page.annotation.MybatisRepository;

import java.util.Set;

/**
 * Created by Hope6537 on 2015/3/10.
 */
@MybatisRepository
public interface ItemDao extends BasicDao<ItemInfo> {

    int getItemUsingCount(@Param("itemId") String itemId);

    int onlyChangeStatusByIds(@Param("status") String status, @Param("ids") Set<String> ids);

}
