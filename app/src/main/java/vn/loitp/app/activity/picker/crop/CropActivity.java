package vn.loitp.app.activity.picker.crop;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.yalantis.ucrop.util.FileUtils;

import java.io.File;

import androidx.annotation.Nullable;
import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.picker.crop.CropImage;
import vn.loitp.picker.crop.CropImageView;
import vn.loitp.picker.crop.LGalleryActivity;
import vn.loitp.views.LToast;

public class CropActivity extends BaseFontActivity {
    private ImageView iv;
    private final int REQUEST_CODE_GET_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv = findViewById(R.id.iv);
        findViewById(R.id.bt_crop).setOnClickListener(view -> {
            crop();
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_crop;
    }

    private void crop() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startActivityForResult(new Intent(activity, LGalleryActivity.class), REQUEST_CODE_GET_FILE);
                        LActivityUtil.tranIn(activity);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        LToast.showShort(activity, "Error onPermissionDenied WRITE_EXTERNAL_STORAGE", R.drawable.bkg_horizontal);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        LToast.showShort(activity, "Error onPermissionDenied WRITE_EXTERNAL_STORAGE", R.drawable.bkg_horizontal);
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LLog.d(TAG, "onActivityResult requestCode " + requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            LLog.e(TAG, "data == null return");
            return;
        }
        if (requestCode == REQUEST_CODE_GET_FILE) {
            if (data.getExtras() == null) {
                LLog.e(TAG, "data.getExtras() == null return");
                return;
            }
            final String filePath = data.getExtras().getString(LGalleryActivity.RETURN_VALUE);
            if (filePath == null) {
                LLog.e(TAG, "filePath == null return");
                return;
            }
            LLog.d(TAG, "filePath " + filePath);
            final File file = new File(filePath);
            if (!file.exists()) {
                LLog.e(TAG, "file is not exists");
                return;
            }
            final Uri imageUri = Uri.fromFile(file);
            if (imageUri == null) {
                LLog.e(TAG, "imageUri == null");
                return;
            }
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setBorderLineColor(Color.WHITE)
                    .setBorderLineThickness(2)
                    .setCircleSize(30)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setCircleColor(Color.WHITE)
                    .setBackgroundColor(Color.argb(200, 0, 0, 0))
                    .start(activity);
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) {
                final String realPath = FileUtils.getPath(this, result.getUri());
                if (realPath == null) {
                    return;
                }
                final File file = new File(realPath);
                LLog.d(TAG, "onActivityResult file " + file.getPath());
                LImageUtil.load(activity, file, iv);
            }
        }
    }
}