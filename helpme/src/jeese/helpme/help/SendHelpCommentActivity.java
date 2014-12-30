package jeese.helpme.help;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import jeese.helpme.R;
import jeese.helpme.activity.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SendHelpCommentActivity extends Activity {
	private Button sendHelpCommentBtn;
	private EditText editHelpComment;
	private Button sendCancelBtn;
	private static final String SERVER_URL="http://120.24.208.130:8080/api/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_comment);
		
		 editHelpComment=(EditText) findViewById(R.id.edit_lifehelp_text);
	     sendHelpCommentBtn=(Button) findViewById(R.id.send_btn);
	     sendCancelBtn=(Button) findViewById(R.id.cancel_send_btn);
	     
	     sendHelpCommentBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String content = editHelpComment.getText().toString();

					if(content.equals("")){
						Toast.makeText(getApplicationContext(), "发布的内容不能为空", Toast.LENGTH_SHORT).show();
						return;
					}
					
					AjaxParams params=new AjaxParams();
					params.put("username", "xiaoming");
					params.put("datetime", "2014-12-16");
					params.put("type", "1");
					params.put("content", content);
					
					FinalHttp finalHttp = new FinalHttp();
					finalHttp.post(SERVER_URL, params, new AjaxCallBack<Object>(){
						@Override
						public void onLoading(long count, long current) {
							super.onLoading(count, current); 
							}
						@Override
						public void onSuccess(Object t) {
							System.out.println("返回信息是：" + t.toString());
						}
					});
					
				}
			});
		     
		     sendCancelBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//点击取消按钮跳转到MainActivity
					Intent intent=new Intent(SendHelpCommentActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
			});
		     /**
		      *	根据输入文本的变化更改确认按钮的字体颜色
		      */
		     editHelpComment.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
						// 根据输入文本的变化更改确认按钮的字体颜色
						String str = editHelpComment.getText().toString();
						if(str.equals("")){
							sendHelpCommentBtn.setTextColor(getResources().getColor(R.color.gray));
						}else{
							sendHelpCommentBtn.setTextColor(getResources().getColor(R.color.dark_gray));
						}	
					}
					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
							int arg3) {
					}
					@Override
					public void afterTextChanged(Editable arg0) {	
					}
				});
		}

	}



	


	

		

	     
	     
	    