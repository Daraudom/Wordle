package com.example.wordle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.wordle.FourLetterWordList.FourLetterWordList.getRandomFourLetterWord

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Retrieving the ids for EditText and the Guess Button
        val guessButton = findViewById<Button>(R.id.guessButton)
        val input = findViewById<EditText>(R.id.InputGuess)
        val inputText = input.text
        var wordToGuess = getRandomFourLetterWord()
        val resetButton = findViewById<Button>(R.id.resetButton)
        val answer = findViewById<TextView>(R.id.Answer)
        answer.text = wordToGuess.lowercase()

        // Guess1
        val guess1 = findViewById<TextView>(R.id.guess1)
        val guess1Check = findViewById<TextView>(R.id.guess1Check)
        val answer1 = findViewById<TextView>(R.id.Answer1)
        val answer1Check = findViewById<TextView>(R.id.answer1Check)

        // Guess2
        val guess2 = findViewById<TextView>(R.id.guess2)
        val guess2Check = findViewById<TextView>(R.id.guess2Check)
        val answer2 = findViewById<TextView>(R.id.answer2)
        val answer2Check = findViewById<TextView>(R.id.answer2Check)

        // Guess3
        val guess3 = findViewById<TextView>(R.id.guess3)
        val guess3Check = findViewById<TextView>(R.id.guess3Check)
        val answer3 = findViewById<TextView>(R.id.answer3)
        val answer3Check = findViewById<TextView>(R.id.answer3Check)

        guessButton.setOnClickListener {
            hideKeyboard()
            if (counter == 3) Toast.makeText(it.context, "Hit Reset To Try a New Word", Toast.LENGTH_SHORT).show()
            else if (inputText.length != 4) /* || checkAlpha(inputText.toString(), alphabets) || checkAlpha(inputText.toString(), bigAlpha)) */ {
                Toast.makeText(it.context, "Please Enter a Valid 4 Letter Word!", Toast.LENGTH_SHORT).show()
                input.text.clear()
            }

            else if (inputText.length == 4 && counter == 0) {
                answer1.text = inputText
                answer1Check.text = checkGuess(inputText.toString().uppercase(), wordToGuess)
                guess1.visibility = View.VISIBLE
                answer1.visibility = View.VISIBLE
                guess1Check.visibility = View.VISIBLE
                answer1Check.visibility = View.VISIBLE
                if (answer1Check.text.equals("OOOO")) {
                    resetButton.visibility = View.VISIBLE
                    Toast.makeText(it.context, "Congratulation! You have guessed the right word!", Toast.LENGTH_SHORT).show()
                    counter = 3
                } else counter++
                input.text.clear()
            }
            else if (counter == 1) {
                answer2.text = inputText
                answer2Check.text = checkGuess(inputText.toString().uppercase(), wordToGuess)
                guess2.visibility = View.VISIBLE
                answer2.visibility = View.VISIBLE
                guess2Check.visibility = View.VISIBLE
                answer2Check.visibility = View.VISIBLE
                if (answer2Check.text.equals("OOOO")) {
                    resetButton.visibility = View.VISIBLE
                    Toast.makeText(it.context, "Congratulation! You have guessed the right word!", Toast.LENGTH_SHORT).show()
                    counter = 3
                } else counter++
                input.text.clear()
            }
            else if (counter == 2) {
                counter++
                answer3.text = inputText
                answer3Check.text = checkGuess(inputText.toString().uppercase(), wordToGuess)
                guess3.visibility = View.VISIBLE
                answer3.visibility = View.VISIBLE
                guess3Check.visibility = View.VISIBLE
                answer3Check.visibility = View.VISIBLE
                resetButton.visibility = View.VISIBLE
                answer.visibility = View.VISIBLE
                if (answer3Check.text.equals("OOOO")) {
                    Toast.makeText(it.context, "Congratulation! You have guessed the right word!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(it.context, "Please hit the retry button to guess another word.", Toast.LENGTH_LONG).show()
                }
                input.text.clear()
            }
             resetButton.setOnClickListener {
                 hideKeyboard()
                 resetButton.visibility = View.INVISIBLE
                 counter = 0
                 wordToGuess = getRandomFourLetterWord()
                 answer.text = wordToGuess.lowercase()
//                 answer.visibility = View.INVISIBLE
                 input.text.clear()
                 guess1.visibility = View.INVISIBLE
                 guess2.visibility = View.INVISIBLE
                 guess3.visibility = View.INVISIBLE
                 guess1Check.visibility = View.INVISIBLE
                 guess2Check.visibility = View.INVISIBLE
                 guess3Check.visibility = View.INVISIBLE
                 answer1.visibility = View.INVISIBLE
                 answer1Check.visibility = View.INVISIBLE
                 answer2.visibility = View.INVISIBLE
                 answer2Check.visibility = View.INVISIBLE
                 answer3.visibility = View.INVISIBLE
                 answer3Check.visibility = View.INVISIBLE
             }
        }
    }
    var counter = 0
//    val alphabets = arrayOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
//    fun capping(smallAlpha: Array<String>) : Array<String> {
//        val capAlphabets = emptyArray<String>()
//        var index = 0
//        for (i in smallAlpha) {
//            capAlphabets[index] = i.uppercase()
//            index++
//        }
//        return capAlphabets
//    }
//    val bigAlpha = capping(alphabets)
//    fun checkAlpha(input: String, alphabets: Array<String>) : Boolean {
//        var index = 0
//        for (i in input) {
//            for (x in alphabets) {
//                if (!(i.equals(x))) return false
//            }
//        }
//        return true
//    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String, wordToGuess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}