package com.gyanbooster.view_controller.fragment.profile_fragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gyanbooster.R;
import com.gyanbooster.constants.Constants;
import com.gyanbooster.dao.profile_response.DashboardResponse;
import com.gyanbooster.dao.profile_response.ProfileDetailResponse;
import com.gyanbooster.dao.profile_response.UserProfileData;
import com.gyanbooster.databinding.FragmentProfileDetailsBinding;
import com.gyanbooster.databinding.FragmentYourCoursesBinding;
import com.gyanbooster.model.ProfileDetailsModel;
import com.gyanbooster.shared_preference.GyanBoosterPreferences;
import com.gyanbooster.utility.Util;
import com.gyanbooster.view_controller.fragment.BaseFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Observable;
import java.util.Observer;

import static com.gyanbooster.utility.Util.isEmailValid;
import static com.gyanbooster.utility.Util.isValidMobile;
import static com.gyanbooster.utility.Util.showCenteredToast;

public class FragmentProfileDetails extends BaseFragment implements Observer, View.OnClickListener {

    private FragmentProfileDetailsBinding fragmentProfileDetailsBinding;
    private View v;
    private ProfileDetailsModel profileDetailsModel = new ProfileDetailsModel();
    public static final int PICK_IMAGE = 1;
    private File imageFile;
    private String profileImageFileName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentProfileDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_details, container, false);
        v = fragmentProfileDetailsBinding.getRoot();
        fragmentProfileDetailsBinding.setClick(this);
        profileDetailsModel.addObserver(this);

        setData();

        return v;
    }

    private void setData() {
        fragmentProfileDetailsBinding.etFirstName.setText(GyanBoosterPreferences.getUserFrontName());
        fragmentProfileDetailsBinding.etEmail.setText(GyanBoosterPreferences.getUserEmail());
        fragmentProfileDetailsBinding.etPhone.setText(GyanBoosterPreferences.getUserPhone());

        String thumbnail = Constants.THUMBNAIL_BASE_URL + GyanBoosterPreferences.getUserImage();
        Glide.with(this).load(thumbnail).dontAnimate().into(fragmentProfileDetailsBinding.imgUserImage);
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.profile);
    }

    @Override
    public void update(Observable observable, Object o) {
        Util.dismissProDialog();
        try {
            if (o != null) {

                if (o instanceof ProfileDetailResponse) {
                    ProfileDetailResponse profileDetailResponse = ((ProfileDetailResponse) o);
                    if (profileDetailResponse.getStatus().equalsIgnoreCase("1")) {
                        Util.showCenteredToast(getActivity(), profileDetailResponse.getMsg());
                        Util.showProDialog(getActivity());
                        profileDetailsModel.dashboardApi(getActivity());

                    }else{
                        Util.showCenteredToast(getActivity(), profileDetailResponse.getMsg());

                    }
                }else if (o instanceof DashboardResponse){
                    DashboardResponse dashboardResponse = ((DashboardResponse) o);
                    UserProfileData userProfileData = dashboardResponse.getUser();
                    GyanBoosterPreferences.setUserId(userProfileData.getFront_user_id());
                    GyanBoosterPreferences.setUserFrontName(userProfileData.getFront_user_fname());
                    GyanBoosterPreferences.setUserEmail(userProfileData.getFront_user_email());
                    GyanBoosterPreferences.setUserPhone(userProfileData.getFront_user_phn());
                    GyanBoosterPreferences.setUserImage(userProfileData.getUser_image());
                    GyanBoosterPreferences.savePreferencese();
                    setData();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            int vId = v.getId();
            switch (vId) {
                case R.id.txtSubmit:
                    String fName = fragmentProfileDetailsBinding.etFirstName.getText().toString();
                    String password = fragmentProfileDetailsBinding.etPassword.getText().toString();
                    String email = fragmentProfileDetailsBinding.etEmail.getText().toString();
                    String mob = fragmentProfileDetailsBinding.etPhone.getText().toString();
                    if (isValid(fName, email, mob)) {
                        Util.showProDialog(getActivity());
                        profileDetailsModel.profileDetailsApi(getActivity(), fName, "", email, mob, password, imageFile,profileImageFileName ,GyanBoosterPreferences.getUserImage());
                    }
                    break;
                case R.id.txtChoosePicture:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValid(String fullName, String email, String mobileNo) throws Exception {
        boolean validation_detials_flag = false;
        if (Util.isDeviceOnline(getActivity())) {
            if (TextUtils.isEmpty(fullName)) {
                showCenteredToast(getActivity(), getString(R.string.name_validation_message));
                fragmentProfileDetailsBinding.etFirstName.requestFocus();
            } else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(mobileNo)) {
                showCenteredToast(getActivity(), getString(R.string.email_phone_validation_message));
                fragmentProfileDetailsBinding.etEmail.requestFocus();
            } else if (!TextUtils.isEmpty(email) && !isEmailValid(email)) {
                showCenteredToast(getActivity(), getString(R.string.invalid_email));
                fragmentProfileDetailsBinding.etEmail.requestFocus();
            } else if (!TextUtils.isEmpty(mobileNo) && !isValidMobile(mobileNo)) {
                showCenteredToast(getActivity(), getString(R.string.mobile_number));
                fragmentProfileDetailsBinding.etPhone.requestFocus();
            } else {
                validation_detials_flag = true;
            }
        } else {
            showCenteredToast(getActivity(), getString(R.string.network_connection));
        }
        return validation_detials_flag;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            Uri selectedImageURI = data.getData();

            imageFile = new File(getPath(getActivity(), selectedImageURI));
            if (imageFile != null) {
                fragmentProfileDetailsBinding.txtFileName.setText(imageFile.getName());
                profileImageFileName = imageFile.getName();
            }

            Glide.with(getActivity()).load(selectedImageURI).dontAnimate().into(fragmentProfileDetailsBinding.imgUserImage);


        }
    }

    private String getPath(Context context, Uri uri) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String uri_authority = uri.getAuthority();
                String selection = "_id=?";
                if ("com.android.providers.media.documents".equals(uri_authority)) {
                    String docId = DocumentsContract.getDocumentId(uri);
                    String[] split = docId.split(":");
                    String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                } else if ("media".equals(uri_authority)) {
                    String docId = uri.getLastPathSegment();
                    String[] selectionArgs = new String[]{docId};
                    return getDataColumn(context, uri, selection, selectionArgs);
                } else if ("com.android.providers.downloads.documents".equals(uri_authority)) {
                    String id = DocumentsContract.getDocumentId(uri);
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                } else if ("com.google.android.apps.docs.storage".equals(uri_authority) || uri_authority.equals("com.dropbox.android.FileCache") || uri_authority.equals("com.google.android.apps.docs.storage.legacy") || uri_authority.equals("com.microsoft.skydrive.content.external") || uri_authority.equals("com.google.android.apps.photos.contentprovider") || uri_authority.equals("cn.wps.moffice_eng.fileprovider")) {
                    Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
                    returnCursor.moveToFirst();
                    String file_name = returnCursor.getString(returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    long file_size = returnCursor.getLong(returnCursor.getColumnIndex(OpenableColumns.SIZE));
                    if (!(file_size > Constants.ATTACHMENT_FILE_SIZE)) {
                        FileInputStream in = (FileInputStream) context.getContentResolver().openInputStream(uri);
                        String path = Environment.getExternalStorageDirectory() + File.separator + Constants.APP_FOLDER_NAME + File.separator + Constants.ATTACHMENTS_FOLDER_NAME;
                        File file = new File(path);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        FileOutputStream out = new FileOutputStream(new File(path, file_name));
                        FileChannel inChannel = in.getChannel();
                        FileChannel outChannel = out.getChannel();
                        inChannel.transferTo(0, inChannel.size(), outChannel);
                        in.close();
                        out.close();
                        return path + File.separator + file_name;
                    } else {
                        Util.showCenteredToast(null, getString(R.string.file_size_alert));
                    }

                } else {
                    return uri.getPath();
                }

            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                String[] proj = {MediaStore.Images.Media.DATA};
                String result = null;
                CursorLoader cursorLoader = new CursorLoader(context, uri, proj, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();

                if (cursor != null) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    result = cursor.getString(column_index);
                }
                return result;
            }

        } catch (Exception e) {
            Util.dismissProDialog();
            e.printStackTrace();
        }


        return null;
    }

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

}
