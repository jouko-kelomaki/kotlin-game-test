package fi.kelomaki.keloGame.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import fi.kelomaki.keloGame.ecs.component.GraphicComponent
import fi.kelomaki.keloGame.ecs.component.TransformComponent
import ktx.ashley.get
import ktx.ashley.allOf
import ktx.graphics.use
import ktx.log.debug
import ktx.log.error
import ktx.log.logger

private val LOG = logger<RenderSystem>()

class RenderSystem(
    private val batch: Batch,
    private val gameViewport: Viewport
) : SortedIteratingSystem(
    allOf(TransformComponent::class, GraphicComponent::class).get(),
    compareBy { entity -> entity[TransformComponent.mapper] }
) {
    override fun update(deltaTime: Float) {
        forceSort()
        gameViewport.apply()
        batch.use(gameViewport.camera.combined) {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null) { "entity nullness error" }
        val graphic = entity[GraphicComponent.mapper]
        require(graphic != null) { "graphic component missing" }

        if (graphic.sprite.texture == null) {
            LOG.error { "entity missing texture for rendering" }
            return
        }

        graphic.sprite.run {
            rotation = transform.rotationDeg
            //LOG.debug { "(${originX}, ${originY}) ${entity}." }
            setBounds(transform.position.x, transform.position.y, transform.size.x, transform.size.y)
            draw(batch)
        }
    }
}