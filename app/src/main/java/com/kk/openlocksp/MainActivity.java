package com.kk.openlocksp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import static com.kk.openlocksp.LockApi.*;
import static com.kk.openlocksp.MApp.mApp;

public class MainActivity extends AppCompatActivity {

    EditText edt1,edt2,edt3;
    TextView tvLog;

    private void showMessage(String text)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this ,text,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void writeLog(String text)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvLog.append(text);
            }
        });
    }

    private void initView()
    {
        edt1= findViewById(R.id.edt1);
        edt2= findViewById(R.id.edt2);
        edt3= findViewById(R.id.edt3);
        tvLog= findViewById(R.id.tvLog);
        tvLog.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        
        //初始化串口，传入处理串口接收
        mApp.initCOM(new KCallBack.CallBackInterface() {
            @Override
            public void sendToData(String str) {
                //do something
                writeLog("串口接收："+str+"\n");
            }
        });

        //设置串口
        findViewById(R.id.btnSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SerialPortSetDialog(MainActivity.this, "DEVICE","BAUDRATE").show();
            }
        });

        //连接串口
        findViewById(R.id.btnConn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreUtil sp= new SharedPreUtil(MainActivity.this);
                        String[] rsMsg={""};
                        setCommLock(sp.getString("DEVICE"), Integer.parseInt( sp.getString("BAUDRATE")), rsMsg);

                        showMessage("连接控制板,"+ rsMsg[0]);

                    }
                }).start();
            }
        });

        //开锁
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (isInteger(edt1.getText().toString())==false || isInteger(edt2.getText().toString())==false)
                        {
                            showMessage("请输入有效的板号、锁号");
                            return;
                        }
                        String[] rsMsg={""};
                        openLock(Byte.valueOf(edt1.getText().toString()),
                                Byte.valueOf(edt2.getText().toString()),
                                rsMsg);
                        showMessage("开锁,"+rsMsg[0]);
                    }
                }).start();


            }
        });

        //所有开锁
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (isInteger(edt1.getText().toString())==false )
                        {
                            showMessage("请输入有效的板号");
                            return;
                        }
                        String[] rsMsg={""};
                        openAllLock(Byte.valueOf(edt1.getText().toString()),
                                rsMsg);
                        showMessage("所有开锁,完成");
                    }
                }).start();


            }
        });

        //状态
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (isInteger(edt1.getText().toString())==false || isInteger(edt2.getText().toString())==false)
                        {
                            showMessage("请输入有效的板号、锁号");
                            return;
                        }
                        String[] rsMsg={""};
                        getState(Byte.valueOf(edt1.getText().toString()),
                                Byte.valueOf(edt2.getText().toString()),
                                rsMsg);
                        showMessage("状态,"+rsMsg[0]);
                    }
                }).start();

            }
        });

        //所有状态
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (isInteger(edt1.getText().toString())==false )
                        {
                            showMessage("请输入有效的板号");
                            return;
                        }
                        String[] rsMsg={""};
                        getAllState(Byte.valueOf(edt1.getText().toString()),
                                rsMsg);
                        showMessage("所有状态,"+rsMsg[0]);
                    }
                }).start();

            }
        });

        //通电
        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (isInteger(edt1.getText().toString())==false || isInteger(edt2.getText().toString())==false)
                        {
                            showMessage("请输入有效的板号、锁号");
                            return;
                        }
                        String[] rsMsg={""};
                        openPower(Byte.valueOf(edt1.getText().toString()),
                                Byte.valueOf(edt2.getText().toString()),
                                rsMsg);
                        showMessage("通电,"+rsMsg[0]);
                    }
                }).start();


            }
        });

        //断电
        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (isInteger(edt1.getText().toString())==false || isInteger(edt2.getText().toString())==false)
                        {
                            showMessage("请输入有效的板号、锁号");
                            return;
                        }
                        String[] rsMsg={""};
                        closePower(Byte.valueOf(edt1.getText().toString()),
                                Byte.valueOf(edt2.getText().toString()),
                                rsMsg);
                        showMessage("断电,"+rsMsg[0]);
                    }
                }).start();
            }
        });

        //发送
        findViewById(R.id.btnSent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (edt3.getText().toString().trim().equals(""))
                        {
                            showMessage("请输入发送十六进制内容");
                            return;
                        }
                        String[] rsMsg={""};
                        byte[] to_send = toByteArray(edt3.getText().toString());
                        if (sendData(to_send, rsMsg)) {
                            writeLog("接收：" + rsMsg[0] + "\n");
                        }
                        else
                        {
                            writeLog("发送失败，或串口未连接！"+"\n");
                        }
                    }
                }).start();
            }
        });

        //清空
        findViewById(R.id.btnCls).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLog.setText("");
            }
        });



    }


}
