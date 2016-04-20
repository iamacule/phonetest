package phamhungan.com.phonetestv3.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import phamhungan.com.phonetestv3.Main;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.fragment.SingleTest;
import phamhungan.com.phonetestv3.util.AminationUtil;
import phamhungan.com.phonetestv3.util.DataUtil;
import phamhungan.com.phonetestv3.util.DialogUtil;
import phamhungan.com.phonetestv3.util.EventUtil;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by MrAn PC on 20-Jan-16.
 */
public class ChooserActivity extends AppCompatActivity implements View.OnClickListener {
    private Button butInfo;
    private Button butSingle;
    private Button butFull;
    private ChooserActivity context;
    private String stringExtra;
    private ImageView imgLogo;
    private RelativeLayout main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_chooser);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            stringExtra = extras.getString(getResources().getString(R.string.which_test));
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra(getResources().getString(R.string.which_test), stringExtra);
            startActivity(intent);
        }
        context = this;
        butInfo = (Button) findViewById(R.id.butInfo);
        butSingle = (Button) findViewById(R.id.butSingle);
        butFull = (Button) findViewById(R.id.butFull);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        main = (RelativeLayout) findViewById(R.id.main);
        imgLogo.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.icon), ScreenUtil.getScreenWidth(getWindowManager()) / 4));
        loadAdsMob();
        setOnClick();
    }

    private void loadAdsMob() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Log.d("Google AdsMob", "Started");
    }

    private void setOnClick() {
        butInfo.setOnClickListener(this);
        butSingle.setOnClickListener(this);
        butFull.setOnClickListener(this);
    }

    public void showDialogOK(String s) {
        AlertDialog.Builder al1 = new AlertDialog.Builder(this);
        al1.setTitle(getResources().getString(R.string.guide));
        al1.setMessage(s);
        al1.setPositiveButton(getResources().getString(R.string.start), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataUtil.whichTest = true;
                Intent intent = new Intent(context, TestActivity.class);
                intent.putExtra(getResources().getString(R.string.which_test), getResources().getString(R.string.full_test));
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        AlertDialog alert = al1.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_actions_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemShare:
                ScreenUtil.showSharedialog(this);
                break;
            case R.id.itemAbout:
                ScreenUtil.goAbout(this);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!checkRating())
            DialogUtil.showRatingDialog(this);
        else
            EventUtil.backPressExitApp(this);
    }

    private boolean checkRating() {
        return Main.preferences.getRating();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.butInfo:
                intent = new Intent(this, PhoneInfoActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.butSingle:
                DataUtil.whichTest = false;
                intent = new Intent(this, TestActivity.class);
                intent.putExtra(getResources().getString(R.string.which_test), getResources().getString(R.string.single_test));
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.butFull:
                showDialogOK(Html.fromHtml(getResources().getString(R.string.notice_full_test)).toString());
                break;
        }
    }
}
