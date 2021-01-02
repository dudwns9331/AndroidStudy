package yj.p.firebasecrud_test;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import yj.p.firebasecrud_test.databinding.ActivityPostDetailBinding;
import yj.p.firebasecrud_test.models.Post;
import yj.p.firebasecrud_test.models.User;
import yj.p.firebasecrud_test.models.Comment;


/**
 * post 클릭시 실행되는 엑티비티
 */
public class PostDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PostDetailActivity";
    public static final String EXTRA_POST_KEY = "post_key";

    private DatabaseReference mPostReference;
    private DatabaseReference mCommentsReference;
    private ValueEventListener mPostListener;
    private String mPostKey;
    private CommentAdapter mAdapter;
    private ActivityPostDetailBinding binding;

    MaterialButton buttonPostComment;           // post 버튼
    RecyclerView recyclerPostComments;          // post-comment가 표시되는 리사이클러 뷰
    LinearLayout postAuthorLayout;              // post의 전체 요소가 담긴 LinearLayout

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        buttonPostComment = findViewById(R.id.buttonPostComment);
        recyclerPostComments = findViewById(R.id.recyclerPostComments);


        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_Post_Key");
        }

        // posts의 post_key 객체를 가져온다.
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("posts").child(mPostKey);
        // post-comment의 post_key 객체를 가져온다.
        mCommentsReference = FirebaseDatabase.getInstance().getReference()
                .child("post-comments").child(mPostKey);

        buttonPostComment.setOnClickListener(this);
        recyclerPostComments.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onStart() {
        super.onStart();

        // 엑티비티가 시작될 때마다 수행된다.
        // 데이터베이스에 있는 값 불러옴
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                Post post = datasnapshot.getValue(Post.class);              // 데이터베이스의 post값들을 가져옴
                binding.postAuthorLayout.postAuthor.setText(post.author);   // 레이아웃에 지정
                binding.postTextLayout.postTitle.setText(post.title);       // 타이틀 지정
                binding.postTextLayout.postBody.setText(post.body);         // 내용 지정
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(PostDetailActivity.this, "Failed to load post.", Toast.LENGTH_SHORT).show();
            }

        };

        // 값을 가져올때마다 리스너를 쓴다.
        mPostReference.addValueEventListener(postListener);
        mPostListener = postListener;

        // comment를 담을 리사이클러뷰의 어댑터를 지정한다.
        mAdapter = new CommentAdapter(this, mCommentsReference);
        binding.recyclerPostComments.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        // 어댑터의 리스너를 지워준다.
        if (mPostListener != null) {
            mPostReference.removeEventListener(mPostListener);
        }
        // 어댑터의 내용 초기화
        mAdapter.cleanupListener();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonPostComment) {
            // post버튼 눌렸을때 실행한다. comment 추가 수행
            postComment();
        }
    }

    private void postComment() {
        // uid를 가져온다. ex)   8E1iRw6dgwPeWlELpKn6xzF9bUp1
        final String uid = getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                        // user 데이터 모델에서 email, username을 가져온다.
                        User user = datasnapshot.getValue(User.class);
                        String authorName = user.username;

                        String commentText = binding.fieldCommentText.getText().toString();
                        // 얻은 uid를 통해서 comment를 추가한다.
                        Comment comment = new Comment(uid, authorName, commentText);
                        // comment의 값을 추가한다. uid, authorName, commentText
                        // post-comment -> UserId -> author, text, uid
                        mCommentsReference.push().setValue(comment);

                        binding.fieldCommentText.setText(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    /**
     * CommentView의 홀더
     * 댓글의 구성요소를 지정해준다.
     */
    private static class CommentViewHolder extends RecyclerView.ViewHolder {

        public TextView authorView;
        public TextView bodyView;

        public CommentViewHolder(View itemView) {
            super(itemView);

            authorView = itemView.findViewById(R.id.commentAuthor);
            bodyView = itemView.findViewById(R.id.commentBody);
        }
    }

    /**
     * 댓글 어댑터
     * 달린 댓글의 리싸이클러 뷰를 관리하는 어댑터
     */
    private static class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

        private Context mContext;
        private DatabaseReference mDatabaseReference;
        private ChildEventListener mChildEventListener;

        private List<String> mCommentIds = new ArrayList<>();
        private List<Comment> mComments = new ArrayList<>();

        /**
         * 어댑터의 생성
         * @param context
         * @param ref
         */
        public CommentAdapter(final Context context, DatabaseReference ref) {
            mContext = context;
            mDatabaseReference = ref;

            ChildEventListener childEventListener = new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot datasnapshot, @Nullable String previousChildName) {
                    Log.d(TAG, "onChildAdded:" + datasnapshot.getKey());

                    // 댓글이 추가 되었을때, 데이터베이스에서 값을 가져오고 List에 값을 추가한다.

                    Comment comment = datasnapshot.getValue(Comment.class);

                    mCommentIds.add(datasnapshot.getKey());
                    mComments.add(comment);
                    notifyItemInserted(mComments.size() - 1);
                }

                // 댓글의 내용이 바뀌면
                @Override
                public void onChildChanged(@NonNull DataSnapshot datasnapshot, @Nullable String previousChildName) {
                    Log.d(TAG, "onChildChanged:" + datasnapshot.getKey());
                    Comment newComment = datasnapshot.getValue(Comment.class);
                    String commentKey = datasnapshot.getKey();

                    int commentIndex = mCommentIds.indexOf(commentKey);

                    if (commentIndex > -1) {
                        mComments.set(commentIndex, newComment);
                        notifyItemChanged(commentIndex);
                    } else {
                        Log.w(TAG, "onChildChanged:unknown_chil: " + commentKey);
                    }
                }

                // 댓글의 내용이 지워지면
                @Override
                public void onChildRemoved(@NonNull DataSnapshot datasnapshot) {
                    Log.d(TAG, "onChildRemoved:" + datasnapshot.getKey());

                    String commentKey = datasnapshot.getKey();

                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        mCommentIds.remove(commentIndex);
                        mComments.remove(commentIndex);
                        notifyItemChanged(commentIndex);
                    } else {
                        Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
                    }
                }

                // 댓글이 이동하면
                @Override
                public void onChildMoved(@NonNull DataSnapshot datasnapshot, @Nullable String previousChildName) {
                    Log.d(TAG, "onChildMoved:" + datasnapshot.getKey());

                    Comment movedComment = datasnapshot.getValue(Comment.class);
                    String commentKey = datasnapshot.getKey();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                    Toast.makeText(mContext, "Failed to load comments.",
                            Toast.LENGTH_SHORT).show();
                }
            };

            ref.addChildEventListener(childEventListener);
            mChildEventListener = childEventListener;
        }

        // onCreateViewHolder를 통해서 ViewHolder 객체 실체화
        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            Comment comment = mComments.get(position);
            holder.authorView.setText(comment.author);
            holder.bodyView.setText(comment.text);
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }

        public void cleanupListener() {
            if (mChildEventListener != null) {
                mDatabaseReference.removeEventListener(mChildEventListener);
            }
        }

    }
}
