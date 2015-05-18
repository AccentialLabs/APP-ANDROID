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
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.adapter.SignaturesAdapter;
import com.accential.trueone.adapter.SingnaturesCompanyAdapter;
import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bean.Company;
import com.accential.trueone.bean.Offer;
import com.accential.trueone.bean.User;
import com.accential.trueone.bo.CompaniesUserBO;
import com.accential.trueone.service.CheckInvitationsService;
import com.accential.trueone.service.CompaniesOfferProfileService;
import com.accential.trueone.service.CompaniesOfferService;
import com.accential.trueone.service.SignCompanyService;
import com.accential.trueone.service.SignaturesSearchService;
import com.accential.trueone.service.SignaturesService;
import com.accential.trueone.utils.DownloadImagemUtil;
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
public class SignaturesActivity extends Activity {

	private ListView lvSignatures;
	private ListView lvSearch;
	private List<CompaniesUser> signatures;
	private List<CompaniesUser> inactives;
	private List<Company> companies;
	private SignaturesResponseReceiver receiver;
	private SignCompResponseReceiver signReceiver;
	private CompanySearchResponseReceiver searchReceiver;
	private CompaniesOfferResponseReceiver compOffersReceiver;
	private CompOffersProfileResponseReceiver myProfileReceiver;
	private Offer offer;
	private SharedPreferences loggedUser;
	private User user;
	private ProgressBar pbSignatures;
	// popup
	private ProgressBar pbCompAllOffer;
	private TableRow tbCompAllOffer;
	private TextView tvQtdAllOffers;
	private TextView tvTitle;
	private ProgressBar pbCompMyOffer;
	private TableRow tbCompMyOffer;
	private TextView tvQtdMyOffer;
	private ProgressBar pbLoadList;
	private TextView tvLoadList;

	private SignaturesAdapter adapter;
	private EditText etSearch;
	private SingnaturesCompanyAdapter searchAdapter;
	private Button btnQtdInvitations;

	private ImageView imgButtonSearch;

	// private ImageView imgSign;

	private InvitationsCountResponseReceiver invitationsCountReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_signatures);

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

		// Garbage collector
		Log.e("", "Iniciando GARBAGE colector...");
		try {
			System.gc();
			Log.e("", "Iniciou o Garbage Collector....");

		} catch (Exception e) {
			Log.e("Error AdventaActivity",
					"Erro ao iniciar garbage colector - linha 61");
			e.printStackTrace();
		}

		pbLoadList = (ProgressBar) findViewById(R.id.pbSignLoadList);
		tvLoadList = (TextView) findViewById(R.id.tvSignLoadList);

		lvSearch = (ListView) findViewById(R.id.lvSearch);
		lvSignatures = (ListView) findViewById(R.id.lvSignatures);
		pbSignatures = (ProgressBar) findViewById(R.id.pbSignatures);
		etSearch = (EditText) findViewById(R.id.etSearchCompanyName);
		btnQtdInvitations = (Button) findViewById(R.id.btnSignaturesInvitations);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		imgButtonSearch = (ImageView) findViewById(R.id.imgSignLupa);
		// imgSign = (ImageView) findViewById(R.id.imageView1);

		ImageView userImg = (ImageView) findViewById(R.id.imageView2);

		// clique na imagem do usuario
		userImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SignaturesActivity.this,
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
				Intent intent = new Intent(SignaturesActivity.this,
						OffersListActivity.class);
				intent.putExtra("listType", "offersMyProfile");
				startActivity(intent);
			}
		});

		// mudando imagem clicado do menu
		imgMenuSign.setImageResource(R.drawable.adventa_icon_sign_blue);

		// clique em compras
		imgMenuCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(SignaturesActivity.this,
						ComprasActivity.class);
				startActivity(intent);
			}
		});

		// clique em assinaturas
		imgMenuSign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(SignaturesActivity.this,
						SignaturesActivity.class);
				startActivity(intent);
			}
		});

		// clique em wishlist
		imgMenuWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(SignaturesActivity.this,
						WishHomeActivity.class);
				startActivity(intent);
			}
		});

		// clique em home
		imgMenuHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(SignaturesActivity.this,
						AdventaActivity.class);
				startActivity(intent);
			}
		});

		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (etSearch.getText().toString().equals("")) {
					lvSearch.setVisibility(View.GONE);
					lvSignatures.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		// mudando fonte do TextView
		Typeface font = Typeface.createFromAsset(getAssets(),
				"helvetica-35-thin-1361522141.ttf");
		tvTitle.setTypeface(font);

		lvSearch.setVisibility(View.GONE);

		loggedUser = this.getSharedPreferences(SessionControl.USER_LOGGED, 0);

		user = SessionControl.decodeSessionUser(loggedUser.getString(
				SessionControl.USER, null));

		// recuperando lista salva no SharedPreferences caso exista
		signatures = SessionControl.decodeSessionCompaniesUser(loggedUser
				.getString(SessionControl.COMPANIES_USERS, null));

		// //////
		Intent commentsIntent2 = new Intent(SignaturesActivity.this,
				CheckInvitationsService.class);
		commentsIntent2.putExtra(CheckInvitationsService.PARAM_IN_USER_ID,
				user.getId());
		SignaturesActivity.this.startService(commentsIntent2);

		// preparando o receiver para o retorno do service
		IntentFilter filterw = new IntentFilter(
				InvitationsCountResponseReceiver.ACTION_RESP_INVITE_COUNT);
		filterw.addCategory(Intent.CATEGORY_DEFAULT);
		invitationsCountReceiver = new InvitationsCountResponseReceiver();
		SignaturesActivity.this.registerReceiver(invitationsCountReceiver,
				filterw);
		// ////////

		// clique no circulo de quantidade de convites
		btnQtdInvitations.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(SignaturesActivity.this,
						InvitatiosActivity.class);
				startActivity(intent);
			}
		});

		// lista nula
		if (signatures == null) {
			Log.e("LISTA NULA", "LISTA NULA - START SERVICE");

			// start service de busca dos comentarios
			Intent commentsIntent = new Intent(this, SignaturesService.class);
			commentsIntent.putExtra(SignaturesService.PARAM_USER_ID,
					user.getId());
			this.startService(commentsIntent);

			// preparando o receiver para o retorno do service
			IntentFilter filter = new IntentFilter(
					SignaturesResponseReceiver.ACTION_RESP_SIGNATURES);
			filter.addCategory(Intent.CATEGORY_DEFAULT);
			receiver = new SignaturesResponseReceiver();
			this.registerReceiver(receiver, filter);
		}// lista cheia - carrega no adapter/listview
		else {
			Log.e("LISTA CHEIA", "LISTA CHEIA - ADAPTANDO");
			pbSignatures.setVisibility(View.GONE);
			adapter = new SignaturesAdapter(SignaturesActivity.this, signatures);
			lvSignatures.setAdapter(adapter);

			pbLoadList.setVisibility(View.VISIBLE);
			tvLoadList.setVisibility(View.VISIBLE);
			// REchamando service
			// start service de busca das assinaturas
			Log.e("REchama Service", "REchamar service para atualizar lista.");
			Intent commentsIntent = new Intent(this, SignaturesService.class);
			commentsIntent.putExtra(SignaturesService.PARAM_USER_ID,
					user.getId());
			this.startService(commentsIntent);

			// preparando o receiver para o retorno do service
			IntentFilter filter = new IntentFilter(
					SignaturesResponseReceiver.ACTION_RESP_SIGNATURES);
			filter.addCategory(Intent.CATEGORY_DEFAULT);
			receiver = new SignaturesResponseReceiver();
			this.registerReceiver(receiver, filter);
		}

		lvSignatures.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int location, long arg3) {

				final Company comp = signatures.get(location).getCompany();
				LayoutInflater layoutInflater = LayoutInflater
						.from(SignaturesActivity.this);

				View promptView = layoutInflater.inflate(
						R.layout.x_pop_company_detail, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						SignaturesActivity.this);

				alertDialogBuilder.setView(promptView);

				TextView compName = (TextView) promptView
						.findViewById(R.id.tvCompName);
				TextView compDesc = (TextView) promptView
						.findViewById(R.id.tvCompDesc);
				TextView compEmail = (TextView) promptView
						.findViewById(R.id.tvCompEmail);
				TextView compTel = (TextView) promptView
						.findViewById(R.id.tvCompTel);
				SmartImageView compPhoto = (SmartImageView) promptView
						.findViewById(R.id.ivCompPhoto);
				// todas ofertas
				pbCompAllOffer = (ProgressBar) promptView
						.findViewById(R.id.pbCompAllOffer);
				tbCompAllOffer = (TableRow) promptView
						.findViewById(R.id.tbRowCompAllOffer);
				tvQtdAllOffers = (TextView) promptView
						.findViewById(R.id.tvCompOfferQtd);
				// para o meu perfil
				pbCompMyOffer = (ProgressBar) promptView
						.findViewById(R.id.pbCompMyOffers);
				tbCompMyOffer = (TableRow) promptView
						.findViewById(R.id.tbRowCompMyOffer);
				tvQtdMyOffer = (TextView) promptView
						.findViewById(R.id.tvCompMyOfferQtd);

				compName.setText(comp.getFancy_name());
				compDesc.setText(comp.getDescription());
				compEmail.setText(comp.getEmail());
				compTel.setText(comp.getPhone());
				compPhoto.setImageUrl(comp.getLogo());

				// carrega service de ofertas da company
				Intent commentsIntent = new Intent(SignaturesActivity.this,
						CompaniesOfferService.class);
				commentsIntent.putExtra(CompaniesOfferService.PARAM_IN_COMP_ID,
						comp.getId());
				SignaturesActivity.this.startService(commentsIntent);

				// preparando o receiver para o retorno do service
				IntentFilter filter = new IntentFilter(
						CompaniesOfferResponseReceiver.ACTION_RESP_COMP_OFFERS);
				filter.addCategory(Intent.CATEGORY_DEFAULT);
				compOffersReceiver = new CompaniesOfferResponseReceiver();
				SignaturesActivity.this.registerReceiver(compOffersReceiver,
						filter);

				// service de ofertas do perfil do usuario
				Intent inten = new Intent(SignaturesActivity.this,
						CompaniesOfferProfileService.class);
				inten.putExtra(CompaniesOfferProfileService.PARAM_IN_USER_ID,
						user.getId());
				SignaturesActivity.this.startService(inten);

				// preparando o receiver para o retorno do service
				IntentFilter filt = new IntentFilter(
						CompOffersProfileResponseReceiver.ACTION_RESP_OFFERS_MY_PROFILE);
				filt.addCategory(Intent.CATEGORY_DEFAULT);
				myProfileReceiver = new CompOffersProfileResponseReceiver();
				SignaturesActivity.this.registerReceiver(myProfileReceiver,
						filt);

				// clique nas ofertas para meu perfil
				tbCompMyOffer.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(SignaturesActivity.this,
								OffersListActivity.class);
						intent.putExtra("listType", "offersMyProfile");
						startActivity(intent);
					}
				});

				// clique nas ofertas de determinada company
				tbCompAllOffer.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(SignaturesActivity.this,
								OffersListActivity.class);
						intent.putExtra("listType", "offersByComp");
						intent.putExtra("compId", comp.getId());
						startActivity(intent);
					}
				});

				alertDialogBuilder.setCancelable(false)
				// caso usuario clique em OK nós iremos cadastrar o
				// comentario
						.setPositiveButton("Voltar",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				final AlertDialog alertD = alertDialogBuilder.create();
				alertD.show();

				return false;
			}
		});

		lvSignatures.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapters, View view,
					final int location, long arg3) {

				final Company comp = signatures.get(location).getCompany();
				LayoutInflater layoutInflater = LayoutInflater
						.from(SignaturesActivity.this);

				View promptView = layoutInflater.inflate(
						R.layout.x_pop_sign_company, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						SignaturesActivity.this);

				alertDialogBuilder.setView(promptView);

				Button btnDetails = (Button) promptView
						.findViewById(R.id.btnSignCompDetails);

				Button btnUnsign = (Button) promptView
						.findViewById(R.id.btnSignCompUnsign);

				Button btnVoltar = (Button) promptView
						.findViewById(R.id.btnSignVoltar);

				final AlertDialog alertD = alertDialogBuilder.create();

				// clique em ver detalhes
				btnDetails.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final Company comp = signatures.get(location)
								.getCompany();
						LayoutInflater layoutInflater = LayoutInflater
								.from(SignaturesActivity.this);

						View promptView = layoutInflater.inflate(
								R.layout.x_pop_company_detail, null);

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								SignaturesActivity.this);

						alertDialogBuilder.setView(promptView);

						TextView compName = (TextView) promptView
								.findViewById(R.id.tvCompName);
						TextView compDesc = (TextView) promptView
								.findViewById(R.id.tvCompDesc);
						TextView compEmail = (TextView) promptView
								.findViewById(R.id.tvCompEmail);
						TextView compTel = (TextView) promptView
								.findViewById(R.id.tvCompTel);
						SmartImageView compPhoto = (SmartImageView) promptView
								.findViewById(R.id.ivCompPhoto);
						// todas ofertas
						pbCompAllOffer = (ProgressBar) promptView
								.findViewById(R.id.pbCompAllOffer);
						tbCompAllOffer = (TableRow) promptView
								.findViewById(R.id.tbRowCompAllOffer);
						tvQtdAllOffers = (TextView) promptView
								.findViewById(R.id.tvCompOfferQtd);
						// para o meu perfil
						pbCompMyOffer = (ProgressBar) promptView
								.findViewById(R.id.pbCompMyOffers);
						tbCompMyOffer = (TableRow) promptView
								.findViewById(R.id.tbRowCompMyOffer);
						tvQtdMyOffer = (TextView) promptView
								.findViewById(R.id.tvCompMyOfferQtd);

						compName.setText(comp.getFancy_name());
						compDesc.setText(comp.getDescription());
						compEmail.setText(comp.getEmail());
						compTel.setText(comp.getPhone());
						compPhoto.setImageUrl(comp.getLogo());

						// carrega service de ofertas da company
						Intent commentsIntent = new Intent(
								SignaturesActivity.this,
								CompaniesOfferService.class);
						commentsIntent.putExtra(
								CompaniesOfferService.PARAM_IN_COMP_ID,
								comp.getId());
						SignaturesActivity.this.startService(commentsIntent);

						// preparando o receiver para o retorno do service
						IntentFilter filter = new IntentFilter(
								CompaniesOfferResponseReceiver.ACTION_RESP_COMP_OFFERS);
						filter.addCategory(Intent.CATEGORY_DEFAULT);
						compOffersReceiver = new CompaniesOfferResponseReceiver();
						SignaturesActivity.this.registerReceiver(
								compOffersReceiver, filter);

						// service de ofertas do perfil do usuario
						Intent inten = new Intent(SignaturesActivity.this,
								CompaniesOfferProfileService.class);
						inten.putExtra(
								CompaniesOfferProfileService.PARAM_IN_USER_ID,
								user.getId());
						SignaturesActivity.this.startService(inten);

						// preparando o receiver para o retorno do service
						IntentFilter filt = new IntentFilter(
								CompOffersProfileResponseReceiver.ACTION_RESP_OFFERS_MY_PROFILE);
						filt.addCategory(Intent.CATEGORY_DEFAULT);
						myProfileReceiver = new CompOffersProfileResponseReceiver();
						SignaturesActivity.this.registerReceiver(
								myProfileReceiver, filt);

						// clique nas ofertas para meu perfil
						tbCompMyOffer.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(
										SignaturesActivity.this,
										OffersListActivity.class);
								intent.putExtra("listType", "offersMyProfile");
								startActivity(intent);
							}
						});

						// clique nas ofertas de determinada company
						tbCompAllOffer
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										Intent intent = new Intent(
												SignaturesActivity.this,
												OffersListActivity.class);
										intent.putExtra("listType",
												"offersByComp");
										intent.putExtra("compId", comp.getId());
										startActivity(intent);
									}
								});

						alertDialogBuilder.setCancelable(false)
						// caso usuario clique em OK nós iremos cadastrar o
						// comentario
								.setPositiveButton("Voltar",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												dialog.cancel();
											}
										});

						final AlertDialog alertD = alertDialogBuilder.create();
						alertD.show();
					}
				});

				// clique em desassinar empresa
				btnUnsign.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// mudando status da assinatura
						CompaniesUserBO.inactiveCompsUser(signatures.get(
								location).getId());

						// remove a empresa do array e faz desaparecer do
						// listview
						signatures.remove(location);

						// salvando lista de assinaturas no SharedPreference
						SharedPreferences.Editor editor = loggedUser.edit();

						String signaturesString = SessionControl
								.encodeSessionCompaniesUser(signatures);

						editor.putString(SessionControl.COMPANIES_USERS,
								signaturesString);
						editor.commit();

						// readapta a lista
						adapter = new SignaturesAdapter(
								SignaturesActivity.this, signatures);
						lvSignatures.setAdapter(adapter);

						alertD.cancel();
					}
				});

				// clique em voltar
				btnVoltar.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						alertD.cancel();
					}
				});

				alertD.show();
			}
		});

		etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {

					Log.e("", "Iniciando GARBAGE colector...");
					try {
						System.gc();
						Log.e("", "Iniciou o Garbage Collector....");

					} catch (Exception e) {
						Log.e("Error AdventaActivity",
								"Erro ao iniciar garbage colector - linha 61");
						e.printStackTrace();
					}

					Log.e("", "CLIQUEI EM BUSCAR - BUSCAR");
					lvSignatures.setVisibility(View.GONE);
					pbSignatures.setVisibility(View.VISIBLE);

					Log.e("valor", "valor do edit: "
							+ etSearch.getText().toString());
					Log.e("service", "STARTANDO SERVICO DE PESQUISA");
					// start service de busca dos comentarios
					Intent commentsIntent = new Intent(v.getContext(),
							SignaturesSearchService.class);
					commentsIntent.putExtra(
							SignaturesSearchService.PARAM_COMP_NAME, etSearch
									.getText().toString());
					v.getContext().startService(commentsIntent);

					// preparando o receiver para o retorno do service
					IntentFilter filter = new IntentFilter(
							CompanySearchResponseReceiver.ACTION_RESP_COMPANY_SEARCH);
					filter.addCategory(Intent.CATEGORY_DEFAULT);
					searchReceiver = new CompanySearchResponseReceiver();
					v.getContext().registerReceiver(searchReceiver, filter);
					return true;
				}
				return false;
			}
		});

		// clique em item da lista de empresas
		lvSearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parents, View view,
					final int location, long arg3) {

				LayoutInflater layoutInflater = LayoutInflater
						.from(SignaturesActivity.this);

				View promptView = layoutInflater.inflate(
						R.layout.x_pop_search_company, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						SignaturesActivity.this);

				alertDialogBuilder.setView(promptView);

				Button btnDetails = (Button) promptView
						.findViewById(R.id.btnSearchCompDetails);

				Button btnSign = (Button) promptView
						.findViewById(R.id.btnSearchCompSign);

				Button btnVoltar = (Button) promptView
						.findViewById(R.id.btnSearchCompVoltar);

				final AlertDialog alertD = alertDialogBuilder.create();

				// clique em detalhe
				btnDetails.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final Company comp = signatures.get(location)
								.getCompany();
						LayoutInflater layoutInflater = LayoutInflater
								.from(SignaturesActivity.this);

						View promptView = layoutInflater.inflate(
								R.layout.x_pop_company_detail, null);

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								SignaturesActivity.this);

						alertDialogBuilder.setView(promptView);

						TextView compName = (TextView) promptView
								.findViewById(R.id.tvCompName);
						TextView compDesc = (TextView) promptView
								.findViewById(R.id.tvCompDesc);
						TextView compEmail = (TextView) promptView
								.findViewById(R.id.tvCompEmail);
						TextView compTel = (TextView) promptView
								.findViewById(R.id.tvCompTel);
						SmartImageView compPhoto = (SmartImageView) promptView
								.findViewById(R.id.ivCompPhoto);
						// todas ofertas
						pbCompAllOffer = (ProgressBar) promptView
								.findViewById(R.id.pbCompAllOffer);
						tbCompAllOffer = (TableRow) promptView
								.findViewById(R.id.tbRowCompAllOffer);
						tvQtdAllOffers = (TextView) promptView
								.findViewById(R.id.tvCompOfferQtd);
						// para o meu perfil
						pbCompMyOffer = (ProgressBar) promptView
								.findViewById(R.id.pbCompMyOffers);
						tbCompMyOffer = (TableRow) promptView
								.findViewById(R.id.tbRowCompMyOffer);
						tvQtdMyOffer = (TextView) promptView
								.findViewById(R.id.tvCompMyOfferQtd);

						compName.setText(comp.getFancy_name());
						compDesc.setText(comp.getDescription());
						compEmail.setText(comp.getEmail());
						compTel.setText(comp.getPhone());
						compPhoto.setImageUrl(comp.getLogo());

						// carrega service de ofertas da company
						Intent commentsIntent = new Intent(
								SignaturesActivity.this,
								CompaniesOfferService.class);
						commentsIntent.putExtra(
								CompaniesOfferService.PARAM_IN_COMP_ID,
								comp.getId());
						SignaturesActivity.this.startService(commentsIntent);

						// preparando o receiver para o retorno do service
						IntentFilter filter = new IntentFilter(
								CompaniesOfferResponseReceiver.ACTION_RESP_COMP_OFFERS);
						filter.addCategory(Intent.CATEGORY_DEFAULT);
						compOffersReceiver = new CompaniesOfferResponseReceiver();
						SignaturesActivity.this.registerReceiver(
								compOffersReceiver, filter);

						pbCompMyOffer.setVisibility(View.GONE);
						tbCompMyOffer.setVisibility(View.GONE);

						// clique nas ofertas para meu perfil
						tbCompMyOffer.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(
										SignaturesActivity.this,
										OffersListActivity.class);
								intent.putExtra("listType", "offersMyProfile");
								startActivity(intent);
							}
						});

						// clique nas ofertas de determinada company
						tbCompAllOffer
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										Intent intent = new Intent(
												SignaturesActivity.this,
												OffersListActivity.class);
										intent.putExtra("listType",
												"offersByComp");
										intent.putExtra("compId", comp.getId());
										startActivity(intent);
									}
								});

						alertDialogBuilder.setCancelable(false)
						// caso usuario clique em OK nós iremos cadastrar o
						// comentario
								.setPositiveButton("Voltar",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												dialog.cancel();
											}
										});

						final AlertDialog alertD = alertDialogBuilder.create();
						alertD.show();
					}
				});

				// clique em assinar
				btnSign.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						pbSignatures.setVisibility(View.VISIBLE);
						final int position = location;

						// chama service para realizar o cadastro da assinatura
						Intent commentsIntent = new Intent(
								SignaturesActivity.this,
								SignCompanyService.class);
						commentsIntent.putExtra(
								SignCompanyService.PARAM_IN_USER_ID,
								user.getId());
						commentsIntent.putExtra(
								SignCompanyService.PARAM_IN_COMPANY_ID,
								companies.get(position).getId());
						SignaturesActivity.this.startService(commentsIntent);

						// preparando o receiver para o retorno do service
						IntentFilter filter = new IntentFilter(
								SignCompResponseReceiver.ACTION_RESP_SIGN_COMP);
						filter.addCategory(Intent.CATEGORY_DEFAULT);
						signReceiver = new SignCompResponseReceiver();
						SignaturesActivity.this.registerReceiver(signReceiver,
								filter);

						companies.remove(location);
						// fecha pop up
						alertD.cancel();
					}
				});

				// clique em voltar
				btnVoltar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						alertD.cancel();
					}
				});

				alertD.show();
			}
		});

		// clique na imagem com transparencia
		// imgSign.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(SignaturesActivity.this,
		// SignaturesActivity.class);
		// startActivity(intent);
		// }
		// });

		// clique na imagem da lupa
		imgButtonSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("", "Iniciando GARBAGE colector...");
				try {
					System.gc();
					Log.e("", "Iniciou o Garbage Collector....");

				} catch (Exception e) {
					Log.e("Error AdventaActivity",
							"Erro ao iniciar garbage colector - linha 61");
					e.printStackTrace();
				}

				Log.e("", "CLIQUEI EM BUSCAR - BUSCAR");
				lvSignatures.setVisibility(View.GONE);
				pbSignatures.setVisibility(View.VISIBLE);

				Log.e("valor", "valor do edit: "
						+ etSearch.getText().toString());
				Log.e("service", "STARTANDO SERVICO DE PESQUISA");
				// start service de busca dos comentarios
				Intent commentsIntent = new Intent(v.getContext(),
						SignaturesSearchService.class);
				commentsIntent.putExtra(
						SignaturesSearchService.PARAM_COMP_NAME, etSearch
								.getText().toString());
				v.getContext().startService(commentsIntent);

				// preparando o receiver para o retorno do service
				IntentFilter filter = new IntentFilter(
						CompanySearchResponseReceiver.ACTION_RESP_COMPANY_SEARCH);
				filter.addCategory(Intent.CATEGORY_DEFAULT);
				searchReceiver = new CompanySearchResponseReceiver();
				v.getContext().registerReceiver(searchReceiver, filter);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Captura o retorno da lista de assinaturas ativas do usuario
	 * 
	 * @author accentialbrasil
	 * 
	 */
	public class SignaturesResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_SIGNATURES = "com.mamlambo.intent.action.MESSAGE_PROCESSED_SIGNATURES";

		@Override
		public void onReceive(Context context, Intent intent) {

			// recurando lista de assisnaturas capturada no service
			Bundle parameters = intent.getExtras();
			signatures = (List<CompaniesUser>) parameters
					.getSerializable(SignaturesService.PARAM_SIGNATURES_LIST);

			// salvando lista de assinaturas no SharedPreference
			SharedPreferences.Editor editor = loggedUser.edit();

			String signaturesString = SessionControl
					.encodeSessionCompaniesUser(signatures);

			editor.putString(SessionControl.COMPANIES_USERS, signaturesString);
			editor.commit();

			// carregando lista do adapter
			pbSignatures.setVisibility(View.GONE);
			pbLoadList.setVisibility(View.GONE);
			tvLoadList.setVisibility(View.GONE);
			adapter = new SignaturesAdapter(SignaturesActivity.this, signatures);
			lvSignatures.setAdapter(adapter);
		}
	}

	/**
	 * Captura o retorno da busca de empresas por nome e recebe uma lista
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * 
	 */
	public class CompanySearchResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_COMPANY_SEARCH = "com.mamlambo.intent.action.MESSAGE_PROCESSED_COMPANY_SEARCH";

		@Override
		public void onReceive(Context context, Intent intent) {

			// recurando lista de assisnaturas capturada no service
			Bundle parameters = intent.getExtras();
			companies = (List<Company>) parameters
					.getSerializable(SignaturesSearchService.PARAM_COMPANIES_LIST);

			// INSERIR ADAPTER NA LISTA
			searchAdapter = new SingnaturesCompanyAdapter(
					SignaturesActivity.this, companies);
			lvSearch.setAdapter(searchAdapter);
			pbSignatures.setVisibility(View.GONE);
			lvSearch.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * Service que carrega a quantidade de convites em espera
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * 
	 */
	public class InvitationsCountResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_INVITE_COUNT = "com.mamlambo.intent.action.MESSAGE_PROCESSED_INVITE_COUNT";

		@Override
		public void onReceive(Context context, Intent intent) {

			int invitatios = intent.getIntExtra(
					CheckInvitationsService.PARAM_OUT_INVITATIONS, 0);

			if (invitatios != 0) {
				btnQtdInvitations.setVisibility(View.VISIBLE);
				btnQtdInvitations.setText(String.valueOf(invitatios));
			} else {
				btnQtdInvitations.setVisibility(View.GONE);
			}

		}
	}

	/**
	 * Service que assina empresa
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 * 
	 */
	public class SignCompResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_SIGN_COMP = "com.mamlambo.intent.action.MESSAGE_PROCESSED_SIGN_COMP";

		@Override
		public void onReceive(Context context, Intent intent) {

			SharedPreferences.Editor editor = loggedUser.edit();
			editor.remove(SessionControl.COMPANIES_USERS);
			editor.commit();

			searchAdapter = new SingnaturesCompanyAdapter(
					SignaturesActivity.this, companies);
			lvSearch.setAdapter(searchAdapter);
			pbSignatures.setVisibility(View.GONE);
			Toast.makeText(SignaturesActivity.this, "Empressa Assinada!",
					Toast.LENGTH_SHORT).show();

		}
	}

	/**
	 * 
	 * Carrega todas ofertas ativas da company sem perfil
	 * 
	 * @author Matheus Odilon - accentialbrasil
	 */
	public class CompaniesOfferResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_COMP_OFFERS = "com.mamlambo.intent.action.MESSAGE_PROCESSED_COMP_OFFERS";

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();
			List<Offer> offers = (List<Offer>) parameters
					.getSerializable(CompaniesOfferService.PARAM_OUT_OFFERS);

			if (offers.isEmpty()) {
				tvQtdAllOffers.setText("0");
			} else {
				tvQtdAllOffers.setText(String.valueOf(offers.size()));
			}

			pbCompAllOffer.setVisibility(View.GONE);
			tbCompAllOffer.setVisibility(View.VISIBLE);
		}
	}

	public class CompOffersProfileResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP_OFFERS_MY_PROFILE = "com.mamlambo.intent.action.MESSAGE_PROCESSED_COMP_MY_OFFERS";

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();
			List<Offer> myOffers = ((List<Offer>) parameters
					.getSerializable(CompaniesOfferProfileService.PARAM_OUT_OFFERS));

			Log.e("", "Checgou no receiver!!!");

			if (myOffers.isEmpty()) {
				tvQtdMyOffer.setText("0");
			} else {
				tvQtdMyOffer.setText("Essa empresa possui "
						+ String.valueOf(myOffers.size())
						+ " ofertas para o seu perfil.");
			}
			pbCompMyOffer.setVisibility(View.GONE);
			tbCompMyOffer.setVisibility(View.VISIBLE);
		}
	}
}
