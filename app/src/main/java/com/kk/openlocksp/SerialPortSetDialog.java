package com.kk.openlocksp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.serialport.SerialPortFinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

/**
 * 串口参数设置
 */
public class SerialPortSetDialog extends Dialog {

    public String DEVICE = "DEVICE";
    public String BAUDRATE = "BAUDRATE";
    public SharedPreUtil sp;

    public SerialPortSetDialog(@NonNull Context context, String aDevice, String aBaudrate) {
        super(context);
        sp= new SharedPreUtil(context);
        this.DEVICE= aDevice;
        this.BAUDRATE= aBaudrate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setCanceledOnTouchOutside(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_serial_port_set);
        Spinner s_device = findViewById(R.id.s_device);
        Spinner s_baud_rate = findViewById(R.id.s_baud_rate);
        Button bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(v -> dismiss());

        setTitle("Set SerialPort");

        SerialPortFinder mSerialPortFinder = new SerialPortFinder();

        //mSerialPortFinder.getAllDevices()
        //mSerialPortFinder.getAllDevicesPath()
        List<String> devices = Arrays.asList(mSerialPortFinder.getAllDevicesPath());
        List<String> baudRates = Arrays.asList(getContext().getResources().getStringArray(R.array.baudrates_value));

        ArrayAdapter entriesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, devices);
        entriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_device.setAdapter(entriesAdapter);
        if (!TextUtils.isEmpty(sp.getString(DEVICE))) {
            for (int i = 0; i < devices.size(); i++) {
                if (sp.getString(DEVICE).equals(devices.get(i))) {
                    s_device.setSelection(i, true);
                }
            }
        }
        s_device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sp.put(DEVICE, devices.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter entryValuesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, baudRates);
        entryValuesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_baud_rate.setAdapter(entryValuesAdapter);
        if (!TextUtils.isEmpty(sp.getString(BAUDRATE))) {
            for (int i = 0; i < baudRates.size(); i++) {
                if (sp.getString(BAUDRATE).equals(baudRates.get(i))) {
                    s_baud_rate.setSelection(i, true);
                }
            }
        }
        s_baud_rate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sp.put(BAUDRATE, baudRates.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
