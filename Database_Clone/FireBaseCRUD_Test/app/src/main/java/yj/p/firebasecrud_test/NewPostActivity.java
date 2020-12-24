package yj.p.firebasecrud_test;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends BaseActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;

    FloatingActionButton fabSubmitPost;
    EditText fieldTitle;
    EditText fieldBody;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        fabSubmitPost = findViewById(R.id.fabSubmitPost);
        fieldTitle = findViewById(R.id.fieldTitle);
        fieldBody = findViewById(R.id.fieldBody);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        fabSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPost();
            }
        });
    }

    private void submitPost() {
        final String title = fieldTitle.getText().toString();
        final String body = fieldBody.getText().toString();


        if(TextUtils.isEmpty(title)) {
            fieldTitle.setError(REQUIRED);
            return;
        }

        if(TextUtils.isEmpty(body)) {
            fieldBody.setError(REQUIRED);
            return;
        }

        setEditingEnabled(false);
        Toast.makeText(this, "Postring...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                User user = datasnapshot.getValue(User.class);

                if(user == null) {
                    Log.e(TAG, "User " + userId + " is unexpectedly null");
                    Toast.makeText(NewPostActivity.this,
                            "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    writeNewPost(userId, user.username, title, body);
                }

                setEditingEnable(true);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                setEditingEnabled(true);
            }
        });

    }

    private void setEditingEnabled(boolean enabled) {
        fieldTitle.setEnabled(enabled);
        fieldBody.setEnabled(enabled);
        if(enabled)
            fabSubmitPost.show();
        else fabSubmitPost.hide();
    }

    private void writeNewPost(String userId, String username, String title, String body) {

        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" +key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
}
