package com.aspprothes.complex_json_nested_objects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private long lastBackPressTime = 0;
    private static final long double_pressed_backbtn = 2000;
    private String json_url = "https://prothesbarai.github.io/Complex_json_object_and_array_parcing/Complex_JSON_Nested_Objects.json";
    private LottieAnimationView animationView;
    private ListView educationListView,skillsListView;
    private TextView textMainTitle,textEmails;
    private LinearLayout mainLinearLayout;
    private ArrayList<HashMap<String,String>> educationArrayList = new ArrayList<>();
    private ArrayList<HashMap<String,String>> skillsArrayList = new ArrayList<>();
    private HashMap<String,String> educationHashMap;
    private HashMap<String,String> skillsHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.sky));
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.sky));
        setContentView(R.layout.activity_main);

        animationView = findViewById(R.id.animationView);
        educationListView = findViewById(R.id.educationListView);
        skillsListView = findViewById(R.id.skillsListView);
        textMainTitle = findViewById(R.id.textMainTitle);
        textEmails = findViewById(R.id.textEmails);
        mainLinearLayout = findViewById(R.id.mainLinearLayout);

        animationView.setVisibility(View.VISIBLE);
        mainLinearLayout.setVisibility(View.GONE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                animationView.setVisibility(View.GONE);
                mainLinearLayout.setVisibility(View.VISIBLE);

                try {
                    String getMainTitle = response.getString("name");
                    String getEmail = response.getString("email");
                    textMainTitle.setText(""+getMainTitle);
                    textEmails.setText(""+getEmail);

                    JSONArray educationJsonArray = response.getJSONArray("education");
                    for (int e=0; e<educationJsonArray.length(); e++){
                        JSONObject educationJsonObject = educationJsonArray.getJSONObject(e);
                        String getSchool = educationJsonObject.getString("school");
                        String getClass = educationJsonObject.getString("class");
                        String getGroup = educationJsonObject.getString("group");
                        String getGpa = educationJsonObject.getString("gpa");
                        String getPassingYear = educationJsonObject.getString("passing_year");
                        educationHashMap = new HashMap<>();
                        educationHashMap.put("getschool",getSchool);
                        educationHashMap.put("getclass",getClass);
                        educationHashMap.put("getgroup",getGroup);
                        educationHashMap.put("getgpa",getGpa);
                        educationHashMap.put("getpassingyear",getPassingYear);
                        educationArrayList.add(educationHashMap);
                    }

                    JSONArray skillsJsonArray = response.getJSONArray("skills");
                    for (int s=0; s<skillsJsonArray.length(); s++){
                        JSONObject skillsJsonObject = skillsJsonArray.getJSONObject(s);
                        String getLanguage = skillsJsonObject.getString("language");
                        String getLearn = skillsJsonObject.getString("learn");
                        skillsHashMap = new HashMap<>();
                        skillsHashMap.put("getlanguage",getLanguage);
                        skillsHashMap.put("getlearn",getLearn);
                        skillsArrayList.add(skillsHashMap);
                    }


                    CustomEducationAdapter customEducationAdapter = new CustomEducationAdapter();
                    CustomSkillsAdapter customSkillsAdapter = new CustomSkillsAdapter();
                    educationListView.setAdapter(customEducationAdapter);
                    skillsListView.setAdapter(customSkillsAdapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                animationView.setVisibility(View.GONE);
                mainLinearLayout.setVisibility(View.GONE);
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);


    }
    //================================================ End On Create Method ============================================================



    // ============================================ Education Adapter Start Here ============================================
    public class CustomEducationAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return educationArrayList.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.education_listview_items_design,parent,false);
            }
            TextView schoolTitle = convertView.findViewById(R.id.schoolTitle);
            TextView className = convertView.findViewById(R.id.className);
            TextView groupName = convertView.findViewById(R.id.groupName);
            TextView cgpaOrGpa = convertView.findViewById(R.id.cgpaOrGpa);
            TextView passingYear = convertView.findViewById(R.id.passingYear);

            HashMap<String,String> hashMap = new HashMap<>();
            hashMap = educationArrayList.get(position);
            String getschoolTitle = hashMap.get("getschool");
            String getclassName= hashMap.get("getclass");
            String getgroupName = hashMap.get("getgroup");
            String getcgpaOrGpa = hashMap.get("getgpa");
            String getpassingYear = hashMap.get("getpassingyear");

            schoolTitle.setText(""+getschoolTitle);
            groupName.setText(""+getgroupName);
            cgpaOrGpa.setText(""+getcgpaOrGpa);
            passingYear.setText(""+getpassingYear);
            className.setText(""+getclassName);

            return convertView;
        }
    }
    // ============================================ Education Adapter End Here ============================================



    // ============================================ Skills Adapter Start Here ============================================
    public class CustomSkillsAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return skillsArrayList.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.skills_listview_items_design,parent,false);
            }
            TextView languageName = convertView.findViewById(R.id.languageName);
            TextView learnFrom = convertView.findViewById(R.id.learnFrom);

            HashMap<String,String> hashMap = new HashMap<>();
            hashMap = skillsArrayList.get(position);
            String getLanguageName = hashMap.get("getlanguage");
            String getLearnFrom = hashMap.get("getlearn");

            languageName.setText(""+getLanguageName);
            learnFrom.setText(""+getLearnFrom);

            return convertView;
        }
    }
    // ============================================ Skills Adapter End Here ============================================



    // ========================================= Double Click On Back Pressed Method Start Here ======================================================
    @Override
    public void onBackPressed() {
        if (isTaskRoot()){
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastBackPressTime < double_pressed_backbtn){
                finish();
            }else{
                lastBackPressTime = currentTime;
                Toast.makeText(this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onBackPressed();
        }
    }
    // =========================================== Double Click On Back Pressed Method End Here ======================================================


}