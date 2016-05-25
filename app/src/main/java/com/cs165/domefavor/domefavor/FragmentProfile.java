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
import android.widget.TextView;
import android.widget.Toast;

import com.marvinlabs.widget.floatinglabel.edittext.FloatingLabelEditText;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * Created by Jilai Zhou on 5/19/2016.
 */
public class FragmentProfile extends Fragment implements FloatingLabelEditText.EditTextListener, View.OnClickListener {

    private TextView name, email;
    private ImageView image;
    private Button save, cancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //inflate the fragment in the UI
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Intent intent = getActivity().getIntent();
        Bundle mbundle = intent.getExtras();

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

        save = (Button) view.findViewById(R.id.saveprofile);
        cancel = (Button) view.findViewById(R.id.cancelprofile);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

        ((FloatingLabelEditText) view.findViewById(R.id.edit_text2)).setEditTextListener(this);
        return view;
    }

    @Override
    public void onTextChanged(FloatingLabelEditText source, String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveprofile) {

        }
        else if (v.getId() == R.id.cancelprofile) {

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
