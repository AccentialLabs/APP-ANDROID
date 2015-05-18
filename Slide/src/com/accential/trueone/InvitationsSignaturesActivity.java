package com.accential.trueone;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.accential.trueone.adapter.CompaniesUserAdapter;
import com.accential.trueone.adapter.CompsInviteUserAdapter;
import com.accential.trueone.bean.CompaniesInvitationsUser;
import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bo.CompaniesInvitationsUserBO;
import com.accential.trueone.bo.CompaniesUserBO;
import com.example.slide.R;



public class InvitationsSignaturesActivity extends Activity{

	//ASSINATURAS
	private CompaniesUserAdapter compUserAdapter;
	private ListView listSignatures;
	private TextView textViewTitle;
	private EditText editTextCompaniesSearch;
	//CONVITES
	private TextView textViewText;
	private TextView textViewFraseCount;
	private ListView listInvitations;
	private CompsInviteUserAdapter compsAdapter;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invitations_signatures);
		
		textViewText = (TextView) findViewById(R.id.textView_compInviteNotFound);
		listInvitations = (ListView) findViewById(R.id.listView_compInvite);
		textViewFraseCount = (TextView) findViewById(R.id.textView_compInviteCountTitle);
		loadViews();
		
		//COME��A VIEWS E L��GICA DE ASSINATURAS
		List<CompaniesInvitationsUser> invites = CompaniesInvitationsUserBO.returnObjsCompInviteUser(285);
		List<CompaniesUser> companies = CompaniesUserBO.returnObjCompaniesUser(261);
		
		Log.i("TESTE", String.valueOf(companies.size()));
		textViewTitle.setText("Voc�� est�� recebendo ofertas de ".concat(String.valueOf(companies.size())).concat(" empresas"));

		compUserAdapter = new CompaniesUserAdapter(companies, this);
		listSignatures.setAdapter(compUserAdapter);
		//TERMINA VIEWS E L��GICA DE ASSINATURAS
		
		//COME��A VIEWS E L��GICA DE CONVITES
		
		Log.i("TESTE SEGUNDA LISTA", String.valueOf(invites.size()));
		
		if(invites.isEmpty()){
			listInvitations.setVisibility(View.GONE);
			textViewFraseCount.setText("Vo�� n��o tem novos convites");
		}else{
			textViewFraseCount.setText("Voc�� tem ".concat(String.valueOf(invites.size())).concat(" convite(s) esperando confirma����o!"));
			textViewText.setVisibility(View.GONE);
			compsAdapter = new CompsInviteUserAdapter(invites, this);
			listInvitations.setAdapter(compsAdapter);
		}
		//TERMINA VIEWS E L��GICA DE CONVITES

		TabHost tabs=(TabHost)findViewById(R.id.tabhost); 
		tabs.setup(); 

		TabHost.TabSpec spec = tabs.newTabSpec("tag1"); 
		spec.setContent(R.id.linearLayout_listCompaniesSignatures); 
		spec.setIndicator("Assinaturas"); 
		tabs.addTab(spec); 

		spec = tabs.newTabSpec("tag2"); 
		spec.setContent(R.id.linearLayout_companiesInvitations); 
		spec.setIndicator("Convites"); 
		tabs.addTab(spec); 

		tabs.setCurrentTab(10); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void searchCompanyByName(View v){
		String compName = editTextCompaniesSearch.getText().toString();
		List<CompaniesUser> comps = new ArrayList<CompaniesUser>();
		comps = CompaniesUserBO.searchByName(compName);

		compUserAdapter = new CompaniesUserAdapter(comps, this);
		listSignatures.setAdapter(compUserAdapter);
	}

	public void loadViews(){

		//ASSINATURES
		editTextCompaniesSearch  = (EditText) findViewById(R.id.editText_CompaniesSearch);
		listSignatures = (ListView) findViewById(R.id.listView_compSignatures);
		textViewTitle = (TextView) findViewById(R.id.textView_companiesCountTitle);

		//CONVITES
		

	}
}
