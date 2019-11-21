package com.example.claudia.chiquilt;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ModificarFragment extends Fragment {
    Spinner org;
    String a = "", orgSelected = "", sexoSelected = "";
    EditText txtID, txtNombre, txtCumple, txtTel;
    TextView txteligeSexo;
    CheckBox checkSexo;
    Button btnModificar, btnBuscar, btnFecha;
    private SQLite sqlite;
    ArrayAdapter<CharSequence> adapter;
    int es;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar, container, false);

        org = (Spinner) view.findViewById(R.id.spinnerOrgModi);
        txtID = (EditText) view.findViewById(R.id.txtIDModi);
        txtNombre = (EditText) view.findViewById(R.id.txtNombreModi);
        txtCumple= (EditText) view.findViewById(R.id.txtCumpleModi);
        txtTel = (EditText) view.findViewById(R.id.txtTelefonoModi);
        checkSexo= (CheckBox) view.findViewById(R.id.checkBoxSexoModi);
        btnModificar = (Button) view.findViewById(R.id.botonModi);
        btnBuscar = (Button) view.findViewById(R.id.btnBuscarModi);
        btnFecha = view.findViewById(R.id.btnDateModi);
        txteligeSexo = view.findViewById(R.id.textViewSexoModi);

        org.setVisibility(View.INVISIBLE);
        txtNombre.setVisibility(View.INVISIBLE);
        txtCumple.setVisibility(View.INVISIBLE);
        txtTel.setVisibility(View.INVISIBLE);
        checkSexo.setVisibility(View.INVISIBLE);
        btnModificar.setVisibility(View.INVISIBLE);
        btnFecha.setVisibility(View.INVISIBLE);
        txteligeSexo.setVisibility(View.INVISIBLE);
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
                        btnFecha.setVisibility(View.VISIBLE);
                        btnModificar.setVisibility(View.VISIBLE);
                        txteligeSexo.setVisibility(View.VISIBLE);
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
                        txtID.setEnabled(false);
                    } else
                        Toast.makeText(getContext(), "Error: No existe ese ID" +
                                "", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error: No has puesto un ID" +
                            "", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
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

                    sqlite.addUpdatePer(t, txtNombre.getText().toString(), orgSelected, txtCumple.getText().toString(), sexoSelected, txtTel.getText().toString(),"path");
                    //recuperar id del ultimo registtro y pasa como parmetro

                    Toast.makeText(getContext(), "Modificación del registro con ID: "+t, Toast.LENGTH_SHORT).show();
                    txtID.setText("");
                    txtNombre.setText("");
                    txtCumple.setText("");
                    txtTel.setText("");
                    org.setSelection(0);
                    checkSexo.setChecked(false);
                    txtID.setEnabled(true);
                    org.setVisibility(View.INVISIBLE);
                    txtNombre.setVisibility(View.INVISIBLE);
                    txtCumple.setVisibility(View.INVISIBLE);
                    txtTel.setVisibility(View.INVISIBLE);
                    checkSexo.setVisibility(View.INVISIBLE);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnFecha.setVisibility(View.INVISIBLE);
                    txteligeSexo.setVisibility(View.INVISIBLE);

                } else {
                    Toast.makeText(getContext(), "Error: no puede haber campos vacios", Toast.LENGTH_SHORT).show();
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