package com.hatcloud.genealogy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hatcloud.genealogy.R;
import com.hatcloud.genealogy.db.PersonDBUtil;
import com.hatcloud.genealogy.model.Person;
import com.hatcloud.genealogy.util.MyApplication;

/**
 * 这是用于显示数据库中所有的信息的Activity
 * Created by Jeff on 15/5/14.
 */
public class AllInfoActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_info);
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        ListView listView = (ListView) findViewById(R.id.listView);
        List<HashMap<String, String>> data = new ArrayList<>();
        List<Person> people = PersonDBUtil.cursorToPeople(personDBUtil.getAllPeople());


        //personDBUtil.deleteAll();
        //importTestData();

        for (Person person : people) {
            HashMap<String, String> p = new HashMap<>();
            String sex = person.getSex() == 1 ? "男" : "女";
            p.put("id", String.valueOf(person.getId()));
            p.put("name", person.getName());
            p.put("age",String.valueOf(person.getAge()));
            p.put("sex", sex);
            p.put("family_id", String.valueOf(person.getFamilyId()));
            p.put("parent_id", String.valueOf(person.getParentId()));
            data.add(p);
        }

        SimpleAdapter adapter = new SimpleAdapter(AllInfoActivity.this, data,
                R.layout.item_all_info,
                new String[] { "id", "name", "age", "sex", "family_id", "parent_id" },
                new int[] {R.id.id, R.id.name, R.id.age, R.id.sex, R.id.familyId, R.id.parentId });

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ItemClickListener());
    }

    public void onClickAddItem(View view) {
        Intent i = new Intent(this, AddItemActivity.class);
        startActivity(i);
    }

    private class ItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = (ListView) parent;
            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
            String personid = data.get("id").toString();
            PersonInfoActivity.actionStart(AllInfoActivity.this, personid);
        }
    }

    private void importTestData() {
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());

        Person person1 = new Person("田日天", 1, "1950-4-5", "2010-5-7", 1, -1);
        Person person2 = new Person("林绿华", 2, "1951-7-23", "1993-7-2", 1, -1);
        Person person3 = new Person("赵破劫", 1, "1943-6-1", "2004-1-2", 2, -1);
        Person person4 = new Person("王无烟", 2, "1941-6-8", "", 2, -1);
        Person person5 = new Person("田斩仙", 1, "1970-11-4", "", 3, 1);
        Person person6 = new Person("赵美琳", 2, "1971-3-1", "", 3, 2);
        Person person7 = new Person("田诛魔", 1, "1975-1-5", "", 4, 1);
        Person person8 = new Person("张雪瑶", 2, "1973-3-1", "2004-3-6", 4, -1);
        Person person9 = new Person("田血衣", 2, "1980-6-23", "", -2, 1);
        Person person10 = new Person("田傲天", 1, "2000-7-1", "", -2, 4);
        Person person11 = new Person("赵美丽", 2, "1965-2-5", "", -2, 2);
        Person person12 = new Person("田浩", 1, "1992-3-1", "", -2, 3);

        personDBUtil.save(person1);
        personDBUtil.save(person2);
        personDBUtil.save(person3);
        personDBUtil.save(person4);
        personDBUtil.save(person5);
        personDBUtil.save(person6);
        personDBUtil.save(person7);
        personDBUtil.save(person8);
        personDBUtil.save(person9);
        personDBUtil.save(person10);
        personDBUtil.save(person11);
        personDBUtil.save(person12);

    }
}
