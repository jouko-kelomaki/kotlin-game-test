package fi.kelomaki.keloGame.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class MoveArrowHeadComponent : Component, Pool.Poolable{
    override fun reset() {
        // nothing to reset
    }

    companion object {
        val mapper = mapperFor<MoveArrowHeadComponent>()
    }
}