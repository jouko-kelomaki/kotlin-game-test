package fi.kelomaki.keloGame

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.FitViewport
import fi.kelomaki.keloGame.ecs.system.MoveArrowSystem
import fi.kelomaki.keloGame.ecs.system.RenderSystem
import fi.kelomaki.keloGame.screens.BaseScreen
import fi.kelomaki.keloGame.screens.GameScreen
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<KeloGame>()

class KeloGame : KtxGame<BaseScreen>() {

    val gameViewport = FitViewport(640f, 480f)
    val batch: Batch by lazy {SpriteBatch()}
    val engine: Engine by lazy {PooledEngine().apply {
        addSystem(RenderSystem(batch, gameViewport ))
        addSystem(MoveArrowSystem())
    }}
    
    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        LOG.debug {"instantiate game screen"}
        addScreen(GameScreen(this))
        setScreen<GameScreen>()
    }

    override fun dispose() {
        super.dispose()
        batch.dispose()
    }
}