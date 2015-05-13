package com.hatcloud.genealogy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jeff on 15/5/12.
 * 建立本应用数据库的工具类
 */
public class GenealogyOpenHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "db_genealogy";

    public static int VERSION = 1;

    public static final String CREATE_PERSON = "create table Person ("
            + "_id integer primary key autoincrement"
            + "name text"
            + "sex integer"    //0:男性 1:女性
            + "birth_date text"
            + "death_date text"
            + "family_id integer"
            + "parent_id integer)";


    public GenealogyOpenHelper(Context context) {
        super(context,DB_NAME,null,VERSION);
    }

    /**
     * @param context 应用的上下文
     * @param name 应用的数据库名字
     * @param factory 是指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
     * @param version  数据库版本，必须是大于0的int（即非负数）
     */
    public GenealogyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion) {
        }

    }
}
