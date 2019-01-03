package com.example.ado.cookbookuser.model;

import org.litepal.crud.DataSupport;

//创建的食谱（数据库相关）
public class CreateCookBook extends DataSupport{

    private int id;
    private String name;
    private byte[] cover;
    private String material;
    private String step1;
    private String step2;
    private String step3;
    private String step4;
    private String step5;
    private String step6;
    private String step7;
    private String step8;
    private String step9;
    private String step10;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getStep1() {
        return step1;
    }

    public void setStep1(String step1) {
        this.step1 = step1;
    }

    public String getStep2() {
        return step2;
    }

    public void setStep2(String step2) {
        this.step2 = step2;
    }

    public String getStep3() {
        return step3;
    }

    public void setStep3(String step3) {
        this.step3 = step3;
    }

    public String getStep4() {
        return step4;
    }

    public void setStep4(String step4) {
        this.step4 = step4;
    }

    public String getStep5() {
        return step5;
    }

    public void setStep5(String step5) {
        this.step5 = step5;
    }

    public String getStep6() {
        return step6;
    }

    public void setStep6(String step6) {
        this.step6 = step6;
    }

    public String getStep7() {
        return step7;
    }

    public void setStep7(String step7) {
        this.step7 = step7;
    }

    public String getStep8() {
        return step8;
    }

    public void setStep8(String step8) {
        this.step8 = step8;
    }

    public String getStep9() {
        return step9;
    }

    public void setStep9(String step9) {
        this.step9 = step9;
    }

    public String getStep10() {
        return step10;
    }

    public void setStep10(String step10) {
        this.step10 = step10;
    }


}

