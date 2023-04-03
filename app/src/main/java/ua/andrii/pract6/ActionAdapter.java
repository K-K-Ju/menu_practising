package ua.andrii.pract6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ActionViewHolder> {
    String[] actions;
    MainActivity parentContext;

    public ActionAdapter(String [] actions){
        this.actions = actions;
    }

    @NonNull
    @Override
    public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentContext = (MainActivity) parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ActionViewHolder(
                layoutInflater.inflate(
                        R.layout.action_layout,
                        parent,
                        false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {
        String action = actions[position];
        ((TextView)holder.itemView.findViewById(R.id.tvAction)).setText(action);
    }

    @Override
    public int getItemCount() {
        return actions.length;
    }

    public class ActionViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {
        View itemView;
        public ActionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TextView text = (TextView) view;
            if ("+".contentEquals(text.getText())) {
                parentContext.performAction(MainActivity.Action.ADD);
            } else if ("-".contentEquals(text.getText())) {
                parentContext.performAction(MainActivity.Action.SUB);
            } else if ("*".contentEquals(text.getText())) {
                parentContext.performAction(MainActivity.Action.MUL);
            } else {
                parentContext.performAction(MainActivity.Action.DIV);
            }
        }
    }
}
