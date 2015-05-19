package com.hatcloud.genealogy.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hatcloud.genealogy.R;
import com.hatcloud.genealogy.activity.PersonInfoActivity;
import com.hatcloud.genealogy.model.Person;

/**
 * Created by Jeff on 15/5/17.
 */
public class PersonGeneralInfo extends Fragment{

    private View view;
    private TextView tvPersonName;
    private TextView tvPersonSex;
    private TextView tvPersonAge;
    private TextView tvBirthToDeath;
    private TextView tvPersonLife;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Person person = ((PersonInfoActivity) getActivity()).getPerson();

        view = inflater.inflate(R.layout.frag_person_general_info, container, false);
        tvPersonName = (TextView) view.findViewById(R.id.person_name);
        tvPersonSex = (TextView) view.findViewById(R.id.person_sex);
        tvPersonAge = (TextView) view.findViewById(R.id.person_age);
        tvBirthToDeath = (TextView) view.findViewById(R.id.person_birth_to_death);
        tvPersonLife = (TextView)view.findViewById(R.id.person_life);

        tvPersonName.setText(person.getName());
        tvPersonSex.setText(person.getSexStr());
        tvPersonAge.setText("" + person.getAge());
        tvBirthToDeath.setText(person.getBirthDate() + " 至 " + person.getDeathDate());

        return view;
    }

}
