package com.accential.trueone.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.View;
import android.widget.TextView;

import com.accential.trueone.bean.Offer;

public class FiltroDeCores {

	private Map<String, String> todasAsCores;
	private Map<String, TextView> todosOsTextViews;
	private Map<String, View> todasAsViews;

	public FiltroDeCores(Map<String, View> todasAsViews) {
		this.todasAsViews = todasAsViews;
	}

	public List<String> filtraCorPara(Offer oferta){

		String arrayCarac = oferta.getMetrics();
		List<String> coresParaMostrar = new ArrayList<String>();
		String[] cores = new String[20];

		String teste = arrayCarac.replace(":", "").
				replace("}", "").replace("{", "").replace("[", "").replace("]", "").replace(" ", "").replace("\"", "");
		StringBuilder stringBuilder = new StringBuilder(teste);  
		stringBuilder.insert(3, ','); 

		cores = stringBuilder.toString().split(",");

		for(String cor : cores) {
			if(todasAsCores.containsKey(cor)) {
				coresParaMostrar.add(todasAsCores.get(cor));

			}
		}

		return coresParaMostrar;
	}

	public List<View> filtraCorTextView(Offer oferta){

		String arrayCarac = oferta.getMetrics();
		List<View> viewsParaMostrar = new ArrayList<View>(); 
		String[] cores = new String[20];

		String teste = arrayCarac.replace(":", "").
				replace("}", "").replace("{", "").replace("[", "").replace("]", "").replace(" ", "").replace("\"", "");
		StringBuilder stringBuilder = new StringBuilder(teste);  
		stringBuilder.insert(3, ','); 

		cores = stringBuilder.toString().split(",");

		for (String string : cores) {
			if(todasAsViews.containsKey(string)){
				viewsParaMostrar.add(todasAsViews.get(string));
			}
		}
		return viewsParaMostrar;
	}

}
