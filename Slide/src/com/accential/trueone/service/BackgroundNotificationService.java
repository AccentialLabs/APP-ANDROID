package com.accential.trueone.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.slide.AdventaActivity;
import com.example.slide.CarrousselActivity;
import com.example.slide.R;

@SuppressWarnings("all")
/**
 * Service responsavel por mostrar notificacoes in background
 * @author Matheus Odilon - accentialbrasil
 *
 */
public class BackgroundNotificationService extends Service {

	private NotificationManager mNM;
	Bundle b;
	Intent notificationIntent;
	private final IBinder mBinder = new LocalBinder();
	private String newtext;

	public class LocalBinder extends Binder {
		BackgroundNotificationService getService() {
			return BackgroundNotificationService.this;
		}
	}

	@Override
	public void onCreate() {
		Timer timer = null;
		if (timer == null) {
			timer = new Timer();
			// tarefa agendada
			TimerTask tarefa = new TimerTask() {
				@Override
				public void run() {
					
					Log.e("Notification", "Let's notify");
					
					// CRIANDO NOTIFICACAO DENTRO DA TAREFA
					mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

					newtext = "Você já viu as novidades do Adventa hoje? Tá esperando acabar???";

					Notification notification = new Notification(
							R.drawable.ofertas_on, newtext,
							System.currentTimeMillis());
					PendingIntent contentIntent = PendingIntent.getActivity(
							BackgroundNotificationService.this, 0, new Intent(
									BackgroundNotificationService.this,
									AdventaActivity.class), 0);
					notification.setLatestEventInfo(
							BackgroundNotificationService.this, "Adventa",
							newtext, contentIntent);
					notification.defaults |= Notification.DEFAULT_SOUND;
					notification.defaults |= Notification.DEFAULT_VIBRATE;

					mNM.notify(0, notification);
					notificationIntent = new Intent(
							BackgroundNotificationService.this,
							AdventaActivity.class);
					// showNotification();

					NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
					// Damos um "self execute" para que o servico continue
					// rodando mesmo nao sendo o primeiro acesso do usuario
					startService(new Intent(BackgroundNotificationService.this,
							BackgroundNotificationService.class));
				}
			};
			timer.scheduleAtFixedRate(tarefa, (1000 * 720), (1000 * 720));
		}
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	public void onDestroy() {
		// mNM.cancel(R.string.action_share);
		stopSelf();
	}

	private void showNotification() {
		CharSequence text = getText(R.string.action_share);

		Notification notification = new Notification(R.drawable.ic_launcher,
				text, System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, CarrousselActivity.class), 0);
		notification.setLatestEventInfo(this, "BackgroundAppExample", newtext,
				contentIntent);
		notification.flags = Notification.FLAG_ONGOING_EVENT
				| Notification.FLAG_NO_CLEAR;
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		mNM.notify(R.string.action_share, notification);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
