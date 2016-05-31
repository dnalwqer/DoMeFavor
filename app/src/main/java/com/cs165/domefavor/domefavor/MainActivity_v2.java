package com.cs165.domefavor.domefavor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;
import com.lhh.apst.library.Margins;

/**
 *
 * Created by Jilai Zhou on 5/23/2016.
 */
public class MainActivity_v2 extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        GoogleApiClient.OnConnectionFailedListener{

    public AdvancedPagerSlidingTabStrip mAPSTS;
    public APSTSViewPager mVP;
    private String mID;
    private Fragment mFirstFragment = null;
    private Fragment mSecondFragment = null;
    private Fragment mThirdFragment = null;
    private Fragment mFourthFragment = null;
    private ImageView mIvCenterBtn = null;
    private GoogleApiClient mGoogleApiClient;


    private static final int VIEW_FIRST = 0;
    private static final int VIEW_SECOND = 1;
    private static final int VIEW_THIRD = 2;
    private static final int VIEW_FOURTH = 3;

    private static final int VIEW_SIZE = 4;
    private static final String TAG = "MainActivity";
    private static final int REQUEST_FINE_LOCATION =122;

    private int mSize = 0;

    public Fragment getFragment(int id){
        switch(id){
            case 0:
                return mFirstFragment;
            case 1:
                return mSecondFragment;
            case 2:
                return mThirdFragment;
            case 3:
                return mFourthFragment;
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_tab);
        findViews();
        init();
        Intent intent = getIntent();
        Bundle mBundle = intent.getExtras();
        mID = mBundle.getString("Email");


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void setABTitle(String str){
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.abs_layout);
            TextView v = (TextView) findViewById(R.id.abs_title);
            v.setText(str);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void findViews(){
        mAPSTS = (AdvancedPagerSlidingTabStrip)findViewById(R.id.tabs);
        mVP = (APSTSViewPager)findViewById(R.id.vp_main);
        mIvCenterBtn = (ImageView)findViewById(R.id.ivCenterBtn);
    }

    private void init(){
        mIvCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity_v2.this, "Center Btn is Clicked.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity_v2.this, NewTaskActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("Email", mID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mSize = getResources().getDimensionPixelSize(R.dimen.weibo_tab_size);
        mVP.setOffscreenPageLimit(VIEW_SIZE);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        mVP.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

        adapter.notifyDataSetChanged();
        mAPSTS.setViewPager(mVP);
        mAPSTS.setOnPageChangeListener(this);
        mVP.setCurrentItem(VIEW_FIRST);
//        mAPSTS.showDot(VIEW_FIRST, "99+");
    }

    public String getID(){return mID;}

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                getSupportActionBar().setTitle("HOME");
                break;
            case 1:
                getSupportActionBar().setTitle("LIST");
                break;
            case 2:
                getSupportActionBar().setTitle("MAP");
                break;
            case 3:
                getSupportActionBar().setTitle("PROFILE");
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_signout) {
            Bundle mbundle = new Bundle();
            int flag = 1;
            mbundle.putInt("isSignOut", flag);
            if (mGoogleApiClient.isConnected()) {
                signOut();
            }
            Intent intent = new Intent(this, SignInActivity.class);
            intent.putExtras(mbundle);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);

    }

    public class FragmentAdapter extends FragmentStatePagerAdapter implements
            AdvancedPagerSlidingTabStrip.IconTabProvider,
            AdvancedPagerSlidingTabStrip.LayoutProvider,
            AdvancedPagerSlidingTabStrip.TipsProvider{

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        if(null == mFirstFragment)
                            mFirstFragment = new FragmentHistory();
                        return mFirstFragment;

                    case VIEW_SECOND:
                        if(null == mSecondFragment)
                            mSecondFragment = new FragmentTaskList();
                        return mSecondFragment;

                    case VIEW_THIRD:
                        if(null == mThirdFragment)
                            mThirdFragment = new FragmentMap();
                        return mThirdFragment;

                    case VIEW_FOURTH:
                        if(null == mFourthFragment)
                            mFourthFragment = new FragmentProfile();
                        return mFourthFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return VIEW_SIZE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        return  "Home";
                    case  VIEW_SECOND:
                        return  "List";
                    case  VIEW_THIRD:
                        return  "Map";
                    case  VIEW_FOURTH:
                        return  "Profile";
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public float getPageWeight(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        return  0.92f;
                    case  VIEW_SECOND:
                        return  1.0f;
                    case  VIEW_THIRD:
                        return  1.0f;
                    case  VIEW_FOURTH:
                        return  0.92f;
                    default:
                        break;
                }
            }
            return 1.0f;
        }

        @Override
        public int[] getPageRule(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_LEFT};
                    case  VIEW_SECOND:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_LEFT};
                    case  VIEW_THIRD:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_RIGHT};
                    case  VIEW_FOURTH:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_RIGHT};
                    default:
                        break;
                }
            }
            return new int[0];
        }

        @Override
        public Margins getPageMargins(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        return  new Margins(getResources().getDimensionPixelSize(R.dimen.home_bar_icon_margins),0,0,0);
                    case VIEW_SECOND:
                        return  null;
                    case VIEW_THIRD:
                        return  null;
                    case VIEW_FOURTH:
                        return  new Margins(0,0,getResources().getDimensionPixelSize(R.dimen.home_bar_icon_margins),0);
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public Integer getPageIcon(int index) {
            if(index >= 0 && index < VIEW_SIZE){
                switch (index){
                    case  VIEW_FIRST:
                        return  R.mipmap.ic_home_gray;
                    case VIEW_SECOND:
                        return  R.mipmap.ic_list_gray;
                    case VIEW_THIRD:
                        return  R.mipmap.ic_map_gray;
                    case VIEW_FOURTH:
                        return  R.mipmap.ic_perm_identity_gray;
                    default:
                        break;
                }
            }
            return 0;
        }

        @Override
        public Integer getPageSelectIcon(int index) {
            if(index >= 0 && index < VIEW_SIZE){
                switch (index){
                    case  VIEW_FIRST:
                        return  R.mipmap.ic_home_orange;
                    case VIEW_SECOND:
                        return  R.mipmap.ic_list_orange;
                    case VIEW_THIRD:
                        return  R.mipmap.ic_map_orange;
                    case VIEW_FOURTH:
                        return  R.mipmap.ic_perm_identity_orange;
                    default:
                        break;
                }
            }
            return 0;
        }

        @Override
        public Rect getPageIconBounds(int position) {
            return new Rect(0, 0, mSize, mSize);
        }

        @Override
        public int[] getTipsRule(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_LEFT};
                    case  VIEW_SECOND:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_LEFT};
                    case  VIEW_THIRD:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_RIGHT};
                    case  VIEW_FOURTH:
                        return  new int[]{
                                RelativeLayout.ALIGN_PARENT_RIGHT};
                    default:
                        break;
                }
            }
            return new int[0];
        }

        @Override
        public Margins getTipsMargins(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case VIEW_FIRST:
                        return new Margins(4 *getResources().getDimensionPixelSize(R.dimen.psts_dot_m_right), 0, 0, 0);
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public Drawable getTipsDrawable(int position) {
            return null;
        }
    }

}
