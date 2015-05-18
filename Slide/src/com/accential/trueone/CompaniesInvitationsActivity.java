package com.accential.trueone;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.InvitationAdapter;
import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bean.User;
import com.accential.trueone.service.CompaniesInvitationsService;
import com.accential.trueone.service.InvitationsService;
import com.example.slide.R;

/**
 * In usum
 * Gerencia views de convite de companias para usuarios
 * @author Matheus Odilon - accentialbrasil
 * @version 1.0
 *
 */
public class CompaniesInvitationsActivity extends Activity {

	private InvitationAdapter adapter;
	private ListView listInvitations;
	private List<CompaniesInvitationsUser> invitations;
	private CompaniesInvitationsResponseReceiver receiver;
	private ActOrAnactInvitationsResponseReceiver receiver2;
	private TextView invitesNotFound;
	private User user;
	private boolean logado = true;
	private TextView textViewNameUser;
	private TextView qtdAvisoNovidade;
	private int qtdAvisoTotal;

	private ImageView imageMenuWish;
	private TextView textMenuWish;
	private ImageView imageMenuCompras;
	private TextView textMenuCompras;
	private ImageView imageMenuAssinaturas;
	private TextView textMenuAssinaturas;
	private ImageView imageMenuDash;
	private TextView textMenuDash;
	private TextView textMenuOferta;
	private ImageView imageMenuOferta;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_invitation);	

		listInvitations = (ListView) findViewById(R.id.listView_invitations);
		invitesNotFound = (TextView) findViewById(R.id.textView_invitesNotFound);
		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);
		qtdAvisoNovidade = (TextView) findViewById(R.id.textView_qtdConvites);

		imageMenuWish = (ImageView) findViewById(R.id.imageFooterWish);
		textMenuWish = (TextView) findViewById(R.id.textViewFooterWish);
		imageMenuCompras = (ImageView) findViewById(R.id.imageFooterCompras);
		textMenuCompras = (TextView) findViewById(R.id.textViewFooterCompras);
		imageMenuAssinaturas = (ImageView) findViewById(R.id.imageFooterAssinaturas);
		textMenuAssinaturas = (TextView) findViewById(R.id.textViewFooterAssinaturas);
		imageMenuDash = (ImageView) findViewById(R.id.imageFooterInvitations);
		textMenuDash = (TextView) findViewById(R.id.textViewFooterInvitations);
		imageMenuOferta = (ImageView) findViewById(R.id.imageFooterDashboard);
		textMenuOferta = (TextView) findViewById(R.id.textViewFooterDash);

		invitesNotFound.setVisibility(View.GONE);

		Intent intentMain = getIntent();
		Bundle parameters = intentMain.getExtras();

		qtdAvisoTotal = intentMain.getIntExtra(MainActivity.QTQ_NOVA, 0);
		qtdAvisoNovidade.setText(String.valueOf(qtdAvisoTotal));
		user = (User) getIntent().getSerializableExtra("usuario");

		textViewNameUser.setText(user.getName());

		//START SERVICE...
		Intent msgIntent = new Intent(CompaniesInvitationsActivity.this, CompaniesInvitationsService.class);
		msgIntent.putExtra(CompaniesInvitationsService.PARAM_IN_USER_ID, 285);
		Log.i("START NO SERVICE", "SERVICO STARTADO");
		startService(msgIntent);

		IntentFilter filter = new IntentFilter(CompaniesInvitationsResponseReceiver.ACTION_RESP_COMPS_INVITATION);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new CompaniesInvitationsResponseReceiver();
		registerReceiver(receiver, filter);


		listInvitations.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(CompaniesInvitationsActivity.this);
				builder1.setTitle(invitations.get(position).getCompany().getFancy_name());
				builder1.setMessage(invitations.get(position).getCompany().getDescription() + "\n" + "\n" +
						invitations.get(position).getCompany().getEmail());
				builder1.setCancelable(true);
				builder1.setPositiveButton("Voltar",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
				AlertDialog alert11 = builder1.create();
				alert11.show();
			}
		});


		//SELECIONA ITENS DO MENU
		// 1 - WISHLIST
		textMenuWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//AO SELECIONAR A OP����O WISHLIST NO MENU A ACTIVITY CARREGA O OBJ USUARIO E O ATRIBIUTO 'LOGADO'
				//PARA TRANSFERIR VIA INTENT
				imageMenuWish.setImageResource(R.drawable.wishlist_on);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuDash.setImageResource(R.drawable.dashboard_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_off);

				textMenuWish.setTextColor(Color.rgb(255, 117, 24));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));
				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(105, 105, 105));
				//MUDEI AQUI - WishlistActivity
				Intent intent = new Intent(v.getContext(), WishlistLand.class);
				Bundle parameters = new Bundle();

				intent.putExtra(MainActivity.QTQ_NOVA, qtdAvisoTotal);
				intent.putExtra("user", user);
				parameters.putBoolean("logado", logado);

				intent.putExtras(parameters);
				startActivity(intent);
			}
		});

		//SELECIONA ITENS DO MENU
		//2 - COMPRAS
		textMenuCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//AO SELECIONAR A OP����O COMPRAS DO MENU A ACTIVITY CARREGA O OBJ USUARIO 
				//PARA TRANSFERIR VIA INTENT
				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_on);
				imageMenuDash.setImageResource(R.drawable.dashboard_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_off);

				textMenuCompras.setTextColor(Color.rgb(255, 117, 24));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));
				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(105, 105, 105));

				Intent intent = new Intent(v.getContext(), ComprasActivity.class);

				intent.putExtra(MainActivity.QTQ_NOVA, qtdAvisoTotal);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});

		//SELECIONA ITENS DO MENU
		//3 - ASSINATURAS
		textMenuAssinaturas.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_on);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuDash.setImageResource(R.drawable.dashboard_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_off);

				textMenuAssinaturas.setTextColor(Color.rgb(255, 117, 24));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(105, 105, 105));
				//MUDEI AQUI - CompaniesUserActivity

				Intent intentC = new Intent(v.getContext(), SignaturesActivity.class);
				intentC.putExtra(MainActivity.QTQ_NOVA, qtdAvisoTotal);
				intentC.putExtra("user", user);
				startActivity(intentC);
			}
		});

		//SELECIONA ITENS DO MENU
		//4 - DASHBOARD
		textMenuDash.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuDash.setImageResource(R.drawable.dashboard_on);

				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(255, 117, 24));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));

				Intent intentD = new Intent(v.getContext(), MainActivity.class);
				Bundle bundle = new  Bundle();
				bundle.putBoolean("logado", true);
				intentD.putExtras(bundle);
				intentD.putExtra("usuario", user);
				startActivity(intentD);
			}
		});

		//SELECIONA ITENS DO MENU
		//5 - OFERTA
		textMenuOferta.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				imageMenuWish.setImageResource(R.drawable.wishlist_off);
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				imageMenuCompras.setImageResource(R.drawable.compras_off);
				imageMenuDash.setImageResource(R.drawable.dashboard_off);
				imageMenuOferta.setImageResource(R.drawable.ofertas_on);

				textMenuAssinaturas.setTextColor(Color.rgb(105, 105, 105));
				textMenuWish.setTextColor(Color.rgb(105, 105, 105));
				textMenuDash.setTextColor(Color.rgb(105, 105, 105));
				textMenuCompras.setTextColor(Color.rgb(105, 105, 105));
				textMenuOferta.setTextColor(Color.rgb(255, 117, 24));

				Intent intentD = new Intent(v.getContext(), MainActivity.class);
				Bundle bundle = new  Bundle();
				bundle.putBoolean("logado", true);
				intentD.putExtras(bundle);
				intentD.putExtra("usuario", user);
				startActivity(intentD);
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Quando h�� clique no bot��o "add" de algum item da lista
	 * @param v
	 */
	public void clickPositive(View v){
		int position = (Integer) v.getTag();

		Log.i("teste", "CLIQUE NO BOTAO POSITIVO");

		Intent msgIntent2 = new Intent(CompaniesInvitationsActivity.this, InvitationsService.class);
		msgIntent2.putExtra(InvitationsService.PARAM_IN_ACTIVE_INACTIVE, 0);

		msgIntent2.putExtra(InvitationsService.PARAM_IN_INVITATION_ID, invitations.get(position).getId());
		Log.i("START NO SERVICE", "SERVICO STARTADO");
		startService(msgIntent2);

		IntentFilter filter2 = new IntentFilter(ActOrAnactInvitationsResponseReceiver.ACTION_RESP_ACTIVE_INVITATION);
		filter2.addCategory(Intent.CATEGORY_DEFAULT);
		receiver2 = new ActOrAnactInvitationsResponseReceiver();
		registerReceiver(receiver, filter2);

		//----------------------------------

		//SERVICE QUE EXECUTA LISTA...
		Intent msgIntent = new Intent(CompaniesInvitationsActivity.this, CompaniesInvitationsService.class);
		msgIntent.putExtra(CompaniesInvitationsService.PARAM_IN_USER_ID, 285);
		Log.i("START NO SERVICE", "SERVICO STARTADO");
		startService(msgIntent);

		IntentFilter filter = new IntentFilter(CompaniesInvitationsResponseReceiver.ACTION_RESP_COMPS_INVITATION);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new CompaniesInvitationsResponseReceiver();
		registerReceiver(receiver, filter);

		Toast.makeText(this, "Empresa assinada!", Toast.LENGTH_SHORT).show();

	}

	/**
	 * Quando h�� clique no bot��o "cancel" de algum item da lista
	 * @param v
	 */
	public void clickNegative(View v){

		Log.i("teste", "CLIQUE NO BOTAO NEGATIVO");

		int position = (Integer) v.getTag();

		Intent msgIntent2 = new Intent(CompaniesInvitationsActivity.this, InvitationsService.class);
		msgIntent2.putExtra(InvitationsService.PARAM_IN_ACTIVE_INACTIVE, 1);

		msgIntent2.putExtra(InvitationsService.PARAM_IN_INVITATION_ID, invitations.get(position).getId());
		Log.i("START NO SERVICE", "SERVICO STARTADO");
		startService(msgIntent2);

		IntentFilter filter2 = new IntentFilter(ActOrAnactInvitationsResponseReceiver.ACTION_RESP_ACTIVE_INVITATION);
		filter2.addCategory(Intent.CATEGORY_DEFAULT);
		receiver2 = new ActOrAnactInvitationsResponseReceiver();
		registerReceiver(receiver, filter2);

		//----------------------------------

		//SERVICE QUE EXECUTA LISTA...
		Intent msgIntent = new Intent(CompaniesInvitationsActivity.this, CompaniesInvitationsService.class);
		msgIntent.putExtra(CompaniesInvitationsService.PARAM_IN_USER_ID, 285);
		Log.i("START NO SERVICE", "SERVICO STARTADO");
		startService(msgIntent);

		IntentFilter filter = new IntentFilter(CompaniesInvitationsResponseReceiver.ACTION_RESP_COMPS_INVITATION);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new CompaniesInvitationsResponseReceiver();
		registerReceiver(receiver, filter);

		Toast.makeText(this, "Pedido rejeitado!", Toast.LENGTH_SHORT).show();
	}

	public class CompaniesInvitationsResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_COMPS_INVITATION =    
				"com.mamlambo.intent.action.MESSAGE_PROCESSED_COMPS_INVITATION";
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle parameters = intent.getExtras();

			invitations = (List<CompaniesInvitationsUser>) parameters.getSerializable(CompaniesInvitationsService.PARAM_OUT_INVITATIONS);
			adapter = new InvitationAdapter(invitations, CompaniesInvitationsActivity.this);
			listInvitations.setAdapter(adapter);

			if(invitations.isEmpty()){
				invitesNotFound.setVisibility(View.VISIBLE);
			}

			Log.i("teste","LISTA RETORNADA PELO SERVICE - TAMANHO: " + String.valueOf(invitations.size()));
		}
	}

	public class ActOrAnactInvitationsResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_ACTIVE_INVITATION =    
				"com.mamlambo.intent.action.MESSAGE_PROCESSED_INVITATION_ACT";
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle parameters = intent.getExtras();

			String string = parameters.getString("resultado");
			Log.i("teste", string);

		}
	}

}
