package com.example.smarttaskapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LaterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaterFragment extends Fragment implements FragmentButtonsInterface{

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
    private TaskRecyclerViewAdapter adapter;
    private List<Task> copy;

    public LaterFragment(List<Task> taskList,TaskRecyclerViewInterface taskRecyclerViewInterface) {
        this.taskList = taskList;
        this.taskRecyclerViewInterface = taskRecyclerViewInterface;
        // Required empty public constructor
    }

    public LaterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LaterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LaterFragment newInstance(String param1, String param2) {
        LaterFragment fragment = new LaterFragment();
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
        return inflater.inflate(R.layout.fragment_later, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String[] date = new String[1];
        copy =taskListCopy(taskList);
        initializeList(copy,date[0]);
        CalendarView calendarView = view.findViewById(R.id.calenderFilter);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Create a Calendar instance and set the selected date
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Create a SimpleDateFormat for the desired format
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

                // Format the selected date
                String formattedDate = sdf.format(selectedDate.getTime());
                date[0] = formattedDate;
                copy = taskListCopy(taskList);
                initializeList(copy,date[0]);
                adapter.taskList = copy;
                adapter.notifyDataSetChanged();
                // Log or use the formatted date as needed
                Log.d("FormattedDate", formattedDate);

            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFragmentl);
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

    private void initializeList(List<Task> taskList, String date) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        }

        Iterator<Task> iterator = taskList.iterator();
        while (iterator.hasNext()) {
            Task t = iterator.next();

            if (t.getDueTo().equals("")) {
                iterator.remove();
                continue;
            }

            for (int i = 0; i < date.length(); i++) {
                if (t.getDueTo().charAt(i) != date.charAt(i)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public TaskRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public List<Task> getList() {
        return copy;
    }
}