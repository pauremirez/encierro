package com.navarra.dya.encierro;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import static com.navarra.dya.encierro.CommonUtilities.displayMessage;
import static com.navarra.dya.encierro.CommonUtilities.PROJECT_ID;

public class GCMIntentService extends GCMBaseIntentService {

    public GCMIntentService()
	{
		super(PROJECT_ID);
		Log.d(TAG, "GCMIntentService init");
	}
	

	@Override
	protected void onError(Context ctx, String sError) {
		Log.d(TAG, "Error: " + sError);
        displayMessage(ctx,getString(R.string.gcm_error, sError));
		
	}

	@Override
	protected void onMessage(Context ctx, Intent intent) {
		
		Log.d(TAG, "Message Received");
		
		String message = intent.getStringExtra("message");
		
		sendGCMIntent(ctx, message);
        displayMessage(ctx,message);
        generateNotification(ctx, message);
		
	}

	
	private void sendGCMIntent(Context ctx, String message) {
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("GCM_RECEIVED_ACTION");
		
		broadcastIntent.putExtra("gcm", message);
		
		ctx.sendBroadcast(broadcastIntent);
		
	}
	
	
	@Override
	protected void onRegistered(Context ctx, String regId) {
		// send regId to your server
		Log.d(TAG, "Device registered: redId="+regId);
		displayMessage(ctx,"Your device registrared with GCM");
        ServerUtilities.register(ctx,MainActivity.userId,regId,MainActivity.stand,MainActivity.resource,MainActivity.mPhoneNumber, MainActivity.status,MainActivity.gpsPosition);
	}

	@Override
	protected void onUnregistered(Context ctx, String regId) {
		// send notification to your server to remove that regId
        displayMessage(ctx,getString(R.string.gcm_registerred));
        //ServerUtilities.unregister(ctx,regId);
	}

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);

        String title = context.getString(R.string.app_name);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);

    }





}
