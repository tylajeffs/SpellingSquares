package edu.byuh.cis.cs203.numberedsquares;

import android.content.res.Resources;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by tylajeffs
 */

public class SpellingGame implements GameStyle
{
    //fields
    private ArrayList<String> allLabels;
    private GameStatus gameStatus;
    private String lLabels = "A TO IVY TREE PLANT FLOWER PLANTER GARDENER VEGETABLE";
    private int level;
    private ArrayList<String> levelLabels;
    private String expectedLabel;
    private int numCorrectSquaresTouched;



    /**
     * constructor, initializes a few things
     */
    public SpellingGame()
    {
        //set level
        level = 1;

        //set number of correct squares touched to 0
        numCorrectSquaresTouched = 0;

        //initialize labels
        allLabels = new ArrayList<String>();
        levelLabels = new ArrayList<String>();

        //split the string of labels
        String[] labelsArray = lLabels.split(" ");

        //add labels to the array list
        for(String l : labelsArray)
        {
            allLabels.add(l);
        }

        //get the labels for this level (letters in the word)
        getThisLevelsLabels();

        //set the expected label to be the first letter in the word
        resetExpectedLabel();


    }


    /**
     * Method to get the string for the next level toast message
     *
     * @param res
     * @return String for the next level toast
     */
    @Override
    public String getNextLevelLabel(Resources res)
    {
        return "good job! now spell: " + allLabels.get(level - 1);
    }


    /**
     * Method to get the label for the try again toast
     *
     * @param res
     * @return String for the toast
     */
    @Override
    public String getTryAgainLabel(Resources res)
    {
        return "Oops! You need to spell " + allLabels.get(level - 1);
    }




    /**
     * method to get the labels for the squares for that level
     * @return list of string labels
     */
    @Override
    public List<String> getSquareLabels()
    {
        //split the string of level labels into a new list of labels
        getThisLevelsLabels();

        return levelLabels;
    }


    /**
     * method to return the game status - continue, level complete, try again
     * @param ns Numbered Square that was touched
     * @return
     */
    @Override
    public GameStatus getGameStatus(NumberedSquare ns)
    {
        //check if square label is equal to expected label
        if(ns.label.equals(expectedLabel))
        {
            //if the labels match, check to see if it is the last square in the level
            if(numCorrectSquaresTouched == (level - 1))
            {
                //check to see if it's the last level
                //if it is, restart at the 1st level
                if(level == 9)
                {
                    //this is the last level, start the first level again
                    resetGame();
                    return gameStatus.LEVEL_COMPLETE;
                }
                else
                {
                    increaseLevel();
                    //return level complete if it is the last square
                    return gameStatus.LEVEL_COMPLETE;
                }

            }
            else
            {
                //it's just the right square, return continue and increase the expected label
                increaseNumCorrectSquaresTouched();
                increaseExpectedLabel();

                return gameStatus.CONTINUE;
            }

        }
        else   //wrong square
        {

            //(if it's not frozen) wrong square, try again
            if(!ns.frozen)
            {
                //reset the expected label id
                resetExpectedLabel();

                //reset the number of correct squares that were touched
                resetNumCorrectSquaresTouched();

                return gameStatus.TRY_AGAIN;
            }
            else //continue if the square is already frozen
            {
                return gameStatus.CONTINUE;
            }

        }

    }



    /**
     * helper method that increases the level by 1
     */
    private void increaseLevel()
    {
        //increase the level by one
        level++;

        //get the labels for this level
        getThisLevelsLabels();

        //reset the number of correct squares that were touched
        resetNumCorrectSquaresTouched();

        //reset the expected label with the next level's word
        resetExpectedLabel();
    }


    /**
     * helper method to set level back to 1
     */
    private void resetLevel()
    {
        //set level to 0
        level = 1;
    }


    /**
     * helper method to split the string per level and find the labels for one specific level
     */
    private void getThisLevelsLabels()
    {
        //clear level labels
        levelLabels.clear();

        //split the string into single characters at the index specified by level
        //in the labels per level list
        String[] theseLabels = allLabels.get(level-1).split("");

        //add each of the labels to the level labels arraylist
        for(String character : theseLabels)
        {
            levelLabels.add(character);
        }

    }

    /**
     * helper method to reset the the expected label to the first letter in the level's word
     */
    private void resetExpectedLabel()
    {
        //set the expected label to be the first letter in the word for this level
        expectedLabel = levelLabels.get(0);

    }

    /**
     * helper method to increase the expected label by 1 letter in the level's word
     */
    private void increaseExpectedLabel()
    {
        //increase the expected label
        expectedLabel = levelLabels.get(numCorrectSquaresTouched);

    }


    /**
     * helper method to increase the count of correct squares touched in the level
     */
    private void increaseNumCorrectSquaresTouched()
    {
        //increase by 1
        numCorrectSquaresTouched += 1;
    }


    /**
     * helper method to reset the amount of correct squares touched in the level
     */
    private void resetNumCorrectSquaresTouched()
    {
        //set to 0
        numCorrectSquaresTouched = 0;
    }


    /**
     * helper method that restarts the game, resets all variables
     */
    private void resetGame()
    {
        //reset level to 1
        resetLevel();

        //set number of squares touched to 0
        resetNumCorrectSquaresTouched();

        //get the labels for this level
        getThisLevelsLabels();

        //reset the expected label with the 1st level's word
        resetExpectedLabel();
    }

}
