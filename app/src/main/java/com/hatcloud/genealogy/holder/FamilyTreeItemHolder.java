package com.hatcloud.genealogy.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.hatcloud.genealogy.R;
import com.hatcloud.genealogy.activity.PersonInfoActivity;
import com.hatcloud.genealogy.db.PersonDBUtil;
import com.hatcloud.genealogy.util.MyApplication;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Jeff on 15/5/27.
 */
public class FamilyTreeItemHolder extends TreeNode.BaseNodeViewHolder<FamilyTreeItemHolder.IconTreeItem>{

    private TextView tvValue;
    private PrintView arrowView;

    public FamilyTreeItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, final IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.node_person, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.text);

        //final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
        //iconView.setIconText(context.getResources().getString(value.icon));

        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);

        PersonDBUtil personDBUtil = PersonDBUtil.getIntance(MyApplication.getContext());
        if(!personDBUtil.getChildren(personDBUtil.find(value.id)).moveToNext()){
            view.findViewById(R.id.arrow_icon).setVisibility(View.INVISIBLE);
        }

        if (node.isFirstChild()) {
            view.findViewById(R.id.top_line).setVisibility(View.INVISIBLE);
        }
        if(node.isLastChild() || node.isExpanded()){
            view.findViewById(R.id.bot_line).setVisibility(View.INVISIBLE);
        }

        view.findViewById(R.id.btn_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String personid = String.valueOf(value.id);
                PersonInfoActivity.actionStart(context, personid);
            }
        });

        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static class IconTreeItem {
        public int icon;
        public String text;
        public int id;

        public IconTreeItem(int icon, String text, int id) {
            this.icon = icon;
            this.text = text;
            this.id = id;
        }
    }
}
