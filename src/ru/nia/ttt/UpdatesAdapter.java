package ru.nia.ttt;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class UpdatesAdapter extends ArrayAdapter<Update> {
    int mResource;
    DecimalFormat mHoursFormat;

    public UpdatesAdapter(Context context, int resource, List<Update> objects, DecimalFormat hoursFormat) {
        super(context, resource, objects);
        mResource = resource;
        mHoursFormat = hoursFormat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get object to display
        Update update = getItem(position);

        // a view to work with
        LinearLayout updateView;

        // inflate view
        if(null == convertView) {
            updateView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(mResource, updateView, true);
        } else {
            updateView = (LinearLayout) convertView;
        }

        // find TextViews to write in
        TextView userName = (TextView)updateView.findViewById(R.id.user_name);
        TextView startedAt = (TextView)updateView.findViewById(R.id.started_at);
        TextView hours = (TextView)updateView.findViewById(R.id.hours);
        TextView message = (TextView)updateView.findViewById(R.id.message);

        // write
        userName.setText(getContext().getString(R.string.user_prefix) + update.getUser());

        String startedAtString = DateFormat.getTimeFormat(getContext()).format(update.getStartedAt().getTime());
        startedAt.setText(startedAtString);

        Double hoursValue = update.getHours();
        String hoursString;
        if(null == hoursValue) {
            hoursString = "?";
        } else {
            hoursString = mHoursFormat.format(update.getHours()) + getContext().getString(R.string.hours);
        }
        hours.setText(hoursString);

        message.setText(update.getMessage());

        return updateView;
    }
}
