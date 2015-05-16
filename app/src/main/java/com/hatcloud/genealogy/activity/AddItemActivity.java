package com.hatcloud.genealogy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.hatcloud.genealogy.R;
import com.hatcloud.genealogy.db.PersonDBUtil;
import com.hatcloud.genealogy.model.Person;
import com.hatcloud.genealogy.util.MyApplication;
import com.hatcloud.genealogy.util.StrUtil;

/**
 * Created by Jeff on 15/5/14.
 */
public class AddItemActivity extends BaseActivity {

    EditText nameEdit;
    CheckBox maleCheckBox;
    CheckBox femaleCheckBox;
    EditText birthDateEdit;
    EditText deathDateEdit;
    EditText familyIdEdit;
    EditText parentIdEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        nameEdit = (EditText) findViewById(R.id.edit_name);
        maleCheckBox = (CheckBox) findViewById(R.id.cb_male);
        femaleCheckBox = (CheckBox) findViewById(R.id.cb_female);
        birthDateEdit = (EditText) findViewById(R.id.edit_birth_date);
        deathDateEdit = (EditText) findViewById(R.id.edit_death_date);
        familyIdEdit = (EditText) findViewById(R.id.edit_family_id);
        parentIdEdit = (EditText) findViewById(R.id.edit_parent_id);
    }


    public void onClickSaveItem(View view) {
        boolean isWrong = false;
        String name = nameEdit.getText().toString();
        int sex = maleCheckBox.isChecked() ? 1 : 2;
        String birthDate = birthDateEdit.getText().toString();
        String deathDate = deathDateEdit.getText().toString();
        String strFamilyId = familyIdEdit.getText().toString();
        String strParentId = parentIdEdit.getText().toString();

        if (TextUtils.isEmpty(name) && !isWrong) {
            Toast.makeText(MyApplication.getContext(), "姓名为空或填写错误！", Toast.LENGTH_SHORT).show();
            isWrong = true;
        }

        if (!StrUtil.isSex("" + sex)) {
            Toast.makeText(MyApplication.getContext(), "性别为空！", Toast.LENGTH_SHORT).show();
            isWrong = true;
        }

        if (!StrUtil.isDate(birthDate)) {
            Toast.makeText(MyApplication.getContext(), "出生日期为空或出错", Toast.LENGTH_SHORT).show();
            isWrong = true;
        }

        if (StrUtil.isEmpty(deathDate) && !isWrong) {
            deathDate = "";
        } else if (!StrUtil.isDate(deathDate) && !isWrong ){
            Toast.makeText(MyApplication.getContext(), "死亡日期出错", Toast.LENGTH_SHORT).show();
            isWrong = true;
        }

        if (StrUtil.isEmpty(strFamilyId) && !isWrong) {
            strFamilyId = "0";
        } else if (!StrUtil.isNum(strFamilyId) && !isWrong ){
            Toast.makeText(MyApplication.getContext(), "家庭编号必须是数字", Toast.LENGTH_SHORT).show();
            isWrong = true;
        }

        if (!StrUtil.isNum(strParentId)) {
            Toast.makeText(MyApplication.getContext(), "父母编号为空或出错", Toast.LENGTH_SHORT).show();
            isWrong = true;
        }

        if (!isWrong) {

            Person person = new Person(name, sex, birthDate, deathDate
                    , Integer.valueOf(strFamilyId), Integer.valueOf(strParentId));
            PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
            personDBUtil.save(person);
            Intent i = new Intent(AddItemActivity.this, AllInfoActivity.class);
            startActivity(i);
        }

    }

    public void onCheckChangeMale(View view) {
        if (maleCheckBox.isChecked()) {
            femaleCheckBox.setChecked(false);
        }
    }

    public void onCheckChangeFeMale(View view) {
        if(femaleCheckBox.isChecked()) {
            maleCheckBox.setChecked(false);
        }
    }
}
