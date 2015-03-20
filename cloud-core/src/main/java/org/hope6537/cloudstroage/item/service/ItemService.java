package org.hope6537.cloudstroage.item.service;

import org.hope6537.cloudstroage.basic.service.BasicService;
import org.hope6537.cloudstroage.item.dao.ItemDao;
import org.hope6537.cloudstroage.item.model.ItemInfo;

import java.util.Set;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public interface ItemService extends BasicService<ItemInfo, ItemDao> {

    boolean onlyChangeStatusByIds(String status, Set<String> ids);

}
