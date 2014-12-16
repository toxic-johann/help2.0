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
	 * Service的调用接口
	 */
	public class MyBinder extends Binder {
		/**
		 * 发送求助消息
		 */
		public void SendHelp() {

		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("开启后台服务。。。");

		init();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("销毁后台服务。。。");

		// 停止定位服务
		Intent intent = new Intent(this, LocationService.class);
		stopService(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	private void init() {
		// 注册线控监听
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
					System.out.println("返回信息是+" + t.toString());
				};

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
				};
			});

		}

		@Override
		public void onClick() {
			System.out.println("按了一下");
			Toast.makeText(getApplicationContext(),
					"按了一下求助按钮",
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
					System.out.println("返回信息是+" + t.toString());
				};

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
				};
			});
		}
	};
}
