package com.example.slide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.accential.trueone.utils.SessionControl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.Toast;

@SuppressWarnings("all")
public class OffersDetailActivity extends FragmentActivity implements
		TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

	private ImageView imgMenu;
	private View includeMenu;
	private TabHost mTabHost;
	private ViewPager mViewPager;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, OffersDetailActivity.TabInfo>();
	private PagerAdapter mPagerAdapter;

	// Informação da Tab
	private class TabInfo {
		private String tag;
		private Class<?> clss;
		private Bundle args;
		private Fragment fragment;

		TabInfo(String tag, Class<?> clazz, Bundle args) {
			this.tag = tag;
			this.clss = clazz;
			this.args = args;
		}
	}

	// Um simples factory que retorna View para o TabHost
	class TabFactory implements TabContentFactory {

		private final Context mContext;

		public TabFactory(Context context) {
			mContext = context;
		}

		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Infla o layout
		setContentView(R.layout.x_offers_main);
		// Inicializa o TabHost
		imgMenu = (ImageView) findViewById(R.id.imageView3);
		includeMenu = (View) findViewById(R.id.include2);
		imgMenu.setVisibility(View.GONE);

		this.initialiseTabHost(savedInstanceState);
		if (savedInstanceState != null) {
			// Define a Tab de acordo com o estado salvo
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}

		// Inicializa o ViewPager
		this.intialiseViewPager();
	}

	protected void onSaveInstanceState(Bundle outState) {
		// salva a Tab selecionada
		outState.putString("tab", mTabHost.getCurrentTabTag());
		super.onSaveInstanceState(outState);
	}

	private void intialiseViewPager() {

		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(Fragment.instantiate(this,
				OffersFragFirst.class.getName()));
		fragments.add(Fragment.instantiate(this,
				OffersFragSecond.class.getName()));
		fragments.add(Fragment.instantiate(this,
				OffersFragThird.class.getName()));
		fragments.add(Fragment.instantiate(this,
				OffersFragFourth.class.getName()));
		this.mPagerAdapter = new ViewPagerAdapter(
				super.getSupportFragmentManager(), fragments);
		this.mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
		this.mViewPager.setAdapter(this.mPagerAdapter);
		this.mViewPager.setOnPageChangeListener(this);
	}

	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		TabInfo tabInfo = null;
		OffersDetailActivity.AddTab(this, this.mTabHost, this.mTabHost
				.newTabSpec("Tab1").setIndicator("PROD."),
				(tabInfo = new TabInfo("Tab1", OffersFragFirst.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		OffersDetailActivity.AddTab(this, this.mTabHost, this.mTabHost
				.newTabSpec("Tab2").setIndicator("DESC."),
				(tabInfo = new TabInfo("Tab2", OffersFragSecond.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		OffersDetailActivity.AddTab(this, this.mTabHost, this.mTabHost
				.newTabSpec("Tab3").setIndicator("ESPEC."),
				(tabInfo = new TabInfo("Tab3", OffersFragThird.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		OffersDetailActivity.AddTab(this, this.mTabHost, this.mTabHost
				.newTabSpec("Tab4").setIndicator("OPIN."),
				(tabInfo = new TabInfo("Tab4", OffersFragFourth.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		mTabHost.setOnTabChangedListener(this);
	}

	private static void AddTab(OffersDetailActivity activity, TabHost tabHost,
			TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		// Attach uma Tab view factory para o spec
		tabSpec.setContent(activity.new TabFactory(activity));
		tabHost.addTab(tabSpec);

	}

	public void onTabChanged(String tag) {
		// Avisa para o mViewPager qual a Tab que está ativa
		int pos = this.mTabHost.getCurrentTab();
		this.mViewPager.setCurrentItem(pos);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		this.mTabHost.setCurrentTab(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	public void onBackPressed() {

		Log.e("", "CLIQUEI VOLTAR!!! VOLTAR - VOLTAR - VOLTAR ");

		SharedPreferences loggedUser = OffersDetailActivity.this
				.getSharedPreferences(SessionControl.USER_LOGGED, 0);
		SharedPreferences.Editor editor = loggedUser.edit();
		editor.remove(SessionControl.OFFER_DETAILS_COMPANY);
		editor.commit();

		// Intent intent = new Intent(OffersDetailActivity.this,
		// AdventaActivity.class);
		// startActivity(intent);

		Intent intent = new Intent(OffersDetailActivity.this,
				AdvetaInitialActivity.class);
		startActivity(intent);

		return;
	}

}