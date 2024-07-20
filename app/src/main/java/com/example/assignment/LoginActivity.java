package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DB_Context.DBContext;
import DB_Context.UserModel;

public class LoginActivity extends AppCompatActivity {
private EditText username;
private EditText password;

private String user_id;
private Button login_btn;

TextView sign_up_text;
    DBContext dbcontext=new DBContext(this);
    ArrayList<UserModel> userModelArrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username_login);
        password=findViewById(R.id.password_login);
        login_btn=findViewById(R.id.login_button);
        sign_up_text=findViewById(R.id.signuptext);
        List<String> table_list=dbcontext.getTableList();
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String name=username.getText().toString();
            String pass=password.getText().toString();



                userModelArrayList=dbcontext.readUser();
                UserModel user;
                int isUser_exist=0;
                for (int i=0;i< userModelArrayList.size();i++)
                {
                    user=userModelArrayList.get(i);
                    if(name.equals(user.getUsername())&&pass.equals(user.getPassword()))
                    {
                        user_id=String.valueOf(user.getId());

                        isUser_exist++;
                    }
                }
                if(isUser_exist!=0){

                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_id", user_id);
                    editor.putString("username", name);

                    editor.apply();

                    Intent intent=new Intent(LoginActivity.this,UI.dashboardActivity.class);
                    intent.putExtra("username",name);
                    intent.putExtra("password",pass);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid user name and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sign_up_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}