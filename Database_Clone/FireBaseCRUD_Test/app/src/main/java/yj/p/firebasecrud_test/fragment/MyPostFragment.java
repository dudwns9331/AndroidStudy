package yj.p.firebasecrud_test.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyPostFragment extends PostListFragment{

    public MyPostFragment() {}


    /**
     * Firebase Query를 통해서 밑의 내용 전달
     * @param mDatabase
     * @return user-posts의 uid를 전달
     */
    @Override
    public Query getQuery(DatabaseReference mDatabase) {
        return mDatabase.child("user-posts").child(getUid());
    }
}
