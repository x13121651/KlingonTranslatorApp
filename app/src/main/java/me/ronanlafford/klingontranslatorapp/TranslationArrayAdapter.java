package me.ronanlafford.klingontranslatorapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ronan on 30/11/2016.
 */
//Custom adapter class
public class TranslationArrayAdapter extends ArrayAdapter<Translation> {

    private static class ViewHolder {

        //declare the views to be used
        ImageView logo;
        TextView languageView;
        TextView messageView;
        TextView responseView;
    }

    public TranslationArrayAdapter(Context context, List<Translation> translations) {
        super(context, -1, translations);
    }

    // creates the custom views for the ListView's items
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get translation object for this specified ListView position
        Translation tn = getItem(position);
        // view that references list items views
        ViewHolder viewHolder;

        // check for a reusable ViewHolder from a ListView item that scrolled offscreen
        if (convertView == null) {
            // If there is no reusable ViewHolder then create one
            viewHolder = new ViewHolder();

            // inflate the list item layout to the view holder and assign the component values to the list item
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView =
                    inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.logo =
                    (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.languageView =
                    (TextView) convertView.findViewById(R.id.textView9);
            viewHolder.messageView =
                    (TextView) convertView.findViewById(R.id.textView11);
            viewHolder.responseView =
                    (TextView) convertView.findViewById(R.id.textView10);

            convertView.setTag(viewHolder);
        } else {
            // reuse existing ViewHolder stored as the list item's tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //create the context for the viewHolders to know what to do
        Context context = getContext();
        // for loading String resources
        viewHolder.logo.setImageResource(R.mipmap.ic_launcher);
        viewHolder.languageView.setText(tn.getLanguage());
        viewHolder.messageView.setText(tn.getMessageRequest());
        viewHolder.responseView.setText(tn.getTranslatedResponse());

        // return completed list item to display
        return convertView;
    }


}
