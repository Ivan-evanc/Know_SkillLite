package ke.co.lt.com.skilllite;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.BaseVariables;
import config.SessionManagement;
import de.hdodenhof.circleimageview.CircleImageView;

import static config.BaseVariables.URL_REG_SKILLS;
import static config.BaseVariables.USER_ID;

public class RegSkillsActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_idNumber, et_neigh;
    TextView btn_login;
    private ProgressDialog progress_dialog;
    private Button mStartCamera;
    private CircleImageView mImageView;
    static int TAKE_PICTURE = 1;
    private SessionManagement sessionManagement;
    private String user_id;
    private Spinner s_country, s_county, s_estate, s_expertize;

    private Bitmap bitmap;
    private String encodedImage;

    List<String> listCounties;
    List<String> listEstates;
    List<String> listSkills;
    List<String> listCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_skills);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sessionManagement = new SessionManagement(this);
        user_id = sessionManagement.getUserInfo().get(USER_ID);

        et_neigh = findViewById(R.id.et_neighbours);
        et_idNumber = findViewById(R.id.et_reg_id);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        s_country = findViewById(R.id.s_country);
        s_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String county = s_country.getSelectedItem().toString();
                loadTheAvailCounties(county);
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
        s_expertize = findViewById(R.id.e_expertize);

        listCounties = new ArrayList<>();
        listEstates = new ArrayList<>();
        listSkills = new ArrayList<>();
        listCountries = new ArrayList<>();


        populateDynamicSpinners();

        mStartCamera = findViewById(R.id.startCamera);
        mStartCamera.setOnClickListener(this);
        mImageView = findViewById(R.id.imageView);

        mImageView.setVisibility(View.GONE);

        progress_dialog = new ProgressDialog(this);
        progress_dialog.setMessage("Please Wait...");
        progress_dialog.setCancelable(false);
    }

    private void populateDynamicSpinners() {
        loadAvailCountries();
        loadAvailSkills();
    }

    public void loadAvailCountries(){
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseVariables.URL_FETCH_EXPERT,
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
                        s_expertize.setAdapter(adapter);
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
        if (id == R.id.btn_login) {
            attemptSendinfo();
        } else if (id == R.id.startCamera) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, TAKE_PICTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {


        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            bitmap = (Bitmap) intent.getExtras().get("data");
            mImageView.setImageBitmap(bitmap);
            mImageView.setVisibility(View.VISIBLE);
        }
    }


    private void attemptSendinfo() {

        String country = s_country.getSelectedItem().toString();
        String county = s_county.getSelectedItem().toString();
        String estate = s_estate.getSelectedItem().toString();
        String neigh = et_neigh.getText().toString();
        String expert = s_expertize.getSelectedItem().toString();
        String idNumber = et_idNumber.getText().toString();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (TextUtils.isEmpty(idNumber)) {
            alertView("ID Number is required");
        } else if (TextUtils.isEmpty(neigh)) {
            alertView("Neighbours is required");
        } else if (bitmap == null) {
            alertView("Your Photo is Needed!");
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            progress_dialog.show();
            signupUser(idNumber, country, county, estate, neigh, expert, encodedImage);
        }
    }

    private void alertView(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(RegSkillsActivity.this).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void signupUser(String idNumber, String country, String county, String estate, String neigh, String expert, String encodedImage) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> postParam1 = new HashMap<>();
        postParam1.put("user_id",user_id);
        postParam1.put("idnumber", idNumber);
        postParam1.put("country", country);
        postParam1.put("county", county);
        postParam1.put("estate", estate);
        postParam1.put("neigh", neigh);
        postParam1.put("skills", expert);
        postParam1.put("image", encodedImage);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_REG_SKILLS,
                new JSONObject(postParam1), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int resultSet = response.getInt("status");
                            if (resultSet == 200) {
                                progress_dialog.dismiss();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Skills Registration Completed!!", Toast.LENGTH_LONG).show();
                                        progress_dialog.dismiss();
                                        startActivity(new Intent(RegSkillsActivity.this, LoginActivity.class));
                                    }
                                }, 2000);


                            } else {
                                progress_dialog.dismiss();
                                String message = response.getString("message");
                                Toast.makeText(RegSkillsActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            progress_dialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, 2000);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress_dialog.dismiss();
                Toast.makeText(RegSkillsActivity.this, "Error occurred during registration try again later", Toast.LENGTH_LONG).show();
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
