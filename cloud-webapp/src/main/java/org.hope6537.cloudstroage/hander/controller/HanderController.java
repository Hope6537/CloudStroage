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
import org.hope6537.cloudstroage.hander.model.*;
import org.hope6537.cloudstroage.hander.service.HanderService;
import org.hope6537.cloudstroage.item.service.ItemService;
import org.hope6537.cloudstroage.member.model.Member;
import org.hope6537.date.DateFormatCalculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Hope6537 on 2015/3/13.
 */
@Controller
@RequestMapping("/hander")
public class HanderController extends BasicController<Hander, HanderDao, HanderService> {


    @Value("${serverPath}")
    private String serverPath;

    @Value("${netURL}")
    private String netURL;

    @Value("${netPath}")
    private String netPath;

    @Value("${hdfsPath}")
    private String hdfsPath;

    @Value("${hdfsUrlPrefix}")
    private String hdfsUrlPrefix;

    @Value("${hdfsUrlSuffix}")
    private String hdfsUrlSuffix;

    @Autowired
    @Qualifier("handerService")
    @Override
    public void setService(HanderService service) {
        super.setService(service);
    }

    @Autowired
    private ItemService itemService;

    @Override
    public String toPage() {
        return null;
    }

    @Override
    public AjaxResponse addModel(@RequestBody Hander model) {
        model.setUpdateDate(DateFormatCalculate.createNowTime());
        return super.addModel(model);
    }

    /**
     * 更新模型
     */
    @Override
    public AjaxResponse updateModel(@RequestBody Hander model) {
        //如果不是文件夹，那么照常更新
        if (!model.checkFolder()) {
            return super.updateModel(model);
        } else {
            //否则迭代更新
            return AjaxResponse.getInstanceByResult(service.updateFolderName(model));
        }
    }

    /**
     * 删除模型
     */
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

    /**
     * 多重添加文件
     */
    @RequestMapping(value = "/addMulti", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse addMultiModel(@RequestBody HanderItemWrapper handerItemWrapper) {
        if (ApplicationConstant.notNull(handerItemWrapper)) {
            //根据包装器添加，建立hander和item的关系，同时批量更新item的状态
            boolean res = service.addHander2ItemByWrapper(handerItemWrapper)
                    && itemService.onlyChangeStatusByIds(ApplicationConstant.FILE_STATUS_NORMAL, handerItemWrapper.getItemIds());
            return AjaxResponse.getInstanceByResult(res);
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    /**
     * 多重删除
     */
    @RequestMapping(value = "/deleteMulti", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResponse deleteMultiModel(@RequestBody List<Hander> handers) {
        if (ApplicationConstant.notNull(handers)) {
            //迭代删除
            return AjaxResponse.getInstanceByResult(service.deleteMultiHander(handers).get());
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);

    }

    /**
     * 根据父节点查询下属子文件
     */
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
            return ApplicationConstant.collectionCheck(list);
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    /**
     * 根据父节点查询，并包装
     */
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
        if (ApplicationConstant.notNull(fullPath, member)) {
            return ApplicationConstant.collectionCheck(service.getHanderListByPath(member.getMemberId(), fullPath));
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse getMultiDownloadLink(@RequestBody List<Hander> handerList, HttpServletRequest request) {
        Member member = getLoginMember(request);
        if (ApplicationConstant.notNull(handerList, member)) {
            Set<String> ids = new HashSet<>();
            handerList.forEach(hander -> ids.add(hander.getHanderId()));
            List<HanderDownloadWrapper> list = service.getMultiDownloadLink(ids, member.getMemberId());
            list.forEach(item -> {
                item.setHdfsPath(hdfsUrlPrefix + item.getHdfsPath() + hdfsUrlSuffix);
                item.setServerPath(netPath + item.getServerPath());
            });

            return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(list)).addAttribute("list", list);
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    @RequestMapping(value = "/zTree", method = RequestMethod.GET)
    @ResponseBody
    public List<ZTreeModel> getZTreeHanderByParentId(HttpServletRequest request) {
        String parentId = request.getParameter("id");
        if (ApplicationConstant.isNull(parentId)) {
            parentId = "-1";
        }
        List<ZTreeModel> list = service.getZTreeHander(parentId, getLoginMember(request).getMemberId());
        return list;
    }

    @RequestMapping(value = "/copyOrMove/{type}", method = RequestMethod.PUT)
    @ResponseBody
    public AjaxResponse updateCopyOrMoveHander(@PathVariable String type, @RequestBody CopyOrMoveWrapper copyOrMoveWrapper, HttpServletRequest request) {
        Member member = getLoginMember(request);
        boolean res = true;
        if (ApplicationConstant.notNull(member, copyOrMoveWrapper, type)) {
            if (type.equals("copy")) {
                return AjaxResponse.getInstanceByResult(res)
                        .addReturnMsg("复制" + (res ? ApplicationConstant.SUCCESSCHN : ApplicationConstant.ERRORCHN));
            } else if (type.equals("move")) {
                List<Hander> list = copyOrMoveWrapper.getSelection();
                list.forEach(hander -> {
                    if (!hander.getMemberId().equals(member.getMemberId())) {
                        return;
                    }
                    hander.setParentId(copyOrMoveWrapper.getNewParentId());
                    service.updateEntry(hander);
                });
                return AjaxResponse.getInstanceByResult(res)
                        .addReturnMsg("移动" + (res ? ApplicationConstant.SUCCESSCHN : ApplicationConstant.ERRORCHN));
            } else {
                return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
            }
        }
        return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN);
    }

    private static class CopyOrMoveWrapper {
        private List<Hander> selection;
        private String newParentId;

        public List<Hander> getSelection() {
            return selection;
        }

        public void setSelection(List<Hander> selection) {
            this.selection = selection;
        }

        public String getNewParentId() {
            return newParentId;
        }

        public void setNewParentId(String newParentId) {
            this.newParentId = newParentId;
        }
    }
}
