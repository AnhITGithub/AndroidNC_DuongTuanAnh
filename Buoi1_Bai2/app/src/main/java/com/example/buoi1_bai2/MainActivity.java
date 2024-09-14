package com.example.buoi1_bai2;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private TextView textView1, textView2, textView3;
    private Button button1, button2, button3;
    private Handler handler;

    private volatile boolean isRunning1 = true;
    private volatile boolean isRunning2 = true;
    private volatile boolean isRunning3 = true;

    private Thread thread1, thread2, thread3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        handler = new android.os.Handler(new android.os.Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        textView1.setText(String.valueOf(msg.arg1));
                        break;
                    case 2:
                        textView2.setText(String.valueOf(msg.arg1));
                        break;
                    case 3:
                        textView3.setText(String.valueOf(msg.arg1));
                        break;
                }
                return true;
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRunning1) {
                    button1.setText("START");
                    isRunning1 = false;
                } else {
                    button1.setText("STOP");
                    isRunning1 = true;
                    startThread1();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRunning2) {
                    button2.setText("START");
                    isRunning2 = false;
                } else {
                    button2.setText("STOP");
                    isRunning2 = true;
                    startThread2();
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRunning3) {
                    button3.setText("START");
                    isRunning3 = false;
                } else {
                    button3.setText("STOP");
                    isRunning3 = true;
                    startThread3();
                }
            }
        });
        startThread1();
        startThread2();
        startThread3();
    }

    private void startThread1() {
        if (thread1 == null || !thread1.isAlive()) {
            thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
                    while (isRunning1) {
                        int number = random.nextInt(51) + 50; // Sinh số ngẫu nhiên trong khoảng 50 đến 100
                        Message msg = handler.obtainMessage(1, number, 0);
                        handler.sendMessage(msg);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread1.start();
        }
    }
    private void startThread2(){
        if(thread2==null||!thread2.isAlive()){
            thread2=new Thread(new Runnable() {
                @Override
                public void run() {
                    int number=1;
                    while (isRunning2){
                        Message msg=handler.obtainMessage(2,number,0);
                        handler.sendMessage(msg);
                        number+=2;
                        try{
                            Thread.sleep(2500);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread2.start();
        }
    }
    private void startThread3(){
        if(thread3==null||!thread3.isAlive()){
            thread3=new Thread(new Runnable() {
                @Override
                public void run() {
                    int number=0;
                    while (isRunning3){
                        Message msg=handler.obtainMessage(3,number,0);
                        handler.sendMessage(msg);
                        number++;
                        try {
                            Thread.sleep(2000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread3.start();
        }
    }
}
