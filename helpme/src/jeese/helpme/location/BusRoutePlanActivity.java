package jeese.helpme.location;

import jeese.helpme.R;
import jeese.helpme.service.LocationService;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.overlay.BusRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.WalkRouteResult;

public class BusRoutePlanActivity extends Activity implements OnRouteSearchListener, OnMarkerClickListener,
OnMapClickListener, OnInfoWindowClickListener, InfoWindowAdapter {
	private AMap aMap;
	private MapView mapView;
	private int busMode = RouteSearch.BusDefault;// ����Ĭ��ģʽ
	private BusRouteResult busRouteResult;// ����ģʽ��ѯ���
	private LatLonPoint startPoint = null;
	private LatLonPoint endPoint = null;
	private String city = null;
	private RouteSearch routeSearch;
	public ArrayAdapter<String> aAdapter;
	// ����Ŀ�ĵ���Ϣ
	private Bundle bundle;
	private LatLng des;
	private UiSettings mUiSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busnav);

		mapView = (MapView) findViewById(R.id.busnav_mapview);
		mapView.onCreate(savedInstanceState);// �˷���������д
		
		// Ŀ�ĵ�λ�þ�γ��
		bundle = this.getIntent().getExtras();
		startPoint = new LatLonPoint(LocationService.getGeoLat(),LocationService.getGeoLng());
		endPoint = new LatLonPoint(bundle.getDouble("latitude"),
				bundle.getDouble("longitude"));
		des = new LatLng(bundle.getDouble("latitude"),
				bundle.getDouble("longitude"));
		
//		endPoint = new LatLonPoint(23.114155, 113.318977);
//		des = new LatLng(23.114155, 113.318977);
		
		city = LocationService.getCityCode();
		init();
		//��ʼ��������·��
		searchRouteResult(startPoint,endPoint);
	}

	/**
	 * ��ʼ��AMap����
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			mUiSettings = aMap.getUiSettings();
		}
		routeSearch = new RouteSearch(this);
		routeSearch.setRouteSearchListener(this);
		
		aMap.setOnMapClickListener(BusRoutePlanActivity.this);
		aMap.setOnMarkerClickListener(BusRoutePlanActivity.this);
		aMap.setOnInfoWindowClickListener(BusRoutePlanActivity.this);
		aMap.setInfoWindowAdapter(BusRoutePlanActivity.this);
		
		// ����Ĭ�ϷŴ���С��ť�Ƿ���ʾ
		mUiSettings.setZoomControlsEnabled(false);
		// ����ָ�����Ƿ���ʾ
		mUiSettings.setCompassEnabled(true);
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

	/**
	 * ѡ�񹫽�ģʽ
	 */
	private void busRoute() {
		busMode = RouteSearch.BusDefault;
	}

	/**
	 * ��ʼ����·���滮����
	 */
	public void searchRouteResult(LatLonPoint startPoint, LatLonPoint endPoint) {
		//ѡ�񹫽�ģʽ
		busRoute();
		
		final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
				startPoint, endPoint);
		BusRouteQuery query = new BusRouteQuery(fromAndTo, busMode, city, 0);// ��һ��������ʾ·���滮�������յ㣬�ڶ���������ʾ������ѯģʽ��������������ʾ������ѯ�������ţ����ĸ�������ʾ�Ƿ����ҹ�೵��0��ʾ������
		routeSearch.calculateBusRouteAsyn(query);// �첽·���滮����ģʽ��ѯ

	}

	/**
	 * ����·�߲�ѯ�ص�
	 */
	@Override
	public void onBusRouteSearched(BusRouteResult result, int rCode) {
		if (rCode == 0) {
			if (result != null && result.getPaths() != null
					&& result.getPaths().size() > 0) {
				busRouteResult = result;
				BusPath busPath = busRouteResult.getPaths().get(0);
				aMap.clear();// �����ͼ�ϵ����и�����
				BusRouteOverlay routeOverlay = new BusRouteOverlay(this, aMap,
						busPath, busRouteResult.getStartPos(),
						busRouteResult.getTargetPos());
				routeOverlay.removeFromMap();
				routeOverlay.addToMap();
				routeOverlay.zoomToSpan();
			} else {
				//ToastUtil.show(RouteActivity.this, R.string.no_result);
			}
		} else if (rCode == 27) {
			//ToastUtil.show(RouteActivity.this, R.string.error_network);
		} else if (rCode == 32) {
			//ToastUtil.show(RouteActivity.this, R.string.error_key);
		} else {
//			ToastUtil.show(RouteActivity.this, getString(R.string.error_other)
//					+ rCode);
		}
	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
