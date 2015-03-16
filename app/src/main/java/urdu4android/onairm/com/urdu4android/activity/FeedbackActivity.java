package urdu4android.onairm.com.urdu4android.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import urdu4android.onairm.com.urdu4android.Config;
import urdu4android.onairm.com.urdu4android.R;
import urdu4android.onairm.com.urdu4android.framework.TitleController;

public class FeedbackActivity extends Activity  implements View.OnClickListener{


    private static final String EXTRA_PENDING_INTENT = "pending_intent";
    private TitleController titleController;

    private EditText editTextSubject;
    private EditText editTextContent;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        editTextSubject = (EditText) findViewById(R.id.editText_subject);
        editTextContent = (EditText) findViewById(R.id.editText_content);
        buttonSubmit = (Button) findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);
        titleController = new TitleController(this, findViewById(R.id.subTitleBar));

    }

    public static void actionStart(Activity activity, Intent pendingIntent) {
        Intent intent = new Intent(activity, FeedbackActivity.class);
        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit:
                Toast.makeText(this,"wait for interface", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
