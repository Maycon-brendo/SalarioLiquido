package com.example.salarioliquido

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.salarioliquido.databinding.ActivityResultadoBinding
import com.example.salarioliquido.viewmodel.MainViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class ResultadoActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityResultadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }

    val sdf = SimpleDateFormat("dd-M-yyyy-|-hh-mm")
    val currentDate = sdf.format(Date())

    var tituloSalario = ""
    var tituloDesconto = ""
    var tituloPorcentagem = ""


    private fun setup() {
        setupClickListeners()
        setupViews()
    }

    private fun setupClickListeners() {
        binding.btnSalvar.setOnClickListener {
            callWriteOnExternalStorage()
        }
    }

    fun callWriteOnExternalStorage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                callDialog(
                    "É preciso liberar WRITE_EXTERNAL_STORAGE",
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS_CODE
                )
            }
        } else {
            criarFile()
        }
    }

    private val REQUEST_PERMISSIONS_CODE = 12800

    private fun callDialog(messagem: String, permissions: Array<String>) {
        var mDialog = AlertDialog.Builder(this)
            .setTitle("Permissão")
            .setMessage(messagem)
            .setPositiveButton("OK") { dialog, id ->
                ActivityCompat.requestPermissions(
                    this@ResultadoActivity, permissions,
                    REQUEST_PERMISSIONS_CODE
                )
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, id ->
                dialog.dismiss()
            }
        mDialog.show()
    }

    private fun criarFile() {
        val file = File(getExternalFilesDir(null), "${currentDate}.txt")
        try {
            val os: OutputStream = FileOutputStream(file)
            os.write("${binding.tvSalario.text} \n${binding.tvTotalDescontos.text} \n${binding.tvPorcentagemDesconto.text}".toByteArray())
            os.close()
        } catch (e: IOException) {
            Log.d("Permissao", "Erro de escrita em arquivo")
        }

    }

    private fun setupViews() {

        val intent = intent
        val extras = intent.extras
        val salario = extras!!.getString("EXTRA_SALARIO")
        val desconto = extras!!.getString("EXTRA_DESCONTO")
        val desconto_porcentagem = extras!!.getString("EXTRA_PORCENTAGEM")
        binding.tvSalario.text = "Salario liquido R\$ ${salario}"
        binding.tvTotalDescontos.text = "Total de descontoR\$ ${desconto}"
        binding.tvPorcentagemDesconto.text = "Total de porcentagem subtraida  ${desconto_porcentagem}"
    }
}