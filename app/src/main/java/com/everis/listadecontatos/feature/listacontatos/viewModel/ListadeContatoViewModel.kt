package com.everis.listadecontatos.feature.listacontatos.viewModel

import android.view.View
import android.widget.Toast
import com.everis.listadecontatos.application.ContatoApplication
import com.everis.listadecontatos.feature.listacontatos.adapter.ContatoAdapter
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.feature.listacontatos.repository.ListaDeContatosRepository
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class ListadeContatoViewModel (
        var repository: ListaDeContatosRepository? = null

        ){

    fun getListadeContatos(
        busca: String,
        onSucess: ((List<ContatosVO>)->Unit),
        onError: ((Exception)->Unit)
    ){
        Thread(Runnable {
            Thread.sleep(1500)
            repository?.requestListaDeContatos(
                busca,
                onSucess = {lista ->
                    onSucess(lista)
                }, onError =  { ex ->
                    onError(ex)
                }
            )

        }).start()

    }

}