package com.example.slide;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.accential.trueone.adapter.InvitationsAdapter;
import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.CompaniesInvitationsUserBO;
import com.accential.trueone.service.InvitationsIntentService;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;

@SuppressWarnings("all")
/**
 * Convites
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
public class InvitatiosActivity extends Activity {

	private List<CompaniesInvitationsUser> invitations;
	private User user;
	private SharedPreferences loggedUser;
	private ListView lvInvitations;
	private TextView tvEmptyWarning;
	private ProgressBar pbInvitations;

	private InvitationsAdapter adapter;
	private InvitsResponseReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_invitations);

		// verificamos se aplicacao esta sendo usada em um smartphone ou tablet
		String typeUser = getResources().getString(R.string.isTablet);
		if (typeUser.equals("false")) {
			Log.e("tstando list de busca",
					"Estamos abrindo o app de um smartphe");
			OrientacaoUtils.setOrientationVertical(this);
		} else {
			Log.e("tstando lista de busca",
					"Estamos abrindo o app de um tablet");
			OrientacaoUtils.setOrientationHorizontal(this);
		}

		lvInvitations = (ListView) findViewById(R.id.lvInvitations);
		tvEmptyWarning = (TextView) findViewById(R.id.tvInvitationsWarning);
		pbInvitations = (ProgressBar) findViewById(R.id.pbInvitations);

		// capturando dados da "sessao"
		loggedUser = this.getSharedPreferences(SessionControl.USER_LOGGED, 0);
		user = SessionControl.decodeSessionUser(loggedUser.getString("user",
				null));

		ImageView userImg = (ImageView) findViewById(R.id.imageView2);

		// clique na imagem do usuario
		userImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InvitatiosActivity.this,
						UserActivity.class);
				startActivity(intent);
			}
		});

		// menu
		ImageView imgMenuCompras = (ImageView) findViewById(R.id.imageView3footer);
		ImageView imgMenuSign = (ImageView) findViewById(R.id.imageView4);
		ImageView imgMenuWish = (ImageView) findViewById(R.id.imageView5);
		ImageView imgMenuHome = (ImageView) findViewById(R.id.imageView1footer);

		// trocando icone clicado no menu
		imgMenuSign.setImageResource(R.drawable.adventa_icon_sign_blue);

		// clique em compras
		imgMenuCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(InvitatiosActivity.this,
						ComprasActivity.class);
				startActivity(intent);
			}
		});

		// clique em assinaturas
		imgMenuSign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(InvitatiosActivity.this,
						SignaturesActivity.class);
				startActivity(intent);
			}
		});

		// clique em wishlist
		imgMenuWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(InvitatiosActivity.this,
						WishHomeActivity.class);
				startActivity(intent);
			}
		});

		// clique em home
		imgMenuHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(InvitatiosActivity.this,
						AdventaActivity.class);
				startActivity(intent);
			}
		});

		Log.e("", "ID DO USUARIO: " + String.valueOf(user.getId()));

		// chamando service
		Intent commentsIntent = new Intent(this, InvitationsIntentService.class);
		commentsIntent.putExtra(InvitationsIntentService.PARAM_IN_USER_ID,
				user.getId());
		this.startService(commentsIntent);

		// preparando o receiver para o retorno do service
		IntentFilter filter = new IntentFilter(
				InvitsResponseReceiver.ACTION_RESP_INVITATIONS);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new InvitsResponseReceiver();
		this.registerReceiver(receiver, filter);

		/**
		 * Clique em item da lista
		 */
		lvInvitations.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int location, long arg3) {

				LayoutInflater layoutInflater = LayoutInflater.from(view
						.getContext());

				View promptView = layoutInflater.inflate(
						R.layout.x_pop_invitation_comp, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						view.getContext());

				alertDialogBuilder.setView(promptView);

				final AlertDialog alertD = alertDialogBuilder.create();

				Button BtnSim = (Button) promptView
						.findViewById(R.id.btnInivitYes);
				Button BtnNao = (Button) promptView
						.findViewById(R.id.btnInivitNot);
				Button btnVoltar = (Button) promptView
						.findViewById(R.id.btnVoltar);
				Button btnPerfil = (Button) promptView
						.findViewById(R.id.btnVerPerfil);
				TextView tvCompName = (TextView) promptView
						.findViewById(R.id.tvPopCompName);
				TextView tvCompDesc = (TextView) promptView
						.findViewById(R.id.tvPopCompDesc);

				// setando valores aos TextView
				tvCompName.setText(invitations.get(location).getCompany()
						.getFancy_name());
				tvCompDesc.setText(invitations.get(location).getCompany()
						.getDescription());
				//

				btnVoltar.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						alertD.cancel();
					}
				});

				// clica em nao assinar
				BtnNao.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// remover item e atualizar lista
						CompaniesInvitationsUserBO.inactivateInvite(invitations
								.get(location).getId());
						refreshList(location);
						alertD.cancel();
					}
				});

				// clica em assinar
				BtnSim.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						invitations.get(location).setUser(user);
						CompaniesInvitationsUserBO.activateInvite(invitations
								.get(location).getId(), invitations
								.get(location));
						refreshList(location);

						// Apaga registro da lista de assinaturas salva
						// anteriormente na sessão para
						// carregar lista atualizada
						SharedPreferences.Editor editor = loggedUser.edit();
						editor.remove(SessionControl.COMPANIES_USERS);
						editor.commit();

						// fechar popup
						alertD.cancel();
					}
				});

				// clique em ver perfil
				btnPerfil.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {

						Company comp = invitations.get(location).getCompany();
						CompaniesInvitationsUser invite = invitations
								.get(location);

						SharedPreferences.Editor editor = loggedUser.edit();

						String stringCompany = SessionControl
								.encodeSessionCompany(comp);
						String stringInvite = SessionControl
								.encodeSessionInvitation(invite);

						editor.putString(
								SessionControl.COMPANIES_DETAIL_COMPANY,
								stringCompany);
						editor.putString(
								SessionControl.COMPANIES_DETAIL_INVITE,
								stringInvite);
						editor.commit();

						Intent intent = new Intent(view.getContext(),
								CompaniesDetailActivity.class);
						startActivity(intent);
					}
				});

				alertD.show();
			}
		});

	}

	public class InvitsResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_INVITATIONS = "com.mamlambo.intent.action.MESSAGE_PROCESSED_INVITATIONS";

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();
			invitations = ((List<CompaniesInvitationsUser>) parameters
					.getSerializable(InvitationsIntentService.PARAM_OUT_INVITATIONS));

			adapter = new InvitationsAdapter(InvitatiosActivity.this,
					invitations);
			lvInvitations.setAdapter(adapter);

			pbInvitations.setVisibility(View.GONE);
		}
	}

	/**
	 * Atualiza lista quando usuário aceita assinar ou não uma empresa. Caso
	 * lista esteja vazia, voltamos para a tela anterior.
	 * 
	 * @param int location
	 */
	public void refreshList(int location) {
		invitations.remove(location);

		if (invitations.isEmpty()) {
			lvInvitations.setVisibility(View.GONE);
			pbInvitations.setVisibility(View.GONE);
			tvEmptyWarning.setVisibility(View.GONE);

			Intent intent = new Intent(InvitatiosActivity.this,
					SignaturesActivity.class);
			startActivity(intent);

		} else {

			adapter = new InvitationsAdapter(InvitatiosActivity.this,
					invitations);
			lvInvitations.setAdapter(adapter);

		}
	}

}
