package at.fhjoanneum.android.sqlite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import at.fhjoanneum.android.sqlite.data.Cocktail;

/**
 * The connection to the phone camera and the gallery gets managed in this
 * activity. It is only shown in an dilog which is set in the android manifest.
 * If you do not like the standard photos of the cocktails you can make your own
 * picture or use one in your phones gallery. The pictures gets saved or copied
 * to a new location in your DCIM folder which gets not shown in your gallery
 * automatically. If you delete one of this pictures the standard photo gets
 * taken in your app.
 * 
 * @author Manuela Mayer
 * @version 1.0
 *
 */
public class PhotoCameraGalleryActivity extends Activity {

	private ImageView ivCocktailPic;
	private Bitmap newPic = null;
	private static Cocktail cocktail;
	private Button btCancel;
	private Button btNewPic;
	private Button btGallery;
	private Button btChoosePic;

	private boolean picChoosen = false;

	// Album
	private static final String JPEG_FILE_PREFIX = "co_";
	private static final String JPEG_FILE_SUFFIX = "2.jpg";

	/**
	 * This procedure sets general settings of the activity like no title or you
	 * can not finish the dialog by clicking outside of it. It calls further
	 * methods to work with the views from the xml file and the pictures.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setFinishOnTouchOutside(false);
		setContentView(R.layout.dialog_change_pic);
		findViewsById();
		setPic();
		setListeners();
	}

	/**
	 * Sets the image view of the dialog with the correct picture of the
	 * cocktail. This means if a cocktail has a self made picture, this picture
	 * gets shown and if it has only the standard picture, this one gets shown.
	 * 
	 */
	private void setPic() {
		String picName = JPEG_FILE_PREFIX
				+ cocktail.getName().toLowerCase().replace(" ", "")
						.replace("-", "");

		String root = Environment.getExternalStorageDirectory().toString();
		File imageFile = new File(root + "/DCIM/CocktailClassics/" + picName
				+ JPEG_FILE_SUFFIX);

		if (imageFile.exists()) {
			Bitmap myBitmap = BitmapFactory.decodeFile(imageFile
					.getAbsolutePath());
			ivCocktailPic.setImageBitmap(myBitmap);

		} else {
			int imageRessourceID = this.getResources().getIdentifier(picName,
					"drawable", this.getPackageName());
			ivCocktailPic.setImageResource(imageRessourceID);
		}
	}

	/**
	 * Connects the fields of the dialog_change_pic.xml with the fragment.
	 */
	private void findViewsById() {
		btNewPic = (Button) findViewById(R.id.bt_make_pic2);
		btGallery = (Button) findViewById(R.id.bt_choose_pic2);
		btCancel = (Button) findViewById(R.id.bt_cancel2);
		btChoosePic = (Button) findViewById(R.id.bt_save_pic2);
		ivCocktailPic = (ImageView) findViewById(R.id.iv_cocktail_pic);
	}

	/**
	 * Sets listeners for every button in the xml file
	 */
	private void setListeners() {
		btCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onCancelClicked(v);
			}
		});

		btNewPic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onNewPicClicked(v);
			}
		});

		btGallery.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onGalleryClicked(v);
			}
		});

		btChoosePic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onChoosePicClicked(v);
			}
		});
	}

	/**
	 * The current activity gets cancelled which means we return to the
	 * CocktailDetailFragment
	 */
	private void onCancelClicked(View v) {
		this.finish();
	}

	/**
	 * Values get set to start the camera from the mobile phone
	 */
	private void onNewPicClicked(View v) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		File f = new File(android.os.Environment.getExternalStorageDirectory(),
				"temp.jpg");

		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

		startActivityForResult(intent, 1);
	}

	/**
	 * Values get set to start the gallery from the mobile phone
	 */
	private void onGalleryClicked(View v) {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		startActivityForResult(intent, 2);
	}

	/**
	 * Picture which was chosen or made gets copied in our folder in the
	 * gallery.
	 */
	private void onChoosePicClicked(View v) {
		if (picChoosen) {
			String root = Environment.getExternalStorageDirectory().toString();
			File myDir = new File(root + "/DCIM/CocktailClassics");
			myDir.mkdirs();
			String fname = getFileName();
			File file = new File(myDir, fname);
			if (file.exists())
				file.delete();
			try {
				FileOutputStream out = new FileOutputStream(file);
				newPic.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.flush();
				out.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.finish();
	}

	/**
	 * Creates the correct file name for the pictures. If a picture with this
	 * name gets deleted or renamed, the app uses the standard photo for the
	 * cocktail
	 * 
	 * @return Name of the photo
	 */
	private String getFileName() {
		String name;
		if (cocktail != null) {
			name = JPEG_FILE_PREFIX
					+ cocktail.getName().toLowerCase().replace(" ", "")
					+ JPEG_FILE_SUFFIX;
		} else {
			name = String.valueOf(System.currentTimeMillis()) + ".jpg";
		}
		return name;
	}

	/**
	 * It depends on which mode was chosen before. If you want to take a new
	 * picture, the camera gets opened and you can make a new picture, if you
	 * want to take a picture from your phone gallery, the gallery gets opened
	 * and you can choose one picture.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {

				String picturePath = null;
				File f = new File(Environment.getExternalStorageDirectory()
						.toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}
				try {
					Bitmap bitmap;
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
					bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
							bitmapOptions);
					picturePath = android.os.Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "Phoenix" + File.separator + "default";
					f.delete();
					OutputStream outFile = null;
					File file = new File(picturePath, getFileName());
					try {
						outFile = new FileOutputStream(file);
						bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
						outFile.flush();
						outFile.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					newPic = bitmap;

				} catch (Exception e) {
					e.printStackTrace();
				}

				ivCocktailPic.setImageBitmap(newPic);

			} else if (requestCode == 2) {
				Uri selectedImage = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor c = getContentResolver().query(selectedImage, filePath,
						null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
				String picturePath = c.getString(columnIndex);
				c.close();
				Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
				Log.w("path of image from gallery", picturePath + "");

				newPic = thumbnail;

			}
		}

		if (newPic != null) {
			picChoosen = true;
			ivCocktailPic.setImageBitmap(newPic);
		}
	}

	/**
	 * Gets the cocktail from the calling fragment to use the name of it.
	 * 
	 * @param cock
	 *            Cocktail you want to change the picture
	 */
	public static void setCocktail(Cocktail cock) {
		cocktail = cock;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
