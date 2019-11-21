package com.example.claudia.chiquilt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Foro extends AppCompatActivity {
    //Declaramos
    EditText nombre;
    //EditText edad;
    //RadioButton generoM , generoF;
    Button agregar;

    RecyclerView listaPersonas;

    DatabaseReference databaseReference;

    FirebaseRecyclerAdapter<com.example.claudia.chiquilt.
            Persona,PersonaViewHolder.ViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        //Referenciamos
        nombre = findViewById(R.id.editText2);
        //  edad = findViewById(R.id.editText3);
        //generoM = findViewById(R.id.radioButton);
        //  generoF = findViewById(R.id.radioButton2);
        agregar = findViewById(R.id.button6);
        listaPersonas = findViewById(R.id.lista_personas);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listaPersonas.setLayoutManager(linearLayoutManager);

        adapter=new FirebaseRecyclerAdapter<com.example.claudia.chiquilt.Persona, PersonaViewHolder.ViewHolder>(
                com.example.claudia.chiquilt.Persona.class,
                R.layout.obj_persona,
                PersonaViewHolder.ViewHolder.class,
                databaseReference.child("persona")
        ) {
            @Override
            protected void populateViewHolder(PersonaViewHolder.ViewHolder viewHolder,
                                              com.example.claudia.chiquilt.Persona model, final int position) {
                viewHolder.nombre.setText(model.getNombre());
                //        viewHolder.edad.setText(String.valueOf(model.getEdad()));
                //      viewHolder.genero.setText(model.getGenero());

                viewHolder.eliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Usuario eliminado", Toast.LENGTH_SHORT).show();
                        adapter.getRef(position).removeValue();
                    }
                });

                /*viewHolder.editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), EditarPersona.class);
                        intent.putExtra("id",adapter.getRef(position).getKey());
                        intent.putExtra("nombre",adapter.getItem(position).getNombre());
                        Toast.makeText(getApplicationContext(), "Usuario "+ adapter.getItem(position).getNombre()+" seleccionado", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    }
                });
*/
            }
        };

        listaPersonas.setAdapter(adapter);


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreP = nombre.getText().toString();
//                int edadP = Integer.valueOf(edad.getText().toString());
                String id = databaseReference.push().getKey();
                /*String generoP;
                if(generoM.isChecked()){
                    generoP="masculino";
                }else{
                    generoP="femenino";
                }*/
                com.example.claudia.chiquilt.Persona persona=new com.example.claudia.chiquilt.Persona(nombreP);

                databaseReference.child("persona").child(id).setValue(persona);

                Toast.makeText(getApplicationContext(), "Usuario registrado", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

