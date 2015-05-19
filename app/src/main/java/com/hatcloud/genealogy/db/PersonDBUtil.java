package com.hatcloud.genealogy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hatcloud.genealogy.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 15/5/13.
 */
public class PersonDBUtil {

    //数据库的所有列，为了方便rawQuerry()方法的使用
    private String[] allColumn = new String[]{
            "_id", "name", "sex", "birth_date", "death_date", "family_id", "parent_id"};


    public static final int ID = 0;

    public static final int NAME = 1;

    public static final int SEX = 2;

    public static final int BIRTH_DATE = 3;

    public static final int DEATH_DATE = 4;

    public static final int FAMILY_ID = 5;

    public static final int PARENT_ID = 6;

    private static PersonDBUtil personDBUtil;

    private SQLiteDatabase db;

    private PersonDBUtil(Context context) {
        GenealogyOpenHelper dbHelper = new GenealogyOpenHelper(context);

        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取DBUtil的实例，通过synchronized关键字的修饰可以保证同一时间只有一个线程获得数据库的实例
     */
    public synchronized static PersonDBUtil getIntance(Context context) {
        if (personDBUtil == null) {
            personDBUtil = new PersonDBUtil(context);
        }
        return personDBUtil;
    }

    /**
     * 将新的信息存储进数据库
     * @param person 存储的条目
     */
    public void save(Person person) {

        boolean flag = true;
        List<Person> people = cursorToPeople(queryByName(person.getName()));
        for (Person p : people) {
            if (p.equals(person)) {
                flag = false;
            }
        }

        if (person != null && flag) {
            ContentValues values = new ContentValues();
            values.put("name", person.getName());
            values.put("sex", person.getSex());
            values.put("birth_date", person.getBirthDate());
            values.put("death_date", person.getDeathDate());
            values.put("family_id", person.getFamilyId());
            values.put("parent_id", person.getParentId());
            db.insert("Person", null, values);
        }
    }

    /**
     * 更新数据
     * @param person 要更新数据的人
     */
    public void update(Person person) {
        if (person != null) {
            ContentValues values = new ContentValues();
            values.put("name", person.getName());
            values.put("sex", person.getSex());
            values.put("birth_date", person.getBirthDate());
            values.put("death_date", person.getDeathDate());
            values.put("family_id", person.getFamilyId());
            values.put("parent_id", person.getParentId());
            db.update("Person", values, "_id=?", new String[]{String.valueOf(person.getId())});
        }
    }

    /**
     * 调用delete(id1)来删除id为id1的条目
     * 调用delete(id1, id2, id3,...)来删除多个条目
     * @param ids 可以是一个或多个int型参数
     */
    public void delete(int... ids) {
        if (ids.length > 0) {
            StringBuffer sb = new StringBuffer();
            Integer[] integers = new Integer[ids.length];
            int j = 0;
            for (int i : ids) {
                sb.append('?').append(',');
                integers[j++] = i;
            }
            sb.deleteCharAt(sb.length() - 1);

            db.execSQL("delete from Person where _id in(" + sb.toString() + ")", integers);
        }
    }

    /**
     * 在不删除表的情况下清空表里所有的数据
     * 注意：在删除所有数据后，新加入的数据的自增ID不是从0开始的
     */
    public void deleteAll() {
        db.execSQL("delete from Person");
    }

    /**
     * 查找方法
     * @param id 所查找人的id
     * @return 返回查找的结果，返回类型是Person
     */
    public Person find(int id) {
        Cursor cursor = db.query("Person", allColumn,"_id=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor.moveToNext()) {
            return new Person(cursor.getInt(ID),
                    cursor.getString(NAME),
                    cursor.getInt(SEX),
                    cursor.getString(BIRTH_DATE),
                    cursor.getString(DEATH_DATE),
                    cursor.getInt(FAMILY_ID),
                    cursor.getInt(PARENT_ID));
        }
        return null;
    }

    /**
     * 按名字查找人
     * @param name 所查找人的姓名
     * @return 返回的是一个Cursor，提供给SimpleCursorAdapter使用,也可以使用DBUtil.cursorToPeople(Cursor cursor)方法来获取Person集合
     */
    public Cursor queryByName(String name) {
        return db.query("Person", allColumn, "name = ?", new String[]{name},
                null,null,null);
    }

    /**
     * 根据familyId来查找一对夫妇
     * @param familyId 家庭编号，一对夫妇的家庭编号是一致的
     * @return 返回的是一个Cursor，提供给SimpleCursorAdapter使用,也可以使用DBUtil.cursorToPeople(Cursor cursor)方法来获取Person集合
     */
    public Cursor queryByFamilyId(int familyId) {
        return db.query("Person", allColumn, "family_id", new String[]{String.valueOf(familyId)},
                null,null,null);
    }

    /**
     * 根据parentId来查找某个家庭的孩子们
     * @param parentId 父母的家庭编号
     * @return 返回的是一个Cursor，提供给SimpleCursorAdapter使用,也可以使用DBUtil.cursorToPeople(Cursor cursor)方法来获取Person集合
     */
    public Cursor queryByParentId(int parentId){
        return db.query("Person", allColumn, "parent_id", new String[]{String.valueOf(parentId)},
                null,null,null);
    }

    /**
     * 获取Person表中所有的数据
     * @return 返回的是一个Cursor，提供给SimpleCursorAdapter使用,也可以使用DBUtil.cursorToPeople(Cursor cursor)方法来获取Person集合
     */
    public Cursor getAllPeople() {
        return db.rawQuery("select * from Person", null);
    }

    /**
     * 获取Person表中数据的条数
     * @return Person表中所有的有效数据的数量
     */
    public long getCount() {
        Cursor cursor = db.rawQuery("select count(*) from Person", null);
        if (cursor.moveToNext()) {
            return cursor.getLong(0);
        }
        return 0;
    }

    /**
     * 获取一个孩子的父亲
     * @param child 要查找父亲的孩子的Person实例
     * @return 传入参数所代表的孩子的父亲的Person实例
     */
    public Person getFather(Person child) {
        Cursor cursor = db.query("Person", allColumn,"family_id=? and sex=?"
                , new String[]{String.valueOf(child.getParentId()), String.valueOf(1)}
                , null, null, null);
        if (cursor.getCount() != 0) {
            return cursorToPeople(cursor).get(0);
        } else {
            return null;
        }

    }

    /**
     * 获取一个孩子的母亲
     * @param child 要查找母亲的孩子的Person实例
     * @return 传入参数所代表的孩子的母亲的Person实例
     */
    public Person getMother(Person child) {
        Cursor cursor = db.query("Person", allColumn,"family_id=? and sex=?"
                , new String[]{String.valueOf(child.getParentId()), String.valueOf(2)}
                , null, null, null);
        if (cursor.getCount() != 0) {
            return cursorToPeople(cursor).get(0);
        } else {
            return null;
        }
    }

    /**
     * 获取某个人所有的孩子
     * @param parent 要查找孩子的某个父母的Person实例
     * @return 返回的是一个查找到的所有孩子条目的Cursor实例，提供给SimpleCursorAdapter使用,也可以使用DBUtil.cursorToPeople(Cursor cursor)方法来获取Person集合
     */
    public Cursor getChildren(Person parent) {
        Cursor cursor = db.query("Person", allColumn, "parent_id=?", new String[]{String.valueOf(parent.getFamilyId())}
                , null, null, null);
        return cursor;
    }

    /**
     * 因为应用中很多方法返回的都只是Cursor，不是具体的Person集合，
     * 所以创建了这个方法，方便将其他方法得到的Cursor所指向的条目转换成Person集合的实例
     * @param cursor 用以转换的Cursor
     * @return 转换完成后的Person集合的实例
     */
    public static List<Person> cursorToPeople(Cursor cursor) {
        List<Person> people = new ArrayList<Person>();
        while (cursor.moveToNext()) {
            Person person = new Person(cursor.getInt(PersonDBUtil.ID),
                    cursor.getString(PersonDBUtil.NAME),
                    cursor.getInt(PersonDBUtil.SEX),
                    cursor.getString(PersonDBUtil.BIRTH_DATE),
                    cursor.getString(PersonDBUtil.DEATH_DATE),
                    cursor.getInt(PersonDBUtil.FAMILY_ID),
                    cursor.getInt(PersonDBUtil.PARENT_ID));
            people.add(person);
        }
        return people;
    }
}
