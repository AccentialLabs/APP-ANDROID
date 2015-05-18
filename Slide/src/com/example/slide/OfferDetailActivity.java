package com.example.slide;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.bo.OffersCommentBO;
import com.accential.trueone.utils.SessionControl;

/**
 * Página principal do Detalhe da Oferta
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class OfferDetailActivity extends FragmentActivity {

	private List<OffersComment> offersComment;
	private SharedPreferences loggedUser;
	private Offer offer;
	private int nota;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x_activity_offer_detail);

		ViewPager pager = (ViewPager) findViewById(R.id.viewPagerOfferDetail);
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

		// pegando a oferta salva na sessao
		loggedUser = getSharedPreferences(SessionControl.USER_LOGGED, 0);
		offer = SessionControl.decodeSessionOffer(loggedUser.getString(
				SessionControl.OFFER_DETAIL, null));

		// procurando comentarios da oferta
		offersComment = OffersCommentBO.searchByOffer(offer.getId());

		// nota da oferta para ser mostrada em estrela
		nota = 0;
		for (OffersComment comment : offersComment) {
			nota = nota + Integer.parseInt(comment.getEvaluation());
		}
		nota = nota / offersComment.size();

	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			switch (pos) {
			case 0:
				return OfferDetailFirst.newInstance(nota);
			case 1:
				return OfferDetailSecond.newInstance("Descrição");
			case 2:
				return OfferDetailThird.newInstance("Especif");
			case 3:
				return OfferDetailFourth.newInstance("Wishlist", offersComment);
			default:
				return OfferDetailFirst.newInstance(nota);
			}
		}

		@Override
		public int getCount() {
			return 4;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handler dos cliques em cada menu
		switch (item.getItemId()) {
		case R.id.action_settings:
			String shareBody = offer.getTitle();
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Oferta T1");
			sharingIntent
					.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent,
					"Compartilhe por..."));
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
