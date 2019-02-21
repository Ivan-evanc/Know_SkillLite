package fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ke.co.lt.com.skilllite.HomeActivity;
import ke.co.lt.com.skilllite.R;

import static android.app.Activity.RESULT_OK;
import static config.BaseVariables.URL_REG_SKILLS;
import static config.BaseVariables.USER_ID;


public class RegisterSkillsFragment extends Fragment implements View.OnClickListener {
    EditText et_idNumber, et_neigh;
    TextView btn_login;
    private ProgressDialog progress_dialog;
    private Button mStartCamera;
    private CircleImageView mImageView;
    static int TAKE_PICTURE = 1;
    private Spinner s_country, s_county, s_estate, s_expertize;
    ;

    private Bitmap bitmap;
    private String encodedImage;

    List<String> listCounties;
    List<String> listEstates;
    List<String> listSkills;
    List<String> listCountries;

    private SessionManagement sessionManagement;
    private String user_id;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        et_neigh = view.findViewById(R.id.et_neighbours);
        et_idNumber = view.findViewById(R.id.et_reg_id);
        btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        s_country = view.findViewById(R.id.s_country);
        s_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String country = s_country.getSelectedItem().toString();
                listCounties.clear();
                loadTheAvailCounties(country);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s_county = view.findViewById(R.id.s_county);
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
        s_estate = view.findViewById(R.id.s_estate);
        s_expertize = view.findViewById(R.id.e_expertize);

        listCounties = new ArrayList<>();
        listCounties.add("--Select your country--");
        listEstates = new ArrayList<>();
        listSkills = new ArrayList<>();
        listCountries = new ArrayList<>();

        sessionManagement = new SessionManagement(getActivity());
        user_id = sessionManagement.getUserInfo().get(USER_ID);


        populateDynamicSpinners();

        mStartCamera = view.findViewById(R.id.startCamera);
        mStartCamera.setOnClickListener(this);
        mImageView = view.findViewById(R.id.imageView);

        mImageView.setVisibility(View.GONE);

        progress_dialog = new ProgressDialog(getActivity());
        progress_dialog.setMessage("Please Wait...");
        progress_dialog.setCancelable(false);

        return view;
    }

    private void populateDynamicSpinners() {
        loadTheAvailCountries();
        loadAvailSkills();
    }

    private void loadTheAvailCountries() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, arraySpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_country.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), "No Records Found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error " + error + " occurred", Toast.LENGTH_LONG).show();
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

    private void loadTheAvailCounties(String c) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Map<String, String> postParam = new HashMap<>();
        postParam.put("country", c);

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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, arraySpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_county.setAdapter(adapter);
                    } else {
                        listCounties.clear();
                        s_county.setAdapter(null);
                        Toast.makeText(getContext(), "No Records Found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error " + error + " occurred", Toast.LENGTH_LONG).show();
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

    public void loadAvailEstates(String cty) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Map<String, String> postParam = new HashMap<>();
        postParam.put("county", cty);
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, arraySpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_estate.setAdapter(adapter);
                    } else {
                        listEstates.clear();
                        s_estate.setAdapter(null);
                        Toast.makeText(getContext(), "No Records Found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error " + error + " occurred", Toast.LENGTH_LONG).show();
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
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, arraySpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_expertize.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), "No Records Found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error " + error + " occurred", Toast.LENGTH_LONG).show();
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
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {


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
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
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
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Map<String, String> postParam = new HashMap<>();
        postParam.put("user_id",user_id);
        postParam.put("idnumber", idNumber);
        postParam.put("country", country);
        postParam.put("county", county);
        postParam.put("estate", estate);
        postParam.put("neigh", neigh);
        postParam.put("skills", expert);
        postParam.put("image", encodedImage);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_REG_SKILLS,
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int resultSet = Integer.parseInt(response.getString("status"));
                            if (resultSet == 200) {
                                progress_dialog.dismiss();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "Skills Registration Completed!!", Toast.LENGTH_LONG).show();
                                        progress_dialog.dismiss();
                                        startActivity(new Intent(getContext(), HomeActivity.class));
                                    }
                                }, 2000);


                            } else {
                                progress_dialog.dismiss();
                                String message = response.getString("message");
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(), error.toString()+"Error occurred during registration try again later", Toast.LENGTH_LONG).show();
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
