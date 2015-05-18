package com.accential.trueone.adapter;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.accential.trueone.bean.Wishlist;
import com.example.slide.R;

/**
 * Infla uma lista com todos desejos de um determinado usuario
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class WishlistLandAdapter extends BaseAdapter {

	private List<Wishlist> wishies;
	private LayoutInflater mInflater;
	private Map myMap;
	//teste do botao excluir
	private ImageView btnExcluir;

	public WishlistLandAdapter(Context context, List<Wishlist> wishies, Map myMap){
		this.wishies = wishies;
		this.myMap = myMap;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return wishies.size();
	}

	@Override
	public Object getItem(int position) {
		return wishies.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		Wishlist wishlist = wishies.get(position);

		view = mInflater.inflate(R.layout.activity_item_wishlist, null);

		//TESTE DO BOTAO EXCLUIR
		btnExcluir = (ImageView) view.findViewById(R.id.imageView3);
		btnExcluir.setTag(position);
		//--

		((TextView) view.findViewById(R.id.textView_productName)).setText(wishlist.getName());

		if(wishlist.getDescription().equals("")){
			((TextView) view.findViewById(R.id.textView6)).setText("Desejo sem descri����o!");
		}else{
			((TextView) view.findViewById(R.id.textView6)).setText(wishlist.getDescription());
		}

		//TIRAMOS O NOME PELA MUDANCA DE LAYOUT - Para uso futuro deixarei comentado o campo em activity_item_wishlist.xml
		//((TextView) view.findViewById(R.id.textView_userName)).setText(wishlist.getUser().getName());

		((TextView) view.findViewById(R.id.textView_wishDatSolicitacao)).setText(
				String.valueOf( wishlist.getDateRegister().get(Calendar.DAY_OF_MONTH)) + "/"
						+ (wishlist.getDateRegister().get(Calendar.MONTH) + 1) + "/" + wishlist.getDateRegister().get(Calendar.YEAR));

		if(wishlist.getEndsAt() != null){
			((TextView) view.findViewById(R.id.textView_wishDatLimite)).setText(
					String.valueOf( wishlist.getEndsAt().get(Calendar.DAY_OF_MONTH)) + "/"
							+ (wishlist.getEndsAt().get(Calendar.MONTH) + 1) + "/" + wishlist.getEndsAt().get(Calendar.YEAR));
		}else{
			((TextView) view.findViewById(R.id.textView_wishDatLimite)).setText("--/--/----");
		}

		/**
		 * QUANTIDADE DE OFERTAS QUE O DESEJO CONTEM 
		 * MAPA COM CHAVE STRING (NOME/TITULO DO DESEJO)
		 * RETORNO INT (QUANTIDADE DE OFERTAS RELACIONADAS)
		 */
		((TextView) view.findViewById(R.id.textView_qtdOffers)).setText(String.valueOf(myMap.get(wishlist.getName())));

		return view;
	}

}
