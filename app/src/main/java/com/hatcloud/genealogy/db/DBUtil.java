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
public class DBUtil {

    private String[] allcolumn = new String[]{
            "_id", "name", "sex", "birth_date", "death_date", "family_id", "parent_id"};

    public static final int ID = 0;

    public static final int NAME = 1;

    public static final int SEX = 2;

    public static final int BIRTH_DATE = 3;

    public static final int DEATH_DATE = 4;

    public static final int FAMILY_ID = 5;

    public static final int PARENT_ID = 6;

    private static DBUtil dbUtil;

    private SQLiteDatabase db;

    private DBUtil(Context context) {
        GenealogyOpenHelper dbHelper = new GenealogyOpenHelper(context);

        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取DBUtil的实例
     */
    public synchronized static DBUtil getIntance(Context context) {
        if (dbUtil == null) {
            dbUtil = new DBUtil(context);
        }
        return dbUtil;
    }

    /**
     * 将新的信息存储进数据库
     */
    public void save(Person person) {
        if (person != null) {
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
     * @param person
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
            db.update("Person", null, "_id=?", new String[]{String.valueOf(person.getId())});
        }
    }


    public void delete(int id) {
        db.delete("Person", "_id=?", new String[]{ String.valueOf(id)});
    }

    public Person find(int id) {
        Cursor cursor = db.query("Person",allcolumn,"_id=?", new String[]{String.valueOf(id)},
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
     * @param name
     * @return 返回的是一个Cursor，提供给SimpleCursorAdapter使用
     */
    public Cursor queryByName(String name) {
        return db.query("Person", allcolumn, "name = ?", new String[]{name},
                null,null,null);
    }

    public Cursor queryByFamilyId(int familyId) {
        return db.query("Person", allcolumn, "family_id", new String[]{String.valueOf(familyId)},
                null,null,null);
    }

    public Cursor queryByParentId(int ParentId){
        return db.query("Person", allcolumn, "parent_id", new String[]{String.valueOf(ParentId)},
                null,null,null);
    }

    public Cursor getAllPeople() {
        return db.rawQuery("select * from Person", null);
    }

    /**
     * 获取数据库中数据的条数
     * @return
     */
    public long getCount() {
        Cursor cursor = db.rawQuery("select count(*) from Person", null);
        if (cursor.moveToNext()) {
            return cursor.getLong(0);
        }
        return 0;
    }

}
