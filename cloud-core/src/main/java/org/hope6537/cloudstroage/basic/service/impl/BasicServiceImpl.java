package org.hope6537.cloudstroage.basic.service.impl;

import org.hope6537.cloudstroage.basic.dao.BasicDao;
import org.hope6537.cloudstroage.basic.service.BasicService;
import org.hope6537.context.ApplicationConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public class BasicServiceImpl<T, DaoType extends BasicDao<T>> implements BasicService<T, DaoType> {

    protected Logger logger = LoggerFactory.getLogger(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1].getClass());

    @Autowired
    protected DaoType dao;

    @Override
    public boolean addEntry(T t) {
        logger.debug("基本服务——添加对象");
        return dao.addEntry(t) == ApplicationConstant.EFFECTIVE_LINE_ONE;
    }

    @Override
    public boolean updateEntry(T t) {
        logger.debug("基本服务——更新对象");
        return dao.updateEntry(t) == ApplicationConstant.EFFECTIVE_LINE_ONE;
    }

    @Override
    public boolean disableEntry(T t) {
        logger.debug("基本服务——无效化对象");
        return dao.disableEntry(t) == ApplicationConstant.EFFECTIVE_LINE_ONE;
    }

    @Override
    public boolean deleteEntry(T t) {
        logger.debug("基本服务——删除对象");
        return dao.deleteEntry(t) == ApplicationConstant.EFFECTIVE_LINE_ONE;
    }

    @Override
    public T getEntryById(Integer id) {
        logger.debug("基本服务——根据ID获取对象");
        return dao.getEntryById(id);
    }

    @Override
    public List<T> getEntryListByEntry(T t) {
        logger.debug("基本服务——根据对象当前信息获取对象");
        return dao.getEntryListByEntry(t);
    }
}
