package ke.co.lt.com.skilllite;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static config.BaseVariables.SPINNER_GENDER;
import static config.BaseVariables.URL_SIGNUP;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_username, et_firstname, et_lastname;
    EditText et_phoneNumber, et_password,et_email;
    TextView btn_login;
    private ProgressDialog progress_dialog;
    private CountryCodePicker ccp;
    private Spinner s_gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ccp = findViewById(R.id.cpp);

        et_username = findViewById(R.id.et_reg_username);
        et_firstname = findViewById(R.id.et_reg_firstname);
        et_lastname = findViewById(R.id.et_reg_lastname);
        et_email = findViewById(R.id.et_reg_email);
        et_phoneNumber = findViewById(R.id.et_signin_phone);
        et_password = findViewById(R.id.et_reg_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        s_gender = findViewById(R.id.spinner_gender);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, SPINNER_GENDER);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_gender.setAdapter(adapter);


        progress_dialog = new ProgressDialog(this);
        progress_dialog.setMessage("Please Wait...");
        progress_dialog.setCancelable(false);
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
        }
    }



    private void attemptSendinfo() {
        String firstname = et_firstname.getText().toString();
        String lastname = et_lastname.getText().toString();
        String username = et_username.getText().toString();
        String gender = s_gender.getSelectedItem().toString();
        String phoneNumber = et_phoneNumber.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String code = ccp.getSelectedCountryCodeWithPlus();

        if(TextUtils.isEmpty(firstname)) {
            alertView("FirstName is required");
        } else if (TextUtils.isEmpty(lastname)) {
            alertView("LastName is required");
        } else if (TextUtils.isEmpty(username)) {
            alertView("UserName is required");
        }else if (TextUtils.isEmpty(phoneNumber)) {
            alertView("Phone is required");
        }else if(TextUtils.isEmpty(email)){
            alertView("Email is Required!");
        } else if (TextUtils.isEmpty(password)) {
            alertView("Password is required");
        }else{
            String completePhone = code + phoneNumber.substring(1);
            progress_dialog.show();
            signupUser(firstname, lastname, username, gender, completePhone, email, password);
        }
    }

    private void alertView(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void signupUser(String firstname, String lastname, String username, String gender, String phoneNumber, String email, String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> postParam = new HashMap<>();
        postParam.put("firstname", firstname);
        postParam.put("lastname", lastname);
        postParam.put("username", username);
        postParam.put("gender", gender);
        postParam.put("phone", phoneNumber);
        postParam.put("email", email);
        postParam.put("password", password);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_SIGNUP,
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
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
                                        Toast.makeText(getApplicationContext(),"Registration Completed!!", Toast.LENGTH_LONG).show();
                                        progress_dialog.dismiss();
                                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    }
                                }, 2000);


                            } else {
                                progress_dialog.dismiss();
                                String message = response.getString("message");
                                Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG).show();
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
                Toast.makeText(SignupActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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
