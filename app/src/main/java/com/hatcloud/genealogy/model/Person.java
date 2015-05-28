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

    /**
     * 数据库自动分配的ID
     */
    private int id;

    /**
     * 姓
     * 不能为空
     */
    private String lastName;

    /**
     * 名
     * 不能为空
     */
    private String firstName;

    /**
     * 曾用名
     */
    private String usedName;

    /**
     * 字
     */
    private String styleName;

    /**
     * 号
     */
    private String haoName;

    /**
     * 性别：1:男性 2:女性
     * 不能为空
     */
    private int sex;

    /**
     * 辈分
     */
    private int familyHierarchyPosition;

    /**
     * 父亲的ID
     */
    private int fatherId;

    /**
     * 母亲的ID
     */
    private int motherId;

    /**
     * 配偶们的ID
     * 格式为"ID1,ID2,ID3"
     */
    private String spouseIds;

    /**
     * 在兄弟姐妹中的排行，老大的排号为1
     * 独生子女的排号为1
     * 不能为空
     */
    private int familyOrder;

    /**
     * 出生日期
     * 格式为"YYYY-MM-DD"
     * 不能为空
     */
    private String birthDate;

    /**
     * 死亡日期
     * 格式为"YYYY-MM-DD"
     */
    private String deathDate;

    /**
     * 由姓和名组合的完整名字
     */
    private String name;

    /**
     * 辈分较老的先祖的尊称
     * 由 名 + “公” 组成
     */
    private String respectableName;

    public Person(String lastName, String firstName, int sex,
                  int familyOrder, String birthDate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.sex = sex;
        this.familyOrder = familyOrder;
        this.birthDate = birthDate;
        this.name = lastName + firstName;
        this.respectableName = firstName + "公";
    }

    public Person(int id, String lastName, String firstName, String usedName,
                  String styleName, String haoName, int sex, int familyHierarchyPosition,
                  int fatherId, int motherId, String spouseIds, int familyOrder,
                  String birthDate, String deathDate) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.usedName = usedName;
        this.styleName = styleName;
        this.haoName = haoName;
        this.sex = sex;
        this.familyHierarchyPosition = familyHierarchyPosition;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.spouseIds = spouseIds;
        this.familyOrder = familyOrder;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.name = lastName + firstName;
        this.respectableName = firstName + "公";
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

    public String getNodeName() {
        if (StrUtil.isEmpty(deathDate)) {
            return name;
        } else {
            return respectableName;
        }
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

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUsedName() {
        return usedName;
    }

    public String getStyleName() {
        return styleName;
    }

    public String getHaoName() {
        return haoName;
    }

    public int getFamilyHierarchyPosition() {
        return familyHierarchyPosition;
    }

    public int getFatherId() {
        return fatherId;
    }

    public int getMotherId() {
        return motherId;
    }

    public String getSpouseIds() {
        return spouseIds;
    }

    public int getFamilyOrder() {
        return familyOrder;
    }

    public String getRespectableName() {
        return respectableName;
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

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUsedName(String usedName) {
        this.usedName = usedName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public void setHaoName(String haoName) {
        this.haoName = haoName;
    }

    public void setFamilyHierarchyPosition(int familyHierarchyPosition) {
        this.familyHierarchyPosition = familyHierarchyPosition;
    }

    public void setFatherId(int fatherId) {
        this.fatherId = fatherId;
    }

    public void setMotherId(int motherId) {
        this.motherId = motherId;
    }

    public void setSpouseIds(String spouseIds) {
        this.spouseIds = spouseIds;
    }

    public void setFamilyOrder(int familyOrder) {
        this.familyOrder = familyOrder;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRespectableName(String respectableName) {
        this.respectableName = respectableName;
    }

    @Override
    public String toString() {

        if(!TextUtils.isEmpty(name)) {
            return "Person [id=" + id + ", name=" + name + ", sex=" + sex + ", "
                    + ", BirthDate=" + birthDate + ", DeathDate=" + deathDate
                    + ", FatherID=" + fatherId + ", MotherID=" + motherId + "]";
        }
        else {
            return "无";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (familyOrder != person.familyOrder) return false;
        if (sex != person.sex) return false;
        if (!birthDate.equals(person.birthDate)) return false;
        if (!firstName.equals(person.firstName)) return false;
        if (!lastName.equals(person.lastName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + lastName.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + sex;
        result = 31 * result + familyHierarchyPosition;
        result = 31 * result + fatherId;
        result = 31 * result + familyOrder;
        result = 31 * result + birthDate.hashCode();
        return result;
    }
}
