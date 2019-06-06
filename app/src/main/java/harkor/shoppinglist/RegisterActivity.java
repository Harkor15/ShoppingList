package harkor.shoppinglist;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
@BindView(R.id.email_plain_text) EditText emailField;
@BindView(R.id.password_plain_text) EditText passwordField;
@BindView (R.id.password_plain_text2) EditText passwordFiled2;
@OnClick(R.id.register) void registerClick(){
    String email=emailField.getText().toString();
    String password=passwordField.getText().toString();
    String password2=passwordFiled2.getText().toString();
    if(email!=""&&password!=""&&password.equals(password2)){
        register(email,password);
    }
}
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }

    void register(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("register", "createUserWithEmail:success");
                            finish();
                        } else {
                            Log.w("register", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
