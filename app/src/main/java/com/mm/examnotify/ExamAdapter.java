package com.mm.examnotify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ExamAdapter extends ListAdapter<Exam, ExamAdapter.ExamHolder> {
    private OnItemClickListener listener;

    public ExamAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Exam> DIFF_CALLBACK = new DiffUtil.ItemCallback<Exam>() {
        @Override
        public boolean areItemsTheSame(Exam oldItem, Exam newItem) {
            return oldItem.getExam_id() == newItem.getExam_id();
        }

        @Override
        public boolean areContentsTheSame(Exam oldItem, Exam newItem) {
            return oldItem.getExam_title().equals(newItem.getExam_title()) &&
                    oldItem.getExam_date().equals(newItem.getExam_date()) &&
                    oldItem.getExam_time().equals(newItem.getExam_time());
        }
    };

    @NonNull
    @Override
    public ExamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_item, parent, false);
        return new ExamHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamHolder holder, int position) {
        Exam currentExam = getItem(position);
        holder.textViewExamTitle.setText(currentExam.getExam_title());
        holder.textViewExamDate.setText(currentExam.getExam_date());
        holder.textViewExamTime.setText(currentExam.getExam_time());
    }

    public Exam getExamAt(int position) {
        return getItem(position);
    }

    class ExamHolder extends RecyclerView.ViewHolder {
        private TextView textViewExamTitle;
        private TextView textViewExamDate;
        private TextView textViewExamTime;

        public ExamHolder(View itemView) {
            super(itemView);
            textViewExamTitle = itemView.findViewById(R.id.text_view_exam_title);
            textViewExamDate = itemView.findViewById(R.id.text_view_exam_date);
            textViewExamTime = itemView.findViewById(R.id.text_view_exam_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Exam exam);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}