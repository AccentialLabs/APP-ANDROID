package com.accential.trueone.adapter;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accential.trueone.bean.OffersComment;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

/**
 * Listagem dos comentário de determinada oferta
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class OffersCommentAdapter extends BaseAdapter {

	private List<OffersComment> comments;
	private LayoutInflater inflater;

	public OffersCommentAdapter(Context context, List<OffersComment> comments) {
		this.comments = comments;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		OffersComment comment = comments.get(position);
		view = inflater.inflate(R.layout.x_offers_comment, null);

		// titulo
		((TextView) view.findViewById(R.id.tvCommentTitle)).setText(comment
				.getTitle());
		// usuario
		((TextView) view.findViewById(R.id.tvCommentAutor)).setText(comment
				.getUser().getName());

		SmartImageView img = (SmartImageView) view
				.findViewById(R.id.ivCommentUserPhoto);
		img.setImageUrl(comment.getUser().getPhoto());

		int mes = comment.getDateRegister().get(Calendar.MONTH);

		// data
		String rDate = String.valueOf(comment.getDateRegister().get(
				Calendar.DAY_OF_MONTH)
				+ "/"
				+ String.valueOf(mes + 1)
				+ "/"
				+ comment.getDateRegister().get(Calendar.YEAR));
		((TextView) view.findViewById(R.id.tvCommentDate)).setText(rDate);

		// declaramos o Tv da descricao, pois testaremos o uso de uma fonte
		// exterior
		TextView tvDesc = (TextView) view.findViewById(R.id.tvCommentDesc);
		tvDesc.setText(comment.getDescricao());
		Typeface font = Typeface.createFromAsset(parent.getContext()
				.getAssets(), "helvetica-normal.ttf");
		tvDesc.setTypeface(font);

		return view;
	}
}
