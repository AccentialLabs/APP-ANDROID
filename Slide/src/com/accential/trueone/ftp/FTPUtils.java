package com.accential.trueone.ftp;

import java.io.File;

import org.jibble.simpleftp.SimpleFTP;

import android.os.AsyncTask;
import android.util.Log;

public class FTPUtils {

	public static void conectar() {
		Log.e("", "FTP PASSO 1");

		AsyncTask<Void, Void, Void> async = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {

					SimpleFTP ftp = new SimpleFTP();

					ftp.connect("ftp.trueone.com.br", 21, "public",
							"8I%Mz@2mRQdt");

					Log.e("", "FTP PASSO 2");

					ftp.bin();

					Log.e("", "FTP PASSO 3");

					ftp.cwd("/uploads/users");

					Log.e("", "FTP PASSO 4");

					ftp.stor(new File("testeAndroid.jpg"));

					Log.e("", "FTP PASSO 5");

					ftp.disconnect();

					Log.e("", "FTP PASSO 6");

				} catch (Exception e) {
					Log.e("error", "ERROR NO FTP !!!!!");
					e.printStackTrace();
				}

				return null;
			}

		};

		try {
			async.execute();
		} catch (Exception e) {

		}

	}

}
