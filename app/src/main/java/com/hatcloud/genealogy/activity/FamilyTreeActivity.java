package com.hatcloud.genealogy.activity;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.hatcloud.genealogy.R;
import com.hatcloud.genealogy.db.PersonDBUtil;
import com.hatcloud.genealogy.fragment.FamilyTreeFragment;
import com.hatcloud.genealogy.model.Person;
import com.hatcloud.genealogy.util.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 15/5/27.
 */
public class FamilyTreeActivity extends BaseActivity{

    public List<Person> rootPeople;
    public int tempId;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_family_tree);

        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(this);
        //personDBUtil.deleteAll();
        //importTestData();

        int rootPersonId = getIntent().getExtras().getInt("id", 0);
        tempId = getIntent().getExtras().getInt("temp_id", 0);

        for (Person p : personDBUtil.cursorToPeople(personDBUtil.getRootPeople())) {
            if (rootPersonId == p.getId()) {
                rootPersonId = 0;
                break;
            }
        }

        if (rootPersonId == 0) {
            rootPeople = personDBUtil.cursorToPeople(personDBUtil.getRootPeople());
            //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            rootPeople = new ArrayList<>();
            rootPeople.add(personDBUtil.find(rootPersonId));
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setTitle(personDBUtil.find(rootPersonId).getNodeName());
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }




        Fragment fragment = new FamilyTreeFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                Intent searchIntent = new Intent(FamilyTreeActivity.this, SearchResultsActivity.class);
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

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (rootPeople.size() > 1) {
                    break;
                }
                PersonDBUtil personDBUtil = PersonDBUtil.getIntance(this);
                Person person = rootPeople.get(0);
                int tempID = person.getId();
                for (int i = 0; i < 4; i++) {
                    if (person.getFatherId() > 0) {
                        person = personDBUtil.find(person.getFatherId());
                    } else {
                        return true;
                    }
                }
                actionStart(this, person.getId(), tempID);
                break;
        }
        return true;
    }*/

    public List<Person> getRootPeople() {
        return rootPeople;
    }


    public int getTempId() {
        return tempId;
    }

    public static void actionStart(Context context, int id, int tempID) {
        Intent intent = new Intent(context, FamilyTreeActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("temp_id", tempID);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
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
