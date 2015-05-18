package com.accential.trueone.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.utils.DownloadImagemUtil;

@SuppressWarnings("all")
public class OfferAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private List<Offer> offers;
	private final Activity context;
	private DownloadImagemUtil downloader;
	
	
	public OfferAdapter(LayoutInflater inflater, List<Offer> offers,
			Activity context, DownloadImagemUtil downloader) {
		
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.offers = offers;
		this.context = context;
		this.downloader = new DownloadImagemUtil(context);
	}

	@Override
	public int getCount() {		
		return offers != null ? offers.size() : 0;
	}

	@Override
	public Object getItem(int position) {		
		return offers != null ? offers.get(position) : null;
	}

	@Override
	public long getItemId(int position) {	
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		return null;
	}
	
	static class ViewHolder{
		ImageView offerImage;
		ImageView companyImage;
		ImageView plusIcon;
		ImageView buyIcon;
		ImageView novoIcon;	
		ImageView image;
		
	}

}
