package com.example.slide;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.bean.User;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.utils.Mask;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;
import com.loopj.android.image.SmartImageView;

@SuppressWarnings("all")
public class UserActivity extends Activity {

	private EditText etName;
	private EditText etEmail;
	private EditText etBirth;
	private Button btnSave;
	private Button btnCancel;
	private Button btnAddress;
	private Button btnLogout;
	private Spinner spGender;
	private ArrayAdapter<String> adapter;
	private SmartImageView ivPhoto;

	private SharedPreferences loggedUser;
	private User user;
	private TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_users);

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

		loggedUser = this.getSharedPreferences(SessionControl.USER_LOGGED, 0);
		// recupera usuario logado
		user = SessionControl.decodeSessionUser(loggedUser.getString(
				SessionControl.USER, null));

		// iniciando Garbage collector
		try {
			System.gc();
		} catch (Exception e) {
			Log.e("Error AdventaActivity",
					"Erro ao iniciar garbage colector - linha 61");
			e.printStackTrace();
		}

		String[] listGender = { "Selecione", "Feminino", "Masculino" };

		etEmail = (EditText) findViewById(R.id.etUserEmail);
		etName = (EditText) findViewById(R.id.etUserName);
		spGender = (Spinner) findViewById(R.id.spUserGender);
		ivPhoto = (SmartImageView) findViewById(R.id.ivUserPhoto);
		etBirth = (EditText) findViewById(R.id.etUserBirth);
		btnSave = (Button) findViewById(R.id.btnUserSave);
		btnCancel = (Button) findViewById(R.id.btnUserCancel);
		btnAddress = (Button) findViewById(R.id.btnUserAddress);
		btnLogout = (Button) findViewById(R.id.btnUserLogout);
		tvTitle = (TextView) findViewById(R.id.textView1);

		Typeface font = Typeface.createFromAsset(getAssets(),
				"helvetica-35-thin-1361522141.ttf");
		tvTitle.setTypeface(font);

		// carregando foto
		ivPhoto.setImageUrl(user.getPhoto());

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listGender);
		spGender.setAdapter(adapter);

		// logica para mostrar gender
		if (user.getGender().equals("male")) {
			spGender.setSelection(2);
		} else if (user.getGender().equals("female")) {
			spGender.setSelection(1);
		}

		etEmail.setText(user.getEmail());
		etName.setText(user.getName());
		etBirth.addTextChangedListener(Mask.insert("##/##/####", etBirth));
		// validando brithdy
		if (user.getBirthday() == null) {

		} else {
			// pog para formatar a data
			int mes = user.getBirthday().get(Calendar.MONTH);
			String birthMes = String.valueOf(mes + 1);
			if (birthMes.length() == 1) {
				birthMes = String.valueOf(0) + birthMes;
			}
			String birth = String.valueOf(user.getBirthday().get(
					Calendar.DAY_OF_MONTH)
					+ "/"
					+ birthMes
					+ "/"
					+ user.getBirthday().get(Calendar.YEAR));

			etBirth.setText(birth);
		}

		// clique no botao save
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				salvarDadosPrincipais();
			}
		});

		// clique no botao cancelar
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserActivity.this,
						AdventaActivity.class);
				startActivity(intent);
			}
		});

		// clique no botao endereco
		btnAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(UserActivity.this,
						UserAddressActivity.class);
				startActivity(intent);
			}
		});

		// clique no botao sair
		// para mais detalhes ler documentaçao da classe SessionControl
		btnLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SessionControl.logoof(v.getContext(), loggedUser);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("SimpleDateFormat")
	public void salvarDadosPrincipais() {

		User saveUser = new User();

		int idGender = spGender.getSelectedItemPosition();
		String gender = "";

		if (idGender == 1) {
			gender = "female";
		} else if (idGender == 2) {
			gender = "male";
		}

		try {
			// convertendo data de nascimento - String to Calendar
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(etBirth.getText().toString()));

			saveUser.setId(user.getId());
			saveUser.setGender(gender);
			saveUser.setName(etName.getText().toString());
			saveUser.setEmail(etEmail.getText().toString());
			saveUser.setBirthday(cal);

			UserBO.saveMainData(saveUser);

			user.setGender(gender);
			user.setName(etName.getText().toString());
			user.setEmail(etEmail.getText().toString());
			user.setBirthday(cal);

			String stringUser = SessionControl.encodeSessionUser(user);
			SharedPreferences.Editor editor = loggedUser.edit();
			editor.remove(SessionControl.USER);
			editor.commit();
			editor.putString(SessionControl.USER, stringUser);
			editor.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		Toast.makeText(UserActivity.this,
				"Seus Dados foram salvos com sucesso!", Toast.LENGTH_SHORT)
				.show();
	}

}
