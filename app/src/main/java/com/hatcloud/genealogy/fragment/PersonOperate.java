package com.hatcloud.genealogy.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hatcloud.genealogy.R;
import com.hatcloud.genealogy.activity.AllInfoActivity;
import com.hatcloud.genealogy.activity.PersonInfoActivity;
import com.hatcloud.genealogy.db.PersonDBUtil;
import com.hatcloud.genealogy.model.Person;
import com.hatcloud.genealogy.util.MyApplication;

/**
 * Created by Jeff on 15/5/17.
 */
public class PersonOperate extends Fragment {

    Button btnDelete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_person_operate, container, false);

        final Person person = ((PersonInfoActivity) getActivity()).getPerson();
        final PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());

        btnDelete = (Button) view.findViewById(R.id.btn_delete_person);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personDBUtil.delete(person.getId());
                Intent intent = new Intent(getActivity().getBaseContext(), AllInfoActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
