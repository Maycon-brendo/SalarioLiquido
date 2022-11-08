package com.example.salarioliquido.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var descontoINSS = 0.00
    var descontoIRPF = 0.00
    var valorDescontoTotal = 0.00
    var porcentagemDescontoTotal = 0.00
    var valorDescontoDependentes = 0.00
    var salarioLiq = 0.00
    var condINSS = 0.00

    //salario
    private val _salario: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
    val salario: LiveData<Double> = _salario

    fun setSalario(value: Double) {
        _salario.setValue(value)
    }

    //dependentes
    private val _numDependentes: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val numDependentes: LiveData<Int> = _numDependentes

    fun setnumDependentes(value: Int) {
        _numDependentes.setValue(value)
    }


    //pensao
    private val _pensao: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
    val pensao: LiveData<Double> = _pensao

    fun setPensao(value: Double) {
        _pensao.setValue(value)
    }


    //plano saude
    private val _planoSaude: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
    val planoSaude: LiveData<Double> = _planoSaude

    fun setPlanoSaude(value: Double) {
        _planoSaude.setValue(value)
    }


    //outros
    private val _outrosDes: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
    val outrosDes: LiveData<Double> = _outrosDes

    fun setOustrosDes(value: Double) {
        _outrosDes.setValue(value)
    }



    private val _salarioFinal: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
    val salarioFinal: LiveData<Double> = _salarioFinal

    fun setsalarioFinal(value: Double) {
        _salarioFinal.setValue(value)
    }

    private val _descontoFinal: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
    val descontoFinal: LiveData<Double> = _descontoFinal

    fun setdescontoFinal(value: Double) {
        _descontoFinal.setValue(value)
    }

    private val _descontoPorcentagemFinal: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
    val descontoPorcentagemFinal: LiveData<Double> = _descontoPorcentagemFinal

    fun setdescontoPorcentagemFinal(value: Double) {
        _descontoPorcentagemFinal.setValue(value)
    }

    fun calculaDesINSS() {
        if (salario.value!! <= 1659.38) {
            descontoINSS = 8.0
        } else if (salario.value!! in 1659.39..2765.66) {
            descontoINSS = 9.0
        } else if (salario.value!! in 2765.67..5531.31) {
            descontoINSS = 11.0
        } else if(salario.value!! > 5531.31){
            condINSS = 608.44
        }
    }

    fun calculaDesIRPF() {

        if (salario.value!! <= 1903.98) {
            descontoIRPF = 0.0
        } else if (salario.value!! in 1903.99..2826.65) {
            descontoIRPF = 7.5
        } else if (salario.value!! in 2826.66..3751.05) {
            descontoIRPF = 15.0
        } else if (salario.value!! in 3751.06..4664.68) {
            descontoIRPF = 22.5
        } else if (salario.value!! > 4664.68){
            descontoIRPF = 27.5
        }
    }

    fun calculaPorcentagemTotalDesconto() {
        porcentagemDescontoTotal = descontoINSS + descontoIRPF
    }

    fun calculaDesDependentes() {
        valorDescontoDependentes = numDependentes.value!! * 189.59
    }

    fun calculaDescontoTotal() {
        valorDescontoTotal = salario.value!! - salarioLiq
    }

    fun calFinal() {
        calculaDesINSS()
        calculaDesIRPF()
        calculaPorcentagemTotalDesconto()
        calculaDesDependentes()
        setdescontoPorcentagemFinal(porcentagemDescontoTotal)
        if (condINSS == 0.00) {
            salarioLiq = salario.value!! -
                    descontoINSS / 100 * salario.value!! - descontoIRPF / 100 * salario.value!! - pensao.value!! - valorDescontoDependentes - outrosDes.value!! - planoSaude.value!!
            calculaDescontoTotal()
            setsalarioFinal(salarioLiq)
            setdescontoFinal(valorDescontoTotal)
        } else {
            salarioLiq = salario.value!! -
                    condINSS - (descontoIRPF / 100 * salario.value!!) - pensao.value!! - valorDescontoDependentes - outrosDes.value!! - planoSaude.value!!
            setsalarioFinal(salarioLiq)
            setdescontoFinal(valorDescontoTotal)
        }
    }
}
