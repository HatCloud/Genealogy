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
import com.hatcloud.genealogy.db.PersonDBUtil;
import com.hatcloud.genealogy.model.Person;
import com.hatcloud.genealogy.util.MyApplication;

/**
 * Created by Jeff on 15/5/17.
 */
public class PersonLifeInfoFragment extends Fragment {

    TextView tvLifeInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_person_life_info, container, false);

        tvLifeInfo = (TextView) view.findViewById(R.id.person_life_info);
        final Person person = ((PersonInfoActivity) getActivity()).getPerson();
        tvLifeInfo.setText(person.getLifeInfo());

        return view;
    }
}
