package urdu4android.onairm.com.urdu4android.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import urdu4android.onairm.com.urdu4android.R;
import urdu4android.onairm.com.urdu4android.framework.TitleController;

public class ProfileActivity extends Activity  {


    private static final String EXTRA_PENDING_INTENT = "pending_intent";
    private TitleController titleController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        titleController = new TitleController(this, findViewById(R.id.subTitleBar));
    }

    public static void actionStart(Activity activity, Intent pendingIntent) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }


}
