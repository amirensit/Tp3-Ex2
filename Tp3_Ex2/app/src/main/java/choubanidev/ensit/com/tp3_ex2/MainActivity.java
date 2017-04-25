package choubanidev.ensit.com.tp3_ex2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView myrcv;
    EtudiantAdapter myAdapter;
    SQLHelper db;
    EditText nom;

    final int ADD_ITEM_ACTIVITY = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myrcv = (RecyclerView) findViewById(R.id.rcv);

        db = new SQLHelper(this);
        nom=(EditText) findViewById(R.id.nom);
        List<Etudiant> data;
        int countOSs = db.getEtudiantsCount();
        if(countOSs == 0){
            Etudiant etudiant;
            etudiant=new Etudiant(1,"Etudiant1","Etudiant1@gmail.com","Genie Logiciel",0); db.addListEtudiant(etudiant);
        }
        myAdapter = new EtudiantAdapter(MainActivity.this,db.getAllEtudiant());
        myrcv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        myrcv.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
            if (id == R.id.add) {
                Toast.makeText(getApplicationContext(), "Ajouter un etudiant", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,AddStudent.class);
                startActivityForResult(intent,ADD_ITEM_ACTIVITY);
            }
            return super.onOptionsItemSelected(item);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != ADD_ITEM_ACTIVITY) return;
        if (resultCode != RESULT_OK) return;
        Etudiant etudiant = data.getParcelableExtra("newetudiant");
        db.addListEtudiant(etudiant);
        myAdapter.updateCursor(db.getAllEtudiant());
    }





}
