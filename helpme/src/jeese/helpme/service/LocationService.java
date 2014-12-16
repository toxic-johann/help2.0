package jeese.helpme.service;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.model.LatLng;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

/**
 * ��λ���񣬴�appʱ�������ر�appʱ�ر� ����app����ʱbindservice ��app�ر�ʱunbindservice
 * ͨ����̬�������Ի�ȡ��λ��Ϣ ����ǰ���ж��Ƿ�λ�ɹ���service�Ƿ��Ѿ�����
 * service����һ�ζ�λһ�Σ���Ҫ����λ��ʱstartservice���
 * @author Jeese
 * 
 */
public class LocationService extends Service implements AMapLocationListener {

	private LocationManagerProxy mLocationManagerProxy = null;

	private static double geoLat;// γ��
	private static double geoLng;// ����
	private static String province; // ʡ����
	private static String city; // ��������
	private static String city_code; // ���б���
	private static String district;// ��������
	private static String ad_code;// �������
	private static String street;// �ֵ���������Ϣ
	private static String address;// ��ϸ��ַ

	private static boolean success = false;// �Ƿ�λ�ɹ�

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		init();
	}

	@Override
	public void onDestroy() {
		if (mLocationManagerProxy != null) {
			mLocationManagerProxy.removeUpdates(this);
			mLocationManagerProxy.destroy();
		}
		mLocationManagerProxy = null;
		super.onDestroy();
	}

	/**
	 * ��ʼ����λ
	 */
	private void init() {

		mLocationManagerProxy = LocationManagerProxy
				.getInstance(LocationService.this);

		// �˷���Ϊÿ���̶�ʱ��ᷢ��һ�ζ�λ����Ϊ�˼��ٵ������Ļ������������ģ�
		// ע�����ú��ʵĶ�λʱ��ļ���������ں���ʱ�����removeUpdates()������ȡ����λ����
		// �ڶ�λ�������ں��ʵ��������ڵ���destroy()����
		// ����������ʱ��Ϊ-1����λֻ��һ��
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, -1, 15, this);

		mLocationManagerProxy.setGpsEnable(false);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation != null
				&& amapLocation.getAMapException().getErrorCode() == 0) {
			// ����λ����ϸ��Ϣ
			geoLat = amapLocation.getLatitude();
			geoLng = amapLocation.getLongitude();
			province = amapLocation.getProvince();
			city = amapLocation.getCity();
			city_code = amapLocation.getCityCode();
			district = amapLocation.getDistrict();
			address = amapLocation.getAddress();
			street = amapLocation.getStreet();
			ad_code = amapLocation.getAdCode();

			// ���ö�λ�ɹ�
			success = true;
			
			System.out.println("��λһ��" + amapLocation.getStreet());
		}
	}

	// �󶨷��񣬷���app�ر�ʱ�رն�λ����
	private MyBinder myBinder = new MyBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return myBinder;
	}

	public class MyBinder extends Binder {

		public LocationService getService() {
			return LocationService.this;
		}
	}

	/******* get *************/
	public static boolean getSuccess() {
		return success;
	}

	public static double getGeoLat() {
		return geoLat;
	}

	public static double getGeoLng() {
		return geoLng;
	}

	public static String getProvince() {
		return province;
	}

	public static String getCity() {
		return city;
	}

	public static String getCityCode() {
		return city_code;
	}

	public static String getDistrict() {
		return district;
	}

	public static String getAdCode() {
		return ad_code;
	}

	public static String getStreet() {
		return street;
	}

	public static String getAddress() {
		return address;
	}
	
	public static LatLng getLatLng() {
		return new LatLng(geoLat,geoLng);
	}

}
