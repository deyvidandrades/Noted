package com.deyvidandrades.noted.dialogs

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import com.deyvidandrades.noted.R
import com.deyvidandrades.noted.assistentes.AnimacaoBotao
import com.deyvidandrades.noted.assistentes.Persistencia
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.materialswitch.MaterialSwitch

class DialogoConfiguracoes : BottomSheetDialogFragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.dialogo_configuracoes, container, false)

        val switchDarkMode: MaterialSwitch = view.findViewById(R.id.switch_dark_mode)
        val switchPrivado: MaterialSwitch = view.findViewById(R.id.switch_privado)

        val tvAbrirTermos: TextView = view.findViewById(R.id.tv_termos)
        val tvVersao: TextView = view.findViewById(R.id.tv_versao)

        val btnSalvar: Button = view.findViewById(R.id.btn_salvar)

        switchDarkMode.isChecked = Persistencia.isDarkTheme
        switchPrivado.isChecked = Persistencia.isPrivado
        switchPrivado.visibility = if (Persistencia.isPrivado && !Persistencia.auth) View.GONE else View.VISIBLE

        val info = requireContext().packageManager.getPackageInfo(
            requireContext().packageName, PackageManager.GET_ACTIVITIES
        )
        tvVersao.text = "${requireContext().getString(R.string.app_name)} v${info.versionName}"

        tvAbrirTermos.setOnClickListener {
            AnimacaoBotao.animar(it)
            //todo
            startActivity(Intent(Intent.ACTION_VIEW, getString(R.string.url_termos).toUri()))
        }

        btnSalvar.setOnClickListener {
            Persistencia.setAnotacoesPrivado(switchPrivado.isChecked)
            Persistencia.setDarkMode(switchDarkMode.isChecked)

            AppCompatDelegate.setDefaultNightMode(
                if (Persistencia.isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            dismiss()
        }

        return view
    }
}