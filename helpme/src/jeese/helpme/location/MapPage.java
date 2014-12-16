package jeese.helpme.location;

import jeese.helpme.R;
import jeese.helpme.service.LocationService;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MapPage extends Activity implements OnClickListener {
	private MapView mapView;
	private AMap aMap;
	private UiSettings mUiSettings;
	
	private Bundle bundle;
	double desla;
	double deslo;
	private LatLng des;//Ŀ�ĵ�����

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mappage);
		mapView = (MapView) findViewById(R.id.mappage_mapview);
		mapView.onCreate(savedInstanceState);// ����Ҫд

		// Ŀ�ĵ�λ�þ�γ��
		bundle = this.getIntent().getExtras();
		
		desla = 23.114155;
		deslo = 113.318977;
		
//		desla = bundle.getDouble("latitude");
//		deslo = bundle.getDouble("longitude");
		des = new LatLng(desla,deslo);
		
		//��λһ��
		Intent intent = new Intent(this, LocationService.class);
		startService(intent);
		
		init();
	}

	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			mUiSettings = aMap.getUiSettings();
			setUpMap();
		}
		Button mappage_walk = (Button) findViewById(R.id.mappage_walk);
		mappage_walk.setOnClickListener(this);
		Button mappage_drive = (Button) findViewById(R.id.mappage_drive);
		mappage_drive.setOnClickListener(this);
		Button mappage_transit = (Button) findViewById(R.id.mappage_transit);
		mappage_transit.setOnClickListener(this);
	}

	/**
	 * amap���һЩ�¼�������
	 */
	private void setUpMap() {
		// ���õ�ͼlogo��ʾ�����·�
		mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
		// ����Ĭ�����ű���
		aMap.moveCamera(CameraUpdateFactory.zoomTo((float) 16));
		// ����Ĭ�ϷŴ���С��ť�Ƿ���ʾ
		mUiSettings.setZoomControlsEnabled(false);
		// ����ָ�����Ƿ���ʾ
		mUiSettings.setCompassEnabled(true);

		// �ڱ��λ�������
		aMap.addMarker(new MarkerOptions().position(des).icon(
				BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		//����ǵص���ʾ����Ļ��
		aMap.moveCamera(CameraUpdateFactory
				.newCameraPosition(new CameraPosition(des, 16,
						30, 0)));
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.mappage_walk:
			Intent intent0 = new Intent();
			Bundle bundle0 = new Bundle();
			bundle0.putDouble("longitude",
					deslo);
			bundle0.putDouble("latitude",
					desla);
			intent0.putExtras(bundle0);
			intent0.setClass(this,WalkRoutePlan.class);
			startActivity(intent0);
			break;

		case R.id.mappage_drive:
			Intent intent1 = new Intent();
			Bundle bundle1 = new Bundle();
			bundle1.putDouble("longitude",
					deslo);
			bundle1.putDouble("latitude",
					desla);
			intent1.putExtras(bundle1);
			intent1.setClass(this,DriveRoutePlan.class);
			startActivity(intent1);
			break;

		case R.id.mappage_transit:
			Intent intent2 = new Intent();
			Bundle bundle2 = new Bundle();
			bundle2.putDouble("longitude",
					deslo);
			bundle2.putDouble("latitude",
					desla);
			intent2.putExtras(bundle2);
			intent2.setClass(this,BusRoutePlan.class);
			startActivity(intent2);
			break;
		}
	}
}
