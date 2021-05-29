package com.example.edlink.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.edlink.R
import com.example.edlink.adapter.ClassesPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_classes.*
import kotlinx.android.synthetic.main.fragment_classes.toolbar
import kotlinx.android.synthetic.main.fragment_home.*

class ClassesFragment : Fragment() {

    private lateinit var pageAdapter: ClassesPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = "Classes"
        pageAdapter = ClassesPagerAdapter(childFragmentManager)

        pageAdapter.addFragment(AcademicFragment(),"Academic")
        pageAdapter.addFragment(GeneralFragment(),"General")
        classesViewPager.adapter = pageAdapter
        classesTabLayout.setupWithViewPager(classesViewPager)

    }


}