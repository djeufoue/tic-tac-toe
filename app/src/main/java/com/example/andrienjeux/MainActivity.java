package com.example.andrienjeux;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean au_tour_de_jouer_de_joueur_1 = true;

    private int numbre_de_tour_du_jeux;

    private int number_de_points_de_joueur_1;
    private int number_de_points_de_joueur_2;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i=0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int id_de_la_resource = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(id_de_la_resource);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        Button button = (Button)view;
        String texte_du_button = button.getText().toString();
        boolean texte_du_button_est_vide = texte_du_button.equals("");
        boolean texte_du_button_nest_pas_vide = !texte_du_button_est_vide;
        if (texte_du_button_nest_pas_vide) {
            return; // commentaire: ne rien faire si le text du button n'est pas vide
        }

        if (au_tour_de_jouer_de_joueur_1) {
            button.setText("X");
        }
        else { // sinon c'est le tour de jouer de joueur 2
            button.setText("O");
        }

        /* les 3 lignes ci-dessous font la meme chose, mais les 2 derniers ont ete commenter */
        numbre_de_tour_du_jeux += 1; // ==> numbre_de_tour_du_jeux = numbre_de_tour_du_jeux + 1;
        //numbre_de_tour_du_jeux++;

        if (est_ce_qu_un_joueur_a_gagner()) {
            // si un jouer a gagner, alors determinons quel jouer c'est:
            if (au_tour_de_jouer_de_joueur_1) {
                jouer_1_a_gagner();
            }
            else {
                jouer_2_a_gagner();
            }
        }
        else if (numbre_de_tour_du_jeux == 9) {
            match_nul();
        }
        else {
            // Si c'etait le tour de joueur 1 alors prochain tour sera pour joueur 2
            // Si c'etait le tour de joueur 2 alors prochain tour sera pour joueur 1
            au_tour_de_jouer_de_joueur_1 = !au_tour_de_jouer_de_joueur_1;
        }
    }

    /* La fonction ci-dessous nous dis si un joueur a gagner, mais ne nous dis pas quel jouer c'est.
     */
    private boolean est_ce_qu_un_joueur_a_gagner() {
        String[][] textes_des_buttons = new String[3][3];

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                Button button = buttons[i][j];
                String texte_du_button = button.getText().toString();
                textes_des_buttons[i][j] = texte_du_button;
            }
        }

        // Le suivant compare les lignes
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {

                String texte_i0 = textes_des_buttons[i][0];
                String texte_i1 = textes_des_buttons[i][1];
                String texte_i2 = textes_des_buttons[i][2];
                boolean texte_est_vide = texte_i0.equals("");

                if(texte_i0.equals(texte_i1)
                && texte_i0.equals(texte_i2)
                && !texte_est_vide)
                    return true; // oui, joueur a gagner.
            }
        }

        // Le suivant compare les colonnes
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {

                String texte_0i = textes_des_buttons[0][i];
                String texte_1i = textes_des_buttons[1][i];
                String texte_2i = textes_des_buttons[2][i];
                boolean texte_est_vide = texte_0i.equals("");

                if(texte_0i.equals(texte_1i)
                && texte_0i.equals(texte_2i)
                && !texte_est_vide)
                    return true; // oui, joueur a gagner.
            }
        }

        // Le suivant compare la diagonale primaire
        String texte_00 = textes_des_buttons[0][0];
        String texte_11 = textes_des_buttons[1][1];
        String texte_22 = textes_des_buttons[2][2];
        boolean texte_est_vide = texte_00.equals("");
        if(texte_00.equals(texte_11)
        && texte_00.equals(texte_22)
        && !texte_est_vide)
            return true; // oui, joueur a gagner.

        // Le suivant compare la diagonale secondaire
        String texte_02 = textes_des_buttons[0][2];
        texte_11 = textes_des_buttons[1][1]; // pas possible de re-declarer une variable
        String texte_20 = textes_des_buttons[2][0];
        texte_est_vide = texte_02.equals(""); // pas possible de re-declarer une variable
        if (texte_02.equals(texte_11)
        && texte_02.equals(texte_20)
        && !texte_est_vide)
            return true; // oui, joueur a gagner.

        return false; // non, joueur n'a pas gagner.
    }

    private void jouer_1_a_gagner() {
        number_de_points_de_joueur_1 += 1; // augmente l'entier de 1
        Toast.makeText(this, "Joueur 1 a gagner!", Toast.LENGTH_SHORT).show(); // montre un message sur l'ecran pour une petite duree
        mettre_a_jour_le_texte_des_points();
        relancer_le_jeux();
    }

    private void jouer_2_a_gagner() {
        number_de_points_de_joueur_2++; // augmente l'entier de 1
        Toast.makeText(this, "Joueur 2 a gagner!", Toast.LENGTH_SHORT).show(); // montre un message sur l'ecran pour une petite duree
        mettre_a_jour_le_texte_des_points();
        relancer_le_jeux();
    }

    private void match_nul() {
        Toast.makeText(this, "Match nul!", Toast.LENGTH_SHORT).show(); // montre un message sur l'ecran pour une petite duree
        relancer_le_jeux();
    }

    private void mettre_a_jour_le_texte_des_points() {
        String texte_joueur_1 = "Player 1: " + number_de_points_de_joueur_1;
        String texte_joueur_2 = "Player 2: " + number_de_points_de_joueur_2;
        textViewPlayer1.setText(texte_joueur_1);
        textViewPlayer2.setText(texte_joueur_2);
    }

    private void relancer_le_jeux() {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                Button button = buttons[i][j];
                button.setText(""); // enlever le texte afficher sur le button
            }
        }

        numbre_de_tour_du_jeux = 0;
        au_tour_de_jouer_de_joueur_1 = true;
    }
}
