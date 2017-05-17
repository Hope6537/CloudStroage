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
    private HdfsUtils hdfsUtils;

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @ResponseBody
    public AjaxResponse uploadImage(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {

        String projectPath = System.getProperty("user.dir");
        String fileName = getUploadFileName(multipartFile);
        String serverPathFolder = projectPath + "/" + getServerPath(ResourceFile.FILE);
        String hdfsPathFolderNoUrl = getHdfsFolderPath();
        String hdfsPathFolder = hdfsPath + hdfsPathFolderNoUrl;
        String netURL = getURL(ResourceFile.FILE);
        ItemInfo itemInfo = new ItemInfo();
        String md5;
        OutputStream server = null;
        try {
            InputStream source = multipartFile.getInputStream();
            File serverPathDirectory = new File(serverPathFolder);
            File hdfsPathDirectory = new File(hdfsPathFolder);
            synchronized (this) {
                if (!serverPathDirectory.exists()) {
                    serverPathDirectory.mkdirs();
                }
                if (!hdfsPathDirectory.exists()) {
                    hdfsPathDirectory.mkdirs();
                }
                server = new FileOutputStream(new File(serverPathFolder, fileName));
                OutputStream hdfs = hdfsUtils.getHdfsOutPutStream(hdfsPathFolder + "/" + fileName);
                md5 = FileUtil.copyFileToServerAndHDFS(source, server, hdfs, 4096);
                closeStream(new Closeable[]{source, server, hdfs});
                if (ApplicationConstant.notNull(md5)) {

                    itemInfo.setAbsolutePath(hdfsPathFolderNoUrl + "/" + fileName);
                    itemInfo.setServerPath(netURL + "/" + fileName);
                    itemInfo.setSize(String.valueOf(multipartFile.getSize()));
                    itemInfo.setStatus(ApplicationConstant.FILE_STATUS_NO_CONTACT);
                    itemInfo.setSha1(md5);
                    itemService.addEntry(itemInfo);
                }

            }
        } catch (IOException e) {
            return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN).addAttribute("Exception", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN).addAttribute("Exception", e.getMessage());
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
                return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN).addAttribute("Exception", e.getMessage());
            }
        }
        return AjaxResponse.getInstanceByResult(ApplicationConstant.notNull(md5))
                .addAttribute("serverPath", netPath + netURL + "/" + fileName)
                .addAttribute("hdfsPath", hdfsPathFolderNoUrl + "/" + fileName)
                .addAttribute("itemId", itemInfo.getItemId());
    }

    private void closeStream(Closeable[] list) {
        for (Closeable stream : list) {
            if (stream == null) {
                continue;
            }
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

    private String getHdfsFolderPath() {
        return "/CloudStroage/" + DateFormatCalculate.createNowTime(DateFormatCalculate.BASIC_DATE_FORMAT);
    }

    public String getURL(String fileType) {
        return netURL + "/" + ResourceFile.FILEUPLOAD + "/" + fileType;
    }

}
