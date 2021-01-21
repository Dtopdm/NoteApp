package com.example.noteapp.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.OnItemClickListener;
import com.example.noteapp.R;
import com.example.noteapp.models.Note;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder>{

    private String[] titles = new String[]{"Note App","Fast", "Free"};
    private String[] descriptions = new String[]{"Welcome to the era of fast and secure noting", "The world fastest note app", "Free forever. No ads. No subscription fees"};
    private int[] images = new int[]{R.drawable.note, R.drawable.note2, R.drawable.note3};
    private OnItemClickListener onItemClickListener;

    public BoardAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDescription;
        private Button btnStart;
        private ImageView imageBoard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            imageBoard = itemView.findViewById(R.id.imageBoard);
            btnStart = itemView.findViewById(R.id.btnStart);
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(getAdapterPosition());
                }
            });
        }

        public void bind(int position) {
            textTitle.setText(titles[position]);
            textDescription.setText(descriptions[position]);
            imageBoard.setImageResource(images[position]);
            if (position == (titles.length - 3)) btnStart.setVisibility(View.VISIBLE);
            else  btnStart.setVisibility(View.GONE);
        }
    }
}
