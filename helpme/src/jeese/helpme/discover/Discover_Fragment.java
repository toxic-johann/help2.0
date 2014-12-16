package jeese.helpme.discover;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;

import jeese.helpme.R;
import jeese.helpme.service.LocationService;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Discover_Fragment extends Fragment {

	private View mView;
	private MapView mapView;
	private AMap aMap;
	private UiSettings mUiSettings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = View.inflate(getActivity(), R.layout.discover_fragment, null);
		mapView = (MapView) mView.findViewById(R.id.discover_fragment_mapview);
		mapView.onCreate(savedInstanceState);// ����Ҫд
		init();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.discover_fragment, null);
		}
		// �����rootView��Ҫ�ж��Ƿ��Ѿ����ӹ�parent��
		// �����parent��Ҫ��parentɾ����Ҫ��Ȼ�ᷢ�����rootview�Ѿ���parent�Ĵ���
		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}
		return mView;
	}

	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			mUiSettings = aMap.getUiSettings();
			setUpMap();
		}
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
		// aMap.addMarker(new MarkerOptions().position(des).icon(
		// BitmapDescriptorFactory
		// .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		// ����ǵص���ʾ����Ļ��
		aMap.moveCamera(CameraUpdateFactory
				.newCameraPosition(new CameraPosition(LocationService
						.getLatLng(), 16, 30, 0)));
	}

	/**
	 * ����������д
	 */
	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * ����������д
	 */
	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * ����������д
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * ����������д
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
}
