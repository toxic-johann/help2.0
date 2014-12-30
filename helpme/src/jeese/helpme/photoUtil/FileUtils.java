/*package jeese.helpme.photoUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.os.Environment;

public class FileUtils {
	
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/formats/";
	public static String SDPATH1 = Environment.getExternalStorageDirectory()
        + "/myimages/";
	*//**
	 * ����ͼƬ
	 * @param bm
	 * @param picName
	 *//*
	public static void saveBitmap(Bitmap bm, String picName) {
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPEG"); 
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			//ͼƬ�Ѿ�����
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	*//**
	 *��sd��Ŀ¼���洴���ļ���
	 *//*
	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			System.out.println("createSDDir:" + dir.getAbsolutePath());//��ȡ����·��
			System.out.println("createSDDir:" + dir.mkdir());//��sd��Ŀ¼���洴���ļ���
		}
		return dir;
	}

	*//**
	 *�ж�ͼƬ�ļ��Ƿ���� 
	 *//*
	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		public boolean isFile()���Դ˳���·������ʾ���ļ��Ƿ���һ����׼�ļ���
		 * ������ļ�����һ��Ŀ¼����������������ϵͳ�йصı�׼����ô���ļ��Ǳ�׼ �ļ���
		 * �� Java Ӧ�ó��򴴽������з�Ŀ¼�ļ�һ���Ǳ�׼�ļ��� 
		 
		file.isFile();
		
		 * public boolean exists()���Դ˳���·������ʾ���ļ���Ŀ¼�Ƿ���ڡ� 
		 * ���أ�
		 * ���ҽ����˳���·������ʾ���ļ���Ŀ¼����ʱ������ true�����򷵻� false 
		return file.exists();
	}
	
	*//**
	 *ɾ��ͼƬ�ļ�
	 *//*
	public static void delFile(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}
	*//**
	 *ɾ��Ŀ¼
	 *//*
	public static void deleteDir(String path) {
		File dir = new File(path);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // ɾ�������ļ�
			else if (file.isDirectory())
				deleteDir(path); // �ݹ�ķ�ʽɾ���ļ���
		}
		dir.delete();// ɾ��Ŀ¼����
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

}
*/
package jeese.helpme.photoUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class FileUtils {
	
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/formats/";

	public static void saveBitmap(Bitmap bm, String picName) {
		Log.e("", "����ͼƬ");
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPEG"); 
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			Log.e("", "�Ѿ�����");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static void delFile(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // ɾ�������ļ�
			else if (file.isDirectory())
				deleteDir(); // �ݹ�ķ�ʽɾ���ļ���
		}
		dir.delete();// ɾ��Ŀ¼����
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

}
