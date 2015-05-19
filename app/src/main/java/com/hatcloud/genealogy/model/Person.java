package com.hatcloud.genealogy.model;

import android.text.TextUtils;

import com.hatcloud.genealogy.util.StrUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jeff on 15/5/13.
 */
public class Person {

    private int id;

    private String name;

    /**
     * 1:男性 2:女性
     */
    private int sex;

    private String birthDate;

    private String deathDate;

    /**
     * familyId是每个家庭的编号，一对夫妇为一个家庭
     */
    private int familyId;

    /**
     * parentId即父母的familyId
     */
    private int parentId;

    public Person(String name, int sex, String birthDate, int parentId) {
        this.name = name;
        this.sex = sex;
        this.birthDate = birthDate;
        this.parentId = parentId;
    }

    public Person(String name, int sex, String birthDate, String deathDate, int familyId, int parentId) {
        this.name = name;
        this.sex = sex;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.familyId = familyId;
        this.parentId = parentId;
    }

    public Person(int id, String name, int sex, String birthDate, String deathDate, int familyId, int parentId) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.familyId = familyId;
        this.parentId = parentId;
    }

    public int getYear(String dateStr) {
        Date date;
        int year = 0;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = simpleDateFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            year = calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return year;
    }

    public int getAge() {

        if (StrUtil.isEmpty(deathDate)) {
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            return Math.abs(currentYear - getYear(birthDate));
        } else {
            return Math.abs(getYear(deathDate) - getYear(birthDate));
        }
    }

    public int getNominalAge() {
        return getAge() + 1;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSex() {
        return sex;
    }

    public String getSexStr() {
        if (sex == 1) {
            return "男";
        } else if(sex == 2) {
            return "女";
        }

        return "未知";
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getDeathDate() {
        if (StrUtil.isEmpty(deathDate)) {
            return "";
        }
        return deathDate;
    }

    public int getFamilyId() {
        return familyId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {

        if(!TextUtils.isEmpty(name)) {
            return "Person [id=" + id + ", name=" + name + ", sex=" + sex + ", "
                    + ", BirthDate=" + birthDate + ", DeathDate=" + deathDate
                    + ", FamilyID=" + familyId + ", ParentID=" + parentId + "]";
        }
        else {
            return "无";
        }
    }

    @Override
    public boolean equals(Object o) {
        Person p = (Person) o;
        if (name.equals(p.getName())
                && sex == p.getSex()
                && birthDate.equals(p.getBirthDate())
                && deathDate.equals(p.getDeathDate())
                && familyId == p.getFamilyId()
                && parentId == p.getParentId()) {
            return true;
        } else {
            return false;
        }


    }
}
