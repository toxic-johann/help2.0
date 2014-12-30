package jeese.helpme.help;

import android.app.Activity;
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
import jeese.helpme.photoUtil.FileUtils;
import jeese.helpme.photoUtil.PhotoActivity;
import jeese.helpme.help.ChooseHelpRange;
import jeese.helpme.service.LocationService;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class SendQuestionActivity extends Activity implements OnItemClickListener {
	
	private TextView chooseFriend;
	private Button sendQuestionBtn;
	private EditText editQuestionContent;
	private Button sendCancelBtn;
	private ImageView camera;
	private GridView gridview;
	private GridAdapter adapter;
	private static final String SERVER_URL="http://120.24.208.130:8080/api/";
	
	public List<String> drr = new ArrayList<String>();//���ѹ�����ͼƬ��·��
	public List<Bitmap> bmp = new ArrayList<Bitmap>();//�������������ʾ��Բ�Ǿ���ͼƬ
	
	private float dp;
	//requestCode
	private static final int TAKE_PICTURE = 0;//���յ�������
	private static final int RESULT_LOAD_IMAGE = 1;//�����ѡȡ��������
	private static final int CUT_PHOTO_REQUEST_CODE = 2;//�ü�ͼƬ��������
	private static final int CHOOSE_FRIEND = 4;//ѡ��������Χ��������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_question);
		
		init();
	}
	
	public void init(){
//		dp = getResources().getDimension(R.dimen.dp);//�߿��С
		
		chooseFriend=(TextView) findViewById(R.id.choose_friend);
		editQuestionContent=(EditText) findViewById(R.id.edit_ask_question_text);
		sendQuestionBtn=(Button) findViewById(R.id.send_btn);
	    sendCancelBtn=(Button) findViewById(R.id.cancel_send_btn);
	    camera=(ImageView) findViewById(R.id.send_cose_camera);
	    //��ʼ��gridview
//		selectimg_horizontalScrollView = (HorizontalScrollView) findViewById(R.id.selectimg_horizontalScrollView);
	    gridview=(GridView) findViewById(R.id.noScrollgridview);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridviewInit();
	     
//	    editHelpContent.setFocusable(true);
//	    editHelpContent.setFocusableInTouchMode(true); 
	    
	    /**
	     * ������½����ö�˭������������
	     */
	    chooseFriend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SendQuestionActivity.this, ChooseHelpRange.class);
				startActivityForResult(intent, CHOOSE_FRIEND);
			}
		});
	    
	    /**
	     * ���cameraͼ�꣬����ѡ�����������ϴ����ߴ����ѡ����Ƭ�ϴ�����ȡ��
	     */
	     camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new PopupWindows(SendQuestionActivity.this, camera);
			}
		});
	     
	     /**
	      *	���������ı��ı仯����ȷ�ϰ�ť��������ɫ
	      */
	     editQuestionContent.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
					// ���������ı��ı仯����ȷ�ϰ�ť��������ɫ
					String str = editQuestionContent.getText().toString();
					if(str.equals("")){
						sendQuestionBtn.setTextColor(getResources().getColor(R.color.gray));
					}else{
						sendQuestionBtn.setTextColor(getResources().getColor(R.color.dark_gray));
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
				SendQuestionActivity.this.finish();
			}
		});
	     
	     /**
	      * ������Ͱ�ť�������ݷ��ͳ�ȥ(δʵ�֣�����)
	      */
	     sendQuestionBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String content = editQuestionContent.getText().toString();
				if (content.equals("")) {
					Toast.makeText(getApplicationContext(), "���������ݲ���Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				Double loaitude = LocationService.getGeoLat();
				Double longitude = LocationService.getGeoLng();
				String city = LocationService.getCityCode();
				String address = LocationService.getAddress();
				
				AjaxParams params=new AjaxParams();
				params.put("username", "xiaoming");
				params.put("datetime", "2014-12-16");
				params.put("type", "1");
				params.put("content", content);
				params.put("longitude", longitude.toString());
				params.put("latitude", loaitude.toString());
				params.put("city", city);
				params.put("address", address);
				
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
//				path = file.getPath();//getpath �õ���д��·�������ݵ�ǰĿ¼λ�ÿ�����д·�����õ����·����
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
			startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);
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
			if (drr.size() < 9 && resultCode == -1) {
				startPhotoZoom(photoUri);
			}
			break;
		case RESULT_LOAD_IMAGE:
			if (drr.size() < 9 && resultCode == RESULT_OK && null != data) {
				Uri uri = data.getData();//�õ�ͼƬ��·��
				if (uri != null) {
					startPhotoZoom(uri);
				}
			}
			break;
		case CUT_PHOTO_REQUEST_CODE:
			if (resultCode == RESULT_OK && null != data) {
				//��õ�ǰ��bitmap
				Bitmap map = Bimp.getLoacalBitmap(drr.get(drr.size() - 1));
				//��photoactivity�е�List<Bitmap> bitmap�����һ��bitmap
//				PhotoActivity.bitmap.add(map);
				//�õ�Բ�ǵ�С����ͼƬ
				map = Bimp.createFramedPhoto(480, 480, map,(int) (dp * 1.6f));
				bmp.add(map);
				gridviewInit();//����������gridview
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
//			LinearLayout ll_popup = (LinearLayout) view
//					.findViewById(R.id.ll_popup);
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
					Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(i, RESULT_LOAD_IMAGE);
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
	public class GridAdapter extends BaseAdapter {
		/*����д�õĲ����ļ����������������ǵ�������ʱ���������ڴ������Զ���ؼ��������Ҫ�õ�LayoutInflater��
		 * LayoutInflater��Android���ǡ���չ������˼������������findViewById().
		 * ��ͬ����LayoutInflater��������ò����ļ�����ģ���findViewById()��������þ���ؼ��ġ�
		 * LayoutInflater������BaseAdapter��getView�������õ���������ȡ����View�����ء�*/
		private LayoutInflater listContainer;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public class ViewHolder {
			public ImageView image;
			public Button bt;
		}

		public GridAdapter(Context context) {
			listContainer = LayoutInflater.from(context);
		}
		public GridAdapter(Context context,ArrayList<Bitmap>bmpList){
			listContainer = LayoutInflater.from(context);
		}
		public int getCount() {
			if (bmp.size() < 9) {
				return bmp.size() + 1;
			} else {
				return bmp.size();
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
//						PhotoActivity.bitmap.remove(sign);
						bmp.get(sign).recycle();
						bmp.remove(sign);
						drr.remove(sign);

						gridviewInit();
					}
				});
			}
			return convertView;
		}
	}
	/**
	 * ��ʼ���͸����������gridview
	 */
	public void gridviewInit() {
		adapter = new GridAdapter(this);
		adapter.setSelectedPosition(0);
		
		//gridview��size�����9
		int size = 0;
		if (bmp.size() < 9) {
			size = bmp.size() + 1;//ѡ�е�ͼƬ��������һ���������ͼƬ��ͼƬ
		} else {
			size = bmp.size();
		}
		//����getLayoutParams()��������ȡ�ؼ���LayoutParams
//		LayoutParams params = gridview.getLayoutParams();
//		final int width = size * (int) (dp * 9.4f);
//		params.width = width;
//		gridview.setLayoutParams(params);
//		gridview.setColumnWidth((int) (dp * 9.4f));
//		gridview.setStretchMode(GridView.NO_STRETCH);
//		gridview.setNumColumns(size);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(this);

		//view �������ʱ���ã�ÿ��view�ı�ʱ�������
/*		selectimg_horizontalScrollView.getViewTreeObserver()
				.addOnPreDrawListener(// �������
						new OnPreDrawListener() {
							public boolean onPreDraw() {
								selectimg_horizontalScrollView.scrollTo(width,
										0);
								selectimg_horizontalScrollView
										.getViewTreeObserver()
										.removeOnPreDrawListener(this);
								return false;
							}
						});*/
	}
	/**
	 * ���item�������ͼƬ
	 */
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(SendQuestionActivity.this
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		if (arg2 == bmp.size()) {
			String sdcardState = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
				new PopupWindows(SendQuestionActivity.this, gridview);
			} else {
				Toast.makeText(getApplicationContext(), "sdcard�Ѱγ�������ѡ����Ƭ",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Intent intent = new Intent(SendQuestionActivity.this,
					PhotoActivity.class);

			intent.putExtra("ID", arg2);
			startActivity(intent);
		}
	}
}
