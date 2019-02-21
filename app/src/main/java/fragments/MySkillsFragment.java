package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import config.SessionManagement;
import ke.co.lt.com.skilllite.R;

import static config.BaseVariables.USER_ID;


public class MySkillsFragment extends Fragment {
    private ListView lv;
    private SessionManagement sessionManagement;
    private String user_id;
    private List<String> skillList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_skills, container, false);

        lv = view.findViewById(R.id.idListView);
        sessionManagement = new SessionManagement(getActivity());
        user_id = sessionManagement.getUserInfo().get(USER_ID);

        skillList = new ArrayList<>();

        populateListView();

        return view;
    }

    private void populateListView() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        Map<String, String> postParam = new HashMap<>();
        postParam.put("user_id", user_id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BaseVariables.URL_FETCH_MY_SKILLS,
                new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                try {
                    int resultSet = Integer.parseInt(response.getString("status"));
                    if (resultSet == 200) {
                        JSONArray jsonArray = response.getJSONArray("skills");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("skill");
                            skillList.add(name);
                        }

                        String[] arraySKill = skillList.toArray(new String[skillList.size()]);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1, arraySKill);
                        lv.setAdapter(adapter);
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

}
