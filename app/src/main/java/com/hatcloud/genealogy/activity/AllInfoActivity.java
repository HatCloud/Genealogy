package com.hatcloud.genealogy.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
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
        importTestData();

        for (Person person : people) {
            HashMap<String, String> p = new HashMap<>();
            String sex = person.getSex() == 1 ? "男" : "女";
            p.put("id", String.valueOf(person.getId()));
            p.put("name", person.getName());
            p.put("age",String.valueOf(person.getAge()));
            p.put("sex", sex);
            p.put("father_id", String.valueOf(person.getFatherId()));
            p.put("mother_id", String.valueOf(person.getMotherId()));
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
        Intent i = new Intent(this, FamilyTreeActivity.class);
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

    @Override

    public
    boolean
    onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actionbar, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //set up the query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //start the search intent
                Intent searchIntent = new Intent(AllInfoActivity.this, SearchResultsActivity.class);
                searchIntent.setAction(Intent.ACTION_SEARCH);
                searchIntent.putExtra(SearchManager.QUERY, query);
                startActivity(searchIntent);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                //do nothing in our case
                return true;
            }
        });

        return true;

    }



    private void importTestData() {
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());

        Person person = new Person("马", "氏", 2, 0, "1755-7-5");
        person.setDeathDate("1800-3-4");
        personDBUtil.save(person);

        person = new Person("刘", "氏", 2, 0, "1760-2-1");
        person.setDeathDate("1820-5-10");
        personDBUtil.save(person);

        person = new Person("田", "广传", 1, 1, "1750-4-5");
        person.setDeathDate("1812-3-1");
        person.setStyleName("子路");
        person.setHaoName("清远");
        person.setFamilyHierarchyPosition(134);
        for(Person p : PersonDBUtil.cursorToPeople(personDBUtil.queryByName("马氏"))){
            person.setSpouseIds(person.getSpouseIds() + "" + p.getId());
        }
        personDBUtil.save(person);

        person = new Person("田", "巨源", 1, 1, "1766-12-5");
        person.setDeathDate("1831-3-1");
        for(Person p : PersonDBUtil.cursorToPeople(personDBUtil.queryByName("田广传"))){
            person.setFatherId(p.getId());
        }
        person.setFamilyHierarchyPosition(personDBUtil.find(person.getFatherId()).getFamilyHierarchyPosition() + 1);
        personDBUtil.save(person);

        person = new Person("田", "巨泉", 1, 2, "1767-1-5");
        person.setDeathDate("1810-2-24");
        for(Person p : PersonDBUtil.cursorToPeople(personDBUtil.queryByName("田广传"))){
            person.setFatherId(p.getId());
        }
        person.setFamilyHierarchyPosition(personDBUtil.find(person.getFatherId()).getFamilyHierarchyPosition() + 1);
        personDBUtil.save(person);

        person = new Person("田", "巨川", 1, 3, "1770-10-2");
        person.setDeathDate("1840-2-24");
        for(Person p : PersonDBUtil.cursorToPeople(personDBUtil.queryByName("田广传"))){
            person.setFatherId(p.getId());
        }
        person.setFamilyHierarchyPosition(personDBUtil.find(person.getFatherId()).getFamilyHierarchyPosition() + 1);
        personDBUtil.save(person);

        person = new Person("田", "巨浪", 1, 4, "1775-12-3");
        person.setDeathDate("1830-1-14");
        for(Person p : PersonDBUtil.cursorToPeople(personDBUtil.queryByName("田广传"))){
            person.setFatherId(p.getId());
        }
        person.setFamilyHierarchyPosition(personDBUtil.find(person.getFatherId()).getFamilyHierarchyPosition() + 1);
        personDBUtil.save(person);

        person = new Person("田", "高千", 1, 1, "1790-12-5");
        person.setDeathDate("1852-3-4");
        for(Person p : PersonDBUtil.cursorToPeople(personDBUtil.queryByName("田巨浪"))){
            person.setFatherId(p.getId());
        }
        person.setFamilyHierarchyPosition(personDBUtil.find(person.getFatherId()).getFamilyHierarchyPosition() + 1);
        personDBUtil.save(person);

        person = new Person("田", "必从", 1, 1, "1818-2-27");
        person.setDeathDate("1863-12-4");
        for(Person p : PersonDBUtil.cursorToPeople(personDBUtil.queryByName("田高千"))){
            person.setFatherId(p.getId());
        }
        person.setFamilyHierarchyPosition(personDBUtil.find(person.getFatherId()).getFamilyHierarchyPosition() + 1);
        personDBUtil.save(person);

        person = new Person("田", "玟璋", 1, 1, "1840-2-12");
        person.setDeathDate("1900-11-24");
        for(Person p : PersonDBUtil.cursorToPeople(personDBUtil.queryByName("必从"))){
            person.setFatherId(p.getId());
        }
        person.setFamilyHierarchyPosition(personDBUtil.find(person.getFatherId()).getFamilyHierarchyPosition() + 1);
        personDBUtil.save(person);

        person = new Person("田", "广", 1, 1, "1870-3-17");
        person.setDeathDate("1940-9-20");
        for(Person p : PersonDBUtil.cursorToPeople(personDBUtil.queryByName("玟璋"))){
            person.setFatherId(p.getId());
        }
        person.setFamilyHierarchyPosition(personDBUtil.find(person.getFatherId()).getFamilyHierarchyPosition() + 1);
        personDBUtil.save(person);

        person = new Person("田", "恭良", 1, 1, "1905-12-5");
        person.setDeathDate("1978-12-20");
        for(Person p : PersonDBUtil.cursorToPeople(personDBUtil.queryByName("广"))){
            person.setFatherId(p.getId());
        }
        person.setFamilyHierarchyPosition(personDBUtil.find(person.getFatherId()).getFamilyHierarchyPosition() + 1);
        personDBUtil.save(person);

        person = new Person("田", "漆宗", 1, 1, "1942-11-1");
        person.setDeathDate("2001-2-10");
        for(Person p : PersonDBUtil.cursorToPeople(personDBUtil.queryByName("恭良"))){
            person.setFatherId(p.getId());
        }
        person.setFamilyHierarchyPosition(personDBUtil.find(person.getFatherId()).getFamilyHierarchyPosition() + 1);
        personDBUtil.save(person);


        person = new Person("田", "浩", 1, 1, "1970-8-13");
        for(Person p : PersonDBUtil.cursorToPeople(personDBUtil.queryByName("漆宗"))){
            person.setFatherId(p.getId());
        }
        person.setFamilyHierarchyPosition(personDBUtil.find(person.getFatherId()).getFamilyHierarchyPosition() + 1);
        personDBUtil.save(person);

    }
}
