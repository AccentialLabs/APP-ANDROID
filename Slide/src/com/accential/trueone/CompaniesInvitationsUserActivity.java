package com.accential.trueone;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.accential.trueone.adapter.CompsInviteUserAdapter;
import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bo.CompaniesInvitationsUserBO;
import com.example.slide.R;

/**
 * Exibi lista de Convites - De Company para User
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class CompaniesInvitationsUserActivity extends Activity{

	private TextView textViewText;
	private TextView textViewFraseCount;
	private ListView list;
	private CompsInviteUserAdapter compsAdapter;
	//MENU
	private ImageView imageMenuWish;
	private TextView textMenuWish;
	private ImageView imageMenuCompras;
	private TextView textMenuCompras;
	private ImageView imageMenuAssinaturas;
	private TextView textMenuAssinaturas;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_companies_invite);

		loadViews();

		List<CompaniesInvitationsUser> invites = CompaniesInvitationsUserBO.returnObjsCompInviteUser(130);

		if(invites.isEmpty()){
			list.setVisibility(View.GONE);
			textViewFraseCount.setText("Vo�� n��o tem novos convites");
		}else{
			textViewFraseCount.setText("Voc�� tem ".concat(String.valueOf(invites.size())).concat(" convite(s) esperando confirma����o!"));
			textViewText.setVisibility(View.GONE);
			compsAdapter = new CompsInviteUserAdapter(invites, this);
			list.setAdapter(compsAdapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void loadViews(){

		textViewText = (TextView) findViewById(R.id.textView_compInviteNotFound);
		list = (ListView) findViewById(R.id.listView_compInvite);
		textViewFraseCount = (TextView) findViewById(R.id.textView_compInviteCountTitle);
		//MENU
		imageMenuWish = (ImageView) findViewById(R.id.imageFooterWish);
		textMenuWish = (TextView) findViewById(R.id.textViewFooterWish);
		imageMenuCompras = (ImageView) findViewById(R.id.imageFooterCompras);
		textMenuCompras = (TextView) findViewById(R.id.textViewFooterCompras);
		imageMenuAssinaturas = (ImageView) findViewById(R.id.imageFooterAssinaturas);
		textMenuAssinaturas = (TextView) findViewById(R.id.textViewFooterAssinaturas);

		imageMenuAssinaturas.setImageResource(R.drawable.assinaturas_on);
		imageMenuWish.setImageResource(R.drawable.wishlist_on);
		imageMenuCompras.setImageResource(R.drawable.compras_on);

	}


}
