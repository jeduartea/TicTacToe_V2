package co.edu.unal.tictactoe_v2

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.ViewDebug
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import co.edu.unal.tictactoe_v2.utils.TicTacToeConsole
import co.edu.unal.tictactoe_v2.viewmodels.MainViewModel
import com.example.flatdialoglibrary.dialog.FlatDialog
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    private val one by lazy<ConstraintLayout> { findViewById(R.id.one) }
    private val two by lazy<ConstraintLayout> { findViewById(R.id.two) }
    private val three by lazy<ConstraintLayout> { findViewById(R.id.three) }
    private val four by lazy<ConstraintLayout> { findViewById(R.id.four) }
    private val five by lazy<ConstraintLayout> { findViewById(R.id.five) }
    private val six by lazy<ConstraintLayout> { findViewById(R.id.six) }
    private val seven by lazy<ConstraintLayout> { findViewById(R.id.seven) }
    private val eight by lazy<ConstraintLayout> { findViewById(R.id.eight) }
    private val nine by lazy<ConstraintLayout> { findViewById(R.id.nine) }

    private val oneImage by lazy<ImageView> { findViewById(R.id.image_one) }
    private val twoImage by lazy<ImageView> { findViewById(R.id.image_two) }
    private val threeImage by lazy<ImageView> { findViewById(R.id.image_three) }
    private val fourImage by lazy<ImageView> { findViewById(R.id.image_four) }
    private val fiveImage by lazy<ImageView> { findViewById(R.id.image_five) }
    private val sixImage by lazy<ImageView> { findViewById(R.id.image_six) }
    private val sevenImage by lazy<ImageView> { findViewById(R.id.image_seven) }
    private val eightImage by lazy<ImageView> { findViewById(R.id.image_eight) }
    private val nineImage by lazy<ImageView> { findViewById(R.id.image_nine) }

    private val container by lazy<ConstraintLayout> { findViewById(R.id.container) }

    private val reset by lazy<Button> { findViewById(R.id.reset) }
    private val exit by lazy<Button> { findViewById(R.id.exit) }
    private val difficulty by lazy<Button> { findViewById(R.id.difficulty) }


    lateinit var animationScale: Animation
    lateinit var viewModel: MainViewModel

    val ticTacToeConsole = TicTacToeConsole()

    var turn = false

    var result: String? = null

    var difficultyState: String? = null

    lateinit var mpClick: MediaPlayer
    lateinit var mpInitio: MediaPlayer

    private var database = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setListeners(animationScale)
        writeBoardFirebase()
        readBoardFirebase()
        waitChanges()
    }

    private fun waitChanges(){
        val docRef = database.collection("boards").document("board")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("DEBUG", "Listen failed.", e)
                return@addSnapshotListener
            }

            val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
                "Local"
            else
                "Server"

            if (snapshot != null && snapshot.exists()) {
                Log.d("DEBUG", "$source data: ${snapshot.data}")
            } else {
                Log.d("DEBUG", "$source data: null")
            }
        }
    }

    private fun writeBoardFirebase(){
        database.collection("boards").document("board").set(
            hashMapOf("0" to ticTacToeConsole.board[0].toString(),
                "1" to ticTacToeConsole.board[1].toString(),
                "2" to ticTacToeConsole.board[2].toString(),
                "3" to ticTacToeConsole.board[3].toString(),
                "4" to ticTacToeConsole.board[4].toString(),
                "5" to ticTacToeConsole.board[5].toString(),
                "6" to ticTacToeConsole.board[6].toString(),
                "7" to ticTacToeConsole.board[7].toString(),
                "8" to ticTacToeConsole.board[8].toString()),
        )
    }

    private fun readBoardFirebase(){
        database.collection("boards").document("board").get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("DEBUG", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("DEBUG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("DEBUG", "get failed with ", exception)
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharArray("ticTacToeConsole", ticTacToeConsole.getBoard())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val ticTacToe = savedInstanceState.getCharArray("ticTacToeConsole")
        ticTacToeConsole.setBoard(ticTacToe)
        loadState(ticTacToe)

    }

    private fun loadState(ticTacToe: CharArray?) {
        ticTacToe?.forEachIndexed { index, objectTicTacToe ->
            when (objectTicTacToe) {
                'X' -> paintV2AfterRotate(index + 1, false)
                'O' -> paintV2AfterRotate(index + 1, true)
            }
        }
    }

    private fun init() {
        animationScale = AnimationUtils.loadAnimation(this, R.anim.button_choice_small)
        viewModel = MainViewModel()
        mpInitio = MediaPlayer.create(this, R.raw.inicio)
        mpClick = MediaPlayer.create(this, R.raw.clic)
    }

    private fun setListeners(animationScale: Animation) {
        one.setOnClickListener {
            it.startAnimation(animationScale)
            mpClick.start()
            executeOperation(1)
        }
        two.setOnClickListener {
            it.startAnimation(animationScale)
            mpClick.start()
            executeOperation(2)
        }
        three.setOnClickListener {
            it.startAnimation(animationScale)
            mpClick.start()
            executeOperation(3)
        }
        four.setOnClickListener {
            it.startAnimation(animationScale)
            mpClick.start()
            executeOperation(4)
        }
        five.setOnClickListener {
            it.startAnimation(animationScale)
            mpClick.start()
            executeOperation(5)
        }
        six.setOnClickListener {
            it.startAnimation(animationScale)
            mpClick.start()
            executeOperation(6)
        }
        seven.setOnClickListener {
            it.startAnimation(animationScale)
            mpClick.start()
            executeOperation(7)
        }
        eight.setOnClickListener {
            it.startAnimation(animationScale)
            mpClick.start()
            executeOperation(8)
        }
        nine.setOnClickListener {
            it.startAnimation(animationScale)
            mpClick.start()
            executeOperation(9)
        }
        reset.setOnClickListener {
            it.startAnimation(animationScale)
            mpInitio.start()
            Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show()
            ticTacToeConsole.reset()
            clear()
        }
        exit.setOnClickListener {
            it.startAnimation(animationScale)
            mpInitio.start()
            onBackPressed()
        }
        difficulty.setOnClickListener {
            it.startAnimation(animationScale)
            mpInitio.start()
            dialogMenu()
        }
    }

    private fun dialogMenu() {
        var flatDialog = FlatDialog(this@MainActivity)
        flatDialog.setTitle("Difficulty")
            .setSubtitle("Pick your level of difficulty")
            .setFirstButtonText("Easy")
            .setSecondButtonText("Medium")
            .setThirdButtonText("Hard")
            .isCancelable(true)
            .withFirstButtonListner {
                Toast.makeText(this, "Easy", Toast.LENGTH_SHORT).show()
                ticTacToeConsole.reset()
                clear()
                flatDialog.dismiss()
            }
            .withSecondButtonListner {
                Toast.makeText(this, "Medium", Toast.LENGTH_SHORT).show()
                ticTacToeConsole.reset()
                clear()
                flatDialog.dismiss()
            }
            .withThirdButtonListner {
                Toast.makeText(this, "Hard", Toast.LENGTH_SHORT).show()
                ticTacToeConsole.reset()
                clear()
                flatDialog.dismiss()
            }
            .show()
    }

    private fun executeOperation(position: Int) {
        if (ticTacToeConsole.reportWinner() > 9) {
            checkFinish()
        } else {
            if (!checkBoard(position)) {
                paintV2(position)
                val move = ticTacToeConsole.TicTacToeConsoleV2(position)
                paintV2(move)
                checkFinish()
            } else {
                Toast.makeText(this, "Illegal Move", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkFinish() {
        when (ticTacToeConsole.reportWinner()) {
            10 -> result = "It's a tie."
            11 -> result = "you Wins!"
            12 -> result = "machine Wins!"
        }
        if (!result.isNullOrEmpty()) {
            Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkBoard(position: Int): Boolean {
        val board = ticTacToeConsole.getBoard()
        return board[position - 1] == 'X' || board[position - 1] == 'O'
    }

    private fun clear() {
        oneImage.setImageDrawable(null)
        twoImage.setImageDrawable(null)
        threeImage.setImageDrawable(null)
        fourImage.setImageDrawable(null)
        fiveImage.setImageDrawable(null)
        sixImage.setImageDrawable(null)
        sevenImage.setImageDrawable(null)
        eightImage.setImageDrawable(null)
        nineImage.setImageDrawable(null)
        result = null
    }

    private fun paintV2(position: Int) {
        val image =
            if (turn) resources.getDrawable(R.drawable.circle) else resources.getDrawable(R.drawable.cross)
        when (position) {
            1 -> oneImage.setImageDrawable(image)
            2 -> twoImage.setImageDrawable(image)
            3 -> threeImage.setImageDrawable(image)
            4 -> fourImage.setImageDrawable(image)
            5 -> fiveImage.setImageDrawable(image)
            6 -> sixImage.setImageDrawable(image)
            7 -> sevenImage.setImageDrawable(image)
            8 -> eightImage.setImageDrawable(image)
            9 -> nineImage.setImageDrawable(image)
        }
        turn = !turn
    }

    private fun paintV2AfterRotate(position: Int, turnOptional: Boolean) {
        val image =
            if (turnOptional) resources.getDrawable(R.drawable.circle) else resources.getDrawable(R.drawable.cross)
        when (position) {
            1 -> oneImage.setImageDrawable(image)
            2 -> twoImage.setImageDrawable(image)
            3 -> threeImage.setImageDrawable(image)
            4 -> fourImage.setImageDrawable(image)
            5 -> fiveImage.setImageDrawable(image)
            6 -> sixImage.setImageDrawable(image)
            7 -> sevenImage.setImageDrawable(image)
            8 -> eightImage.setImageDrawable(image)
            9 -> nineImage.setImageDrawable(image)
        }
        turn = !turn
    }
}