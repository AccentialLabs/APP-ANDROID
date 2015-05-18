package com.example.slide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.OfferBO;
import com.accential.trueone.utils.SessionControl;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {

	private List<Offer> offers;
	private SharedPreferences loggedUser;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// recuperando usuario logado na "sessao"
		loggedUser = getSharedPreferences(SessionControl.USER_LOGGED, 0);
		user = SessionControl.decodeSessionUser(loggedUser.getString("user",
				null));

		// mostra toast de boas vindas ao usuario
		Toast.makeText(this, "Bem-vindo " + user.getName(), Toast.LENGTH_SHORT)
				.show();

		ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
		offers = new ArrayList<Offer>();

		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

		// aqui vem o processo de busca das ofertas
		Map<String, Map<String, Map<String, String>>> key = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, Map<String, String>> paramss = new HashMap<String, Map<String, String>>();
		Map<String, String> conditions = new HashMap<String, String>();

		conditions.put("Offer.ends_at >", "2014-10-20");
		conditions.put("Offer.status", "ACTIVE");
		conditions.put("Offer.public", "ACTIVE");
		paramss.put("conditions", conditions);
		key.put("Offer", paramss);

		offers = OfferBO.listAllOffers(key);
		Log.e("main", "executa main");

	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			switch (pos) {
			case 0:
				return FirstFragment.newInstance("Vitrine", offers, user);
			case 1:
				return SecondFragment.newInstance("Minhas Compras");
			case 2:
				return ThirdFragment.newInstance("Assinaturas");
			case 3:
				return FourthFragment.newInstance("Wishlist");
			default:
				return FirstFragment.newInstance("Vitrine", offers, user);
			}
		}

		@Override
		public int getCount() {
			return 4;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Carrega o arquivo de menu.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_users_settings:
			starIntentUser();
			return true;
		case R.id.action_settings:
			SessionControl.shareApp(MainActivity.this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void starIntentUser() {
		Intent intent = new Intent(MainActivity.this, UserActivity.class);
		startActivity(intent);
	}

}
