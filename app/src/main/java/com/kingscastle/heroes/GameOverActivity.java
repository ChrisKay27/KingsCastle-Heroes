package com.kingscastle.heroes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import com.kingscastle.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameOverActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.kingscastle.heroes.R.layout.activity_game_over);

//        if( getIntent() != null && getIntent().getExtras() != null ) {
        int roundNum = getIntent().getExtras().getInt("RoundNum");
        ((TextView) findViewById(com.kingscastle.heroes.R.id.you_lost)).setText("You lost, but you made it to level " + roundNum);

        findViewById(com.kingscastle.heroes.R.id.back_to_main_menu_button).setOnClickListener(this);
    }



    @Override
    public void onClick( View v) {
        switch( v.getId() ){
            case com.kingscastle.heroes.R.id.back_to_main_menu_button:{
                Intent i = new Intent(this, StartScreenActivity.class);
                finish();
                startActivity(i);
                break;
            }
        }

    }
}
