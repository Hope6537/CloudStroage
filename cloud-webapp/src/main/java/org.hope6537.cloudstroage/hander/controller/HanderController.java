/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China 
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.hander.controller;

import org.hope6537.ajax.AjaxResponse;
import org.hope6537.ajax.ReturnState;
import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.basic.controller.BasicController;
import org.hope6537.cloudstroage.hander.dao.HanderDao;
import org.hope6537.cloudstroage.hander.model.Hander;
import org.hope6537.cloudstroage.hander.model.HanderWrapper;
import org.hope6537.cloudstroage.hander.service.HanderService;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.date.DateFormatCalculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Hope6537 on 2015/3/13.
 */
@Controller
@RequestMapping("/hander")
public class HanderController extends BasicController<Hander, HanderDao, HanderService> {


    /*
     * TODO: 在这里使用这些方法
     *
     * public void getSonHanderIds(Hander hander, Set<String> list);
     * public boolean updateFolderName(Hander hander, String newName); -ok
     * public boolean deleteFolder(Hander hander); -ok
     * public boolean disableFolder(Hander hander); -ok
     * public Hander getSonHanderToHander(Hander hander);
     * public List<Hander> getHanderListByMemberId(String memberId);
     * public List<Hander> getHanderListByPath(String memberId, String fullPath);
     * public List<Hander> getHanderListByParentHander(Hander hander);
     */

    @Autowired
    @Qualifier("handerService")
    @Override
    public void setService(HanderService service) {
        super.setService(service);
    }

    @Override
    public String toPage() {
        return null;
    }

    @Override
    public AjaxResponse addModel(@RequestBody Hander model) {
        model.setUpdateDate(DateFormatCalculate.createNowTime());
        return super.addModel(model);
    }

    @Override
    public AjaxResponse updateModel(@RequestBody Hander model) {
        if (!model.checkFolder()) {
            return super.updateModel(model);
        } else {
            return AjaxResponse.getInstanceByResult(service.updateFolderName(model));
        }
    }

    @Override
    public AjaxResponse deleteModel(@RequestBody Hander model) {
        if (!model.checkFolder()) {
            return super.deleteModel(model);
        } else {
            return AjaxResponse.getInstanceByResult(service.deleteFolder(model));
        }
    }

    @Override
    public AjaxResponse disableModel(@RequestBody Hander model) {
        if (!model.checkFolder()) {
            return super.disableModel(model);
        } else {
            return AjaxResponse.getInstanceByResult(service.disableFolder(model));
        }
    }

    @RequestMapping(value = "/deleteMulti", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResponse deleteMultiModel(@RequestBody List<Hander> handers) {
        if (ApplicationConstant.notNull(handers)) {
            return AjaxResponse.getInstanceByResult(service.deleteMultiHander(handers).get());
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);

    }

    @RequestMapping(value = "/{parentHanderId}/son", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse getSonHanderByParent(@PathVariable String parentHanderId, HttpServletRequest request) {
        Member member = getLoginMember(request);
        if (ApplicationConstant.notNull(member)) {
            if (ApplicationConstant.isNull(parentHanderId)) {
                parentHanderId = "-1";
            }
            Hander query = new Hander();
            query.setMemberId(member.getMemberId());
            query.setParentId(parentHanderId);
            query.setStatus(ApplicationConstant.STATUS_NORMAL);
            List<Hander> list = service.getEntryListByEntry(query);
            if (ApplicationConstant.notNull(list)) {
                return AjaxResponse.getInstanceByResult(true).addAttribute("list", list);
            } else {
                if (list != null && list.size() == 0) {
                    return AjaxResponse.getInstanceByResult(true).addAttribute("empty", true).addReturnMsg("空文件夹");
                }
            }
            return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);

        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(value = "/{parentHanderId}/son/wrapper", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse getSonHanderByParentWithWrapper(@PathVariable String parentHanderId, HttpServletRequest request) {
        Member member = getLoginMember(request);
        synchronized (this) {
            if (ApplicationConstant.notNull(member)) {
                if (ApplicationConstant.isNull(parentHanderId)) {
                    parentHanderId = "-1";
                }
                Hander query = new Hander();
                query.setMemberId(member.getMemberId());
                query.setParentId(parentHanderId);
                query.setStatus(ApplicationConstant.STATUS_NORMAL);
                HanderWrapper wrapper = service.getWrapperByHanderId(query);
                if (ApplicationConstant.notNull(wrapper)) {
                    List<Hander> list = wrapper.getHanders();
                    if (ApplicationConstant.notNull(list)) {
                        return AjaxResponse.getInstanceByResult(true).addAttribute("list", list);
                    }
                }
                return new AjaxResponse(ReturnState.WARNING, "空文件夹");
            }
            return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
        }
    }

    @RequestMapping(value = "/{fullPath}/path", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse getSonHanderByPath(@PathVariable String fullPath, HttpServletRequest request) {
        Member member = getLoginMember(request);
        if (ApplicationConstant.notNull(fullPath) && ApplicationConstant.notNull(member)) {
            List<Hander> list = service.getHanderListByPath(member.getMemberId(), fullPath);
            if (ApplicationConstant.notNull(list)) {
                return AjaxResponse.getInstanceByResult(true).addAttribute("list", list);
            } else {
                if (list != null && list.size() == 0) {
                    return AjaxResponse.getInstanceByResult(true).addAttribute("empty", true).addReturnMsg("空文件夹");
                }
            }
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

}
