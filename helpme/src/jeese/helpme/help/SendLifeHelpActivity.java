package jeese.helpme.help;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import jeese.helpme.R;
import jeese.helpme.photoUtil.Bimp;
import jeese.helpme.photoUtil.ChoosePicActivity;
import jeese.helpme.photoUtil.FileUtils;
import jeese.helpme.photoUtil.PhotoActivity;
import jeese.helpme.service.LocationService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class SendLifeHelpActivity extends Activity{
	
	private TextView chooseFriend;
	private Button sendLifeHelpBtn;
	private EditText editHelpContent;
	private Button sendCancelBtn;
	private ImageView camera;
	
	private GridView noScrollgridview;;
	private GridAdapter adapter;
	
	private static final String SERVER_URL="http://120.24.208.130:8080/api/";
	
	public List<String> drr = new ArrayList<String>();//���ѹ�����ͼƬ��·��
	public List<Bitmap> bmp = new ArrayList<Bitmap>();//�������������ʾ��Բ�Ǿ���ͼƬ
	
	private float dp;
	//requestCode
	private static final int TAKE_PICTURE = 0;//���յ�������
//	private static final int RESULT_LOAD_IMAGE = 1;//�����ѡȡ��������
//	private static final int CUT_PHOTO_REQUEST_CODE = 2;//�ü�ͼƬ��������
	private static final int CHOOSE_FRIEND = 4;//ѡ��������Χ��������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_life_help);
		
		init();
	}
	
	public void init(){
//		dp = getResources().getDimension(R.dimen.dp);//�߿��С
		
		chooseFriend=(TextView) findViewById(R.id.choose_friend);
		editHelpContent=(EditText) findViewById(R.id.edit_lifehelp_text);
		sendLifeHelpBtn=(Button) findViewById(R.id.send_btn);
	    sendCancelBtn=(Button) findViewById(R.id.cancel_send_btn);
	    camera=(ImageView) findViewById(R.id.send_cose_camera);
	    //��ʼ��gridview
	    noScrollgridview=(GridView) findViewById(R.id.noScrollgridview);
	    noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
	    adapter=new GridAdapter(this);
	    adapter.update();
	    noScrollgridview.setAdapter(adapter);
	    noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if (position == Bimp.bmp.size()) {
					new PopupWindows(SendLifeHelpActivity.this, noScrollgridview);
				} else {
					Intent intent = new Intent(SendLifeHelpActivity.this,
							PhotoActivity.class);
					intent.putExtra("ID", position);
					startActivity(intent);
				}
			}
		});
	     
//	    editHelpContent.setFocusable(true);
//	    editHelpContent.setFocusableInTouchMode(true); 
	    
	    /**
	     * ������½����ö�˭������������
	     */
	    chooseFriend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SendLifeHelpActivity.this, ChooseHelpRange.class);
				startActivityForResult(intent, CHOOSE_FRIEND);
			}
		});
	    
	    /**
	     * ���cameraͼ�꣬����ѡ�����������ϴ����ߴ����ѡ����Ƭ�ϴ�����ȡ��
	     */
	     camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new PopupWindows(SendLifeHelpActivity.this, camera);
			}
		});
	     
	     /**
	      *	���������ı��ı仯����ȷ�ϰ�ť��������ɫ
	      */
	     editHelpContent.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
					// ���������ı��ı仯����ȷ�ϰ�ť��������ɫ
					String str = editHelpContent.getText().toString();
					if(str.equals("")){
						sendLifeHelpBtn.setTextColor(getResources().getColor(R.color.gray));
					}else{
						sendLifeHelpBtn.setTextColor(getResources().getColor(R.color.dark_gray));
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
	     
	     /**
	      * ���ȡ����ť�رյ�ǰ����
	      */
	     sendCancelBtn.setOnClickListener(new OnClickListener() {
				
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	     
	     /**
	      * ������Ͱ�ť�������ݷ��ͳ�ȥ(δʵ�֣�����)
	      */
	     sendLifeHelpBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String content = editHelpContent.getText().toString();
				if (content.equals("")) {
					Toast.makeText(getApplicationContext(), "���������ݲ���Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				//��ȡͼƬ·��
				List<String> list = new ArrayList<String>();				
				for (int i = 0; i < Bimp.drr.size(); i++) {
					String Str = Bimp.drr.get(i).substring( 
							Bimp.drr.get(i).lastIndexOf("/") + 1,
							Bimp.drr.get(i).lastIndexOf("."));
					list.add(FileUtils.SDPATH+Str+".JPEG");				
				}
				// �����ѹ��ͼƬȫ������  list ·��������
				// �����ѹ������ bmp ����  ���� Bimp.bmp����
				
				AjaxParams params=new AjaxParams();
				// �ϴ�ͼƬ�ļ�
				for(int i=0; i < drr.size(); i++){
					String path = drr.get(i);
					try {
						params.put("profile_picture", new File(path));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}

				// ����ϴ��������� .........
				FileUtils.deleteDir();
				
				
				Double loaitude = LocationService.getGeoLat();
				Double longitude = LocationService.getGeoLng();
				String city = LocationService.getCityCode();
				String address = LocationService.getAddress();
				
				params.put("username", "xiaoming");
				params.put("datetime", "2014-12-16");
				params.put("type", "1");
				params.put("content", content);
				params.put("longitude", longitude.toString());
				params.put("latitude", loaitude.toString());
				params.put("city", city);
				params.put("address", address);
				
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
	}

	private Uri photoUri;
	private String path = "";
	/**
	 * �����ϴ�
	 */
	public void photo() {
		try {
			//image�Ļ�ȡ����ͨ����Android�Դ���CameraӦ�������
			Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			//���������Ҫ��ȡ������SD��д�룬��ʱ�ͱ�����Ҫ�ж�һ��SD����״̬�������п��ܳ���
			String sdcardState = Environment.getExternalStorageState();
			String sdcardPathDir = android.os.Environment.getExternalStorageDirectory().getPath() + "/tempImage/";
			File file = null;
			
			//Environment.MEDIA_MOUNTED��ʾsd����������
			if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
				/*�����ָ��image�洢��SDCard�ϵ�tempImage�ļ�������*/ 
				File fileDir = new File(sdcardPathDir); 
				if (!fileDir.exists()) {
					fileDir.mkdirs();//���ļ����Ѿ����ھͲ���Ҫ�½��ļ�����
				}
				/*����һ���ļ�*/
				file = new File(sdcardPathDir + System.currentTimeMillis()
						+ ".JPEG");
			}
			if (file != null) {
				path = file.getPath();//getpath �õ���д��·�������ݵ�ǰĿ¼λ�ÿ�����д·�����õ����·����
				//uri�������Ǹ������URI�ҵ�ĳ����Դ�ļ���������ʽ�磺 file:///sdcard/temp.jpg
				photoUri = Uri.fromFile(file);
				/*
				 * ����Camara���ص�������ͼ�����ǿ��Դ��ݸ���һ������EXTRA_OUTPUT,������Camera��ȡ����ͼƬ�洢��һ��ָ����URIλ�ô���
				 * �����ͽ��ļ��Ĵ洢��ʽ��uriָ������CameraӦ����
				 */
				openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
				/*
				 * ����������Ҫ������Camera�󣬿��Է���Camera��ȡ����ͼƬ��   
				 * ���ԣ�����ʹ��startActivityForResult������Camera��֮����ڻ�ȡ��ͼƬ���вü�  
				 */
				startActivityForResult(openCameraIntent, TAKE_PICTURE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ����ͼƬ
	 * */
	private void startPhotoZoom(Uri uri) {
		try {
			//SimpleDateFormat ʹ�ÿ���ѡ���κ��û����������-ʱ���ʽ��ģʽ��
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			//SimpleDateFormat�е�format�������԰�Date�͵��ַ���ת�����ض���ʽ��String����
			String address = sDateFormat.format(new java.util.Date());
			
			if (!FileUtils.isFileExist("")) {
				FileUtils.createSDDir("");
			}
			/*������drr������һ���µ�ͼƬ·��*/
			drr.add(FileUtils.SDPATH + address + ".JPEG");
			//�����õ���ʽ��ͼ�������û��ָ�������������ƣ�����ͨ��intent��ϵͳ���ҵ�һ������ʵ�Activity��
			//ͨ�����URI���Է���һ�����ص���Դ����ͼƬ��Դ
			Uri imageUri = Uri.parse("file:///sdcard/formats/" + address
					+ ".JPEG");

			final Intent intent = new Intent("com.android.camera.action.CROP");	
			intent.setDataAndType(uri, "image/*");

			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 480);
			intent.putExtra("outputY", 480);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", false);
			intent.putExtra("return-data", false);
//			startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ϊ�˻�ȡCamera���ص�ͼƬ��Ϣ����д�÷�����  
	 */ 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.drr.size() < 9 && resultCode == -1) {
				Bimp.drr.add(path);
//				startPhotoZoom(photoUri);
			}
			break;
		case CHOOSE_FRIEND:
			if(resultCode == RESULT_OK){
				String result = data.getExtras().getString("group").toString();
				chooseFriend.setText(result);
			}
			break;
		}
	}
	
	/**
	 * ���cameraͼ������浯����ѡ���
	 * */
	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));
			
			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					photo();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					/*Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(i, RESULT_LOAD_IMAGE);*/
					Intent intent = new Intent(SendLifeHelpActivity.this, 
							ChoosePicActivity.class);
					startActivity(intent);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});
		}
	}
	/**
	 * һ���̳�BaseAdapter���Զ���������  
	 * @author Administrator
	 *
	 */
	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		/*����д�õĲ����ļ����������������ǵ�������ʱ���������ڴ������Զ���ؼ��������Ҫ�õ�LayoutInflater��
		 * LayoutInflater��Android���ǡ���չ������˼������������findViewById().
		 * ��ͬ����LayoutInflater��������ò����ļ�����ģ���findViewById()��������þ���ؼ��ġ�
		 * LayoutInflater������BaseAdapter��getView�������õ���������ȡ����View�����ء�*/
		private LayoutInflater inflater; // ��ͼ����
		private int selectedPosition = -1;// ѡ�е�λ��
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
//			return (Bimp.bmp.size() + 1);
			if (Bimp.bmp.size() < 9) {
				return Bimp.bmp.size() + 1;
			} else {
				return Bimp.bmp.size();
			}
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}
		/**
		 * ListView Item����
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			final int sign = position;
			// �Զ�����ͼ
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				// ��ȡ�ؼ�����:image
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				// ���ÿؼ�����convertView
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if(position == 0){
					holder.image.setVisibility(View.GONE);
				}
				if (position == 9) {
					holder.image.setVisibility(View.GONE);//�����ﵽ9�����������ͼƬ
				}
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			return convertView;
		}
		public class ViewHolder {
			public ImageView image;
		}
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};
		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								String newStr = path.substring(
										path.lastIndexOf("/") + 1,
										path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.max += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}
	protected void onDestroy() {

		FileUtils.deleteDir();
		// ����ͼƬ����
		for (int i = 0; i < bmp.size(); i++) {
			bmp.get(i).recycle();
		}
		for (int i = 0; i < Bimp.bmp.size(); i++) {
			Bimp.bmp.get(i).recycle();
		}
		Bimp.bmp.clear();
		bmp.clear();
		drr.clear();
		super.onDestroy();
	}
	
	/**
	 * ��ʼ���͸����������gridview
	 */
	/*public void gridviewInit() {
		adapter = new GridAdapter(this);
		adapter.setSelectedPosition(0);
		
		//gridview��size�����9
		int size = 0;
		if (bmp.size() < 9) {
			size = bmp.size() + 1;//ѡ�е�ͼƬ��������һ���������ͼƬ��ͼƬ
		} else {
			size = bmp.size();
		}
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(this);
	}
	*/
	/**
	 * ���item�������ͼƬ
	 */
	/*public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(SendLifeHelpActivity.this
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		if (arg2 == bmp.size()) {
			String sdcardState = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
				new PopupWindows(SendLifeHelpActivity.this, noScrollgridview);
			} else {
				Toast.makeText(getApplicationContext(), "sdcard�Ѱγ�������ѡ����Ƭ",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Intent intent = new Intent(SendLifeHelpActivity.this,
					PhotoActivity.class);

			intent.putExtra("ID", arg2);
			startActivity(intent);
		}
	}
	*/

/*		public View getView(int position, View convertView, ViewGroup parent) {
			final int sign = position;
			// �Զ�����ͼ
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				// ��ȡlist_item�����ļ�����ͼ
				convertView = listContainer.inflate(
						R.layout.item_gridview, null);

				// ��ȡ�ؼ�����:image�����Ͻǵ�СԲ��ɾ����ť
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				holder.bt = (Button) convertView
						.findViewById(R.id.item_grida_bt);
				// ���ÿؼ�����convertView
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
//				holder.image.setLayoutParams(new LinearLayout.LayoutParams(screenW, screenH));
				holder.bt.setVisibility(View.GONE);//�������ϽǺ�ɫɾ��С��ť
				if(position == 0){
					holder.image.setVisibility(View.GONE);
					holder.bt.setVisibility(View.GONE);//�������ϽǺ�ɫɾ��С��ť
				}
				if (position == 9) {
					holder.image.setVisibility(View.GONE);//�����ﵽ9�����������ͼƬ
				}
			} else {
				holder.image.setImageBitmap(bmp.get(position));
				//������Ͻǰ�ť��ɾ����ǰͼƬ
				holder.bt.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						PhotoActivity.bitmap.remove(sign);
						bmp.get(sign).recycle();
						bmp.remove(sign);
						drr.remove(sign);

						gridviewInit();
					}
				});
			}
			return convertView;
		}*/
}
