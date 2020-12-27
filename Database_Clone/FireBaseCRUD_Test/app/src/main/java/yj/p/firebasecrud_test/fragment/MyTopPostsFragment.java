package yj.p.firebasecrud_test.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyTopPostsFragment extends PostListFragment{

    public MyTopPostsFragment() {

    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        String myUserId = getUid();
        Query myTopPostQuery = databaseReference.child("user-posts").child(myUserId)
                .orderByChild("starCount");

        return myTopPostQuery;

    }
}
