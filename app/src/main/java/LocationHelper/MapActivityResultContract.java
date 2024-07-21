package LocationHelper;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import UI.MapActivity;

public class MapActivityResultContract extends ActivityResultContract<Void, Intent> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Void input) {
        return new Intent(context, MapActivity.class);
    }

    @Override
    public Intent parseResult(int resultCode, @Nullable Intent intent) {
        return intent;
    }
}
