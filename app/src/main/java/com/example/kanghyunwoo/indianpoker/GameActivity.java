package com.example.kanghyunwoo.indianpoker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    // current game
    Game currentGame = new Game();
    // if true, you can touch screen
    boolean touchPossible = false;
    // 1: win, -1: loose
    int gameResult = 0;

    int countTouch = 0;

    // text of my bet, your bet, my chips, your chips in layout
    TextView myBetText;
    TextView yourBetText;
    TextView myChipsText;
    TextView yourChipsText;
    // image of myCard or back side of card
    ImageView myCardImageView;

    // TODO : audio sound for game
    // systemSoundID_betting
    // systemSoundID_cardChange
    // systemSoundID_finishingBetting

    // TODO : recognizing tap(betting) and swipe down(finishing betting)

    // TODO : bluetooth networking

    // TODO : motion recognition


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // text of my bet, your bet, my chips, your chips in layout
        myBetText = (TextView)findViewById(R.id.myBet);
        yourBetText = (TextView)findViewById(R.id.yourBet);
        myChipsText = (TextView)findViewById(R.id.myChips);
        yourChipsText = (TextView)findViewById(R.id.yourChips);
        // image of myCard or back side of card
        myCardImageView = (ImageView)findViewById(R.id.myCard);


    }


    /**
     * chooseFirst
     * When chooseFirst button is clicked, choose first player by calling
     * function pickFirstCards
     */
    public void chooseFirst() {
        pickFirstCards();
        /*
        TODO : chooseFirstButton
        chooseFirstButton.isHidden = true
        chooseFirstButton.isEnabled = false
        */
    }

    /**
     * pickFirstCards
     * Pick my first card and your first card and compare it to choose first
     */
    public void pickFirstCards() {
        // initialize cardType 1 to 10
        ArrayList<Integer> cardType = new ArrayList<Integer>();
        for(int i = 1; i <= 10; i++) {
            cardType.add(i);
        }
        Random rand = new Random();
        int myFirstCard = cardType.remove(rand.nextInt(cardType.size()));
        int yourFirstCard = cardType.remove(rand.nextInt(cardType.size()));

        // I'm first player
        if (myFirstCard > yourFirstCard) {
            // TODO
            //sendNum(yourFirstCard + 20)
            //currentGame.meFirst = true
            //self.updateTurn(myturn: true)
        }
        // You're first player
        else {
            // TODO
            //sendNum(yourFirstCard+30)
            //currentGame.meFirst = false
            //self.updateTurn(myturn: false)
        }
        // TODO
        //startMotion()
        //updateCardImage(myFirstCard)
        //pickCards()
    }

    /**
     * pickCards
     * Pick my card and your card for this set and send to other player
     */
    public void pickCards() {
        // pick new cards(mine and yours)
        int myNewCard = currentGame.pickCard();
        int yourNewCard = currentGame.pickCard();
        currentGame.nextSet = false;
        currentGame.myCard = myNewCard;
        currentGame.yourCard = yourNewCard;

        // TODO :send my card to you, it's your card to you
        //sendNum(myNewCard)
        // TODO : send your card to you, it's my card to you
        //sendNum(yourCard + 10)
    }

    /**
     * touchBet
     * When you touch the screen for bet
     */
    public void touchBet() {
        if (currentGame.isMyTurn == true &&
                (currentGame.myBet - currentGame.yourBet < currentGame.yourChips) &&
                currentGame.myChips > 0 &&
                currentGame.newSet == false &&
                currentGame.myBet != 0 &&
                touchPossible) {
            // first betting makes myBet and yourBet same
            if (currentGame.myBet < currentGame.yourBet) {
                int diff = currentGame.yourBet - currentGame.myBet;
                countTouch += diff;
                currentGame.myBet += diff;
                currentGame.yourBet -= diff;
            }
            // normal case, myBet++
            else {
                countTouch += 1;
                currentGame.myBet += 1;
                currentGame.yourBet -= 1;
            }
            // TODO : sound for touch
            //AudioServicesPlaySystemSound (systemSoundID_betting)

            myBetText.setText(String.valueOf(currentGame.myBet));
            myChipsText.setText(String.valueOf(currentGame.myChips));

            // TODO : send number to other player
            //sendNum(101)
        }
    }

    /**
     * updateCardImage
     * Update card image with card nubmer
     */
    public void updateCardImage(int num) {
        String myCardName = "card" + num;
        int resID = getResources().getIdentifier(myCardName ,
                "drawable", this.getPackageName());
        myCardImageView.setImageResource(resID);
    }

    /**
     * updateBetAndChips
     * Update bet and chips in scoreboard
     */
    public void updateBetAndChips() {
        myBetText.setText(String.valueOf(currentGame.myBet));
        yourBetText.setText(String.valueOf(currentGame.yourBet));
        myChipsText.setText(String.valueOf(currentGame.myChips));
        yourChipsText.setText(String.valueOf(currentGame.yourChips));
    }

    /**
     * updateTurn
     * When turn change, we need to change in
     * boundary line in each player, clear or set
     */
    public void updateTurn(boolean myTurn) {
        // my turn
        if (myTurn) {
            currentGame.isMyTurn = true;
            // TODO : change in scoreborad
            //myView.layer.borderColor=UIColor.black.cgColor
            //yourView.layer.borderColor=UIColor.clear.cgColor
        }
        // your turn
        else {
            currentGame.isMyTurn = false;
            // TODO : change in scoreborad
            //yourView.layer.borderColor=UIColor.black.cgColor
            //myView.layer.borderColor=UIColor.clear.cgColor
        }
    }

    /**
     * initialBet
     * Initial betting form you and me, just bet 1 chip
     */
    public void initialBet() {
        currentGame.myBet += 1;
        currentGame.yourBet += 1;
        currentGame.myChips -= 1;
        currentGame.yourChips -= 1;
    }

    /**
     * finishBetting
     * Finishing betting by swipe down
     */
    public void finishBetting() {
        if (currentGame.isMyTurn) {
            // TODO : systemSoundID_finisiBetting
            //AudioServicesPlaySystemSound (self.systemSoundID_finishBetting)
            int result = currentGame.myTurn();
            // I win, you loose
            if(result > 0) {
                gameResult = 1;
            }
            // I loose, you win
            else if(result < 0) {
                gameResult = -1;
            }

            if (currentGame.newSet == true) {
                // TODO
                //seeCard.isHiddent = false
            }
            // TODO : send 100 to other player
            //sendNum(100)

            updateTurn(false);
            countTouch = 0;

            // winner pick my new card and your new card and send it
            if (currentGame.nextSet == true) {
                updateTurn(true);
                pickCards();
            }
        }
    }

    /**
     * initializeCurrentGame
     * Initialize current game
     */
    public void initializeCurrentGame() {
        currentGame = new Game();
        /*
        Is it same with "currentGame = new Game();"
        currentGame.initializeCard();
        currentGame.myBet = 0;
        currentGame.yourBet = 0;
        currentGame.myChips = 30;
        currentGame.yourChips = 30;
        currentGame.isMyTurn = false;
        currentGame.newSet = false;
        currentGame.nextSet = false;
        */
        updateCardImage(0);
    }

    /**
     * startMotion
     * When you locate your phone on forehead, it shows your card on screen
     */
    public void startMotion() {
        // TODO : motion recognition
        //
    }

    /**
     * sendNum
     * TODO : function sends nubmer to other player
     */

    /**
     * didReceiveCardInfo
     * TODO : deal with received data
     */

    /**
     * moveToResult
     * TODO : Move to result page
     * we need to choose how to show result page
     */






}
