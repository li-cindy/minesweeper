package hu.ait.minesweeper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import hu.ait.minesweeper.model.MinesweeperModel
import hu.ait.minesweeper.view.MinesweeperView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnRestart.setOnClickListener {
            minesweeperView.resetGame()
        }

    }

    public fun MinesweeperView.resetGame(){
        MinesweeperModel.resetModel()
        resetParameters()
        invalidate()
    }

}
