package com.accential.trueone.utils;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.accential.trueone.adapter.SignaturesLandAdapter;
import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bo.CompaniesUserBO;
import com.loopj.android.image.SmartImageView;

public class MinhaTask extends AsyncTask<Object, Object, String>{

	private ProgressBar progressBar;
	private TextView texto;
	private int total = 0;
	private static int PROGRESSO = 25;
	private SmartImageView imagem;
	private List<CompaniesUser> compsUser;
	private SignaturesLandAdapter adapter;
	private ListView lista;


	public MinhaTask(Context context, ProgressBar progressBar, SignaturesLandAdapter adapter,
			List<CompaniesUser> compsUser, ListView lista) {
		this.progressBar = progressBar;
		this.adapter = adapter;
		this.compsUser = compsUser;
		this.lista = lista;
	}

	@Override
	protected void onPreExecute() {
		List<CompaniesUser> compsUser = CompaniesUserBO.returnObjCompaniesUser(285);
		lista.setAdapter(adapter);
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(Object... params) {
		try {

			Thread.sleep(1000);

			for (int i=0; i<4; i++) {
				publishProgress();
				Thread.sleep(1000);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		total += PROGRESSO;
		progressBar.incrementProgressBy(PROGRESSO);
		texto.setText(total + "%");

		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {            
		progressBar.setVisibility(ProgressBar.INVISIBLE);
		super.onPostExecute(result);
	}
}