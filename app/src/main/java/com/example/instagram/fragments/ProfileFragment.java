package com.example.instagram.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.example.instagram.PostsAdapter;
import com.example.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {

    @Override
    protected void setRecyclerView() {
        whichFragment = 1;
        adapter = new PostsAdapter(getContext(), mPosts, whichFragment);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    @Override
    protected void queryPosts() {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e == null) {
                    mPosts.addAll(posts);
                    adapter.notifyDataSetChanged();
                    for (int i = 0; i < posts.size(); i++) {
                        Log.d(TAG, "Post [" + i + "] = "
                                + posts.get(i).getDescription()
                                + "username = " + posts.get(i).getUser().getUsername());
                    }
                } else {
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                }
            }
        });
    }
}
