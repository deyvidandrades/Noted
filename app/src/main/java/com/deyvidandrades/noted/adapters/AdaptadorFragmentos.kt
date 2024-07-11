package com.deyvidandrades.noted.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.deyvidandrades.noted.fragments.FragmentAnotacoes
import com.deyvidandrades.noted.fragments.FragmentCategorias

class AdaptadorFragmentos(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private val arrayFragmentos = arrayOf(
        FragmentAnotacoes(),
        FragmentCategorias()
    )

    override fun getItemCount() = arrayFragmentos.count()

    override fun createFragment(position: Int): Fragment {
        return arrayFragmentos[position] as Fragment
    }
}