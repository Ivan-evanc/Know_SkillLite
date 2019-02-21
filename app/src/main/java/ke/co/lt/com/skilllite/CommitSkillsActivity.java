package ke.co.lt.com.skilllite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;

import config.BaseVariables;

import static config.BaseVariables.SPINNER_GENDER;

public class CommitSkillsActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner s_gender, s_country, s_county, s_estate, s_skills;
    private TextView tv_skills_add;
    private List<String> listCounties, listEstates, listSkills;
    private List<String> listCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_skills);


        s_gender = findViewById(R.id.spinner_gender);
        s_country = findViewById(R.id.s_country);
        s_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String country = s_country.getSelectedItem().toString();
                loadTheAvailCounties(country);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s_county = findViewById(R.id.s_county);
        s_county.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String county = s_county.getSelectedItem().toString();
                loadAvailEstates(county);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s_estate = findViewById(R.id.s_estate);
        s_skills = findViewById(R.id.s_expertize);
        tv_skills_add = findViewById(R.id.tv_skills_add);
        tv_skills_add.setOnClickListener(this);

        listCountries = new ArrayList<>();
        listCounties = new ArrayList<>();
        listCounties.add("--Choose County--");
        listEstates = new ArrayList<>();
        listEstates.add("--Select Estate--");
        listSkills = new ArrayList<>();
        listSkills.add("--Select Skills to Search--");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, SPINNER_GENDER);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_gender.setAdapter(adapter);


        populateDynamicSpinners();

    }

    public void populateDynamicSpinners() {
        loadTheAvailCountries();
        loadAvailSkills();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_skills_add) {
            String gender = s_gender.getSelectedItem().toString();
            String country = s_country.getSelectedItem().toString();
            String county = s_county.getSelectedItem().toString();
            String estate = s_estate.getSelectedItem().toString();
            String skills = s_skills.getSelectedItem().toString();
            if (gender == "--Select Your Gender--") {
                alertView("Select Your Gender!");
            } else if (country == "--Choose Country--") {
                alertView("Country is needed!");
            } else if (county == "--Choose County--") {
                alertView("County is needed!");
            } else if (estate == "--Select Estate--") {
                alertView("Estate is needed!");
            } else if (skills == "--Select Skills to Search--") {
                alertView("Skills is needed!");
            } else {

                moveToNextScreen(gender, country, county, estate, skills);
            }

        }
    }

    private void alertView(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(CommitSkillsActivity.this).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void moveToNextScreen(String gender, String country, String county, String estate, String skills) {
        Intent intent = new Intent(CommitSkillsActivity.this, QuerySkillsActivity.class);
        intent.putExtra("skills", skills);
        intent.putExtra("gender", gender);
        intent.putExtra("country", country);
        intent.putExtra("county", county);
        intent.putExtra("estate", estate);
        startActivity(intent);
    }

    private void loadTheAvailCountries() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BaseVariables.URL_FETCH_COUNTRIES,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                try {
                    int resultSet = Integer.parseInt(response.getString("status"));
                    if (resultSet == 200) {
                        JSONArray jsonArray = response.getJSONArray("countries");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("country");
                            listCountries.add(name);
                        }

                        String[] arraySpinner = listCountries.toArray(new String[listCountries.size()]);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, arraySpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_country.setAdapter(adapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error " + error + " occurred", Toast.LENGTH_LONG).show();
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

    private void loadTheAvailCounties(String country) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> postParam = new HashMap<>();
        postParam.put("country", country);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseVariables.URL_FETCH_COUNTIES,
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                try {
                    int resultSet = Integer.parseInt(response.getString("status"));
                    if (resultSet == 200) {
                        JSONArray jsonArray = response.getJSONArray("counties");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            listCounties.add(name);
                        }

                        String[] arraySpinner = listCounties.toArray(new String[listCounties.size()]);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, arraySpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_county.setAdapter(adapter);
                    } else {
                        listCounties.clear();
                        s_county.setAdapter(null);
                        Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error " + error + " occurred", Toast.LENGTH_LONG).show();
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

    public void loadAvailEstates(String county) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> postParam = new HashMap<>();
        postParam.put("county", county);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseVariables.URL_FETCH_ESTATES,
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                try {
                    int resultSet = Integer.parseInt(response.getString("status"));
                    if (resultSet == 200) {
                        JSONArray jsonArray = response.getJSONArray("estates");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            listEstates.add(name);
                        }

                        String[] arraySpinner = listEstates.toArray(new String[listEstates.size()]);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, arraySpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_estate.setAdapter(adapter);
                    } else {
                        listEstates.clear();
                        s_estate.setAdapter(null);
                        Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error " + error + " occurred", Toast.LENGTH_LONG).show();
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

    public void loadAvailSkills() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BaseVariables.URL_FETCH_EXPERT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                try {
                    int resultSet = Integer.parseInt(response.getString("status"));
                    if (resultSet == 200) {
                        JSONArray jsonArray = response.getJSONArray("expert");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            listSkills.add(name);
                        }

                        String[] arraySpinner = listSkills.toArray(new String[listSkills.size()]);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, arraySpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_skills.setAdapter(adapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error " + error + " occurred", Toast.LENGTH_LONG).show();
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
