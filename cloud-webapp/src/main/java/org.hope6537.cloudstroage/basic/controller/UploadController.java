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
import org.hope6537.date.DateFormatCalculate;
import org.hope6537.file.FileUtil;
import org.hope6537.hadoop.ConfigurationFactory;
import org.hope6537.hadoop.hdfs.HdfsUtils;
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

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @ResponseBody
    public AjaxResponse uploadImage(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        String fileName = getUploadFileName(multipartFile);
        String serverPath = getServerPath(ResourceFile.IMAGE);
        String netURL = getURL(ResourceFile.IMAGE);
        boolean result = false;
        try {
            HdfsUtils hdfsUtils = HdfsUtils.getInstanceOfPseudoDistributed(ConfigurationFactory.getConfigurationOfPseudoDistributed());
            InputStream source = multipartFile.getInputStream();
            OutputStream server = new FileOutputStream(new File(serverPath, fileName));
            OutputStream hdfs = hdfsUtils.getHdfsOutPutStream("/CloudStroage/user/hope6537/" + DateFormatCalculate.createNowTime(DateFormatCalculate.BASIC_DATE_FORMAT) + "/" + fileName);
            closeStream(new Closeable[]{source, server, hdfs});
            result = FileUtil.copyFileToServerAndHDFS(source, server, hdfs, 4096);
        } catch (IOException e) {
            return new AjaxResponse(ReturnState.ERROR, ApplicationConstant.ERRORCHN).addAttribute("Exception", e.getMessage());
        }
        return AjaxResponse.getInstanceByResult(result)
                .addAttribute("serverPath", netURL + File.separator + fileName)
                .addAttribute("hdfsPath", hdfsURL + "/" + fileName);
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
        return serverPath + File.separator + ResourceFile.FILEUPLOAD + File.separator + fileType;
    }

    public String getURL(String fileType) {
        return netURL + File.separator + ResourceFile.FILEUPLOAD + File.separator + fileType;
    }


}
