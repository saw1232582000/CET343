package SMSHelper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SMSHelper {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private Activity activity;

    private Context context;

    public SMSHelper(Context context) {
        this.context = context;
    }

    public void sendSms(String phoneNumber, String message) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Toast.makeText(context, "SMS sent.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "SMS failed, please try again.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public void handlePermissionsResult(int requestCode, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(context, "Permission granted, you can now send SMS.", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(context, "Permission denied, cannot send SMS.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
