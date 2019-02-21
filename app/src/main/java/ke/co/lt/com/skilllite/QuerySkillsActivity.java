package ke.co.lt.com.skilllite;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

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
import java.util.Map;

import adapters.ExpertSearchAdapter;
import model.ExpertSearchModel;

import static config.BaseVariables.URL_QUERY_SKILLS;

public class QuerySkillsActivity extends AppCompatActivity {

    private ExpertSearchAdapter expertSearchAdapter;
    private RecyclerView recyclerview;
    private ArrayList<ExpertSearchModel> expertSearchModelArrayList;
    private String gender, country, county, estate, skills;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_skills);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerview = findViewById(R.id.recycler1);
        Bundle bundle = getIntent().getExtras();
        gender = bundle.getString("gender");
        country = bundle.getString("country");
        county = bundle.getString("county");
        estate = bundle.getString("estate");
        skills = bundle.getString("skills");
        expertSearchModelArrayList = new ArrayList<>();
        querySkillsFroDB();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void querySkillsFroDB() {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> postParam = new HashMap<>();
        postParam.put("gender", gender);
        postParam.put("country", country);
        postParam.put("county", county);
        postParam.put("estate", estate);
        postParam.put("skills", skills);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_QUERY_SKILLS,
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int statusCode = Integer.parseInt(response.getString("status"));
                    if (statusCode == 200) {
                        JSONArray jsonArray = response.getJSONArray("List");
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(j);
                            String name = jsonObject.getString("name");
                            String gender = jsonObject.getString("gender");
                            String country = jsonObject.getString("country");
                            String county = jsonObject.getString("county");
                            String estate = jsonObject.getString("estate");
                            String phone = jsonObject.getString("phone");
                            String neigh = jsonObject.getString("neigh");
                            String skillname = jsonObject.getString("skillname");
                            String image = jsonObject.getString("image");

                            ExpertSearchModel expertSearchModel = new ExpertSearchModel(name, gender, country, county, estate, phone, neigh, skillname, image);
                            expertSearchModelArrayList.add(expertSearchModel);
                        }

                        expertSearchAdapter = new ExpertSearchAdapter(getApplicationContext(), expertSearchModelArrayList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerview.setLayoutManager(mLayoutManager);
                        recyclerview.setItemAnimator(new DefaultItemAnimator());
                        recyclerview.setAdapter(expertSearchAdapter);

                    } else if (statusCode == 201) {
                        Toast.makeText(getApplicationContext(), "No info is present", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QuerySkillsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}
