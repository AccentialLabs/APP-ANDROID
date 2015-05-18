package com.accential.trueone.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class DownImageUtil {

	public Drawable getImagem() throws Exception {
		URL url = new URL("https://www.filepicker.io/api/file/iT5G8zWlRZmszI4Qpemw");

		InputStream is = (InputStream) getObjeto(url);
		Drawable d = Drawable.createFromStream(is, "src");
		
		Log.i("VALOR DRAWABLE", String.valueOf(d));
		
		return d;
	}

	private Object getObjeto(URL url) throws MalformedURLException, IOException {
		Object content = url.getContent();
		return content;
	}
	
	public static Bitmap testePartionBitmap(Bitmap bitmap){
		
		Bitmap outroBitmap = Bitmap.createBitmap(bitmap, 10, 10, 100, 100);
		return outroBitmap;
	}

}
