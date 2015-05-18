package com.example.slide;

import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.accential.trueone.adapter.OffersCommentAdapter;
import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.utils.SessionControl;

/**
 * Comentários
 * 
 * @version 0.1
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class OfferDetailFourth extends Fragment {

	private View v;
	private SharedPreferences loggedUser;
	private static List<OffersComment> comments;
	private ListView lvOffersComment;
	private OffersCommentAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.x_offers_details_fouth, container, false);

		lvOffersComment = (ListView) v.findViewById(R.id.lvOffersComment);
		adapter = new OffersCommentAdapter(v.getContext(), comments);
		lvOffersComment.setAdapter(adapter);

		loggedUser = v.getContext().getSharedPreferences(
				SessionControl.USER_LOGGED, 0);

		return v;
	}

	public static OfferDetailFourth newInstance(String text,
			List<OffersComment> offersComments) {

		// recebemos a lista como paramentro da view principal
		comments = offersComments;

		OfferDetailFourth f = new OfferDetailFourth();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}
}
