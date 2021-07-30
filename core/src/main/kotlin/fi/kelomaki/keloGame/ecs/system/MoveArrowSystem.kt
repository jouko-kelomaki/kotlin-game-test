package fi.kelomaki.keloGame.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.ashley.oneOf
import ktx.log.debug
import ktx.log.logger
import kotlin.math.atan
import com.badlogic.gdx.Gdx
import fi.kelomaki.keloGame.ecs.component.*
import java.lang.Float.min
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt

private val LOG = logger<MoveArrowSystem>()

private val correctVectorFromInput = { Vector2(Gdx.input.x.toFloat(), 480f - Gdx.input.y ) }


class MoveArrowSystem() : IteratingSystem(oneOf(MoveArrowHeadComponent::class,
    MoveArrowBodyComponent::class, MoveArrowComponent::class).get()) {
    private val target : Vector2 = Vector2(20f, 20f)
    private val distanceFromTarget = 15f


    override fun processEntity(entity: Entity, deltaTime: Float) {
        val mouseCoords = correctVectorFromInput()

        val targetToMouse = Vector2(mouseCoords.x, mouseCoords.y).mulAdd(target, -1f)
        val headPos = Vector2(0f, 0f).let {
            it.set(Vector2(target.x, target.y)
                .add(Vector2(targetToMouse.x, targetToMouse.y)
                    .scl(distanceFromTarget / targetToMouse.len())))
        }
        val rotation = Math.toDegrees(atan(( (mouseCoords.y - target.y) / (mouseCoords.x - target.x)))
            .toDouble()).toFloat()

        entity[MoveArrowHeadComponent.mapper]?.run {
            entity[TransformComponent.mapper]?.run {
                position.set(headPos.x, headPos.y, 0f)
                rotationDeg = rotation + 180
                //LOG.debug { "head: ${position}, ${rotation}." }
            }
        }

        entity[MoveArrowBodyComponent.mapper]?.run {
            entity[TransformComponent.mapper]?.run {
                position.set(headPos.x + 10, headPos.y, 0f)
                rotationDeg = rotation

                val arrowLen = min(sqrt(
                    (mouseCoords.x - target.x).toDouble().pow(2.0)
                            + (mouseCoords.y - target.y).toDouble().pow(2.0)
                ).toFloat(), 100f)

                size.set(Vector2(arrowLen, size.y))
                entity[GraphicComponent.mapper]?.run {

                    LOG.debug { arrowLen.toString() }
                    sprite.run {
                        setRegion( texture.width - ceil(arrowLen).toInt(), 0 , ceil(arrowLen).toInt(), texture.height)
                    }
                }

                // LOG.debug { "head: ${position}, ${rotation}." }
            }
        }

        entity[MoveArrowComponent.mapper]?.run
    }
}