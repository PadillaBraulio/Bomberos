package project.bomberos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import project.bomberos.ItemFragment.OnListFragmentInteractionListener;
import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<EmergencyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<EmergencyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.longitude.setText(mValues.get(position).getLongitude());
        holder.latitude.setText(mValues.get(position).getLatitude());
        holder.date.setText(mValues.get(position).getDate());
        holder.phone.setText(mValues.get(position).getTelefone());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView longitude;
        public final TextView latitude;
        public final TextView phone;
        public final TextView date;
        public EmergencyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            longitude = (TextView) view.findViewById(R.id.longitude);
            latitude = (TextView) view.findViewById(R.id.latitude);
            phone = (TextView) view.findViewById(R.id.phone);
            date = (TextView) view.findViewById(R.id.date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + latitude.getText() + "'";
        }
    }
}
