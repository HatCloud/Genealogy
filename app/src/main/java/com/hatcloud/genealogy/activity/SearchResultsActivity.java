package com.hatcloud.genealogy.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hatcloud.genealogy.R;
import com.hatcloud.genealogy.db.PersonDBUtil;
import com.hatcloud.genealogy.model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jeff on 15/5/20.
 */
public class SearchResultsActivity extends Activity {

    ListView lvSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        lvSearchResult = (ListView) findViewById(R.id.lv_search_result);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            PersonDBUtil personDBUtil = PersonDBUtil.getIntance(this);
            List<Person> personList = PersonDBUtil.cursorToPeople(personDBUtil.queryByName(query));
            List<HashMap<String, String>> data = new ArrayList<>();

            for (Person person : personList) {
                HashMap<String, String> p = new HashMap<>();
                String sex = person.getSex() == 1 ? "男" : "女";
                p.put("id", String.valueOf(person.getId()));
                p.put("name", person.getName());
                p.put("sex", sex);
                p.put("birth_date", person.getBirthDate());
                data.add(p);
            }

            SimpleAdapter adapter = new SimpleAdapter(SearchResultsActivity.this, data,
                    R.layout.item_all_info,
                    new String[] { "name", "sex", "birth_date" },
                    new int[] {R.id.name, R.id.sex, R.id.birth_date});
            lvSearchResult.setAdapter(adapter);
            lvSearchResult.setOnItemClickListener(new ItemClickListener());
        }
    }

    private class ItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = (ListView) parent;
            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
            String personid = data.get("id").toString();
            PersonInfoActivity.actionStart(SearchResultsActivity.this, personid);
        }
    }
}
