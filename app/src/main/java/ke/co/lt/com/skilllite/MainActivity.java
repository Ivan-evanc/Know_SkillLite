package ke.co.lt.com.skilllite;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_main_login, tv_main_reg;
    private TextView tv_tap_skills, tv_search_skills;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this ,R.color.colorPrimaryDark));
        }

        tv_main_login = findViewById(R.id.tv_main_login);
        tv_main_login.setOnClickListener(this);
        tv_main_reg = findViewById(R.id.tv_main_reg);
        tv_main_reg.setOnClickListener(this);

        tv_search_skills = findViewById(R.id.tv_search_skills);
        tv_search_skills.setOnClickListener(this);

        tv_tap_skills = findViewById(R.id.tv_tap_skills);
        tv_tap_skills.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tv_main_reg){
            startActivity(new Intent(MainActivity.this, SignupActivity.class));
        }else if(id == R.id.tv_main_login){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else if(id == R.id.tv_tap_skills){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else if(id == R.id.tv_search_skills){
            startActivity(new Intent(MainActivity.this, CommitSkillsActivity.class));
        }
    }
}
