package com.example.tasksapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tasksapp.databinding.FragmentTasksBinding


class TasksFragment : Fragment() {

    private var _binding:FragmentTasksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentTasksBinding.inflate(inflater,container,false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao

        /**
         * The above code gets a reference to the current application, builds the
        database if it doesn’t already exist, and returns an instance of it. It then
        assigns its TaskDao object to a local variable named dao.
        Now that we know how to get a reference to a TaskDao object, we can
        update the TasksFragment code to create a
        TasksViewModelFactory
         */
        val viewModelFactory = TasksViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(TasksViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = TaskItemAdapter()
        binding.tasksList.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}