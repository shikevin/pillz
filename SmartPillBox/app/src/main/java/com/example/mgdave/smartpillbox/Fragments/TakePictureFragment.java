package com.example.mgdave.smartpillbox.Fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mgdave.smartpillbox.Constants;
import com.example.mgdave.smartpillbox.FileChooser;
import com.example.mgdave.smartpillbox.R;
import com.example.mgdave.smartpillbox.VideoUploadTask;

public class TakePictureFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "TakePictureFragmentTag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_picture, container, false);
        Button takePictureButton = (Button) view.findViewById(R.id.take_picture_button);
        takePictureButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_picture_button: {
                Intent intent = new Intent();
                intent.setType("image/*,video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture or Video"), 2);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            Uri uri = data.getData();
            String path = FileChooser.getPath(getActivity().getApplicationContext(), uri);
            ContentResolver contentResolver = getActivity().getContentResolver();
            String mimeType = contentResolver.getType(uri);
            VideoUploadTask uploadVideo = new VideoUploadTask(getActivity(),path, Constants.IMAGES_URL,mimeType);
            uploadVideo.execute("");
        }
    }
}
