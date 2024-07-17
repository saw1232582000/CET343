package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import DB_Context.DBContext;
import DB_Context.UserModel;

public class SignupActivity extends AppCompatActivity {

    TextView login_text;
    EditText username;
    EditText password;
    EditText confirm_password;
    Button signupbtn;

    DBContext dbcontext=new DBContext(this);
    ArrayList<UserModel> userModelArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        login_text=findViewById(R.id.logintext);
        username=findViewById(R.id.signup_username);
        password=findViewById(R.id.signup_password);
        confirm_password=findViewById(R.id.signup_confirm_password);
        signupbtn=findViewById(R.id.signup_button);

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=username.getText().toString();
                String pass=password.getText().toString();
                String con_pass=confirm_password.getText().toString();

                if(name.isEmpty() || pass.isEmpty() || con_pass.isEmpty())
                {
                    Toast.makeText(SignupActivity.this, "Please fill all the data", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pass.equals(con_pass)){
                        userModelArrayList=dbcontext.readUser();
                        UserModel user;
                        Boolean isUser=false;
                        if(userModelArrayList!=null){
                            for (int i=0;i<userModelArrayList.size();i++)
                            {
                                user=userModelArrayList.get(i);

                                if(name.equals(user.getUsername())&& pass.equals(user.getPassword()))
                                {
                                    isUser=true;
                                    break;
                                }
                            }
                        }

                        if(isUser)
                        {
                            Toast.makeText(getApplicationContext(),"User already exists. Please login to continue",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            dbcontext.addUser(name,pass);
                            Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                    else
                    {
                        Toast.makeText(SignupActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
