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

    public static int VERSION = 9;

    public static final String CREATE_PERSON = "create table Person ("
            + "_id integer primary key autoincrement,"
            + "last_name text not null,"     //姓
            + "first_name text not null,"    //名
            + "name text,"           //姓名
            + "used_name text,"     //曾用名
            + "style_name text,"    //字
            + "hao_name text,"      //号
            + "sex integer not null,"        //0:男性 1:女性
            + "family_hierarchy_position int,"  //辈分
            + "father_id integer,"  //父亲
            + "mother_id integer,"  //母亲
            + "spouse_ids text,"     //配偶们
            + "family_order int not null,"    //家中排号
            + "birth_date text not null,"    //出生日期
            + "death_date text,"   //死亡日期
            + "life_info text)";   //生平


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

        db.execSQL("drop table if exists Person");
        onCreate(db);
        /*switch (oldVersion) {
            case 1:
                db.execSQL("drop table if exists Person");
                onCreate(db);
            case 3:
                db.execSQL("drop table if exists Person");
                onCreate(db);
            default:
        }*/

    }
}
