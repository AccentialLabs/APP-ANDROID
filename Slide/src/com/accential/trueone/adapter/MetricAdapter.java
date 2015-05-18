package com.accential.trueone.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.slide.R;

public class MetricAdapter extends BaseAdapter {

	private List<String> metrics;
	private LayoutInflater inflater;
	private int selected = -1;

	public MetricAdapter(Context context, List<String> metrics) {
		this.metrics = metrics;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return metrics.size();
	}

	@Override
	public Object getItem(int location) {
		return metrics.get(location);
	}

	@Override
	public long getItemId(int location) {
		return location;
	}

	public void select(int position) {
		this.selected = position;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int location, View view, ViewGroup parent) {

		String metric = metrics.get(location);

		view = inflater.inflate(R.layout.x_activity_metric_item, null);

		TextView tv = (TextView) view.findViewById(R.id.tvMetric);
		if (metric.contains("#")) {
			tv.setText("");
			tv.setBackgroundColor(Color.parseColor(metric));
		} else {
			tv.setText(metric);
		}

		if (selected != -1 && location == selected) {
			tv.setBackgroundColor(Color.parseColor("#3399cc"));
			tv.setTextColor(Color.WHITE);
		}

		return view;
	}
}
