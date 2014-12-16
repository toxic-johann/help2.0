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
		mapView.onCreate(savedInstanceState);// 必须要写
		init();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.discover_fragment, null);
		}
		// 缓存的rootView需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
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
	 * amap添加一些事件监听器
	 */
	private void setUpMap() {
		// 设置地图logo显示在左下方
		mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
		// 设置默认缩放倍数
		aMap.moveCamera(CameraUpdateFactory.zoomTo((float) 16));
		// 设置默认放大缩小按钮是否显示
		mUiSettings.setZoomControlsEnabled(false);
		// 设置指南针是否显示
		mUiSettings.setCompassEnabled(true);

		// 在标记位置做标记
		// aMap.addMarker(new MarkerOptions().position(des).icon(
		// BitmapDescriptorFactory
		// .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		// 将标记地点显示在屏幕中
		aMap.moveCamera(CameraUpdateFactory
				.newCameraPosition(new CameraPosition(LocationService
						.getLatLng(), 16, 30, 0)));
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
}
