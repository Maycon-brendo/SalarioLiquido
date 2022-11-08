package com.example.salarioliquido

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.salarioliquido.databinding.ActivityMainBinding
import com.example.salarioliquido.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }

    private fun setup() {
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnCalcular.setOnClickListener {
            if (!binding.textInputLayoutSalarioBrutoDentro.text.isNullOrBlank()) {

                //setando salario
                viewModel.setSalario(
                    binding.textInputLayoutSalarioBrutoDentro.text.toString().toDouble()
                )

                //setando salario
                if (!binding.textInputLayoutNumeroDeDepentendesDentro.text.isNullOrBlank()) {

                    viewModel.setnumDependentes(
                        binding.textInputLayoutNumeroDeDepentendesDentro.text.toString().toInt()
                    )
                } else {
                    viewModel.setnumDependentes(0)

                }

                //setando pensao
                if (!binding.textInputLayoutPensaoDentro.text.isNullOrBlank()) {

                    viewModel.setPensao(
                        binding.textInputLayoutPensaoDentro.text.toString().toDouble()
                    )
                } else {
                    viewModel.setPensao(0.00)

                }

                //setando plano
                if (!binding.textInputLayoutPlanoSaudeDentro.text.isNullOrBlank()) {
                    viewModel.setPlanoSaude(
                        binding.textInputLayoutPlanoSaudeDentro.text.toString().toDouble()
                    )
                } else {
                    viewModel.setPlanoSaude(0.00)
                }

                //setando plano
                if (!binding.textInputLayoutOutrosDentro.text.isNullOrBlank()) {
                    viewModel.setOustrosDes(
                        binding.textInputLayoutOutrosDentro.text.toString().toDouble()
                    )
                } else {
                    viewModel.setOustrosDes(0.00)
                }


                //calculo final
                viewModel.calFinal()
                
                

                //prox pag
//                val intent = Intent(this, ResultadoActivity::class.java)
//                intent.putExtra(ResultadoActivity.MEU_SALARIO, viewModel.salarioFinal.value.toString())
//                intent.putExtra("MEU_DESCONTO", viewModel.descontoFinal.value.toString())
//                intent.putExtra("MEU_DESCONTOPORCENT", viewModel.descontoPorcentagemFinal.value.toString())
//                startActivity(intent)
                val intent = Intent(this, ResultadoActivity::class.java)
                val extras = Bundle()
                extras.putString("EXTRA_SALARIO", viewModel.salarioFinal.value.toString())
                extras.putString("EXTRA_DESCONTO", viewModel.descontoFinal.value.toString())
                extras.putString("EXTRA_PORCENTAGEM", viewModel.descontoPorcentagemFinal.value.toString())
                intent.putExtras(extras)
                startActivity(intent)
            }
        }
    }
}