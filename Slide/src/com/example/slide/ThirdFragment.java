package com.example.slide;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.accential.trueone.adapter.SignaturesLandAdapter;
import com.accential.trueone.bean.CompaniesUser;
import com.accential.trueone.bo.CompaniesUserBO;

public class ThirdFragment extends Fragment {

	private List<CompaniesUser> singnedComps;
	private SignaturesLandAdapter adapter;
	private ListView signListView;
	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.third_frag, container, false);

		TextView tv = (TextView) v.findViewById(R.id.tvFragThird);
		signListView = (ListView) v.findViewById(R.id.signaturesListView);
		tv.setText(getArguments().getString("msg"));

		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					singnedComps = CompaniesUserBO
							.returnObjCompaniesUser(308);
					Log.e("empresas",
							"ASSINATURAS: "
									+ String.valueOf(singnedComps.size()));
					adapter = new SignaturesLandAdapter(v.getContext(),
							singnedComps);

				} catch (Exception e) {
					e.printStackTrace();

				}
			}

		};

		//thread.start();
		//signListView.setAdapter(adapter);

		return v;
	}

	public static ThirdFragment newInstance(String text) {

		ThirdFragment f = new ThirdFragment();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;
	}

}
