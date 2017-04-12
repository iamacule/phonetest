package phamhungan.com.phonetestv3.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import butterknife.BindView;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.MrAnActivity;
import phamhungan.com.phonetestv3.ui.layout.MyTableLayout;
import phamhungan.com.phonetestv3.ui.layout.MyTableRow;
import phamhungan.com.phonetestv3.ui.layout.MyTitleTextView;
import phamhungan.com.phonetestv3.util.DialogLicense;
import phamhungan.com.phonetestv3.util.DialogUtil;
import phamhungan.com.phonetestv3.util.ResizeBitmap;
import phamhungan.com.phonetestv3.util.ScreenUtil;

/**
 * Created by MrAn PC on 27-Jan-16.
 */
public class AboutActivity extends MrAnActivity {
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.lnMain)
    LinearLayout lnMain;
    @BindView(R.id.butContact)
    Button butContact;
    @BindView(R.id.butPremium)
    Button butPremium;
    @BindView(R.id.butRate)
    Button butRate;
    @BindView(R.id.butSoftwareLicense)
    Button butSoftwareLicense;

    private MyTitleTextView txtTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getView() {
        return R.layout.layout_about;
    }

    @Override
    public void initializeChildView() {
        if (!needShowAds()) {
            butPremium.setVisibility(View.GONE);
        }
        imgLogo.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.icon), ScreenUtil.getScreenWidth(getWindowManager()) / 3));
        txtTitle = new MyTitleTextView(this, "GENERAL");
        MyTableLayout myTableLayout = new MyTableLayout(this);
        MyTableRow rowVersion;
        MyTableRow rowDevelop;

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            rowVersion = new MyTableRow(this, "VERSION", pInfo.versionName, "");
            myTableLayout.addView(rowVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        rowDevelop = new MyTableRow(this, "DEVELOPED BY", "AN PHAM", "");

        myTableLayout.addView(rowDevelop);

        lnMain.addView(txtTitle);
        lnMain.addView(myTableLayout);
    }

    @Override
    public void initializeChildValue() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions_bar, menu);
        for (int i = 0; i < menu.size(); i++){
            if(menu.getItem(i).getItemId()==R.id.itemAbout){
                menu.getItem(i).setVisible(false);
            }
            if(menu.getItem(i).getItemId()==R.id.removeAds){
                if(!needShowAds()){
                    menu.getItem(i).setVisible(false);
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemShare:
                ScreenUtil.showSharedialog(this);
                break;
            case R.id.removeAds:
                removeAds();
                break;
        }
        return true;
    }

    @Override
    public void initializeChildAction() {
        butContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","an.phamhung@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PhoneTest (Hardware) : Contacts");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        butPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAds();
            }
        });

        butRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.url_market))));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.url))));
                }
            }
        });

        butSoftwareLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLicense.Build dialog = new DialogLicense.Build(AboutActivity.this);
                dialog.show();
            }
        });
    }
}
