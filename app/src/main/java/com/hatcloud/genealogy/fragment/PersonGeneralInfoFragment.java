package com.hatcloud.genealogy.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatcloud.genealogy.R;
import com.hatcloud.genealogy.activity.PersonInfoActivity;
import com.hatcloud.genealogy.model.Person;
import com.hatcloud.genealogy.util.StrUtil;

/**
 * Created by Jeff on 15/5/17.
 */
public class PersonGeneralInfoFragment extends Fragment{

    private View view;
    private TextView tvPersonName;
    private TextView tvPersonSex;
    private TextView tvPersonUsedName;
    private TextView tvPersonStyleName;
    private TextView tvPersonHaoName;
    private TextView tvPersonFamilyHierarchyPosition;
    private TextView tvPersonFamilyOrder;
    private TextView tvPersonBirthDate;
    private TextView tvPersonDeathDate;
    private TextView tvPersonAgeTitle;
    private TextView tvPersonAge;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Person person = ((PersonInfoActivity) getActivity()).getPerson();

        view = inflater.inflate(R.layout.frag_person_general_info, container, false);
        tvPersonName = (TextView) view.findViewById(R.id.person_name);
        tvPersonSex = (TextView) view.findViewById(R.id.person_sex);
        tvPersonUsedName = (TextView) view.findViewById(R.id.person_used_name);
        tvPersonStyleName = (TextView) view.findViewById(R.id.person_style_name);
        tvPersonHaoName = (TextView) view.findViewById(R.id.person_hao_name);
        tvPersonFamilyHierarchyPosition = (TextView) view.findViewById(R.id.person_family_hierarchy_position);
        tvPersonFamilyOrder = (TextView) view.findViewById(R.id.person_family_order);
        tvPersonBirthDate = (TextView) view.findViewById(R.id.person_birth_date);
        tvPersonDeathDate = (TextView) view.findViewById(R.id.person_death_date);
        tvPersonAgeTitle = (TextView) view.findViewById(R.id.person_age_title);
        tvPersonAge = (TextView) view.findViewById(R.id.person_age);

        tvPersonName.setText(person.getName());
        tvPersonSex.setText(person.getSexStr());
        if (StrUtil.isEmpty(person.getUsedName())) {
            (view.findViewById(R.id.layout_used_name)).setVisibility(View.GONE);
        } else {
            tvPersonUsedName.setText(person.getUsedName());
        }

        if (StrUtil.isEmpty((person.getStyleName()))) {
            (view.findViewById(R.id.layout_style_name)).setVisibility(View.GONE);
        } else {
            tvPersonStyleName.setText(person.getStyleName());
        }

        if (StrUtil.isEmpty(person.getHaoName())) {
            (view.findViewById(R.id.layout_hao_name)).setVisibility(View.GONE);
        } else {
            tvPersonHaoName.setText(person.getHaoName());
        }

        if (person.getFamilyHierarchyPosition() == 0) {
            (view.findViewById(R.id.layout_person_family_position)).setVisibility(View.GONE);
        } else {
            tvPersonFamilyHierarchyPosition.setText(person.getFamilyHierarchyPosition() + "");
        }

        tvPersonBirthDate.setText(person.getBirthDate());
        tvPersonDeathDate.setText(person.getDeathDate());

        if (!StrUtil.isEmpty(person.getDeathDate())) {
            tvPersonAgeTitle.setText(getString(R.string.death_age));
        }
        tvPersonAge.setText(person.getAge() + "");

        return view;
    }

}
