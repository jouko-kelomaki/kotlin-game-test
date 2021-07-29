package keloGame.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import fi.kelomaki.keloGame.KeloGame
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

/** Launches the desktop (LWJGL3) application.  */

fun main() {
    Lwjgl3Application(KeloGame(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("kotlin-game-test")
        setWindowedMode(16*40, 12*40)
        setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
    })
}