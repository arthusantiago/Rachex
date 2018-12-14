package com.example.root.prova1;

import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView qtdKm;
    private Spinner combustivel;
    private RadioButton soIda;
    private RadioButton idaEvolta;
    private RadioGroup radioGroup;
    private TextView qtdAmigos;
    private Button calcular;
    private Button enviarEmail;
    private Button limpar;
    private TextView total;
    private AlertDialog.Builder diag_mensagem;
    int tempCombustivel;
    Double totalPorPessoa = 0.0;
    DecimalFormat df2 = new DecimalFormat(".##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qtdKm = findViewById(R.id.qtdKm);
        combustivel = findViewById(R.id.combustivel);
        soIda = findViewById(R.id.soIda);
        idaEvolta = findViewById(R.id.idaEvolta);
        radioGroup = findViewById(R.id.radioGroup);
        qtdAmigos = findViewById(R.id.qtdAmigos);
        calcular = findViewById(R.id.calcular);
        enviarEmail = findViewById(R.id.enviarEmail);
        limpar = findViewById(R.id.limpar);
        total = findViewById(R.id.total);

        ArrayList<String> opcoesCombustiveis = new ArrayList<>();
        opcoesCombustiveis.add("Combustivel:");
        opcoesCombustiveis.add("Gasolina");
        opcoesCombustiveis.add("Álcool");
        opcoesCombustiveis.add("Disel");
        //Construindo adaptador do Spinner
        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,opcoesCombustiveis)
                {
                    //Desabilitar a primeira opção do Array opcoesSpinner
                    @Override
                    public boolean isEnabled(int position){
                        if(position == 0)
                        {
                            // Desabilita o primeiro item o transforma em hint(sugestão) da list
                            return false;
                        }else
                        {
                            return true;
                        }
                    }
                    //
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if(position == 0){
                            // Deixa o primeiro item em cinza
                            tv.setTextColor(Color.GRAY);
                        }
                        else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
        combustivel.setAdapter(spinnerArrayAdapter);

        calcular.setOnClickListener(ouvinteCalcular);
        limpar.setOnClickListener(ouvinteLimpar);
        enviarEmail.setOnClickListener(ouvinteEmail);
    }


    //Ouvinte do calcular
    View.OnClickListener ouvinteCalcular = new View.OnClickListener()
    {
        @Override
        public void onClick (View v)
        {

            //verificando se os campos estão vazios
            if(qtdKm.getText().toString().trim().equals("") && qtdAmigos.getText().toString().trim().equals(""))
            {
                alertar(1);
                //diag_mensagem.setMessage("Você precisa preenchar algum campo!");
                //diag_mensagem.setNeutralButton("OK",null);
                //diag_mensagem.show();
            }else{
                if(TextUtils.isEmpty(qtdKm.getText().toString())){
                    qtdKm.setError("");
                }else if(TextUtils.isEmpty(qtdAmigos.getText().toString())){
                    qtdAmigos.setError("");
                }else{
                    //logica da programação
                    /*
                    * qtdGasolina = distancia / consumoCarro/L
                    * totalPorPessoa = qtdAmigos / ((qtdGasolina * ida/idaEvolta )* combustivel)
                    * */

                    //Quantidade de gasolina que vou precisar
                    Double kms = Double.parseDouble(qtdKm.getText().toString());
                    Double qtdCombustivel = kms / 10;

                    //Variáveis para o calculo
                    int tempQtdAmigos = Integer.parseInt(qtdAmigos.getText().toString());
                    tempCombustivel = combustivel.getSelectedItemPosition();


                    // Esse estrutura condicional me fala se foi selecionado Ida ou Ida e volta
                    if(radioGroup.getCheckedRadioButtonId() == R.id.soIda){

                        switch (tempCombustivel){
                            case 1: //Gasolina = 4,35
                                totalPorPessoa = (qtdCombustivel * 4.35) / tempQtdAmigos;
                                break;
                            case 2: //Álcool = 3,35
                                totalPorPessoa = (qtdCombustivel * 3.35) / tempQtdAmigos;
                                break;
                            case 3: //Disel = 3,39
                                totalPorPessoa = (qtdCombustivel * 3.39) / tempQtdAmigos;
                                break;
                            default:
                             alertar(2);
                                break;
                        }
                    }else if (radioGroup.getCheckedRadioButtonId() == R.id.idaEvolta){
                        switch (tempCombustivel){
                            case 1: //Gasolina = 4,35
                                totalPorPessoa = ((qtdCombustivel * 2) * 4.35) / tempQtdAmigos ;
                                break;
                            case 2: //Álcool = 3,35
                                totalPorPessoa = ((qtdCombustivel * 2) * 3.35) / tempQtdAmigos;
                                break;
                            case 3: //Disel = 3,39
                                totalPorPessoa = ((qtdCombustivel * 2) * 3.39) / tempQtdAmigos;
                                break;
                            default:
                                alertar(2);
                                break;
                        }
                    }else{
                        alertar(3);
                    }
                    //Exibindo o resultado

                    total.setText(df2.format(totalPorPessoa));
                }
            }
        }
    };



    public void alertar (int lugar){
        diag_mensagem = new AlertDialog.Builder(MainActivity.this);
        diag_mensagem.setTitle("Rachex");
        switch (lugar){
            case 1: // Campos de qtdAmigos e qtdKm vazio
                diag_mensagem.setMessage("Você precisa preenchar algum campo!");
                diag_mensagem.setNeutralButton("OK",null);
                diag_mensagem.show();
                break;
            case 2: // Switch Case dos combustiveis. Caso a pessoa não tenha escolhido um combustivel
                diag_mensagem.setMessage("Você precisa escolher um combustivel.");
                diag_mensagem.setNeutralButton("OK",null);
                diag_mensagem.show();
                break;
            case 3: //Caso a pessoa não tenha selecionado a opção Ida ou Ida e volta
                diag_mensagem.setMessage("Você precisa dizer se é Ida ou Ida e Volta");
                diag_mensagem.setNeutralButton("OK",null);
                diag_mensagem.show();
                break;
            case 4: //teste
                diag_mensagem.setMessage("cheguei aqui !");
                diag_mensagem.setNeutralButton("OK",null);
                diag_mensagem.show();
                break;
        }
    };

    //função botão de limpar
    View.OnClickListener ouvinteLimpar = new View.OnClickListener()
    {
        @Override
        public void onClick (View v) {
            qtdAmigos.setText("");
            qtdKm.setText("");
            total.setText("");
        };
    };

    View.OnClickListener ouvinteEmail = new View.OnClickListener()
    {
        @Override
        public void onClick (View v) {
            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
            String temp = df2.format(totalPorPessoa);
            intent.putExtra("totalPorPessoa",temp);
            startActivity(intent);
        };
    };

    public TextView getTotal() {
        return total;
    }
}
