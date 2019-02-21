package ke.co.lt.com.skilllite;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import config.SessionManagement;

import static config.BaseVariables.URL_LOGIN;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView btn_login;
    private EditText et_username, et_password;
    private ProgressDialog progress_dialog;
    private TextView tv_reg_user;
    private SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            }
        }

        sessionManagement = new SessionManagement(this);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        tv_reg_user = findViewById(R.id.tv_reg_user);

        tv_reg_user.setOnClickListener(this);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_login) {
            attemptUserLogin();
            //startActivity(new Intent(LoginActivity.this, CommitSkillsActivity.class));
        } else if (id == R.id.tv_reg_user) {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        }
    }

    private void attemptUserLogin() {
        final String username = et_username.getText().toString();
        final String password = et_password.getText().toString();
        if (TextUtils.isEmpty(username)) {
            alertView("Username is required");
        } else if (TextUtils.isEmpty(password)) {
            alertView("Password is required");
        } else {
            progress_dialog = new ProgressDialog(this);
            progress_dialog.setMessage("Checking Credentials...");
            progress_dialog.setCancelable(false);
            progress_dialog.show();
            loginUser(username, password);

        }
    }


    private void alertView(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void loginUser(String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> postParam = new HashMap<>();
        postParam.put("username", username);
        postParam.put("password", password);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_LOGIN,
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                progress_dialog.dismiss();
                try {
                    int resultSet = Integer.parseInt(response.getString("status"));
                    if (resultSet == 200) {

                        progress_dialog.dismiss();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        try {
                            sessionManagement.setUserSession(response.getString("id"), response.getString("username"), response.getString("name"), response.getString("phone"), response.getString("gender"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (resultSet == 201) {
                        Toast.makeText(LoginActivity.this, "Wrong Username Or Password", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    progress_dialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress_dialog.dismiss();
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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
