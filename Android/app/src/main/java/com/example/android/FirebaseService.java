package com.example.android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import com.example.android.Data.AppDB;
import com.example.android.Data.Contact;
import com.example.android.Data.ContactDao;
import com.example.android.Data.Message;
import com.example.android.Data.messageDao;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FirebaseService extends FirebaseMessagingService implements IMessageNotifier {

    private AppDB db;
    private messageDao msgDao;
    private ContactDao contactDao;
    private List<IMessageListener> listeners;

    // Activates when receiving message from the firebase.
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if (message.getNotification() != null) {
            // Creates a new notification channel.
            createNotificationChannel();

            // Creates the notification builder.
            String userId = message.getNotification().getTitle()
                    , content = message.getNotification().getBody();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.mipmap.default_icon)
                    .setContentTitle(userId)
                    .setContentText(content)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            // Updates the msgDao database.
            db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
            msgDao = db.messageDao();
            contactDao = db.contactDao();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm");
            String date = formatter.format(new Date());
            Contact contact = contactDao.get(userId);
            contact.setLastDate(date);
            contact.setLast(content);
            contactDao.update(contact);
            msgDao.insert(new Message(userId, content, date, false));
            if (MyApp.messageNotify != null)
                MyApp.messageNotify.NotifyAll(message.getNotification().getTag()
                        , message.getNotification().getBody());
            notificationManager.notify(1, builder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", "Messages", importance);
            channel.setDescription("Display notifications messages when user send message");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void addMessageListener(IMessageListener ml) {
        listeners.add(ml);
    }

    @Override
    public void removeMessageListener(IMessageListener ml) {
        listeners.add(ml);
    }
}