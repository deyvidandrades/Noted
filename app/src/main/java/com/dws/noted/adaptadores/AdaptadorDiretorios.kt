package com.dws.noted.adaptadores

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dws.noted.R
import com.dws.noted.activities.DiretorioActivity
import com.dws.noted.asistentes.AnimacaoBotao
import com.dws.noted.objetos.Diretorio
import com.dws.noted.persistencia.Persistencia
import java.util.*
import java.util.concurrent.Executor


class AdaptadorDiretorios(
    context: Context,
    arrayList: ArrayList<Diretorio>,
    private val activity: AppCompatActivity
) :
    RecyclerView.Adapter<AdaptadorDiretorios.ViewHolder>() {

    private val context: Context
    private var arrayList: ArrayList<Diretorio> = ArrayList()

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.item_diretorio, parent, false
        )
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = arrayList[position]

        holder.ivPivado.visibility = if (item.getPrivado()) View.VISIBLE else View.GONE
        holder.ivNotPrivado.visibility = if (item.getPrivado()) View.GONE else View.VISIBLE

        holder.tvNome.text = item.getNome()

        holder.tvNumAnotacoes.text =
            if (item.getNumAnotacoes() < 10) "0" + item.getNumAnotacoes().toString()
            else item.getNumAnotacoes().toString()

        holder.tvNumPalavras.text =
            if (item.getNumPalavras() < 10) "0" + item.getNumPalavras().toString()
            else item.getNumPalavras().toString()

        holder.tvNumLinhas.text =
            if (item.getNumLinhas() < 10) "0" + item.getNumLinhas().toString()
            else item.getNumLinhas().toString()

        holder.relativeDiretorio.setOnClickListener { v ->
            AnimacaoBotao.animar(v)
            if (item.getPrivado()) {
                autenticar(position)

            } else {
                val intent = Intent(context, DiretorioActivity::class.java)
                intent.putExtra("index", position)
                context.startActivity(intent)
            }
        }

        holder.relativeDiretorio.setOnLongClickListener { v ->
            AnimacaoBotao.animar(v)

            if (item.getPrivado())
                autenticarParaRemocao(position, item)
            else {
                Persistencia.getInstance(
                    context.getSharedPreferences(
                        "MAIN_DATA",
                        Context.MODE_PRIVATE
                    )
                ).removerDiretorio(item)

                arrayList.remove(item)
                notifyItemRemoved(position)
            }
            true
        }
    }

    private fun autenticarParaRemocao(position: Int, item: Diretorio) {
        executor = ContextCompat.getMainExecutor(activity)

        biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        context,
                        "Falha ao autenticar", Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    Persistencia.getInstance(
                        context.getSharedPreferences(
                            "MAIN_DATA",
                            Context.MODE_PRIVATE
                        )
                    ).removerDiretorio(item)

                    arrayList.remove(item)
                    notifyItemRemoved(position)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        context, "Falha ao autenticar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticação necessária!")
            .setSubtitle("Para deletar esse diretório a autenticação é necessária.")
            .setNegativeButtonText("Cancelar")
            .build()

        biometricPrompt.authenticate(promptInfo)

    }

    private fun autenticar(pos: Int) {
        executor = ContextCompat.getMainExecutor(activity)

        biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        context,
                        "Falha ao autenticar", Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    val intent = Intent(context, DiretorioActivity::class.java)
                    intent.putExtra("index", pos)
                    context.startActivity(intent)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        context, "Falha ao autenticar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticação")
            .setSubtitle("Para acessar esse diretório a autenticação é necessária.")
            .setNegativeButtonText("Cancelar")
            .build()

        biometricPrompt.authenticate(promptInfo)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNome: TextView
        var tvNumAnotacoes: TextView
        var tvNumPalavras: TextView
        var tvNumLinhas: TextView

        var ivPivado: ImageView
        var ivNotPrivado: ImageView

        var relativeDiretorio: RelativeLayout

        init {
            tvNumAnotacoes = itemView.findViewById(R.id.diretorio_num_anotacoes)
            tvNumPalavras = itemView.findViewById(R.id.diretorio_num_palavras)
            tvNumLinhas = itemView.findViewById(R.id.diretorio_num_linhas)
            tvNome = itemView.findViewById(R.id.diretorio_nome)

            ivPivado = itemView.findViewById(R.id.iv_privado)
            ivNotPrivado = itemView.findViewById(R.id.iv_not_privado)

            relativeDiretorio = itemView.findViewById(R.id.relative_diretorio)
        }
    }

    init {
        this.context = context
        this.arrayList = arrayList
    }
}