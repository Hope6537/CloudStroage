package org.hope6537.cloudstroage.item.model;

import org.hope6537.cloudstroage.basic.model.BasicModel;
import org.hope6537.context.ApplicationConstant;

import java.io.File;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public class ItemInfo extends BasicModel {

    private static final long serialVersionUID = -9212078382954330289L;
    private Integer itemId;

    private String fileName;

    private String absolutePath;

    private String path;

    private String folder;

    private Integer parentId;

    private Integer itemTypeId;

    private File file;

    public ItemInfo() {

    }

    public static ItemInfo getInstanceOfTest() {
        return new ItemInfo("_testFileName", "_/cloudstroage/user/hope6537/test.dat", "_/test.dat", "folder", -1, ApplicationConstant.STATUS_NORMAL, -1);
    }


    public ItemInfo(String fileName, String absolutePath, String path, String folder, Integer parentId, String status, Integer itemTypeId) {
        this.fileName = fileName;
        this.absolutePath = absolutePath;
        this.path = path;
        this.folder = folder;
        this.parentId = parentId;
        this.status = status;
        this.itemTypeId = itemTypeId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    public Integer getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Integer itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
