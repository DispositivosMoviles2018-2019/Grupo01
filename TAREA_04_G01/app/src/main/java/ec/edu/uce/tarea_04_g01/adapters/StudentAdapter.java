package ec.edu.uce.tarea_04_g01.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ec.edu.uce.tarea_04_g01.R;
import ec.edu.uce.tarea_04_g01.models.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolderStudents> {

    ArrayList<Student> students;

    public StudentAdapter(ArrayList<Student> students) {
        this.students = students;
    }

    @Override
    public ViewHolderStudents onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_student, null, false);

        return new ViewHolderStudents(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderStudents holder, int position) {
        holder.name.setText(students.get(position).getName());
        holder.description.setText(students.get(position).getDescription());
        holder.photo.setImageResource(students.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolderStudents extends RecyclerView.ViewHolder {

        TextView name;
        TextView description;
        ImageView photo;

        public ViewHolderStudents(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.idName);
            description = (TextView) itemView.findViewById(R.id.idDescription);
            photo = (ImageView) itemView.findViewById(R.id.idPhoto);
        }
    }
}
