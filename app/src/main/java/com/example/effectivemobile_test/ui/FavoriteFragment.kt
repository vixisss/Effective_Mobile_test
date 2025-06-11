package com.example.effectivemobile_test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.effectivemobile_test.databinding.FragmentFavoriteBinding
import com.example.effectivemobile_test.presentation.MainViewModel
import com.example.effectivemobile_test.ui.adapter.FavoriteCoursesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteCoursesAdapter

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = FavoriteCoursesAdapter(
            courses = emptyList(),
            onFavoriteClick = { course ->
                viewModel.toggleFavorite(course.id)
            }
        )

        binding.listCourses.layoutManager = LinearLayoutManager(requireContext())
        binding.listCourses.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            adapter.updateCourses(courses)
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.loadCourses()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}