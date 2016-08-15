package phamhungan.com.phonetestv3.ui.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Set;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.ui.toast.Boast;
import phamhungan.com.phonetestv3.util.DataUtil;

/**
 * Created by MrAn PC on 26-Jan-16.
 */
public class BlueToothFragment extends BaseFragment{
    private ToggleButton tgBlue;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private TextView txtList ;
    private Button butVisible,butList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setFullScreen(false);
        txtList = (TextView)view.findViewById(R.id.txtList);
        tgBlue = (ToggleButton)view.findViewById(R.id.tgBlue);
        butVisible = (Button)view.findViewById(R.id.butVisible);
        butList = (Button)view.findViewById(R.id.butList);

        tgBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BA.isEnabled())
                {
                    BA.disable();
                    Boast.makeText(getActivity(), "Turned off").show();
                    tgBlue.setText("Turn Off");
                    tgBlue.setChecked(false);
                    butVisible.setVisibility(View.INVISIBLE);
                    butList.setVisibility(View.INVISIBLE);
                    txtList.setText("");
                }
                else
                {
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, 1);
                    Boast.makeText(getActivity(),"Turned on").show();
                    tgBlue.setText("Turn On");
                    tgBlue.setChecked(true);
                    butVisible.setVisibility(View.VISIBLE);
                    butList.setVisibility(View.VISIBLE);
                }
            }
        });

        butVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(butVisible.isEnabled())
                {
                    Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(getVisible, 0);
                }
                else
                {
                    Boast.makeText(getActivity(),"Turned on first").show();
                }
            }
        });

        butList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(butList.isEnabled())
                {
                    pairedDevices = BA.getBondedDevices();
                    txtList.setText("");
                    if(pairedDevices.size()>0){
                        for(BluetoothDevice bt : pairedDevices)
                            txtList.append("   "+bt.getName()+"\n");
                        Boast.makeText(getActivity(),"Showing Paired Devices").show();
                    }else {
                        txtList.setText("Not found");
                    }
                }
                else
                {
                    Boast.makeText(getActivity(),"Turned on first").show();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(DataUtil.whichTest){
            super.setHasOptionsMenu(false);
        }else {
            super.setHasOptionsMenu(true);
        }
        BA = BluetoothAdapter.getDefaultAdapter();
        if(BA.isEnabled())
        {
            tgBlue.setText("Turn On");
            tgBlue.setChecked(true);
            butVisible.setVisibility(View.VISIBLE);
            butList.setVisibility(View.VISIBLE);
        }
        else
        {
            tgBlue.setText("Turn Off");
            tgBlue.setChecked(false);
            butVisible.setVisibility(View.INVISIBLE);
            butList.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode == getActivity().RESULT_OK){
                tgBlue.setText("Turn On");
                tgBlue.setChecked(true);
            }else {
                tgBlue.setText("Turn Off");
                tgBlue.setChecked(false);
            }
        }
    }

    @Override
    protected int getRootLayout() {
        return R.layout.layout_bluetooth_test;
    }
}
