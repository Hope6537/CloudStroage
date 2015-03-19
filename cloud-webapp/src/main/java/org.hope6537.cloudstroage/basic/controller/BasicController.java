/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.basic.controller;

import org.hope6537.ajax.AjaxResponse;
import org.hope6537.ajax.ReturnState;
import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.basic.dao.BasicDao;
import org.hope6537.cloudstroage.basic.model.BasicModel;
import org.hope6537.cloudstroage.basic.service.BasicService;
import org.hope6537.cloudstroage.member.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Hope6537 on 2015/3/13.
 */
public abstract class BasicController<Model extends BasicModel, Dao extends BasicDao<Model>, Service extends BasicService<Model, Dao>> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected static final String BASEPATH = ApplicationConstant.ADMINPATH;

    protected Service service;

    public void setService(Service service) {
        this.service = service;
    }

    @SuppressWarnings("unchecked")
    private final Class<Model> typeClass = (Class<Model>)
            ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    private Class type;


    @RequestMapping(value = "/toPage")
    public abstract String toPage();

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected AjaxResponse getModelList() {
        logger.debug(typeClass.toString());
        Model model = (Model) Model.getInstance(typeClass);
        model.setStatus(ApplicationConstant.STATUS_NORMAL);
        List<Model> list = service.getEntryListByEntry(model);
        return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(list)).addAttribute("list", list);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/model")
    @ResponseBody
    protected AjaxResponse getModelListByModel(@RequestBody Model model) {
        logger.debug(typeClass.toString());
        List<Model> list = service.getEntryListByEntry(model);
        return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(list)).addAttribute("list", list);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public AjaxResponse getSingleModel(@PathVariable String id) {
        if (ApplicationConstant.notNull(id)) {
            Model model = service.getEntryById(id);
            return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(model)).addAttribute("model", model);
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addModel(@RequestBody Model model) {
        if (ApplicationConstant.notNull(model)) {
            return AjaxResponse.getInstanceByResult(service.addEntry(model));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public AjaxResponse updateModel(@RequestBody Model model) {
        if (ApplicationConstant.notNull(model) && ApplicationConstant.notNull(model.commonId())) {
            return AjaxResponse.getInstanceByResult(service.updateEntry(model));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/disable")
    @ResponseBody
    public AjaxResponse disableModel(@RequestBody Model model) {
        if (ApplicationConstant.notNull(model) && ApplicationConstant.notNull(model.commonId())) {
            return AjaxResponse.getInstanceByResult(service.disableEntry(model));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    @ResponseBody
    public AjaxResponse deleteModel(@RequestBody Model model) {
        if (ApplicationConstant.notNull(model) && ApplicationConstant.notNull(model.commonId())) {
            return AjaxResponse.getInstanceByResult(service.deleteEntry(model));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    public static Member getLoginMember(HttpServletRequest request) {
        return (Member) request.getSession().getAttribute("loginMember");
    }

}
