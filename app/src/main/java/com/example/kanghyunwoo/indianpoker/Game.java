package com.example.kanghyunwoo.indianpoker;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by KimYoonseo on 2017. 8. 9..
 */

public class Game {
    // Total card set (not initialize now)
    ArrayList<Integer> cardSet = new ArrayList<Integer>();
    {
        cardSet = new ArrayList<Integer>() {{
            initializeCard();
        }};
    }

    // number of chips I bet in current set
    int myBet = 0;
    // number of chips you bet in current set
    int yourBet = 0;
    // my card number in current set
    int myCard = 0;
    // your cared number in current set
    int yourCard = 0;
    // number of my chips
    int myChips = 30;
    // number of your chips
    int yourChips = 30;
    // if true, you are first
    boolean meFirst = false;
    // if true, it's your turn
    boolean isMyTurn = false;
    //
    boolean nextSet = false;
    //
    boolean newSet = false;


    /**
     * initializeCard
     * Function adds 1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,10,10 to cardSet
     */
    public void initializeCard() {
        for(int i = 0; i < 20; i++){
            cardSet.add((int)(i/2)+1);
        }
    }

    /**
     * pickCard
     * Pick a card in current card set and return the number
     */
    public int pickCard() {
        // empty card set
        if(cardSet.size() == 0) {
            initializeCard();
        }
        // randomly pick a card
        Random rand = new Random();
        int pickedCard = rand.nextInt(cardSet.size());
        myCard = cardSet.remove(pickedCard);

        return myCard;
    }

    /**
     * myTurn
     * Progress this turn(set?) with current my bet and your bet
     */
    public int myTurn() {
        if (myBet == 1 || myBet < yourBet) {    // die, I loose
            yourChips += (myBet + yourBet);
            myBet = 0;
            yourBet = 0;
            if (myCard == 10) {
                if (myChips <= 10) {
                    // when your chips are less than 10
                    yourChips -= myChips;
                    myChips =  0;
                } else {
                    myChips -= 10;
                    yourChips += 10;
                }
                yourChips += 10;
                myChips -= 10;
            }
            meFirst = false;
            newSet = true;
        } else if (myBet > yourBet) {           // more bet
            newSet = false;
        } else if (myCard > yourCard) {         // card open (I win)
            myChips += (myBet + yourBet);
            myBet = 0;
            yourBet = 0;
            meFirst = true;
            nextSet = true;
            newSet = true;
        } else if (myCard == yourCard) {        // draw
            nextSet = true;
            newSet = true;
        } else if (myCard < yourCard) {         // card open (I loose)
            yourChips += (myBet + yourBet);
            myBet = 0;
            yourBet = 0;
            meFirst = false;
            newSet = true;
        }

        // Game Over
        if (yourChips == 0 && yourBet == 0 || (yourChips == 0 && myBet == yourBet)) {
            // I win, you loose
            //return true;
            return 1;
        } else if (myChips == 0 && myBet == 0 || (myChips == 0 && myBet == yourBet)) {
            // I loose, you win
            //return false;
            return 2;
        }
        //return false;
        return 0;
    }

    /**
     * yourTurn
     * Progress this turn(set?) with current my bet and your bet
     */
    public int yourTurn() {
        if (yourBet == 1 || yourBet < myBet) {    // die, you loose
            myChips += (myBet + yourBet);
            myBet = 0;
            yourBet = 0;
            if (yourCard == 10) {
                if (yourChips <= 10) {
                    // when your chips are less than 10
                    myChips -= yourChips;
                    yourChips =  0;
                } else {
                    myChips += 10;
                    yourChips -= 10;
                }
            }
            nextSet = true;
            meFirst = true;
            newSet = true;
        } else if (yourBet > myBet) {           // more bet
            newSet = false;
        } else if (myCard > yourCard) {         // card open (I win)
            myChips += (myBet + yourBet);
            myBet = 0;
            yourBet = 0;
            meFirst = true;
            nextSet = true;
            newSet = true;
        } else if (myCard == yourCard) {        // draw
            nextSet = false;
            newSet = true;
        } else if (myCard < yourCard) {         // card open (I loose)
            yourChips += (myBet + yourBet);
            myBet = 0;
            yourBet = 0;
            meFirst = false;
            newSet = true;
        }

        // Game Over
        if (yourChips == 0 && yourBet == 0 || (yourChips == 0 && myBet == yourBet)) {
            // I win, you loose
            //return true;
            return 1;
        } else if (myChips == 0 && myBet == 0 || (myChips == 0 && myBet == yourBet)) {
            // I loose, you win
            //return false;
            return -1;
        }
        //return false;
        return 0;
    }
}
