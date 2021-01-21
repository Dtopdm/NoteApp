package com.example.noteapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.App;
import com.example.noteapp.FormFragment;
import com.example.noteapp.OnItemClickListener;
import com.example.noteapp.Prefs;
import com.example.noteapp.R;
import com.example.noteapp.models.Note;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private NoteAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm yyyy/MM/dd", Locale.ROOT);
        String date = simpleDateFormat.format(System.currentTimeMillis());
        for (int i = 1; i < 11; i++) {
            adapter.addItem(new Note("Note " + i, date));
        }
        setHasOptionsMenu(true);
        loadData();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_nav_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navigation_delete)
            new Prefs(requireContext()).deleteBoardStatus();
            openBoardFragment();
            return super.onOptionsItemSelected(item);
        }

    private void openBoardFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.formFragment);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.fab).setOnClickListener(v -> openForm());
        setFragmentListener();
        initList();
    }

    private void loadData() {
        List<Note> list = App.getAppDataBase().noteDao().getAll();
        adapter.setList(list);
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Note note = adapter.getItem(position);
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(int position) {
                deleteItem(position);
            }

            private void deleteItem(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Notice");
                builder.setMessage("Delete this entry?");
                builder.setPositiveButton("Yes", (dialog, which) -> adapter.deleteItem(position));
                builder.setNegativeButton("No", null);
                builder.create().show();
            }
        });
    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener(FormFragment.RK_FORM,
                getViewLifecycleOwner(), (requestKey, result) -> {
//                    Note note = (Note) result.getSerializable("note");
                    Note note = (Note) result.getSerializable(FormFragment.RK_FORM);
                    adapter.addItem(note);
                });
    }

    private void openForm() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.formFragment);
    }
}