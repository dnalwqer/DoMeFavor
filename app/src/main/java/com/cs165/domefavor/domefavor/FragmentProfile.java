package com.cs165.domefavor.domefavor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.marvinlabs.widget.floatinglabel.edittext.FloatingLabelEditText;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 *
 * Created by Jilai Zhou on 5/19/2016.
 */
public class FragmentProfile extends Fragment implements FloatingLabelEditText.EditTextListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView name, email;
    private ImageView image;
    private Button save, cancel;
    private Bundle mbundle;
    private SegmentedGroup segment;
    private String gender = "Male";
    private String age = "18";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //inflate the fragment in the UI
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Intent intent = getActivity().getIntent();
        mbundle = intent.getExtras();

        name = (TextView) view.findViewById(R.id.profile_name);
        name.setText(mbundle.getString("Name"));

        email = (TextView) view.findViewById(R.id.profile_email);
        email.setText(mbundle.getString("Email"));

        image = (ImageView) view.findViewById(R.id.imageProfile);
        Uri uri = mbundle.getParcelable("Photo");
        if (uri != null)
            new downloadTask().execute(uri.toString());
        else {
            image.setImageResource(R.drawable.default_profile);
        }

        segment = (SegmentedGroup) view.findViewById(R.id.segment);
        segment.setOnCheckedChangeListener(this);
        save = (Button) view.findViewById(R.id.saveprofile);
        save.setOnClickListener(this);

        ((FloatingLabelEditText) view.findViewById(R.id.edit_text2)).setEditTextListener(this);
        return view;
    }

    @Override
    public void onTextChanged(FloatingLabelEditText source, String text) {
        age = text;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveprofile) {

            PriceItem item = new PriceItem(0.0, mbundle.getString("Email"), age, gender);
            new saveProfileTask().execute(item);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.segmentbutton1:
                gender = "Male";
                break;
            case R.id.segmentbutton2:
                gender = "Female";
                break;
            case R.id.segmentbutton3:
                gender = "Unknown";
                break;
            default:
        }
    }

    class saveProfileTask extends AsyncTask<PriceItem, Void, Void> {
        @Override
        protected Void doInBackground(PriceItem... params) {
            try {
                Server.saveProfile(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getActivity(), "Saved successfully", Toast.LENGTH_SHORT).show();
        }


    }

    class downloadTask extends AsyncTask<String, Void, Uri> {
        @Override
        protected Uri doInBackground(String... params) {
            try {
                String name = "photo";
                FileOutputStream fos = getActivity().openFileOutput(name, Context.MODE_WORLD_READABLE);
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                if (conn.getResponseCode() == 200) {
                    InputStream is = conn.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    is.close();
                    fos.close();
                    return Uri.fromFile(getActivity().getFileStreamPath(name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Uri uri) {
            if (uri != null) {
                image.setImageURI(uri);
            }
        }
    }
}
