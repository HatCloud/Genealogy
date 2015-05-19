package com.hatcloud.genealogy.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.hatcloud.genealogy.R;
import com.hatcloud.genealogy.db.PersonDBUtil;
import com.hatcloud.genealogy.fragment.PersonFamilyInfo;
import com.hatcloud.genealogy.fragment.PersonGeneralInfo;
import com.hatcloud.genealogy.fragment.PersonOperate;
import android.support.v7.widget.Toolbar;

import com.hatcloud.genealogy.model.Person;
import com.hatcloud.genealogy.util.MyApplication;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * Created by Jeff on 15/5/17.
 */
public class PersonInfoActivity extends BaseActivity {

    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_person_info);

        int id = getIntent().getIntExtra("id", 0);
        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());

        person = personDBUtil.find(id);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(person.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.general, PersonGeneralInfo.class)
                .add(R.string.family, PersonFamilyInfo.class)
                .add(R.string.operate, PersonOperate.class)
                .create());


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }

    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, PersonInfoActivity.class);
        intent.putExtra("id", Integer.valueOf(id));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public Person getPerson() {
        return person;
    }
}
