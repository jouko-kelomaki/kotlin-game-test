package fi.kelomaki.keloGame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.FitViewport
import fi.kelomaki.keloGame.KeloGame
import fi.kelomaki.keloGame.ecs.component.GraphicComponent
import fi.kelomaki.keloGame.ecs.component.MoveArrowBodyComponent
import fi.kelomaki.keloGame.ecs.component.MoveArrowHeadComponent
import fi.kelomaki.keloGame.ecs.component.TransformComponent
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with
import ktx.graphics.use
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<GameScreen>()

class GameScreen(game: KeloGame) : BaseScreen(game) {
    private val playerTexture = Texture(Gdx.files.internal("mursu.png"))
    private val backgroundTexture = Texture(Gdx.files.internal("bg-ice.png"))
    private val arrowHeadTexture = Texture(Gdx.files.internal("arrowHead.png"))
    private val arrowBodyTexture = Texture(Gdx.files.internal("arrowTail.png"))

    private val player = engine.entity {
        with<TransformComponent> {
            position.set(1f, 1f, 0f)
            size.set(40f, 40f)
        }
        with<GraphicComponent> {
            sprite.run {
                setRegion(playerTexture)
                setSize(40f, 40f)
                setOriginCenter()
            }
        }
    }

    private val backGround = engine.entity {
        with<TransformComponent> {
            position.set(0f, 0f, 2f)
            size.set(640f, 480f)
        }
        with<GraphicComponent> {
            sprite.run{
                setRegion(backgroundTexture)
                setSize(640f, 480f)
                setOriginCenter()
            }
        }
    }

    private val ArrowHead = engine.entity {
        with<MoveArrowHeadComponent>()
        with<TransformComponent> {
            position.set(100f, 20f, 1f)
            size.set(20f, 20f)
        }
        with<GraphicComponent> {
            sprite.run {
                setRegion(arrowHeadTexture)
                setSize(20f, 20f)
                setOriginCenter()
            }
        }
    }

    private val ArrowBody = engine.entity {
        with<MoveArrowBodyComponent>()
        with<TransformComponent> {
            size.set(100f, 20f)
        }
        with<GraphicComponent> {
            sprite.run {
                setRegion(arrowBodyTexture)
                setSize(100f, 20f)
                setOriginCenter()
                setOrigin(0f, originY) // to cleverly rotate along with the head
            }
        }
    }





    override fun show() {
        LOG.debug { "gameScreen is on" }
    }

    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun dispose() {
        playerTexture.dispose()
    }

}