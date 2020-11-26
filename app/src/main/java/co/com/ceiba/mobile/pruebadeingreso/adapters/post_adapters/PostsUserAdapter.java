package co.com.ceiba.mobile.pruebadeingreso.adapters.post_adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.models.post_model.PostData;

public class PostsUserAdapter extends RecyclerView.Adapter<PostsUserAdapter.MyViewHolder> {

    ArrayList<PostData> postData;
    public PostsUserAdapter(ArrayList<PostData> postData){
        this.postData = postData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(postData.get(position).getTitle());
        holder.body.setText(postData.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        if( postData != null)
            return postData.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, body;
        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
        }
    }
}
