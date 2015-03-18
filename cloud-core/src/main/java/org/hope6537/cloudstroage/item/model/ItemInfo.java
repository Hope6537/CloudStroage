package org.hope6537.cloudstroage.item.model;

import org.hope6537.cloudstroage.basic.context.ApplicationConstant;
import org.hope6537.cloudstroage.basic.model.BasicModel;
import org.hope6537.date.DateFormatCalculate;

import java.io.File;
import java.util.UUID;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public class ItemInfo extends BasicModel {

    private static final long serialVersionUID = -9212078382954330289L;

    private String itemId;

    private String absolutePath;

    private String serverPath;

    private String itemTypeId;

    private String sha1;

    private String size;

    private File file;

    public ItemInfo() {

    }

    public static ItemInfo getInstanceOfTest() {
        String fileName = UUID.randomUUID().toString();
        return new ItemInfo(
                "hdfs://hadoop2master/user/" + DateFormatCalculate.createNowTime() + "/" + fileName,
                "http://static.hope6537.org/upload/" + DateFormatCalculate.createNowTime() + "/" + fileName,
                ApplicationConstant.STATUS_NORMAL,
                "-1",
                "_sha111111",
                "1.24MB");
    }


    public ItemInfo(String absolutePath, String serverPath, String status, String itemTypeId, String sha1, String size) {
        this.absolutePath = absolutePath;
        this.serverPath = serverPath;
        this.status = status;
        this.itemTypeId = itemTypeId;
        this.sha1 = sha1;
        this.size = size;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(String itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
    }

    @Override
    public String commonId() {
        return getItemId();
    }

    @Override
    public String getStatus() {
        return super.getStatus();
    }
}
