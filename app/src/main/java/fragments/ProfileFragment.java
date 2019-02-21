package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import config.SessionManagement;
import ke.co.lt.com.skilllite.R;

import static config.BaseVariables.GENDER;
import static config.BaseVariables.NAME;
import static config.BaseVariables.PHONE;

public class ProfileFragment extends Fragment {
    TextView  tv_name, tv_gender, tv_phone;
    private SessionManagement sessionManagement;
    private String name, phone, gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        tv_name = view.findViewById(R.id.profile_name);
        tv_phone = view.findViewById(R.id.profile_phone);
        tv_gender = view.findViewById(R.id.profile_gender);

        sessionManagement = new SessionManagement(getActivity());
        name = sessionManagement.getUserInfo().get(NAME);
        phone = sessionManagement.getUserInfo().get(PHONE);
        gender = sessionManagement.getUserInfo().get(GENDER);

        tv_name.setText(name);
        tv_phone.setText(phone);
        tv_gender.setText(gender);
        return view;
    }
}
