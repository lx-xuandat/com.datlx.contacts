package com.datlx.contacts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.datlx.contacts.R;
import com.datlx.contacts.model.Contact;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private final Context context;
    private final List<Contact> arrayContact;

    public ContactAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayContact = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_contact, parent, false);
            viewHolder.imgAvatar = convertView.findViewById(R.id.img_avatar);
            viewHolder.tvName = convertView.findViewById(R.id.txt_name);
            viewHolder.tvNumber = convertView.findViewById(R.id.txt_phone);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Contact contact = arrayContact.get(position);

        viewHolder.tvName.setText(contact.getContactName());
        viewHolder.tvNumber.setText(contact.getPhone());

        if (contact.getAvatar() != null) {
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.ic_male);
        } else {
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.ic_female);
        }

        return convertView;
    }

    public static class ViewHolder {
        ImageView imgAvatar;
        TextView tvName;
        TextView tvNumber;
    }
}
