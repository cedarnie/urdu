package urdu4android.onairm.com.urdu4android.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import urdu4android.onairm.com.urdu4android.R;

public class LoginActivity extends Activity {

    private static final String EXTRA_PENDING_INTENT = "pending_intent";
    private EditText etPhone = null,etCode;


    public static void actionLogin(Activity activity, Intent pendingIntent) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		etPhone = (EditText) findViewById(R.id.etPhoneNum);
//		etCode = (EditText) findViewById(R.id.etCode);
//
//		findViewById(R.id.btnGetCode).setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//
//				if (TextUtils.isEmpty(etPhone.getText())) {
//					Toast.makeText(AtyLogin.this, R.string.phone_num_can_not_be_empty, Toast.LENGTH_LONG).show();
//					return;
//				}
//
//				final ProgressDialog pd = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
//				new GetCode(etPhone.getText().toString(), new GetCode.SuccessCallback() {
//
//					@Override
//					public void onSuccess() {
//						pd.dismiss();
//						Toast.makeText(AtyLogin.this, R.string.suc_to_get_code, Toast.LENGTH_LONG).show();
//					}
//				}, new GetCode.FailCallback() {
//
//					@Override
//					public void onFail() {
//						pd.dismiss();
//						Toast.makeText(AtyLogin.this, R.string.fail_to_get_code, Toast.LENGTH_LONG).show();
//					}
//				});
//
//			}
//		});
		
		
		findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if (TextUtils.isEmpty(etPhone.getText())) {
					Toast.makeText(LoginActivity.this, R.string.phone_num_can_not_be_empty, Toast.LENGTH_LONG).show();
					return;
				}
				
				if (TextUtils.isEmpty(etCode.getText())) {
					Toast.makeText(LoginActivity.this, R.string.code_can_not_be_empty, Toast.LENGTH_LONG).show();
					return;
				}
				
				
//				final ProgressDialog pd = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
//				new Login(MD5Tool.md5(etPhone.getText().toString()), etCode.getText().toString(), new Login.SuccessCallback() {
//
//					@Override
//					public void onSuccess(String token) {
//
//						pd.dismiss();
//
//						Config.cacheToken(AtyLogin.this, token);
//						Config.cachePhoneNum(AtyLogin.this, etPhone.getText().toString());
//
//						Intent i = new Intent(AtyLogin.this, AtyTimeline.class);
//						i.putExtra(Config.KEY_TOKEN, token);
//						i.putExtra(Config.KEY_PHONE_NUM, etPhone.getText().toString());
//						startActivity(i);
//
//						finish();
//					}
//				}, new Login.FailCallback() {
//
//					@Override
//					public void onFail() {
//						pd.dismiss();
//
//						Toast.makeText(AtyLogin.this, R.string.fail_to_login, Toast.LENGTH_LONG).show();
//					}
//				});
				
			}
		});
	}
	
}
