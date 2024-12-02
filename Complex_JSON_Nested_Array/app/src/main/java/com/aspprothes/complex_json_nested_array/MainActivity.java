package com.aspprothes.complex_json_nested_array;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.aspprothes.complex_json_nested_array.model.SubjectModel;
import com.aspprothes.complex_json_nested_array.model.TitleModel;
import com.mursaat.extendedtextview.AnimatedGradientTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private long lastBackPressedBtnTime = 0;
    private static final long backPressed_btn_time = 2000;
    private String json_url = "https://prothesbarai.github.io/Complex_json_object_and_array_parcing/Complex_JSON_Nested_Array.json";
    private ArrayList<TitleModel> titleList = new ArrayList<>();
    private LinearLayout linearLayoutLoader;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.sky));
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.sky));
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        linearLayoutLoader = findViewById(R.id.linearLayoutLoader);


        linearLayoutLoader.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                linearLayoutLoader.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject titleNameObject = response.getJSONObject(i);
                        String getNameTitle = titleNameObject.getString("name");
                        String getDept = titleNameObject.getString("dept");

                        JSONArray jsonSubjectArray = titleNameObject.getJSONArray("subjects");
                        ArrayList<SubjectModel> subjectList = new ArrayList<>();

                        for (int j = 0; j < jsonSubjectArray.length(); j++) {
                            JSONObject jsonSubObject = jsonSubjectArray.getJSONObject(j);
                            String getSemesterName = jsonSubObject.getString("semester");
                            String getSubName = jsonSubObject.getString("sub");

                            SubjectModel subject = new SubjectModel(getSemesterName, getSubName);
                            subjectList.add(subject);
                        }

                        TitleModel title = new TitleModel(getNameTitle, getDept, subjectList);
                        titleList.add(title);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter();
                listView.setAdapter(customArrayAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                linearLayoutLoader.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonArrayRequest);








    }
    // =============================== On Create Method End Here ===========================================


    // ================================ Create CustomArray Adapter Start Here ======================================================
    public class CustomArrayAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return titleList.size();
        }

        @Override
        public Object getItem(int position) {
            return titleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.listvies_itms_design, parent, false);
            }

            AnimatedGradientTextView yourName = convertView.findViewById(R.id.yourName);
            TextView yourDept = convertView.findViewById(R.id.yourDept);
            GridView gridView = convertView.findViewById(R.id.gridView);

            TitleModel titleModel = titleList.get(position);
            yourName.setText(titleModel.getName());
            yourDept.setText(titleModel.getDept());

            // Set GridView Adapter for subjects
            CustomGridAdapter customGridAdapter = new CustomGridAdapter(titleModel.getSubjects());
            gridView.setAdapter(customGridAdapter);

            return convertView;
        }
    }

    // ================================ Create CustomArray Adapter End Here ========================================================



    public class CustomGridAdapter extends BaseAdapter {
        private ArrayList<SubjectModel> subjects;

        public CustomGridAdapter(ArrayList<SubjectModel> subjects) {
            this.subjects = subjects;
        }

        @Override
        public int getCount() {
            return subjects.size();
        }

        @Override
        public Object getItem(int position) {
            return subjects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.gridviews_layout_design, parent, false);
            }

            TextView semesterName = convertView.findViewById(R.id.semesterName);
            TextView subName = convertView.findViewById(R.id.subName);

            SubjectModel subject = subjects.get(position);
            semesterName.setText(subject.getSemester());
            subName.setText(subject.getSubject());

            return convertView;
        }
    }






    // ===========================================On BackPressed Method start Here ===================================================
    @Override
    public void onBackPressed() {
        if (isTaskRoot()){
            long getCurrentTime = System.currentTimeMillis();
            if (getCurrentTime - lastBackPressedBtnTime < backPressed_btn_time){
                finish();
            }else{
                lastBackPressedBtnTime = getCurrentTime;
                Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onBackPressed();
        }
    }
    // ===========================================On BackPressed Method End Here =====================================================
}