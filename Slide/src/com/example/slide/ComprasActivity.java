package com.example.slide;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.ComprasAdapter;
import com.accential.trueone.bean.Checkout;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.OffersComment;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.OffersCommentBO;
import com.accential.trueone.service.CommentService;
import com.accential.trueone.service.ComprasService;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;
import com.loopj.android.image.SmartImageView;

/**
 * Rechamar o service: Mostramos a lista precarregada, mas ao mesmo tempo
 * continuamos a atualizar os dados, assim evitamos que os dados sejam "velhos"
 * ou desatualizados
 * 
 * @author Matheus Odilon - accentialbrasil
 * 
 */
@SuppressWarnings("all")
public class ComprasActivity extends Activity {

	private ListView lvCompras;
	private List<Checkout> compras;
	private ProgressBar pbCompras;
	private SharedPreferences loggedUser;
	private User user;
	private ComprasResponseReceiver receiver;
	private CommentsResponseReceiver commentReceiver;
	private ComprasAdapter adapter;
	private TextView tvTitle;

	private ProgressBar pbLoadList;
	private TextView tvLoadList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_compras);

		// verificamos se aplicacao está sendo usada em um smartphone ou tablet
		String type = getResources().getString(R.string.isTablet);
		if (type.equals("false")) {
			Log.e("tstando list de busca",
					"Estamos abrindo o app de um smartphe");
			OrientacaoUtils.setOrientationVertical(this);
		} else {
			Log.e("tstando lista de busca",
					"Estamos abrindo o app de um tablet");
			OrientacaoUtils.setOrientationHorizontal(this);
		}

		pbLoadList = (ProgressBar) findViewById(R.id.pbComprasLoadList);
		tvLoadList = (TextView) findViewById(R.id.tvComprasLoadList);

		lvCompras = (ListView) findViewById(R.id.lvCompras);
		pbCompras = (ProgressBar) findViewById(R.id.pbCompras);
		tvTitle = (TextView) findViewById(R.id.tvTitle);

		// mudando fonte do TextView
		Typeface font = Typeface.createFromAsset(getAssets(),
				"helvetica-35-thin-1361522141.ttf");
		tvTitle.setTypeface(font);

		ImageView userImg = (ImageView) findViewById(R.id.imageView2);

		// clique na imagem do usuario
		userImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ComprasActivity.this,
						UserActivity.class);
				startActivity(intent);
			}
		});

		// menu
		ImageView imgMenuCompras = (ImageView) findViewById(R.id.imageView3footer);
		ImageView imgMenuSign = (ImageView) findViewById(R.id.imageView4);
		ImageView imgMenuWish = (ImageView) findViewById(R.id.imageView5);
		ImageView imgMenuHome = (ImageView) findViewById(R.id.imageView1footer);
		ImageView imgMenuOffer = (ImageView) findViewById(R.id.imageView2footer);

		imgMenuOffer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ComprasActivity.this,
						OffersListActivity.class);
				intent.putExtra("listType", "offersMyProfile");
				startActivity(intent);
			}
		});

		// mudando imagem clicada no menu
		imgMenuCompras.setImageResource(R.drawable.adventa_icon_compra_blue);

		// clique em compras
		imgMenuCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ComprasActivity.this,
						ComprasActivity.class);
				startActivity(intent);
			}
		});

		// clique em assinaturas
		imgMenuSign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ComprasActivity.this,
						SignaturesActivity.class);
				startActivity(intent);
			}
		});

		// clique em wishlist
		imgMenuWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ComprasActivity.this,
						WishHomeActivity.class);
				startActivity(intent);
			}
		});

		// clique em home
		imgMenuHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ComprasActivity.this,
						AdventaActivity.class);
				startActivity(intent);
			}
		});

		// recupera "sessao"
		loggedUser = this.getSharedPreferences(SessionControl.USER_LOGGED, 0);
		// recupera usuario logado
		user = SessionControl.decodeSessionUser(loggedUser.getString(
				SessionControl.USER, null));

		compras = SessionControl.decodeSessionComprasUser(loggedUser.getString(
				SessionControl.COMPRAS_USERS, null));

		// lista vazia
		if (compras == null) {

			// chamando service
			Intent commentsIntent = new Intent(this, ComprasService.class);
			commentsIntent.putExtra(ComprasService.PARAM_COMPRAS_USER_ID,
					user.getId());
			this.startService(commentsIntent);

			// preparando o receiver para o retorno do service
			IntentFilter filter = new IntentFilter(
					ComprasResponseReceiver.ACTION_RESP_COMPRAS);
			filter.addCategory(Intent.CATEGORY_DEFAULT);
			receiver = new ComprasResponseReceiver();
			this.registerReceiver(receiver, filter);
			// lista cheia
		} else {
			pbCompras.setVisibility(View.GONE);
			adapter = new ComprasAdapter(ComprasActivity.this, compras);
			lvCompras.setAdapter(adapter);

			tvLoadList.setVisibility(View.VISIBLE);
			pbLoadList.setVisibility(View.VISIBLE);
			// REchamando Service
			Log.e("", "REchamando Service - Carregando lista novamente.");
			Intent commentsIntent = new Intent(this, ComprasService.class);
			commentsIntent.putExtra(ComprasService.PARAM_COMPRAS_USER_ID,
					user.getId());
			this.startService(commentsIntent);

			// preparando o receiver para o retorno do service
			IntentFilter filter = new IntentFilter(
					ComprasResponseReceiver.ACTION_RESP_COMPRAS);
			filter.addCategory(Intent.CATEGORY_DEFAULT);
			receiver = new ComprasResponseReceiver();
			this.registerReceiver(receiver, filter);
		}

		// clique RAPIDO em item da lista
		lvCompras.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int location, long arg3) {

				LayoutInflater mInflater = LayoutInflater
						.from(ComprasActivity.this);

				View mPromptView = mInflater.inflate(R.layout.x_pop_compras,
						null);

				AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(
						ComprasActivity.this);

				mAlertDialogBuilder.setView(mPromptView);

				Button btnDetalhes = (Button) mPromptView
						.findViewById(R.id.btnComprasDetalhes);
				Button btnComentar = (Button) mPromptView
						.findViewById(R.id.btnComprasComentario);
				Button btnSair = (Button) mPromptView
						.findViewById(R.id.btnComprasSair);

				final AlertDialog mAlert = mAlertDialogBuilder.create();

				// clique em ver detalhe
				btnDetalhes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						//
						LayoutInflater layoutInflater = LayoutInflater
								.from(ComprasActivity.this);

						View promptView = layoutInflater.inflate(
								R.layout.x_pop_compra_details, null);

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								ComprasActivity.this);

						alertDialogBuilder.setView(promptView);

						// imagem da oferta
						((SmartImageView) promptView
								.findViewById(R.id.ivCompraOfferPhoto))
								.setImageUrl(compras.get(location).getOffer()
										.getPhoto());

						// titulo da oferta
						((TextView) promptView
								.findViewById(R.id.tvCompraOfferTitle))
								.setText(compras.get(location).getOffer()
										.getTitle());

						// valor unitario
						((TextView) promptView
								.findViewById(R.id.tvCompraValueUnit))
								.setText("R$ ".concat(String.valueOf(
										compras.get(location).getOffer()
												.getValue()).replace(".", ",")));

						// quantidade
						((TextView) promptView.findViewById(R.id.tvCompraQtd))
								.setText(String.valueOf(compras.get(location)
										.getAmount()));

						// total
						((TextView) promptView
								.findViewById(R.id.tvCompraValueTotal))
								.setText("R$ ".concat(String.valueOf(
										compras.get(location).getTotalValue())
										.replace(".", ",")));

						// nome empresa
						((TextView) promptView.findViewById(R.id.tvCompName))
								.setText(compras.get(location).getCompany()
										.getFancy_name());

						// email empresa
						TextView email = (TextView) promptView
								.findViewById(R.id.tvCompEmail);
						email.setText(compras.get(location).getCompany()
								.getEmail());

						// telefone da empresa
						((TextView) promptView.findViewById(R.id.tvCompTel))
								.setText(compras.get(location).getCompany()
										.getPhone());

						// tempo de entrega
						((TextView) promptView
								.findViewById(R.id.tvCompEntregaQtd))
								.setText(String.valueOf(compras.get(location)
										.getDeliveryTime()));

						// tipo de pagamento
						((TextView) promptView
								.findViewById(R.id.tvCompPaymentType))
								.setText(compras.get(location).getMethod()
										.getType()
										+ " - "
										+ compras.get(location).getMethod()
												.getName());

						// parcelas
						TextView tvInstalmment = (TextView) promptView
								.findViewById(R.id.textView8);

						if (compras.get(location).getInstallment() == 0) {
							tvInstalmment.setText("sem parcelamento");
						} else {
							tvInstalmment.setText(String.valueOf(compras.get(
									location).getInstallment()));
						}

						// clique no email
						email.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View view) {
								Intent sendIntent = new Intent(
										Intent.ACTION_VIEW);
								sendIntent.setType("plain/text");
								sendIntent.setData(Uri.parse(compras
										.get(location).getCompany().getEmail()));
								sendIntent
										.setClassName("com.google.android.gm",
												"com.google.android.gm.ComposeActivityGmail");
								sendIntent.putExtra(Intent.EXTRA_EMAIL,
										new String[] { compras.get(location)
												.getCompany().getEmail() });
								sendIntent.putExtra(Intent.EXTRA_SUBJECT,
										"Contato");
								sendIntent.putExtra(Intent.EXTRA_TEXT,
										"Digite o texto...");
								startActivity(sendIntent);
							}
						});

						alertDialogBuilder.setCancelable(false)
						// caso usuario clique em OK nós iremos cadastrar o
						// comentario
								.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												dialog.cancel();
											}
										});

						AlertDialog alertD = alertDialogBuilder.create();

						alertD.show();

					}
				});
				// clique em comentar
				btnComentar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						LayoutInflater layoutInflater = LayoutInflater
								.from(ComprasActivity.this);

						View promptView = layoutInflater.inflate(
								R.layout.x_pop_comment, null);

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								ComprasActivity.this);

						alertDialogBuilder.setView(promptView);

						// pegando as views
						final EditText titulo = (EditText) promptView
								.findViewById(R.id.editText1);
						final EditText descricao = (EditText) promptView
								.findViewById(R.id.etCommentDesc);
						final RatingBar nota = (RatingBar) promptView
								.findViewById(R.id.ratingBar1);

						// mudando cor do rating
						LayerDrawable stars = (LayerDrawable) nota
								.getProgressDrawable();
						stars.getDrawable(2).setColorFilter(
								Color.parseColor("#f37165"),
								PorterDuff.Mode.SRC_ATOP);
						//
						alertDialogBuilder
								.setCancelable(false)
								// caso usuario clique em OK nós iremos
								// cadastrar o
								// comentario
								.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {

												if ((titulo.getText().length() != 0)
														&& (descricao.getText()
																.length() != 0)
														&& (nota.getRating() != 0)) {

													// oferta a ser mandada como
													// parametro
													Offer offer = compras.get(
															location)
															.getOffer();

													OffersComment comment = new OffersComment();
													comment.setUser(user);
													comment.setOffer(offer);
													comment.setTitle(titulo
															.getText()
															.toString());
													comment.setDescricao(descricao
															.getText()
															.toString());
													comment.setEvaluation(String.valueOf(nota
															.getRating()));

													Log.e("", "SALVAR DAO");
													OffersCommentBO
															.saveComment(comment);

													Toast.makeText(
															ComprasActivity.this,
															"Comentário Salvo!",
															Toast.LENGTH_SHORT)
															.show();
												} else {
													Toast.makeText(
															ComprasActivity.this,
															"Preencha todos os campos para adicionar o comentário.",
															Toast.LENGTH_LONG)
															.show();
												}
											}
										})
								.setNegativeButton("Cancelar",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												dialog.cancel();
											}

										});

						// create an alert dialog

						AlertDialog alertD = alertDialogBuilder.create();

						alertD.show();

					}
				});
				// clique em sair
				btnSair.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mAlert.cancel();
					}
				});
				mAlert.show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class ComprasResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_COMPRAS = "com.mamlambo.intent.action.MESSAGE_PROCESSED_COMPRAS";

		@Override
		public void onReceive(Context context, Intent intent) {

			// recurando lista de assisnaturas capturada no service
			Bundle parameters = intent.getExtras();
			compras = (List<Checkout>) parameters
					.getSerializable(ComprasService.PARAM_COMPRAS_LIST);

			// salvando lista de assinaturas no SharedPreference
			SharedPreferences.Editor editor = loggedUser.edit();

			String comprasString = SessionControl
					.encodeSessionComprasUser(compras);

			editor.putString(SessionControl.COMPRAS_USERS, comprasString);
			editor.commit();

			pbCompras.setVisibility(View.GONE);
			tvLoadList.setVisibility(View.GONE);
			pbLoadList.setVisibility(View.GONE);
			adapter = new ComprasAdapter(ComprasActivity.this, compras);
			lvCompras.setAdapter(adapter);

		}
	}

	public class CommentsResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_COMMENTS_CHECK = "com.mamlambo.intent.action.MESSAGE_PROCESSED_COMMENTS_CHECK";

		@Override
		public void onReceive(Context context, Intent intent) {

			// recurando lista de assisnaturas capturada no service
			Bundle parameters = intent.getExtras();
			List<OffersComment> comments = (List<OffersComment>) parameters
					.getSerializable(CommentService.PARAM_OUT_COMMENTS);
			Log.e("SERVICE",
					"RESPONSE SERVICE - COMENTARIO: "
							+ String.valueOf(comments.size()));
		}
	}

}
