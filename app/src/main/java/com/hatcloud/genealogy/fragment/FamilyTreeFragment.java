package com.hatcloud.genealogy.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hatcloud.genealogy.R;
import com.hatcloud.genealogy.activity.FamilyTreeActivity;
import com.hatcloud.genealogy.db.PersonDBUtil;
import com.hatcloud.genealogy.holder.FamilyTreeItemHolder;
import com.hatcloud.genealogy.model.Person;
import com.hatcloud.genealogy.util.MyApplication;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.List;

/**
 * Created by Jeff on 15/5/27.
 */
public class FamilyTreeFragment extends Fragment{
    private AndroidTreeView tView;
    PersonDBUtil personDBUtil;
    TreeNode tempNode;
    int tempID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_family_tree, null, false);
        ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);
        Button btnUpOneLevel = (Button) rootView.findViewById(R.id.btn_up_one_level);
        personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        tempID = ((FamilyTreeActivity) getActivity()).getTempId();


        TreeNode root = TreeNode.root();

        TreeNode rootPersonNode;
        final List<Person>  rootPeople = ((FamilyTreeActivity) getActivity()).getRootPeople();

        btnUpOneLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rootPersonId =rootPeople.get(0).getId();
                for (Person p : personDBUtil.cursorToPeople(personDBUtil.getRootPeople())) {
                    if (rootPersonId == p.getId()) {
                        rootPersonId = 0;
                        break;
                    }
                }
                if (rootPersonId != 0) {
                    FamilyTreeActivity.actionStart(getActivity(), rootPeople.get(0).getFatherId(), 0);
                }
            }
        });

        for (Person p : rootPeople) {
            rootPersonNode = new TreeNode(new FamilyTreeItemHolder.IconTreeItem(
                    R.string.ic_person,
                    p.getNodeName() +
                            "[第" + p.getFamilyHierarchyPosition() + "代]", p.getId()));

            traversalTree(rootPersonNode, p);
            root.addChild(rootPersonNode);
        }


        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(FamilyTreeItemHolder.class);
        tView.setDefaultNodeClickListener(nodeClickListener);

        if (tempID != 0) {
            while (tempNode.getParent() != root) {
                tempNode = tempNode.getParent();
                tView.expandNode(tempNode);
            }
        } else {
            tView.expandLevel(2);
        }

        containerView.addView(tView.getView());

        return rootView;
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            if (node.getLevel() >= 5 && node.getChildren().size() > 0) {
                FamilyTreeItemHolder.IconTreeItem item = (FamilyTreeItemHolder.IconTreeItem) value;
                node.setExpanded(true);
                FamilyTreeActivity.actionStart(getActivity(), item.id, 0);
            }
        }
    };

    void traversalTree(TreeNode rootNode, Person rootPerson) {
        if (personDBUtil.getChildren(rootPerson).moveToNext()) {
            for (Person p : personDBUtil.cursorToPeople(personDBUtil.getChildren(rootPerson))) {
                TreeNode node = new TreeNode(new FamilyTreeItemHolder.IconTreeItem(
                        R.string.ic_person,
                        p.getNodeName() +
                        "[第" + rootPerson.getFamilyHierarchyPosition() + "代]", p.getId()));
                rootNode.addChild(node);
                traversalTree(node, p);
                if (p.getId() == tempID) {
                    tempNode = node;
                }
            }
        }
    }

}
