package com.everis.listadecontatos.feature.listacontatos.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.everis.listadecontatos.R
import com.everis.listadecontatos.application.ContatoApplication
import com.everis.listadecontatos.bases.BaseActivity
import com.everis.listadecontatos.feature.contato.ContatoActivity
import com.everis.listadecontatos.feature.listacontatos.adapter.ContatoAdapter
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO
import com.everis.listadecontatos.feature.listacontatos.repository.ListaDeContatosRepository
import com.everis.listadecontatos.feature.listacontatos.viewModel.ListadeContatoViewModel
import com.everis.listadecontatos.helpers.HelperDB
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class ListaDeContatosActivity : BaseActivity() {

    private var adapter:ContatoAdapter? = null

    var viewModel: ListadeContatoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(viewModel == null)viewModel = ListadeContatoViewModel(
            ListaDeContatosRepository(
                HelperDB(this)
            )
        )
        setupToolBar(toolBar, "Lista de contatos",false)
        setupListView()
        setupOnClicks()
    }

    private fun setupOnClicks(){
        fab.setOnClickListener { onClickAdd() }
        ivBuscar.setOnClickListener { onClickBuscar() }
    }

    private fun setupListView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        onClickBuscar()
    }

    private fun onClickAdd(){
        val intent = Intent(this,ContatoActivity::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int){
        val intent = Intent(this,ContatoActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun onClickBuscar(){
        val busca = etBuscar.text.toString()
        progress.visibility = View.VISIBLE
        viewModel?.getListadeContatos(
            busca,
            onSucess = { listaFiltrada ->
                runOnUiThread {
                    adapter = ContatoAdapter(this,listaFiltrada) {onClickItemRecyclerView(it)}
                    recyclerView.adapter = adapter
                    progress.visibility = View.GONE
                    Toast.makeText(this,"Buscando por $busca",Toast.LENGTH_SHORT).show()
                }

            }, onError = { ex ->
                runOnUiThread {
                    AlertDialog.Builder(this)
                        .setTitle("Aten????o")
                        .setMessage("N??o foi Posivel Completar Sua Solicita????o")
                        .setPositiveButton("OK"){alert, i ->
                            alert.dismiss()
                        }

                }

            }
        )

    }

}
