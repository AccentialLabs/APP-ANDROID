package com.example.slide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.bean.CompanyCategory;
import com.accential.trueone.bean.CompanySubCategory;
import com.accential.trueone.bean.User;
import com.accential.trueone.bean.Wishlist;
import com.accential.trueone.bo.CompanySubCategoryBO;
import com.accential.trueone.bo.WishlistBO;
import com.accential.trueone.service.WishlistService;
import com.accential.trueone.utils.Mask;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;

@SuppressWarnings("unused")
public class WishlistActivity extends Activity {

	private EditText etName;
	private EditText etDesc;
	private EditText etEnds;
	private TextView tvTitle;
	private Spinner spinCateg;
	private Spinner spinSubCateg;
	private Button btnSaveWish;
	private List<CompanyCategory> categories;
	private WishlistResponseReceiver receiver;
	private SharedPreferences loggedUser;
	private User user;
	private ProgressBar progressCateg;
	private ProgressBar progressSubCateg;
	private CompanyCategory selectedCategory;

	private ImageView imgWish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_new_wishlist);

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

		// recuperando usuario logado na "sessao"
		loggedUser = getSharedPreferences(SessionControl.USER_LOGGED, 0);
		user = SessionControl.decodeSessionUser(loggedUser.getString("user",
				null));

		etName = (EditText) findViewById(R.id.etName);
		etDesc = (EditText) findViewById(R.id.etDescription);
		etEnds = (EditText) findViewById(R.id.etEnds);
		spinCateg = (Spinner) findViewById(R.id.spinCategory);
		spinSubCateg = (Spinner) findViewById(R.id.spinSubCategory);
		btnSaveWish = (Button) findViewById(R.id.btnSaveWish);
		progressCateg = (ProgressBar) findViewById(R.id.progressCateg);
		progressSubCateg = (ProgressBar) findViewById(R.id.progressSubCateg);
		tvTitle = (TextView) findViewById(R.id.textView1);
		imgWish = (ImageView) findViewById(R.id.imageView1);

		Typeface font = Typeface.createFromAsset(getAssets(),
				"helvetica-35-thin-1361522141.ttf");
		tvTitle.setTypeface(font);
		btnSaveWish.setTypeface(font);

		ImageView userImg = (ImageView) findViewById(R.id.imageView2);

		// clique na imagem do usuario
		userImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WishlistActivity.this,
						UserActivity.class);
				startActivity(intent);
			}
		});

		// startando service
		Intent categService = new Intent(WishlistActivity.this,
				WishlistService.class);
		startService(categService);
		Log.e("", "Executando service 1");

		// registrando o receiver do service
		IntentFilter filter = new IntentFilter(
				WishlistResponseReceiver.ACTION_RESPOSTA);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new WishlistResponseReceiver();
		registerReceiver(receiver, filter);

		// mascara para o campo data
		etEnds.addTextChangedListener(Mask.insert("##/##/####", etEnds));

		// onclick no botao cadastrar
		// salva wishlist
		btnSaveWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				// capturando objs das spinners Categoria e SubCategoria
				CompanyCategory category = (CompanyCategory) spinCateg
						.getSelectedItem();

				CompanySubCategory subcategory = (CompanySubCategory) spinSubCateg
						.getSelectedItem();

				// convertendo data
				Calendar dateNow = Calendar.getInstance();
				Calendar dateLimit = Calendar.getInstance();

				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

				String dataFinal = etEnds.getText().toString();
				Date date = null;
				try {
					date = formatador.parse(dataFinal);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dateLimit.setTime(date);

				//
				Wishlist wishlist = new Wishlist();
				wishlist.setCategory(category);
				wishlist.setSubCategory(subcategory);
				wishlist.setUser(user);
				wishlist.setName(etName.getText().toString());
				wishlist.setDescription(etDesc.getText().toString());
				wishlist.setDateRegister(dateNow);
				wishlist.setEndsAt(dateLimit);

				Log.e("", "Executar save Wish");
				WishlistBO.saveMyWishlist(wishlist);
				Toast.makeText(WishlistActivity.this,
						"Seu desejo foi cadastrado com sucesso!",
						Toast.LENGTH_SHORT).show();

				// nós apagamos aqui o registro da lista no Shared para podermos
				// atualizar a lista quando voltarmos para wishHome
				SharedPreferences.Editor editor = loggedUser.edit();
				editor.remove(SessionControl.WISHLIST_USERS);
				editor.commit();

				Intent intent = new Intent(WishlistActivity.this,
						WishHomeActivity.class);
				startActivity(intent);
			}
		});

		// seleciona categoria
		spinCateg.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {

				selectedCategory = (CompanyCategory) parent.getSelectedItem();

				List<CompanySubCategory> subCategories = CompanySubCategoryBO
						.getByCategoryId(selectedCategory.getId());
				ArrayAdapter<CompanySubCategory> subCategoryAdapter = new ArrayAdapter<CompanySubCategory>(
						WishlistActivity.this, R.layout.spinner_personal_item,
						subCategories);
				spinSubCateg.setAdapter(subCategoryAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapter) {

			}
		});

		// clique na imagem do coração
		imgWish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WishlistActivity.this,
						WishHomeActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class WishlistResponseReceiver extends BroadcastReceiver {
		public static final String ACTION_RESPOSTA = "com.mamlambo.intent.action.MESSAGE_PROCESSED_WISHLIST";

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle parameters = intent.getExtras();

			@SuppressWarnings("unchecked")
			List<CompanyCategory> categorias = (List<CompanyCategory>) parameters
					.getSerializable(WishlistService.PARAM_OUT_CATEGORIE);

			@SuppressWarnings("unchecked")
			List<CompanySubCategory> subCategorias = (List<CompanySubCategory>) parameters
					.getSerializable(WishlistService.PARAM_OUT_SUB_CATEGORIE);

			// jogando lista de categorias para o spinnerview
			// ArrayAdapter<CompanyCategory> categoryAdapter = new
			// ArrayAdapter<CompanyCategory>(
			// WishlistActivity.this,
			// android.R.layout.simple_spinner_item, categorias);
			ArrayAdapter<CompanyCategory> categoryAdapter = new ArrayAdapter<CompanyCategory>(
					WishlistActivity.this, R.layout.spinner_personal_item,
					categorias);
			spinCateg.setAdapter(categoryAdapter);

			// jogando lista de subcategorias para o spinnerview
			ArrayAdapter<CompanySubCategory> subcategoryAdapter = new ArrayAdapter<CompanySubCategory>(
					WishlistActivity.this, R.layout.spinner_personal_item,
					subCategorias);
			spinSubCateg.setAdapter(subcategoryAdapter);

			spinCateg.setVisibility(View.VISIBLE);
			spinSubCateg.setVisibility(View.VISIBLE);
			progressCateg.setVisibility(View.INVISIBLE);
			progressSubCateg.setVisibility(View.INVISIBLE);

			Log.e("", "FIM DO SERVICE");
		}
	}

}