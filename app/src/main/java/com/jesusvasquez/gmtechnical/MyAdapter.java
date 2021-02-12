package com.jesusvasquez.gmtechnical;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public ArrayList<Commit> localDataSet = new ArrayList<Commit>();

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView messageView;
        private final TextView hashView;
        private final TextView authorView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            messageView = (TextView) view.findViewById(R.id.messageView);
            hashView = (TextView) view.findViewById(R.id.hashView);
            authorView = (TextView) view.findViewById(R.id.authorView);
        }
        public TextView getMessageView() {
            return messageView;
        }
        public TextView getAuthorView() {
            return authorView;
        }
        public TextView getHashView() {
            return hashView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     */
    public MyAdapter(ArrayList<Commit> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getMessageView().setText(localDataSet.get(position).message);
        viewHolder.getAuthorView().setText(localDataSet.get(position).name);
        viewHolder.getHashView().setText(localDataSet.get(position).hash);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}
