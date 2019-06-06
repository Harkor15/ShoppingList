package harkor.shoppinglist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogInActivity extends AppCompatActivity {
    @BindView(R.id.email_plain_text) EditText emailText;
    @BindView(R.id.password_plain_text) EditText passwordText;
    @OnClick(R.id.register_button) void openRegisterScreen(){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.offline_button) void useOffline(){
        openMainScreen();
    }
    @OnClick(R.id.log_in_button) void loginClick(){
        String password=passwordText.getText().toString();
        String email=emailText.getText().toString();
        if(password!=""&&email!=""){
            signIn(email,password);
        }
    }
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
          openMainScreen();
        }
    }

    void openMainScreen(){
        Intent intent=new Intent(this,MainScreenActivity.class);
        startActivity(intent);
    }

    void signIn(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("LogIn", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            openMainScreen();
                        } else {
                            Log.w("LogIn", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
