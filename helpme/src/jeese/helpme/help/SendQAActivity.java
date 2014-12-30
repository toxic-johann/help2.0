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


public class SendQAActivity extends Activity {
	private Button sendQuestionAnswerBtn;
	private EditText editQuestionAnswer;
	private Button sendCancelBtn;
	private static final String SERVER_URL="http://120.24.208.130:8080/api/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_question_answer);
		
		editQuestionAnswer=(EditText) findViewById(R.id.edit_answer_text);
		 sendQuestionAnswerBtn=(Button) findViewById(R.id.send_btn);
	     sendCancelBtn=(Button) findViewById(R.id.cancel_send_btn);
	     
	     sendQuestionAnswerBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String content = editQuestionAnswer.getText().toString();

					if(content.equals("")){
						Toast.makeText(getApplicationContext(), "���������ݲ���Ϊ��", Toast.LENGTH_SHORT).show();
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
							System.out.println("������Ϣ�ǣ�" + t.toString());
						}
					});
					
				}
			});
		     
		     sendCancelBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//���ȡ����ť��ת��MainActivity
					Intent intent=new Intent(SendQAActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
			});
		     /**
		      *	���������ı��ı仯����ȷ�ϰ�ť��������ɫ
		      */
		     editQuestionAnswer.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
						// ���������ı��ı仯����ȷ�ϰ�ť��������ɫ
						String str = editQuestionAnswer.getText().toString();
						if(str.equals("")){
							sendQuestionAnswerBtn.setTextColor(getResources().getColor(R.color.gray));
						}else{
							sendQuestionAnswerBtn.setTextColor(getResources().getColor(R.color.dark_gray));
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