package cpnv.com.accueil_cpw;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    /** DECLARE LES VARIABLES **/

    //Base de donnee
    DatabaseManager cosWorldDB;

    //Titre
    TextView mPseudoText;
    TextView mGenreText;
    TextView mPaysText;
    TextView mDescriptionText;

    //Image
    ImageView mImage1;
    ImageView mImage2;
    ImageView mImage3;
    ImageView mImage4;
    ImageView mImage5;
    ImageView mImage6;

    //Input
    EditText mPseudoInput;
    EditText mDescriptionInput;

    //Radio
    RadioGroup mGenreGroup;
    RadioButton mFemmeRadio;
    RadioButton mHommeRadio;

    //Spinner (Select)
    Spinner mPaysSpinner;

    //BoutonValider
    Button mSubBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //DATABASE
        cosWorldDB = new DatabaseManager(this);


        //Titre
        mPseudoText = (TextView) findViewById(R.id.tvPseudo);
        mGenreText = (TextView) findViewById(R.id.tvGenre);
        mPaysText = (TextView) findViewById(R.id.tvPays);
        mDescriptionText = (TextView) findViewById(R.id.tvDescription);

        //Image
        mImage1 = (ImageView) findViewById(R.id.ivImage1);
        mImage2 = (ImageView) findViewById(R.id.ivImage2);
        mImage3 = (ImageView) findViewById(R.id.ivImage3);
        mImage4 = (ImageView) findViewById(R.id.ivImage4);
        mImage5 = (ImageView) findViewById(R.id.ivImage5);
        mImage6 = (ImageView) findViewById(R.id.ivImage6);

        //Champs text
        mPseudoInput = (EditText) findViewById(R.id.etxtPseudo);
        mDescriptionInput = (EditText) findViewById(R.id.etxtDescription);

        //Radio button
        mGenreGroup = (RadioGroup) findViewById(R.id.RadioFH);
        mFemmeRadio = (RadioButton) findViewById(R.id.radioFemme);
        mHommeRadio = (RadioButton) findViewById(R.id.radioHomme);

        //Bouton valider
        mSubBtn = (Button) findViewById(R.id.btnValider);


        /** SPINNER **/
        //Recuperation du spinner declarer
        mPaysSpinner = (Spinner) findViewById(R.id.SpinnerPays);

        //Création d'une liste
        List PaysList = new ArrayList();
        PaysList.add("Suisse");
        PaysList.add("France");
        PaysList.add("Belgique");
        PaysList.add("Etc...");

        //Le Spinner a besoin d'un adapter, on lui passe le contexte (this) et un fichier de presentation, ainsi que la liste
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                PaysList
        );

        //On definit la présentation du spinner quand il est deroule
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Adapter du spinner
        mPaysSpinner.setAdapter(adapter);
        /** SPINNER END **/


        //Appelle la fonction pour afficher les data de la database
        Cursor data = cosWorldDB.showData();

        //Controle que la database soit vide ou non
        if (data.getCount() == 0){
            AddData(); //Appelle la fonction d'ajout des datas
        }else{ //Si la database n'est pas vide, affiche les datas
            while (data.moveToNext()) { //Cherche dans la database
                mPseudoInput.setText(data.getString(1)); //Le pseudo
                mDescriptionInput.setText(data.getString(2)); //La description

                //Appelle la fonction d'update en cas de modifications
                UpdateData();
            }
        }


    }


    //Fonction d'ajout des datas
    public void AddData () {
        mSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Au clique du bouton valider

//                Toast.makeText(getApplicationContext(), "AJOUT DE DONNEES ICI", Toast.LENGTH_SHORT).show();

                //Appelle la fonction pour afficher les datas
                Cursor data = cosWorldDB.showData();
                String id = "1"; //définit l'ID à 1

                // Controle que la database soit vide
                if (data.getCount() == 0) {
                    //Insert les datas
                    boolean insertData = cosWorldDB.addData(id, mPseudoInput.getText().toString(), mDescriptionInput.getText().toString());

                    //Si return true (voir la base)
                    if (insertData== true) {
                        Toast.makeText(EditActivity.this, "Data ajoutee avec succes", Toast.LENGTH_SHORT).show(); //Affiche un message de reussite
                    }else{
                        Toast.makeText(EditActivity.this, "Data fausses", Toast.LENGTH_SHORT).show(); //Affiche un message d'erreur
                    }
                }

                //Retourne a la page d'accueil
                Intent mainReturnActivity = new Intent(EditActivity.this, MainActivity.class);
                startActivity(mainReturnActivity);
            }
        });
    }

    //Fonction de MAJ
    public void UpdateData() {
        mSubBtn.setOnClickListener(new View.OnClickListener() { //Au clique du bouton valider
            @Override
            public void onClick(View v) {
                //Update les donnee
                boolean update = cosWorldDB.updateData(mPseudoInput.getText().toString(), mDescriptionInput.getText().toString());

                //Si retourne true
                if (update == true){
                    //Message de reussite
                    Toast.makeText(EditActivity.this, "Update Data OK!", Toast.LENGTH_SHORT).show();
                }else{
                    //Message d'echec
                    Toast.makeText(EditActivity.this, "Something went wrong with the update", Toast.LENGTH_SHORT).show();
                }

                //Retourne a la page d'accueil
                Intent mainReturnActivity2 = new Intent(EditActivity.this, MainActivity.class);
                startActivity(mainReturnActivity2);
            }
        });
    }

}
