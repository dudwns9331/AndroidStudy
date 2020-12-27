package yj.p.firebasecrud_test.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyPostFragment extends PostListFragment{

    public MyPostFragment() {}

    @Override
    public Query getQuery(DatabaseReference mDatabase) {
        return mDatabase.child("user-posts").child(getUid());
    }
}
