package com.accential.trueone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.accential.trueone.adapter.WishlistAdapter;
import com.accential.trueone.bean.User;
import com.accential.trueone.bean.Wishlist;
import com.accential.trueone.bo.WishlistBO;
import com.example.slide.R;
/**
 * 
 * @author Matheus Odilon - accentialbrasil
 *
 */
@SuppressWarnings("all")
public class WishlistActivity extends Activity{

	private ListView list;
	private WishlistAdapter wAdapter;
	private ImageView imageView;

	//VIEWS DE USER
	private int userId;
	private TextView textViewNameUser;
	//private ImageView imageViewPhotoUser;
	//private ProgressBar progress;
	private Intent intentInclude;

	private Button buttonNewWish;

	private ImageView imageMenuCompras;
	private TextView textMenuCompras;
	private ImageView imageMenuAssinaturas;
	private TextView textMenuAssinaturas;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);								
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail_wishlist);		

		list = (ListView) findViewById(R.id.listWish);
		imageView = (ImageView) findViewById(R.id.imageFooterWish);
		buttonNewWish = (Button) findViewById(R.id.button_includeWish);

		imageView.setImageResource(R.drawable.wishlist_off);

		imageMenuCompras = (ImageView) findViewById(R.id.imageFooterCompras);
		textMenuCompras = (TextView) findViewById(R.id.textViewFooterCompras);
		imageMenuAssinaturas = (ImageView) findViewById(R.id.imageFooterAssinaturas);
		textMenuAssinaturas = (TextView) findViewById(R.id.textViewFooterAssinaturas);

		imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_on);
		imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_on);
		imageMenuCompras.setImageResource(R.drawable.compras_on);

		//VIEWS DE USUARIO
		//progress = (ProgressBar) findViewById(R.id.progressBarViewHeader);
		textViewNameUser = (TextView) findViewById(R.id.textViewHeader_NomeUsuario);
		//imageViewPhotoUser = (ImageView) findViewById(R.id.imageViewHeader);

		intentInclude = new Intent(this, WishlistInclude.class);

		//RECEBENDO OBJ USER PASSADO PELA INTENT /MAINACTIVITY
		Intent intent = getIntent();
		//RECEBENDO USER PASSADO PELA INTENT
		User u = (User) getIntent().getSerializableExtra("user");
		intentInclude.putExtra("user", u);

		//INTENT - "LINK" PARA ACTIVITY DE COMPRAS - CHECKOUTS
		final Intent intentCompras = new Intent(this, CheckoutActivity.class);
		final Intent intentAssinaturas = new Intent(this, CompaniesUserActivity.class);
		intentAssinaturas.putExtra("user", u);
		intentCompras.putExtra("user", u);

		//SETANDO ATRIBUTOS SEGUNDO USER RECEBIDO
		textViewNameUser.setText(u.getName().toUpperCase());
		//DownloadImagemUtil downPhoto = new DownloadImagemUtil(this);
		//downPhoto.download(this, u.getPhoto(), imageViewPhotoUser, progress);

		userId = u.getId();
		Log.i("ID USER", String.valueOf(userId));
		Map<String, Map<String, Map<String, String>>> key = new HashMap<String,Map<String, Map<String, String>>>();
		Map<String,Map<String,String>> params = new HashMap<String,Map<String,String>>();
		Map<String,String> conditions = new HashMap<String,String>();

		conditions.put("UsersWishlist.user_id", String.valueOf(userId));
		params.put("conditions", conditions);
		key.put("UsersWishlist", params);

		List<Wishlist> listaDeDesejos = WishlistBO.retornaObj(285);

		wAdapter = new WishlistAdapter(listaDeDesejos, this);
		list.setAdapter(wAdapter);
		Log.i("TAMANHO LISTA OBJ", String.valueOf(listaDeDesejos.size()));


		//CLIQUE - MENU COMPRAS
		textMenuCompras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intentCompras);
			}
		});

		//CLIQUE - MENU ASSINATURAS
		textMenuAssinaturas.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_off);
				startActivity(intentAssinaturas);	
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void createWish(View v){
		startActivity(intentInclude);
	}


}
