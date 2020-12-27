package yj.p.firebasecrud_test.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class RecentPostsFragment extends PostListFragment{

    public  RecentPostsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query recentPostQuery = databaseReference.child("posts")
                .limitToFirst(100);

        return recentPostQuery;
    }
}
