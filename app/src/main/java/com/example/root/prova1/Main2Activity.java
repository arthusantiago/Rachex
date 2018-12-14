package com.example.root.prova1;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class Main2Activity extends ListActivity {
    //Meu Array contendo a minha listagem de calculadoras
    String[] listagem_dos_contatos = new String[] {"Jonathas","Arthu","Vinicius"};
    ArrayAdapter<String> meu_adaptador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setContentView(R.layout.activity_main2);

        meu_adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listagem_dos_contatos);
        setListAdapter(meu_adaptador);
        super.onCreate(savedInstanceState);

    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        //passando parâmetros para o construtor do objeto
        Bundle extras = getIntent().getExtras();
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        String assunto = "Valores da Gasolina da Viagem";
        String corpo_email = "Olá, segue o valor da gasolina na viagem: R$" + extras.getString("totalPorPessoa") ;

        switch(position)
        {
            case 0 :
                // o e-mail vai para o Jonathas
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"emaildojonathas@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, assunto);
                i.putExtra(Intent.EXTRA_TEXT, corpo_email);
                try {
                    startActivity(Intent.createChooser(i, "Enviando email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Main2Activity.this, "Configurar o app cliente de email,", Toast.LENGTH_SHORT).show();
                }
                break;

            case 1 :
                // o e-mail vai para o Arthu
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"emaildojonathas@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, assunto);
                i.putExtra(Intent.EXTRA_TEXT, corpo_email);
                try {
                    startActivity(Intent.createChooser(i, "Enviando email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Main2Activity.this, "Configurar o app cliente de email,", Toast.LENGTH_SHORT).show();
                }
                break;

            case 2 :
                // o e-mail vai para o vinicius
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"emaildojonathas@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, assunto);
                i.putExtra(Intent.EXTRA_TEXT, corpo_email);
                try {
                    startActivity(Intent.createChooser(i, "Enviando email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Main2Activity.this, "Configurar o app cliente de email,", Toast.LENGTH_SHORT).show();
                }
                break;

        };

    }

}
