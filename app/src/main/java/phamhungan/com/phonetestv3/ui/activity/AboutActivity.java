package phamhungan.com.phonetestv3.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import butterknife.BindView;
import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.MrAnActivity;
import phamhungan.com.phonetestv3.ui.layout.MyTableLayout;
import phamhungan.com.phonetestv3.ui.layout.MyTableRow;
import phamhungan.com.phonetestv3.ui.layout.MyTitleTextView;
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
        imgLogo.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.icon), ScreenUtil.getScreenWidth(getWindowManager()) / 3));
        txtTitle = new MyTitleTextView(this, "GENERAL");
        MyTableLayout myTableLayout = new MyTableLayout(this);
        MyTableRow rowVersion;
        MyTableRow rowDevelop;
        MyTableRow rowContact;
        MyTableRow rowRemoveAds;
        MyTableRow rowRate;

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            rowVersion = new MyTableRow(this, "VERSION", pInfo.versionName, "");
            myTableLayout.addView(rowVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        rowDevelop = new MyTableRow(this, "DEVELOPED BY", "AN PHAM", "");
        rowContact = new MyTableRow(this, "CONTACT", "an.phamhung@gmail.com", "");
        rowContact.getValue().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "an.phamhung@gmail.com");

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        rowRemoveAds = new MyTableRow(this, "PREMIUM USER", "Remove Ads", "");
        rowRate = new MyTableRow(this, "RATE US", "RATE", "");

        myTableLayout.addView(rowDevelop);
        myTableLayout.addView(rowContact);
        myTableLayout.addView(rowRemoveAds);
        myTableLayout.addView(rowRate);

        lnMain.addView(txtTitle);
        lnMain.addView(myTableLayout);
    }

    @Override
    public void initializeChildValue() {

    }

    @Override
    public void initializeChildAction() {

    }
}
