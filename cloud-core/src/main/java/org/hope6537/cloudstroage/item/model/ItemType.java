package org.hope6537.cloudstroage.item.model;

import org.hope6537.cloudstroage.basic.model.BasicModel;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public class ItemType extends BasicModel {

    private static final long serialVersionUID = -2980673066684276250L;

    private String typeId;

    private String typeName;


    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String commonId() {
        return getTypeId();
    }
}
