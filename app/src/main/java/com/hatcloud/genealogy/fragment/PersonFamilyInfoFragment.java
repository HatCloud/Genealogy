package com.hatcloud.genealogy.fragment;

import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hatcloud.genealogy.R;
import com.hatcloud.genealogy.activity.PersonInfoActivity;
import com.hatcloud.genealogy.db.PersonDBUtil;
import com.hatcloud.genealogy.model.Person;
import com.hatcloud.genealogy.util.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jeff on 15/5/17.
 */
public class PersonFamilyInfoFragment extends Fragment {

    TextView tvFather;
    TextView tvMother;
    TextView tvSpousesTitle;
    ListView lvSpouses;
    ListView lvChildren;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_person_family_info, container, false);

        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        final Person person = ((PersonInfoActivity) getActivity()).getPerson();
        final Person father = personDBUtil.getFather(person);
        final Person mother = personDBUtil.getMother(person);
        String fatherName = "佚名";
        String motherName = "佚名";

        tvFather = (TextView) view.findViewById(R.id.father);
        if (father != null) {
            fatherName = father.getName();
            tvFather.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            tvFather.getPaint().setAntiAlias(true);//抗锯齿
        }
        tvFather.setText(fatherName);
        tvFather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(father != null) {
                    PersonInfoActivity.actionStart(MyApplication.getContext(), "" + father.getId());
                }
            }
        });


        tvMother = (TextView) view.findViewById(R.id.mother);
        if (mother != null) {
            motherName = mother.getName();
            tvMother.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            tvMother.getPaint().setAntiAlias(true);//抗锯齿
        }
        tvMother.setText(motherName);
        tvMother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mother != null) {
                    PersonInfoActivity.actionStart(MyApplication.getContext(), "" + mother.getId());
                }
            }
        });

        lvChildren = (ListView) view.findViewById(R.id.children);
        List<HashMap<String, String>> data = new ArrayList<>();
        List<Person> children = PersonDBUtil.cursorToPeople(personDBUtil.getChildren(person));

        for (Person child : children) {
            HashMap<String, String> p = new HashMap<>();
            p.put("id", String.valueOf(child.getId()));
            p.put("name", child.getName());
            p.put("sex", child.getSexStr());
            p.put("birth_date", child.getBirthDate());
            data.add(p);
        }

        SimpleAdapter adapter = new SimpleAdapter(MyApplication.getContext(), data,
                R.layout.item_children,
                new String[] { "name", "sex", "birth_date" },
                new int[] {R.id.name, R.id.sex, R.id.birth_date});

        lvChildren.setAdapter(adapter);
        lvChildren.setOnItemClickListener(new ItemClickListener());

        lvSpouses = (ListView) view.findViewById(R.id.spouses);
        tvSpousesTitle = (TextView) view.findViewById(R.id.spouses_title);
        if (person.getSex() == 1) {
            tvSpousesTitle.setText("妻子");
        } else if (person.getSex() == 1){
            tvSpousesTitle.setText("丈夫");
        }
        data = new ArrayList<>();
        List<Person> spouses = personDBUtil.getSpouse(person);

        for (Person spouse : spouses) {
            HashMap<String, String> p = new HashMap<>();
            p.put("id", String.valueOf(spouse.getId()));
            p.put("name", spouse.getName());
            p.put("birth_date", spouse.getBirthDate());
            data.add(p);
        }

        adapter = new SimpleAdapter(MyApplication.getContext(), data,
                R.layout.item_spouses,
                new String[] { "name", "birth_date" },
                new int[] {R.id.name, R.id.birth_date});

        lvSpouses.setAdapter(adapter);
        lvSpouses.setOnItemClickListener(new ItemClickListener());

        return view;
    }

    private class ItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = (ListView) parent;
            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
            String personid = data.get("id").toString();
            PersonInfoActivity.actionStart(MyApplication.getContext(), personid);
        }
    }
}
