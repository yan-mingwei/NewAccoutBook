package com.yan.newaccountbook.bean;

public class TypeBean {
    int id;
    String typeName;
    int imageId;
    int sImageId;
    int kind;   //收入 1  支出  0

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getsImageId() {
        return sImageId;
    }

    public void setsImageId(int sImageId) {
        this.sImageId = sImageId;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public TypeBean(int id, String typeName, int imageId, int sImageId, int kind) {
        this.id = id;
        this.typeName = typeName;
        this.imageId = imageId;
        this.sImageId = sImageId;
        this.kind = kind;
    }
}
