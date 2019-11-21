package com.example.claudia.chiquilt;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;



public class NuevoFragment extends Fragment {
    Spinner org;
    String a = "", orgSelected = "", sexoSelected="";
    EditText txtID, txtNombre, txtCumple, txtTel;
    CheckBox checkSexo;
    Button registra, listar, btnFecha;
    private SQLite sqlite;
    ArrayAdapter<CharSequence> adapter;

    public NuevoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nuevo, container, false);

        org = (Spinner) view.findViewById(R.id.spinnerOrg);
        txtID = (EditText) view.findViewById(R.id.txtID);
        txtNombre = (EditText) view.findViewById(R.id.txtNombre);
        txtCumple= (EditText) view.findViewById(R.id.txtCumple);
        txtTel = (EditText) view.findViewById(R.id.txtTelefono);
        checkSexo= (CheckBox) view.findViewById(R.id.checkBoxSexo);
        registra = (Button) view.findViewById(R.id.botonRegistrar);
        listar = (Button) view.findViewById(R.id.btnList);
        btnFecha = view.findViewById(R.id.btnDate);
        checkSexo.setVisibility(View.VISIBLE);
        sqlite = new SQLite(getContext());
        sqlite.abrir();
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.Opciones, android.R.layout.simple_spinner_item);
        org.setAdapter(adapter);

        org.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                orgSelec();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH);
                int dd = calendario.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);

                        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
                        txtCumple.setText(dateFormat.format(cal.getTime()));

                    }
                }, yy, mm, dd);

                datePicker.show();
            }
        });

        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSexo.isChecked()) {
                    sexoSelected = "Emergencia";
                } else {
                    sexoSelected = "Regular";
                }

                if (!txtID.getText().toString().equals("") && !txtNombre.getText().toString().equals("") && !txtCumple.getText().toString().equals("") &&
                        !txtTel.getText().toString().equals("") && !orgSelected.equals("") && !sexoSelected.equals("")) {

                    int t = Integer.parseInt(txtID.getText().toString());

                    Toast.makeText(getContext(), t + " " + txtNombre.getText().toString() + orgSelected + " " + txtCumple.getText().toString() + " " + sexoSelected + " " +
                            txtTel.getText().toString(), Toast.LENGTH_LONG).show();

                    if (sqlite.addRegistro(t, txtNombre.getText().toString(), orgSelected, txtCumple.getText().toString(), sexoSelected, txtTel.getText().toString(),"path")) {
                        //recuperar id del ultimo registtro y pasa como parmetro

                        Toast.makeText(getContext(), "Registro añadido", Toast.LENGTH_SHORT).show();
                        txtID.setText("");
                        txtNombre.setText("");
                        txtCumple.setText("");
                        txtTel.setText("");
                        org.setSelection(0);
                        checkSexo.setChecked(false);

                    } else {
                        Toast.makeText(getContext(), "Error: compruebe que los datos sean correctos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error: no puede haber campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentPrincipal, new ListarFragment()).commit();
            }
        });


        return view;
    }

    public void orgSelec(){
        String opcion = String.valueOf(org.getSelectedItemId());
        int op = Integer.parseInt(opcion);
        System.out.println(opcion);
        if (op == 0) {

        }
        if (op == 1) {

            orgSelected = adapter.getItem(1).toString();
        } else if (op == 2) {

            orgSelected = adapter.getItem(2).toString();
        } else if (op == 3) {

            orgSelected = adapter.getItem(3).toString();
        } else if (op == 4) {

            orgSelected = adapter.getItem(4).toString();

        }
    }

}