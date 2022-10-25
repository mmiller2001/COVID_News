package com.example.custom_listview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;


import com.example.custom_listview.databinding.ActivityMainBinding;
import com.example.custom_listview.databinding.ActivityUserBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<News> newsArrayList = new ArrayList<>();
//        ***************************************************
//        API CALL
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://coronavirus-smartable.p.rapidapi.com/news/v1/US/")
                .get()
                .addHeader("X-RapidAPI-Key", "10be3c5e10msh1430b076737ef5dp1ec3b3jsn345249446720")
                .addHeader("X-RapidAPI-Host", "coronavirus-smartable.p.rapidapi.com")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String myResponse = response.body().string();
                    String locationComponents = "";
                    try {
                        JSONObject jsonObject = new JSONObject(myResponse);

//                        String updatedDateTime = jsonObject.getString("updatedDateTime");
//                        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-mm-dd");
//                        Date dtIn = inFormat.parse(updatedDateTime);
//                        SimpleDateFormat inFormat2 = new SimpleDateFormat("yyyy-mm-dd");
//                        updatedDateTime = inFormat2.format(dtIn);

                        JSONArray location = jsonObject.getJSONArray("news"); // news array
                        for(int i = 0; i < location.length(); i++) {
                            JSONObject object = location.getJSONObject(i);

                            String publishedDateTime = object.getString("publishedDateTime");
                            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-mm-dd");
                            Date dtIn = inFormat.parse(publishedDateTime);
                            SimpleDateFormat inFormat2 = new SimpleDateFormat("yyyy-mm-dd");
                            publishedDateTime = inFormat2.format(dtIn);
//                            *****************************************************
//                            JSONArray images = object.getJSONArray("images");
//                            JSONObject linkImage = images.getJSONObject(0);
//                            String urlImage = linkImage.getString("url");
//                            Drawable myImage = LoadImageFromWebOperations(urlImage);
//                            *****************************************************

                            String title = object.getString("title"); // name
                            int heat = object.getInt("heat");
                            String path = object.getString("path"); // last message
                            String sourceUrl = object.getString("sourceUrl");
                            String webUrl = object.getString("webUrl");
                            String originalUrl = object.getString("originalUrl");
                            String featuredContent = object.getString("featuredContent");
                            String highlight = object.getString("highlight");
                            String locale = object.getString("locale"); // country
                            String ampWebUrl = object.getString("ampWebUrl"); // lastmsgTime
                            String time = object.getString("publishedDateTime"); // phoneNo
                            String excerpt = object.getString("excerpt");

                            News news = new News(path,title,excerpt,sourceUrl,webUrl,originalUrl,featuredContent,highlight,publishedDateTime);
                            User user = new User(title,excerpt,publishedDateTime,time,ampWebUrl);//,imageId[i]);

                            newsArrayList.add(news);
                            //userArrayList.add(user);

                            locationComponents += "--" + title + ", " + heat + ", " + path + ", " + ampWebUrl + ", " + time + "\n\n";
                        }
                        //locationComponents = location.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String finalLocationComponents = locationComponents;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListAdapter listAdapter = new ListAdapter(MainActivity.this,newsArrayList);

                            binding.listview.setAdapter(listAdapter);
                            binding.listview.setClickable(true);
                            binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    //User user = userArrayList.get(position);
                                    News news = newsArrayList.get(position);

//                                    String phone = user.phoneNo;
//                                    SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-mm-dd");
//                                    Date dtIn = null;
//                                    try {
//                                        dtIn = inFormat.parse(phone);
//                                    } catch (ParseException e) {
//                                        e.printStackTrace();
//                                    }
//                                    SimpleDateFormat inFormat2 = new SimpleDateFormat("yyyy-mm-dd");
//                                    phone = inFormat2.format(dtIn);

                                    Intent i = new Intent(MainActivity.this,UserActivity.class);
                                    i.putExtra("name",news.title);
                                    i.putExtra("phone",news.publishedDateTime);
                                    i.putExtra("country",news.excerpt);
                                    //i.putExtra("imageid",imageId[position]);
                                    startActivity(i);
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private Drawable LoadImageFromWebOperations(String urlImage) {
        try {
            InputStream is = (InputStream) new URL(urlImage).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}