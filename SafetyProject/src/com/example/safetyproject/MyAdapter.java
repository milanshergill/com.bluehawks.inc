package com.example.safetyproject;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Model> {

	private final Context context;
	private final ArrayList<Model> modelsArrayList;

	public MyAdapter(Context context, ArrayList<Model> modelsArrayList) {

		super(context, R.layout.drawer_list_row, modelsArrayList);

		this.context = context;
		this.modelsArrayList = modelsArrayList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// 1. Create inflater
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// 2. Get rowView from inflater
		View rowView = null;
		if (!modelsArrayList.get(position).isGroupHeader()) {
			rowView = inflater.inflate(R.layout.drawer_list_row, parent, false);

			// 3. Get icon, title & info views from the rowView
			ImageView imgView = (ImageView) rowView
					.findViewById(R.id.icon_image);
			TextView titleView = (TextView) rowView
					.findViewById(R.id.feature_name);
			TextView infoView = (TextView) rowView
					.findViewById(R.id.feature_info);

			// 4. Set the text for textView
			imgView.setImageResource(modelsArrayList.get(position).getIcon());
			titleView.setText(modelsArrayList.get(position).getTitle());
			infoView.setText(modelsArrayList.get(position).getInfo());
		} else {
			rowView = inflater.inflate(R.layout.drawer_list_header, parent,
					false);
			TextView titleView = (TextView) rowView.findViewById(R.id.header);
			titleView.setText(modelsArrayList.get(position).getTitle());

		}

		// 5. return rowView
		return rowView;
	}
}