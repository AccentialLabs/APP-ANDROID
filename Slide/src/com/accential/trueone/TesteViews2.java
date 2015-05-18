package com.accential.trueone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlarmManager;
import android.app.ExpandableListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.service.OfferNewsIntentService;
import com.accential.trueone.service.SignaturesSearchIntentService;
import com.example.slide.R;

public class TesteViews2 extends ExpandableListActivity {

	private NewsOfferReceiver receiver;
	private WebView wbv;
	private TextView email;
	private TextView senha;
	private String strEmail;
	private String strSenha; 
	private ProgressBar progress;
	private List<CompaniesUser> compsUser;
	private Spinner spinnerHeader;
	private List<String> opcoes = new ArrayList<String>();

	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//setListAdapter(new ExpandableListAdapter(TesteViews2.this));

		opcoes.add("");
		opcoes.add("Dados Cadastrais");
		opcoes.add("Sair");

		spinnerHeader = (Spinner) findViewById(R.id.spinner_header);
		email = (TextView) findViewById(R.id.editEmail);
		senha = (TextView) findViewById(R.id.editSenha);
		wbv = (WebView) findViewById(R.id.webView1);
		compsUser = new ArrayList<CompaniesUser>();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.drawable.spinner_item,opcoes);

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opcoes);

		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;

		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinnerHeader.setAdapter(adapter);

		spinnerHeader.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				if(parent.getItemAtPosition(position).toString().equals("Sair")){
					sair();
				}

			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date dt1 = new Date();
		Log.i("teste", "DATA: " + String.valueOf(df.format(dt1)));

		//TESTE DO ALARME 
		alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, ExemploDeBroadcastReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

		//		Calendar calendar =  Calendar.getInstance();
		//		calendar.setTimeInMillis(System.currentTimeMillis());
		//		calendar.set(Calendar.HOUR_OF_DAY, 13);
		//		calendar.set(Calendar.MINUTE, 12);

		alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
				60 * 1000, alarmIntent);


		Timer timer2 = new Timer();
		TimerTask task2 = new TimerTask() {

			@Override
			public void run() {
				Log.i("teste", "EXECUTEI O TIMERTASK");
			}
		};

		long interval = 1000 * 60 * 1;
		//timer2.scheduleAtFixedRate(task2, interval, interval);

		SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_USER, 0);
		int frequency = settings.getInt(ConfigActvity.FREQUENCY_NOTIFICATION, 0);

		Log.i("teste", "A FREQUENCY ��: " + String.valueOf(frequency));

		//TASK BUSCADOR 
		Timer timerSearch = new Timer();
		TimerTask taskSeach	= new TimerTask() {

			@Override
			public void run() {

				SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_USER, 0);
				int frequency = settings.getInt(ConfigActvity.FREQUENCY_NOTIFICATION, 0);
				Log.i("teste", "PESQUISANDO FREQUENCIA... FREQUENCIA: " + String.valueOf(frequency));
			}
		};

		if(frequency == 1){
			Log.i("teste", "REFERENCY= 1");
		}else if(frequency == 2){
			Log.i("teste", "REFERENCY= 2");
			timerSearch.scheduleAtFixedRate(taskSeach, interval, interval);
		}else if(frequency == 3){
			Log.i("teste", "REFERENCY= 3");
		}

		/**
		 * STARTANDO SERVICE
		 */
		Log.i("TESTE", "START SERVICE NOW!!!");
		Intent msgIntent = new Intent(TesteViews2.this, OfferNewsIntentService.class);
		msgIntent.putExtra(OfferNewsIntentService.PARAM_IN_USER_ID, 285);
		Log.i("START NO SERVICE", "SERVICO STARTADO");

		startService(msgIntent);

		IntentFilter filter = new IntentFilter(NewsOfferReceiver.ACTION_RESP_NEWS_OFFERS);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new NewsOfferReceiver();
		registerReceiver(receiver, filter);


		//compsUser = CompaniesUserBO.returnObjCompaniesUser(285);
		//Log.i("teste", "TAMANHO DA LISTA: " + String.valueOf(compsUser.size()));

		//startaService();

		//newsService();

		//		try{
		//			Calendar calendar = Calendar.getInstance();
		//			Calendar calendar2 = Calendar.getInstance();
		//			calendar2.add(Calendar.DAY_OF_MONTH, -3);
		//			Log.i("teste", "Dia do Calendar: " + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		//			Log.i("teste", "Dia do Calendar2: " + String.valueOf(calendar2.get(Calendar.DAY_OF_MONTH)));
		//
		//			if(calendar.after(calendar2)){
		//				Log.i("teste","essa data �� depois!!!");
		//			}else{
		//				Log.i("teste", "essa data �� antes");
		//			}
		//
		//		}catch(Exception e){
		//
		//		}
		//
		//		OfferDAO dao = new OfferDAO();
		//		int i = dao.news();
		//		Log.i("TESTE", "RETORNO DO METODO NEWS: " + String.valueOf(i));
		//
		//		//TESTANDO DISPOSITIVO
		//		TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		//		if(manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE){
		//			//Log.i("TESTE", "�� UM TABLET");
		//			//Log.i("TESTE", "PHONE TYPE= " + String.valueOf(manager.getPhoneType()));
		//		}else{
		//			//Log.i("TESTE", "�� UM SMARTPHONE");
		//			//Log.i("TESTE", "PHONE TYPE= " + String.valueOf(manager.getPhoneType()));
		//		}
		//
		//		String type = getResources().getString(R.string.isTablet);
		//		Log.i("teste", "IS TABLET: " +  type);
	}

	public void startaService(){
		//START SERVICE

		wbv.loadUrl("https://www.baltimorecity.gov/Portals/_default/Skins/EnterpriseUI/images/icons/loading.gif");
		Intent msgIntent = new Intent(TesteViews2.this, SignaturesSearchIntentService.class);
		msgIntent.putExtra(SignaturesSearchIntentService.PARAM_IN_TITLE, "teste");
		Log.i("START NO SERVICE", "SERVICO STARTADO");

		startService(msgIntent);

		IntentFilter filter = new IntentFilter(WishResponseReceiver.ACTION_RESP);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new NewsOfferReceiver();
		registerReceiver(receiver, filter);

		wbv.setVisibility(View.GONE);
	}

	public void newsService(){

		Log.i("teste", "EXECULTANDO METODO START SERVICE");

		Intent msgIntent = new Intent(TesteViews2.this, OfferNewsIntentService.class);
		startService(msgIntent);

		IntentFilter filter = new IntentFilter(NewsOfferReceiver.ACTION_RESP_NEWS_OFFERS);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		//receiver2 = new NewsOfferReceiver();
		//registerReceiver(receiver2, filter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class WishResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP =    
				"com.mamlambo.intent.action.MESSAGE_PROCESSED";

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle parameters = intent.getExtras();
			List<CompaniesUser> comps = (List<CompaniesUser>) parameters.getSerializable(SignaturesSearchIntentService.PARAM_OUT_COMP_LIST);
			Log.i("TESTE", "TAMANHO DA LISTA DE ASSINATURAS - SERVICE: " +  String.valueOf(comps.size()));
		}
	}

	public class ExpandableListAdapter extends BaseExpandableListAdapter {
		private Context myContext;
		public ExpandableListAdapter(Context context) {
			Log.i("teste", "USANDO LISTA ASSINATURAS - TAMANHO DA LISTA: " + String.valueOf(compsUser.size()));
			myContext = context;
		}
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) myContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(
						R.layout.activity_item_signatures, null);
			}
			TextView compName = (TextView) convertView
					.findViewById(R.id.textView_companyName);

			CompaniesUser comp = compsUser.get(groupPosition);
			compName.setText(comp.getCompany().getFancy_name());

			return convertView;
		}
		@Override
		public int getChildrenCount(int groupPosition) {
			return 1;
		}
		@Override
		public Object getGroup(int groupPosition) {
			return null;
		}
		@Override
		public int getGroupCount() {
			return compsUser.size();
		}
		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) myContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(
						R.layout.activity_signatures_detalhes, null);
			}
			((TextView) convertView.findViewById(R.id.textView_companyDescription)).setText(String.valueOf(compsUser.get(groupPosition).getCompany().getDescription()));

			return convertView;
		}
		@Override
		public boolean hasStableIds() {
			return false;
		}
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	public class NewsOfferReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_NEWS_OFFERS =    
				"com.mamlambo.intent.action.MESSAGE_PROCESSED_NEWS_OFFER";

		@Override
		public void onReceive(Context context, Intent intent) {

			int qtd = intent.getIntExtra(OfferNewsIntentService.PARAM_OUT_QTD_NEWS, 0);
			Bundle params = intent.getExtras();

			Map<String, Object> myMap = (Map<String, Object>) params.getSerializable(OfferNewsIntentService.PARAM_OUT_MAP_NEWS);
			List<CompaniesInvitationsUser> invites = (List<CompaniesInvitationsUser>) myMap.get(OfferNewsIntentService.MAP_INVITES);

			Log.i("TESTE", "RECEIVER- TAMANHO DO MAPA: " +  String.valueOf(myMap.size()));
			for (CompaniesInvitationsUser invite : invites) {
				Log.i("teste","ID DO CONVITE: " + String.valueOf(invite.getId()) + "- STATUS: " + invite.getStatus() + "- PREVIEW " +
						invite.getPreview());
			}
		}
	}

	public void clean(View v){
		v.getContext().getSharedPreferences(LoginActivity.PREFS_USER, 0).edit().clear().commit();
		Log.i("teste", "REMOVE SHARED PREFERENCE");
	}

	public void sair(){
		finish();
	}


}

