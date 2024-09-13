package com.example.buoi1_bai1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadWebpageAsyncTask extends Activity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Đảm bảo tệp XML có tên chính xác
        textView = (TextView) findViewById(R.id.TextView01);  // Kết nối TextView với XML
    }

    public void readWebpage(View view) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute("https://vi.wikipedia.org/wiki/IShowSpeed");  // Thay URL bằng địa chỉ trang web bạn muốn tải
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response += line;
                    }
                } finally {
                    urlConnection.disconnect();  // Đảm bảo đóng kết nối
                }
            } catch (Exception e) {
                e.printStackTrace();  // Hiển thị ngoại lệ để dễ dàng tìm lỗi
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);  // Hiển thị kết quả trong TextView
        }
    }
}