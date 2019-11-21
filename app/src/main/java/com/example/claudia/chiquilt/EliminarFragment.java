package com.example.claudia.chiquilt;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class EliminarFragment extends Fragment {
    Spinner org;
    String a = "", orgSelected = "", sexoSelected = "";
    EditText txtID, txtNombre, txtCumple, txtTel;
    TextView txteligeSexo;
    CheckBox checkSexo;
    Button btnBuscar, btnEliminar;
    private SQLite sqlite;
    ArrayAdapter<CharSequence> adapter;
    int es;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eliminar, container, false);

        org = (Spinner) view.findViewById(R.id.spinnerOrgDelete);
        txtID = (EditText) view.findViewById(R.id.txtIDDelete);
        txtNombre = (EditText) view.findViewById(R.id.txtNombreDelete);
        txtCumple= (EditText) view.findViewById(R.id.txtCumpleDelete);
        txtTel = (EditText) view.findViewById(R.id.txtTelefonoDelete);
        checkSexo= (CheckBox) view.findViewById(R.id.checkBoxSexoDelete);
        btnBuscar = (Button) view.findViewById(R.id.btnBuscarDelete);
        btnEliminar = view.findViewById(R.id.Delete);
        txteligeSexo = view.findViewById(R.id.textViewSexoDelete);

        org.setVisibility(View.INVISIBLE);
        txtNombre.setVisibility(View.INVISIBLE);
        txtCumple.setVisibility(View.INVISIBLE);
        txtTel.setVisibility(View.INVISIBLE);
        checkSexo.setVisibility(View.INVISIBLE);
        txteligeSexo.setVisibility(View.INVISIBLE);
        btnEliminar.setVisibility(View.INVISIBLE);
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

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sqlite=new SQLite(getContext());
                sqlite.abrir();

                System.out.print("Entraste");
                if(txtID.getText().toString()!=null){
                    sqlite.Eliminar(txtID.getText());
                    Toast.makeText(getContext(), "Se ha eliminado el registro con ID: "+txtID.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Llene el campo", Toast.LENGTH_SHORT).show();
                }




                txtID.setText("");
                txtNombre.setText("");
                txtCumple.setText("");
                txtTel.setText("");
                org.setSelection(0);
                checkSexo.setChecked(false);
                org.setVisibility(View.INVISIBLE);
                txtNombre.setVisibility(View.INVISIBLE);
                txtCumple.setVisibility(View.INVISIBLE);
                txtTel.setVisibility(View.INVISIBLE);
                checkSexo.setVisibility(View.INVISIBLE);
                txteligeSexo.setVisibility(View.INVISIBLE);
                btnEliminar.setVisibility(View.INVISIBLE);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!txtID.getText().toString().equals("")) {
                    if (sqlite.getCant(Integer.parseInt(txtID.getText().toString())).getCount() == 1) {
                        org.setVisibility(View.VISIBLE);
                        txtNombre.setVisibility(View.VISIBLE);
                        txtCumple.setVisibility(View.VISIBLE);
                        txtTel.setVisibility(View.VISIBLE);
                        checkSexo.setVisibility(View.VISIBLE);
                        txteligeSexo.setVisibility(View.VISIBLE);
                        btnEliminar.setVisibility(View.VISIBLE);
                        int f = Integer.parseInt(txtID.getText().toString());
                        Cursor cursor = sqlite.getCant(f);
                        String nombre = null, organizacion = null, cumple = null, sexo = null, telefono = null, foto=null;
                        if (cursor.moveToFirst()) {
                            do {
                                nombre = cursor.getString(1);
                                organizacion = cursor.getString(2);
                                cumple = cursor.getString(3);
                                sexo = cursor.getString(4);
                                telefono = cursor.getString(5);
                                foto = cursor.getString(6);
                            } while (cursor.moveToNext());
                        }

                        switch (organizacion) {
                            case "Cita medica":
                                org.setSelection(1);
                                break;
                            case "Vacuna":
                                org.setSelection(2);
                                break;
                            case "Evento":
                                org.setSelection(3);
                                break;
                            case "Otro":
                                org.setSelection(4);
                                break;
                        }

                        txtNombre.setText(nombre);
                        txtCumple.setText(cumple);
                        txtTel.setText(telefono);
                        checkSexo.setChecked(sexo.equals("Emergencia"));
                        txtNombre.setEnabled(false);
                        org.setEnabled(false);
                        txtCumple.setEnabled(false);
                        checkSexo.setEnabled(false);
                        txtTel.setEnabled(false);
                    } else
                        Toast.makeText(getContext(), "Error: No existe ese ID" +
                                "", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error: No has puesto un ID" +
                            "", Toast.LENGTH_SHORT).show();
                }
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

