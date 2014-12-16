package jeese.helpme.service;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import jeese.helpme.headset.HeadSetHelper;
import jeese.helpme.headset.HeadSetHelper.OnHeadSetListener;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MainService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return new MyBinder();
	}

	/**
	 * Service�ĵ��ýӿ�
	 */
	public class MyBinder extends Binder {
		/**
		 * ����������Ϣ
		 */
		public void SendHelp() {

		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("������̨���񡣡���");

		init();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("���ٺ�̨���񡣡���");

		// ֹͣ��λ����
		Intent intent = new Intent(this, LocationService.class);
		stopService(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	private void init() {
		// ע���߿ؼ���
		HeadSetHelper.getInstance().setOnHeadSetListener(headSetListener);
		HeadSetHelper.getInstance().open(this);

	}

	OnHeadSetListener headSetListener = new OnHeadSetListener() {
		@Override
		public void onFiveClick() {
			AjaxParams params = new AjaxParams();
			params.put("username", "michael yang");
			params.put("password", "123456");
			params.put("email", "test@tsz.net");

			FinalHttp fh = new FinalHttp();
			fh.post("www.baidu.com", params, new AjaxCallBack<Object>() {
				@Override
				public void onStart() {
				};

				@Override
				public void onLoading(long count, long current) {

				};

				@Override
				public void onSuccess(Object t) {
					System.out.println("������Ϣ��+" + t.toString());
				};

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
				};
			});

		}

		@Override
		public void onClick() {
			System.out.println("����һ��");
			Toast.makeText(getApplicationContext(),
					"����һ��������ť",
					Toast.LENGTH_SHORT).show();
			AjaxParams params = new AjaxParams();
			params.put("username", "michael yang");
			params.put("password", "123456");
			params.put("email", "test@tsz.net");

			FinalHttp fh = new FinalHttp();
			fh.post("www.baidu.com", params, new AjaxCallBack<Object>() {
				@Override
				public void onStart() {
				};

				@Override
				public void onLoading(long count, long current) {

				};

				@Override
				public void onSuccess(Object t) {
					System.out.println("������Ϣ��+" + t.toString());
				};

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
				};
			});
		}
	};
}
