package com.example.smarttaskapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment implements FragmentButtonsInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView view;
    private List<Task> taskList;
    private TaskRecyclerViewInterface taskRecyclerViewInterface;
    List<Task> copy;

    public TaskRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public List<Task> getList() {
        return copy;
    }

    @Override
    public void removeTask(int position) {
        Log.i("removeTaskTodayF", "removeTask: copy size = " + copy.size() + "task removed :" + copy.get(position).getType());
        taskList.remove(copy.get(position));copy.remove(position);
    }

    private TaskRecyclerViewAdapter adapter;

    public TodayFragment(List<Task> taskList,TaskRecyclerViewInterface taskRecyclerViewInterface,TaskRecyclerViewAdapter adaptert) {
        this.taskList = taskList;
        this.taskRecyclerViewInterface = taskRecyclerViewInterface;

        // Required empty public constructor
    }

    public TodayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayFragment newInstance(String param1, String param2) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("todayfragment", "onviewcreate :  task size = " + taskList.size());
        copy =taskListCopy(taskList);
        initializeList(copy);
        Log.i("todayfragment", "onviewcreate :  task copy size = " + copy.size());


        RecyclerView recyclerView = view.findViewById(R.id.reyclerViewFragmentt);
        adapter = new TaskRecyclerViewAdapter(getContext(),copy,taskRecyclerViewInterface);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }

    private List<Task> taskListCopy(List<Task> taskList) {
        List<Task> copy = new ArrayList<Task>();
        if (taskList != null ){
        for (Task t:taskList) {
            copy.add(t);
        } return copy;
    }return copy;
    }

    private void initializeList(List<Task> taskList) {
        String today = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            today = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        }

        Iterator<Task> iterator = taskList.iterator();
        while (iterator.hasNext()) {
            Task t = iterator.next();

            if (t.getDueTo().equals("")) {
                iterator.remove();
                continue;
            }

            for (int i = 0; i < today.length(); i++) {
                if (t.getDueTo().charAt(i) != today.charAt(i)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    // Public method to update the task in the fragment
    public void updateTask(int position, Task updatedTask) {
        // Implement the logic to update the task in your RecyclerView
        // For example, update the task in your taskList and notify the adapter
        if (position >= 0 && position < copy.size()) {
            copy.set(position, updatedTask);
            adapter.notifyItemChanged(position);
        }
    }
}