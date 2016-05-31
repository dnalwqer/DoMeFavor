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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.marvinlabs.widget.floatinglabel.edittext.FloatingLabelEditText;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import info.hoang8f.android.segmented.SegmentedGroup;
import info.hoang8f.widget.FButton;

/**
 *
 * Fragment for showing the profile
 *
 * Created by Jilai Zhou on 5/19/2016.
 */
public class FragmentProfile extends Fragment implements FloatingLabelEditText.EditTextListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView name, email;
    private ImageView image;
    private FButton save;
    private Bundle mbundle;
    private SegmentedGroup segment;
    private TextView text;
    private RadioButton radio1, radio2, radio3;
    private String gender = "Male";
    private String age = "18";
    private FloatingLabelEditText editText;
    private Uri uri = null;

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
        uri = mbundle.getParcelable("Photo");
        if (uri != null)
            new downloadTask().execute(uri.toString());
        else {
            image.setImageResource(R.drawable.default_profile);
        }

        radio1 = (RadioButton) view.findViewById(R.id.segmentbutton1);
        radio2 = (RadioButton) view.findViewById(R.id.segmentbutton2);
        save = (FButton) view.findViewById(R.id.saveprofile);
        radio3 = (RadioButton) view.findViewById(R.id.segmentbutton3);
        editText = (FloatingLabelEditText) view.findViewById(R.id.edit_text2);
        text = (TextView) view.findViewById(R.id.profile_credit);

        segment = (SegmentedGroup) view.findViewById(R.id.segment);
        segment.setOnCheckedChangeListener(this);


        editText.setEditTextListener(this);
        save.setOnClickListener(this);

        new getProfile().execute(mbundle.getString("Email"));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        System.out.println("onViewCreated");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveprofile) {
            PriceItem item;
            if (uri != null) {
                item = new PriceItem(0.0, mbundle.getString("Email"), age.trim(), gender, uri.toString());
            }
            else {
                item = new PriceItem(0.0, mbundle.getString("Email"), age.trim(), gender, "N/A");
            }

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

    @Override
    public void onTextChanged(FloatingLabelEditText source, String text) {
        age = text;
    }

    /**
     * AsyncTask for saving the profile
     */
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

    /**
     * AsnycTask to get the profile
     */
    class getProfile extends AsyncTask<String, Void, PriceItem> {
        @Override
        protected PriceItem doInBackground(String... ID) {
            try {
                return Server.getProfile(ID[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(PriceItem priceItem) {
            if (priceItem != null) {
                editText.setInputWidgetText("        " + priceItem.getAge());
                switch (priceItem.getGender()) {
                    case "Male":
                        radio1.setChecked(true);
                        break;
                    case "Female":
                        radio2.setChecked(true);
                        break;
                    case "Unknown":
                        radio3.setChecked(true);
                        break;
                    default:
                        break;
                }
                text.setText(priceItem.getCreditMoney());
            }
        }
    }
}

