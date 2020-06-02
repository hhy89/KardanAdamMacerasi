package com.hhy.game.snowman.game

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.os.SystemClock.sleep
import android.view.MotionEvent
import android.view.SurfaceHolder
import com.hhy.game.snowman.GameOverActivity
import com.hhy.game.snowman.R
import kotlin.math.roundToInt

class Game(private val context: Context, private val values: Values, private val holder: SurfaceHolder,
                    private val resources: Resources) {
    private val options: BitmapFactory.Options = BitmapFactory.Options()
    private lateinit var background: ScrollableBackground
    private lateinit var pauseButton: Bitmap
    private val obstacles = ArrayList<Obstacle>()
    private lateinit var player: Player
    private lateinit var scoreText: ScoreText
    private var gameRunning: Boolean = false
    private var currentScore: Int = 0
    private var recordScore: Int = 0
    private var touchYup: Float = 0.0f
    private var touchYdown: Float = 0.0f

    // move character for accelerometer sensor
    fun onTouchEvent(event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_DOWN) {
            touchYdown = event.y
            // pause button
            if (values.pauseButtonRect.contains(event.x.roundToInt(), event.y.roundToInt())) {
                if (gameRunning) {
                    gameRunning = false
                    player.stopAnimation()
                } else {
                    gameRunning = true
                    player.startAnimation()
                }
            }
        }
        // player movement
        if (event.action == MotionEvent.ACTION_UP) {
            touchYup = event.y

            // up up up
            if (touchYdown - touchYup > values.swipeValue && touchYdown > values.swipeValue2) {
                player.lineUp()
            }
            // down down down
            if (touchYdown - touchYup < -values.swipeValue && touchYup > values.swipeValue2) {
                player.lineDown()
            }
            touchYdown = 0.0f
            touchYup = 0.0f
        }
    }

    // update things
    fun update(elapsed: Int) {
        if (gameRunning) {
            currentScore += elapsed / 15
            var gameSpeed: Int = currentScore / 200
            if (gameSpeed < 6) gameSpeed = 6

            // background update
            background.update(gameSpeed)

            // player update
            player.update()

            // obstacles update
            for (o in obstacles) {
                o.update(gameSpeed)

                // if plater hit any obstacle
                // finish the game. sorry bro
                if (player.line == o.line && (player.boundingRectangle.intersect(o.boundingRectangle))) {
                    finishGame()
                }
            }
        }
    }

    // draw them
    fun draw() {
        val canvas: Canvas? = holder.lockCanvas()
        if (canvas != null) {
            canvas.drawColor(Color.WHITE)
            this.drawGame(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun drawGame(canvas: Canvas) {
        // background
        background.draw(canvas)

        // obstacles
        for (o in obstacles)
            o.draw(canvas)

        // player
        player.draw(canvas)

        // pause button
        canvas.drawBitmap(pauseButton, null, values.pauseButtonRect, null)

        // scoretexts
        scoreText.drawRecord(canvas, recordScore.toString())
        scoreText.drawScore(canvas, currentScore.toString())
        if (!gameRunning) scoreText.drawPause(canvas)
    }

    private fun restartGame() {
        // clear obstacle list
        obstacles.clear()
        gameRunning = true
        loadRecord()

        // player = snowman
        val snowmanBitmap: Bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.snowman, options),
                values.snowmanWidth, values.snowmanHeight, false)
        player = Player(snowmanBitmap, values, 1)
        player.createAnimator(snowmanBitmap, 4, 3, 30, 30.0)
        player.startAnimation()

        // scrollable background. i love it!
        background = ScrollableBackground( Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.background, options),
                values.width, values.height, false), values.backgroundRect)

        // pause button
        pauseButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.pause, options),
                values.pausedButtonWidth, values.pausedButtonHeight, false)

        // score text. can u beat me?
        scoreText = ScoreText(values, resources)

        generateObstacle()
    }

    // generate obstacle
    fun generateObstacle() {
        val obstacle = Obstacle(values, resources)
        obstacles.add(obstacle)

        // clear unnecessary obstacles!
        /*
        // this does not work! shame on you!
        for (i in obstacles.indices) {
            if (obstacles[i].screenOff)
                obstacles.removeAt(i)
        }
        */
        // only this works perfectly
        val iterator = obstacles.iterator()
        while (iterator.hasNext()) {
            val obs = iterator.next()
            if (obs.screenOff) {
                iterator.remove()
            }
        }
    }

    // which is last?
    fun getLastObs(): Obstacle {
        return obstacles[obstacles.size - 1]
    }

    // distance is hard!
    fun getObstacleDistance(): Int {
        return values.obstacleDistance
    }

    // finish the game
    private fun finishGame() {
        // keep them for gameover screen
        val score: Int = currentScore
        val record: Int = recordScore
        // save record
        saveRecord()

        // chosen player
        val snowmanBitmapDie: Bitmap = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.snowman_die, options),
            values.snowmanWidth, values.snowmanHeight, false
        )
        player = Player(snowmanBitmapDie, values, player.line)

        player.createAnimator(snowmanBitmapDie, 4, 3, 30, 30.0)
        player.startAnimation()

        // wait the animation
        sleep(240)

        gameRunning = false

        // go to gameover screen
        val intent = Intent(context, GameOverActivity::class.java)
        intent.putExtra("score", score)
        intent.putExtra("record", record)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    // save record
    private fun saveRecord() {
        if (currentScore > recordScore) {
            val sharedPreferences = context.getSharedPreferences("snowman", MODE_PRIVATE)
            sharedPreferences.edit().putInt("record_key", currentScore).apply()
        }
    }

    // load record
    private fun loadRecord() {
        val sharedPreferences = context.getSharedPreferences("snowman", MODE_PRIVATE)
        recordScore = sharedPreferences.getInt("record_key", 0)
    }

    // pause game
    fun pauseGame() {
        gameRunning = false
        player.stopAnimation()
    }

    init {
        options.inScaled = false
        // start the game
        restartGame()
    }
}