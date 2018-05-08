package cpnv.com.accueil_cpw;

/** SOURCE : https://www.youtube.com/watch?v=GqcFEvBCnIk
 *
 */

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    /**Déclare les variables**/

    //Base de donnee
    DatabaseManager cosWorldDB;

    /** GALLERY **/
    ViewPager viewPager;


    /** CHAMPS **/
    TextView Pseudo;
    TextView Gender;
    TextView Coma;
    TextView Geo;
    TextView Description;
    TextView Country;
    Button Edit, Update;

    /** BOUTONS **/
    ImageView Home;
    TextView Accueil;
    ImageView Group;

    /**TOAST**/
    String stHome = "HOME HERE";
    String stGroup = "GROUPE HERE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Base de donnee
        cosWorldDB = new DatabaseManager(this);


        /** IMAGE SLIDE **/
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);


        //Metadata
        Pseudo = (TextView) findViewById(R.id.txtPseudo);
        Gender = (TextView) findViewById(R.id.txtGender);
        Coma = (TextView) findViewById(R.id.txtComa);
        Geo = (TextView) findViewById(R.id.txtGeo);
        Description = (TextView) findViewById(R.id.txtDescription);
        Country = (TextView) findViewById(R.id.txtCountry);

        Edit = (Button) findViewById(R.id.btnEdit);

        //Bouton menu
        Home = (ImageView) findViewById(R.id.imgHome);
        Accueil = (TextView) findViewById(R.id.txtAccueil);
        Group = (ImageView) findViewById(R.id.imgGroup);


        /** BOUTONS CLIQUE **/
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Home
                Toast.makeText(getApplicationContext(),stHome,Toast.LENGTH_SHORT).show();
            }
        });
        Group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Groupe
                Toast.makeText(getApplicationContext(),stGroup,Toast.LENGTH_SHORT).show();
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Edite les donnees
                Intent editActivity = new Intent(MainActivity.this, EditActivity.class);
                startActivity(editActivity);
            }
        });

        //Fonction d'affichage des donnes
        Cursor data = cosWorldDB.showData();

        //Met les textview vide
        Pseudo.setText("");
        Description.setText("");
        Gender.setText("");
        Country.setText("");

        //Verifie si la table est empty
        if (data.getCount() == 0) {
            Toast.makeText(getApplicationContext(),"DATABASE EMPTY",Toast.LENGTH_SHORT).show(); //Affiche un message pour dire qu'elle est vide
            Intent editActivity = new Intent(MainActivity.this, EditActivity.class);
            startActivity(editActivity);
        }else{
            //Affiche les donnees dans textview
            while (data.moveToNext()) {
                Pseudo.append(data.getString(1));
                Gender.append(data.getString(2));
                Country.append(data.getString(3));
                Description.append(data.getString(4));
            }
        }
    }

}
