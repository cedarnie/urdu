package urdu4android.onairm.com.urdu4android.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import urdu4android.onairm.com.urdu4android.Config;
import urdu4android.onairm.com.urdu4android.R;
import urdu4android.onairm.com.urdu4android.framework.TitleController;

public class MoreActivity extends Activity  implements View.OnClickListener{


    private static final String EXTRA_PENDING_INTENT = "pending_intent";
    private TitleController titleController;
    ImageView avatar;
    TextView message;
    TextView telCall;
    TextView feedback;
    TextView aboutUs;
    TextView aboutApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        avatar = (ImageView)findViewById(R.id.imageView_avatar);
        message = (TextView)findViewById(R.id.textView_message);
        telCall = (TextView)findViewById(R.id.textView_tel);
        feedback = (TextView)findViewById(R.id.textView_feedback);
        aboutUs = (TextView)findViewById(R.id.textView_aboutus);
        aboutApp = (TextView)findViewById(R.id.textView_aboutapp);
        avatar.setOnClickListener(this);
        message.setOnClickListener(this);
        telCall.setOnClickListener(this);
        feedback.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        aboutApp.setOnClickListener(this);
        //avatar.setImageUrl("http://7vznzz.com1.z0.glb.clouddn.com/2.jpg", ImageCacheManager.getInstance().getImageLoader());
        titleController = new TitleController(this, findViewById(R.id.subTitleBar));

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(80)
                .oval(false).build();

        Picasso.with(this)
                .load("http://7vznzz.com1.z0.glb.clouddn.com/2.jpg")
                .fit()
                .transform(transformation)
                .into(avatar);
    }

    public static void actionStart(Activity activity, Intent pendingIntent) {
        Intent intent = new Intent(activity, MoreActivity.class);
        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_avatar:
                ProfileActivity.actionStart(this, null);
                break;
            case R.id.textView_message:
                Uri uri = Uri.parse("smsto:"+ Config.TEL_NUM);
                Intent intentMsg = new Intent(Intent.ACTION_SENDTO, uri);
//                intentMsg.putExtra("sms_body", "Hello,");
                startActivity(intentMsg);
                break;
            case R.id.textView_tel:
                Uri uriCall = Uri.parse("tel:"+Config.TEL_NUM);
                Intent intentCall = new Intent(Intent.ACTION_CALL, uriCall);
                startActivity(intentCall);
                break;
            case R.id.textView_feedback:
                FeedbackActivity.actionStart(this,null);
            default:
                break;
        }
    }
}
