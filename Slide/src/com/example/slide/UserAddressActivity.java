package com.example.slide;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.accential.trueone.bean.User;
import com.accential.trueone.bo.UserBO;
import com.accential.trueone.utils.Mask;
import com.accential.trueone.utils.OrientacaoUtils;
import com.accential.trueone.utils.SessionControl;
import com.loopj.android.image.SmartImageView;

public class UserAddressActivity extends Activity {

	private EditText etAddress;
	private EditText etZipCode;
	private EditText etNumber;
	private EditText etDistrict;
	private EditText etCity;
	private EditText etComplement;
	private Spinner spUf;
	private Button btnSave;
	private Button btnCancel;
	private TextView tvTitle;
	private SmartImageView userPhoto;
	// private ImageView imgIconUser;

	private ArrayAdapter<String> adapter;
	private SharedPreferences loggedUser;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.x_activity_users_address);

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

		etAddress = (EditText) findViewById(R.id.etUserAddress);
		etZipCode = (EditText) findViewById(R.id.etUserCep);
		etNumber = (EditText) findViewById(R.id.etUserNumber);
		etDistrict = (EditText) findViewById(R.id.etUserDistrict);
		etCity = (EditText) findViewById(R.id.etUserCity);
		etComplement = (EditText) findViewById(R.id.etUserComplement);
		spUf = (Spinner) findViewById(R.id.spUserUf);
		btnSave = (Button) findViewById(R.id.btnUserAddressSave);
		btnCancel = (Button) findViewById(R.id.btnUserAddressCancel);
		tvTitle = (TextView) findViewById(R.id.textView1);

		userPhoto = (SmartImageView) findViewById(R.id.ivUserPhoto);

		// imgIconUser = (ImageView) findViewById(R.id.imageView1);

		Typeface font = Typeface.createFromAsset(getAssets(),
				"helvetica-35-thin-1361522141.ttf");
		tvTitle.setTypeface(font);

		// mascara cep
		etZipCode.addTextChangedListener(Mask.insert("#####-###", etZipCode));

		loggedUser = this.getSharedPreferences(SessionControl.USER_LOGGED, 0);
		// recupera usuario logado
		user = SessionControl.decodeSessionUser(loggedUser.getString(
				SessionControl.USER, null));

		if (user.getState() != null) {
			String state = user.getState();
			String[] estados = { state, "AC", "AL", "AP", "AM", "BA", "CE",
					"DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PR",
					"PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE",
					"TO" };
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, estados);
			spUf.setAdapter(adapter);
		}

		etAddress.setText(user.getAddress());
		etCity.setText(user.getCity());
		etComplement.setText(user.getComplement());
		etDistrict.setText(user.getDistrict());
		etNumber.setText(user.getNumber());
		etZipCode.setText(user.getZip_code());
		userPhoto.setImageUrl(user.getPhoto());

		// clique no botão salvar
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				User saveUser = new User();

				saveUser.setId(user.getId());
				saveUser.setAddress(etAddress.getText().toString());
				saveUser.setCity(etCity.getText().toString());
				saveUser.setComplement(etComplement.getText().toString());
				saveUser.setDistrict(etDistrict.getText().toString());
				saveUser.setNumber(etNumber.getText().toString());
				saveUser.setZip_code(etZipCode.getText().toString());
				saveUser.setState((String) spUf.getSelectedItem());

				UserBO.saveAddressData(saveUser);
				Toast.makeText(
						UserAddressActivity.this,
						"Os dados do seu endereço foram atualizados com sucesso.",
						Toast.LENGTH_SHORT).show();

				user.setAddress(saveUser.getAddress());
				user.setCity(saveUser.getCity());
				user.setComplement(saveUser.getComplement());
				user.setDistrict(saveUser.getDistrict());
				user.setNumber(saveUser.getNumber());
				user.setState(saveUser.getState());
				user.setZip_code(saveUser.getZip_code());

				String stringUser = SessionControl.encodeSessionUser(user);
				SharedPreferences.Editor editor = loggedUser.edit();
				editor.remove(SessionControl.USER);
				editor.commit();
				editor.putString(SessionControl.USER, stringUser);
				editor.commit();

			}
		});

		// clique no botão cancelar
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// voltamos para tela anterior
				Intent intent = new Intent(UserAddressActivity.this,
						UserActivity.class);
				startActivity(intent);
			}
		});

		// clique no icone de usuario
		// imgIconUser.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(UserAddressActivity.this,
		// UserActivity.class);
		// startActivity(intent);
		// }
		// });
	}
}
