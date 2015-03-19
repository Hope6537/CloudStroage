/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

package org.hope6537.cloudstroage.basic.controller;

import org.hope6537.ajax.AjaxResponse;
import org.hope6537.ajax.ReturnState;
import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.basic.context.ResourceFile;
import org.hope6537.cloudstroage.item.model.ItemInfo;
import org.hope6537.cloudstroage.item.service.ItemService;
import org.hope6537.date.DateFormatCalculate;
import org.hope6537.file.FileUtil;
import org.hope6537.hadoop.hdfs.HdfsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

/**
 * Created by Hope6537 on 2015/3/14.
 */
@Controller
public class UploadController {

    /**
     * 开发时路径
     */
    @Value("${serverPath}")
    private String serverPath;
    /**
     * 发布时路径
     */
    @Value("${netURL}")
    private String netURL;

    @Value("${hdfsURL}")
    private String hdfsURL;

    @Autowired
    private HdfsUtils hdfsUtils;

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @ResponseBody
    public AjaxResponse uploadImage(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        String fileName = getUploadFileName(multipartFile);
        String serverPathFolder = getServerPath(ResourceFile.FILE);
        String hdfsPathFolder = hdfsURL + "/CloudStroage/" + DateFormatCalculate.createNowTime(DateFormatCalculate.BASIC_DATE_FORMAT);
        String netURL = getURL(ResourceFile.FILE);
        String md5;
        try {
            InputStream source = multipartFile.getInputStream();
            File serverPathDirectory = new File(serverPathFolder);
            File hdfsPathDirectory = new File(hdfsPathFolder);
            synchronized (this) {
                if (!serverPathDirectory.exists()) {
                    serverPathDirectory.mkdir();
                }
                if (!hdfsPathDirectory.exists()) {
                    hdfsPathDirectory.mkdir();
                }

                OutputStream server = new FileOutputStream(new File(serverPathFolder, fileName));
                OutputStream hdfs = hdfsUtils.getHdfsOutPutStream(hdfsPathFolder + "/" + fileName);
                md5 = FileUtil.copyFileToServerAndHDFS(source, server, hdfs, 4096);
                closeStream(new Closeable[]{source, server, hdfs});
                if (ApplicationConstant.notNull(md5)) {
                    ItemInfo itemInfo = new ItemInfo();
                    itemInfo.setAbsolutePath(hdfsPathFolder + "/" + fileName);
                    itemInfo.setServerPath(serverPathFolder + "/" + fileName);
                    itemInfo.setSize(String.valueOf(multipartFile.getSize()));
                    itemInfo.setStatus(ApplicationConstant.FILE_STATUS_NO_CONTACT);
                    itemInfo.setSha1(md5);
                    itemService.addEntry(itemInfo);
                }

            }
        } catch (IOException e) {
            return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN).addAttribute("Exception", e.getMessage());
        }
        return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(md5))
                .addAttribute("serverPath", netURL + "/" + fileName)
                .addAttribute("hdfsPath", hdfsPathFolder + "/" + fileName);
    }

    private void closeStream(Closeable[] list) {
        for (Closeable stream : list) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getUploadFileName(MultipartFile multipartFile) {
        String uploadFileName = multipartFile.getOriginalFilename();
        String type = uploadFileName.substring(uploadFileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + type;
    }

    private String getServerPath(String fileType) {
        return serverPath + "/" + ResourceFile.FILEUPLOAD + "/" + fileType;
    }

    public String getURL(String fileType) {
        return netURL + "/" + ResourceFile.FILEUPLOAD + "/" + fileType;
    }


}
