package com.hatcloud.genealogy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
public class AllInfoActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_info);
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        ListView listView = (ListView) findViewById(R.id.listView);
        List<HashMap<String, String>> data = new ArrayList<HashMap<String,
                String>>();
        List<Person> people = PersonDBUtil.cursorToPeople(personDBUtil.getAllPeople());

        for (Person person : people) {
            HashMap<String, String> p = new HashMap<String, String>();
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
    }

    public void onClickAddItem(View view) {
        Intent i = new Intent(this, AddItemActivity.class);
        startActivity(i);
    }
}
