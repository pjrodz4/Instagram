package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    public int whichFragment;

    public PostsAdapter(Context context, List<Post> posts, int whichFragment) {
        this.context = context;
        this.posts = posts;
        this.whichFragment = whichFragment;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder viewHolder, int i) {
        Post post = posts.get(i);
        viewHolder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvHandle;
        private ImageView ivImage;
        private TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {
            ParseFile image = post.getImage();
            if (whichFragment == 0) {
                tvHandle.setText(post.getUser().getUsername());
                tvDescription.setText(post.getDescription());
            } else if (whichFragment == 1) {
                tvHandle.setVisibility(View.GONE);
                tvDescription.setVisibility(View.GONE);
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                int pxWidth = displayMetrics.widthPixels;
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(pxWidth, pxWidth/3);
                ivImage.setLayoutParams(layoutParams);
            }
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

        }

        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position
                Post post = posts.get(position);
                // create intent for new activity
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                context.startActivity(intent);
            }
        }
    }

}
