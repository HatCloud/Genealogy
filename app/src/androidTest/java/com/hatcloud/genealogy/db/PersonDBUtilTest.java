package com.hatcloud.genealogy.db;

import android.database.Cursor;
import android.util.Log;

import com.hatcloud.genealogy.model.Person;
import com.hatcloud.genealogy.util.MyApplication;

import junit.framework.TestCase;

import java.util.List;

public class PersonDBUtilTest extends TestCase {

    private static String TAG = "PersonDBUtilTest";

    public void testSave() throws Exception {
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());

        if(personDBUtil.getCount() == 0){
            for (int i = 0; i < 10; i++) {
                personDBUtil.save(new Person("张" + (i + 1), i % 2 + 1, "1980-05-" + (i + 1), i / 2));
            }
        }

    }

    public void testFind() throws Exception {
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        Person person = personDBUtil.find(9);
        Log.i(TAG, "---------------查找ID为9的条目---------------");
        Log.i(TAG, "结果: " + person.toString());
    }

    public void testUpdate() throws Exception {
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        Person person = personDBUtil.find(6);
        person.setName("张六");
        person.setFamilyId(4);
        personDBUtil.update(person);
        person = personDBUtil.find(5);
        person.setName("张五");
        person.setFamilyId(4);
        personDBUtil.update(person);
    }

    public void testDelete() throws Exception {
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        personDBUtil.delete(1, 2, 3);
    }

    public void testGetCount() throws Exception {
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        Log.i(TAG, "---------------所有条目的数量---------------");
        Log.i(TAG, "Count:" + String.valueOf(personDBUtil.getCount()));
    }

    public void testQueryByName() throws Exception {
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        Cursor cursor = personDBUtil.queryByName("张4");
        List<Person> people = PersonDBUtil.getPeopleFromCursor(cursor);

        Log.i(TAG, "---------------查找所有张4----------------------");
        for (Person person : people) {
            Log.i(TAG, person.toString());
        }
        Log.i(TAG, "---------------查找结束-------------------------");
    }

    public void testQueryByFamilyId() throws Exception {
        //todo
    }

    public void testQueryByParentId() throws Exception {
        //todo
    }

    public void testGetAllPeople() throws Exception {
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        Cursor cursor = personDBUtil.getAllPeople();
        List<Person> people = PersonDBUtil.getPeopleFromCursor(cursor);


        Log.i(TAG, "---------------开始显示所有人信息---------------");
        for (Person person : people) {
            Log.i(TAG, person.toString());
        }
        Log.i(TAG, "---------------显示结束------------------------");
    }

    public void testGetFather() throws Exception {
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        Person person = personDBUtil.find(9);
        Person father = personDBUtil.getFather(person);
        String fatherInfo;
        Log.i(TAG, "---------------父亲----------------------------");
        Log.i(TAG, "Child: " + person.toString());
        if (father != null) {
            fatherInfo = father.toString();
        }
        else {
            fatherInfo = "Null";
        }
        Log.i(TAG, "Father: " + fatherInfo);
    }

    public void testGetMother() throws Exception {
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        Person person = personDBUtil.find(9);
        Person mother = personDBUtil.getMother(person);
        String motherInfo;
        Log.i(TAG, "---------------母亲----------------------------");
        Log.i(TAG, "Child: " + person.toString());
        if (mother != null) {
            motherInfo = mother.toString();
        }
        else {
            motherInfo = "Null";
        }
        Log.i(TAG, "Mother: " + motherInfo);
    }

    public void testGetChildren() throws Exception {
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        Person parent = personDBUtil.find(5);
        List<Person> children = PersonDBUtil.getPeopleFromCursor(personDBUtil.getChildren(parent));

        Log.i(TAG, "---------------查找张五的孩子-------------------");
        for (Person person : children) {
            Log.i(TAG, person.toString());
        }
        Log.i(TAG, "---------------查找结束------------------------");
    }
}